grammar Program;

program : 'Declaraciones' declaration+ 'Codigo' block ; 

declaration : id=ID ':' type=TYPE ( '=' exp)? #varDeclaration 
			| id=ID '(' ')' ':' type=TYPE #funDeclarationSP 
			| id=ID '(' formal_parameters ')' ':' type=TYPE #funDeclaration ;
			
formal_parameters : formal_parameter ( ',' formal_parameter) * ; 

formal_parameter : (id=ID ':' type=TYPE)* ; 

sentence : id=ID '=' exp #asignSentence 
	     | 'if' '(' exp ')' trueBlock=block ('else' elseBlock= block )? #ifSentence 
	     | 'while' '(' exp ')' block #whileSentence;
	     
block: 	'{' sentence+ '}' ;    

exp : op='(int)' right=exp #unaryExpr 
	| op='(double)' right=exp #unaryExpr 
	| op=('+'|'-' |'!') right=exp #unaryExpr 
	| left=exp op=('*'|'/'|'%') right=exp #binaryExpr 
	| left=exp op=('+'|'-') right=exp #binaryExpr 
	| left=exp op=('<='|'<'|'>='|'>') right=exp #binaryExpr 
	| left=exp op=('!='|'=') right=exp #binaryExpr 
	| left=exp op=('&&'|'||') right=exp #binaryExpr 
	| '(' exp ')' #parenExpr 
	| name=ID '(' real_parameters? ')' #callExpr 
	| id=ID #idExpr | DOUBLE #doubleExp 
	| INT #intExpr ; 
	
real_parameters : exp (',' exp )* ; 

TYPE : 'Int' | 'Double' | 'String' 'Boolean' ; 

ID : ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')* ; 

INT : ('0'..'9')+ ; 

DOUBLE : INT '.' INT? ; 

WS : [ \t\r\n]+ -> skip ; 

