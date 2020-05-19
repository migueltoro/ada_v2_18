package us.lsi.gurobi;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.flujosparalelos.StreamsP;

public class AuxiliaryGurobi {
	
public static Locale locale = new Locale("en", "US");
	
	
	public static BiPredicate<Integer,Integer> all2 = (i,j) -> true;
	public static Predicate<Integer> all1 = i -> true;
	public static BiFunction<Integer,Integer,String> noFactor2 = (i,j) -> "";
	public static Function<Integer,String> noFactor1 = i -> "";
	
	/**
	 * @param n El rango del sumatorio es 0..n
	 * @param id El identificador de la variable
	 * @param right La parte derecha de la restrcci&oacute;n: " >= 1; \n", etc.
	 * @return Genera varId_0+varId_0+... end
	 */
	public static String sum_1_v(Integer n, String id) {
		return IntStream.range(0, n).boxed()
				.map(i -> varId(id,i))
				.collect(Collectors.joining(" + "));
	}
	
	public static String sum_1(Integer n, String id, 
			Predicate<Integer> p, 
			Function<Integer,String> factor) {
		return IntStream.range(0, n).boxed()
				.filter(p)
				.map(i -> String.format("%s %s",factor.apply(i),varId(id,i)))
				.collect(Collectors.joining(" + "));
	}
	
	public static String sum_2_1_v(Integer n, Integer j, String id) {
		return IntStream.range(0, n).boxed()
				.map(i -> varId(id,i,j))
				.collect(Collectors.joining(" + "));
	}
	
	public static String sum_2_1(Integer n, Integer j, String id, 
			BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> factor) {
		return IntStream.range(0, n).boxed()
				.filter(i->pd.test(i,j))
				.map(i -> String.format("%s %s", factor.apply(i,j),varId(id,i,j)))
				.collect(Collectors.joining(" + "));
	}
	
	public static String sum_2_2_v(Integer n, Integer i, String id) {
		return IntStream.range(0, n).boxed()
				.map(j -> varId(id,i,j))
				.collect(Collectors.joining(" + "));
	}
	
	public static String sum_2_2(Integer n, Integer i, String id, 
			BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> factor) {
		return IntStream.range(0, n).boxed()
				.filter(j->pd.test(i,j))
				.map(j -> String.format("%s %s", factor.apply(i,j),varId(id,i,j)))
				.collect(Collectors.joining(" + "));
	}
	
	public static String sum_2_v(Integer n, Integer m, String id) {
		return StreamsP.allPairs(n, m)
				.map(p -> varId(id,p.first,p.second))
				.collect(Collectors.joining(" + "));
	}
	
	public static String sum_2_p(Integer n, Integer m, String id,
			BiPredicate<Integer, Integer> pd) {
		return StreamsP.allPairs(n, m)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> varId(id,p.first,p.second))				
				.collect(Collectors.joining(" + "));
	}
	
	public static String sum_2_f(Integer n, Integer m, String id, 
			BiFunction<Integer,Integer,String> factor) {
		return StreamsP.allPairs(n, m)
				.map(p -> String.format("%s %s", factor.apply(p.first,p.second),varId(id,p.first,p.second)))
				.collect(Collectors.joining(" + "));
	}
	
	public static String sum_2(Integer n, Integer m, String id, 
			BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> factor) {
		return StreamsP.allPairs(n, m)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> String.format("%s %s",factor.apply(p.first,p.second),varId(id,p.first,p.second)))
				.collect(Collectors.joining(" + "));
	}
	
	
	public static String forAll_1(Integer n, 
			Function<Integer,String> constraint) {
		return IntStream.range(0, n).boxed()
				.map(i -> String.format("%s",constraint.apply(i)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String forAll_1_r(Integer n1, Integer n2,
			Function<Integer,String> constraint) {
		return IntStream.range(n1, n2).boxed()
				.map(i -> String.format("%s",constraint.apply(i)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String forAll_1(Integer n, 
			Predicate<Integer> p, 
			Function<Integer,String> constraint) {
		return IntStream.range(0, n).boxed()
				.filter(p)
				.map(i -> String.format("%s",constraint.apply(i)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	
	public static String forAll_1_r(Integer n1, Integer n2,
			Predicate<Integer> p, 
			Function<Integer,String> constraint) {
		return IntStream.range(n1, n2).boxed()
				.filter(p)
				.map(i -> String.format("%s",constraint.apply(i)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String forAll_2(Integer n, Integer m, 
			BiFunction<Integer,Integer,String> constraint) {
		return StreamsP.allPairs(n, m)
				.map(p -> String.format("%s",constraint.apply(p.first,p.second)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String forAll_2(Integer n, Integer m, 
			BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> constraint) {
		return StreamsP.allPairs(n, m)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> String.format("%s",constraint.apply(p.first,p.second)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String forAll_2_r(Integer n1, Integer n2, Integer m1, Integer m2,
			BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> constraint) {
		return StreamsP.allPairs(n1, n2, m1, m2)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> String.format("%s",constraint.apply(p.first,p.second)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String vars(int n, String id) {
		return IntStream.range(0,n).boxed()
				.map(i -> varId(id,i))
				.collect(Collectors.joining(" "," ", " \n"));
	}
	
	public static String vars(int n, int m, String id) {
		return StreamsP.allPairs(n, m)
				.map(p -> varId(id, p.first, p.second))
			    .collect(Collectors.joining(" ","  "," \n"));
	}
	
	public static String varId(String id, Integer i) {
		return String.format("%s_%d",id,i);
	}
	
	public static String varId(String id, Integer i, Integer j) {
		return String.format("%s_%d_%d",id,i,j);
	}
	
	public static String goalMin(String goal) {
		return "Minimize\n\n  "+goal+"\n";
	}
	
	public static String goalMax(String goal) {
		return "Maximize\n\n  "+goal+" \n";
	}
	
	public static String constraintLe(String expression, String right, Integer n, String constraintName) {
		return String.format("   %s%d: %s <= %s",constraintName,n,expression,right);
	}
	
	public static String constraintGe(String expression, String right, Integer n, String constraintName) {
		return String.format("   %s%d: %s >= %s",constraintName,n,expression,right);
	}
	
	
	public static String constraintEq(String expression, String right, Integer n, String constraintName) {
		return String.format("   %s%d: %s = %s",constraintName,n,expression,right);
	}
	
	public static String bound(String leftNumber, String varName, String rightNumber) {
		return String.format("%s <= %s <= %s",leftNumber,varName,rightNumber);
	}
	
	public static String boundLe(String varName, String number) {
		return String.format("%s <= %s",varName,number);
	}
	
	public static String boundGe(String varName, String number) {
		return String.format("%s >= %s",varName,number);
	}

}
