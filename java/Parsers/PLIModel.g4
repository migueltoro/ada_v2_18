grammar PLIModel;

model : head? goal constraints bounds? bin_vars? int_vars? free_vars? semi_continuous_vars?;

head : 'head section' declaration* ; 

declaration : type=ID name=ID '=' val=exp #varDeclar 
 		| type=ID name=ID '(' formal_parameters? ')' #funDeclar ; 

formal_parameters : formal_parameter (',' formal_parameter )* ; 

formal_parameter : type=ID name=ID? ; 

goal : 'goal section' obj=('min'|'max') generate_exp #goalSection ; 

constraints : 'constraints section' list+ ;

list : indexed_elem (',' indx)* ('|' exp)? ; 

indx : index_name=ID 'in' li=exp '..' ls=exp ; 

indexed_elem : constraint | bound | generate_exp ; 

constraint : generate_exp rel_op exp #atomConstraint 
		| var_id '->' constraint #indicatorConstraint 
		| 'or' '(' rel_op n=INT ',' constraint ('|' constraint )+ ')' #orConstraint 
		| left=constraint '=>' right=constraint #implyConstraint 
		| left=var_id '!=' right=var_id #differentValueConstraint 
		| 'allDifferent' '(' vars=list ')' #allDifferentValuesConstraint 
		| 'allDifferentInValues' '(' vars=list ';' values=exp+ ')' #allDifferentInValuesConstraint 
		| left=var_id '=' 'MAX' '(' vars=list ')' #maxConstraint 
		| left=var_id '=' 'MIN' '(' vars=list ')' #minConstraint 
		| left=var_id '=' 'OR' '(' vars=list ')' #orBinConstraint 
		| left=var_id '=' 'AND' '(' vars=list ')' #andBinConstraint 
		| left=var_id '=' 'ABS' '(' right=var_id ')' #absConstraint 
		| left=var_id '=' 'PWL' '(' right=var_id ')'  ':' data= pair+ #piecewiseConstraint 
		; 
		
pair : '(' INT ',' INT ')' ;	

generate_exp : factor s_factor* #factorGenerateExp 
  		| 'sum' '(' list ')' s_factor* #sumGenerateExp ; 
  		
s_factor : '+' factor #plusFactor 
		| '+' 'sum' '(' list ')' #plusSum 
		| '-' 'sum' '(' list ')' #minusSum 
		| '-' factor #minusFactor ;
		
factor : exp? var_id #varFactor; 
	   
var_id : name=ID index_var_id ; 

index_var_id : ('[' exp (',' exp )* ']')? ; 

bound : name=var_id op=rel_op exp #oneSideBound 
 	  |li=exp '<=' name=var_id '<=' ls=exp #twoSideBound ; 
 		
bounds : 'bounds section' list+ ; 
 
bin_vars : 'bin' list+ ; 

int_vars : 'int' list+ ; 

free_vars : 'free' list+ ; 

semi_continuous_vars: 'semi-continuous' list+ ; 

exp : op='(int)' right=exp #unaryOpExpr 
		| op='(double)' right=exp #unaryOpExpr 
		| op=('+'|'-' |'!') right=exp #unaryOpExpr 
		| left=exp op=('*'|'/'|'%') right=exp #opExpr 
		| left=exp op=('+'|'-') right=exp #opExpr 
		| left=exp op=('<='|'<'|'>='|'>') right=exp #opExpr 
		| left=exp op=('!='|'=') right=exp #opExpr 
		| left=exp op=('&&'|'||') right=exp #opExpr 
		| '(' exp ')' #parenExpr 
		| call_function #funExpr 
		| id=ID #idExpr 
		| DOUBLE #doubleExp 
		| INT #intExpr ; 
 
 call_function : name=ID '(' real_parameters? ')' ; 
 
 real_parameters : exp (',' exp )* ; 
 
 rel_op : '>=' | '>' | '<=' | '<' | '=' ; 
 
 ID : ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')* ; 
 
 INT : ('0'..'9')+ ;
 
 DOUBLE : INT '.' INT? ; 
 
 WS : [ \t\r\n]+ -> skip ; 