/* =========================================================================

    ShortSymbolGenerator.kt
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.obfuscator

import java.util.Hashtable
import java.util.UUID

/**
 * 難読化を目的とした、KSPのシンボル名を一定の規則に従い生成する
 */
object ShortSymbolGenerator
{

    /** オブファスケート後のシンボル名の接頭文字  */
    private const val PREFIX = "_"

    /** 生成するシンボルの最大文字数  */
    private const val MAX_SYMBOL_LENGTH = 4

    /** シンボル名変更前と変更後の履歴を残すテーブル(元の名前: オブファスケート後)  */
    private val history = Hashtable<String, String>(1024)

    /**
     * static initializer
     */
    init
    {
        reset()
    }

    /**
     * 内部カウンタを初期状態にリセットする
     */
    fun reset()
    {
        history.clear()
    }

    /**
     * 頭文字に "v"、以降を ユニークな文字列を生成する (桁数: #MAX_SYMBOL_LENGTH )
     */
    fun generate(orgName : String) : String
    {
        var limit = 0L
        var uuid : String
        do
        {
            uuid = UUID.randomUUID().toString()
            uuid = PREFIX + uuid.replace("-".toRegex(), "").substring(0, MAX_SYMBOL_LENGTH)
        }
        while(history.containsValue(uuid) && ++limit < java.lang.Long.MAX_VALUE)

        if(limit == java.lang.Long.MAX_VALUE)
        {
            throw RuntimeException("Too many symbols declared. Cannot generate any more.")
        }

        history[orgName] = uuid
        return uuid
    }

    /**
     * オブファスケート前のシンボル名からオブファスケート後のシンボル名を逆引きする
     */
    fun getSymbolFromOrgName(orgName : String) : String?
    {
        var ret : String? = history[orgName]
        if(ret == null)
        {
            ret = generate(orgName)
        }
        return ret
    }

    /**
     * オブファスケート後のシンボル名からオブファスケート前のシンボル名を逆引きする
     */
    fun getOrgNameFromSymbol(symbol : String) : String?
    {
        for(k in history.keys)
        {
            if(symbol == history[k])
            {
                return k
            }
        }
        return null
    }
}
