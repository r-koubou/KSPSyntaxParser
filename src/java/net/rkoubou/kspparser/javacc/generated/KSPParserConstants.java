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
  int CALL = 28;
  /** RegularExpression Id. */
  int CONTINUE = 29;
  /** RegularExpression Id. */
  int PREPROCESSOR_SET_COND = 30;
  /** RegularExpression Id. */
  int PREPROCESSOR_RESET_COND = 31;
  /** RegularExpression Id. */
  int PREPROCESSOR_CODE_IF = 32;
  /** RegularExpression Id. */
  int PREPROCESSOR_CODE_IF_NOT = 33;
  /** RegularExpression Id. */
  int PREPROCESSOR_CODE_END_IF = 34;
  /** RegularExpression Id. */
  int VARIABLE_INT = 35;
  /** RegularExpression Id. */
  int VARIABLE_INT_ARRAY = 36;
  /** RegularExpression Id. */
  int VARIABLE_REAL = 37;
  /** RegularExpression Id. */
  int VARIABLE_REAL_ARRAY = 38;
  /** RegularExpression Id. */
  int VARIABLE_STRING = 39;
  /** RegularExpression Id. */
  int VARIABLE_STRING_ARRAY = 40;
  /** RegularExpression Id. */
  int BOOL_GT = 41;
  /** RegularExpression Id. */
  int BOOL_LT = 42;
  /** RegularExpression Id. */
  int BOOL_GE = 43;
  /** RegularExpression Id. */
  int BOOL_LE = 44;
  /** RegularExpression Id. */
  int BOOL_EQ = 45;
  /** RegularExpression Id. */
  int BOOL_NE = 46;
  /** RegularExpression Id. */
  int BOOL_NOT = 47;
  /** RegularExpression Id. */
  int BOOL_AND = 48;
  /** RegularExpression Id. */
  int BOOL_OR = 49;
  /** RegularExpression Id. */
  int ASSIGN = 50;
  /** RegularExpression Id. */
  int PLUS = 51;
  /** RegularExpression Id. */
  int MINUS = 52;
  /** RegularExpression Id. */
  int MUL = 53;
  /** RegularExpression Id. */
  int DIV = 54;
  /** RegularExpression Id. */
  int MOD = 55;
  /** RegularExpression Id. */
  int BIT_AND = 56;
  /** RegularExpression Id. */
  int BIT_OR = 57;
  /** RegularExpression Id. */
  int BIT_NOT = 58;
  /** RegularExpression Id. */
  int STRING_ADD = 59;
  /** RegularExpression Id. */
  int LPAREN = 60;
  /** RegularExpression Id. */
  int RPAREN = 61;
  /** RegularExpression Id. */
  int LBRACKET = 62;
  /** RegularExpression Id. */
  int RBRACKET = 63;
  /** RegularExpression Id. */
  int COMMA = 64;
  /** RegularExpression Id. */
  int IDENTIFIER = 65;
  /** RegularExpression Id. */
  int LETTER = 66;
  /** RegularExpression Id. */
  int PART_LETTER = 67;

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
    "\"call\"",
    "\"continue\"",
    "\"SET_CONDITION\"",
    "\"RESET_CONDITION\"",
    "\"USE_CODE_IF\"",
    "\"USE_CODE_IF_NOT\"",
    "\"END_USE_CODE\"",
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
