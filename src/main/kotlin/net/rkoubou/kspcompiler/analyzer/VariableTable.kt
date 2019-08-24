/* =========================================================================

    VariableTable.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.analyzer.SymbolDefinition.SymbolType
import net.rkoubou.kspcompiler.javacc.generated.ASTVariableDeclaration


/**
 * 変数テーブル
 */
class VariableTable : SymbolTable<ASTVariableDeclaration, Variable>, AnalyzerConstants
{
    /**
     * ctor
     */
    constructor() : super()

    /**
     * ctor
     */
    constructor(parent : VariableTable) : super(parent)

    /**
     * ctor
     */
    constructor(parent : VariableTable, startIndex : Int) : super(parent, startIndex)

    /**
     * 変数テーブルへの追加
     */
    override fun add(node : ASTVariableDeclaration) : Boolean
    {
        val v = Variable(node)
        return add(v)
    }

    /**
     * 変数テーブルへの追加
     */
    fun add(v : Variable) : Boolean
    {
        val name = v.name
        if(table.containsKey(name))
        {
            // 宣言済み
            return false
        }

        if(v.isConstant)
        {
            v.index = -1
        }
        else
        {
            v.index = index
            index += 1
        }
        v.symbolType = SymbolType.Variable
        v.setTypeFromVariableName()
        table[name] = v
        return true
    }
}
