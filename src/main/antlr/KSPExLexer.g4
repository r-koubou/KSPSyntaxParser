/* =========================================================================

    KSPExLexer.g4
    Copyright (c) R-Koubou

   ======================================================================== */

// Extended feature from KSP language specification.

lexer grammar KSPExLexer;
import KSPLexer;

//------------------------------------------------------------------------------
// Keyword
//------------------------------------------------------------------------------


//------------------------------------------------------------------------------
// Literal
//------------------------------------------------------------------------------
INTEGER_LITERAL
    : DECIMAL_LITERAL
    | HEX_LITERAL
    | EXT_HEX_LITERAL
    | EXT_BIN_LITERAL;

fragment DECIMAL_LITERAL:
    '1'..'9' '0'..'9'*;

fragment HEX_LITERAL:
    '9' ('0'..'9' | 'a'..'f' | 'A'..'F')+ ('h' | 'H');

fragment EXT_HEX_LITERAL:
    '0x' ('0'..'9' | 'a'..'f' | 'A'..'F')+;

fragment EXT_BIN_LITERAL:
    '0b' [01]+;
