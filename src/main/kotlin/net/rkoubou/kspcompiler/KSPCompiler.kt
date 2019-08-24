/* =========================================================================

    KSPCompiler.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler

import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.PrintStream
import java.io.Writer

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser

import net.rkoubou.kspcompiler.analyzer.AnalyzeErrorCounter
import net.rkoubou.kspcompiler.analyzer.SemanticAnalyzer
import net.rkoubou.kspcompiler.analyzer.SymbolCollector
import net.rkoubou.kspcompiler.javacc.generated.ASTRootNode
import net.rkoubou.kspcompiler.javacc.generated.KSPParser
import net.rkoubou.kspcompiler.obfuscator.Obfuscator
import net.rkoubou.kspcompiler.options.CommandlineOptions
import kotlin.system.exitProcess

/**
 * アプリケーション・エントリポイント
 */
fun main(args : Array<String>)
{
    KSPCompiler.execute(args)
}

object KSPCompiler
{
    fun execute(args : Array<String>)
    {
        var stdout : PrintStream = System.out
        var stderr : PrintStream = System.err
        var cmdLineParser : CmdLineParser
        stdout.use { stderr.use {
            // -Dkspparser.stdout.encoding=#### の指定があった場合、そのエンコードを標準出力・エラーに再設定する
            if(System.getProperty("kspparser.stdout.encoding") != null)
            {
                val encoding = System.getProperty("kspparser.stdout.encoding")
                stdout = PrintStream(System.out, true, encoding)
                stderr = PrintStream(System.err, true, encoding)
                System.setOut(stdout)
                System.setErr(stderr)
            }
            // コマンドライン引数の解析
            cmdLineParser = CommandlineOptions.setup(args)
            if(CommandlineOptions.options.usage)
            {
                usage(cmdLineParser)
                exitProcess(1)
            }
            if(CommandlineOptions.options.sourceFile == null)
            {
                usage(cmdLineParser)
                exitProcess(1)
            }

            // プログラム解析
            val file = File(CommandlineOptions.options.sourceFile!!)
            val p = KSPParser(file)

            // 構文解析フェーズ
            val rootNode = p.analyzeSyntax()
            if(rootNode == null || AnalyzeErrorCounter.hasError())
            {
                exitProcess(1)
            }

            // シンボル収集フェーズ
            val symbolCollector = SymbolCollector(rootNode)
            AnalyzeErrorCounter.reset()
            symbolCollector.analyze()
            if(AnalyzeErrorCounter.hasError())
            {
                exitProcess(1)
            }

            // 意味解析フェーズ
            if((!CommandlineOptions.options.parseonly))
            {
                val semanticAnalyzer = SemanticAnalyzer(symbolCollector)
                AnalyzeErrorCounter.reset()
                semanticAnalyzer.analyze()

                if(AnalyzeErrorCounter.hasError())
                {
                    exitProcess(1)
                }

                // オブファスケートは意味解析フェーズで構築したASTが必要なため
                if(CommandlineOptions.options.obfuscate)
                {
                    val obfuscator = Obfuscator(rootNode, symbolCollector)
                    obfuscator.analyze()

                    // ファイルに出力
                    val outputFile = CommandlineOptions.options.outputFile
                    if(outputFile != null)
                    {
                        FileWriter(outputFile,false).use {
                            it.write(obfuscator.toString())
                        }
                    }
                    else
                    {
                        // 標準出力に出力
                        println(obfuscator)
                    }
                }
                exitProcess(0)
            }
        }}
    }

    /**
     * コマンドライン引数の表示
     */
    private fun usage(p : CmdLineParser?)
    {
        System.err.println("usage")
        System.err.println("  java -jar ./KSPSyntaxParser.jar [options] source")
        if(p != null)
        {
            System.err.println()
            p.printUsage(System.err)
        }
        else
        {
            System.err.println("please type -h to show usage")
        }
        System.err.println()
    }

}
