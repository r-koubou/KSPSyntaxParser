/* =========================================================================

    Argument.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.javacc.generated.ASTVariableDeclaration

/**
 * 引数の変数の中間表現を示す
 */
class Argument : Variable
{
    /** on init 内で変数宣言されている必要があるかどうか（例：on ui_control コールバック）  */
    var requireDeclarationOnInit = false

    /**
     * Ctor.
     */
    constructor(node : ASTVariableDeclaration) : super(node)
    {
    }

    /**
     * Ctor.
     */
    constructor(src : Variable) : super(src.astNode)
    {
        SymbolDefinition.copy(src, this)
    }
}
