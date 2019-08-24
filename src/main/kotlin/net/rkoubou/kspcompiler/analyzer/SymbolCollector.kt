/* =========================================================================

    SymbolCollector.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.io.IOException

import net.rkoubou.kspcompiler.analyzer.data.reserved.ReservedSymbolManager
import net.rkoubou.kspcompiler.javacc.generated.ASTCallbackArgumentList
import net.rkoubou.kspcompiler.javacc.generated.ASTCallbackDeclaration
import net.rkoubou.kspcompiler.javacc.generated.ASTRootNode
import net.rkoubou.kspcompiler.javacc.generated.ASTUserFunctionDeclaration
import net.rkoubou.kspcompiler.javacc.generated.ASTVariableDeclaration
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants
import net.rkoubou.kspcompiler.javacc.generated.SimpleNode

/**
 * シンボルテーブル構築クラス
 */
open class SymbolCollector(node : ASTRootNode) : AbstractAnalyzer(node)
{
    val uiTypeTable = UITypeTable()
    val variableTable = VariableTable()
    val reservedCallbackTable = CallbackTable()
    val usercallbackTable = CallbackTable()
    val commandTable = CommandTable()
    val userFunctionTable = UserFunctionTable()
    val preProcessorSymbolTable = PreProcessorSymbolTable()

    /**
     * 予約済み変数を定義ファイルから収集する
     */
    @Throws(IOException::class)
    private fun collectReservedariable()
    {
        val mgr = ReservedSymbolManager.manager
        mgr.load()
        mgr.apply(uiTypeTable)
        mgr.apply(variableTable)
        mgr.apply(reservedCallbackTable)
        mgr.apply(commandTable)
    }

    /**
     * ユーザースクリプトからシンボルを収集
     */
    @Throws(Exception::class)
    override fun analyze()
    {
        collectReservedariable()
        astRootNode.jjtAccept(this, null)
    }

    /**
     * 変数テーブル構築
     */
    override fun visit(node : ASTVariableDeclaration, data : Any?) : Any?
    {
        val ret = defaultVisit(node, data)
        //--------------------------------------------------------------------------
/*
    VariableDeclaration                     // NOW
            -> ASTVariableInitializer
                -> [
                      ArrayInitializer
                    | UIInitializer
                    | PrimitiveInititalizer
                ]
*/
        //--------------------------------------------------------------------------
        if(validateVariableImpl(node))
        {
            variableTable.add(node)
            val v = variableTable.search(node.symbol)
            //--------------------------------------------------------------------------
            // UI変数チェック / 外部定義とのマージ
            //--------------------------------------------------------------------------
            if(v!!.isUIVariable)
            {
                val uiName = v.uiTypeName
                val uiType = uiTypeTable.search(uiName)
                if(uiType == null)
                {
                    // NI が定義していないUIの可能性
                    MessageManager.printlnW(MessageManager.PROPERTY_WARN_UI_VARIABLE_UNKNOWN, v)
                    AnalyzeErrorCounter.w()
                }
                else
                {
                    // UI変数に適したデータ型へマージ
                    v.accessFlag = AnalyzerConstants.ACCESS_ATTR_UI
                    // v.type       = uiType.uiValueType; 型は意味解析フェーズでチェック
                    if(uiType.constant)
                    {
                        v.accessFlag = v.accessFlag or AnalyzerConstants.ACCESS_ATTR_CONST
                    }
                    // 意味解析フェーズで詳細を参照するため保持
                    v.uiTypeInfo = uiType
                }
            }
            else
            {
                // const、poly修飾子は構文解析フェーズで代入済み
                v.type = SymbolDefinition.getKSPTypeFromVariableName(v.name)
            }// プリミティブ型
        }

        return ret
    }

    /**
     * 変数、プリプロセッサシンボル収集の共通の事前検証処理
     */
    protected fun validateVariableImpl(node : SimpleNode) : Boolean
    {
        //--------------------------------------------------------------------------
        // 変数は on init 内でしか宣言できない
        //--------------------------------------------------------------------------
        var currentCallBack : ASTCallbackDeclaration?
        run {
            var n = node.jjtGetParent()
            do
            {
                if(n.id == KSPParserTreeConstants.JJTCALLBACKDECLARATION)
                {
                    currentCallBack = n as ASTCallbackDeclaration
                    break
                }
                n = n.jjtGetParent()
            }
            while(true)
        }
        //--------------------------------------------------------------------------
        // 変数名の検証（型チェックは意味解析フェーズで実行）
        //--------------------------------------------------------------------------
        run {
            val d = node.symbol
            //--------------------------------------------------------------------------
            // 予約済み（NIが禁止している）接頭語検査
            //--------------------------------------------------------------------------
            if(!EvaluationUtility.isAvailableUserVariableName(d, false))
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_VARIABLE_PREFIX_RESERVED, d)
                AnalyzeErrorCounter.e()
            }
            //--------------------------------------------------------------------------
            // on init 外での宣言検査
            //--------------------------------------------------------------------------
            if(currentCallBack!!.symbol.name != "init")
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_VARIABLE_ONINIT, d)
                AnalyzeErrorCounter.e()
            }
            //--------------------------------------------------------------------------
            // 定義済みの検査
            //--------------------------------------------------------------------------
            run {
                val v = variableTable.search(d)
                // NI の予約変数との重複
                if(v != null && v.reserved)
                {
                    MessageManager.printlnE(MessageManager.PROPERTY_ERROR_VARIABLE_RESERVED, d)
                    AnalyzeErrorCounter.e()
                    return false
                }
                else if(v != null)
                {
                    MessageManager.printlnE(MessageManager.PROPERTY_ERROR_VARIABLE_DECLARED, d)
                    AnalyzeErrorCounter.e()
                    return false
                }
                else
                {
                    return true
                }// 未定義：新規追加可能
                // ユーザー変数との重複
            }
        }
    }

    /**
     * コールバックテーブル構築
     */
    override fun visit(node : ASTCallbackDeclaration, data : Any?) : Any?
    {
        val ret = defaultVisit(node, data)

        if(node.jjtGetNumChildren() >= 2)
        {
            // コールバック引数リストあり
            val argList = node.jjtGetChild(0) as ASTCallbackArgumentList
            val listSize = argList.args.size
            for(i in 0 until listSize)
            {
                val arg = argList.args[i]
                val v = variableTable.search(arg)
                if(v == null)
                {
                    val s = SymbolDefinition(node.symbol)
                    s.name = arg
                    MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_NOT_DECLARED, s)
                    AnalyzeErrorCounter.e()
                }
                else
                {
                    v.referenced = true
                }
            }
        }

        val reserved = reservedCallbackTable.search(node.symbol.name)
        if(reserved == null)
        {
            // NI が定義していないコールバックの可能性
            MessageManager.printlnW(MessageManager.PROPERTY_WARN_CALLBACK_UNKNOWN, node.symbol)
            AnalyzeErrorCounter.w()
        }

        val newCallback : Callback
        newCallback = if(reserved != null)
        {
            Callback(reserved)
        }
        else
        {
            Callback(node)
        }

        if(!usercallbackTable.add(newCallback))
        {
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_CALLBACK_DECLARED, node.symbol)
            AnalyzeErrorCounter.e()
        }
        return ret
    }

    /**
     * ユーザー定義関数テーブル構築
     */
    override fun visit(node : ASTUserFunctionDeclaration, data : Any) : Any
    {
        val ret = defaultVisit(node, data)

        if(!node.symbol.validateNonVariablePrefix())
        {
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_GENERAL_SYMBOL_PREFIX_NUMERIC, node.symbol)
            AnalyzeErrorCounter.e()
            return ret
        }

        if(!userFunctionTable.add(node))
        {
            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_FUNCTION_DECLARED, node.symbol)
            AnalyzeErrorCounter.e()
            return ret
        }

        return ret
    }
}