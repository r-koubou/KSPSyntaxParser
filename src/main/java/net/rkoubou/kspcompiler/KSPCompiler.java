package net.rkoubou.kspcompiler;

import org.antlr.v4.runtime.*;

import net.rkoubou.kspcompiler.generated.antlr.KSPLexer;
import net.rkoubou.kspcompiler.generated.antlr.KSPParser;
import net.rkoubou.kspcompiler.generated.antlr.KSPParser.CompilationUnitContext;

public class KSPCompiler
{
    public static void main(String[] args) throws Throwable
    {
        CharStream stream = CharStreams.fromFileName(args[0]);
        KSPLexer lexer = new KSPLexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        KSPParser parser = new KSPParser(tokenStream);
    }
}