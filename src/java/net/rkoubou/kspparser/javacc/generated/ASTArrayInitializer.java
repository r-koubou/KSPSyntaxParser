/* Generated By:JJTree: Do not edit this line. ASTArrayInitializer.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=net.rkoubou.kspparser.javacc.ASTKSPNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package net.rkoubou.kspparser.javacc.generated;

public
class ASTArrayInitializer extends SimpleNode {

  /** トークン ":="" を含んでいたかどうか。UIの初期化子の場合分け用 */
  public boolean hasAssign;

  public ASTArrayInitializer(int id) {
    super(id);
  }

  public ASTArrayInitializer(KSPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(KSPParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b6c1cb377a57e7bb86713244763cae2a (do not edit this line) */
