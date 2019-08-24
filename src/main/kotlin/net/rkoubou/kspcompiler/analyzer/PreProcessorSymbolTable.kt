/* =========================================================================

    PreProcessorSymbolTable.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.analyzer.SymbolDefinition.SymbolType
import net.rkoubou.kspcompiler.javacc.generated.ASTPreProcessorDefine
import net.rkoubou.kspcompiler.javacc.generated.ASTPreProcessorUnDefine
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants

/**
 * プリプロセッサシンボルテーブル
 */
class PreProcessorSymbolTable : SymbolTable<ASTPreProcessorDefine, PreProcessorSymbol>, KSPParserTreeConstants
{
    /**
     * ctor
     */
    constructor() : super()

    /**
     * ctor
     */
    constructor(parent : PreProcessorSymbolTable) : super(parent)

    /**
     * ctor
     */
    constructor(parent : PreProcessorSymbolTable, startIndex : Int) : super(parent, startIndex)

    /**
     * プリプロセッサシンボルテーブルへの追加
     */
    override fun add(node : ASTPreProcessorDefine) : Boolean
    {
        return add(PreProcessorSymbol(node))
    }

    /**
     * プリプロセッサシンボルテーブルへの追加
     */
    fun add(c : PreProcessorSymbol) : Boolean
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

    /**
     * UNDEFによるプリプロセッサシンボルテーブルからの除去
     */
    fun remove(undef : ASTPreProcessorUnDefine) : Boolean
    {
        val d = ASTPreProcessorDefine(KSPParserTreeConstants.JJTPREPROCESSORDEFINE)
        SymbolDefinition.copy(undef.symbol, d.symbol)
        return remove(PreProcessorSymbol(d))
    }

    /**
     * UNDEFによるプリプロセッサシンボルテーブルからの除去
     */
    fun remove(c : PreProcessorSymbol) : Boolean
    {
        val name = c.name
        if(!table.containsKey(name))
        {
            return false
        }
        table.remove(name)
        return true
    }
}
