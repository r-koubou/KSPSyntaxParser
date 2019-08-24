/* =========================================================================

    BasicEvaluationAnalyzerTemplate.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.analyzer.SymbolDefinition.SymbolType
import net.rkoubou.kspcompiler.javacc.generated.ASTAdd
import net.rkoubou.kspcompiler.javacc.generated.ASTBitwiseAnd
import net.rkoubou.kspcompiler.javacc.generated.ASTBitwiseOr
import net.rkoubou.kspcompiler.javacc.generated.ASTCallUserFunctionStatement
import net.rkoubou.kspcompiler.javacc.generated.ASTConditionalAnd
import net.rkoubou.kspcompiler.javacc.generated.ASTConditionalOr
import net.rkoubou.kspcompiler.javacc.generated.ASTDiv
import net.rkoubou.kspcompiler.javacc.generated.ASTEqual
import net.rkoubou.kspcompiler.javacc.generated.ASTGE
import net.rkoubou.kspcompiler.javacc.generated.ASTGT
import net.rkoubou.kspcompiler.javacc.generated.ASTLE
import net.rkoubou.kspcompiler.javacc.generated.ASTLT
import net.rkoubou.kspcompiler.javacc.generated.ASTLiteral
import net.rkoubou.kspcompiler.javacc.generated.ASTLogicalNot
import net.rkoubou.kspcompiler.javacc.generated.ASTMod
import net.rkoubou.kspcompiler.javacc.generated.ASTMul
import net.rkoubou.kspcompiler.javacc.generated.ASTNeg
import net.rkoubou.kspcompiler.javacc.generated.ASTNot
import net.rkoubou.kspcompiler.javacc.generated.ASTNotEqual
import net.rkoubou.kspcompiler.javacc.generated.ASTPreProcessorDefine
import net.rkoubou.kspcompiler.javacc.generated.ASTPreProcessorIfDefined
import net.rkoubou.kspcompiler.javacc.generated.ASTPreProcessorIfUnDefined
import net.rkoubou.kspcompiler.javacc.generated.ASTPreProcessorUnDefine
import net.rkoubou.kspcompiler.javacc.generated.ASTRootNode
import net.rkoubou.kspcompiler.javacc.generated.ASTStrAdd
import net.rkoubou.kspcompiler.javacc.generated.ASTSub
import net.rkoubou.kspcompiler.javacc.generated.ASTUserFunctionDeclaration
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants

/**
 * 基本的な四則演算の評価処理を実装したテンプレートクラス
 */
abstract class BasicEvaluationAnalyzerTemplate
/**
 * ctor
 */
    (rootNode : ASTRootNode, symbolCollector : SymbolCollector) : AbstractAnalyzer(rootNode)
{
    // シンボルテーブル保持インスタンス
    val uiTypeTable : UITypeTable
    val variableTable : VariableTable
    val reservedCallbackTable : CallbackTable
    val userCallbackTable : CallbackTable
    val commandTable : CommandTable
    val userFunctionTable : UserFunctionTable
    val preProcessorSymbolTable : PreProcessorSymbolTable

    init
    {
        this.uiTypeTable = symbolCollector.uiTypeTable
        this.variableTable = symbolCollector.variableTable
        this.reservedCallbackTable = symbolCollector.reservedCallbackTable
        this.userCallbackTable = symbolCollector.usercallbackTable
        this.commandTable = symbolCollector.commandTable
        this.userFunctionTable = symbolCollector.userFunctionTable
        this.preProcessorSymbolTable = symbolCollector.preProcessorSymbolTable
    }

    /**
     * 条件式 OR
     */
    override fun visit(node : ASTConditionalOr, data : Any) : Any
    {
/*
                 or
                 +
                 |
            +----+----+
            |         |
        0: <expr>   1:<expr>
*/
        return EvaluationUtility.evalBinaryBooleanOperator(node, this, data, variableTable)
    }

    /**
     * 条件式 AND
     */
    override fun visit(node : ASTConditionalAnd, data : Any) : Any
    {
/*
                and
                 +
                 |
            +----+----+
            |         |
        0: <expr>   1:<expr>
*/
        return EvaluationUtility.evalBinaryBooleanOperator(node, this, data, variableTable)
    }

    /**
     * 論理積
     */
    override fun visit(node : ASTBitwiseOr, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryNumberOperator(node, this, data, variableTable)
    }

    /**
     * 論理和
     */
    override fun visit(node : ASTBitwiseAnd, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryNumberOperator(node, this, data, variableTable)
    }

    /**
     * 比較 (=)
     */
    override fun visit(node : ASTEqual, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryBooleanOperator(node, this, data, variableTable)
    }

    /**
     * 比較 (#)
     */
    override fun visit(node : ASTNotEqual, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryBooleanOperator(node, this, data, variableTable)
    }

    /**
     * 不等号(<)
     */
    override fun visit(node : ASTLT, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryBooleanOperator(node, this, data, variableTable)
    }

    /**
     * 不等号(>)
     */
    override fun visit(node : ASTGT, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryBooleanOperator(node, this, data, variableTable)
    }

    /**
     * 不等号(<=)
     */
    override fun visit(node : ASTLE, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryBooleanOperator(node, this, data, variableTable)
    }

    /**
     * 不等号(>=)
     */
    override fun visit(node : ASTGE, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryBooleanOperator(node, this, data, variableTable)
    }

    /**
     * 加算(+)
     */
    override fun visit(node : ASTAdd, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryNumberOperator(node, this, data, variableTable)
    }

    /**
     * 減算(-)
     */
    override fun visit(node : ASTSub, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryNumberOperator(node, this, data, variableTable)
    }

    /**
     * 文字列連結
     */
    override fun visit(node : ASTStrAdd, data : Any) : Any
    {
        return EvaluationUtility.evalStringAddOperator(node, this, data)
    }

    /**
     * 乗算(*)
     */
    override fun visit(node : ASTMul, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryNumberOperator(node, this, data, variableTable)
    }

    /**
     * 除算(/)
     */
    override fun visit(node : ASTDiv, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryNumberOperator(node, this, data, variableTable)
    }

    /**
     * 余算(mod)
     */
    override fun visit(node : ASTMod, data : Any) : Any
    {
        return EvaluationUtility.evalBinaryNumberOperator(node, this, data, variableTable)
    }

    /**
     * 単項マイナス(-)
     */
    override fun visit(node : ASTNeg, data : Any) : Any
    {
        return EvaluationUtility.evalSingleOperator(node, false, false, this, data, variableTable)
    }

    /**
     * 単項NOT(not)
     */
    override fun visit(node : ASTNot, data : Any) : Any
    {
        return EvaluationUtility.evalSingleOperator(node, false, false, this, data, variableTable)
    }

    /**
     * 単項論理否定(not)
     */
    override fun visit(node : ASTLogicalNot, data : Any) : Any
    {
        //--------------------------------------------------------------------------
        // 条件評価ステートメントでしか使えない
        //--------------------------------------------------------------------------
        if(!EvaluationUtility.isInConditionalStatement(node))
        {
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_SINGLE_OPERATOR_LNOT, node.symbol)
            AnalyzeErrorCounter.e()
        }
        return EvaluationUtility.evalSingleOperator(node, false, true, this, data, variableTable)
    }

    /**
     * リテラル定数参照
     * @return node自身
     */
    override fun visit(node : ASTLiteral, data : Any) : Any
    {
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
        return node
    }

    /**
     * ユーザー定義関数呼び出し
     */
    override fun visit(node : ASTCallUserFunctionStatement, data : Any) : Any
    {
        return node
    }

    //--------------------------------------------------------------------------
    // プリプロセッサ
    //--------------------------------------------------------------------------

    /**
     * プリプロセッサシンボル定義
     */
    override fun visit(node : ASTPreProcessorDefine, data : Any) : Any
    {
        val ret = defaultVisit(node, data)
        // プリプロセッサなので、既に宣言済みなら上書きもせずそのまま。
        // 複数回宣言可能な KONTAKT 側の挙動に合わせる形をとった。
        if(preProcessorSymbolTable.search(node.symbol) == null)
        {
            val decl = ASTPreProcessorDefine(KSPParserTreeConstants.JJTPREPROCESSORDEFINE)
            SymbolDefinition.copy(node.symbol, decl.symbol)
            decl.symbol.symbolType = SymbolType.PreprocessorSymbol

            val v = PreProcessorSymbol(decl)
            preProcessorSymbolTable.add(v)
        }
        return ret
    }

    /**
     * プリプロセッサシンボル破棄
     */
    override fun visit(node : ASTPreProcessorUnDefine, data : Any) : Any
    {
        // 宣言されていないシンボルを undef しようとした場合
        // 現状のKONTAKTでは未定義のシンボルでもエラーとならないので
        // 「意味解析では何もしない」
        // どのコールバック内でもundef可能なため、動的に呼ばれるコールバックなどは
        // 実行時に初めて解決するケースがある。
        // -> 意味解析だとASTの構造上スクリプトの上の行から下に向けてトラバースする。
        // 判定方法のコードはコメントアウトで以下に残しておく
/*
        if( preProcessorSymbolTable.search( node.symbol.getName() ) == null )
        {
            MessageManager.printlnW( MessageManager.PROPERTY_WARN_PREPROCESSOR_UNKNOWN_DEF, node.symbol );
            AnalyzeErrorCounter.w();
        }
        else
        {
            preProcessorSymbolTable.remove( node );
        }
*/
        return defaultVisit(node, data)
    }

    /**
     * ifdef
     */
    override fun visit(node : ASTPreProcessorIfDefined, data : Any) : Any
    {
        return defaultVisit(node, data)
    }

    /**
     * ifndef
     */
    override fun visit(node : ASTPreProcessorIfUnDefined, data : Any) : Any
    {
        return defaultVisit(node, data)
    }
}
