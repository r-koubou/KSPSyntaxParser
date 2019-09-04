/* =========================================================================

    KSPLexer.g4
    Copyright (c) R-Koubou

   ======================================================================== */

lexer grammar KSPLexer;

@header {
    package net.rkoubou.kspcompiler.generated.antlr;
}

// Note: Token which prefix is 'EXT_', it is extended feature from pure KSP language specification.

//------------------------------------------------------------------------------
// Keyword (KSP standard)
//------------------------------------------------------------------------------

    // Variable
    DECLARE:                'declare';
    CONST:                  'const';
    POLYPHONIC:             'polyphonic';
    // Callback
    ON:                     'on';
    END:                    'end';
    FUNCTION:               'function';
    // Statement
    IF:                     'if';
    ELSE:                   'else';
    SELECT:                 'select';
    CASE:                   'case';
    TO:                     'to';
    WHILE:                  'while';
    CALL:                   'call';

//------------------------------------------------------------------------------
// Keyword (extended)
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
// Preprocessor (KSP standard)
//------------------------------------------------------------------------------

    PREPROCESSOR_SET_COND:      'SET_CONDITION';
    PREPROCESSOR_RESET_COND:    'RESET_CONDITION';
    PREPROCESSOR_CODE_IF:       'USE_CODE_IF';
    PREPROCESSOR_CODE_IF_NOT:   'USE_CODE_IF_NOT';
    PREPROCESSOR_CODE_END_IF:   'END_USE_CODE';

//------------------------------------------------------------------------------
// Preprocessor (extended)
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

STRING_LITERAL:
    '"'
        (~["\\\r\n] | EscapeSequence)*
    '"';

fragment EscapeSequence:
    '\\' [btnfr"'\\];


//------------------------------------------------------------------------------
// Identifier
//------------------------------------------------------------------------------

IDENTIFIER: LETTER LETTER_OR_DIGIT*;

fragment LETTER:
    [a-zA-Z_];

fragment LETTER_OR_DIGIT
    : LETTER
    | [0-9];

//------------------------------------------------------------------------------
// Newline
// KSP require Newline token for parsing
//------------------------------------------------------------------------------

EOL
    : CR LF
    | LF
    | MULTI_LINE_DELIMITER;

fragment CR : '\r';
fragment LF : '\n';
fragment MULTI_LINE_DELIMITER:
    '...' [ \t]* EOL;



//------------------------------------------------------------------------------
// Skip: Whitespace / Comment
//------------------------------------------------------------------------------

Whitespace:
    [ \t\f]+ -> skip;

LineComment:
    '{' .*? '}' -> skip;
