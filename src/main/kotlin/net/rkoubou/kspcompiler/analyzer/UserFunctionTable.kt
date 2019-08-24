/* =========================================================================

    UserFunctionTable.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.analyzer.SymbolDefinition.SymbolType
import net.rkoubou.kspcompiler.javacc.generated.ASTUserFunctionDeclaration

/**
 * ユーザー定義関数テーブル
 */
class UserFunctionTable : SymbolTable<ASTUserFunctionDeclaration, UserFunction>, AnalyzerConstants
{
    /**
     * ctor
     */
    constructor() : super()

    /**
     * ctor
     */
    constructor(parent : UserFunctionTable) : super(parent)

    /**
     * ctor
     */
    constructor(parent : UserFunctionTable, startIndex : Int) : super(parent, startIndex)

    /**
     * ユーザー定義関数テーブルへの追加
     */
    override fun add(node : ASTUserFunctionDeclaration) : Boolean
    {
        return add(UserFunction(node))
    }

    /**
     * ユーザー定義関数テーブルへの追加
     */
    fun add(c : UserFunction) : Boolean
    {
        val name = c.name
        if(table.containsKey(name))
        {
            return false
        }

        c.index = index
        index += 1
        c.symbolType = SymbolType.UserFunction
        table[name] = c
        return true
    }
}
