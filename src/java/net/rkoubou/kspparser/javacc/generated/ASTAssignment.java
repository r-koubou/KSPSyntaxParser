/* Generated By:JJTree: Do not edit this line. ASTAssignment.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=net.rkoubou.kspparser.javacc.ASTKSPNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package net.rkoubou.kspparser.javacc.generated;

public
class ASTAssignment extends SimpleNode {
  public ASTAssignment(int id) {
    super(id);
  }

  public ASTAssignment(KSPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(KSPParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4d2538e2e3bec3a35d874848c6d06225 (do not edit this line) */
