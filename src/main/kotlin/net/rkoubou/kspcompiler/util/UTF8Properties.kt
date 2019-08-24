/* =========================================================================

    UTF8Properties.kt
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.util

import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Properties

/**
 * Java Propertiesファイルを UTF-8で扱うためのラッパー
 */
object UTF8Properties
{

    /**
     * 指定された入力ストリームのコンテンツがUTF-8 エンコードであることを条件に Properties クラスを生成する
     */
    @Throws(IOException::class)
    fun load(stream : InputStream) : Properties
    {
        val reader = InputStreamReader(stream, "utf-8")
        val p = Properties()
        p.load(reader)
        return p
    }

    /**
     * 指定されたパスのコンテンツがUTF-8 エンコードであることを条件に Properties クラスを生成する
     */
    @Throws(IOException::class)
    fun load(path : String) : Properties
    {
        return load(FileInputStream(path))
    }
}

