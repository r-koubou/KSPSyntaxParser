/* Generated By:JavaCC: Do not edit this line. KSPParserDefaultVisitor.java Version 6.0_1 */
package net.rkoubou.kspparser.javacc.generated;

public class KSPParserDefaultVisitor implements KSPParserVisitor{
  public Object defaultVisit(SimpleNode node, Object data){
    node.childrenAccept(this, data);
    return data;
  }
  public Object visit(SimpleNode node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTRootNode node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTVariable node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTVariableDeclaration node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTCallbackDeclaration node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTCallbackArgument node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTCallbackArgumentList node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTUserFunctionDeclaration node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTBlock node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTPreProcessorStatement node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTIfStatement node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTSelectStatement node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTCaseStatement node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTCaseCondition node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTWhileStatement node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTAssignment node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTConditionalOr node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTConditionalAnd node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTInclusiveOr node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTAnd node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTEqual node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTNotEqual node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTLT node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTGT node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTLE node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTGE node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTAdd node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTSub node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTStrAdd node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTMul node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTDiv node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTMod node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTNeg node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTNot node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTLogicalNot node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTLiteral node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTRefVariable node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTCallCommand node, Object data){
    return defaultVisit(node, data);
  }
}
/* JavaCC - OriginalChecksum=2fc671ebacbf2425a2a31d4643a0c8bd (do not edit this line) */
