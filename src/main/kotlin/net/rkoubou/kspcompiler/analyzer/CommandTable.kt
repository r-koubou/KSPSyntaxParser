/* =========================================================================

    CommandTable.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.analyzer.SymbolDefinition.SymbolType
import net.rkoubou.kspcompiler.javacc.generated.ASTCallCommand

/**
 * KSPコマンド定義テーブル
 */
class CommandTable : SymbolTable<ASTCallCommand, Command>, AnalyzerConstants
{
    /**
     * ctor
     */
    constructor() : super()

    /**
     * ctor
     */
    constructor(parent : CommandTable) : super(parent)

    /**
     * ctor
     */
    constructor(parent : CommandTable, startIndex : Int) : super(parent, startIndex)

    /**
     * ユーザー定義関数テーブルへの追加
     */
    override fun add(node : ASTCallCommand) : Boolean
    {
        return add(Command(node))
    }

    /**
     * コマンドテーブルへの追加
     */
    fun add(c : Command) : Boolean
    {
        val name = c.name
        if(table.containsKey(name))
        {
            return false
        }

        c.index = this.index
        this.index++
        c.symbolType = SymbolType.Command
        table[name] = c
        return true
    }
}
