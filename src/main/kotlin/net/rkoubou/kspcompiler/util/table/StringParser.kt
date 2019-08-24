/* =========================================================================

    StringParser.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.util.table

/**
 * Stringインスタンスを格納するデフォルト実装
 */
class StringParser : SeparatedTextParser()
{
    /**
     * [.parse] から行を読み込む毎に呼び出される。
     */
    override fun parseLine(line : String, split : Array<String>) : Row
    {
        val ret = Row()
        for(s in split)
        {
            ret.add(Column(s))
        }
        return ret
    }
}
