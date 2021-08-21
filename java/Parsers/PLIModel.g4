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

g_list: list | var_ids | exps;

indx : index_name=ID 'in' li=exp '..' ls=exp ; 

indexed_elem : constraint | bound | generate_exp  ; 

constraint : generate_exp rel_op exp #atomConstraint 
		| var_id '=' values=INT '->' constraint #indicatorConstraint 
		| var_id '==' generate_exp #equalsConstraint 
		| 'or' '(' rel_op n=INT ',' constraint ('|' constraint )+ ')' #orConstraint 
		| left=constraint '=>' right=constraint #implyConstraint 
		| left=var_id '!=' right=var_id #differentValueConstraint 
		| 'allDifferent' '(' vars=g_list ')' #allDifferentValuesConstraint 
		| 'allDifferentInValues' '(' vars=g_list ';' values=g_list ')' #allInValuesConstraint 
		| var=var_id 'in' values=list #valueInValuesConstraint 
		| left=var_id '=' 'MAX' '(' vars=var_ids ')' #maxConstraint 
		| left=var_id '=' 'MIN' '(' vars=var_ids ')' #minConstraint 
		| left=var_id '=' 'OR' '(' vars=var_ids ')' #orBinConstraint 
		| left=var_id '=' 'AND' '(' vars=var_ids ')' #andBinConstraint 
		| left=var_id '=' 'ABS' '(' right=var_id ')' #absConstraint 
		| left=var_id '=' 'PWL' '(' right=var_id ')'  ':' data= pair+ #piecewiseConstraint 
		; 
						
pair : '(' INT ',' INT ')' ;	

generate_exp : factor s_factor* #factorGenerateExp 
  		| 'sum' '(' list ')' s_factor* #sumGenerateExp ; 
  		
s_factor : '+' factor #plusFactor 
		| '-' factor #minusFactor 
		| '+' 'sum' '(' list ')' #plusSum 
		| '-' 'sum' '(' list ')' #minusSum 
		;
			
factor : exp var_id #varFactor 
	   | exp #expFactor
	   | var_id #varIdFactor 
	   ;
	   
var_id : name=ID '[' index_var_id? ']'; 

var_ids : var_id (',' var_id);

index_var_id : exp (',' exp )*  ; 

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

 call_function : name=ID '(' exps? ')' ; 
 
 exps : exp (',' exp )* ; 
 
 rel_op : '>=' | '>' | '<=' | '<' | '=' ; 
 
 ID : ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')* ; 
 
 INT : ('0'..'9')+ ;
 
 DOUBLE : INT '.' INT? ; 
 
 WS : [ \t\r\n]+ -> skip ; 