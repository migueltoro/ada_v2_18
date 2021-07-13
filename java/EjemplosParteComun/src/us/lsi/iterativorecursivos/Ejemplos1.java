package us.lsi.iterativorecursivos;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.common.Comparators;
import us.lsi.common.Enumerate;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.common.Printers;
import us.lsi.streams.Stream2;
import us.lsi.iterables.Iterables;
import us.lsi.iterables.IteratorFusionOrdered;
import us.lsi.iterables.IteratorMap;
import us.lsi.math.Math2;

public class Ejemplos1 {
	
	public static Integer sumaPrimos(String file) {
		return Stream2.file(file)
				.flatMap(e->Stream2.split(e,"[ ,]"))
				.mapToInt(e->Integer.parseInt(e))
				.filter(e->Math2.esPrimo(e))
				.sum();
	}
	
	public static Integer sumaPrimos1(String file) {
		Iterator<String> fileIt = Iterables.file(file).iterator();
		Integer suma = 0;
		while(fileIt.hasNext()){
			String linea = fileIt.next();
			Iterator<String> lineaIt = Iterables.split(linea,"[ ,]").iterator();
			while(lineaIt.hasNext()){
	     		String p = lineaIt.next();
				Integer e = Integer.parseInt(p);
				if(Math2.esPrimo(e)){
	 				suma = suma + e;
				}
	 		}
		}
	 	return suma;
	}

	
	public static Integer sumaPrimos2(String file) {
		Iterable<String> fileIt = Iterables.file(file);
		Integer suma = 0;
		for(String linea: fileIt) {		
			for(String e: Iterables.split(linea, "[ ,]")) {
				Integer en = Integer.parseInt(e);
				if (Math2.esPrimo(en)) {
					suma = suma + en;
				}
			}
		}
		return suma;
	}
	
	public static Integer sumaPrimos3(String file) {
		Iterable<String> fileIt = Iterables.file(file);
		Iterable<String> ff = Iterables.flatMap(fileIt,linea->Iterables.split(linea,"[ ,]"));
		return sumaPrimos3(ff.iterator(),0);
	}
	private static Integer sumaPrimos3(Iterator<String> ff, Integer b) {
		if(ff.hasNext()) {
			String p = ff.next();
			Integer en = Integer.parseInt(p);
			if (Math2.esPrimo(en)) {
				b = b + en;
			}
			b = sumaPrimos3(ff,b);
		}
		return b;
	}
	

	public static Map<Integer,List<Integer>> agrupaPorResto1(String file,Integer n) {
		return Stream2.file(file)
				.flatMap(e->Stream2.split(e,"[ ,]"))
				.map(e->Integer.parseInt(e))
				.collect(Collectors.groupingBy(e->e%n));
	}
	
	public static Map<Integer, List<Integer>> agrupaPorResto2(String file, Integer n) {
		Iterable<String> fileIt = Iterables.file(file);
		Map<Integer, List<Integer>> grupos = new HashMap<>();
		for(String linea:fileIt) {
			for(String e:Iterables.split(linea, "[ ,]")) {
				Integer en = Integer.parseInt(e);
				Integer key = en % n;
				List<Integer> ls;
				if (grupos.containsKey(key)) {
					ls = grupos.get(key);
				} else {
					ls = new ArrayList<>();
					grupos.put(key, ls);
				}
				ls.add(en);
			}
		}
		return grupos;
	}

	public static <E> Integer index0(List<E> ls, E elem) {
		Optional<Integer> r = IntStream.range(0,ls.size()).boxed()
				.filter(i->ls.get(i).equals(elem))
				.findFirst();
		return r.isPresent()?r.get():-1;
	}

	public static <E> Integer index1(List<E> ls, E elem) {
		Integer i = 0;
		Integer n = ls.size();
		Integer b = -1;
		while(i < n && b == -1){
		   if(ls.get(i).equals(elem)) b = i;
		   i = i+1;
		}
		return b;
	}
	
	public static <E> Integer index3(List<E> ls, E elem) {
		return index3(ls,elem,0);
	}
	private static <E> Integer index3(List<E> ls, E elem, Integer i) {
		Integer b = -1;
		Integer n = ls.size();
		if(n-i > 0){
			if (ls.get(i).equals(elem)) b = i;
		    else b = index3(ls,elem,i+1);
		}
		return b;
	}

	public static <T> Integer index4(List<T> ls, T elem, Comparator<T> cmp) {
		return index4(ls,elem,0,ls.size(),cmp);
	}
	private static <T> Integer index4(List<T> ls,T elem,Integer i,Integer j, Comparator<T> cmp) {
		Integer r = -1;
		Integer k = (j+i)/2;
		if(j-i > 0){
		   if (Comparators.isEQ(ls.get(k),elem,cmp)) r = k;
		   else if (Comparators.isLT(elem,ls.get(k),cmp)) r = index4(ls,elem,i,k,cmp);
		   else r = index4(ls,elem,k+1,j,cmp);
		}
		return r;
	}

	public static <T> Integer index5(List<T> ls, T elem) {
		return index5(ls,elem,0,ls.size());
	}
	private static <T> Integer index5(List<T> ls,T elem,Integer i,Integer j) {
		Integer r = -1;
		Integer k = (j+i)/2;
		if(j-i > 0){
			if (ls.get(k).equals(elem)) 
	 			r = k;
			else {
	 			Integer r1 = index5(ls,elem,i,k);
	 			Integer r2 = index5(ls,elem,k+1,j);
	 			r = r1 != -1 ? r1: r2;
			}
		}
		return r;
	}
	
	public static record  P2(Integer i, Integer j) {
		public static P2 of(Integer i, Integer j) {
			return new P2(i,j);
		}
	}

	
	public static record P3(Integer i, Integer j) {
		public static P3 of(Integer i, Integer j) {
			return new P3(i,j);
		}
		public Integer k() {
			return (i+j)/2;
		}
	}

	public static <T> Integer index6(List<T> ls, T elem, Comparator<T> cmp) {
		P3 p = P3.of(0, ls.size());
		while ((p.j - p.i) > 0 && !Comparators.isEQ(elem, ls.get(p.k()), cmp)) {
			if (Comparators.isLT(elem, ls.get(p.k()), cmp))
				p = P3.of(p.i(), p.k());
			else
				p = P3.of(p.k() + 1, p.j);
		}
		return (p.j() - p.i() > 0 && Comparators.isEQ(elem, ls.get(p.k()), cmp)) ? p.k() : -1;
	}
	
	public static <T> Integer index7(List<T> ls, T elem, Comparator<T> cmp) {
		Function<P3,P3> next = p-> {if (Comparators.isLT(elem, ls.get(p.k()), cmp)) return P3.of(p.i(), p.k()); 
									else return P3.of(p.k() +1, p.j());};
		Stream<P3> s = Stream.iterate(P3.of(0, ls.size()), p-> (p.j()-p.i())>0, p-> next.apply(p));
		
		Optional<P3> r = s.filter(p->Comparators.isEQ(elem, ls.get(p.k()),cmp)).findFirst();
		return r.isPresent()? r.get().k() : -1;
	}
	
	public static <E> Integer index2(Iterable<E> it, E e) {
		Stream<Enumerate<E>> s = Stream2.enumerate(Stream2.asStream(it));
		Optional<Enumerate<E>> entry = s.filter(p->p.counter().equals(e)).findFirst();
		return entry.isPresent()?entry.get().counter():-1;
	}
	
	public static <T> Integer index8(Iterable<T> it, T e) {
		Integer i = 0;
		Integer b = -1;
		Iterator<T> itt = it.iterator();
		while(itt.hasNext() && b == -1){
		   T a = itt.next();
		   if(a.equals(e)) b = i;
		   i = i +1;
		}
		return b;
	}
	
	public static Integer cercano(List<Integer> ls, Integer e){
		if(ls.isEmpty()) Preconditions.checkArgument(!ls.isEmpty());
		int n = ls.size();
		return cercano(ls, e, 0, n, n/2);
	}
	public static Integer cercano(List<Integer> ls, Integer e, Integer i,Integer j,Integer k) {	
		Integer r;
	 	if(j-i == 1) {
	 		r = ls.get(i);
	 	} else if(j-i == 2){
	 		r = masCercano(e,ls.get(i),ls.get(i+1));
		} else if(e == ls.get(k)) {
	 		r = ls.get(k);
	 	} else if(e < ls.get(k)){
	 		r = cercano(ls,e,i,k+1,(i+k+1)/2);
	 	} else {
			r = cercano(ls,e,k,j,(k+j)/2);
	 	}
		return r;
	}
	public static Integer masCercano(Integer e, Integer e1, Integer e2){
	 	Integer r;
		if(Math.abs(e-e1) <= Math.abs(e-e2)){
	 		r = e1;
	 	} else {
	 		r = e2;
	 	}
	 	return r;
	}

	
	public static boolean esPalindromo1(String st) {
		UnaryOperator<P2> next = p -> P2.of(p.i+1, p.j-1);
		Stream<P2> s = Stream.iterate(P2.of(0,st.length()-1),p->(p.j-p.i)>=0, next);
		return s.allMatch(p->st.charAt(p.i) == st.charAt(p.j));
	}
	
	public static boolean esPalindromo2(String p) {
		int i = 0;
		int j= p.length()-1;
		Boolean b = true;
		while(j - i >= 0 && b) {
			b = p.charAt(i) == p.charAt(j);
			i = i + 1;
			j = j - 1;
		}
		return b;
	}
	
	public static boolean esPalindromo3(String p) {
		return esPalindromoRecursivoFinal(p,0,p.length()-1,true);
	}
	
	public static boolean esPalindromoRecursivoFinal(String p, int i, int j, Boolean b) {
		if (j - i >= 0 & b) {
			b = esPalindromoRecursivoFinal(p, i + 1, j - 1, p.charAt(i) == p.charAt(j));
		}
		return b;
	}
	
	public static boolean esPalindromo4(String p) {
		return esPalindromoRecursivoNoFinal(p,0,p.length()-1);
	}
	
	public static boolean esPalindromoRecursivoNoFinal(String p, int i, int j) {
		boolean b = true;
		if (j - i >= 0) {
			b = esPalindromoRecursivoNoFinal(p, i + 1, j - 1);
			if (b) b = p.charAt(i) == p.charAt(j);
		}
		return b;
	}

	public static Boolean esAritmetica1(List<Integer> ls) {
		Integer n = ls.size();
		if(n<3) return true;
		Integer rz = ls.get(1)-ls.get(0);
		return IntStream.range(0,n-1)
	 		.allMatch(i->(ls.get(i+1)-ls.get(i)) == rz);
	}

	public Boolean esAritmetica2(List<Integer> list) {
		Integer n = list.size();
		if(n<3) return true;
		Integer i = 0;
		Boolean b = true;
		Integer rz = list.get(1)-list.get(0);
		while(i<n-1  && b){
		   b = (list.get(i+1)-list.get(i)) == rz;
		}
		return b;
	}

	public static Boolean esAritmetica3(Iterable<Integer> it) {
		Integer a1, a2;
		Iterator<Integer> itt = it.iterator();
		if(itt.hasNext()) a1 = itt.next(); else return false;
		if(itt.hasNext()) a2 = itt.next(); else return false;
		Integer rz = a2-a1;
		Stream<Pair<Integer,Integer>> it2 = Stream2.consecutivePairs(Stream2.asStream(()->itt));
		return it2.allMatch(p->(p.second()-p.first()) == rz);
	}

	public static Boolean esAritmetica4(Iterator<Integer> it) {
		Integer a1, a2;
		if(it.hasNext()) a1 = it.next(); else return false;
		if(it.hasNext()) a2 = it.next(); else return false;
		Boolean b = true;
		Integer rz = a2-a1;
		while(it.hasNext()  && b){
		   b =  (a2-a1) == rz;
		   a1 = a2;
		   a2 = it.next();
		}
		return b;
	}

	
	public static Double valorDePolinomio(List<Double> coeficientes, Double a){
		Double b = 0.;
		Integer i;
		Double p;
		Integer n = coeficientes.size();
		i = 0; 
		p = 1.;
		while(i<n) {
			Double f = p*coeficientes.get(i);
			b = b + f;
			i = i+1;
			p = p*a;
		} 
		return b;	
	}
	
	// Coeficientes de mayor a menor
	public static Double valorDePolinomioHornerI(List<Double> digitos, Double a){
		Double b = 0.;
		Integer i = 0;
		Integer n = digitos.size();
		while(i<n) {
			Double d = digitos.get(i);
			b = b*a + d;
		    i = i +1;
		} 
		return b;	
	}

	
	// Coeficientes de menor a mayor
	public static Double valorDePolinomioHornerD(List<Double> c, Double a){
		return valorDePolinomioHornerD(c,a,0,c.size());
	}
	public static Double valorDePolinomioHornerD(
	 		List<Double> c,Double a,Integer i,Integer n){
		Double b = 0.;
		if(i<n) {
			b = valorDePolinomioHornerD(c,a,i+1,c.size());
			Double d = c.get(i);
			b = b*a + d;
		} 
		return b;	
	}

	public static List<Integer> digitos(Integer n, Integer a){
		List<Integer> r;
		if(n>0) {
			r = digitos(n/a,a);
			Integer d = n%a;
		    r.add(d);
		} else {
			r = new ArrayList<>();
		}
		return r;	
	}	

	public static Integer numeroDeCeros(Integer n, Integer a){
		Integer b = 0;
		while(n>0) {
		     Integer d = n%a;
		     if(d == 0){
		    	 b = b+1;
		     }
		     n = n/a;
		} 
		return b;	
	}

	
	public static List<Integer> digitosI(Integer n, Integer a){
		List<Integer> b = new ArrayList<>(); 
		while(n>0) {
			Integer d = n%a;
		    b.add(0,d);
			n = n/a;
		}  
		return b;	
	}
	
	public static Long entero(List<Integer> digitos,Integer a){
		Long b = 0L;
		Integer i = 0;
		Integer n = digitos.size();
		while(n-i > 0){
			Integer d = digitos.get(i);
			b = b * a + d;
			i = i+1;
		} 
		return b;
	}

	public static Integer inverso(Integer n, Integer a){
		Integer b = 0;
		while(n > 0){
			int d =n%a;
			b = b * a + d;
			n = n/a;
		}
		return b;
	}
	
	public static <E> List<E> mezclaOrdenada(List<E> ls1, List<E> ls2, Comparator<E> cmp) {
		List<E> ls3 = new ArrayList<>();
		Integer i1, i2, i3;
		Integer n1 = ls1.size();
		Integer n2 = ls2.size();
		i1 = 0;
		i2 = 0;
		i3 = 0;
		while (n1 + n2 - i3 > 0) {
			E e;
			if (i2 >= n2 || i1 < n1 && Comparators.isLE(ls1.get(i1), ls2.get(i2), cmp)) {
				e = ls1.get(i1);
				i1 = i1 + 1;
				i3 = i3 + 1;
			} else {
				e = ls2.get(i2);
				i2 = i2 + 1;
				i3 = i3 + 1;
			}
			ls3.add(e);
		}
		return ls3;
	}

	public static <E>  void mezclaOrdenada(Iterable<E> it1, Iterable<E> it2, String fileOut, Comparator<E> cmp) {
		Iterator<E> itt1 = it1.iterator();
		Iterator<E> itt2 = it2.iterator();
		PrintStream f = Printers.file(fileOut);
		E e1 = null;
		if(itt1.hasNext()) e1 = itt1.next();
		E e2 = null;
		if(itt2.hasNext()) e2 = itt2.next();
		while(e1 != null || e2 != null){
			E e;
			if(e2==null||e1!=null && Comparators.isLE(e1,e2,cmp)){
				e = e1;
				e1 = null;
				if(itt1.hasNext()) e1 = itt1.next();
			} else {
				e = e2;
				e2 = null;
				if(itt2.hasNext()) e2 = itt2.next();
			}		   
			f.println(e.toString());
		}
		f.close();
	}

	
	
	/**
	 * 
	 * @param base Base
	 * @param n Exponente
	 * @return Valor de base^n de forma iterativa
	 */
	public static Long pot2(Long base, Integer n){
		Long r = base;
		Long u = 1L;
		while( n > 0){
	       if(n%2==1){
			     u = u * r;
		   }
		   r = r * r;
		   n = n/2;
		}
		return u;
	}



	public static void main(String[] args) throws IOException {
		List<Integer> ls1 = List2.of(-1,3,5,5,7,9,13,15,15,17,19);
		Integer e = 13;
		System.out.println(String.format("0: %d, %d, %d, %d, %d, %d, %d",
				index0(ls1,e),index1(ls1,e),index3(ls1,e),
				index4(ls1,e,Comparator.naturalOrder()),
				index5(ls1,e),
				index6(ls1,e,Comparator.naturalOrder()),
				index7(ls1,e,Comparator.naturalOrder())));
		Iterator<Integer> it1 = Files.lines(Path.of("ficheros/numeros.txt")).map(x->Integer.parseInt(x)).iterator();
		Iterator<Integer> it2 = Files.lines(Path.of("ficheros/numeros.txt")).map(x->Integer.parseInt(x)).iterator();
		System.out.println(String.format("1: %d, %d",index2(()->it1,e), index8(()->it2,e)));
		List<Double> coeficientes = List2.of(0.,0.,0.,0.,1.);		
		Double v1 = valorDePolinomio(coeficientes,2.);
		Double v3 = valorDePolinomioHornerD(coeficientes,2.);
		Collections.reverse(coeficientes);
		Double v2 = valorDePolinomioHornerI(coeficientes,2.);	
		System.out.println("2: = "+v1+","+v2+","+v3);
		Integer n = 1002030789;
		Integer a = 10;
		List<Integer> ls = digitos(n,a);
		System.out.println("Digitos = "+ls);
		ls = digitosI(n,a);
		System.out.println("Digitos I = "+ls);
		System.out.println("Ceros = "+numeroDeCeros(n,a));
		System.out.println("Entero = "+n+","+entero(ls,a));
		System.out.println(String.format("3.0 %d",sumaPrimos("ficheros/numeros2.txt")));
		System.out.println(String.format("3.1 %d",sumaPrimos1("ficheros/numeros2.txt")));
		System.out.println(String.format("3.2 %d",sumaPrimos2("ficheros/numeros2.txt")));
		System.out.println(String.format("3.3 %d",sumaPrimos3("ficheros/numeros2.txt")));
		var r1 = agrupaPorResto1("ficheros/numeros2.txt",5);
		var r2 = agrupaPorResto2("ficheros/numeros2.txt",5);
		System.out.println("4 \n "+r1);
		System.out.println("5 \n "+r2);
		String text = "reordenanedroer";
		String text2 = "reordenanefroer";
		System.out.println(String.format("4: %b, %b, %b, %b",
				esPalindromo1(text),esPalindromo2(text),esPalindromo3(text),esPalindromo4(text)));
		System.out.println(String.format("4: %b, %b, %b, %b",
				esPalindromo1(text2),esPalindromo2(text2),esPalindromo3(text2),esPalindromo4(text2)));
		List<Integer> l1 = List2.of(1,3,5,7,9,11);
		List<Integer> l2 = List2.of(0,2,4,10,19,21,23,45);
		List<Integer> l3 = mezclaOrdenada(l2,l1, Comparator.naturalOrder());
		System.out.println(String.format("5: %s",l3));
		Iterable<String> f1 = Iterables.file("ficheros/numeros3.txt");
		Iterable<Integer> f11 = IteratorMap.of(f1, x->Integer.parseInt(x));
		Iterable<String> f2 = Iterables.file("ficheros/numeros4.txt");
		Iterable<Integer> f22 = IteratorMap.of(f2, x->Integer.parseInt(x));
		mezclaOrdenada(f11,f22,"ficheros/numeros5.txt",Comparator.naturalOrder());
		f1 = Iterables.file("ficheros/numeros3.txt");
		f11 = IteratorMap.of(f1, x->Integer.parseInt(x));
		f2 = Iterables.file("ficheros/numeros4.txt");
		f22 = IteratorMap.of(f2, x->Integer.parseInt(x));
		Iterable<Integer> f3 = IteratorFusionOrdered.of(f11,f22,Comparator.naturalOrder());
		PrintStream p = Printers.file("ficheros/numeros6.txt");
		for(Integer er:f3) p.println(er.toString());
	}
	
	

}
