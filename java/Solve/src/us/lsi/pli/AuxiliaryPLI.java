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

import us.lsi.common.Lists2;
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
	 * Un contador de las variables binarias que se van creando implicitamente
	 */
	public static Integer nBinary = 0;
	/**
	 * Un contador de las variables enteras que se van creando implicitamente
	 */
	public static Integer nInteger = 0;
	/**
	 * La lista de variables libres
	 */
	public static List<String> freeVarsList = new ArrayList<>();
	/**
	 * Restricciones que se van creando implicitamente
	 */
	public static List<String> gConstraints = new ArrayList<>();
	/**
	 * Restricciones sos que se van creando implicitamente
	 */
	public static List<String> sosConstraints = new ArrayList<>();
	/**
	 * Cotas que se van creando implicitamente
	 */
	public static List<String> bounds = new ArrayList<>();
	/**
	 * Una constante para emular la restriccion con variable indicadora
	 */
	public static Integer M = 1000;
	
	private static String end = "";
	private static String sep = " ";
	private static String min = "Minimize\n\n";
	private static String max = "Maximize\n\n";
	private static String intVars = "\nGeneral\n\n";
	private static String binaryVars = "\nBinary\n\n";
	private static String freeVars = "\nFree\n\n";
	private static String boundsSection = "\n\nBounds\n\n";
	private static String lastEnd = "\nEnd\n\n";
	private static String constraintsSection = "\n\n\nSubject To\n\n";
	private static String generalConstraintsSection = "\n\nGeneral Constraints\n\n";
	
	public static enum PLIType{Gurobi,LPSolve}
	public static PLIType type = PLIType.Gurobi;
	
	public static String generalConstraintsSection() {
		StringBuilder r = new StringBuilder();		
		if(!gConstraints.isEmpty()) {
			r.append(String.format("%s %s\n",generalConstraintsSection,forAll("gc",gConstraints)));
		}
		if(!sosConstraints.isEmpty()) {
			r.append(sosConstraints.stream().map(x->"SOS: "+x+end).collect(Collectors.joining("\n","\n\nsos\n","\n")));
		}
		return r.toString();
	}
	
	@SafeVarargs
	public static String intVarsSection(List<String>... vs) {
		List<String> r = Arrays.stream(vs).flatMap(x->x.stream()).collect(Collectors.toList());
		r.addAll(listOf(0,AuxiliaryPLI.nInteger, i->v("y$",i))); 
		if(r.isEmpty()) return "";
		return r.stream().collect(Collectors.joining(sep,intVars,end));
	}
	
	@SafeVarargs
	public static String binaryVarsSection(List<String>... vs) {
		List<String> r = Arrays.stream(vs).flatMap(x->x.stream()).collect(Collectors.toList());
		r.addAll(listOf(0,AuxiliaryPLI.nBinary, i->v("x$",i))); 
		if(r.isEmpty()) return "";
		return r.stream().collect(Collectors.joining(sep,binaryVars,end));
	}
	
	
	public static String freeVarsSection() {
		List<String> r = freeVarsList.stream().collect(Collectors.toList()); 
		if(r.isEmpty() || AuxiliaryPLI.type == PLIType.Gurobi) return "";
		return r.stream().collect(Collectors.joining(sep,freeVars,end));
	}
	
	@SafeVarargs
	public static String boundsSection(List<String>... bnd) {
		List<String> r = Arrays.stream(bnd).flatMap(x->x.stream()).collect(Collectors.toList()); 
		r.addAll(bounds);
		return IntStream.range(0,r.size()).boxed()			
			.filter(i->!r.get(i).isEmpty())
			.map(i -> String.format("   %s%s",r.get(i),end))
			.collect(Collectors.joining("\n",boundsSection,"\n"));
	}
	
	public static void setType(PLIType type) {
		AuxiliaryPLI.type = type;
		switch(type) {
		case Gurobi:
			end = "";
			sep = " ";
			min = "Minimize\n\n";
			max = "Maximize\n\n";
			intVars = "\nGeneral\n\n";
			binaryVars = "\nBinary\n\n";
			freeVars = "\nFree\n\n";
			boundsSection = "\nBounds\n\n";
			lastEnd = "\n\nEnd\n\n";
			constraintsSection = "\n\nSubject To\n\n";
			generalConstraintsSection = "\n\nGeneral Constraints\n\n";
			break;
		case LPSolve:
			end = ";";
			sep = ",";
			min = "min:  ";
			max = "max:  ";
			intVars = "\nint  ";
			binaryVars = "\nbin  ";
			freeVars = "\nfree  ";
			boundsSection = "\n\n";
			lastEnd = "";
			constraintsSection = "\n\n";
			generalConstraintsSection = "";
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
	
	public static String sum(List<String> ls) {
		return ls.stream().collect(Collectors.joining(" + "));
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
	 * @param name El nombre del grupo de restricciones
	 * @param fr Una funci&oacute; que calcula una restricci&oacute;n
	 * @return \( \forall_{i=n1}^{n2-1} fc(i) \)
	 */
	public static String forAll(String name, List<String> fr) {
		return IntStream.range(0, fr.size()).boxed()
				.map(i -> String.format("   %s%d: %s%s",name,i,fr.get(i),end))
				.collect(Collectors.joining("\n","\n","\n"));
	}
	
	/**
	 * @param name El nombre del grupo de restricciones
	 * @param fr Una funci&oacute; que calcula una restricci&oacute;n
	 * @return \( \forall_{i=n1}^{n2-1} fc(i) \)
	 */
	public static String forAll(String name,String... fr) {
		return IntStream.range(0,fr.length).boxed()
				.map(i->String.format("   %s%d: %s%s",name,i,fr[i],end))
				.collect(Collectors.joining("\n"));				
	}
	
	public static  String endSection() {
		return AuxiliaryPLI.lastEnd;
	}
		
	public static <E> List<E> listOf(List<List<E>> elems){
		return elems.stream().flatMap(x->x.stream()).collect(Collectors.toList());
	}
	
	public static <E> List<E> listOf(@SuppressWarnings("unchecked") List<E>... elems){
		return Arrays.stream(elems).flatMap(x->x.stream()).collect(Collectors.toList());
	}
	
	public static <E> List<E> listOf(@SuppressWarnings("unchecked") E... elems){
		return Arrays.asList(elems);
	}
	
	public static <E> List<E> listOf(int n1, int n2, 
			Function<Integer,E> e){
		return IntStream.range(n1, n2).boxed()
				.map(i->e.apply(i))
				.collect(Collectors.toList());
	}
	
	public static <E> List<E> listOf(int n1, int n2, 
			Predicate<Integer> pd, Function<Integer,E> e){
		return IntStream.range(n1, n2).boxed()
				.filter(pd)
				.map(i->e.apply(i))
				.collect(Collectors.toList());
	}

	
	public static <E> List<E> listOf(int n1, int n2, int m1, int m2, 
			BiFunction<Integer,Integer,E> e){
		return Streams2.allPairs(n1, n2, m1, m2)
				.map(p->e.apply(p.first,p.second))
				.collect(Collectors.toList());
	}
	
	public static <E> List<E> listOf(int n1, int n2, int m1, int m2, 
			BiPredicate<Integer,Integer> pd, BiFunction<Integer,Integer,E> e){
		return Streams2.allPairs(n1, n2, m1, m2)
				.filter(p->pd.test(p.first,p.second))
				.map(p->e.apply(p.first,p.second))
				.collect(Collectors.toList());
	}
	
	public static <E> List<E> listOf(int n1, int n2, int m1, int m2, int r1, int r2, 
			TriFunction<Integer,Integer,Integer,E> e){
		return Streams2.allPairs(n1, n2, m1, m2, r1,r2)
				.map(p->e.apply(p.first,p.second,p.third))
				.collect(Collectors.toList());
	}
	
	public static <E> List<E> listOf(int n1, int n2, int m1, int m2, int r1, int r2, 
			TriPredicate<Integer,Integer,Integer> pd, TriFunction<Integer,Integer,Integer,E> e){
		return Streams2.allPairs(n1, n2, m1, m2, r1,r2)
				.filter(p->pd.test(p.first,p.second,p.third))
				.map(p->e.apply(p.first,p.second,p.third))
				.collect(Collectors.toList());
	}
	
	
	/**
	 * @param x El identificador de la variable
	 * @param i Un &iacute;dice
	 * @return \( x\_i \)
	 */
	public static String v(String x, int i) {
		return String.format("%s_%d",x,i);
	}
	
	/**
	 * @param x El identificador de la variable
	 * @param i Un &iacute;dice
	 * @param j Un &iacute;dice
	 * @return \( x\_i\_j \)
	 */
	public static String v(String x, int i, int j) {
		return String.format("%s_%d_%d",x,i,j);
	}
	
	public static String f(Integer c, String x, Integer i) {
		return String.format("%d %s_%d",c,x,i);
	}
	
	public static String f(Double c, String x, Integer i) {
		return String.format("%.2f %s_%d",c,x,i);
	}
	
	public static String f(Integer c, String x, Integer i, Integer j) {
		return String.format("%d %s_%d_%d",c,x,i,j);
	}
	
	public static String f(Double c, String x, Integer i, Integer j) {
		return String.format("%.2f %s_%d_%d",c,x,i,j);
	}

	
	
	public static String varFree(String x) {
		String s = "";
		switch(type) {
		case Gurobi: s = constraintGe(x,-1000000);break;
		case LPSolve: freeVarsList.add(x);break;
		}
		return s;
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
	public static String constraintLe(String e, Double n) {
		return String.format("%s <= %.2f",e,n);
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
	public static String constraintGe(String e, Double n) {
		return String.format("%s >= %.2f",e,n);
	}
	
	/**
	 * @param e Un expresi&oacute;n 
	 * @param n Un n&uacute;mero
	 * @return \( e \ge n \)
	 */
	public static String constraintBound(Integer ln, String e, Integer rn) {
		return String.format("%d <= %s <= %d",ln,e,rn);
	}
	
	
	/**
	 * @param e Un expresi&oacute;n 
	 * @param n Un n&uacute;mero
	 * @return \( e \le n \)
	 */
	public static String constraintBound(Double ln, String e, Double rn) {
		return String.format("%.2f <= %s <= %.2f",ln,e,rn);
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
	public static String constraintEq(String e, Double n) {
		return String.format("%s = %.2f",e,n);
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
	
	public static String constraintEq(String x, String exp, Double n) {
		String s = constraintEq(difference(exp,x),-n);
		return s;
	}
	
	/**
	 * @param abs
	 * @param exp
	 * @return \( abs = |exp| \)
	 */
	public static List<String> constraintAbs(String abs, String exp) {
		Integer r = AuxiliaryPLI.nInteger;
		String s1 = constraintEq(v("y$",r),exp,0);
		String s2;
		String s3;
		List<String> s = null;;
		switch(type) {
		case Gurobi: 
			String s0 = String.format("%s = ABS ( %s )",abs,v("y$",r));
			gConstraints.add(s0);
			AuxiliaryPLI.nInteger += 1;
			s = Lists2.of(s1);
			break;
		case LPSolve: 
			s2 = constraintEq(sum(v("y$",r),f(-1,"y$",r+1),v("y$",r+2)),0);
			s3 = constraintEq(sum(abs,f(-1,"y$",r+1),f(-1,"y$",r+2)),0);
			String s4 = constraintLe(sum(v("y$",r+1),v("y$",r+2)),1);
			sosConstraints.add(s4);
			AuxiliaryPLI.nInteger += 3;
			s = Lists2.of(s1,s2,s3);
			break;
		}
		return s;
	}
	
	/**
	 * @pre Solo usable con Gurobi
	 * @param bv Una variable binaria
	 * @param c Una restricci&oacute;n
	 * @return \( bv = 1 \implies constrainst \)
	 */
	public static String constraintIndicator(String bv, String c) {
		String s = "";
		switch(type) {
		case Gurobi:
			s = String.format("%s = 1 -> %s",bv,c);
			break;
		case LPSolve:
			s = String.format("%s - %s %s + %s",M,M,bv,c);
			break;
		}
		return s;
	}
	
	/**
	 * @post Declara implicitamente variables binarias
	 * @param lc Una restriccion
	 * @param rc Una restriccion
	 * @return \( lc \implies rc\)
	 */
	public static List<String> constraintImply(String lc, String rc) {
		Integer r = AuxiliaryPLI.nBinary;
		String s1 = constraintIndicator(v("x$",r),lc);
		String s2 = constraintIndicator(v("x$",r+1),rc);
		String s3 = constraintLe(difference(v("x$",r),v("x$",r+1)),0);
		AuxiliaryPLI.nBinary += 2;
		return Lists2.of(s1,s2,s3);
	}
	
	/**
	 * @post Declara implicitamente variables binarias
	 * @param lv Una variable binaria
	 * @param rv Una variable binaria
	 * @return \( lv \implies rv\)
	 */
	public static String constraintBinaryImply(String lv, String rv) {
		return constraintLe(difference(lv,rv),0);
	}
	
	public static enum RelationalOp{Eq,Le,Ge};
	
	/**
	 * @param op Tipo de restriccion
	 * @param e Un expresi&oacute;n 
	 * @param n Un n&uacute;mero
	 * @return \( e  op n \)
	 */
	public static String constraintOr(RelationalOp op, String e, Integer n) {
		String s = "";
		switch(op) {
		case Eq: s = constraintEq(e,n); break;
		case Ge: s = constraintGe(e,n); break;
		case Le: s = constraintLe(e,n); break;
		}
		return s;
	}
	
	/**
	 * @post Declara implicitamente variables binarias
	 * @pre \( n <= c.length \)
	 * @param n El numero de restricciones que se deben cumplir
	 * @param type El tipo de la restrccion or: segu  que sea Eq, Le, Ge el numero de restrcciones que se deben cumplir 
	 * sera igual a n, menor o igual o mayor o igual
	 * @param c las restricciones
	 * @return \( c_0 \or c_ 1 \or ... \)
	 */
	public static List<String> constraintOr(RelationalOp type, Integer n, String... c) {
		Preconditions.checkArgument(n>0 && n<c.length,String.format("El parametro n no cumple las restricciones y es % d",n));
		Integer r = AuxiliaryPLI.nBinary;
		String s;
		List<String> lc = listOf(0,c.length,i->constraintIndicator(v("x$",r+i), c[i]));
		AuxiliaryPLI.nBinary += c.length;
		s = constraintOr(type,sum(listOf(r,r+c.length,i->v("x$",i))),1);
		lc.add(s);
		return lc;
	}
	
	public static String constraintsSection() {
		return AuxiliaryPLI.constraintsSection;
	}
	
	/**
	 * @param x Identificador de una variable
	 * @param ns Numero de valores
	 * @return \( x \in ns \)
	 */
	public static List<String> constraintValueInList(String x, List<Integer> ns) {
		Integer n = ns.size();
		Integer r = AuxiliaryPLI.nBinary;
		String s1 = constraintEq(difference(sum(listOf(r,r+n,i->f(ns.get(i-r),"x$",i))),x),0);
		AuxiliaryPLI.nBinary += n;
		String s2 = constraintEq(sum(listOf(r,r+n,i->v("x$",i))),1);
		return Lists2.of(s1,s2);
	}
	
	/**
	 * @pre Solo usable con Gurobi
	 * @param x1 Una variable
	 * @param x2 Otra variable
	 * @return \( x1 != x2 \)
	 */
	public static List<String> constraintDifferentValue(String x1, String x2){	
		List<String> ls = null;
		switch(type) {
		case Gurobi:
			ls = constraintOr(RelationalOp.Eq,1,
					constraintGe(difference(x1,x2),1),
					constraintGe(difference(x2,x1),1));
			break;
		case LPSolve:
			Integer r = AuxiliaryPLI.nBinary;
			String s1 = constraintGe(String.format("%s - %s + %s - %s %s",x1,x2,M,M,v("x$",r)),1);
			String s2 = constraintGe(String.format("%s - %s + %s - %s %s",x2,x1,M,M,v("x$",r+1)),1);
			String s3 = constraintEq(sum(v("x$",r),v("x$",r+1)),1);
			AuxiliaryPLI.nBinary += 2;
			ls = Lists2.of(s1,s2,s3);
			break;
		}
		return  ls;
	}
	
//	public static List<String> constraintDifferentValue(String x1, String x2){	
//		Integer r = AuxiliaryPLI.nInteger;
//		AuxiliaryPLI.nInteger += 1;
//		List<String> s1 = constraintAbs(v("y$",r),sum(difference(x1,x2)));
//		String s2 = constraintGe(v("y$",r),1);
//		s1.add(s2);
//		return s1;
//	}
	
	/**
	 * @param xs Una lista de variables
	 * @return Todos los valores de las variables son diferentes
	 */
	public static List<String> constraintAllDifferents(List<String> xs){
		Integer n = xs.size();
		List<String> ls = Streams2.allPairs(0,n,0,n).filter(p->p.second > p.first)
			.flatMap(p->constraintDifferentValue(xs.get(p.first),xs.get(p.second)).stream())
			.collect(Collectors.toList());
		return ls;
	}
	
	/**
	 * @param xs Una lista de variables
	 * @return Todos los valores de las variables son diferentes
	 */
	public static List<String> constraintAllDifferents(String... xs){
		Integer n = xs.length;
		List<String> ls = Streams2.allPairs(0,n,0,n)
			.filter(p->p.second > p.first)
			.flatMap(p->constraintDifferentValue(xs[p.first],xs[p.second]).stream())
			.collect(Collectors.toList());
		return ls;
	}
	
	/**
	 * @param xs Una lista de variables
	 * @return Todos los valores de las variables son diferentes
	 */
	public static List<String> constraintStrictIncreasing(List<String> xs){
		return Streams2.consecutivePairs(xs.stream())
			.map(p->constraintGe(difference(p.second,p.first),1))
			.collect(Collectors.toList());
	}
	
	/**
	 * @param xs Una lista de variables
	 * @return Todos los valores de las variables son diferentes
	 */
	public static List<String> constraintIncreasing(List<String> xs){
		return Streams2.consecutivePairs(xs.stream())
			.map(p->constraintGe(difference(p.second,p.first),0))
			.collect(Collectors.toList());
	}
	
	/**
	 * @param v1 Una variable
	 * @param v2 Una variable
	 * @param v3 Una variable
	 * @return \( v1 = v2 * v3 \)
	 */
	public static List<String> constraintBinaryProduct(String v1, String v2, String v3) {
		String s1 = constraintLe(difference(v1,v2),0);
		String s2 = constraintLe(difference(v1,v3),0);
		String s3 = constraintGe(difference(difference(v1,v2),v3), -1);
		return Lists2.of(s1,s2,s3);
	}

	/**
	 * @param e Una expresi&oacute;n
	 * @return La expresi&oacute;n a ser miniminizada o maximinizada en el formato adecuado
	 */
	public static String goalMaxSection(String e) {
		return String.format("%s  %s%s",max,e,end);
	}
	
	/**
	 * @param e Una expresi&oacute;n
	 * @return La expresi&oacute;n a ser miniminizada o maximinizada en el formato adecuado
	 */
	public static String goalMinSection(String e) {
		return String.format("%s  %s%s",min,e,end);
	}
	
}
