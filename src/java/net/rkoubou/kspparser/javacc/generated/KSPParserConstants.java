/* Generated By:JJTree&JavaCC: Do not edit this line. KSPParserConstants.java */
package net.rkoubou.kspparser.javacc.generated;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface KSPParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int MULTI_LINE_COMMENT = 5;
  /** RegularExpression Id. */
  int INTEGER_LITERAL = 7;
  /** RegularExpression Id. */
  int DECIMAL_LITERAL = 8;
  /** RegularExpression Id. */
  int HEX_LITERAL = 9;
  /** RegularExpression Id. */
  int REAL_LITERAL = 10;
  /** RegularExpression Id. */
  int STRING_LITERAL = 11;
  /** RegularExpression Id. */
  int EOL = 12;
  /** RegularExpression Id. */
  int CR = 13;
  /** RegularExpression Id. */
  int LF = 14;
  /** RegularExpression Id. */
  int MULTI_LINE_DELIMITER = 15;
  /** RegularExpression Id. */
  int DECLARE = 16;
  /** RegularExpression Id. */
  int CONST = 17;
  /** RegularExpression Id. */
  int POLYPHONIC = 18;
  /** RegularExpression Id. */
  int ON = 19;
  /** RegularExpression Id. */
  int END = 20;
  /** RegularExpression Id. */
  int FUNCTION = 21;
  /** RegularExpression Id. */
  int IF = 22;
  /** RegularExpression Id. */
  int ELSE = 23;
  /** RegularExpression Id. */
  int SELECT = 24;
  /** RegularExpression Id. */
  int CASE = 25;
  /** RegularExpression Id. */
  int TO = 26;
  /** RegularExpression Id. */
  int WHILE = 27;
  /** RegularExpression Id. */
  int VARIABLE_INT = 28;
  /** RegularExpression Id. */
  int VARIABLE_INT_ARRAY = 29;
  /** RegularExpression Id. */
  int VARIABLE_REAL = 30;
  /** RegularExpression Id. */
  int VARIABLE_REAL_ARRAY = 31;
  /** RegularExpression Id. */
  int VARIABLE_STRING = 32;
  /** RegularExpression Id. */
  int VARIABLE_STRING_ARRAY = 33;
  /** RegularExpression Id. */
  int BOOL_GT = 34;
  /** RegularExpression Id. */
  int BOOL_LT = 35;
  /** RegularExpression Id. */
  int BOOL_GE = 36;
  /** RegularExpression Id. */
  int BOOL_LE = 37;
  /** RegularExpression Id. */
  int BOOL_EQ = 38;
  /** RegularExpression Id. */
  int BOOL_NE = 39;
  /** RegularExpression Id. */
  int BOOL_NOT = 40;
  /** RegularExpression Id. */
  int BOOL_AND = 41;
  /** RegularExpression Id. */
  int BOOL_OR = 42;
  /** RegularExpression Id. */
  int ASSIGN = 43;
  /** RegularExpression Id. */
  int PLUS = 44;
  /** RegularExpression Id. */
  int MINUS = 45;
  /** RegularExpression Id. */
  int MUL = 46;
  /** RegularExpression Id. */
  int DIV = 47;
  /** RegularExpression Id. */
  int MOD = 48;
  /** RegularExpression Id. */
  int BIT_AND = 49;
  /** RegularExpression Id. */
  int BIT_OR = 50;
  /** RegularExpression Id. */
  int BIT_NOT = 51;
  /** RegularExpression Id. */
  int STRING_ADD = 52;
  /** RegularExpression Id. */
  int LPAREN = 53;
  /** RegularExpression Id. */
  int RPAREN = 54;
  /** RegularExpression Id. */
  int LBRACKET = 55;
  /** RegularExpression Id. */
  int RBRACKET = 56;
  /** RegularExpression Id. */
  int COMMA = 57;
  /** RegularExpression Id. */
  int IDENTIFIER = 58;
  /** RegularExpression Id. */
  int LETTER = 59;
  /** RegularExpression Id. */
  int PART_LETTER = 60;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_MULTI_LINE_COMMENT = 1;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\f\"",
    "\"{\"",
    "\"}\"",
    "<token of kind 6>",
    "<INTEGER_LITERAL>",
    "<DECIMAL_LITERAL>",
    "<HEX_LITERAL>",
    "<REAL_LITERAL>",
    "<STRING_LITERAL>",
    "<EOL>",
    "\"\\r\"",
    "\"\\n\"",
    "<MULTI_LINE_DELIMITER>",
    "\"declare\"",
    "\"const\"",
    "\"polyphonic\"",
    "\"on\"",
    "\"end\"",
    "\"function\"",
    "\"if\"",
    "\"else\"",
    "\"select\"",
    "\"case\"",
    "\"to\"",
    "\"while\"",
    "<VARIABLE_INT>",
    "<VARIABLE_INT_ARRAY>",
    "<VARIABLE_REAL>",
    "<VARIABLE_REAL_ARRAY>",
    "<VARIABLE_STRING>",
    "<VARIABLE_STRING_ARRAY>",
    "\">\"",
    "\"<\"",
    "\">=\"",
    "\"<=\"",
    "\"=\"",
    "\"#\"",
    "\"not\"",
    "\"and\"",
    "\"or\"",
    "\":=\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"mod\"",
    "\".and.\"",
    "\".or.\"",
    "\".not.\"",
    "\"&\"",
    "\"(\"",
    "\")\"",
    "\"[\"",
    "\"]\"",
    "\",\"",
    "<IDENTIFIER>",
    "<LETTER>",
    "<PART_LETTER>",
  };

}
