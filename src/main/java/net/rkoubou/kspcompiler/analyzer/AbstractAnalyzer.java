/* =========================================================================

    AbstractAnalyzer.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer;

import net.rkoubou.kspcompiler.javacc.generated.ASTRootNode;
import net.rkoubou.kspcompiler.javacc.generated.KSPParserDefaultVisitor;
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants;

/**
 * 解析実行のための基底クラス
 */
abstract public class AbstractAnalyzer extends KSPParserDefaultVisitor implements AnalyzerConstants, KSPParserTreeConstants
{

    /** AST ルートノード */
    protected final ASTRootNode astRootNode;

    /**
     * ctor
     */
    public AbstractAnalyzer( ASTRootNode astRootNode )
    {
        this.astRootNode = astRootNode;
    }

    /**
     * 解析を実行
     */
    abstract public void analyze() throws Exception;
}
