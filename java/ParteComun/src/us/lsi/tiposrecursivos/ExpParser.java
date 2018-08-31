package us.lsi.tiposrecursivos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import us.lsi.common.Maps2;
import us.lsi.common.Preconditions;
import us.lsi.regularexpressions.Tokenizer;
import us.lsi.regularexpressions.Tokenizer.TokenType;
import us.lsi.tiposrecursivos.Exp.ExpType;

public class ExpParser {

	public static Exp<Object> scan(String s) {
		Tokenizer tk = Tokenizer.create(s, Operator.functions, Operator.reservedWords);
		Map<String, Exp<Object>> vars = new HashMap<>();
		Exp<Object> r = ExpParser.scanExp(tk, vars);
		return r;
	}
	
	
	public static Exp<Object> scan(String s, Map<String, Exp<Object>> vars) {
		Tokenizer tk = Tokenizer.create(s, Operator.functions, Operator.reservedWords);
		Map<String, Exp<Object>> nVars = new HashMap<>(vars);
	    vars.values().stream().map(e->e.getVars()).forEach(m->nVars.putAll(m));		
	    Exp<Object> r = ExpParser.scanExp(tk, nVars);
		return r;
	}
	
	protected static Exp<Object> scanExp(Tokenizer tk, Map<String,Exp<Object>> vars){
		Exp<Object> r = scanSumando(tk,vars);
		String s;
		while (tk.hasMoreTokens() && 
				tk.seeNextTokenType().equals(TokenType.Operator) &&
				Operator.arities.get(tk.seeNextToken()) == 2) {
			s = tk.matchTokenTypes(TokenType.Operator);
			Exp<Object> exp = ExpParser.scanSumando(tk, vars);
			BinaryOperator<Object,Object,Object> bop = Operator.getBinary(s,r.getType(),exp.getType());
			r = Exp.binary(r,exp,bop);
		}
		return r;
	}
	
	protected static Exp<Object> scanSumando(Tokenizer tk, Map<String,Exp<Object>> vars){
		Exp<Object> r = scanFactor(tk,vars);
		String s;
		while (tk.hasMoreTokens() && 
				tk.seeNextTokenType().equals(TokenType.Operator) &&
				Operator.arities.get(tk.seeNextToken()) == 4) {
			s = tk.matchTokenTypes(TokenType.Operator);
			Exp<Object> exp = ExpParser.scanFactor(tk, vars);
			BinaryOperator<Object,Object,Object> bop = Operator.getBinary(s,r.getType(),exp.getType());
			r = Exp.binary(r,exp,bop);
		}
		return r;
	}
	
	protected static Exp<Object> scanFactor(Tokenizer tk, Map<String,Exp<Object>> vars){
		Exp<Object> r = scanBasic(tk,vars);
		String s;
		if (tk.hasMoreTokens() && 
				tk.seeNextTokenType().equals(TokenType.Operator) &&
				Operator.arities.get(tk.seeNextToken()) == 6) {
			s = tk.matchTokenTypes(TokenType.Operator);
			Exp<Object> exp = ExpParser.scanFactor(tk, vars);
			BinaryOperator<Object,Object,Object> bop = Operator.getBinary(s,r.getType(),exp.getType());
			r = Exp.binary(r,exp,bop);
		}
		return r;
	}
	protected static Exp<Object> scanBasic(Tokenizer tk, Map<String,Exp<Object>> vars){
		Exp<Object> r = null;
		String s;
		TokenType tt = tk.seeNextTokenType();
		switch (tt) {	
		case Integer:
			r = ExpParser.scanConstantInteger(tk);
			break;
		case Double:
			r = ExpParser.scanConstantDouble(tk);
			break; 
		case VariableIdentifier:
			if (tk.seeNextToken().charAt(0) == ('_')) {
				r = ExpParser.scanFree(tk, vars);
			} else {
				r = ExpParser.scanVariable(tk, vars);
			}
			break;
		case Operator:
		case FunctionIdentifier:
			s = tk.matchTokenTypes(TokenType.Operator,TokenType.FunctionIdentifier);			
			tk.matchTokens("(");	
			r = scanParameters(s,tk,vars);
			tk.matchTokens(")");
			break;
		case Separator:	
			tk.matchTokens("(");				
			r = scanExp(tk, vars);
			tk.matchTokens(")");			
			break;
		default:
			Preconditions.checkState(false, 
					String.format("No se esperaba %s en la posición \n   %s",
							tk.getToken(),tk.getSufix()));			
		}			
		return r;
	}

	
	private static Exp<Object> scanParameters(String name, Tokenizer tk, Map<String, Exp<Object>> vars) {
		List<Exp<Object>> exps = new ArrayList<>();
		Exp<Object> exp = ExpParser.scanExp(tk, vars);
		exps.add(exp);
		while (tk.seeNextToken().equals(",")) {
			tk.matchTokens(",");
			exp = ExpParser.scanExp(tk, vars);
			exps.add(exp);
		}
		Exp<Object> r = null;
		int arity = exps.size();
		boolean sameType = IntStream.range(0, exps.size() - 1)
				.allMatch(i -> exps.get(i).getType().equals(exps.get(i + 1).getType()));
		switch (arity) {
		case 1:
			UnaryOperator<Object, Object> uop = Operator.getUnary(name, exp.getType());
			r = Exp.unary(exp, uop);
			break;
		case 2:
			if (sameType) {
				NaryOperator<Object, Object> nop = Operator.getNary(name, exp.getType());
				r = Exp.nary(exps, nop);
			} else {
				BinaryOperator<Object, Object, Object> bop = Operator.getBinary(name, exps.get(0).getType(),
						exps.get(1).getType());
				r = Exp.binary(exps.get(0), exps.get(1), bop);
			}
			break;
		default:
			if (sameType) {
				NaryOperator<Object, Object> nop = Operator.getNary(name, exp.getType());
				r = Exp.nary(exps, nop);
			} else {
				TernaryOperator<Object, Object, Object, Object> top = Operator.getTernary(name, exps.get(0).getType(),
						exps.get(1).getType(), exps.get(2).getType());
				r = Exp.ternary(exps.get(0), exps.get(1), exps.get(2), top);
			}
			break;
		}
		return r;

	}
	
	public static VariableExp<Object> scanVariable(Tokenizer tk, Map<String,Exp<Object>> vars){		
		String s = tk.matchTokenTypes(TokenType.VariableIdentifier);
		VariableExp<Object> r=null;	
		if(vars.containsKey(s)){
	        r = (VariableExp<Object>) vars.get(s);
		} else {
			ExpType expType = VariableExp.getVariableType(s);
			switch(expType){
			case Boolean: r = Exp.variable(s, ExpType.Boolean);break;
			case Integer: r = Exp.variable(s, ExpType.Integer);break;
			case Double:  r = Exp.variable(s, ExpType.Double);				
			}
			vars.put(s, r);
		}
		return r;
	}

	public static Exp<Object> scanFree(Tokenizer tk, Map<String,Exp<Object>> vars){						
		String s = tk.matchTokenTypes(TokenType.VariableIdentifier);
		Exp<Object> r=null;	
		if(vars.containsKey(s)){
	        r = vars.get(s);
		} else {
			ExpType expType = VariableExp.getVariableType(s.substring(1));
			switch(expType){
			case Boolean: r = Exp.free(s, ExpType.Boolean);break;
			case Integer: r = Exp.free(s, ExpType.Integer);break;
			case Double:  r = Exp.free(s, ExpType.Double);				
			}
			vars.put(s, r);
		}
		return r;
	}

	public static ConstantExp<Object> scanConstantDouble(Tokenizer tk){		
		String s = tk.matchTokenTypes(TokenType.Double);
		return Exp.constant(Double.parseDouble(s),ExpType.Double);
	}

	public static ConstantExp<Object> scanConstantInteger(Tokenizer tk){		
		String s = tk.matchTokenTypes(TokenType.Integer);
		return Exp.constant(Integer.parseInt(s),ExpType.Integer);  
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String s1 = "((sqrt((x*3.2))^3)+(x+y+sqrt((x*3.2))+(sqrt((x*3.2))^3)+(x+y)))";
		String s2 = "(x+y+sqrt((x*3.2))+(sqrt((x*3.2))^3)+(x+y))";		
		String s3 = "((((x+3.2)*y)+x)*sqrt((3.2^3)))";
		String s4 = "((((x+3.2)*y)+x)*5.7243340223994625)";
		String s5 = "((sqrt((x*3.2))^3^2)+(v+y+sqrt((_z*3.2))+(sqrt((x*3.2))^3)+(x+y)))";
		String s6 = "iff(x==y,x+3.2*y+x*sqrt(3.2^3),sqrt(x*3.2)^3+x+y+sqrt(x*3.2)+sqrt(x*3.2)^3+x+y)";
		String s8 = "iff(x==y,x+3.2*y+x*sqrt(3.2^3),sqrt(x*3.2)^3+ +(x,y,sqrt(x*3.2),sqrt(x*3.2)^3,x+y))";
		String s9 = "iff(x==y,(int)(((x+3.2)*y+x)*sqrt(3.2^3)),(int)(sqrt(x*3.2)^3+ min(x,y,sqrt(x*3.2),sqrt(x*3.2)^3,x+y)))";
		String s10 = "(int)(x^3+5.6)+9";
		System.out.println(Operator.operators);
		Exp<Object> ex1 = ExpParser.scan(s1);
		System.out.println("ex1 ="+ex1+","+ex1.getVars());
		Map<String,Exp<Object>> vars = Maps2.newHashMap("_z", ex1);
		System.out.println(vars);
		Exp<Object> exp = ExpParser.scan(s5,vars);
		System.out.println("exp ="+exp+"-------"+exp.getVars());
		
		exp.get("x").<Double>asVariable().setValue(4.5);
		exp.get("y").<Double>asVariable().setValue(45.7);
		exp.get("v").<Double>asVariable().setValue(5.7);
		System.out.println(exp.simplify());
		
		System.out.println(exp.getValue());
		
	}
	
}
