package us.lsi.pli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.IntPair;
import us.lsi.common.Preconditions;
import us.lsi.common.TriFunction;
import us.lsi.common.TriPredicate;
import us.lsi.flujosparalelos.Streams2;

/**
 * 
 * 
 * 
 * 
 * @author migueltoro
 *
 */
/**
 * @author migueltoro
 *
 */
/**
 * @author migueltoro
 *
 */
public class AuxiliaryPLI {
	
	public static Locale locale = new Locale("en", "US");
	
	
	/**
	 * El bipredicado siempre true
	 */
	public static BiPredicate<Integer,Integer> all2 = (i,j) -> true;
	/**
	 * El predicado siempre true
	 */
	public static Predicate<Integer> all1 = i -> true;
	/**
	 * Una bifunci&oacute;n que devuelve la cadena vac&iacute;a 
	 */
	public static BiFunction<Integer,Integer,String> nf2 = (i,j) -> "";
	/**
	 * Una funci&oacute;n que devuelve la cadena vac&iacute;a 
	 */
	public static Function<Integer,String> nf1 = i -> "";
	
	/**
	 * Un contador de las varaibles binarias que se van creando implicitamente
	 */
	public static Integer nBinary = 0;
	public static Integer nInteger = 0;
	public static List<String> gConstraints = new ArrayList<>();
	public static List<String> bounds = new ArrayList<>();
	
	public static String end = "";
	public static String sep = "";
	public static String min = "Minimize\n\n";
	public static String max = "Maximize\n\n";
	public static String intVars = "\nGeneral\n\n";
	public static String binaryVars = "\nBinary\n\n";
	public static String freeVars = "\nFree\n\n";
	public static String boundsSection = "\n\nBounds\n\n";
	public static String lastEnd = "\nEnd\n\n";
	public static String constraintsSection = "\n\nSubject To\n\n";
	public static String generalConstraintsSection = "\n\nGeneral Constraints\n\n";
	
	public static enum TipoPLI{Gurobi,LPSolve}
	public static TipoPLI type = TipoPLI.Gurobi;
	
	public static String generalConstraintsSection() {
		return generalConstraintsSection = "\n\nGeneral Constraints\n\n" + forAll_List("gc",gConstraints);
	}
	
	public static String intVarsSection() {
		return "\nGeneral\n\n" + vars_1(AuxiliaryPLI.nInteger,"_y");
	}
	
	public static String binaryVarsSection() {
		return "\nBinary\n\n" +vars_1(AuxiliaryPLI.nBinary,"_x");
	}
	
	public static String boundsSection() {
		return boundsSection +forAll_1_bound(bounds.size(),i->bounds.get(i));
	}
	
	public static void setType(TipoPLI type) {
		AuxiliaryPLI.type = type;
		switch(type) {
		case Gurobi:
			end = "";
			sep = "";
			min = "Minimize\n\n";
			max = "Maximize\n\n";
			intVars = "\nGeneral\n\n";
			binaryVars = "\nBinary\n\n";
			freeVars = "\nFree\n\n";
			boundsSection = "\nBounds\n\n";
			lastEnd = "\nEnd\n\n";
			constraintsSection = "\nSubject To\n\n";			
			break;
		case LPSolve:
			end = ";";
			sep = ",";
			min = "min:  ";
			max = "max:  ";
			intVars = "\nint  ";
			binaryVars = "\nbin  ";
			freeVars = "\nfree\n\n";
			boundsSection = "\n\n";
			lastEnd = "";
			constraintsSection = "\n\n";
			break;	
		}
	}
	
	/**
	 * @param es Un secuencia de sumandos
	 * @return \( es_0 + ... + es_r \)
	 */
	public static String sum(String... es) {
		return Arrays.stream(es)
				.collect(Collectors.joining(" + "));
	}
	
	/**
	 * @param e Una expresion
	 * @param x Una variable
	 * @return \( e - x \)
	 */
	public static String difference(String e, String x) {
		return String.format("%s - %s",e,x);
	}
	
	/**
	 * @param n El rango de i en el sumatorio
	 * @param x El identificador de la variable
	 * @return \( \sum_{i=0}^{n-1} x_i\)
	 */
	public static String sum_1_v(Integer n, String x) {
		return IntStream.range(0, n).boxed()
				.map(i -> var_1(x,i))
				.collect(Collectors.joining(" + "));
	}
	
	/**
	 * @param n1 El limite inferior de i en el sumatorio
	 * @param n2 El limite superior sin incluir de i en el sumatorio
	 * @param x El identificador de la variable
	 * @return \( \sum_{i=0}^{n-1} x_i\)
	 */
	public static String sum_1_v(Integer n1, Integer n2, String x) {
		return IntStream.range(n1, n2).boxed()
				.map(i -> var_1(x,i))
				.collect(Collectors.joining(" + "));
	}
	
	/** 
	 * @param n El rango de i en el sumatorio
	 * @param x El identificador de la variable
	 * @param pd Un predicado
	 * @return \( \sum_{i=0|p(i)}^{n-1} f(i)*x_i\)
	 */
	public static String sum_1_p(Integer n, String x, 
			Predicate<Integer> pd) {
		return IntStream.range(0, n).boxed()
				.filter(pd)
				.map(i -> var_1(x,i))
				.collect(Collectors.joining(" + "));
	}
	
	/** 
	 * @param n El rango de i en el sumatorio
	 * @param x El identificador de la variable
	 * @param fc Una función que devuelve el String asociado al coeficiente de un factor
	 * @return \( \sum_{i=0}^{n-1} f(i)*x_i\)
	 */
	public static String sum_1_f(Integer n, String x, 
			Function<Integer,String> fc) {
		return IntStream.range(0, n).boxed()
				.map(i -> String.format("%s %s",fc.apply(i),var_1(x,i)))
				.collect(Collectors.joining(" + "));
	}
	
	/** 
	 * @param n1 El limite inferior i en el sumatorio
	 * @param n2 El limite superior sin incluir de i en el sumatorio
	 * @param x El identificador de la variable
	 * @param fc Una función que devuelve el String asociado al coeficiente de un factor
	 * @return \( \sum_{i=0}^{n-1} f(i)*x_i\)
	 */
	public static String sum_1_f(Integer n1, Integer n2, String x, 
			Function<Integer,String> fc) {
		return IntStream.range(n1, n2).boxed()
				.map(i -> String.format("%s %s",fc.apply(i),var_1(x,i)))
				.collect(Collectors.joining(" + "));
	}
	
	/** 
	 * @param n El rango de i en el sumatorio
	 * @param x El identificador de la variable
	 * @param pd Un predicado
	 * @param fc Una función que devuelve el String asociado al coeficiente de un factor
	 * @return \( \sum_{i=0|p(i)}^{n-1} f(i)*x_i\)
	 */
	public static String sum_1(Integer n, String x, 
			Predicate<Integer> pd, 
			Function<Integer,String> fc) {
		return IntStream.range(0, n).boxed()
				.filter(pd)
				.map(i -> String.format("%s %s",fc.apply(i),var_1(x,i)))
				.collect(Collectors.joining(" + "));
	}
	
	
	
	/**
	 * @param n El rango de i en el sumatorio
	 * @param j Una valor constante del segundo &iacute;ndice
	 * @param x El identificador de la variable
	 * @return \( \sum_{i=0}^{n-1} x_{ij}\)
	 */
	public static String sum_2_1_v(Integer n, Integer j, String x) {
		return IntStream.range(0, n).boxed()
				.map(i -> var_2(x,i,j))
				.collect(Collectors.joining(" + "));
	}
	
	/** 
	 * @param n El rango de i en el sumatorio 
	 * @param j Un valor constante del segundo &iacute;ndice
	 * @param x El identificador de la variable 
	 * @param pd Un bipredicado
	 * @return \( \sum_{i=0|pd(i,j)}^{n-1} x_{ij}\)
	 */
	public static String sum_2_1_p(Integer n, Integer j, String x, 
			BiPredicate<Integer,Integer> pd) {
		return IntStream.range(0, n).boxed()
				.filter(i->pd.test(i,j))
				.map(i -> var_2(x,i,j))
				.collect(Collectors.joining(" + "));
	}
	
	/**
	 * @param n El rango del sumatorio en i
	 * @param j Un valor constante del segundo &iacute;ndice
	 * @param x El identificador de la variable 
	 * @param fc Una función que devuelve el String asociado al coeficiente de un factor
	 * @return \( \sum_{i=0}^{n-1} f(i,j)*x_{ij}\)
	 */
	public static String sum_2_1_f(Integer n, Integer j, String x, 
			BiFunction<Integer,Integer,String> fc) {
		return IntStream.range(0, n).boxed()
				.map(i -> String.format("%s %s", fc.apply(i,j),var_2(x,i,j)))
				.collect(Collectors.joining(" + "));
	}
	
	/**
	 * @param n El rango del sumatorio en i
	 * @param j Un valor constante del segundo &iacute;ndice
	 * @param x El identificador de la variable 
	 * @param pd Un bipredicado
	 * @param fc Una función que devuelve el String asociado al coeficiente de un factor
	 * @return \( \sum_{i=0|pd(i,j)}^{n-1} f(i,j)*x_{ij}\)
	 */
	public static String sum_2_1(Integer n, Integer j, String x, 
			BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> fc) {
		return IntStream.range(0, n).boxed()
				.filter(i->pd.test(i,j))
				.map(i -> String.format("%s %s", fc.apply(i,j),var_2(x,i,j)))
				.collect(Collectors.joining(" + "));
	}
	
	/**
	 * @param n El rango de j en el sumatorio
	 * @param i Una valor constante del primer &iacute;ndice
	 * @param x El identificador de la variable
	 * @return \( \sum_{j=0}^{n-1} x_{ij}\)
	 */
	public static String sum_2_2_v(Integer n, Integer i, String x) {
		return IntStream.range(0, n).boxed()
				.map(j -> var_2(x,i,j))
				.collect(Collectors.joining(" + "));
	}
	
	/** 
	 * @param n El rango de j en el sumatorio 
	 * @param i Un valor constante del primer &iacute;ndice
	 * @param x El identificador de la variable 
	 * @param pd Un bipredicado
	 * @return \( \sum_{j=0|pd(i,j)}^{n-1} x_{ij}\)
	 */
	public static String sum_2_2_p(Integer n, Integer i, String x,
			BiPredicate<Integer,Integer> pd) {
		return IntStream.range(0, n).boxed()
				.filter(j->pd.test(i,j))
				.map(j -> var_2(x,i,j))
				.collect(Collectors.joining(" + "));
	}
	/**
	 * @param n El rango del sumatorio en j
	 * @param i Un valor constante del segundo &iacute;ndice
	 * @param x El identificador de la variable 
	 * @param fc Una función que devuelve el String asociado al coeficiente de un factor
	 * @return \( \sum_{j=0}^{n-1} f(i,j)*x_{ij}\)
	 */
	public static String sum_2_2_f(Integer n, Integer i, String x,
			BiFunction<Integer,Integer,String> fc) {
		return IntStream.range(0, n).boxed()
				.map(j -> String.format("%s %s", fc.apply(i,j),var_2(x,i,j)))
				.collect(Collectors.joining(" + "));
	}
	
	/**
	 * @param n El rango del sumatorio en j
	 * @param i Un valor constante del segundo &iacute;ndice
	 * @param x El identificador de la variable 
	 * @param pd Un bipredicado
	 * @param fc Una función que devuelve el String asociado al coeficiente de un factor
	 * @return \( \sum_{j=0|pd(i,j)}^{n-1} f(i,j)*x_{ij}\)
	 */
	public static String sum_2_2(Integer n, Integer i, String x, 
			BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> fc) {
		return IntStream.range(0, n).boxed()
				.filter(j->pd.test(i,j))
				.map(j -> String.format("%s %s", fc.apply(i,j),var_2(x,i,j)))
				.collect(Collectors.joining(" + "));
	}
	
	/**
	 * @param n El rango de i en el sumatorio
	 * @param m El rango de j en el sumatorio
	 * @param x El identificador de la variable
	 * @return \( \sum_{i=0,j=0}^{n-1,m-1} x_{ij}\)
	 */
	public static String sum_2_v(Integer n, Integer m, String x) {
		return Streams2.allPairs(n, m)
				.map(p -> var_2(x,p.first,p.second))
				.collect(Collectors.joining(" + "));
	}
	
	/** 
	 * @param n El rango de i en el sumatorio 
	 * @param m El rango de j en el sumatorio
	 * @param x El identificador de la variable 
	 * @param pd Un bipredicado
	 * @return \( \sum_{i=0,j=0|pd(i,j)}^{n-1,m-1} x_{ij}\)
	 */
	public static String sum_2_p(Integer n, Integer m, String x,
			BiPredicate<Integer, Integer> pd) {
		return Streams2.allPairs(n, m)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> var_2(x,p.first,p.second))				
				.collect(Collectors.joining(" + "));
	}
	
	
	/** 
	 * @param n El rango de i en el sumatorio 
	 * @param m El rango de j en el sumatorio
	 * @param x El identificador de la variable 
	 * @param f Una funci&oacute; que calcula el coeficiente del factor
	 * @return \( \sum_{i=0,j=0}^{n-1,m-1} f(i,j)*x_{ij}\)
	 */
	public static String sum_2_f(Integer n, Integer m, String x, 
			BiFunction<Integer,Integer,String> f) {
		return Streams2.allPairs(n, m)
				.map(p -> String.format("%s %s", f.apply(p.first,p.second),var_2(x,p.first,p.second)))
				.collect(Collectors.joining(" + "));
	}
	
	/** 
	 * @param n El rango de i en el sumatorio 
	 * @param m El rango de j en el sumatorio
	 * @param x El identificador de la variable 
	 * @param pd Un bipredicado
	 * @param fc Una función que devuelve el String asociado al coeficiente de un factor
	 * @return \( \sum_{i=0,j=0|pd(i,j)}^{n-1,m-1} f(i,j)*x_{ij} \)
	 */
	public static String sum_2(Integer n, Integer m, String x, 
			BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> fc) {
		return Streams2.allPairs(n, m)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> String.format("%s %s",fc.apply(p.first,p.second),var_2(x,p.first,p.second)))
				.collect(Collectors.joining(" + "));
	}
	
	
	/**
	 * @param n Rango de la variable i
	 * @param name El nombre del grupo de restricciones
	 * @param fr Una funci&oacute; que calcula una restricci&oacute;n
	 * @return \( \forall_{i=0}^{n-1} fc(i) \)
	 */
	public static String forAll_1(Integer n, String name,
			Function<Integer,String> fr) {
		return IntStream.range(0, n).boxed()
				.map(i -> String.format("   %s%d: %s%s",name, i, fr.apply(i),end))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	/**
	 * @param n Rango de la variable i
	 * @param fb Una funci&oacute; que calcula una cota para una variable
	 * @return \( \forall_{i=0}^{n-1} fb(i) \)
	 */
	public static String forAll_1_bound(Integer n, Function<Integer,String> fb) {
		return IntStream.range(0, n).boxed()
				.map(i -> String.format("%s%s",fb.apply(i),end))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	
	/**
	 * @param n1 L&iacute;mite inferior del rango de i
	 * @param n2 L&iacute;mite superior sin incluir del rango de i
	 * @param name El nombre del grupo de restricciones
	 * @param fr Una funci&oacute; que calcula una restricci&oacute;n
	 * @return \( \forall_{i=n1}^{n2-1} fr(i) \)
	 */
	public static String forAll_1(Integer n1, Integer n2, String name,
			Function<Integer,String> fr) {
		return IntStream.range(n1, n2).boxed()
				.map(i -> String.format("   %s%d: %s%s",name,i-n1,fr.apply(i),end))
				.collect(Collectors.joining("\n","\n","\n"));
	}	
	
	/**
	 * @param n Rango de la variable i
	 * @param name El nombre del grupo de restricciones
	 * @param pd Un predicado
	 * @param fr Una funci&oacute; que calcula una restricci&oacute;n
	 * @return \( \forall_{i=0|pd(i)}^{n-1} fr(i) \)
	 */
	public static String forAll_1(Integer n, String name,
			Predicate<Integer> pd, 
			Function<Integer,String> fr) {
		return IntStream.range(0, n).boxed()
				.filter(pd)
				.map(i -> String.format("   %s%d: %s%s",name, i, fr.apply(i),end))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	/**
	 * @param n1 L&iacute;mite inferior del rango de i
	 * @param n2 L&iacute;mite superior sin incluir del rango de i
	 * @param name El nombre del grupo de restricciones
	 * @param pd Un predicado
	 * @param fr Una funci&oacute; que calcula una restricci&oacute;n
	 * @return \( \forall_{i=n1|pd(i)}^{n2-1} fr(i) \)
	 */
	public static String forAll_1(Integer n1, Integer n2, String name,
			Predicate<Integer> pd, 
			Function<Integer,String> fr) {
		return IntStream.range(n1, n2).boxed()
				.filter(pd)
				.map(i -> String.format("   %s%d: %s%s",name, i-n1, fr.apply(i),end))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	/**
	 * @param n Rango de i
	 * @param m Rango de j
	 * @param name El nombre del grupo de restricciones
	 * @param fr Una funci&oacute; que calcula una restricci&oacute;n
	 * @return \( \forall_{i=0,j=0}^{n-1,m-1} fr(i,j) \)
	 */
	public static String forAll_2(Integer n, Integer m, String name,
			BiFunction<Integer,Integer,String> fr) {
		return Streams2.allPairs(n, m)
				.map(p -> String.format("   %s%d: %s%s",name,p.first+m*p.second,fr.apply(p.first,p.second),end))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	/**
	 * @param n Rango de i
	 * @param m Rango de j
	 * @param name El nombre del grupo de restricciones
	 * @param pd Un predicado
	 * @param fr Una funci&oacute; que calcula una restricci&oacute;n
	 * @return \( \forall_{i=0,j=0|pd(i,j)}^{n-1,m-1} fr(i,j) \)
	 */
	public static String forAll_2(Integer n, Integer m, String name,
			BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> fr) {
		return Streams2.allPairs(n, m)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> String.format("   %s%d: %s%s",name,p.first+m*p.second,fr.apply(p.first,p.second),end))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	/**
	 * @param n1 L&iacute;mite inferior del rango de i
	 * @param n2 L&iacute;mite superior sin incuir del rango de i
	 * @param m1 L&iacute;mite inferior del rango de j
	 * @param m2 L&iacute;mite superior sin incuir del rango de j
	 * @param name El nombre del grupo de restricciones
	 * @param pd Un predicado
	 * @param fr Una funci&oacute; que calcula una restricci&oacute;n
	 * @return \( \forall_{i=n1,j=m1|pd(i,j)}^{n2-1,m2-1} fr(i,j) \)
	 */
	public static String forAll_2(Integer n1, Integer n2, Integer m1, Integer m2, String name,
			BiPredicate<Integer,Integer> pd, 
			BiFunction<Integer,Integer,String> fr) {
		return Streams2.allPairs(n1, n2, m1, m2)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> String.format("   %s%d: %s%s",name,p.first-n1+(m2-m1)*(p.second-m1),fr.apply(p.first,p.second),end))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	/**
	 * @param n Rango de i
	 * @param m Rango de j
	 * @param r Rango de k
	 * @param name El nombre del grupo de restricciones
	 * @param pd Un predicado
	 * @param fr Una funci&oacute; que calcula una restricci&oacute;n
	 * @return \( \forall_{i=0,j=0, k=0|pd(i,j,j)}^{n-1,m-1,r-1} fr(i,j,k) \)
	 */
	public static String forAll_3(Integer n, Integer m, Integer r, String name, 
			TriPredicate<Integer,Integer,Integer> pd, 
			TriFunction<Integer,Integer,Integer,String> fr) {
		return Streams2.allPairs(n, m, r)
				.filter(p->pd.test(p.first,p.second,p.third))
				.map(p -> String.format("   %s%d: %s%s",name,p.first+m*p.second+m*r*p.third,fr.apply(p.first,p.second,p.third),end))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static String forAll_List(String name, List<String> constraints) {
		return IntStream.range(0,constraints.size())
				.boxed()
				.map(i->String.format("   %s%d: %s",name,i,constraints.get(i)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
		
	public static String forAll_1_List(Integer n, String name, Function<Integer,List<String>> constraints) {
		return IntStream.range(0, n).boxed()
				.flatMap(i->IntStream.range(0,constraints.apply(i).size()).boxed().map(j->IntPair.of(i,j)))			
				.map(p->String.format("   %s%d: %s",name,p.first+n*p.second,constraints.apply(p.first).get(p.second)))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	public static <E> List<E> listOf(Integer n1, Integer n2, Function<Integer,E> e){
		return IntStream.range(n1, n2).boxed()
				.map(i->e.apply(i))
				.collect(Collectors.toList());
	}
	
	/**
	 * @param n El n&uacute;mero de variables
	 * @param x El identificador de la variable 
	 * @return \( x\_0, x\_1, ... \)
	 */
	public static String vars_1(Integer n, String x) {
		return IntStream.range(0,n).boxed()
				.map(i -> var_1(x,i))
				.collect(Collectors.joining(sep+" "," ", end+"\n"));
	}
	
	/**
	 * @param n El rango de la i
	 * @param m El rango de la j
	 * @param x El identificador de la variable
	 * @return \( x\_0\_0, x_0\_1, ... \)
	 */
	public static String vars_2(Integer n, Integer m, String x) {
		return Streams2.allPairs(n, m)
				.map(p -> var_2(x, p.first, p.second))
			    .collect(Collectors.joining(sep+" ","  ",end+"\n"));
	}
	
	/**
	 * @param n El rango de la i
	 * @param m El rango de la j
	 * @param x El identificador de la variable
	 * @param pd Un predicado
	 * @return \( x\_0\_0, x_0\_1, ... \) con loa sub&iacute;dices filtrados por el predicado
	 */
	public static String vars_2_p(Integer n, Integer m, String x, BiPredicate<Integer,Integer> pd) {
		return Streams2.allPairs(n, m)
				.filter(p->pd.test(p.first,p.second))
				.map(p -> var_2(x, p.first, p.second))
			    .collect(Collectors.joining(sep+" ","  ",end+"\n"));
	}
	
	/**
	 * @param x El identificador de la variable
	 * @param i Un &iacute;dice
	 * @return \( x\_i \)
	 */
	public static String var_1(String x, Integer i) {
		return String.format("%s_%d",x,i);
	}
	
	/**
	 * @param x El identificador de la variable
	 * @param i Un &iacute;dice
	 * @param fc Un funci&oacute;n que calcula el coeficiente 
	 * @return \( fc(i) \: x_i\)
	 */
	public static String factor_1(String x, Integer i, Function<Integer,String> fc) {
		return String.format("%s %s_%d",fc.apply(i),x,i);
	}
	
	/**
	 * @param x El identificador de la variable
	 * @param i Un &iacute;dice
	 * @param j Un &iacute;dice
	 * @return \( x\_i\_j \)
	 */
	public static String var_2(String x, Integer i, Integer j) {
		return String.format("%s_%d_%d",x,i,j);
	}
	
	/**
	 * @param x El identificador de la variable
	 * @param i Un &iacute;dice
	 * @param j Un &iacute;dice
	 * @param fc Un funci&oacute;n que calcula el coeficiente 
	 * @return \( fc(i,j) \: x\_i\_j \)
	 */
	public static String factor_2(String x, Integer i, Integer j, BiFunction<Integer,Integer,String> fc) {
		return String.format("%s %s_%d_2",fc.apply(i,j),x,i,j);
	}
	
	/**
	 * @param e Un expresi&oacute;n 
	 * @param n Un n&uacute;mero
	 * @return \( e \le n \)
	 */
	public static String constraintLe(String e, Integer n) {
		return String.format("%s <= %d",e,n);
	}
	
	/**
	 * @param e Un expresi&oacute;n 
	 * @param n Un n&uacute;mero
	 * @return \( e \le n \)
	 */
	public static String constraintLe(String e, String n) {
		return String.format("%s <= %s",e,n);
	}
	/**
	 * @param e Un expresi&oacute;n 
	 * @param n Un n&uacute;mero
	 * @return \( e \ge n \)
	 */
	public static String constraintGe(String e, Integer n) {
		return String.format("%s >= %d",e,n);
	}
	
	/**
	 * @param e Un expresi&oacute;n 
	 * @param n Un n&uacute;mero
	 * @return \( e \ge n \)
	 */
	public static String constraintGe(String e, String n) {
		return String.format("%s >= %s",e,n);
	}
	
	/**
	 * @param e Un expresi&oacute;n 
	 * @param n Un n&uacute;mero
	 * @return \( e = n \)
	 */
	public static String constraintEq(String e, Integer n) {
		return String.format("%s = %d",e,n);
	}
	
	/**
	 * @param e Un expresi&oacute;n 
	 * @param n Un n&uacute;mero
	 * @return \( e = n \)
	 */
	public static String constraintEq(String e, String n) {
		return String.format("%s = %s",e,n);
	}
	
	
	/**
	 * @param x Una variable 
	 * @param exp Un expresi&oacute;n 
	 * @param n Un n&uacute;mero
	 * @return \( x = exp + n \)
	 */
	public static String constraintEq(String x, String exp, Integer n) {
		String s = constraintEq(difference(exp,x),-n);
		return s;
	}
	
	/**
	 * @pre Solo usable con Gurobi
	 * @param bv Una variable binaria
	 * @param c Una restricci&oacute;n
	 * @return \( bv = 1 \implies constrainst \)
	 */
	public static String constraintVarIndicator(String bv, String c) {
		return String.format("%s = 1 -> %s",bv,c);
	}
	
	/**
	 * @pre Solo usable con Gurobi
	 * @post Declara implicitamente variables binarias
	 * @param lc Una restriccion
	 * @param rc Una restriccion
	 * @return \( lc \implies rc\)
	 */
	public static List<String> constraintImply(String lc, String rc) {
		Integer r = AuxiliaryPLI.nBinary;
		String s1 = constraintVarIndicator(var_1("_x",r),lc);
		String s2 = constraintVarIndicator(var_1("_x",r+1),rc);
		String s3 = constraintLe(difference(var_1("_x",r),var_1("_x",r+1)),0);
		AuxiliaryPLI.nBinary += 2;
		return List.of(s1,s2,s3);
	}
	
	public static enum OrType{Eq,Le,Ge};
	
	/**
	 * @pre Solo usable con Gurobi
	 * @post Declara implicitamente variables binarias
	 * @pre \( n <= c.length \)
	 * @param n El numero de restricciones que se deben cumplir
	 * @param type El tipo de la restrccion or: segu  que sea Eq, Le, Ge el numero de restrcciones que se deben cumplir 
	 * sera igual a n, menor o igual o mayor o igual
	 * @param c las restricciones
	 * @return \( c_0 \or c_ 1 \or ... \)
	 */
	public static List<String> constraintOr(OrType type, Integer n, String... c) {
		String op = null;;
		switch(type) {
		case Eq: op = " = ";break;
		case Ge: op = " >= ";break;
		case Le: op = " <= ";break;
		}
		Preconditions.checkArgument(n>0 && n<c.length,String.format("El parametro n no cumple las restricciones y es % d",n));
		Integer r = AuxiliaryPLI.nBinary;
		List<String> lc = new ArrayList<>();
		String s;
		for (int i = 0; i < c.length; i++) {
			s = constraintVarIndicator(var_1("_x",r+i), c[i]);
			lc.add(s);
		}
		AuxiliaryPLI.nBinary += c.length;
		s = IntStream.range(0,c.length).boxed().map(i->var_1("_x",r+i))
				.collect(Collectors.joining(" + ", "", String.format(" %s %d",op,n)));
		lc.add(s);
		return lc;
	}
	
	/**
	 * @param x Identificador de una variable
	 * @param ns Numero de valores
	 * @return \( x \in ns \)
	 */
	public static List<String> valueInList(String x, List<Integer> ns) {
		Integer n = ns.size();
		Integer r = AuxiliaryPLI.nBinary;
		String s1 = constraintEq(difference(sum_1_f(r,r+n,"_x",i->ns.get(i-r).toString()),x),0);
		AuxiliaryPLI.nBinary += n;
		String s2 = constraintEq(sum_1_v(r,r+n,"_x"),1);
		return List.of(s1,s2);
	}
	
	/**
	 * @pre Solo usable con Gurobi
	 * @param x1 Una variable
	 * @param x2 Otra variable
	 * @return \( x1 != x2 \)
	 */
	public static List<String> differentValue(String x1, String x2){	
		return constraintOr(OrType.Eq,1,
				constraintGe(difference(x1,x2),1),
				constraintGe(difference(x2,x1),1));
	}
	
	/**
	 * @pre Solo usable con Gurobi
	 * @param xs Una lista de variables
	 * @return Todos los valores de las variables son diferentes
	 */
	public static List<String> allDifferentsVarsValues(List<String> xs){
		Integer n = xs.size();
		List<String> ls = Streams2.allPairs(n, n).filter(p->p.second > p.first)
			.flatMap(p->differentValue(xs.get(p.first),xs.get(p.second)).stream())
			.collect(Collectors.toList());
		return ls;
	}
	
	
	/**
	 * @param v1 Una variable
	 * @param v2 Una variable
	 * @param v3 Una variable
	 * @return \( v1 = v2 * v3 \)
	 */
	public static List<String> binaryProduct(String v1, String v2, String v3) {
		String s1 = constraintLe(difference(v1,v2),0);
		String s2 = constraintLe(difference(v1,v3),0);
		String s3 = constraintGe(difference(difference(v1,v2),v3), -1);
		return List.of(s1,s2,s3);
	}
	
	/**
	 * @param name El nombre de la restricci&oacute;n
	 * @param ct Una restricci&oacute;n
	 * @return La restricci&oacute;n en el formato adecuado
	 */
	public static String constraint(String name, String ct) {
		return String.format("   %s:  %s%s",name,ct,end+"\n");
	}
	/**
	 * @param x Una variable
	 * @param ln Un n&uacute;mero
	 * @param rn Un n&uacute;mero
	 * @return \( ln \le x \le rn \)
	 */
	public static String bound(Integer ln, String x, Integer rn) {
		return String.format("%d <= %s <= %d",ln,x,rn);
	}
	/**
	 * @param x Una variable
	 * @param ln Un n&uacute;mero
	 * @param rn Un n&uacute;mero
	 * @return \( ln \le x \le rn \)
	 */
	public static String bound(String ln, String x, String rn) {
		return String.format("%s <= %s <= %s",ln,x,rn);
	}
	/**
	 * @param x Una variable
	 * @param n Un n&uacute;mero
	 * @return \( x \le rn \)
	 */
	public static String boundLe(String x, Integer n) {
		return String.format("%s <= %d",x,n);
	}
	/**
	 * @param x Una variable
	 * @param n Un n&uacute;mero
	 * @return \( x \le rn \)
	 */
	public static String boundLe(String x, String n) {
		return String.format("%s <= %s",x,n);
	}
	
	/**
	 * @param x Una variable
	 * @param n Un n&uacute;mero
	 * @return \( x \ge rn \)
	 */
	public static String boundGe(String x, Integer n) {
		return String.format("%s >= %d",x,n);
	}

	/**
	 * @param x Una variable
	 * @param n Un n&uacute;mero
	 * @return \( x \ge rn \)
	 */
	public static String boundGe(String x, String n) {
		return String.format("%s >= %s",x,n);
	}
	
	/**
	 * @param e Una expresi&oacute;n
	 * @return La expresi&oacute;n a ser miniminizada o maximinizada en el formato adecuado
	 */
	public static String goal(String e) {
		return String.format("  %s%s",e,end);
	}
	
}
