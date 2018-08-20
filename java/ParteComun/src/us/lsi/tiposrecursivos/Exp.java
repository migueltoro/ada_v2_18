package us.lsi.tiposrecursivos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Preconditions;


/**
 * <p> Tipo inmutable que modela una expresión
 * 
 * @author Miguel Toro
 *
 * @param <R> Tipo del resultado de la expresión
 */
public abstract class Exp<R> extends Element {	
	
	/**
	 * @param <R> El tipo de la expresión
	 * @param ls Una lista de operadores ordenada por niveles y de izquierda a derecha
	 * @return La expresión resultante
	 */
	public static <R> Exp<R> exp(List<Operator> ls){
		return Exp.byLevelsToExp(Exp.toLevels(ls));
	}
	
	public static Exp<Object> parse(String s){
		return ExpParser.scan(s);
	}
	
	public static Exp<Object>parse(String s, Map<String, Exp<Object>> vars){
		return ExpParser.scan(s, vars);
	}
	
	public static <R> VariableExp<R> variable(String name, R value, ExpType expType) {
		return new VariableExp<R>(name, value, expType);
	}
	
	public static <R> VariableExp<R> variable(String name, ExpType expType) {
		return new VariableExp<R>(name, null, expType);
	}
	
	public static <R> VariableExp<R> variable(String name, R value) {
		return new VariableExp<R>(name, value, null);
	}
	
	public static <R> VariableExp<R> variable(String name) {
		return new VariableExp<R>(name, null, null);
	}
	
	public static <T1, T2, R> BinaryExp<T1, T2, R> binary(Exp<T1> left,Exp<T2> right,BinaryOperator<T1,T2,R> operator){
		return new BinaryExp<T1,T2,R>(left,right,operator);
	}
	
	public static <R> ConstantExp<R> constant(String name, R value, ExpType expType) {
		return new ConstantExp<R>(name,value,expType);
	}
	
	public static <R> ConstantExp<R> constant(String name) {
		return new ConstantExp<R>(name, null, null);
	}
	
	public static <R> ConstantExp<R> constant(R value) {
		return new ConstantExp<R>(null,value,null);
	}
	
	public static <R> ConstantExp<R> constant(R value,ExpType expType) {
		return new ConstantExp<R>(null,value,expType);
	}
	
	public static <T,R> UnaryExp<T,R> unary(Exp<T> r1,UnaryOperator<T, R> unaryOperatorExp) {
		return new UnaryExp<T,R>(r1, unaryOperatorExp);
	}		
	
	public static <T1, T2, T3, R> TernaryExp<T1, T2, T3, R> ternary(
			Exp<T1> op1, Exp<T2> op2, Exp<T3> op3,TernaryOperator<T1,T2,T3,R> operator) {
		return new TernaryExp<>(op1, op2, op3, operator);
	}
	
	public static <T,R> NaryExp<T,R> nary(List<Exp<T>> ops,NaryOperator<T,R> operator) {
		return new NaryExp<T,R>(ops,operator);
	}
	
	@SafeVarargs
	public static <T,R> NaryExp<T,R> nary(NaryOperator<T,R> accumulator, Exp<T>... ops) {
		return new NaryExp<T,R>(Arrays.asList(ops),accumulator);
	}
	
	public static <R> Free<R> free(String name, ExpType expType) {
		return new Free<R>(name,expType);
	}
	
	public static <R> Free<R> free(String name) {
		return new Free<R>(name,null);
	}
	
	public enum ExpType{Integer,Double,Boolean};
	
	
	public Exp() { 
		super();
	}
	
	/**
	 * @return Número de operandos de la expresión
	 */
	public abstract Integer getArity(); 
	
	/**
	 * @return El operador de la expresión
	 */
	public abstract Operator getOperator(); 
	
	/**
	 * @return Copia profunda de la expresión
	 */
	public abstract Exp<R> copy();

	/**
	 * @return Valor devuelto por la expresión
	 */
	public abstract R getValue();
	
	/**
	 * @return Si la expresión es un patrón
	 */
	public abstract Boolean isPattern();
	
	/**
	 * @return Igualdad
	 */
	public abstract boolean equals(Object other);
	/**
	 * @return Igualdad
	 */
	public abstract int hashCode();	
		
	/**
	 * @return El tipo de la expresión
	 */
	public abstract ExpType getType();
	
	protected abstract String getId();
	
	protected abstract void toDOT(String file);
	
	/**
	 * @return la expresión simplificada
	 */
	public abstract Exp<R> simplify();
	
	/**
	 * @post Si la expresión concuerda con el patrón las variables libres de este quedan ligadas
	 * @param pattern Un patrón
	 * @return Si la expresión concuerda con el patrón
	 */
	public abstract Boolean match(Exp<?> pattern);
	
	/**
	 * @param pattern Un patrón
	 * @param result Un patrón
	 * @return Si la expresión concuerda pattern se devuelve la expresión transformada según result. Si no concuerda
	 * se devuelve la misma expresión
	 */
	public Exp<Object> ifMatchTransform(String pattern, String result){
		Exp<Object> e = Exp.parse(pattern);		
		return ifMatchTransform(e,this.getVars(),result);
	}
	
	public abstract Exp<Object> ifMatchTransform(Exp<Object> pattern, Map<String,Exp<Object>> vars,String result);
	
	private Map<String, Exp<Object>> vars;
	
	protected abstract Map<String, Exp<Object>> vars();
	
	@SuppressWarnings("unchecked")
	public <S> Exp<S> get(String s){
		if(vars==null){
			vars = vars();
		}
		Preconditions.checkArgument(getVars().keySet().contains(s),String.format("La expresión no tiene la varaible %s",s));
		return (Exp<S>) vars().get(s);
	}
	
	/**
	 * @return La asociación de los nombres de variables de la expresión con sus expresiones correspondientes.
	 */
	public Map<String, Exp<Object>> getVars() {
		if(vars==null){
			vars = vars();
		}
		return vars;
	}
	
	/**
	 * @return La prioridad del operador de la expresión
	 */
	public abstract Integer getPriority();
	
	/**
	 * @return Si es una variable
	 */
	public Boolean isVariable() {
		return false;
	}
	/**
	 * @return Si es constante
	 */
	public Boolean isConstant() {
		return false;
	}
	/**
	 * @return Si es una expresión unaria.
	 */
	public Boolean isUnary() {
		return false;
	}
	/**
	 * @return Si es una expresión binaria
	 */
	public Boolean isBinary() {
		return false;
	}
	/**
	 * @return Si es una expresión ternaria
	 */
	public Boolean isTernary() {
		return false;
	}
	
	/**
	 * @return Si es una expresión naria
	 */
	public Boolean isNary() {
		return false;
	}
	
	/**
	 * @return Si es una variable libre
	 */
	public Boolean isFree() {
		return false;
	}
	
	
	/**
	 * @param <S> Tipo de la variable
	 * @return Conversión a  variable
	 */
	@SuppressWarnings("unchecked")
	public <S> VariableExp<S> asVariable() {
		return (VariableExp<S>) this;
	}
	/**
	 * @param <S> Tipo de la variable
	 * @return Conversión a  constante
	 */	
	@SuppressWarnings("unchecked")
	public <S> ConstantExp<S> asConstant() {
		return (ConstantExp<S>) this;
	}
	/**
	 * 
	 * @param <T> Tipo de la variable
	 * @param <S> Tipo de la variable
	 * @return Conversión a  expresión unaria.
	 */
	@SuppressWarnings("unchecked")
	public <T,S> UnaryExp<T,S> asUnary() {
		return (UnaryExp<T,S>) this;
	}
	/**
	 * 
	 * @param <T1> Tipo de la variable
	 * @param <T2> Tipo de la variable
	 * @param <S> Tipo de la variable
	 * @return Conversión a expresión binaria
	 */
	@SuppressWarnings("unchecked")
	public <T1,T2,S> BinaryExp<T1,T2,S> asBinary() {
		return (BinaryExp<T1,T2,S>) this;
	}
	/**
	 * @param <T1> Tipo de la variable
	 * @param <T2> Tipo de la variable
	 * @param <T3> Tipo de la variable
	 * @param <S> Tipo de la variable
	 * @return Conversión a expresión ternaria
	 */
	@SuppressWarnings("unchecked")
	public <T1,T2,T3,S> TernaryExp<T1,T2,T3,S> asTernary() {
		return (TernaryExp<T1,T2,T3,S>) this;
	}
	
	/**
	 * @param <T> Tipo de la variable
	 * @param <S> Tipo de la variable
	 * @return Conversión a  expresión naria
	 */
	@SuppressWarnings("unchecked")
	public <T,S> NaryExp<T,S> asNary() {
		return (NaryExp<T,S>) this;
	}
	
	/**
	 * @param <T> Tipo de la variable
	 * @return Conversión a  Variable Libre
	 */
	@SuppressWarnings("unchecked")
	public <T> Free<T> asFree() {
		return (Free<T>) this;
	}
	
	
	
	/**
	 * @param <R> El tipo de la expresión
	 * @param lo Una lista de operadores por niveles
	 * @return La expresión resultante
	 */
	@SuppressWarnings("unchecked")
	public
	static <R> Exp<R> byLevelsToExp(List<List<Operator>> lo){
		int n = lo.size();
		List<Exp<R>> level = lo.get(n-1).stream().map(x->(Exp<R>)x).collect(Collectors.toList());
		List<Exp<R>> nLevel;
		for(int i=n-2;i>=0;i--){
			int k = 0;
			nLevel = new ArrayList<>();
			for(Operator op :lo.get(i)){
				switch(op.getArity()){
				case 0: nLevel.add((Exp<R>)op); break;
				case 1: nLevel.add(Exp.unary(level.get(k),(UnaryOperator<R,R>)op)); 
						k = k+1; break;
				case 2: nLevel.add(Exp.binary(level.get(k), level.get(k+1),(BinaryOperator<R,R,R>)op)); 
					    k = k+2; break;
				case 3: nLevel.add(Exp.ternary(level.get(k), level.get(k+1),level.get(k+2),(TernaryOperator<R,R,R,R>)op)); 
			    		k = k+3; break;
				default: Preconditions.checkState(false, "Aridad no posible");
				}
			}
			level = nLevel;
		}
		return level.get(0);
	}

	/**
	 * @param <R> El tipo de la expresión
	 * @param ls Una lista de operadores
	 * @return Una lista con los operadores en cada nivel agrupados según sus aridades
	 */
	public static <R> List<List<Operator>> toLevels(List<Operator> ls){
		List<List<Operator>> r = new ArrayList<>();
		List<Operator> lv = Arrays.asList(ls.get(0));
		int i = 1;
		int nh;
		while(lv.size()>0){	
			r.add(lv);	
			nh = lv.stream().mapToInt(x->x.getArity()).sum();
			lv = IntStream.range(i,i+nh).mapToObj(x->ls.get(x)).collect(Collectors.toList());					
			i = i + nh;	
		}
		return r;
	}

	public static void main(String[] args) {
		System.out.println(Operator.operators.keySet().size());
		VariableExp<Double> x = Exp.variable("x",1.0);
		VariableExp<Double> y = Exp.variable("y",2.1);
		ConstantExp<Double> c = Exp.constant(3.2);		
		BinaryExp<Double,Double,Double> plus = Exp.binary(x,y,Operator.getBinary("+", ExpType.Double, ExpType.Double));
		BinaryExp<Double,Double,Double> multiply = Exp.binary(x,c,Operator.getBinary("*", ExpType.Double, ExpType.Double));
		UnaryExp<Double, Double> sqrt = Exp.unary(multiply,Operator.getUnary("sqrt", ExpType.Double));
		UnaryExp<Double, Double> pot1 = Exp.unary(sqrt,Operator.getUnary("square",ExpType.Double));
		UnaryExp<Double, Double> pot = Exp.unary(sqrt,Operator.getUnary("cube",ExpType.Double));
		NaryExp<Double,Double> s = Exp.nary(Operator.getNary("+",ExpType.Double),x,y,sqrt,pot,plus);
		BinaryExp<Double,Double,Double> rr = Exp.binary(pot, s,Operator.getBinary("+",ExpType.Double, ExpType.Double));
		System.out.println(pot1);
		System.out.println(pot1.getValue());
		System.out.println(pot1.simplify());
		System.out.println(pot1.simplify().getValue());
		System.out.println(pot);
		System.out.println(pot.getValue());
		System.out.println(s);
		System.out.println(s.getValue());
		Free<Double> p = Exp.free("_1");
		Boolean r2 = rr.match(Exp.binary(pot,p,Operator.getBinary("+",ExpType.Double,ExpType.Double)));
		System.out.println(rr);
		System.out.println(r2+","+p.getExp());
		List<Operator> lo = Arrays.asList(
				Operator.getBinary("*", ExpType.Double, ExpType.Double),
				Operator.getBinary("+", ExpType.Double, ExpType.Double),
				Operator.getUnary("sqrt", ExpType.Double),
				Operator.getBinary("*", ExpType.Double, ExpType.Double),
				x,
				Operator.getUnary("cube", ExpType.Double),				
				Operator.getBinary("+", ExpType.Double, ExpType.Double),				
				y,
				c,
				x,
				c);
		System.out.println(lo);

		Exp<Double> e = Exp.exp(lo);
		System.out.println(e);
		System.out.println(e.simplify());
		e.toDOT("Exprs.gv", "Expresion");
		Exp<Boolean> b = Exp.binary(x,y,Operator.getBinary("==",ExpType.Double,ExpType.Double));
		Exp<Double> r = Exp.ternary(b, e, rr,
				Operator.getTernary("iff", ExpType.Boolean,ExpType.Double,ExpType.Double));
		System.out.println(r);
		
		Exp<Object> e2 = Exp.parse("0.+3.^(2+5)+x^2+sqrt(4.+x)+x");
		System.out.println(e2);
		Exp<Object> exp = e2.ifMatchTransform("0.+_y", "_y");
		exp.get("x").<Double>asVariable().setValue(4.5);
		System.out.println(exp.simplify());
		System.out.println(exp.getValue());
	}
	

}

