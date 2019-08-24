/* =========================================================================

    CallbackTable.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.analyzer.SymbolDefinition.SymbolType
import net.rkoubou.kspcompiler.javacc.generated.ASTCallbackDeclaration
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants

/**
 * コールバックテーブル
 */
class CallbackTable : SymbolTable<ASTCallbackDeclaration, Callback>, AnalyzerConstants, KSPParserTreeConstants
{
    /**
     * ctor
     */
    constructor() : super()

    /**
     * ctor
     */
    constructor(parent : CallbackTable) : super(parent)

    /**
     * ctor
     */
    constructor(parent : CallbackTable, startIndex : Int) : super(parent, startIndex)

    /**
     * コールバックテーブルへの追加
     */
    override fun add(node : ASTCallbackDeclaration) : Boolean
    {
/*
                        CallbackDeclaration
                                    |
                    +-------------+-----------+
                    |                         |
            CallbackArgumentList            Block
*/
        val c = Callback(node)
        return add(c)
    }

    /**
     * コールバックテーブルへの追加
     */
    @JvmOverloads
    fun add(c : Callback, name : String = c.name) : Boolean
    {
        if(table.containsKey(name))
        {
            //--------------------------------------------------------------------------
            // 宣言済み
            //--------------------------------------------------------------------------
            run {
                val p = table[name]
                //--------------------------------------------------------------------------
                // 外部定義ファイルで取り込んだシンボルがソースコード上で宣言されているかどうか
                // 初回の検出時はフラグを立てるだけ
                //--------------------------------------------------------------------------
                if(p!!.reserved && !p.declared)
                {
                    p.declared = true
                }
                //--------------------------------------------------------------------------
                // 多重定義を許可されていないコールバックには追加不可
                //--------------------------------------------------------------------------
                return p.isAllowDuplicate
            }
        }
        c.index = index
        index++
        c.symbolType = SymbolType.Callback
        table[name] = c
        return true
    }
}
