/* =========================================================================

    SemanticAnalyzer.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.analyzer.SymbolDefinition.SymbolType
import net.rkoubou.kspcompiler.javacc.generated.ASTArrayIndex
import net.rkoubou.kspcompiler.javacc.generated.ASTArrayInitializer
import net.rkoubou.kspcompiler.javacc.generated.ASTAssignment
import net.rkoubou.kspcompiler.javacc.generated.ASTBlock
import net.rkoubou.kspcompiler.javacc.generated.ASTCallCommand
import net.rkoubou.kspcompiler.javacc.generated.ASTCallUserFunctionStatement
import net.rkoubou.kspcompiler.javacc.generated.ASTCaseCondition
import net.rkoubou.kspcompiler.javacc.generated.ASTCommandArgumentList
import net.rkoubou.kspcompiler.javacc.generated.ASTIfStatement
import net.rkoubou.kspcompiler.javacc.generated.ASTPrimitiveInititalizer
import net.rkoubou.kspcompiler.javacc.generated.ASTRefVariable
import net.rkoubou.kspcompiler.javacc.generated.ASTSelectStatement
import net.rkoubou.kspcompiler.javacc.generated.ASTUserFunctionDeclaration
import net.rkoubou.kspcompiler.javacc.generated.ASTVariableDeclaration
import net.rkoubou.kspcompiler.javacc.generated.ASTVariableInitializer
import net.rkoubou.kspcompiler.javacc.generated.ASTWhileStatement
import net.rkoubou.kspcompiler.javacc.generated.SimpleNode
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants
import net.rkoubou.kspcompiler.options.CommandlineOptions

/**
 * 意味解析実行クラス
 */
class SemanticAnalyzer
/**
 * ctor
 */
    (symbolCollector : SymbolCollector) : BasicEvaluationAnalyzerTemplate(symbolCollector.astRootNode, symbolCollector)
{

    // 局所的にのみ使用することを前提としたワークエリア
    private val tempSymbol = SymbolDefinition()

    /**
     * 意味解析の実行
     */
    @Throws(Exception::class)
    override fun analyze()
    {
        astRootNode.jjtAccept(this, null)

        //--------------------------------------------------------------------------
        // 解析後の未使用・未初期化シンボルの洗い出し
        //--------------------------------------------------------------------------

        if(CommandlineOptions.options.unused)
        {
            for(s in variableTable.toArray())
            {
                val v = s as Variable
                // 参照
                if(!v.referenced)
                {
                    MessageManager.printlnW(MessageManager.PROPERTY_WARNING_SEMANTIC_UNUSE_VARIABLE, v)
                    AnalyzeErrorCounter.w()
                }
                // 初期化（１度も値が格納されていない）
                // ただし、UI型変数は除外
                if(CommandlineOptions.options.strict && v.state == AnalyzerConstants.SymbolState.UNLOADED && !v.isUIVariable)
                {
                    MessageManager.printlnW(MessageManager.PROPERTY_WARNING_SEMANTIC_VARIABLE_INIT, v)
                    AnalyzeErrorCounter.w()
                }

            }
            for(v in userFunctionTable.toArray())
            {
                if(!v.referenced)
                {
                    MessageManager.printlnW(MessageManager.PROPERTY_WARNING_SEMANTIC_UNUSE_FUNCTION, v)
                    AnalyzeErrorCounter.w()
                }
            }
        }
        if(CommandlineOptions.options.strict)
        {
            for(s in variableTable.toArray())
            {
                if(Regex(AnalyzerConstants.REGEX_NUMERIC_PREFIX).find(s.name) != null)
                {
                    MessageManager.printlnW(MessageManager.PROPERTY_WARNING_SEMANTIC_INFO_VARNAME, s)
                    AnalyzeErrorCounter.w()
                }
            }
        }

    }

    //--------------------------------------------------------------------------
    // ユーティリティ
    //--------------------------------------------------------------------------

    /**
     * 上位ノードに返す評価式のテンプレを生成する
     */
    fun createEvalNode(src : SimpleNode, nodeId : Int) : SimpleNode
    {
        val ret = SimpleNode(nodeId)
        SymbolDefinition.copy(src.symbol, ret.symbol)
        return ret
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

        // 初期化式なし
        if(node.jjtGetNumChildren() == 0)
        {
            val v = variableTable.search(node.symbol)
            val uiType = uiTypeTable.search(v!!.uiTypeName)

            // const 修飾子がついているかどうかの検証
            if(v.isConstant)
            {
                // 変数宣言時に初期化式が必須
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_REQUIRED_INITIALIZER, v)
                AnalyzeErrorCounter.e()
            }
            else if(v.isUIVariable)
            {
                if(uiType == null)
                {
                    // KSP（data/symbol/uitypes.txt）で未定義のUIタイプ
                    // シンボル収集フェーズで警告出力済みなので何もしない
                    v.state = AnalyzerConstants.SymbolState.INITIALIZED
                }
                else if(uiType.initializerRequired)
                {
                    // 変数宣言時に初期化式が必須
                    MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_REQUIRED_INITIALIZER, v)
                    AnalyzeErrorCounter.e()
                }
            }// UI型変数の初期値代入必須の検証

            return node
        }
        node.childrenAccept(this, node)
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
        // 宣言のみ
        if(node.jjtGetNumChildren() == 0)
        {
            val parent = node.jjtGetParent() as SimpleNode
            // 定数宣言している場合は初期値代入が必須
            if(parent.symbol.isConstant)
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_REQUIRED_INITIALIZER, parent.symbol)
                AnalyzeErrorCounter.e()
            }
            return node
        }

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
        val v = variableTable.search((node.jjtGetParent().jjtGetParent() as SimpleNode).symbol)

        if(node.hasAssign)
        {
            if(v!!.isUIVariable)
            {
                /*
                    プリミティブ型変数なのに ui_#### 修飾子がある状態、かつ := がある状態
                    文保解析フェーズでは解決不可能な言語仕様のためここで判定を行っている

                    e.g.:
                    declare ui_label %a := ( 0, 0 )
                */
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SYNTAX, v)
                AnalyzeErrorCounter.e()
                return node
            }
        }
        else
        {
            if(!v!!.isUIVariable)
            {
                /*
                    ui_#### 修飾子が無い、且つ := が無く、UI初期化子である状態
                    文法解析フェーズでは解決不可能な言語仕様のためここで判定を行っている

                    e.g.:
                    declare  %a( 0, 0 )
                */
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SYNTAX, v)
                AnalyzeErrorCounter.e()
                return node
            }
            uiInitializerImpl(node, v, data)
            return node
        }

        if(node.jjtGetNumChildren() == 0)
        {
            if(v.isConstant)
            {
                // 定数宣言している場合は初期値代入が必須
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_REQUIRED_INITIALIZER, v)
                AnalyzeErrorCounter.e()
                return node
            }
            // 初期値代入なし
            return node
        }

        val expr = node.jjtGetChild(0).jjtAccept(this, data) as SimpleNode
        val eval = expr.symbol as SymbolDefinition

        // const 宣言時にコマンドの戻り値は代入できない（戻り値不定のため、動作保証が出来ない可能性が発生する）
        if(v.isConstant)
        {
            if(node.hasNode(null, SimpleNode.HasNodeIdCondition, KSPParserTreeConstants.JJTCALLCOMMAND))
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_NOCONSTANT_INITIALIZER, v)
                AnalyzeErrorCounter.e()
                return node
            }
        }

        // 型の不一致
        if(v.type and eval.type == 0)
        {
            MessageManager.printlnE(
                MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_INITIALIZER_TYPE,
                v,
                SymbolDefinition.getTypeName(eval.type),
                SymbolDefinition.getTypeName(v.type)
            )
            AnalyzeErrorCounter.e()
            return node
        }

        // 文字列型は初期値代入不可
        if(v.isString)
        {
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_STRING_INITIALIZER, v)
            AnalyzeErrorCounter.e()
            return node
        }

        // 初期値代入。畳み込みで有効な値が格納される
        if(expr.symbol.symbolType !== SymbolType.Command)
        {
            var value : Any? = null
            if(v.isInt)
            {
                value = EvaluationUtility.evalConstantIntValue(expr, 0, variableTable)
            }
            else if(v.isReal)
            {
                value = EvaluationUtility.evalConstantRealValue(expr, 0.0, variableTable)
            }
            else if(v.isString)
            {
                // 文字列は初期値代入不可
                value = null
            }
            v.value = value
        }
        v.state = AnalyzerConstants.SymbolState.INITIALIZED
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
        val v = variableTable.search((node.jjtGetParent().jjtGetParent() as SimpleNode).symbol)
        if(node.hasAssign)
        {
            if(v!!.isUIVariable)
            {
                /*
                    配列型変数なのに ui_#### 修飾子がある状態、かつ := で初期値代入をしている
                    文保解析フェーズでは解決不可能な言語仕様のためここで判定を行っている

                    e.g.:
                    declare ui_table %t[ 3 ] := ( 0, 0, 0 )
                */
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SYNTAX, v)
                AnalyzeErrorCounter.e()
                return node
            }
            arrayInitializerImpl(node, data, false)
        }
        else
        {
            if(v!!.isUIVariable)
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
    protected fun arrayInitializerImpl(node : SimpleNode, data : Any, forceSkipInitializer : Boolean) : Boolean
    {
/*
    VariableDeclaration
            -> ASTVariableInitializer
                -> [
                        ArrayInitializer        //NOW
                            -> ArrayIndex
                            -> Expression
                            -> (,Expression)*
                    | PrimitiveInititalizer
                ]
*/

        val v = variableTable.search((node.jjtGetParent().jjtGetParent() as SimpleNode).symbol)
        var result = true

        //--------------------------------------------------------------------------
        // 型チェック
        //--------------------------------------------------------------------------
        if(!v!!.isArray)
        {
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_NOTARRAY, v)
            AnalyzeErrorCounter.e()
            return false
        }

        //--------------------------------------------------------------------------
        // const 指定のチェック
        //--------------------------------------------------------------------------
        if(v.isConstant)
        {
            // 配列は const 修飾子を付与できない
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_DECLARE_CONST, v)
            AnalyzeErrorCounter.e()
            return false
        }

        //--------------------------------------------------------------------------
        // 要素数宣言
        //--------------------------------------------------------------------------
        if(node.jjtGetNumChildren() == 0 || node.jjtGetChild(0).id != KSPParserTreeConstants.JJTARRAYINDEX)
        {
            // 配列要素数の式がない
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_NOT_ARRAYSIZE, v)
            AnalyzeErrorCounter.e()
            return false
        }

        val arraySizeNode = node.jjtGetChild(0) as SimpleNode
        var size : Int?

        for(i in 0 until arraySizeNode.jjtGetNumChildren())
        {
            val n = arraySizeNode.jjtGetChild(i) as SimpleNode

            if(!SymbolDefinition.isInt(n.symbol.type))
            {
                // 要素数の型が整数以外
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_ARRAYSIZE, v)
                AnalyzeErrorCounter.e()
                return false
            }

            size = EvaluationUtility.evalConstantIntValue(n, 0, variableTable)

            if(size == null || size <= 0)
            {
                // 要素数が不明、または 0 以下
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_ARRAYSIZE, v)
                AnalyzeErrorCounter.e()
                return false
            }

            v.arraySize = v.arraySize + size

        }

        if(v.arraySize > KSPLanguageLimitations.maxKspArraySize)
        {
            // 要素数が上限を超えた
            MessageManager.printlnE(
                MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_MAXARRAYSIZE,
                v,
                KSPLanguageLimitations.maxKspArraySize.toString()
            )
            AnalyzeErrorCounter.e()
            return false
        }

        //--------------------------------------------------------------------------
        // 初期値代入
        //--------------------------------------------------------------------------
        if(forceSkipInitializer || node.jjtGetNumChildren() == 1)
        {
            // 初期値代入なし
            v.state = AnalyzerConstants.SymbolState.UNLOADED
            return true
        }

        if(v.isString)
        {
            // 文字列配列型に初期値代入は出来ない
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_STRING_INITIALIZER, v)
            AnalyzeErrorCounter.e()
            return false
        }

        for(i in 1 until node.jjtGetNumChildren())
        {
            val expr = node.jjtGetChild(i).jjtAccept(this, data) as SimpleNode
            val eval = expr.symbol as SymbolDefinition

            if(v.type and eval.type == 0)
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_ARRAYINITILIZER, v, (i - 1).toString()) // -1: zero origin
                AnalyzeErrorCounter.e()
                result = false
            }
        }
        v.state = AnalyzerConstants.SymbolState.INITIALIZED
        return result
    }

    /**
     * UI型宣言の実装
     */
    protected fun uiInitializerImpl(initializer : SimpleNode, v : Variable, jjtVisitorData : Any)
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
        val uiType = uiTypeTable.search(v.uiTypeName)

        if(uiType == null)
        {
            // KSP（data/symbol/uitypes.txt）で未定義のUIタイプ
            // シンボル収集フェーズで警告出力済みなので何もしない
            v.state = AnalyzerConstants.SymbolState.INITIALIZED
            return
        }

        //--------------------------------------------------------------------------
        // ui_#### が求める型と変数の型のチェック
        //--------------------------------------------------------------------------
        if(v.type != uiType.uiValueType)
        {
            MessageManager.printlnE(
                MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_UITYPE,
                v,
                SymbolDefinition.getTypeName(uiType.uiValueType),
                uiType.name
            )
            AnalyzeErrorCounter.e()
            return
        }

        //--------------------------------------------------------------------------
        // const 指定のチェック
        //--------------------------------------------------------------------------
        if(v.isConstant)
        {
            // ui_#### const 修飾子を付与できない
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_DECLARE_CONST, v)
            AnalyzeErrorCounter.e()
            return
        }

        //--------------------------------------------------------------------------
        // ui_#### が配列型の場合、要素数宣言のチェック
        //--------------------------------------------------------------------------
        if(SymbolDefinition.isArray(uiType.uiValueType))
        {
            if(!arrayInitializerImpl(initializer, jjtVisitorData, true))
            {
                return
            }
        }

        //--------------------------------------------------------------------------
        // 初期値代入式チェック
        //--------------------------------------------------------------------------
        if(!uiType.initializerRequired)
        {
            // 初期化不要
            v.state = AnalyzerConstants.SymbolState.INITIALIZED
            return
        }
        if(initializer.jjtGetNumChildren() < 1)
        {
            // 配列型なら子は2以上 (ArrayIndex, Expression, ...)
            // そうでない場合なら子1は以上(Expression, ...)
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_REQUIRED_INITIALIZER, v)
            AnalyzeErrorCounter.e()
            return
        }

        // for のカウンタ初期値の設定
        var i : Int
        if(initializer.jjtGetChild(0).id == KSPParserTreeConstants.JJTARRAYINDEX)
        {
            // 変数配列型の場合
            // node[ 0 ]        : ArrayIndex
            // node[ 1 ... n ]  : Expression
            i = 1

        }
        else
        {
            // node[ 0 ... n ]  : Expression
            i = 0
        }

        // UI初期化式の引数チェック
        if(initializer.jjtGetNumChildren() - i != uiType.initilzerTypeList.size)
        {
            // 引数の数が一致していない
            val cnt = (initializer.jjtGetNumChildren() - i).toString()
            val req = uiType.initilzerTypeList.size.toString()
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_UIINITIALIZER_COUNT, v, uiType.name, cnt, req)
            AnalyzeErrorCounter.e()
            return
        }

        // i は上記で初期化済み
        while(i < initializer.jjtGetNumChildren())
        {
            var found = false
            val n = initializer.jjtGetChild(i).jjtAccept(this, jjtVisitorData) as SimpleNode
            val param = n.symbol
            val nid = n.id
            var argT = 0

            // 条件式BOOLはエラー対象
            if(nid == KSPParserTreeConstants.JJTCONDITIONALOR || nid == KSPParserTreeConstants.JJTCONDITIONALAND)
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_EXPRESSION_INVALID, n.symbol)
                AnalyzeErrorCounter.e()
                i++
                continue
            }

            // 四則演算等は文法解析時でクリアしているので値だけに絞る
            if(nid != KSPParserTreeConstants.JJTLITERAL && nid != KSPParserTreeConstants.JJTREFVARIABLE)
            {
                i++
                continue
            }

            SEARCH@ for(t in uiType.initilzerTypeList)
            {
                argT = t
                when(nid)
                {
                    //--------------------------------------------------------------------------
                    // リテラル
                    //--------------------------------------------------------------------------
                    KSPParserTreeConstants.JJTLITERAL ->
                    {
                        if(param.type == t)
                        {
                            found = true
                            break@SEARCH
                        }
                    }
                    //--------------------------------------------------------------------------
                    // const 指定ありの変数
                    //--------------------------------------------------------------------------
                    KSPParserTreeConstants.JJTREFVARIABLE ->
                    {
                        val `var` = variableTable.search(n.symbol)
                        if(`var` == null)
                        {
                            // コマンドの戻り値の可能性
                            val cmd = commandTable.search(n.symbol)
                            if(cmd == null)
                            {
                                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_NOT_DECLARED, n.symbol)
                                AnalyzeErrorCounter.e()
                                continue@SEARCH
                            }
                            if(cmd.returnType.contains(t))
                            {
                                found = true
                                break@SEARCH
                            }
                            else
                            {
                                // 戻り値と要求される型の不一致
                                break@SEARCH
                            }
                        }
                        if(`var`.type == t)
                        {
                            found = true
                            `var`.referenced = true
                            break@SEARCH
                        }
                    }
                    //--------------------------------------------------------------------------
                    // 上記以外の式は無効
                    //--------------------------------------------------------------------------
                    else ->
                    {
                        MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_EXPRESSION_INVALID, n.symbol)
                        AnalyzeErrorCounter.e()
                    }
                }

            } //~for( int t : uiType.initilzerTypeList )

            if(!found)
            {
                // イニシャライザ: 型の不一致
                MessageManager.printlnE(
                    MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_UIINITIALIZER_TYPE,
                    v,
                    i.toString(),
                    SymbolDefinition.getTypeName(argT)
                )
                AnalyzeErrorCounter.e()
                break
            }
            i++
        }
        // コールバックから参照されるので意図的に参照フラグON
        v.state = AnalyzerConstants.SymbolState.LOADED
        v.referenced = true
    }

    //--------------------------------------------------------------------------
    // 式
    //--------------------------------------------------------------------------

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

        val exprL = node.jjtGetChild(0).jjtAccept(this, data) as SimpleNode
        val exprR = node.jjtGetChild(1).jjtAccept(this, data) as SimpleNode
        val symL = exprL.symbol
        val symR = exprR.symbol
        val variable : Variable?
        var exprLType : Int
        var exprRType : Int

        variable = variableTable.search(symL)
        if(variable == null)
        {
            // exprL 評価内で変数が見つけられなかった
            return exprL
        }
        variable.referenced = true
        exprLType = variable.type
        exprRType = exprR.symbol.type

        if(variable.isConstant)
        {
            SymbolDefinition.copy(exprR.symbol, tempSymbol)
            tempSymbol.name = variable.name
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_ASSIGN_CONSTVARIABLE, tempSymbol)
            AnalyzeErrorCounter.e()
            return exprL
        }
        // コマンドコールの戻り値が複数の型を持つなどで暗黙の型変換を要する場合
        // 代入先の変数に合わせる。暗黙の型変換が不可能な場合は、代替としてVOIDを入れる
        if(SymbolDefinition.hasMultipleType(exprRType))
        {
            if(exprLType and exprRType == 0)
            {
                exprRType = AnalyzerConstants.TYPE_VOID
            }
            else
            {
                // 暗黙の型変換
                exprRType = exprLType
            }
        }
        // 配列要素への格納もあるので配列ビットをマスクさせている
        if(exprLType and AnalyzerConstants.TYPE_MASK != exprRType and AnalyzerConstants.TYPE_MASK)
        {
            // 代入先が文字列型なら暗黙の型変換が可能
            // 文字列型以外なら型の不一致 or 条件式は代入不可
            if(!symL.isString || symR.isBoolean)
            {
                val vType = SymbolDefinition.getTypeName(SymbolDefinition.getPrimitiveType(exprLType))
                val aType = SymbolDefinition.getTypeName(exprRType)
                SymbolDefinition.copy(symR, tempSymbol)
                tempSymbol.name = variable.name

                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_ASSIGN_TYPE_NOTCOMPATIBLE, tempSymbol, vType, aType)
                AnalyzeErrorCounter.e()
                return exprL
            }
        }
        variable.state = AnalyzerConstants.SymbolState.LOADED
        return exprL
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
        // 上位ノードの型評価式用
        val ret = createEvalNode(node, KSPParserTreeConstants.JJTREFVARIABLE)

        //--------------------------------------------------------------------------
        // 宣言済みかどうか
        //--------------------------------------------------------------------------
        val v = variableTable.search(node.symbol)
        if(v == null)
        {
            // 宣言されていない変数
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_NOT_DECLARED, node.symbol)
            AnalyzeErrorCounter.e()
            return ret
        }
        // 変数へのアクセスが確定したので、AST、戻り値に変数のシンボル情報をコピー
        SymbolDefinition.copy(v, node.symbol, false)
        SymbolDefinition.copy(v, ret.symbol, false)

        if(node.jjtGetParent() != null && node.jjtGetParent().id == KSPParserTreeConstants.JJTASSIGNMENT)
        {
            // これから代入される予定の変数
            v.state = AnalyzerConstants.SymbolState.LOADING
        }

        // 配列型なら親ノードに応じて添字の有無検証
        if(node.isNecessaryValidArraySubscribe && !EvaluationUtility.validArraySubscript(node, false))
        {
            // 添字が必須なのに添字がない
            // 変数ノードの場合、宣言部が行番号にあたるので、出現箇所の出現行番号を指定する
            val sym = SymbolDefinition(v)
            sym.position.copy(node.symbol.position)

            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_ARRAYSUBSCRIPT, sym)
            AnalyzeErrorCounter.e()
            return ret
        }

        if(v.isArray)
        {
            // 上位ノードの型評価式用
            if(node.jjtGetNumChildren() > 0)
            {
                // 添え字がある
                // 要素へのアクセスであるため、配列ビットフラグを外したプリミティブ型として扱う
                ret.symbol.type = v.primitiveType
                node.jjtGetChild(0).jjtAccept(this, node)
            }
            else
            {
                // 添え字が無い
                // 配列変数をコマンドの引数に渡すケース
                // 配列型としてそのまま扱う
                ret.symbol.type = v.type
            }
            ret.symbol.reserved = v.reserved
            v.referenced = true
            v.referenceCount = v.referenceCount + 1
            v.state = AnalyzerConstants.SymbolState.LOADED
            return ret
        }
        else if(node.jjtGetNumChildren() > 0)
        {
            SymbolDefinition.copy(node.symbol, tempSymbol)
            tempSymbol.name = v.name

            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_NOTARRAY, tempSymbol)
            AnalyzeErrorCounter.e()
        }// 配列型じゃないのに添え字がある

        // 上位ノードの型評価式用
        ret.symbol.type = v.primitiveType
        ret.symbol.reserved = v.reserved
        v.referenced = true
        v.state = AnalyzerConstants.SymbolState.LOADED
        return ret
    }

    /**
     * 配列の添え字([])
     * @param data 親ノード
     */
    override fun visit(node : ASTArrayIndex, data : Any) : Any
    {
/*
            parent:<RefVariable>
                    +
                    |
                    +
              [ 0:<expr> ]
*/

        val parent = node.jjtGetParent() as SimpleNode
        val expr = node.jjtGetChild(0).jjtAccept(this, data) as SimpleNode
        val sym = expr.symbol

        // 上位ノードの型評価式用
        val ret = createEvalNode(parent, KSPParserTreeConstants.JJTREFVARIABLE)

        // 添え字の型はintのみ
        if(!SymbolDefinition.isInt(sym.type))
        {
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_ARRAY_ELEMENT_INTONLY, expr.symbol)
            AnalyzeErrorCounter.e()
        }
        // インデックスが定数値で配列サイズ外アクセス（Out of Bounds）
        if(sym.isConstant && !EvaluationUtility.isInVariableDeclaration(node))
        {
            val v = variableTable.search(parent.symbol)
            if(v != null && sym.value != null)
            {
                val index = Integer.parseInt(sym.value!!.toString())
                val size = v.arraySize
                if(!v.reserved && (index < 0 || index >= size) || // ユーザー変数は配列サイズが分かっている
                    v.reserved && index < 0
                )
                // ビルトイン変数は配列サイズが不定なのでネガティブチェックのみ
                {
                    // 行番号を合わせるため、行番号をマージした一時シンボルを生成
                    val s = SymbolDefinition(v)
                    s.position.copy(parent.symbol.position)

                    MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_ARRAYOUTOFBOUNDS, s, size.toString(), index.toString())
                    AnalyzeErrorCounter.e()
                }
            }
        }

        return ret
    }

    //--------------------------------------------------------------------------
    // コマンドコール
    //--------------------------------------------------------------------------

    /**
     * コマンド呼び出し
     */
    override fun visit(node : ASTCallCommand, data : Any) : Any
    {
        val cmd = commandTable.search(node.symbol)
        var undocumentedCmd : Boolean

        // 上位ノードの型評価式用
        val ret = createEvalNode(node, KSPParserTreeConstants.JJTREFVARIABLE)
        ret.symbol.symbolType = SymbolType.Command

        if(cmd == null)
        {
            // シンボルテーブルに定義なし
            // ドキュメントに記載のない隠しコマンドの可能性
            // エラーにせず、警告に留める
            // 戻り値不定のため、全てを許可する
            ret.symbol.type = AnalyzerConstants.TYPE_MULTIPLE
            MessageManager.printlnW(MessageManager.PROPERTY_WARNING_SEMANTIC_COMMAND_UNKNOWN, node.symbol)
            AnalyzeErrorCounter.w()
            return ret
        }

        // 未知のコマンド（戻り値Xタイプ）はチェックをスキップ
        undocumentedCmd = cmd.unknownCommand()
        if(undocumentedCmd)
        {
            // シンボルテーブルに定義はあるけど
            // ドキュメントに記載のない、引数・戻り値不明の隠しコマンド
            // エラーにせず、警告に留める
            // 戻り値不定のため、全てを許可する
            ret.symbol.type = AnalyzerConstants.TYPE_MULTIPLE
            MessageManager.printlnW(MessageManager.PROPERTY_WARN_COMMAND_UNKNOWN, node.symbol)
            AnalyzeErrorCounter.w()
            return ret
        }

        val callback = EvaluationUtility.getCurrentCallBack(node)

        for(t in cmd.returnType.typeList)
        {
            ret.symbol.type = ret.symbol.type or t
        }

        //--------------------------------------------------------------------------
        // 実行が許可されているコールバック内での呼び出しかどうか
        // ユーザー定義関数内からのコールはチェックしない
        //--------------------------------------------------------------------------
        if(callback != null)
        {
            if(!cmd.availableCallbackList.containsKey(callback.symbol.name))
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_COMMAND_NOT_ALLOWED, node.symbol)
                AnalyzeErrorCounter.e()
                return ret
            }
        }

        //--------------------------------------------------------------------------
        // 引数の数チェック
        //--------------------------------------------------------------------------
        if(node.jjtGetNumChildren() == 0 && cmd.argList.size > 0)
        {
            var valid = false

            // void(引数なしの括弧だけ)ならエラーの対象外
            if(valid == false && cmd.argList.size == 1)
            {
                SEARCH@ for(a1 in cmd.argList)
                {
                    for(a2 in a1.arguments)
                    {
                        if(a2.type != AnalyzerConstants.TYPE_MULTIPLE && a2.type and AnalyzerConstants.TYPE_VOID != 0)
                        {
                            valid = true
                            break@SEARCH
                        }
                    }
                }
            }
            if(!valid)
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_COMMAND_ARGCOUNT, node.symbol)
                AnalyzeErrorCounter.e()
            }
            return ret
        }
        else if(node.jjtGetNumChildren() > 0)
        {
            if(node.jjtGetChild(0).jjtGetNumChildren() != cmd.argList.size)
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_COMMAND_ARGCOUNT, node.symbol)
                AnalyzeErrorCounter.e()
                return ret
            }
        }

        // 引数の解析
        node.childrenAccept(this, cmd)
        return ret
    }

    /**
     * コマンド引数
     * @param data 呼び出し元 Command インスタンス
     * @return null
     */
    override fun visit(node : ASTCommandArgumentList, data : Any) : Any?
    {
        val childrenNum = node.jjtGetNumChildren()

        var cmd : Command?

        if(data !is Command)
        {
            // ここに到達する時点で意味解析自体のバグ
            return null
        }

        cmd = data
        val argList = cmd.argList

        //--------------------------------------------------------------------------
        // 引数の型チェック
        //--------------------------------------------------------------------------
        run {
            for(i in 0 until childrenNum)
            {
                val evalValue = node.jjtGetChild(i).jjtAccept(this, null) ?: return false    // 子ノードの評価式結果
                var valid = false
                val evalNode = evalValue as SimpleNode
                val symbol = evalNode.symbol
                val type = symbol.type

                // 評価式が変数だった場合のための変数情報への参照
                val userVar = variableTable.search(symbol)

                // 引数毎に複数のデータ型が許容される仕様のため照合
                for(arg in argList[i].arguments)
                {
                    //--------------------------------------------------------------------------
                    // 型指定なし（全ての型を許容する）
                    //--------------------------------------------------------------------------
                    if(arg.type == AnalyzerConstants.TYPE_ALL)
                    {
                        valid = true
                        break
                    }
                    else if(userVar != null && arg.accessFlag and AnalyzerConstants.ACCESS_ATTR_UI != 0)
                    {
                        if(userVar.uiTypeInfo == null)
                        {
                            // ui_##### 修飾子が無い変数
                            break
                        }
                        if(arg.uiTypeName == "ui_*" || arg.uiTypeName == userVar.uiTypeInfo!!.name)
                        {
                            // 要求されている ui_#### と変数宣言時の ui_#### 修飾子が一致
                            valid = true
                            break
                        }
                    }
                    else if(userVar != null)
                    {
                        if(arg.type == symbol.type)
                        {
                            valid = true
                            break
                        }
                    }
                    else if(evalNode.id == KSPParserTreeConstants.JJTCALLCOMMAND)
                    {
                        val callCmd = evalNode as ASTCallCommand
                        val retCommand = commandTable.search(callCmd.symbol)

                        // [nullチェック]
                        // 隠しコマンドやドキュメント化されていない場合に逆引きできない可能性があるため
                        if(retCommand != null)
                        {
                            for(t in retCommand.returnType.typeList)
                            {
                                // 呼び出したコマンドの戻り値と
                                // このコマンドの求められている引数の型チェック
                                if(t and arg.type != 0)
                                {
                                    valid = true
                                    break
                                }
                            }
                        }
                        else
                        {
                            // 未知のコマンドなので正しいかどうかの判定が不可能
                            // エラーにせずに警告に留める
                            MessageManager.printlnW(MessageManager.PROPERTY_WARNING_SEMANTIC_COMMAND_UNKNOWN, callCmd.symbol)
                            AnalyzeErrorCounter.w()
                            // 戻り値の型チェックが不可能なのでデータ型は一致したものとみなす
                            valid = true
                            break
                        }
                    }
                    else
                    {
                        if(arg.type and type != 0 && arg.type and AnalyzerConstants.TYPE_ATTR_MASK == type and AnalyzerConstants.TYPE_ATTR_MASK)
                        {
                            valid = true
                            break
                        }
                    }//--------------------------------------------------------------------------
                    // リテラル
                    //--------------------------------------------------------------------------
                    //--------------------------------------------------------------------------
                    // コマンドの戻り値
                    //--------------------------------------------------------------------------
                    //--------------------------------------------------------------------------
                    // ui_#### 修飾子が無い変数
                    //--------------------------------------------------------------------------
                    //--------------------------------------------------------------------------
                    // コマンドがUI属性付き変数を要求
                    //--------------------------------------------------------------------------
                } // for( Argument arg : a.arguments )

                if(!valid)
                {
                    MessageManager.printlnE(
                        MessageManager.PROPERTY_ERROR_SEMANTIC_INCOMPATIBLE_ARG,
                        node.symbol
                    )
                    AnalyzeErrorCounter.e()
                }

            } //~for( int i = 0; i < childrenNum; i++ )
        }
        return null
    }

    //--------------------------------------------------------------------------
    // ユーザー定義関数呼び出し
    //--------------------------------------------------------------------------

    /**
     * ユーザー定義関数宣言
     */
    override fun visit(node : ASTUserFunctionDeclaration, data : Any) : Any
    {
        node.childrenAccept(this, data)
        return node
    }

    /**
     * ユーザー定義関数呼び出し
     */
    override fun visit(node : ASTCallUserFunctionStatement, data : Any) : Any
    {
        val f = userFunctionTable.search(node.symbol)
        if(f == null)
        {
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_USERFUNCTION_NOT_DECLARED, node.symbol)
            AnalyzeErrorCounter.e()
            return node
        }
        f.referenced = true
        f.referenceCount = f.referenceCount + 1
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
            -> <expr>
            -> <block>
            :
        | else
            -> <block>
*/
        val cond = node.jjtGetChild(0).jjtAccept(this, data) as SimpleNode
        //--------------------------------------------------------------------------
        // 条件式がBOOL型でない場合
        //--------------------------------------------------------------------------
        run {
            if(!SymbolDefinition.isBoolean(cond.symbol.type))
            {
                MessageManager.printlnE(
                    MessageManager.PROPERTY_ERROR_SEMANTIC_CONDITION_INVALID,
                    cond.symbol,
                    SymbolDefinition.getTypeName(AnalyzerConstants.TYPE_BOOL)
                )
                AnalyzeErrorCounter.e()
            }
        }
        // <block>
        node.childrenAccept(this, data)
        return cond
    }

    /**
     * select~case の case内の評価
     */
    override fun visit(node : ASTSelectStatement, data : Any) : Any
    {
/*

        select
            -> <expr>
            -> <case>
                -> <casecond>
                -> [ to [<expr>] ]
                -> <block>
            -> <case>
                -> <casecond>
                -> [ to [<expr>] ]
                -> <block>
            :
            :
*/

        //--------------------------------------------------------------------------
        // 条件式が整数型でない場合
        //--------------------------------------------------------------------------
        run {
            val cond = node.jjtGetChild(0).jjtAccept(this, data) as SimpleNode
            if(!SymbolDefinition.isInt(cond.symbol.type) || SymbolDefinition.isArray(cond.symbol.type))
            {
                MessageManager.printlnE(
                    MessageManager.PROPERTY_ERROR_SEMANTIC_CONDITION_INVALID,
                    cond.symbol,
                    SymbolDefinition.getTypeName(AnalyzerConstants.TYPE_INT)
                )
                AnalyzeErrorCounter.e()
                return node
            }
        }

        //--------------------------------------------------------------------------
        // case: 整数の定数または定数宣言した変数が有効
        //--------------------------------------------------------------------------
        for(i in 1 until node.jjtGetNumChildren())
        {
            val caseNode = node.jjtGetChild(i) as SimpleNode
            val caseCond1 = caseNode.jjtGetChild(0).jjtAccept(this, data) as SimpleNode
            var caseCond2 : SimpleNode?
            val caseValue1 = EvaluationUtility.evalConstantIntValue(caseCond1, 0, variableTable)
            var caseValue2 : Int? = null
            val blockNode = caseNode.jjtGetChild(caseNode.jjtGetNumChildren() - 1) as SimpleNode

            // 定数値ではない
            if(caseValue1 == null)
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_CASEVALUE_CONSTONLY, caseCond1.symbol)
                AnalyzeErrorCounter.e()

                blockNode.childrenAccept(this, data)
                return node
            }
            // to <expr>
            if(caseNode.jjtGetNumChildren() >= 2)
            {
                val n = caseNode.jjtGetChild(1) as SimpleNode
                if(n.id == KSPParserTreeConstants.JJTCASECONDITION)
                // to ではない場合は Block ノード
                {
                    caseCond2 = caseNode.jjtGetChild(1).jjtAccept(this, data) as SimpleNode
                    caseValue2 = EvaluationUtility.evalConstantIntValue(caseCond2, 0, variableTable)
                    if(caseValue2 == null)
                    {
                        // 定数値ではない
                        MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_CASEVALUE_CONSTONLY, caseCond1.symbol)
                        AnalyzeErrorCounter.e()
                        blockNode.childrenAccept(this, data)
                        return node
                    }
                }
            }
            if(caseValue2 != null)
            {
                // A to B のチェック
                // 例
                // case 1000 to 1000 { range の from to が同じ}
                if(caseValue1.toInt() == caseValue2.toInt())
                {
                    MessageManager.printlnW(
                        MessageManager.PROPERTY_WARNING_SEMANTIC_CASEVALUE,
                        caseCond1.symbol,
                        caseValue1.toString(),
                        caseValue2.toString()
                    )
                    AnalyzeErrorCounter.w()
                }
            }
            blockNode.childrenAccept(this, data)
        }
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
        return node.jjtGetChild(0) as SimpleNode
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

        val cond = node.jjtGetChild(0).jjtAccept(this, data) as SimpleNode
        //--------------------------------------------------------------------------
        // 条件式がBOOL型でない場合
        //--------------------------------------------------------------------------
        run {
            if(!SymbolDefinition.isBoolean(cond.symbol.type))
            {
                MessageManager.printlnE(
                    MessageManager.PROPERTY_ERROR_SEMANTIC_CONDITION_INVALID,
                    cond.symbol,
                    SymbolDefinition.getTypeName(AnalyzerConstants.TYPE_BOOL)
                )
                AnalyzeErrorCounter.e()
            }
        }
        // <block>
        node.childrenAccept(this, data)
        return cond
    }
}
