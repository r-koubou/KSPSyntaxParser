/* =========================================================================

    KSPLexer.g4
    Copyright (c) R-Koubou

   ======================================================================== */

lexer grammar KSPLexer;

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

MULTI_LINE_DELIMITER:
    '...' [ \t]* EOL;

//------------------------------------------------------------------------------
// Skip: Whitespace / Comment
//------------------------------------------------------------------------------

Whitespace:
    [ \t\f]+ -> skip;

BlockComment:
    '{' .*? '}' -> skip;

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
// Preprocessor (KSP standard)
//------------------------------------------------------------------------------

    PREPROCESSOR_SET_COND:      'SET_CONDITION';
    PREPROCESSOR_RESET_COND:    'RESET_CONDITION';
    PREPROCESSOR_CODE_IF:       'USE_CODE_IF';
    PREPROCESSOR_CODE_IF_NOT:   'USE_CODE_IF_NOT';
    PREPROCESSOR_CODE_END_IF:   'END_USE_CODE';

//------------------------------------------------------------------------------
// Operator
//------------------------------------------------------------------------------

    // 比較演算子
    BOOL_GT:    '>';
    BOOL_LT:    '<';
    BOOL_GE:    '>=';
    BOOL_LE:    '<=';
    BOOL_EQ:    '=';
    //TODO in_rangeはコマンドなので演算子扱いしない
    BOOL_NE:    '#';
    BOOL_NOT:   'not';
    BOOL_AND:   'and';
    BOOL_OR:    'or';
    // 算術演算
    ASSIGN:     ':=';
    PLUS:       '+';
    MINUS:      '-';
    MUL:        '*';
    DIV:        '/';
    MOD:        'mod';
    BIT_AND:    '.and.';
    BIT_OR:     '.or.';
    BIT_NOT:    '.not.';
    //TODO ビットシフトはコマンドなので演算子扱いしない
    // 文字列連結
    STRING_ADD: '&';

//------------------------------------------------------------------------------
// Literal
//------------------------------------------------------------------------------

INTEGER_LITERAL
    : DECIMAL_LITERAL
    | HEX_LITERAL;

fragment DECIMAL_LITERAL:
    '1'..'9' '0'..'9'*;

fragment HEX_LITERAL:
    '9' ('0'..'9' | 'a'..'f' | 'A'..'F')+ ('h' | 'H');

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

