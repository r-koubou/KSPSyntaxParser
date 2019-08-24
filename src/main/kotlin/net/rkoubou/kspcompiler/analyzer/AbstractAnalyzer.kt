/* =========================================================================

    AbstractAnalyzer.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.javacc.generated.ASTRootNode
import net.rkoubou.kspcompiler.javacc.generated.KSPParserDefaultVisitor
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants

/**
 * 解析実行のための基底クラス
 */
abstract class AbstractAnalyzer( val astRootNode : ASTRootNode )
    : KSPParserDefaultVisitor(), AnalyzerConstants, KSPParserTreeConstants
{
    /**
     * 解析を実行
     */
    @Throws(Exception::class)
    abstract fun analyze()
}
