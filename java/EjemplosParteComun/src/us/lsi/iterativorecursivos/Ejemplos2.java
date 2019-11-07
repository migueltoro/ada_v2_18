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
import us.lsi.common.Lists2;
import us.lsi.common.Pair;
import us.lsi.flujossecuenciales.IteratorMap;
import us.lsi.flujossecuenciales.IteratorOrdered;
import us.lsi.flujossecuenciales.Iterators;
import us.lsi.flujossecuenciales.Printers;
import us.lsi.flujossecuenciales.Streams;
import us.lsi.math.Math2;

public class Ejemplos2 {
	
	public static Integer sumaPrimos1(String file) {
		return Streams.file(file)
				.flatMap(e->Streams.split(e,"[ ,]"))
				.mapToInt(e->Integer.parseInt(e))
				.filter(e->Math2.esPrimo(e))
				.sum();
	}
	
	public static Integer sumaPrimos2(String file) {
		Iterator<String> fileIt = Iterators.file(file);
		Integer suma = 0;
		while (fileIt.hasNext()) {
			String linea = fileIt.next();
			Iterator<String> lineaIt = Iterators.split(linea, "[ ,]");
			while (lineaIt.hasNext()) {
				String e = lineaIt.next();
				Integer en = Integer.parseInt(e);
				if (Math2.esPrimo(en)) {
					suma = suma + en;
				}
			}
		}
		return suma;
	}

	public static Map<Integer,List<Integer>> agrupaPorResto1(String file,Integer n) {
		return Streams.file(file)
				.flatMap(e->Streams.split(e,"[ ,]"))
				.map(e->Integer.parseInt(e))
				.collect(Collectors.groupingBy(e->e%n));
	}
	
	public static Map<Integer, List<Integer>> agrupaPorResto2(String file, Integer n) {
		Iterator<String> fileIt = Iterators.file(file);
		Map<Integer, List<Integer>> grupos = new HashMap<>();
		while (fileIt.hasNext()) {
			String linea = fileIt.next();
			Iterator<String> lineaIt = Iterators.split(linea, "[ ,]");
			while (lineaIt.hasNext()) {
				String e = lineaIt.next();
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

	public static <T> Integer index6(List<T> ls, T elem, Comparator<T> cmp) {
		P3 p = P3.of(0, ls.size());
		while ((p.j - p.i) > 0 && !Comparators.isEQ(elem, ls.get(p.k), cmp)) {
			if (Comparators.isLT(elem, ls.get(p.k), cmp))
				p = P3.of(p.i, p.k);
			else
				p = P3.of(p.k + 1, p.j);
		}
		return (p.j - p.i > 0 && Comparators.isEQ(elem, ls.get(p.k), cmp)) ? p.k : -1;
	}
	
	public static <T> Integer index7(List<T> ls, T elem, Comparator<T> cmp) {
		Function<P3,P3> next = p-> {if (Comparators.isLT(elem, ls.get(p.k), cmp)) return P3.of(p.i, p.k); 
									else return P3.of(p.k +1, p.j);};
		Stream<P3> s = Stream.iterate(P3.of(0, ls.size()), p-> (p.j-p.i)>0, p-> next.apply(p));
		
		Optional<P3> r = s.filter(p->Comparators.isEQ(elem, ls.get(p.k),cmp)).findFirst();
		return r.isPresent()? r.get().k : -1;
	}
	
	public static <E> Integer index2(Iterator<E> it, E e) {
		Stream<Pair<E, Integer>> s = Streams.enumerate(it);
		Optional<Pair<E, Integer>> entry = s.filter(p->p.a.equals(e)).findFirst();
		return entry.isPresent()?entry.get().b:-1;
	}
	
	public static <T> Integer index8(Iterator<T> it, T e) {
		Integer i = 0;
		Integer b = -1;
		while(it.hasNext() && b == -1){
		   T a = it.next();
		   if(a.equals(e)) b = i;
		   i = i +1;
		}
		return b;
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

	public static Boolean esAritmetica3(Iterator<Integer> it) {
		Integer a1, a2;
		if(it.hasNext()) a1 = it.next(); else return false;
		if(it.hasNext()) a2 = it.next(); else return false;
		Integer rz = a2-a1;
		Stream<Pair<Integer,Integer>> it2 = Streams.consecutivePairs(it);
		return it2.allMatch(p->(p.b-p.a) == rz);
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

	public static <E>  void mezclaOrdenada(Iterator<E> it1, Iterator<E> it2, String fileOut, Comparator<E> cmp) {	
		PrintStream f = Printers.file(fileOut);
		E e1 = null;
		if(it1.hasNext()) e1 = it1.next();
		E e2 = null;
		if(it2.hasNext()) e2 = it2.next();
		while(e1 != null || e2 != null){
			E e;
			if(e2==null||e1!=null && Comparators.isLE(e1,e2,cmp)){
				e = e1;
				e1 = null;
				if(it1.hasNext()) e1 = it1.next();
			} else {
				e = e2;
				e2 = null;
				if(it2.hasNext()) e2 = it2.next();
			}		   
			f.println(e.toString());
		}
		f.close();
}



	public static void main(String[] args) throws IOException {
		List<Integer> ls1 = Lists2.newList(-1,3,5,5,7,9,13,15,15,17,19);
		Integer e = 13;
		System.out.println(String.format("0: %d, %d, %d, %d, %d, %d, %d",
				index0(ls1,e),index1(ls1,e),index3(ls1,e),
				index4(ls1,e,Comparator.naturalOrder()),
				index5(ls1,e),
				index6(ls1,e,Comparator.naturalOrder()),
				index7(ls1,e,Comparator.naturalOrder())));
		Iterator<Integer> it1 = Files.lines(Path.of("ficheros/numeros.txt")).map(x->Integer.parseInt(x)).iterator();
		Iterator<Integer> it2 = Files.lines(Path.of("ficheros/numeros.txt")).map(x->Integer.parseInt(x)).iterator();
		System.out.println(String.format("1: %d, %d",index2(it1,e), index8(it2,e)));
		List<Double> coeficientes = Lists2.newList(0.,0.,0.,0.,1.);		
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
		System.out.println(String.format("3: %d, %d",sumaPrimos1("ficheros/numeros2.txt"),sumaPrimos2("ficheros/numeros2.txt")));
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
		List<Integer> l1 = Lists2.newList(1,3,5,7,9,11);
		List<Integer> l2 = Lists2.newList(0,2,4,10,19,21,23,45);
		List<Integer> l3 = mezclaOrdenada(l2,l1, Comparator.naturalOrder());
		System.out.println(String.format("5: %s",l3));
		Iterator<String> f1 = Iterators.file("ficheros/numeros3.txt");
		Iterator<Integer> f11 = IteratorMap.of(f1, x->Integer.parseInt(x));
		Iterator<String> f2 = Iterators.file("ficheros/numeros4.txt");
		Iterator<Integer> f22 = IteratorMap.of(f2, x->Integer.parseInt(x));
		mezclaOrdenada(f11,f22,"ficheros/numeros5.txt",Comparator.naturalOrder());
		f1 = Iterators.file("ficheros/numeros3.txt");
		f11 = IteratorMap.of(f1, x->Integer.parseInt(x));
		f2 = Iterators.file("ficheros/numeros4.txt");
		f22 = IteratorMap.of(f2, x->Integer.parseInt(x));
		Iterator<Integer> f3 = IteratorOrdered.of(f11,f22,Comparator.naturalOrder());
		PrintStream p = Printers.file("ficheros/numeros6.txt");
		while(f3.hasNext()) p.println(f3.next().toString());
	}
	
	public static class P2 {
		public Integer i, j;
		public static P2 of(Integer i, Integer j) {
			return new P2(i,j);
		}
		private P2(Integer i, Integer j) {
			super();
			this.i = i;
			this.j = j;
		}
	}

	
	public static class P3 {
		public Integer i, j, k;
		public static P3 of(Integer i, Integer j) {
			return new P3(i,j);
		}
		private P3(Integer i, Integer j) {
			super();
			this.i = i;
			this.j = j;
			this.k = (i+j)/2;
		}
	}

}
