grammar CalculatorGrammar;

/**
 * Parser rules
 */

program
    : (statements+=statement)* EOF
    ;

statement
    : varName=IDENTIFIER ASSIGN expr=powExpr LINE_BREAK    # assignStmt
    | expr=powExpr LINE_BREAK                              # exprStmt
    ;

powExpr
    : left=powExpr op=POW right=negExpr                    # powExprBin
    | expr=negExpr                                         # powExprSingle
    ;

negExpr
    : MINUS expr=addExpr
    | expr=addExpr
    ;

addExpr
    : left=addExpr op=(PLUS | MINUS) right=multiplyExpr    # addExprBin
    | expr=multiplyExpr                                    # addExprSingle
    ;

multiplyExpr
    : left=multiplyExpr op=(MULTIPLY | DIVIDE) right=atomExpr   # multExprBin
    | expr=atomExpr                                             # multExprSingle
    ;

atomExpr
    : value=NUMBER                                      # number
    | rawText=STRING                                    # rawString
    | varName=IDENTIFIER                                # variable
    | funcName=IDENTIFIER LPAREN args=arglist RPAREN    # funcName
    | LPAREN expr=powExpr RPAREN                        # parenExpr
    ;

arglist
    : (values+=powExpr (COMMA values+=powExpr)*)?
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

