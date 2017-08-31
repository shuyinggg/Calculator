grammar CalculatorGrammar;

@header {
    package calculator.parser.grammar;
}

/**
 * Parser rules
 */

program
    : (statements+=statement)* EOF
    ;

statement
    : varName=IDENTIFIER ASSIGN expr=addExpr LINE_BREAK    # assignStmt
    | expr=addExpr LINE_BREAK                              # exprStmt
    ;

addExpr
    : left=addExpr op=(PLUS | MINUS) right=multiplyExpr    # addExprBin
    | expr=multiplyExpr                                    # addExprSingle
    ;

multiplyExpr
    : left=multiplyExpr op=(MULTIPLY | DIVIDE) right=negExpr    # multExprBin
    | expr=negExpr                                              # multExprSingle
    ;

negExpr
    : MINUS expr=negExpr                                   # negExprUnary
    | expr=powExpr                                         # negExprSingle
    ;

// The exponentiation operator is right-associative
powExpr
    : left=atomExpr op=POW right=powExpr                   # powExprBin
    | expr=atomExpr                                        # powExprSingle
    ;


atomExpr
    : value=NUMBER                                      # number
    | rawText=STRING                                    # rawString
    | varName=IDENTIFIER                                # variable
    | funcName=IDENTIFIER LPAREN args=arglist RPAREN    # funcName
    | LPAREN expr=addExpr RPAREN                        # parenExpr
    ;

arglist
    : (values+=addExpr (COMMA values+=addExpr)*)?
    ;

/**
 * Lexer rules
 */

// We say a line ends when we encounter any combination of the '\n' and '\r' characters
LINE_BREAK
    : [\n\r]+
    ;

// Ignore and skip all whitespace in our language.
WS
    : [ \t]+ -> skip
    ;

// We ignore comments as well.
COMMENT
    : '#' ~[\r\n]* -> skip
    ;

// We use the same rule for identifiers as Java.
IDENTIFIER
    : [a-zA-Z_][a-zA-Z_0-9]*
    ;

// A number is a bunch of digits, followed optionally by some decimals.
NUMBER
    : DIGIT+ ([.] DIGIT+)?
    ;

// A digit is the characters 0 through 0.
fragment DIGIT
    : [0-9]
    ;

// We define our string to be identical to Java's strings. However, we need to
// do some extra work to account for things like escape sequences and whitespace.
STRING
    : '"' STRING_CHAR* '"'
    ;

fragment STRING_CHAR
    : ~('"' | '\\' | '\r' | '\n') | '\\' ('"' | '\\')
    ;

ASSIGN      : ':=' ;

PLUS        : '+' ;
MINUS       : '-' ;
MULTIPLY    : '*' ;
DIVIDE      : '/' ;
POW         : '^' ;

LPAREN      : '(' ;
RPAREN      : ')' ;

COMMA       : ',' ;

