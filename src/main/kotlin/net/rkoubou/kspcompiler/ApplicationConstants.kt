/* =========================================================================

    ApplicationConstants.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler

/**
 * アプリケーション全体に関連する定数の定義
 */
object ApplicationConstants
{
    /** VM引数 -D dataフォルダの明示的指定時のプロパティ名  */
    private const val SYSTEM_PROPERTY_DATADIR = "kspparser.datadir"

    /** VM引数 -Dkspparser.datadir がなかった時のデフォルトの dataフォルダの相対パス  */
    const val DEFAULT_DATADIR = "data"

    /**  */
    var DATA_DIR : String

    /**
     * static initializer
     */
    init
    {
        DATA_DIR = System.getProperty(SYSTEM_PROPERTY_DATADIR) ?: DEFAULT_DATADIR
    }

}
