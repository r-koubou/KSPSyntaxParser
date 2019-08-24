/* =========================================================================

    StreamCloser.kt
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.util

import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.io.Reader
import java.io.Writer

/**
 * 各種ストリームを安全にクローズする
 */
object StreamCloser
{

    /**
     * 指定されたストリームのクローズ
     */
    fun close(stream : InputStream)
    {
        try
        {
            stream.close()
        }
        catch(e : Throwable)
        {
        }

    }

    /**
     * 指定されたストリームのクローズ
     */
    fun close(out : OutputStream)
    {
        try
        {
            out.flush()
        }
        catch(e : Throwable)
        {
        }

        try
        {
            out.close()
        }
        catch(e : Throwable)
        {
        }

    }

    /**
     * 指定されたストリームのクローズ
     */
    fun close(r : Reader)
    {
        try
        {
            r.close()
        }
        catch(e : Throwable)
        {
        }

    }

    /**
     * 指定されたストリームのクローズ
     */
    fun close(w : Writer)
    {
        try
        {
            w.flush()
        }
        catch(e : Throwable)
        {
        }

        try
        {
            w.close()
        }
        catch(e : Throwable)
        {
        }

    }

    /**
     * 指定されたストリームのクローズ
     */
    fun close(ps : PrintStream)
    {
        try
        {
            ps.flush()
        }
        catch(e : Throwable)
        {
        }

        try
        {
            ps.close()
        }
        catch(e : Throwable)
        {
        }

    }
}
