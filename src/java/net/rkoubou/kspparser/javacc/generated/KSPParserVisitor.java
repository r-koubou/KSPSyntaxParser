/* Generated By:JavaCC: Do not edit this line. KSPParserVisitor.java Version 6.0_1 */
package net.rkoubou.kspparser.javacc.generated;

public interface KSPParserVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTRootNode node, Object data);
  public Object visit(ASTVariableDeclaration node, Object data);
  public Object visit(ASTVariableInitializer node, Object data);
  public Object visit(ASTPrimitiveInititalizer node, Object data);
  public Object visit(ASTArrayInitializer node, Object data);
  public Object visit(ASTCallbackDeclaration node, Object data);
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
  public Object visit(ASTContinueStatement node, Object data);
  public Object visit(ASTCallUserFunctionStatement node, Object data);
  public Object visit(ASTAssignment node, Object data);
  public Object visit(ASTConditionalOr node, Object data);
  public Object visit(ASTConditionalAnd node, Object data);
  public Object visit(ASTStrAdd node, Object data);
  public Object visit(ASTBitwiseOr node, Object data);
  public Object visit(ASTBitwiseAnd node, Object data);
  public Object visit(ASTEqual node, Object data);
  public Object visit(ASTNotEqual node, Object data);
  public Object visit(ASTLT node, Object data);
  public Object visit(ASTGT node, Object data);
  public Object visit(ASTLE node, Object data);
  public Object visit(ASTGE node, Object data);
  public Object visit(ASTAdd node, Object data);
  public Object visit(ASTSub node, Object data);
  public Object visit(ASTMul node, Object data);
  public Object visit(ASTDiv node, Object data);
  public Object visit(ASTMod node, Object data);
  public Object visit(ASTNeg node, Object data);
  public Object visit(ASTNot node, Object data);
  public Object visit(ASTLogicalNot node, Object data);
  public Object visit(ASTLiteral node, Object data);
  public Object visit(ASTRefVariable node, Object data);
  public Object visit(ASTArrayIndex node, Object data);
  public Object visit(ASTCallCommand node, Object data);
  public Object visit(ASTCommandArgumentList node, Object data);
}
/* JavaCC - OriginalChecksum=b3f5b704485b67ef7781bba515e19595 (do not edit this line) */
