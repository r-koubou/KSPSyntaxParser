/* =========================================================================

    ReservedSymbolManager.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer.data.reserved

import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

import net.rkoubou.kspcompiler.ApplicationConstants
import net.rkoubou.kspcompiler.analyzer.AnalyzerConstants
import net.rkoubou.kspcompiler.analyzer.Argument
import net.rkoubou.kspcompiler.analyzer.Callback
import net.rkoubou.kspcompiler.analyzer.CallbackTable
import net.rkoubou.kspcompiler.analyzer.Command
import net.rkoubou.kspcompiler.analyzer.CommandArgument
import net.rkoubou.kspcompiler.analyzer.CommandTable
import net.rkoubou.kspcompiler.analyzer.ReturnType
import net.rkoubou.kspcompiler.analyzer.SymbolDefinition.SymbolType
import net.rkoubou.kspcompiler.analyzer.UIType
import net.rkoubou.kspcompiler.analyzer.UITypeTable
import net.rkoubou.kspcompiler.analyzer.Variable
import net.rkoubou.kspcompiler.analyzer.VariableTable
import net.rkoubou.kspcompiler.javacc.generated.ASTCallCommand
import net.rkoubou.kspcompiler.javacc.generated.ASTCallbackArgumentList
import net.rkoubou.kspcompiler.javacc.generated.ASTCallbackDeclaration
import net.rkoubou.kspcompiler.javacc.generated.ASTVariableDeclaration
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants
import net.rkoubou.kspcompiler.util.table.StringParser

/**
 * data/reserved に配備した予約済み変数、コマンド、コールバック、関数など各種シンボルの定義ファイルからデシリアライズする
 */
class ReservedSymbolManager private constructor() : KSPParserTreeConstants, AnalyzerConstants
{

    /** 予約済みUIタイプ変数  */
    private val uiTypes = HashMap<String, UIType>()

    /** 予約済み変数  */
    private val variables = HashMap<String, Variable>(512)

    /** 予約済みコマンド  */
    private val commands = HashMap<String, Command>(256)

    /** 予約済みコールバック  */
    private val callbacks = HashMap<String, Callback>()

    /**
     * 定義ファイルを再読み込み
     */
    @Throws(IOException::class)
    fun load()
    {
        loadUITypes()
        loadVariables()
        loadCallbacks()
        loadCommands()
    }

    /**
     * 指定されたUI型テーブルにこのクラスが読み込んだ外部変数を適用する
     */
    fun apply(dest : UITypeTable)
    {
        for(key in uiTypes.keys)
        {
            uiTypes[key]?.let { dest.add(it) }
        }
    }

    /**
     * 指定された変数テーブルにこのクラスが読み込んだ外部変数を適用する
     */
    fun apply(dest : VariableTable)
    {
        for(key in variables.keys)
        {
            variables[key]?.let { dest.add(it) }
        }
    }

    /**
     * 指定されたコマンドテーブルにこのクラスが読み込んだ外部コールバックを適用する
     */
    fun apply(dest : CommandTable)
    {
        for(key in commands.keys)
        {
            commands[key]?.let { dest.add(it) }
        }
    }

    /**
     * 指定されたコールバックテーブルにこのクラスが読み込んだ外部コールバックを適用する
     */
    fun apply(dest : CallbackTable)
    {
        for(name in callbacks.keys)
        {
            val v = callbacks[name]
            v?.let { dest.add(it, name) }
        }
    }

    /**
     * UIタイプの予約済み定義ファイルから UIType クラスインスタンスを生成する
     */
    @Throws(IOException::class)
    private fun loadUITypes()
    {
        val f = File(BASE_DIR, "uitypes.txt")
        val parser = StringParser()
        parser.parse(f)

        uiTypes.clear()
        for(row in parser.table)
        {
            val name = row.stringValue(0)
            val constant = "Y" == row.stringValue(1)
            val initializerRequired = "Y" == row.stringValue(2)
            val type = toVariableType(row.stringValue(3)).type
            var typeList = UIType.EMPTY_INITIALIZER_TYPE_LIST

            //--------------------------------------------------------------------------
            // 初期値代入式が必須の場合
            //--------------------------------------------------------------------------
            if(row.length() >= 5)
            {
                typeList = IntArray(row.length() - 4)
                var i = 4
                var x = 0
                while(i < row.length())
                {
                    typeList[x] = toVariableType(row.stringValue(i)).type
                    i++
                    x++
                }
            }
            val ui = UIType(name, true, type, constant, initializerRequired, typeList)
            uiTypes[name] = ui
        }
    }

    /**
     * 変数の予約済み定義ファイルから Variable クラスインスタンスを生成する
     */
    @Throws(IOException::class)
    private fun loadVariables()
    {
        val f = File(BASE_DIR, "variables.txt")
        val parser = StringParser()
        parser.parse(f)

        variables.clear()
        for(row in parser.table)
        {
            val v = toVariableType(row.stringValue(0))
            val name = v.toKSPTypeCharacter() + row.stringValue(1)
            val availableOnInit = "Y" == row.stringValue(2)

            v.name = name
            v.accessFlag = AnalyzerConstants.ACCESS_ATTR_CONST      // ビルトイン変数に代入を許可させない
            v.availableOnInit = availableOnInit        // on init 内で使用可能な変数かどうか。一部のビルトイン定数ではそれを許可していない。
            v.reserved = true                   // 予約変数
            v.referenced = true                   // 予約変数につき、使用・未使用に関わらず参照済みマーク
            v.state = AnalyzerConstants.SymbolState.LOADED     // 予約変数につき、値代入済みマーク
            v.value = v.defaultValue
            variables[name] = v
        }
    }

    /**
     * コマンドのの予約済み定義ファイルから Command クラスインスタンスを生成する
     */
    @Throws(IOException::class)
    private fun loadCommands()
    {
        val f = File(BASE_DIR, "commands.txt")
        val parser = StringParser()
        parser.parse(f)

        commands.clear()
        for(row in parser.table)
        {
            val returnType = row.stringValue(0)
            val name = row.stringValue(1)
            val availableCallback = row.stringValue(2)
            var hasParenthesis = false

            //--------------------------------------------------------------------------
            // data[3] 以降：引数を含む場合
            // 引数のAST、変数を生成
            //--------------------------------------------------------------------------
            val args = ArrayList<CommandArgument>()
            if(row.length() >= 4)
            {
                hasParenthesis = true
                val len = row.length()
                for(i in 3 until len)
                {
                    //--------------------------------------------------------------------------
                    // 複数のデータ型を許容するコマンドがあるので単一にせずにリストにストックしていく
                    //--------------------------------------------------------------------------
                    val typeString = row.stringValue(i)
                    args.add(toVariableTypeForArgument(typeString))
                }
            }
            //--------------------------------------------------------------------------
            // コマンドのAST、変数を生成
            //--------------------------------------------------------------------------
            run {
                val newItem : Command
                val ast = ASTCallCommand(KSPParserTreeConstants.JJTCALLCOMMAND)
                ast.symbol.name = name
                newItem = Command(ast)

                if(args.size > 0)
                {
                    newItem.argList.addAll(args)
                }
                newItem.hasParenthesis = hasParenthesis
                toReturnTypeForCommand(returnType, newItem.returnType)
                newItem.symbolType = SymbolType.Command
                newItem.reserved = true
                newItem.availableCallbackList.clear()
                toAvailableCommandOnCallbackList(availableCallback, newItem.availableCallbackList)
                commands.put(name, newItem)
            }

        } //~for( Column<String> col : parser.getTable() )
    }

    /**
     * コールバックの予約済み定義ファイルから Variable クラスインスタンスを生成する
     */
    @Throws(IOException::class)
    private fun loadCallbacks()
    {
        val f = File(BASE_DIR, "callbacks.txt")
        val parser = StringParser()
        parser.parse(f)

        callbacks.clear()
        for(row in parser.table)
        {
            val name = row.stringValue(0)
            val dup = "Y" == row.stringValue(1)

            //--------------------------------------------------------------------------
            // data[2] 以降：引数を含む場合
            // 引数のAST、変数を生成
            //--------------------------------------------------------------------------
            val args = ArrayList<Argument>()
            if(row.length() >= 3)
            {
                val len = row.length()
                for(i in 2 until len)
                {
                    var typeString = row.stringValue(i)
                    var requireDeclarationOnInit = false

                    if(typeString.startsWith("&"))
                    {
                        // ui_control など引数==宣言した変数の場合
                        requireDeclarationOnInit = true
                        typeString = typeString.substring(1)
                    }

                    val v = toVariableType(typeString)
                    val a = Argument(v)
                    a.name = "<undefined>"                             // シンボル収集時にマージ
                    a.requireDeclarationOnInit = requireDeclarationOnInit  // 引数の変数が on init で宣言した変数かどうか
                    a.reserved = true                                   // 予約変数
                    a.referenced = true                                   // 予約変数につき、使用・未使用に関わらず参照済みマーク
                    a.state = AnalyzerConstants.SymbolState.LOADED                     // 予約変数につき、値代入済みマーク
                    args.add(a)
                }
            }
            //--------------------------------------------------------------------------
            // コールバックのAST、変数を生成
            //--------------------------------------------------------------------------
            run {
                val newItem : Callback
                val ast = ASTCallbackDeclaration(KSPParserTreeConstants.JJTCALLBACKDECLARATION)
                ast.symbol.name = name
                if(args.size > 0)
                {
                    val astList = ASTCallbackArgumentList(KSPParserTreeConstants.JJTCALLBACKARGUMENTLIST)
                    for(a in args)
                    {
                        astList.args.add(a.name)
                    }
                    ast.jjtAddChild(astList, 0)
                    newItem = Callback(ast)
                }
                else
                {
                    newItem = Callback(ast)
                }
                newItem.name = name
                newItem.symbolType = SymbolType.Callback
                newItem.reserved = true
                newItem.declared = false
                newItem.isAllowDuplicate = dup
                callbacks.put(name, newItem)
            }

        } //~for( Column<String> col : parser.getTable() )
    }

    /**
     * 型識別文字から Variableクラスのtypeに格納する形式の値に変換する
     */
    private fun toVariableType(t : String) : Variable
    {
        var tchar = t
        val ret = Variable(ASTVariableDeclaration(KSPParserTreeConstants.JJTVARIABLEDECLARATION))
        var type = AnalyzerConstants.TYPE_NONE
        var accessFlag = AnalyzerConstants.ACCESS_ATTR_NONE
        var uiTypeInfo : UIType? = null

        tchar = tchar.intern()
        if(tchar === "*")
        {
            type = AnalyzerConstants.TYPE_ALL
        }
        else if(tchar === "X")
        {
            type = AnalyzerConstants.TYPE_UNKOWN
        }
        else if(tchar === "*[]")
        {
            type = AnalyzerConstants.TYPE_MULTIPLE or AnalyzerConstants.TYPE_ATTR_ARRAY
        }
        else if(tchar === "V")
        {
            type = AnalyzerConstants.TYPE_VOID
        }
        else if(tchar === "I" || tchar === "@I")
        {
            type = AnalyzerConstants.TYPE_INT
        }
        else if(tchar === "I[]")
        {
            type = AnalyzerConstants.TYPE_INT or AnalyzerConstants.TYPE_ATTR_ARRAY
        }
        else if(tchar === "R" || tchar === "@R")
        {
            type = AnalyzerConstants.TYPE_REAL
        }
        else if(tchar === "R[]")
        {
            type = AnalyzerConstants.TYPE_REAL or AnalyzerConstants.TYPE_ATTR_ARRAY
        }
        else if(tchar === "S" || tchar === "@S")
        {
            type = AnalyzerConstants.TYPE_STRING
        }
        else if(tchar === "S[]")
        {
            type = AnalyzerConstants.TYPE_STRING or AnalyzerConstants.TYPE_ATTR_ARRAY
        }
        else if(tchar === "B" || tchar === "@B")
        {
            type = AnalyzerConstants.TYPE_BOOL
        }
        else if(tchar === "B[]")
        {
            type = AnalyzerConstants.TYPE_BOOL or AnalyzerConstants.TYPE_ATTR_ARRAY
        }
        else if(tchar === "PP")
        {
            type = AnalyzerConstants.TYPE_PREPROCESSOR_SYMBOL
        }
        else if(tchar === "KEY")
        {
            type = AnalyzerConstants.TYPE_KEYID
        }
        else if(tchar == "ui_*")
        {
            uiTypeInfo = UIType.ANY_UI
            accessFlag = accessFlag or AnalyzerConstants.ACCESS_ATTR_UI
        }
        else if(tchar.startsWith("ui_"))
        {
            var found = false
            if(uiTypes.containsKey(tchar))
            {
                uiTypeInfo = uiTypes[tchar]
                accessFlag = accessFlag or AnalyzerConstants.ACCESS_ATTR_UI
                found = true
            }
            require(found) { "Unknown type : $tchar" }
        }
        else
        {
            throw IllegalArgumentException("Unknown type : $tchar")
        }// 指定のUIタイプの場合
        // 全UIタイプを許容する場合

        if(tchar.startsWith("@"))
        {
            accessFlag = accessFlag or AnalyzerConstants.ACCESS_ATTR_CONST
        }

        ret.name = "tmp"
        ret.type = type
        ret.accessFlag = accessFlag
        ret.uiTypeInfo = uiTypeInfo

        return ret
    }

    /**
     * 型識別文字から戻り値の値に変換する(コマンドによって複数の戻り値がある)
     */
    private fun toReturnTypeForCommand(t : String, dest : ReturnType)
    {
        var tchar = t
        tchar = tchar.intern()

        val orCond = tchar.split(REGEX_SPLIT_COND_OR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        //--------------------------------------------------------------------------
        // A または B または .... n の場合
        //--------------------------------------------------------------------------
        if(orCond.size >= 2)
        {
            for(i in orCond)
            {
                dest.typeList.add(toVariableType(i).type)
            }
        }
        else
        {
            dest.typeList.add(toVariableType(tchar).type)
        }
    }

    /**
     * 型識別文字から引数の値に変換する(コマンド引数で複数の型を扱う場合)
     */
    private fun toVariableTypeForArgument(t : String) : CommandArgument
    {
        var tchar = t
        tchar = tchar.intern()
        val ret : CommandArgument
        val args = ArrayList<Variable>()

        val orCond = tchar.split(REGEX_SPLIT_COND_OR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        //--------------------------------------------------------------------------
        // A または B または .... n の場合
        //--------------------------------------------------------------------------
        if(orCond.size >= 2)
        {
            for(i in orCond)
            {
                val v = toVariableType(i)
                args.add(v)
            }
        }
        else
        {
            val v = toVariableType(tchar)
            args.add(v)
        }

        //--------------------------------------------------------------------------
        // 共通の値設定
        //--------------------------------------------------------------------------
        for(x in args.indices)
        {
            val v = args[x]
            v.reserved = false                // KONTAKT内部のビルトインコマンドにつき、非予約変数
            v.referenced = true                 // KONTAKT内部のビルトインコマンドにつき、使用・未使用に関わらず参照済みマーク
            v.state = AnalyzerConstants.SymbolState.LOADED   // KONTAKT内部のビルトインコマンドにつき、値代入済みマーク
            if(v.uiTypeInfo != null)
            {
                v.uiTypeName = v.uiTypeInfo!!.name
            }
        }

        ret = CommandArgument(args)
        return ret
    }

    /**
     * コマンドテーブル生成用：利用可能なコールバック名の記述を元に、利用可能リストを生成する
     */
    private fun toAvailableCommandOnCallbackList(callbackName__ : String, dest : HashMap<String, Callback?>)
    {
        var callbackName = callbackName__
        callbackName = callbackName.intern()
        val orCond = callbackName.split(REGEX_SPLIT_COND_OR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        //--------------------------------------------------------------------------
        // 全コールバックで使用可能
        //--------------------------------------------------------------------------
        if(callbackName === "*")
        {
            dest.putAll(callbacks)
            return
        }
        //--------------------------------------------------------------------------
        // A または B または .... n の場合
        //--------------------------------------------------------------------------
        if(orCond.size >= 2)
        {
            for(i in orCond)
            {
                if(callbacks.containsKey(i))
                {
                    dest[i] = callbacks[i]
                }
            }
        }
        else if(callbackName.startsWith(COND_NOT))
        {
            val exclude = callbackName.substring(1)
            for(key in callbacks.keys)
            {
                if(key != exclude)
                {
                    dest[key] = callbacks[key]
                }
            }
        }
        else
        {
            if(callbacks.containsKey(callbackName))
            {
                dest[callbackName] = callbacks[callbackName]
            }
        }//--------------------------------------------------------------------------
        // 単体のコールバック指定
        //--------------------------------------------------------------------------
        //--------------------------------------------------------------------------
        // A 以外の場合
        //--------------------------------------------------------------------------
    }

    companion object
    {
        /** 定義ファイルの場所  */
        val BASE_DIR = ApplicationConstants.DATA_DIR + "/symbols"

        /** split処理で使用する、条件式ORの文字列表現  */
        const val SPLIT_COND_OR = "||"

        /** split処理で使用する、条件式NOTの文字列表現  */
        const val COND_NOT = "!"

        /** split処理で使用する、条件式ORの正規表現  */
        const val REGEX_SPLIT_COND_OR = "\\|\\|"

        /** シングルトンインスタンス  */
        val manager = ReservedSymbolManager()

        /**
         * Unit test
         */
        @Throws(Throwable::class)
        @JvmStatic
        fun main(args : Array<String>)
        {
            // command: java -classpath ./target/classes/ net.rkoubou.kspcompiler.analyzer.ReservedSymbolManager
            val mgr = ReservedSymbolManager.manager
            mgr.load()
        }
    }
}
