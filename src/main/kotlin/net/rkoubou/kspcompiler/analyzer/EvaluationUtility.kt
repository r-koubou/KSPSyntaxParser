/* =========================================================================

    EvaluationUtility.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.analyzer.SymbolDefinition.SymbolType
import net.rkoubou.kspcompiler.javacc.generated.ASTCallbackDeclaration
import net.rkoubou.kspcompiler.javacc.generated.ASTLiteral
import net.rkoubou.kspcompiler.javacc.generated.ASTUserFunctionDeclaration
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants
import net.rkoubou.kspcompiler.javacc.generated.KSPParserVisitor
import net.rkoubou.kspcompiler.javacc.generated.Node
import net.rkoubou.kspcompiler.javacc.generated.SimpleNode

/**
 * 評価式の汎用処理を実装した補助クラス
 */
open class EvaluationUtility
/**
 * Ctor.
 */
private constructor() : AnalyzerConstants, KSPParserTreeConstants
{
    companion object
    {

        /**
         * 上位ノードに返す評価式のテンプレを生成する
         */
        fun createEvalNode(src : SimpleNode, nodeId : Int) : SimpleNode
        {
            val ret = SimpleNode(nodeId)
            SymbolDefinition.copy(src.symbol, ret.symbol)
            return ret
        }

        //------------------------------------------------------------------------------
        // ステートメント・コールバック・ユーザー関数
        //------------------------------------------------------------------------------

        /**
         * 与えられたノードが指定されたノードID以下に存在するかどうかを判定する
         */
        protected fun isInNode(child : Node, vararg nodeId : Int) : Boolean
        {
            var node = child
            while(true)
            {
                val p = node.jjtGetParent() ?: return false
                for(id in nodeId)
                {
                    if(p.id == id)
                    {
                        return true
                    }
                }
                node = p
            }
        }

        /**
         * 指定されたノードIDを持つ親ノードを検索する。見つからない場合はnullを返す
         */
        protected fun searchParentNode(child : Node, vararg nodeId : Int) : SimpleNode?
        {
            var childNode = child
            while(true)
            {
                val p = childNode.jjtGetParent() ?: return null
                for(id in nodeId)
                {
                    if(p.id == id)
                    {
                        return p as SimpleNode
                    }
                }
                childNode = p
            }
        }

        /**
         * 与えられた式が条件ステートメント(if,while等)内で実行されているかどうかを判定する
         * BOOL演算子はこの状況下でしか使用出来ないKSP仕様
         */
        fun isInConditionalStatement(expr : Node) : Boolean
        {
            return isInNode(expr, KSPParserTreeConstants.JJTIFSTATEMENT, KSPParserTreeConstants.JJTWHILESTATEMENT, KSPParserTreeConstants.JJTSELECTSTATEMENT)
        }

        /**
         * 現在のパース中のコールバックを取得する
         */
        fun getCurrentCallBack(child : Node) : ASTCallbackDeclaration?
        {
            val n = searchParentNode(child, KSPParserTreeConstants.JJTCALLBACKDECLARATION)
            return if(n != null) n as ASTCallbackDeclaration? else null
        }

        /**
         * 現在のパース中のユーザー定義関数を取得する
         */
        fun getCurrentUserFunction(child : Node) : ASTUserFunctionDeclaration?
        {
            val n = searchParentNode(child, KSPParserTreeConstants.JJTUSERFUNCTIONDECLARATION)
            return if(n != null) n as ASTUserFunctionDeclaration? else null
        }

        /**
         * 与えられたノードが変数宣言ノード以下に存在するかどうかを判定する
         */
        fun isInVariableDeclaration(child : Node) : Boolean
        {
            return isInNode(child, KSPParserTreeConstants.JJTVARIABLEDECLARATION)
        }

        /**
         * 与えられたノードが代入ノード以下に存在するかどうかを判定する
         */
        fun isInAssignment(child : Node) : Boolean
        {
            return isInNode(child, KSPParserTreeConstants.JJTASSIGNMENT)
        }


        //------------------------------------------------------------------------------
        // 変数
        //------------------------------------------------------------------------------

        /**
         * 指定されたノードが配列変数参照で、且つ添字を含んでいるかどうかを判定する
         * @param varNode 変数参照のノード
         * @param defaultValue varNodeが配列型変数参照のノードでなかった場合の戻り値
         * @return 配列型変数参照のノードの場合は子ノード(ArrayIndx)を持つかどうかの判定結果、そうでない場合は defaultValue
         */
        fun validArraySubscript(varNode : SimpleNode, defaultValue : Boolean) : Boolean
        {
            if(varNode.id != KSPParserTreeConstants.JJTREFVARIABLE)
            {
                return defaultValue
            }
            return if(!varNode.symbol.isArray)
            {
                defaultValue
            }
            else varNode.jjtGetNumChildren() > 0

            // RefVariable [ ArrayIndex ]
        }

        //------------------------------------------------------------------------------
        // 畳み込み
        //------------------------------------------------------------------------------

        /**
         * 与えられた式ノードから定数値を算出する（畳み込み）
         * 定数値が含まれていない場合はその時点で処理を終了、nullを返す。
         * @param calc 定数値カウント時の再帰処理用。最初のノード時のみ 0 を渡す
         */
        fun evalConstantIntValue(expr : SimpleNode, calc : Int, variableTable : VariableTable) : Int?
        {
            val ret : Int? = null

            //--------------------------------------------------------------------------
            // リテラル・変数
            //--------------------------------------------------------------------------
            when
            {
                expr.jjtGetNumChildren() == 0 -> when(expr.id)
                {
                    KSPParserTreeConstants.JJTLITERAL     ->
                    {
                        return expr.jjtGetValue() as Int
                    }
                    KSPParserTreeConstants.JJTREFVARIABLE ->
                    {
                        val v = variableTable.search(expr.symbol)
                        if(v == null)
                        {
                            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_NOT_DECLARED, expr.symbol)
                            AnalyzeErrorCounter.e()
                            return null
                        }
                        else if(!v.isConstant || !v.isInt)
                        {
                            return null
                        }
                        v.referenced = true
                        v.state = AnalyzerConstants.SymbolState.LOADED
                        return v.value as Int?
                    }
                }
                //--------------------------------------------------------------------------
                // ２項演算子
                //--------------------------------------------------------------------------
                expr.jjtGetNumChildren() == 2 ->
                {
                    val exprL = expr.jjtGetChild(0) as SimpleNode
                    val exprR = expr.jjtGetChild(1) as SimpleNode
                    val numL = evalConstantIntValue(exprL, calc, variableTable) ?: return null
                    val numR = evalConstantIntValue(exprR, numL, variableTable) ?: return null
                    return when(expr.id)
                    {
                        KSPParserTreeConstants.JJTADD        -> numL + numR
                        KSPParserTreeConstants.JJTSUB        -> numL - numR
                        KSPParserTreeConstants.JJTMUL        -> numL * numR
                        KSPParserTreeConstants.JJTDIV        -> numL / numR
                        KSPParserTreeConstants.JJTMOD        -> numL % numR
                        KSPParserTreeConstants.JJTBITWISEOR  -> numL or numR
                        KSPParserTreeConstants.JJTBITWISEAND -> numL and numR
                        else                                 -> null
                    }
                }
                //--------------------------------------------------------------------------
                // 単項演算子
                //--------------------------------------------------------------------------
                expr.jjtGetNumChildren() == 1 ->
                {
                    val exprL = expr.jjtGetChild(0) as SimpleNode
                    val numL = evalConstantIntValue(exprL, calc, variableTable) ?: return null
                    return when(expr.id)
                    {
                        KSPParserTreeConstants.JJTNEG        -> -numL
                        KSPParserTreeConstants.JJTNOT        -> numL.inv()
                        KSPParserTreeConstants.JJTLOGICALNOT -> if(numL != 0) 0 else 1 // 0=false, 1=true としている
                        else                                 -> null
                    }

                }
            }
            return ret
        }

        /**
         * 与えられた式ノードから定数値を算出する（畳み込み）
         * 定数値が含まれていない場合はその時点で処理を終了、nullを返す。
         * @param calc 定数値カウント時の再帰処理用。最初のノード時のみ 0 を渡す
         */
        fun evalConstantRealValue(expr : SimpleNode, calc : Double, variableTable : VariableTable) : Double?
        {
            val ret : Double? = null

            //--------------------------------------------------------------------------
            // リテラル・変数
            //--------------------------------------------------------------------------
            if(expr.jjtGetNumChildren() == 0)
            {
                when(expr.id)
                {
                    KSPParserTreeConstants.JJTLITERAL     ->
                    {
                        return expr.jjtGetValue() as Double
                    }
                    KSPParserTreeConstants.JJTREFVARIABLE ->
                    {
                        val v = variableTable.search(expr.symbol)
                        if(v == null)
                        {
                            MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_NOT_DECLARED, expr.symbol)
                            AnalyzeErrorCounter.e()
                            return null
                        }
                        else if(!v.isConstant || !v.isReal)
                        {
                            return null
                        }
                        v.referenced = true
                        v.state = AnalyzerConstants.SymbolState.LOADED
                        return v.value as Double?
                    }
                }
            }
            else if(expr.jjtGetNumChildren() == 2)
            {
                val exprL = expr.jjtGetChild(0) as SimpleNode
                val exprR = expr.jjtGetChild(1) as SimpleNode
                val numL = evalConstantRealValue(exprL, calc, variableTable) ?: return null
                val numR = evalConstantRealValue(exprR, numL, variableTable) ?: return null
                when(expr.id)
                {
                    KSPParserTreeConstants.JJTADD -> return numL + numR
                    KSPParserTreeConstants.JJTSUB -> return numL - numR
                    KSPParserTreeConstants.JJTMUL -> return numL * numR
                    KSPParserTreeConstants.JJTDIV -> return numL / numR
                    KSPParserTreeConstants.JJTMOD -> return numL % numR
                    else                          -> return null
                }
            }
            else if(expr.jjtGetNumChildren() == 1)
            {
                val exprL = expr.jjtGetChild(0) as SimpleNode
                val numL = evalConstantRealValue(exprL, calc, variableTable) ?: return null
                when(expr.id)
                {
                    KSPParserTreeConstants.JJTNEG -> return -numL
                    else                          -> return null
                }
            }//--------------------------------------------------------------------------
            // 単項演算子
            //--------------------------------------------------------------------------
            //--------------------------------------------------------------------------
            // ２項演算子
            //--------------------------------------------------------------------------
            return ret
        }

        /**
         * 与えられた式ノードから定数値を算出する（畳み込み）
         * 定数値が含まれていない場合はその時点で処理を終了、nullを返す。
         */
        fun evalConstantBooleanValue(node : SimpleNode, variableTable : VariableTable, jjtVisitor : KSPParserVisitor) : Boolean?
        {
            val exprL = node.jjtGetChild(0).jjtAccept(jjtVisitor, null) as SimpleNode
            val exprR = node.jjtGetChild(1).jjtAccept(jjtVisitor, null) as SimpleNode
            val symL = exprL.symbol
            val symR = exprR.symbol
            var ret : Boolean?

            if(symL.type != symR.type || !SymbolDefinition.isConstant(symL.accessFlag) || !SymbolDefinition.isConstant(symR.accessFlag))
            {
                return null
            }

            ret = false
            if(symL.primitiveType != symR.primitiveType)
            {
                return null
            }

            when(symL.primitiveType)
            {
                AnalyzerConstants.TYPE_INT  ->
                {
                    val intL = evalConstantIntValue(exprL, 0, variableTable)
                    val intR = evalConstantIntValue(exprR, 0, variableTable)

                    if(intL != null && intR != null)
                    {
                        when(node.id)
                        {
                            KSPParserTreeConstants.JJTEQUAL    -> ret = intL === intR
                            KSPParserTreeConstants.JJTNOTEQUAL -> ret = intL !== intR
                            KSPParserTreeConstants.JJTGT       -> ret = intL > intR
                            KSPParserTreeConstants.JJTLT       -> ret = intL < intR
                            KSPParserTreeConstants.JJTGE       -> ret = intL >= intR
                            KSPParserTreeConstants.JJTLE       -> ret = intL <= intR
                        }
                    }
                }
                AnalyzerConstants.TYPE_REAL ->
                {
                    val realL = evalConstantRealValue(exprL, 0.0, variableTable)
                    val realR = evalConstantRealValue(exprR, 0.0, variableTable)
                    if(realL != null && realR != null)
                    {
                        when(node.id)
                        {
                            KSPParserTreeConstants.JJTEQUAL    -> ret = realL === realR
                            KSPParserTreeConstants.JJTNOTEQUAL -> ret = realL !== realR
                            KSPParserTreeConstants.JJTGT       -> ret = realL > realR
                            KSPParserTreeConstants.JJTLT       -> ret = realL < realR
                            KSPParserTreeConstants.JJTGE       -> ret = realL >= realR
                            KSPParserTreeConstants.JJTLE       -> ret = realL <= realR
                        }
                    }
                }
            }
            return ret
        }

        //--------------------------------------------------------------------------
        // 式
        //--------------------------------------------------------------------------

        /**
         * 二項演算子の評価
         * @param node 演算子ノード
         * @param numberOp 演算子は数値を扱う演算子かどうか
         * @param booleanOp 演算子はブール演算子かどうか
         * @param jjtVisitor jjtAcceptメソッドのvisitor引数
         * @param jjtAcceptData jjtAcceptメソッドのdata引数
         * @param variableTable 変数テーブル
         * @return SimpleNodeインスタンス（データ型を格納した評価結果。エラー時は TYPE_VOID が格納される）
         */
        fun evalBinaryOperator(
            node : SimpleNode,
            numberOp : Boolean,
            booleanOp : Boolean,
            jjtVisitor : KSPParserVisitor,
            jjtAcceptData : Any,
            variableTable : VariableTable
        ) : SimpleNode
        {
            var n = node
/*
             <operator>
                 +
                 |
            +----+----+
            |         |
        0: <expr>   1:<expr>
*/

            val exprL = n.jjtGetChild(0).jjtAccept(jjtVisitor, jjtAcceptData) as SimpleNode
            val exprR = n.jjtGetChild(1).jjtAccept(jjtVisitor, jjtAcceptData) as SimpleNode
            val symL = exprL.symbol
            val symR = exprR.symbol
            val typeL = symL.type
            val typeR = symR.type
            var typeError = AnalyzerConstants.TYPE_VOID
            var typeCheckResult = true

            exprR.jjtAccept(jjtVisitor, jjtAcceptData)

            // 上位ノードの型評価式用
            var ret = EvaluationUtility.createEvalNode(n, n.id)

            // 型チェック
            if(numberOp)
            {
                // int と real を個別に判定しているのは、KSP が real から int の暗黙の型変換を持っていないため
                if(SymbolDefinition.isInt(symL, symR))
                {
                    ret.symbol.type = AnalyzerConstants.TYPE_INT
                }
                else if(SymbolDefinition.isReal(symL, symR))
                {
                    ret.symbol.type = AnalyzerConstants.TYPE_REAL
                }
                else
                {
                    if(!symL.isNumeral)
                    {
                        typeError = symL.type
                    }
                    else if(!symR.isNumeral)
                    {
                        typeError = symR.type
                    }
                    typeCheckResult = false
                }
            }
            else if(booleanOp)
            {
                ret.symbol.type = AnalyzerConstants.TYPE_BOOL
                if(typeL and typeR == 0)
                {
                    if(!symL.isBoolean)
                    {
                        typeError = symL.type
                    }
                    else if(!symR.isBoolean)
                    {
                        typeError = symR.type
                    }
                    typeCheckResult = false
                }
            }
            //--------------------------------------------------------------------------
            // 型チェック失敗
            //--------------------------------------------------------------------------
            if(!typeCheckResult)
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_BINOPR_DIFFERENT, n.symbol)
                AnalyzeErrorCounter.e()
                ret.symbol.type = typeError
                ret.symbol.name = SymbolDefinition.toKSPTypeCharacter(typeError)
                return ret
            }
            //--------------------------------------------------------------------------
            // 左辺、右辺共にリテラル、定数なら式の結果に定数フラグを反映
            // このノード自体を式からリテラルに置き換える
            //--------------------------------------------------------------------------
            if(!symL.reserved && !symR.reserved &&
                !SymbolDefinition.isArray(symL, symR) &&
                SymbolDefinition.isConstant(symL, symR)
            )
            {
                var constValue : Number? = null
                ret.symbol.accessFlag = ret.symbol.accessFlag or AnalyzerConstants.ACCESS_ATTR_CONST

                if(ret.symbol.isInt)
                {
                    constValue = EvaluationUtility.evalConstantIntValue(n, 0, variableTable)
                }
                else if(ret.symbol.isReal)
                {
                    constValue = EvaluationUtility.evalConstantRealValue(n, 0.0, variableTable)
                }
                // このノード自体を式からリテラルに置き換える
                ret.symbol.symbolType = SymbolType.Literal
                ret.symbol.name = ""
                n = n.reset(ASTLiteral(KSPParserTreeConstants.JJTLITERAL), null, constValue, ret.symbol)
                ret = ret.reset(ASTLiteral(KSPParserTreeConstants.JJTLITERAL), null, constValue, ret.symbol)
            }
            // 元のノードに型データ、値のコピー（値の畳み込み用）
            SymbolDefinition.setValue(ret.symbol, n.symbol)
            SymbolDefinition.setTypeFlag(ret.symbol, n.symbol)
            return ret
        }

        /**
         * &演算子の評価インプリメンテーション
         * @param node &演算子ノード
         * @param jjtVisitor jjtAcceptメソッドのvisitor引数
         * @param jjtAcceptData jjtAcceptメソッドのdata引数
         * @return SimpleNodeインスタンス（データ型を格納した評価結果。エラー時は TYPE_VOID が格納される）
         */
        fun evalStringAddOperator(node : SimpleNode, jjtVisitor : KSPParserVisitor, jjtAcceptData : Any) : SimpleNode
        {
            // 上位ノードの型評価式用
            val ret = EvaluationUtility.createEvalNode(node, node.id)
            ret.symbol.type = AnalyzerConstants.TYPE_STRING

            //--------------------------------------------------------------------------
            // ＊初期値代入式では使用できない
            //--------------------------------------------------------------------------
            run {
                var p : Node? = node.jjtGetParent()
                while(p != null)
                {
                    if(p.id == KSPParserTreeConstants.JJTVARIABLEINITIALIZER)
                    {
                        MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_INITIALIZER_STRINGADD, node.symbol)
                        AnalyzeErrorCounter.e()
                        ret.symbol.type = AnalyzerConstants.TYPE_VOID
                        ret.symbol.name = SymbolDefinition.toKSPTypeCharacter(AnalyzerConstants.TYPE_VOID)
                        return ret
                    }
                    p = p.jjtGetParent()
                }
            }

            val exprL = node.jjtGetChild(0).jjtAccept(jjtVisitor, jjtAcceptData) as SimpleNode
            val exprR = node.jjtGetChild(1).jjtAccept(jjtVisitor, jjtAcceptData) as SimpleNode
            val symL = exprL.symbol
            val symR = exprR.symbol

            //----------------------------------------------------------------------
            // KONTAKT内で暗黙の型変換が作動し、文字列型となる
            //----------------------------------------------------------------------

            // BOOL（条件式）は不可
            if(symL.isBoolean || symR.isBoolean)
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_STRING_OPERATOR_CONDITIONAL, node.symbol)
                AnalyzeErrorCounter.e()
                ret.symbol.type = AnalyzerConstants.TYPE_STRING
                ret.symbol.name = SymbolDefinition.toKSPTypeCharacter(AnalyzerConstants.TYPE_STRING)
                return ret
            }

            // 定数、リテラル同士の連結：結合
            if(SymbolDefinition.isConstant(symL, symR))
            {
                // この式の評価の過程で意味解析エラーを検出している場合、valueに値が存在しない場合がある
                if(symL.value == null || symR.value == null)
                {
                    MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_EXPRESSION_INVALID, node.symbol)
                    AnalyzeErrorCounter.e()
                    ret.symbol.type = AnalyzerConstants.TYPE_VOID
                    ret.symbol.name = SymbolDefinition.toKSPTypeCharacter(AnalyzerConstants.TYPE_VOID)
                    return ret
                }

                var v = symL.value!!.toString() + symR.value!!.toString()
                v = v.replace("\\\"".toRegex(), "")
                v = '"'.toString() + v + '"'.toString()
                ret.symbol.addTypeFlag(AnalyzerConstants.TYPE_NONE, AnalyzerConstants.ACCESS_ATTR_CONST)
                ret.symbol.setValue(v)
                node.symbol.value = ret.symbol.value
                SymbolDefinition.setTypeFlag(ret.symbol, node.symbol)
            }
            return ret
        }

        /**
         * evalBinaryOperator のコンビニエンスメソッド
         */
        fun evalBinaryNumberOperator(node : SimpleNode, jjtVisitor : KSPParserVisitor, jjtAcceptData : Any, variableTable : VariableTable) : SimpleNode
        {
            return evalBinaryOperator(node, true, false, jjtVisitor, jjtAcceptData, variableTable)
        }

        /**
         * evalBinaryOperator のコンビニエンスメソッド
         */
        fun evalBinaryBooleanOperator(node : SimpleNode, jjtVisitor : KSPParserVisitor, jjtAcceptData : Any, variableTable : VariableTable) : SimpleNode
        {
            return evalBinaryOperator(node, false, true, jjtVisitor, jjtAcceptData, variableTable)
        }

        /**
         * 単項演算子の評価
         * @param node 演算子ノード
         * @param numOnly 評価可能なのは整数型のみかどうか（falseの場合は浮動小数も対象）
         * @param booleanOp 演算子はブール演算子かどうか
         * @param jjVisitor jjtAcceptメソッドのvisitor引数
         * @param jjtAcceptData jjtAcceptメソッドのdata引数
         * @param variableTable 変数テーブル
         * @return データ型を格納した評価結果。エラー時は TYPE_VOID が格納される
         */
        fun evalSingleOperator(
            node : SimpleNode,
            numOnly : Boolean,
            booleanOp : Boolean,
            jjVisitor : KSPParserVisitor,
            jjtAcceptData : Any,
            variableTable : VariableTable
        ) : SimpleNode
        {
            var n = node
/*
             <operator>
                 +
                 |
                 +
              <expr>
*/

            val expr = n.jjtGetChild(0).jjtAccept(jjVisitor, jjtAcceptData) as SimpleNode
            val sym = expr.symbol
            val type = if(numOnly) AnalyzerConstants.TYPE_NUMERICAL else sym.type

            // 上位ノードの型評価式用
            var ret = EvaluationUtility.createEvalNode(n, n.id)

            // 式が数値型と一致している必要がある
            if(numOnly && !SymbolDefinition.isNumeral(type))
            {
                MessageManager.printlnE(MessageManager.PROPERTY_ERROR_SEMANTIC_SINGLE_OPERATOR_NUMONLY, expr.symbol)
                AnalyzeErrorCounter.e()
                ret.symbol.type = type
                ret.symbol.name = SymbolDefinition.toKSPTypeCharacter(type)
            }
            else
            {
                val t = if(booleanOp) AnalyzerConstants.TYPE_BOOL else type
                ret.symbol.name = SymbolDefinition.toKSPTypeCharacter(t)
                ret.symbol.type = t
            }
            //--------------------------------------------------------------------------
            // リテラル、定数なら式の結果に定数フラグを反映
            // このノード自体を式からリテラルに置き換える
            //--------------------------------------------------------------------------
            if(!sym.reserved &&
                !sym.isArray &&
                sym.isConstant
            )
            {
                var constValue : Number? = null
                ret.symbol.accessFlag = ret.symbol.accessFlag or AnalyzerConstants.ACCESS_ATTR_CONST

                if(ret.symbol.isInt)
                {
                    constValue = EvaluationUtility.evalConstantIntValue(n, 0, variableTable)
                }
                else if(ret.symbol.isReal)
                {
                    constValue = EvaluationUtility.evalConstantRealValue(n, 0.0, variableTable)
                }
                // このノード自体を式からリテラルに置き換える
                ret.symbol.symbolType = SymbolType.Literal
                ret.symbol.name = ""
                n = n.reset(ASTLiteral(KSPParserTreeConstants.JJTLITERAL), null, constValue, ret.symbol)
                ret = ret.reset(ASTLiteral(KSPParserTreeConstants.JJTLITERAL), null, constValue, ret.symbol)
/*
                ret.symbol.setValue( evalConstantIntValue( node, 0, variableTable ) );
                ret.symbol.addTypeFlag( TYPE_NONE, ACCESS_ATTR_CONST );
*/
            }

            // 元のノードに型データ、値のコピー（値の畳み込み用）
            SymbolDefinition.setValue(ret.symbol, n.symbol)
            SymbolDefinition.setTypeFlag(ret.symbol, n.symbol)
            return ret
        }

        /**
         * 渡された変数名が NI が禁止している変数の接頭文字を含んでいるかどうかを判定する
         */
        fun isAvailableUserVariableName(sym : SymbolDefinition, withPrintMessage : Boolean) : Boolean
        {
            for(n in AnalyzerConstants.RESERVED_VARIABLE_PREFIX_LIST)
            {
                if(sym.name.startsWith(n))
                {
                    if(withPrintMessage)
                    {
                        MessageManager.printlnW(MessageManager.PROPERTY_WARNING_SEMANTIC_VARIABLE_UNKNOWN, sym)
                        AnalyzeErrorCounter.countW()
                    }
                    return false
                }
            }
            return true
        }
    }
}
