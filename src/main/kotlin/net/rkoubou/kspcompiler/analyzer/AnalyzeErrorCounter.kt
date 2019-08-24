/* =========================================================================

    AnalyzeErrorCounter.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.io.PrintStream

/**
 * 解析中のエラー件数収集クラス
 */
object AnalyzeErrorCounter
{

    /** 解析中に検知したエラー総数  */
    private var errorCount = 0

    /** 解析中に検知した警告総数  */
    private var warningCount = 0

    /**
     * エラー総数を＋１
     */
    fun e()
    {
        errorCount++
    }

    /**
     * 警告総数を＋１
     */
    fun w()
    {
        warningCount++
    }

    /**
     * エラー・警告総数を０にリセットする
     */
    fun reset()
    {
        errorCount = 0
        warningCount = 0
    }

    /**
     * エラー総数を取得する
     */
    fun countE() : Int
    {
        return errorCount
    }

    /**
     * エラー総数が１つ以上あるかどうか
     */
    fun hasError() : Boolean
    {
        return errorCount > 0
    }

    /**
     * 警告総数を取得する
     */
    fun countW() : Int
    {
        return errorCount
    }

    /**
     * エラー総数が１つ以上あるかどうか
     */
    fun hasWarning() : Boolean
    {
        return warningCount > 0
    }

    /**
     * 結果を文字列形式で表現する
     */
    fun dump(p : PrintStream)
    {
        if(hasError())
        {
            p.println("$errorCount Error(s)")
        }
        if(hasWarning())
        {
            p.println("$warningCount Warning(s)")
        }
    }
}
