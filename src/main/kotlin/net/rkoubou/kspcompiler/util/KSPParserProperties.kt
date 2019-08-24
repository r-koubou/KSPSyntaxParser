/* =========================================================================

    KSPParserProperties.kt
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.util

import java.io.IOException
import java.util.Properties

import net.rkoubou.kspcompiler.ApplicationConstants

class KSPParserProperties

@Throws(IOException::class)
@JvmOverloads constructor(path : String, dir : String? = ApplicationConstants.DATA_DIR)
{
    /** プロパティ格納先  */
    private val properties : Properties

    init
    {
        var loadDir = dir
        if(loadDir == null)
        {
            loadDir = ApplicationConstants.DEFAULT_DATADIR
        }
        val propertiesPath = "$loadDir/$path"
        properties = UTF8Properties.load(propertiesPath)
    }

    /**
     * Propertiesインスタンスを取得する
     */
    fun get() : Properties
    {
        return properties
    }

    /**
     * 指定されたキーから値の取得を試みる
     */
    fun getInt(key : String, defaultValue : String) : String
    {
        return properties.getProperty(key, defaultValue).trim { it <= ' ' }
    }

    /**
     * 指定されたキーの値が整数の場合、値を int として取得を試みる
     */
    fun getInt(key : String, defaultValue : Int) : Int
    {
        val str = properties.getProperty(key, "").trim { it <= ' ' }
        return if(str.isEmpty())
        {
            defaultValue
        }
        else Integer.parseInt(str)
    }
}
