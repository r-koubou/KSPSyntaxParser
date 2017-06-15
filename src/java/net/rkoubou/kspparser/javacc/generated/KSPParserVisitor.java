/* Generated By:JavaCC: Do not edit this line. KSPParserVisitor.java Version 6.0_1 */
package net.rkoubou.kspparser.javacc.generated;

public interface KSPParserVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTRootNode node, Object data);
  public Object visit(ASTVariableDeclaration node, Object data);
  public Object visit(ASTVariableDeclarator node, Object data);
  public Object visit(ASTCallbackDeclaration node, Object data);
  public Object visit(ASTCallbackArgument node, Object data);
  public Object visit(ASTCallbackArgumentList node, Object data);
  public Object visit(ASTUserFunctionDeclaration node, Object data);
  public Object visit(ASTBlock node, Object data);
  public Object visit(ASTPreProcessorDefine node, Object data);
  public Object visit(ASTPreProcessorUnDefine node, Object data);
  public Object visit(ASTPreProcessorIfDefined node, Object data);
  public Object visit(ASTPreProcessorIfUnDefined node, Object data);
  public Object visit(ASTIfStatement node, Object data);
  public Object visit(ASTSelectStatement node, Object data);
  public Object visit(ASTCaseStatement node, Object data);
  public Object visit(ASTCaseCondition node, Object data);
  public Object visit(ASTWhileStatement node, Object data);
  public Object visit(ASTAssignment node, Object data);
  public Object visit(ASTConditionalOr node, Object data);
  public Object visit(ASTConditionalAnd node, Object data);
  public Object visit(ASTInclusiveOr node, Object data);
  public Object visit(ASTAnd node, Object data);
  public Object visit(ASTEqual node, Object data);
  public Object visit(ASTNotEqual node, Object data);
  public Object visit(ASTLT node, Object data);
  public Object visit(ASTGT node, Object data);
  public Object visit(ASTLE node, Object data);
  public Object visit(ASTGE node, Object data);
  public Object visit(ASTAdd node, Object data);
  public Object visit(ASTSub node, Object data);
  public Object visit(ASTStrAdd node, Object data);
  public Object visit(ASTMul node, Object data);
  public Object visit(ASTDiv node, Object data);
  public Object visit(ASTMod node, Object data);
  public Object visit(ASTNeg node, Object data);
  public Object visit(ASTNot node, Object data);
  public Object visit(ASTLogicalNot node, Object data);
  public Object visit(ASTLiteral node, Object data);
  public Object visit(ASTRefVariable node, Object data);
  public Object visit(ASTCallCommand node, Object data);
}
/* JavaCC - OriginalChecksum=7dde99abca8830bc6169727269ec3e10 (do not edit this line) */
