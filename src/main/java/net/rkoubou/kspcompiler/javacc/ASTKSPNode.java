/* =========================================================================

    ASTKSPNode.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.javacc;

import net.rkoubou.kspcompiler.analyzer.SymbolDefinition;

import net.rkoubou.kspcompiler.javacc.generated.Node;
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants;

/**
 * ASTの基底クラス
 */
abstract public class ASTKSPNode implements Node, KSPParserTreeConstants
{
    public final SymbolDefinition symbol = new SymbolDefinition();

    /**
     * このノードが2項演算子かどうかを判定する
     */
    public boolean isBinaryOperator()
    {
        switch( getId() )
        {
            case JJTADD:
            case JJTSTRADD:
            case JJTSUB:
            case JJTMUL:
            case JJTDIV:
            case JJTMOD:
            case JJTBITWISEOR:
            case JJTBITWISEAND:
                return true;
        }
        return false;
    }

    /**
     * このノードが単項演算子かどうかを判定する
     */
    public boolean isSingleOperator()
    {
        switch( getId() )
        {
            case JJTNEG:
            case JJTNOT:
            case JJTLOGICALNOT:
                return true;
        }
        return false;
    }
}
