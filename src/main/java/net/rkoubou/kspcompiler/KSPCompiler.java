package net.rkoubou.kspcompiler;

import net.rkoubou.kspcompiler.generated.antlr.KSPParserBaseListener;
import org.antlr.v4.runtime.*;

import net.rkoubou.kspcompiler.generated.antlr.KSPLexer;
import net.rkoubou.kspcompiler.generated.antlr.KSPParser;
import net.rkoubou.kspcompiler.generated.antlr.KSPParser.CompilationUnitContext;

public class KSPCompiler
{
    public static void main(String[] args) throws Throwable
    {
        System.out.println( args[0] );
        SemanticAnalyzer analyzer = new SemanticAnalyzer( new KSPParserBaseListener() );
        analyzer.analyze( args[0] );
    }
}