/* =========================================================================

    Obfuscator.kt
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.obfuscator

import net.rkoubou.kspcompiler.analyzer.*
import net.rkoubou.kspcompiler.javacc.generated.*
import net.rkoubou.kspcompiler.options.CommandlineOptions

/**
 * オブファスケーター実行クラス
 */
open class Obfuscator
    (rootNode : ASTRootNode, symbolCollector : SymbolCollector)
    : BasicEvaluationAnalyzerTemplate(rootNode, symbolCollector)
{

    // ソースコード生成バッファ
    protected val outputCode = StringBuilder(1024 * 1024 * 32)

    /**
     * 生成されたKSPスクリプトの取得
     */
    val generatedScript : String
        get() = outputCode.toString()

    /**
     * オブファスケートの実行
     */
    @Throws(Exception::class)
    override fun analyze()
    {
        // オブファスケート：ユーザー定義のシンボル
        ShortSymbolGenerator.reset()
        variableTable.obfuscate()
        userFunctionTable.obfuscate()
        // KSPスクリプト生成
        outputCode.delete(0, outputCode.length)
        astRootNode.jjtAccept(this, null)
    }

    //--------------------------------------------------------------------------
    // ユーティリティ
    //--------------------------------------------------------------------------

    /**
     * 出力コードに改行を挿入する
     */
    protected open fun appendEOL()
    {
        outputCode.append("\n")
    }

    //--------------------------------------------------------------------------
    // 変数宣言
    //--------------------------------------------------------------------------

    /**
     * 変数宣言
     */
    override fun visit(node : ASTVariableDeclaration, data : Any) : Any
    {
/*
    VariableDeclaration                     // NOW
            -> ASTVariableInitializer
                -> [
                      ArrayInitializer
                    | UIInitializer
                    | PrimitiveInititalizer
                ]
*/
        val v = variableTable.search(node.symbol)!!
        if(!v.referenced && !v.reserved)
        {
            return node
        }

        // ユーザー定数で、初期化式中に１つもビルトイン変数・コマンドを含んでいない場合
        if(v.isConstant && !node.hasNode(null, SimpleNode.ReservedSymbolCondition, true))
        {
            return node
        }

        outputCode.append("declare ")

        if(v.isConstant)
        {
            outputCode.append("const ")
        }
        else if(v.isPolyphonicVariable)
        {
            outputCode.append("polyphonic ")
        }

        if(node.jjtGetNumChildren() == 0)
        {
            // 宣言のみ
            // const も付与されないので変数参照時の値は変数名
            if(v.isUIVariable)
            {
                outputCode.append(v.uiTypeName).append(" ")
            }
            v.value = v.variableName
            outputCode.append(v.variableName)
            appendEOL()
            return node
        }

        node.jjtGetChild(0).jjtAccept(this, node)
        appendEOL()

        return node
    }

    /**
     * 変数宣言(+初期値代入)
     */
    override fun visit(node : ASTVariableInitializer, data : Any) : Any
    {
/*
    VariableDeclaration
            -> ASTVariableInitializer   // NOW
                -> [
                      ArrayInitializer
                    | UIInitializer
                    | PrimitiveInititalizer
                ]
*/
        node.childrenAccept(this, data)
        return node
    }

    /**
     * プリミティブ型宣言の実装
     */
    override fun visit(node : ASTPrimitiveInititalizer, data : Any) : Any
    {
/*
    VariableDeclaration
            -> ASTVariableInitializer
                -> [
                      ArrayInitializer
                    | UIInitializer
                    | PrimitiveInititalizer     //NOW
                        -> [Expression]
                ]
*/
        val v = variableTable.search((node.jjtGetParent().jjtGetParent() as SimpleNode).symbol)!!

        if(node.jjtGetNumChildren() == 0)
        {
            // 初期値代入なし
            return node
        }

        val expr = node.jjtGetChild(0) as SimpleNode
        val varName = v.variableName

        // UI型変数+初期化子
        if(v.isUIVariable)
        {
            uiInitializerImpl(node, v, data)
        }
        else
        {
            // プリミティブ初期値代入。
            outputCode.append(varName).append(":=")
            expr.jjtAccept(this, data)
        }

        return node
    }

    /**
     * 配列型宣言の実装
     */
    override fun visit(node : ASTArrayInitializer, data : Any) : Any
    {
/*
    VariableDeclaration
            -> ASTVariableInitializer
                -> [
                      ArrayInitializer        //NOW
                          -> ArrayIndex
                          -> Expression
                          -> (,Expression)*
                    | UIInitializer
                    | PrimitiveInititalizer
                ]
*/
        val v = variableTable.search((node.jjtGetParent().jjtGetParent() as SimpleNode).symbol)!!

        if(node.hasAssign)
        {
            arrayInitializerImpl(node, data, false)
        }
        else
        {
            if(v.isUIVariable)
            {
                uiInitializerImpl(node, v, data)
            }
            else
            {
                arrayInitializerImpl(node, data, false)
            }
        }
        return node
    }

    /**
     * 配列型宣言の実装(詳細).
     * UI変数かつ配列型のケースもあるので外部化
     */
    protected open fun arrayInitializerImpl(node : SimpleNode, data : Any, forceSkipInitializer : Boolean)
    {
/*
    VariableDeclaration
            -> ASTVariableInitializer
                -> [
                        ArrayInitializer        //NOW
                            -> ArrayIndex
                            -> Expression
                            -> (,Expression)*
                    | UIInitializer
                    | PrimitiveInititalizer
                ]
*/
        val v = variableTable.search((node.jjtGetParent().jjtGetParent() as SimpleNode).symbol)!!

        outputCode
            .append(v.variableName)
            .append("[")
            .append(v.arraySize)
            .append("]")

        //--------------------------------------------------------------------------
        // 初期値代入
        //--------------------------------------------------------------------------
        if(forceSkipInitializer || node.jjtGetNumChildren() == 1)
        {
            // 初期値代入なし
            return
        }

        outputCode.append(":=(")

        val length = node.jjtGetNumChildren()
        for(i in 1 until length)
        {
            node.jjtGetChild(i).jjtAccept(this, data)
            if(i < length - 1)
            {
                outputCode.append(",")
            }
        }

        outputCode.append(")")

    }

    /**
     * UI型宣言の実装
     */
    protected open fun uiInitializerImpl(initializer : SimpleNode, v : Variable, jjtVisitorData : Any)
    {
/*
    VariableDeclaration
            -> ASTVariableInitializer
                -> [
                        ArrayInitializer
                            -> ArrayIndex
                            -> Expression
                            -> (,Expression)*
                    | UIInitializer             //NOW
                            -> [ ArrayIndex ]
                            -> Expressiom
                            -> (,Expression)*
                    | PrimitiveInititalizer
                ]
*/
        val uiType = uiTypeTable.search(v.uiTypeName)!!

        outputCode
            .append(uiType.name)
            .append(" ")

        //--------------------------------------------------------------------------
        // ui_#### が配列型の場合、要素数宣言までのコード生成
        //--------------------------------------------------------------------------
        if(SymbolDefinition.isArray(uiType.uiValueType))
        {
            arrayInitializerImpl(initializer, jjtVisitorData, true)
        }
        else
        {
            outputCode.append(v.variableName)
        }

        //--------------------------------------------------------------------------
        // 初期値代入
        //--------------------------------------------------------------------------
        if(initializer.jjtGetNumChildren() == 0)
        {
            // 初期値代入なし
            return
        }

        // for のカウンタ初期値の設定
        var i : Int = if(initializer.jjtGetChild(0).id == KSPParserTreeConstants.JJTARRAYINDEX)
        {
            // 変数配列型の場合
            // node[ 0 ]        : ArrayIndex
            // node[ 1 ... n ]  : Expression
            1
        }
        else
        {
            // node[ 0 ... n ]  : Expression
            0
        }

        val length = initializer.jjtGetNumChildren()

        outputCode.append("(")

        // i は上記で初期化済み
        while(i < length)
        {
            val n = initializer.jjtGetChild(i)
            n.jjtAccept(this, jjtVisitorData)
            if(i < length - 1)
            {
                outputCode.append(",")
            }
            i++
        }

        outputCode.append(")")
        return
    }

    //--------------------------------------------------------------------------
    // コールバック本体
    //--------------------------------------------------------------------------

    /**
     * コールバック定義
     */
    override fun visit(node : ASTCallbackDeclaration, data : Any) : Any
    {
        /*
            ASTCallbackDeclaration
                [ -> ASTCallbackArgumentList ]
        */

        outputCode.append("on ").append(node.symbol.name)

        if(node.jjtGetNumChildren() >= 2)
        {
            // コールバック引数リストあり
            val argList = node.jjtGetChild(0) as ASTCallbackArgumentList
            val listSize = argList.args.size
            outputCode.append("(")
            for(i in 0 until listSize)
            {
                val arg = argList.args[i]
                val v = variableTable.search(arg)!!
                val name = v.variableName

                outputCode.append(name)
                if(i < listSize - 1)
                {
                    outputCode.append(",")
                }
            }
            outputCode.append(")")
        }
        appendEOL()

        defaultVisit(node, data)

        outputCode.append("end on")
        appendEOL()

        return node
    }


    //--------------------------------------------------------------------------
    // 式
    //--------------------------------------------------------------------------

    /**
     * 条件式ノードのソースコード生成
     */
    protected open fun appendConditionalNode(node : Node, data : Any, operator : String)
    {
        node.jjtGetChild(0).jjtAccept(this, data)
        outputCode.append(operator)
        node.jjtGetChild(1).jjtAccept(this, data)
    }

    /**
     * 条件式 OR
     */
    override fun visit(node : ASTConditionalOr, data : Any) : Any
    {
        appendConditionalNode(node, data, " or ")
        return node
    }

    /**
     * 条件式 AND
     */
    override fun visit(node : ASTConditionalAnd, data : Any) : Any
    {
        appendConditionalNode(node, data, " and ")
        return node
    }

    /**
     * 論理積
     */
    override fun visit(node : ASTBitwiseOr, data : Any) : Any
    {
        appendBinaryOperatorNode(node, data, " .or. ")
        return node
    }

    /**
     * 論理和
     */
    override fun visit(node : ASTBitwiseAnd, data : Any) : Any
    {
        appendBinaryOperatorNode(node, data, " .and. ")
        return node
    }

    /**
     * 比較 (=)
     */
    override fun visit(node : ASTEqual, data : Any) : Any
    {
        appendConditionalNode(node, data, "=")
        return node
    }

    /**
     * 比較 (#)
     */
    override fun visit(node : ASTNotEqual, data : Any) : Any
    {
        appendConditionalNode(node, data, "#")
        return node
    }

    /**
     * 不等号(<)
     */
    override fun visit(node : ASTLT, data : Any) : Any
    {
        appendConditionalNode(node, data, "<")
        return node
    }

    /**
     * 不等号(>)
     */
    override fun visit(node : ASTGT, data : Any) : Any
    {
        appendConditionalNode(node, data, ">")
        return node
    }

    /**
     * 不等号(<=)
     */
    override fun visit(node : ASTLE, data : Any) : Any
    {
        appendConditionalNode(node, data, "<=")
        return node
    }

    /**
     * 不等号(>=)
     */
    override fun visit(node : ASTGE, data : Any) : Any
    {
        appendConditionalNode(node, data, ">=")
        return node
    }

    /**
     * 2項演算ノードのソースコード生成
     */
    protected open fun appendBinaryOperatorNode(node : SimpleNode, data : Any, operator : String)
    {
        val exprL = node.jjtGetChild(0) as SimpleNode
        val exprR = node.jjtGetChild(1) as SimpleNode

        // 畳み込みが無理な場合は式を出力
        if(!node.symbol.isConstant)
        {
            // 元のコードの演算子の優先度を担保するため、全て括弧で括る
            val stradd = node.id == KSPParserTreeConstants.JJTSTRADD
            if(!stradd)
            {
                outputCode.append("(")
            }

            exprL.jjtAccept(this, data)
            outputCode.append(operator)
            exprR.jjtAccept(this, data)

            if(!stradd)
            {
                outputCode.append(")")
            }
        }
        else
        {
            outputCode.append(node.symbol.value)
        }// 畳み込み済みの定数値を出力
    }

    /**
     * 加算(+)
     */
    override fun visit(node : ASTAdd, data : Any) : Any
    {
        appendBinaryOperatorNode(node, data, "+")
        return node
    }

    /**
     * 減算(-)
     */
    override fun visit(node : ASTSub, data : Any) : Any
    {
        appendBinaryOperatorNode(node, data, "-")
        return node
    }

    /**
     * 文字列連結
     */
    override fun visit(node : ASTStrAdd, data : Any) : Any
    {
        appendBinaryOperatorNode(node, data, "&")
        return node
    }

    /**
     * 乗算(*)
     */
    override fun visit(node : ASTMul, data : Any) : Any
    {
        appendBinaryOperatorNode(node, data, "*")
        return node
    }

    /**
     * 除算(/)
     */
    override fun visit(node : ASTDiv, data : Any) : Any
    {
        appendBinaryOperatorNode(node, data, "/")
        return node
    }

    /**
     * 余算(mod)
     */
    override fun visit(node : ASTMod, data : Any) : Any
    {
        appendBinaryOperatorNode(node, data, " mod ")
        return node
    }

    /**
     * 単項演算ノードのソースコード生成
     */
    protected open fun appendSingleOperatorNode(node : SimpleNode, data : Any, operator : String)
    {
        // 畳み込みが無理な場合は式を出力
        if(!node.symbol.isConstant)
        {
            val expr = node.jjtGetChild(0) as SimpleNode
            outputCode.append(operator)
            expr.jjtAccept(this, data)
        }
        else
        {
            outputCode.append(node.symbol.value)
        }// 畳み込み済みの定数値を出力
    }

    /**
     * 単項マイナス(-)
     */
    override fun visit(node : ASTNeg, data : Any) : Any
    {
        appendSingleOperatorNode(node, data, "-")
        return node
    }

    /**
     * 単項NOT(not)
     */
    override fun visit(node : ASTNot, data : Any) : Any
    {
        appendSingleOperatorNode(node, data, " .not. ")
        return node
    }

    /**
     * 単項論理否定(not)
     */
    override fun visit(node : ASTLogicalNot, data : Any) : Any
    {
        appendSingleOperatorNode(node, data, "not ")
        return node
    }

    /**
     * 代入式
     */
    override fun visit(node : ASTAssignment, data : Any) : Any
    {
/*
                :=
                +
                |
            +----+----+
            |         |
    0: <variable>   1:<expr>
*/

        val exprL = node.jjtGetChild(0) as SimpleNode
        val exprR = node.jjtGetChild(1) as SimpleNode

        exprL.jjtAccept(this, data)
        outputCode.append(":=")
        exprR.jjtAccept(this, data)

        appendEOL()

        return node
    }

    /**
     * 変数参照
     * @return node自身
     */
    override fun visit(node : ASTRefVariable, data : Any) : Any
    {
/*
        <variable> [ 0:<arrayindex> ]
*/
        //--------------------------------------------------------------------------
        // 宣言済みかどうか
        //--------------------------------------------------------------------------
        val v = variableTable.search(node.symbol)!!

        // ユーザー定義定数なら展開
        if(v.isConstant && !v.reserved && v.value != null)
        {
            outputCode.append(v.value)
        }
        else
        {
            outputCode.append(v.variableName)
            // 配列型なら添字チェック
            if(v.isArray)
            {
                // 上位ノードの型評価式用
                if(node.jjtGetNumChildren() > 0)
                {
                    // 添え字がある
                    // 要素へのアクセスであるため、配列ビットフラグを外したプリミティブ型として扱う
                    node.jjtGetChild(0).jjtAccept(this, node)
                }
                else
                {
                    // 添え字が無い
                    // 配列変数をコマンドの引数に渡すケース
                    // 配列型としてそのまま扱う
                }
            }
            return node
        }
        return node
    }

    /**
     * 配列の添え字([])
     * @param data 親ノード
     */
    override fun visit(node : ASTArrayIndex, data : Any) : Any
    {
/*
            parent:<variable>
                    +
                    |
                    +
                [ 0:<expr> ]
*/

        val expr = node.jjtGetChild(0)

        outputCode.append("[")
        expr.jjtAccept(this, data)
        outputCode.append("]")

        return node
    }

    //--------------------------------------------------------------------------
    // コマンドコール
    //--------------------------------------------------------------------------

    /**
     * コマンド呼び出し
     */
    override fun visit(node : ASTCallCommand, data : Any) : Any
    {
        var cmd : Command? = commandTable.search(node.symbol)

        if(cmd == null)
        {
            // ドキュメントに記載のない隠しコマンドの可能性
            // 存在するコマンドとして扱う
            cmd = Command(node)
        }

        outputCode.append(cmd.name)
        if(cmd.hasParenthesis)
        {
            outputCode.append("(")
            node.childrenAccept(this, data)
            outputCode.append(")")
        }

        // このコマンド呼び出しが上位ノードのコマンド引数
        // としてコールされている場合は改行出来ないためチェックをしている
        if(node.jjtGetParent().id == KSPParserTreeConstants.JJTBLOCK)
        {
            appendEOL()
        }
        return node
    }

    /**
     * コマンド引数
     */
    override fun visit(node : ASTCommandArgumentList, data : Any) : Any
    {
        val childrenNum = node.jjtGetNumChildren()

        //--------------------------------------------------------------------------
        // 引数の出力
        //--------------------------------------------------------------------------
        for(i in 0 until childrenNum)
        {
            node.jjtGetChild(i).jjtAccept(this, data)
            if(i < childrenNum - 1)
            {
                outputCode.append(",")
            }
        }
        return node
    }

    //--------------------------------------------------------------------------
    // ユーザー定義関数
    //--------------------------------------------------------------------------

    /**
     * ユーザー定義関数宣言
     */
    override fun visit(node : ASTUserFunctionDeclaration, data : Any) : Any
    {
        val block = node.jjtGetChild(0)
        val func = userFunctionTable.search(node.symbol.name)!!

        if(!CommandlineOptions.options.inlineUserFunction && func.referenced)
        {
            outputCode.append("function ").append(func.name)
            appendEOL()
            block.jjtAccept(this, data)
            outputCode.append("end function ")
            appendEOL()
        }

        return node
    }

    /**
     * ユーザー定義関数呼び出し
     */
    override fun visit(node : ASTCallUserFunctionStatement, data : Any) : Any
    {
        val func = userFunctionTable.search(node.symbol)!!
        if(CommandlineOptions.options.inlineUserFunction)
        {
            // インライン展開
            val block = func.astNode.jjtGetChild(0)
            block.jjtAccept(this, data)
        }
        else
        {
            outputCode.append("call ").append(func.name)
            appendEOL()
        }
        return node
    }

    //--------------------------------------------------------------------------
    // ステートメント
    //--------------------------------------------------------------------------

    /**
     * スコープ（コールバック・ユーザー定義関数のルート）
     */
    override fun visit(node : ASTBlock, data : Any) : Any
    {
        return node.childrenAccept(this, data)
    }

    /**
     * if 条件式の評価
     */
    override fun visit(node : ASTIfStatement, data : Any) : Any
    {
/*
        if
            -> <expr>       [0]
            -> <block>      [1]
        | else
            -> <block>      [2]
*/
        //--------------------------------------------------------------------------
        // ifスコープ
        //--------------------------------------------------------------------------
        run {
            val cond = node.jjtGetChild(0)
            val block = node.jjtGetChild(1)

            outputCode.append("if").append("(")
            // if( <cond> )
            cond.jjtAccept(this, data)
            outputCode.append(")")
            appendEOL()

            block.jjtAccept(this, data)
        }
        //--------------------------------------------------------------------------
        // else スコープ
        //--------------------------------------------------------------------------
        if(node.jjtGetNumChildren() > 2)
        {
            val block = node.jjtGetChild(2)
            outputCode.append("else")
            appendEOL()

            block.jjtAccept(this, data)
        }

        outputCode.append("end if")
        appendEOL()

        return node
    }

    /**
     * select~case の評価
     */
    override fun visit(node : ASTSelectStatement, data : Any) : Any
    {
/*
        select
            -> <expr>
            -> <case>
                -> <casecond>
                -> [ to <expr> ]
                -> <block>
            -> <case>
                -> <casecond>
                -> [ to <expr> ]
                -> <block>
            :
            :
*/

        val cond = node.jjtGetChild(0)

        outputCode.append("select").append("(")
        cond.jjtAccept(this, data)
        outputCode.append(")")
        appendEOL()

        //--------------------------------------------------------------------------
        // case: 整数の定数または定数宣言した変数が有効
        //--------------------------------------------------------------------------
        for(i in 1 until node.jjtGetNumChildren())
        {
            val caseNode = node.jjtGetChild(i) as SimpleNode
            val caseCond1 = caseNode.jjtGetChild(0) as SimpleNode
            var caseCond2 : SimpleNode? = null
            val blockNode = caseNode.jjtGetChild(caseNode.jjtGetNumChildren() - 1) as SimpleNode

            // to <expr>
            if(caseNode.jjtGetNumChildren() >= 2)
            {
                val n = caseNode.jjtGetChild(1) as SimpleNode
                if(n.id == KSPParserTreeConstants.JJTCASECONDITION)
                {
                    // to
                    caseCond2 = caseNode.jjtGetChild(1) as SimpleNode
                }
            }

            // case
            outputCode.append("case ")
            caseCond1.jjtAccept(this, data)

            // to
            if(caseCond2 != null)
            {
                outputCode.append(" to ")
                caseCond2.jjtAccept(this, data)
            }
            appendEOL()

            // block statement
            blockNode.jjtAccept(this, data)
        }

        outputCode.append("end select")
        appendEOL()

        return node
    }

    /**
     * casecondの評価
     */
    override fun visit(node : ASTCaseCondition, data : Any) : Any
    {
/*
    <casecond>
        -> <expr>
*/
        val n = node.jjtGetChild(0)
        n.jjtAccept(this, data)
        return n
    }

    /**
     * while 条件式の評価
     */
    override fun visit(node : ASTWhileStatement, data : Any) : Any
    {
 /*
        while
            -> <expr>
            -> <block>
*/
        val cond = node.jjtGetChild(0)
        val block = node.jjtGetChild(1)

        outputCode.append("while").append("(")

        // while( <cond> )
        cond.jjtAccept(this, data)
        outputCode.append(")")
        appendEOL()

        block.jjtAccept(this, data)

        outputCode.append("end while")
        appendEOL()

        return node
    }

    /**
     * リテラル定数参照
     * @return node自身
     */
    override fun visit(node : ASTLiteral, data : Any) : Any
    {
        outputCode.append(node.symbol.value)
        return node
    }

    //--------------------------------------------------------------------------
    // プリプロセッサ
    //--------------------------------------------------------------------------

    /**
     * プリプロセッサ def/undef のコード生成
     */
    protected open fun appendPreprocessorDefine(node : SimpleNode, keyword : String)
    {
        outputCode.append(keyword).append("(").append(node.symbol.name).append(")")
        appendEOL()
    }

    /**
     * プリプロセッサシンボル定義
     */
    override fun visit(node : ASTPreProcessorDefine, data : Any) : Any
    {
        appendPreprocessorDefine(node, "SET_CONDITION")
        return node
    }

    /**
     * プリプロセッサシンボル破棄
     */
    override fun visit(node : ASTPreProcessorUnDefine, data : Any) : Any
    {
        appendPreprocessorDefine(node, "RESET_CONDITION")
        return node
    }

    /**
     * プリプロセッサ ifdef/ifndef のコード生成
     */
    protected open fun appendPreprocessorCondition(node : SimpleNode, keyword : String, jjtVisitorData : Any)
    {
        var block : Node? = null
        if(node.jjtGetNumChildren() > 0)
        {
            block = node.jjtGetChild(0)
        }
        outputCode.append(keyword).append("(").append(node.symbol.name).append(")")
        block?.jjtAccept(this, jjtVisitorData)
        outputCode.append("END_USE_CODE")
        appendEOL()
    }

    /**
     * ifdef
     */
    override fun visit(node : ASTPreProcessorIfDefined, data : Any) : Any
    {
        appendPreprocessorCondition(node, "USE_CODE_IF", data)
        return node
    }

    /**
     * ifndef
     */
    override fun visit(node : ASTPreProcessorIfUnDefined, data : Any) : Any
    {
        appendPreprocessorCondition(node, "USE_CODE_IF_NOT", data)
        return node
    }

    /**
     * 生成されたコードを返す
     */
    override fun toString() : String
    {
        return outputCode.toString()
    }
}
