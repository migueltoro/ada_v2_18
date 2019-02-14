package us.lsi.iterativos;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.common.Streams2;
import us.lsi.common.Tuple;
import us.lsi.common.Comparator2;

public class PrimerosEjemplos {

	public static Boolean esProgresionAritmetica(List<Integer> ls) {
		if(ls.size() <=2) return true;
		Integer k = ls.get(1)-ls.get(0);
		return Streams2.consecutivePairs(ls.stream())
				       .allMatch(p->p.v2-p.v1 == k);
	}
	
	
	public static Boolean all(List<Integer> ls, Predicate<Integer> p) {
		Integer n = ls.size();
		var r =  Stream.iterate(
					Tuple.create(0,true),
					t->Tuple.create(t.v1+1,p.test(ls.get(t.v1)))
				 )
				.dropWhile(t->(t.v1<n && t.v2 ))
				.findFirst();
		return r.get().v2;
		
	}
	
	public static Boolean any(List<Integer> ls, Predicate<Integer> p) {
		Integer n = ls.size();
		var r =  Stream.iterate(
					Tuple.create(0,false),
					t->Tuple.create(t.v1+1,p.test(ls.get(t.v1)))
				 )
				.dropWhile(t->(t.v1<n && !t.v2 ))
				.findFirst();
		return r.get().v2;
		
	}
	
	public static Integer factorial1(Integer n) {
		return IntStream.rangeClosed(1,n)
				        .reduce(1, (x,y)->x*y);
	}
	
	public static Integer factorial2(Integer n) {
		var r =  Stream.iterate(Tuple.create(1,1),t->Tuple.create(t.v1+1,t.v2*(t.v1)))
				.dropWhile(t->t.v1<=n)
				.findFirst();
		return r.get().v2;
	}
	
	public static Integer factorial3(Integer n) {
		var t  = Tuple.create(1,1);
		while(t.v1 <= n){
		   t = Tuple.create(t.v1+1, t.v2*(t.v1));
		}
		return t.v2;
	}
	
	public static Integer factorial4(Integer n) {
		Integer e = 1;
		Integer a = 1;
		while(e <= n){	   
		   a = a * e;
		   e = e + 1;		   
		}
		return a;
	}
	
	public static Comparator<Integer> cm = Comparator.naturalOrder();
	
	public static <T> Boolean estaOrdenada1(List<T> ls, Comparator<T> cm){
		return IntStream.range(0, ls.size()-1)
				        .allMatch(i->Comparator2.isLE(ls.get(i),ls.get(i+1),cm));
	}
	
	public static <T> Boolean estaOrdenada2(List<T> ls, Comparator<T> cm){
		return Streams2.consecutivePairs(ls.stream())
				       .allMatch(t->Comparator2.isLE(t.v1,t.v2,cm));
	}
	
	public static void main(String[] args) {
		var ls = List.of(2,4,6,17,10,12);
		var r = esProgresionAritmetica(ls);
		System.out.println(r);
		var s = factorial1(10);
		System.out.println("f1 ="+s);
		var s2 = factorial2(10);
		System.out.println("f2 ="+s2);
		var s5 = factorial3(10);
		System.out.println("f3 ="+s5);
		var s7 = factorial4(10);
		System.out.println("f4 ="+s7);
		var lr = List.of(2,4,6,8,10);
		var s3 = all(lr,x->x%17==0);		
		System.out.println(s3);
		var s4 = any(lr,x->x%17==0);
		System.out.println(s4);
		var b1 = estaOrdenada1(ls,cm);
		System.out.println(b1);
		var b2 = estaOrdenada2(ls,cm);
		System.out.println(b2);
	}

}
