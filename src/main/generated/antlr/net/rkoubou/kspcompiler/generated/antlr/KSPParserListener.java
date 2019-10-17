// Generated from KSPParser.g4 by ANTLR 4.7.2
package net.rkoubou.kspcompiler.generated.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link KSPParser}.
 */
public interface KSPParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link KSPParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(KSPParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link KSPParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(KSPParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link KSPParser#callbackDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterCallbackDeclaration(KSPParser.CallbackDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link KSPParser#callbackDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitCallbackDeclaration(KSPParser.CallbackDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link KSPParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(KSPParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link KSPParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(KSPParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link KSPParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(KSPParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link KSPParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(KSPParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link KSPParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(KSPParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link KSPParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(KSPParser.StatementContext ctx);
}