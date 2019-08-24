/* =========================================================================

    CommandlineOptions.kt
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.options

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser

/**
 * コマンドライン引数をパースしオプションを構成する
 */
class CommandlineOptions
{
    companion object
    {
        /** コマンドラインオプションを格納している Bean  */
        @JvmField val options = KSPParserOptions()

        /**
         * 渡されたコマンドライン引数を元にオプションを構成する
         */
        @Throws(CmdLineException::class)
        fun setup(args : Array<String>) : CmdLineParser
        {
            val parser = CmdLineParser(options)
            parser.parseArgument(*args)

            if(options.strict)
            {
                options.unused = true
            }
            return parser
        }
    }
}
