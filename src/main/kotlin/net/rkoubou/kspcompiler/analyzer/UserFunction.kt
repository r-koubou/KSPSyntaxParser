/* =========================================================================

    UserFunction.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.javacc.generated.ASTUserFunctionDeclaration

/**
 * ユーザー定義関数の中間表現を示す
 */
class UserFunction( val astNode : ASTUserFunctionDeclaration ) : SymbolDefinition()
{
    init
    {
        SymbolDefinition.copy(astNode.symbol, this)
        this.symbolType = SymbolDefinition.SymbolType.UserFunction
    }
}
