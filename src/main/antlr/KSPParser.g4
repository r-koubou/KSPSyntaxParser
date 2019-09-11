/* =========================================================================

    KSPParser.g4
    Copyright (c) R-Koubou

   ======================================================================== */

parser grammar KSPParser;

options {
    tokenVocab = KSPLexer;
}

compilationUnit
    : callbackDeclaration
    | EOL;

//
// コールバック本体
//
callbackDeclaration:
    ON (MULTI_LINE_DELIMITER)* IDENTIFIER (MULTI_LINE_DELIMITER)*
    END (MULTI_LINE_DELIMITER)* ON
;