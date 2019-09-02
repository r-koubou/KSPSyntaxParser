/* =========================================================================

    KSP.g4
    Copyright (c) R-Koubou

   ======================================================================== */

grammar KSP;

@header {
    package net.rkoubou.kspcompiler.generated.antlr;
}

prog: expr;
expr: term (('+'|'-') term)*;
term: factor (('*'|'/') factor)*;
factor: INT
    | '(' expr ')'
    ;
INT     : [0-9]+ ;
