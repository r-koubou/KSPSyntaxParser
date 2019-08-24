/* =========================================================================

    ReturnType.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.util.ArrayList

/**
 * コマンドの戻り値の中間表現を示す
 */
class ReturnType : AnalyzerConstants
{
    /** データ型を格納する（複数タイプを許容するコマンドに対応するため配列としている）  */
    val typeList = ArrayList<Int>()

    /**
     * ctor.
     */
    constructor()

    /**
     * ctor.
     */
    constructor(vararg type : Int)
    {
        for(i in type)
        {
            typeList.add(i)
        }
    }

    /**
     * 戻り値を保有していない場合は true を返す
     */
    fun empty() : Boolean
    {
        return typeList.isEmpty()
    }

    /**
     * 指定されたタイプ値を保有しているかどうか
     * @see AnalyzerConstants
     */
    operator fun contains(type : Int) : Boolean
    {
        for(i in typeList)
        {
            if(i == type)
            {
                return true
            }
        }
        return false
    }

    /**
     * @see java.lang.Object.toString
     */
    override fun toString() : String
    {
        val sb = StringBuilder()
        for(t in typeList)
        {
            sb.append(SymbolDefinition.toKSPTypeCharacter(t))
        }
        return sb.toString()
    }

}
