grammar edu;

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
	:	'"' StringCharacters '"'
	;

fragment
StringCharacters
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
    :   var ASSIGN expression
    ;

var
    :   StringLiteral
    ;

expression
    :   additiveExpression
    |   LPAREN expression RPAREN
    ;

additiveExpression
	:	multiplicativeExpression
	|	additiveExpression '+' multiplicativeExpression
	|	additiveExpression '-' multiplicativeExpression
	;

multiplicativeExpression
	:	value
	|   multiplicativeExpression '*' value
	|	multiplicativeExpression '/' value
	;


value
    : var
    | DecimalLiteral
    ;