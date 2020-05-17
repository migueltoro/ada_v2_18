package us.lsi.lpsolve;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.flujosparalelos.StreamsP;


public class AuxiliaryPLI {
	
	public static Locale locale = new Locale("en", "US");
	
	
	/**
	 * @param n El rango del sumatorio es 0..n
	 * @param id El identificador de la variable
	 * @param right La parte derecha de la restrcci&oacute;n: " >= 1; \n", etc.
	 * @return Genera varId_0+varId_0+... end
	 */
	public static String sum_1(Integer n, String id) {
		return IntStream.range(0, n).boxed()
				.map(i -> varId(id,i))
				.collect(Collectors.joining("+"));
	}
	
	public static String sum_1(Integer n, String id, Predicate<Integer> p, Function<Integer,String> factor) {
		return IntStream.range(0, n).boxed()
				.filter(p)
				.map(i -> String.format("%s*%s",factor.apply(i),varId(id,i)))
				.collect(Collectors.joining("+"));
	}
	
	public static String sum_2_1(Integer n, Integer j, String id) {
		return IntStream.range(0, n).boxed()
				.map(i -> varId(id,i,j))
				.collect(Collectors.joining("+"));
	}
	
	public static String sum_2_1(Integer n, Integer j, String id, BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> factor) {
		return IntStream.range(0, n).boxed()
				.filter(i->pd.test(i,j))
				.map(i -> String.format("%s*%s", factor.apply(i,j),varId(id,i,j)))
				.collect(Collectors.joining("+"));
	}
	
	public static String sum_2_2(Integer n, Integer i, String id) {
		return IntStream.range(0, n).boxed()
				.map(j -> varId(id,i,j))
				.collect(Collectors.joining("+"));
	}
	
	public static String sum_2_2(Integer n, Integer i, String id, BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> factor) {
		return IntStream.range(0, n).boxed()
				.filter(j->pd.test(i,j))
				.map(j -> String.format("%s*%s", factor.apply(i,j),varId(id,i,j)))
				.collect(Collectors.joining("+"));
	}
	
	public static String sum_2(Integer n, Integer m, String id) {
		return StreamsP.allPairs(n, m)
				.map(p -> varId(id,p.first,p.second))
				.collect(Collectors.joining("+"));
	}
	
	public static String sum_2(Integer n, Integer m, String id, BiFunction<Integer,Integer,String> factor) {
		return StreamsP.allPairs(n, m)
				.map(p -> String.format("%s*%s", factor.apply(p.first,p.second),varId(id,p.first,p.second)))
				.collect(Collectors.joining("+"));
	}
	
	public static String sum_2(Integer n, Integer m, String id, BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> factor) {
		return StreamsP.allPairs(n, m)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> String.format("%s*%s",factor.apply(p.first,p.second),varId(id,p.first,p.second)))
				.collect(Collectors.joining("+"));
	}
	
	
	public static String forAll_1(Integer n, Function<Integer,String> constraint) {
		return IntStream.range(0, n).boxed()
				.map(i -> String.format("%s",constraint.apply(i)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String forAll_1(Integer n, Predicate<Integer> p, Function<Integer,String> constraint) {
		return IntStream.range(0, n).boxed()
				.filter(p)
				.map(i -> String.format("%s",constraint.apply(i)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String forAll_2(Integer n, Integer m, BiFunction<Integer,Integer,String> constraint) {
		return StreamsP.allPairs(n, m)
				.map(p -> String.format("%s",constraint.apply(p.first,p.second)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String forAll_2(Integer n, Integer m, BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> constraint) {
		return StreamsP.allPairs(n, m)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> String.format("%s",constraint.apply(p.first,p.second)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String varInt(int n, String id) {
		return IntStream.range(0,n).boxed()
				.map(i -> varId(id,i))
				.collect(Collectors.joining(", ","\nint ", "; \n"));
	}
	
	public static String varBin(int n, String id) {
		return IntStream.range(0,n).boxed()
				.map(i -> varId(id,i))
				.collect(Collectors.joining(", ","\nbin ", "; \n"));
	}
	
	public static String varInt(int n, int m, String id) {
		return StreamsP.allPairs(n, m)
				.map(p -> varId(id, p.first, p.second))
			    .collect(Collectors.joining(", ","\nint ", "; \n"));
	}
	
	public static String varBin(int n, int m, String id) {
		return StreamsP.allPairs(n, m)
				.map(p -> varId(id, p.first, p.second))
			    .collect(Collectors.joining(", ","\nbin ", "; \n"));
	}	
	
	public static String varId(String id, Integer i) {
		return String.format("%s_%d",id,i);
	}
	
	public static String varId(String id, Integer i, Integer j) {
		return String.format("%s_%d_%d",id,i,j);
	}
	
	public static String goalMin(String goal) {
		return "min: "+goal+"; \n";
	}
	
	public static String goalMax(String goal) {
		return "max: "+goal+"; \n";
	}
	
	
	public static String constraintLe(String left, String right) {
		return left+" <= "+ right+";";
	}
	
	public static String constraintLt(String left, String right) {
		return left+" < "+ right+";";
	}
	
	public static String constraintGe(String left, String right) {
		return left+" >= "+ right+";";
	}
	
	public static String constraintGt(String left, String right) {
		return left+" > "+ right+";";
	}
	
	public static String constraintEq(String left, String right) {
		return left+" = "+ right+";";
	}
	
	public static String constraintRange(String left, String center, String right) {
		return left+" <= "+ center+ " <" +right+";";
	}
}
