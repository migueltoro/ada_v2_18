package us.lsi.tiposrecursivos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.common.Preconditions;
import us.lsi.common.Sets2;
import us.lsi.common.Strings2;
import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;
import us.lsi.tiposrecursivos.Exp.ExpType;

public interface Operator {
	
	public static Map<Tuple2<String,List<ExpType>>,Operator> operators = new HashMap<>();
	static Set<String> functions = new HashSet<>();
	public static Set<String> reservedWords = Sets2.of("while","if","else","int","double");
	public static Map<String,Integer> arities = new HashMap<>();
    
	static Object n = new Operators();
	
	Integer getArity();
	
	String getName();
	
	String getId();
	
	Integer getPriority();
	
	public static void add(OperatorCode<?> operator) {
		Tuple2<String,List<ExpType>> t = Tuple.create(operator.getName(),Arrays.asList(operator.parametersType));
		operators.put(t, operator);
		if(operator.isFunction()) functions.add(operator.getName());
		arities.put(operator.name, operator.getArity());
	}
	
	public static boolean contains(String name, ExpType... parametersType) {
		Tuple2<String,List<ExpType>> t = Tuple.create(name,Arrays.asList(parametersType));
		return operators.containsKey(t);
	}
	
	@SuppressWarnings("unchecked")
	public static <T,R> UnaryOperator<T,R> getUnary(String name, ExpType p1) {
		ExpType[] parametersType = {p1};
		Tuple2<String,List<ExpType>> t = Tuple.create(name,Arrays.asList(parametersType));
		Operator r =  operators.get(t);
		Preconditions.checkNotNull(r);
		return (UnaryOperator<T, R>) r;
	}
	
	@SuppressWarnings("unchecked")
	public static <T,R> NaryOperator<T,R> getNary(String name, ExpType p1) {
		ExpType[] parametersType = {p1};
		Tuple2<String,List<ExpType>> t = Tuple.create(name,Arrays.asList(parametersType));
		Operator r =  operators.get(t);
		Preconditions.checkNotNull(r);
		return (NaryOperator<T, R>) r;
	}
	
	@SuppressWarnings("unchecked")
	public static <T1,T2,R> BinaryOperator<T1,T2,R> getBinary(String name, ExpType p1, ExpType p2) {
		ExpType[] parametersType = {p1,p2};
		Tuple2<String,List<ExpType>> t = Tuple.create(name,Arrays.asList(parametersType));
		Operator r =  operators.get(t);
		Preconditions.checkNotNull(r);
		return (BinaryOperator<T1, T2, R>) r;
	}
	
	@SuppressWarnings("unchecked")
	public static <T1,T2,T3,R> TernaryOperator<T1,T2,T3,R> getTernary(String name, ExpType p1, ExpType p2, ExpType p3) {
		ExpType[] parametersType = {p1,p2,p3};
		Tuple2<String,List<ExpType>> t = Tuple.create(name,Arrays.asList(parametersType));
		Operator r =  operators.get(t);
		Preconditions.checkNotNull(r);
		return (TernaryOperator<T1, T2, T3, R>) r;
	}
	
	
	public static void main(String[] args) {
		System.out.println(Tuple.create(1, 2,3, 4, 5));
//		Operators.initial();
		String s = operators.entrySet()
				.stream()
				.map(x->x.getKey().v1+x.getKey().v2+","+x.getValue())
				.collect(Collectors.joining("\n"));
		System.out.println(s);
		Operator op = Operator.getBinary("*",ExpType.Double,ExpType.Double);
		System.out.println(op);
		Operator op2 = Operator.getUnary("sqrt",ExpType.Double);
		System.out.println(op2);
		Operator op1 = Operator.getTernary("iff", ExpType.Boolean,ExpType.Double,ExpType.Double);
		System.out.println(op1);
		Strings2.toConsole(Operator.functions, "Funciones");
		Strings2.toConsole(Operator.reservedWords, "Palabras Reservadas");
	}
	
	
}
