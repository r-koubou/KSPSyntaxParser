/* =========================================================================

    PreProcessorSymbol.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.javacc.generated.ASTPreProcessorDefine
import net.rkoubou.kspcompiler.analyzer.SymbolDefinition

/**
 * プリプロセッサで定義したシンボルの中間表現を示す
 */
class PreProcessorSymbol( val astNode : ASTPreProcessorDefine ) : SymbolDefinition()
{
    init
    {
        SymbolDefinition.copy(astNode.symbol, this)
        this.symbolType = SymbolDefinition.SymbolType.PreprocessorSymbol
    }
}
