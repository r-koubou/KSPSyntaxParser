/* Generated By:JJTree: Do not edit this line. ASTCallbackDeclaration.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=net.rkoubou.kspparser.javacc.ASTKSPNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package net.rkoubou.kspparser.javacc.generated;

public
class ASTCallbackDeclaration extends SimpleNode {
  public ASTCallbackDeclaration(int id) {
    super(id);
  }

  public ASTCallbackDeclaration(KSPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(KSPParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=6be2191a7aa887c88639d0722e85a1e3 (do not edit this line) */