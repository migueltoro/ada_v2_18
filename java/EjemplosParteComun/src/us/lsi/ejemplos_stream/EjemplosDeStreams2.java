package us.lsi.ejemplos_stream;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.common.Multiset;
import us.lsi.common.String2;
import us.lsi.streams.Collectors2;
import us.lsi.streams.Stream2;
import us.lsi.common.Pair;
import us.lsi.math.Math2;


public class EjemplosDeStreams2 {
	
	public static List<Pair<Long,Long>> primosPar(Long start, Integer diff, Integer limit){
		var r10 = Stream.iterate(Math2.siguientePrimo(start),x->true,x->Math2.siguientePrimo(x));
		var r11 = Stream2.consecutivePairs(r10).filter(t->t.second()-t.first()==diff).limit(limit).collect(Collectors.toList());;
		return r11;
	}
	
	public static Stream<Long> divisores(Long n){
		return Stream.iterate(2L, x-> x <= (long) Math.sqrt(n), x -> x+1).filter(x->n%x==0);
	}
	
	public static Stream<Pair<Long,Long>> primos(Long a){
		var r = Stream.iterate(Math2.siguientePrimo(a),x->Math2.siguientePrimo(x));
		var r2 = Stream2.consecutivePairs(r);
		return r2;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		var s1 = List.of(1,2,3,4,5,6,7);
		var s2 = List.of(11,12,13,14,15,16);
		var r2 = List.of(11,12,13,14,15,16,34,57);
		Stream.generate(()->Math2.getEnteroAleatorio(0, 100))
			.limit(4)
			.forEach(System.out::println);
		System.out.println("______");
		var rr = Stream.generate(()->Math2.getEnteroAleatorio(0, 100)).filter(x->x%7==0);
		System.out.println("1: ______");
		var s3 = r2.stream().map(x->x.toString());
		System.out.println("2: ______");
		var s4 = Stream.iterate(0, x->x+1);
		var s5 = Stream2.zip(s2.stream(),s4,(x,y)->Pair.of(x, y));
		s5.forEach(System.out::println);
		System.out.println("3: ______");
		var n = 14L;
		var b = 7L;
		var s6 = Stream.iterate(n,x->x>0,x->x/2);
		var s7 = Stream.iterate(Pair.of(n,b),
								t->t.first()>0,
								t->Pair.of(t.first()/2,t.second()*t.second()))
						.filter(t->t.first()%2!=0)
						.map(t->t.second())
						.reduce(1L,(x,y)->x*y);
		System.out.println("4: ______");
		var ss = Stream2.enumerate(r2.stream());
		var r3 = ss.map(t->t.toString()).collect(Collectors.joining(",","{","}"));
		System.out.println(r3);
		System.out.println("5: ______");
		var enteros = Stream.iterate(0,x->x+1);
		var ss2 = enteros.limit(4);
		var r4 = ss2.map(t->t.toString()).collect(Collectors.joining(","));
		System.out.println(r4);
		System.out.println("6: ______");
		var pp = primosPar(100L,2,3);
		String2.toConsole(pp,"Pares de primos");
		System.out.println("7: ______");
		Long m1 = 1000L;
		Long n1 = 2000L;
		Multiset<Long> rr4 = 
				Stream.iterate(m1,x->x<n1,x->x+1)
				.flatMap(x->divisores(x))
				.collect(Collectors2.toMultiset());
		System.out.println(rr4);
		System.out.println("8: ______");
		System.out.println(Math2.esDivisible(0, 15));
		System.out.println("9: ______");
		var s10 = Stream.iterate(0,x->x<100,x->x+1);
		var s11 = Stream2.cartesianProduct(s10).collect(Collectors.toList());
		System.out.println(s11);
		System.out.println("10: ______");
		var s12 = Stream.iterate(0,x->x<100,x->x+1);
		var s13 = Stream2.consecutivePairs(s12).collect(Collectors.toList());
		System.out.println(s13);
		System.out.println("11: ______");
	}

}
