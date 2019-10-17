/* =========================================================================

    KSPParser.g4
    Copyright (c) R-Koubou

   ======================================================================== */

parser grammar KSPParser;

options {
    tokenVocab = KSPLexer;
}

//
// ルート
//
compilationUnit:
(
      callbackDeclaration
    | EOL
    | MULTI_LINE_DELIMITER
)*
;

//
// コールバック本体
//
callbackDeclaration:
    ON (MULTI_LINE_DELIMITER)* IDENTIFIER (MULTI_LINE_DELIMITER)*
    EOL
    block
    END (MULTI_LINE_DELIMITER)* ON
;

//
// コードブロック
//
block:
    (blockStatement)*
;

blockStatement:
    statement
;


//
// ステートメント
//
statement:
      MULTI_LINE_DELIMITER
    | EOL
;
