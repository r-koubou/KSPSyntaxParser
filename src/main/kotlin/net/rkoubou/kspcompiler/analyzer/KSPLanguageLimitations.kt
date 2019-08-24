/* =========================================================================

    KSPLanguageLimitations.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.util.KSPParserProperties
import kotlin.system.exitProcess

/**
 * KSPの言語仕様、バグなどに起因するパラメータ類の限界値の定義
 */
object KSPLanguageLimitations
{
    /** 定義ファイルパス  */
    private const val PROPERTIES_PATH = "ksp_limitations.properties"

    /** コールバック・ユーザー関数の行数オーバーフローのしきい値  */
    var overflowLines : Int = 0

    /** 配列変数宣言時の要素数の上限  */
    var maxKspArraySize : Int = 0

    init
    {
        try
        {
            val p = KSPParserProperties(PROPERTIES_PATH)
            overflowLines = p.getInt("ksp.overflow.lines", 4950)
            maxKspArraySize = p.getInt("ksp.array.size", 32768)
        }
        catch(e : Throwable)
        {
            e.printStackTrace()
            exitProcess(1)
        }

    }

}
