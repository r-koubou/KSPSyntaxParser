package net.rkoubou.kspcompiler

import net.rkoubou.kspcompiler.generated.antlr.KSPLexer
import net.rkoubou.kspcompiler.generated.antlr.KSPParser
import net.rkoubou.kspcompiler.generated.antlr.KSPParserListener
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class SemanticAnalyzer(listener : KSPParserListener)
{
    private val parserListener = listener

    public fun analyze(inputFile:String)
    {
        val stream = CharStreams.fromFileName(inputFile)
        val lexer: KSPLexer = KSPLexer(stream)
        val tokenStream = CommonTokenStream(lexer)
        val parser: KSPParser = KSPParser(tokenStream)
        parser.compilationUnit()
    }
}
