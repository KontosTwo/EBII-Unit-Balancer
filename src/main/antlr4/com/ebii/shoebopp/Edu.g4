grammar Edu;

// Literals

literal
	:	DecimalLiteral
	|	StringLiteral
	;

DecimalLiteral
    : DecimalDigits '.' DecimalDigits
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
    :   var ASSIGN expression SEMI
    ;

var
    :   StringLiteral
    ;

number
    :   DecimalLiteral
    ;

expression
    :   subtractiveExpression
    |   ternExpression
    ;

ternExpression
    :   subtractiveExpression TERNTHEN expression TERNELSE expression
    ;

subtractiveExpression
	:	additiveExpression
	|	subtractiveExpression '-' additiveExpression
	;

additiveExpression
	:	divisiveExpression
	|	additiveExpression '+' divisiveExpression
	;

divisiveExpression
	:	multiplicativeExpression
	|	divisiveExpression '/' multiplicativeExpression
	;

multiplicativeExpression
	:	value
	|   multiplicativeExpression '*' expression
	;


value
    : var
    | number
    ;