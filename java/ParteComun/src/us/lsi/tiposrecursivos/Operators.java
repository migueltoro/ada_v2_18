package us.lsi.tiposrecursivos;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import us.lsi.common.TriFunction;
import us.lsi.common.Tuple;
import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.Exp.ExpType;

public class Operators {

	public Operators() { 
		
	}
	
	static {
		initial();
	}

	
	public static <T1, R> UnaryOperator<T1,R> createUnary(
			String name, ExpType t1, ExpType result,
			Function<T1, R> code, 
			String format, Boolean isFunction) {
		ExpType[] operatorType = {t1, result};
		UnaryOperator<T1,R> operator = new UnaryOperator<>(name, code,operatorType,format,isFunction);
		Operator.add(operator);
		return operator;
	}

	public static <T1, R> NaryOperator<T1,R> createNary(
			String name, ExpType t1, ExpType result,
			Collector<T1, ?, R> code, Boolean isFunction) {
		ExpType[] operatorType = {t1, result};
		NaryOperator<T1,R> operator = new NaryOperator<>(name, code,operatorType,isFunction);
		Operator.add(operator);
		return operator;
	}

	
	public static <T1, T2, R> BinaryOperator<T1,T2,R> createBinary(
			String name, ExpType t1, ExpType t2, ExpType result,
			BiFunction<T1, T2, R> code, 
		    int priority, String format, Boolean isFunction) {
		ExpType[] operatorType = {t1,t2,result};
		BinaryOperator<T1,T2,R> operator = new BinaryOperator<>(name, code,operatorType, priority, format,isFunction);
		Operator.add(operator);
		return operator;
	}
	
	public static <T1, T2, T3, R> TernaryOperator<T1,T2,T3,R> createTernary(
			String name, ExpType t1, ExpType t2, ExpType t3, ExpType result,
			TriFunction<T1, T2, T3, R> code, 
			String format, Boolean isFunction) {
		ExpType[] operatorType = {t1,t2,t3,result};
		TernaryOperator<T1,T2,T3,R> operator = new TernaryOperator<>(name, code,operatorType, format,isFunction);
		Operator.add(operator);
		return operator;
	}
	
	
	
	static void initial() {
		// Operadores Narios
		Operators.createNary("+", ExpType.Integer,ExpType.Integer,Collectors.reducing(0,(x,y)->x+y), false);
		Operators.createNary("+", ExpType.Double,ExpType.Double,Collectors.reducing(0.,(Double x, Double y)->x+y), false);
		Operators.createNary("*", ExpType.Integer,ExpType.Integer,Collectors.reducing(0,(x,y)->x*y), false);
		Operators.createNary("*", ExpType.Double,ExpType.Double,Collectors.reducing(0.,(Double x, Double y)->x*y), false);
		Operators.createNary("max", ExpType.Integer,ExpType.Integer,Collectors.reducing(Integer.MIN_VALUE,(x,y)->Integer.min(x,y)), true);
		Operators.createNary("max", ExpType.Double,ExpType.Double,Collectors.reducing(Double.MIN_VALUE,(x,y)->Double.min(x,y)), true);
		Operators.createNary("min", ExpType.Integer,ExpType.Integer,Collectors.reducing(Integer.MAX_VALUE,(x,y)->Integer.min(x,y)), true);
		Operators.createNary("min", ExpType.Double,ExpType.Double,Collectors.reducing(Double.MAX_VALUE,(x,y)->Double.min(x,y)), true);
		System.out.println("==================");
		//Operadores unarios
		Operators.createUnary("sqrt",ExpType.Double,ExpType.Double, (Double x)->Math.sqrt(x),"sqrt(%s)", true);
		Operators.createUnary("square",ExpType.Double,ExpType.Double, (Double x)->x*x,"%s^2", true);
		Operators.createUnary("cube",ExpType.Double,ExpType.Double, (Double x)->x*x*x,"%s^3", true);
		Operators.createUnary("toInt",ExpType.Double,ExpType.Integer, (Double x)->x.intValue(),"(int)%s", true);
		Operators.createUnary("toDouble",ExpType.Integer,ExpType.Double, (Integer x)->x.doubleValue(),"(double)%s", true);
		//Operadores Binarios
		Operators.createBinary("+", ExpType.Integer,ExpType.Integer,ExpType.Integer,(Integer x,Integer y)->x+y, 2,"%s+%s",false);
		Operators.createBinary("+", ExpType.Double,ExpType.Double,ExpType.Double,(Double x,Double y)->x+y, 2,"%s+%s",false);
		Operators.createBinary("-", ExpType.Integer,ExpType.Integer,ExpType.Integer,(Integer x,Integer y)->x-y, 2,"%s-%s",false);
		Operators.createBinary("-", ExpType.Double,ExpType.Double,ExpType.Double,(Double x,Double y)->x-y, 2,"%s-%s",false);
		Operators.createBinary("*", ExpType.Integer,ExpType.Integer,ExpType.Integer,(Integer x,Integer y)->x*y, 4,"%s*%s",false);
		Operators.createBinary("*", ExpType.Double,ExpType.Double,ExpType.Double,(Double x,Double y)->x*y, 4,"%s*%s",false);
		Operators.createBinary("^", ExpType.Double,ExpType.Double,ExpType.Double,(Double x,Double y)->Math.pow(x,y), 6,"%s^*%s",false);
		Operators.createBinary("^", ExpType.Integer,ExpType.Integer,ExpType.Integer,(Integer x,Integer y)->Math2.pow(x,y), 6,"%s^*%s",false);
		Operators.createBinary("^", ExpType.Double,ExpType.Integer,ExpType.Double,(Double x,Integer y)->Math2.pow(x,y), 6,"%s^*%s",false);
		Operators.createBinary("==", ExpType.Integer,ExpType.Integer,ExpType.Boolean,(Integer x,Integer y)->x.equals(y), 8,"%s==*%s",false);
		Operators.createBinary("==", ExpType.Double,ExpType.Double,ExpType.Boolean,(Double x,Double y)->x.equals(y), 8,"%==^*%s",false);
		// Operadores ternarios
		Operators.createTernary("iff", ExpType.Boolean,ExpType.Double,ExpType.Double, ExpType.Double,
				(Boolean x, Double y, Double z)->x?y:z,"iff(%s,%s,%s)", true);
		Operators.createTernary("iff", ExpType.Boolean,ExpType.Integer,ExpType.Integer, ExpType.Integer,
				(Boolean x, Integer y, Integer z)->x?y:z,"iff(%s,%s,%s)", true);
	}


	public static void main(String[] args) {
		System.out.println(Tuple.create(1, 2,3, 4, 5));
//		Operators.initial();
		String s = Operator.operators.entrySet()
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
	}
}
