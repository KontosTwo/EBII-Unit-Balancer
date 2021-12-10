grammar Edu;

// Literals

literal
	:	DecimalLiteral
	|	StringLiteral
	;

DecimalLiteral
    : DecimalDigits
    | '.' DecimalDigits
    | DecimalDigits '.' DecimalDigits
    ;

fragment
DecimalDigits
    : DecimalDigit+
    ;

fragment
DecimalDigit
    :   [0-9]
    ;

StringLiteral
	:	StringCharacter+
	;

fragment
StringCharacter
	:	[a-z]
	|   '_'
	|   [A-Z]
	;

// Skip whitespaces
WS : [ \t\r\n]+ -> skip;

// Keywords

// Keywords operators
ASSIGN : '=';
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';
TERNTHEN : '?';
TERNELSE : ':';

// Keywords separators
LPAREN : '(';
RPAREN : ')';
SEMI : ';';

// Keywords functional
KEEP : 'keep';


// Keywords stats
MASS : 'mass';
MORALE : 'morale';



// Lexer

configuration
    :   assignment+
    ;

assignment
    :   assignmentVar ASSIGN expression SEMI
    ;

assignmentVar
    :   StringLiteral
    ;

var
    :   StringLiteral
    ;

number
    :   DecimalLiteral
    ;

expression
    :   additiveExpression
    |   ternExpression
    ;

ternExpression
    :   ternIf TERNTHEN ternThen TERNELSE ternElse
    ;

ternIf
    : additiveExpression
    ;

ternThen
    : additiveExpression
    ;

ternElse
    : additiveExpression
    ;

additiveExpression
    :   multiplicativeExpression
	|	additiveExpression ADD multiplicativeExpression
	|	additiveExpression SUB multiplicativeExpression
	;

multiplicativeExpression
	:   value
    |	multiplicativeExpression MUL value
    |	multiplicativeExpression DIV value
    ;

value
    :
    | LPAREN expression RPAREN
    | var
    | number
    ;