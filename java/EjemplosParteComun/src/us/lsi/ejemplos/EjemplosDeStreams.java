package us.lsi.ejemplos;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.common.SeqAccumulators;
import us.lsi.common.Streams2;
import us.lsi.common.Tuple;
import us.lsi.math.Math2;



public class EjemplosDeStreams {
	
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
		System.out.println(Streams2.accumulateLeft(rr, SeqAccumulators.first()).get());
		System.out.println("______");
		var s3 = r2.stream().map(x->x.toString());
		var a = SeqAccumulators.joiningAccumulator(" ","{","}");
		var r = Streams2.accumulateLeft(s3, a);
		System.out.println(r);	
		System.out.println("______");
		var s4 = Stream.iterate(0, x->x+1);
		var s5 = Streams2.zip(s2.stream(),s4,(x,y)->Tuple.create(x, y));
		s5.forEach(System.out::println);
		System.out.println("______");
		var n = 14L;
		var b = 7L;
		var s6 = Stream.iterate(n,x->x>0,x->x/2);
		var a6 = SeqAccumulators
				.reduce(1L, (x,y)->y%2==0?x*x:x*x*b);
		var r1 = Streams2.accumulateRight(s6, a6);
		var s7 = Stream.iterate(Tuple.create(n,b),
								t->t.v1>0,
								t->Tuple.create(t.v1/2,t.v2*t.v2))
						.filter(t->t.v1%2!=0)
						.map(t->t.v2)
						.reduce(1L,(x,y)->x*y);
					   
		System.out.println(s7+","+r1+","+Math.pow(b,n));
		System.out.println("______");
		var ss = Streams2.elementsAndPosition(r2.stream());
		var r3 = ss.map(t->t.toString()).collect(Collectors.joining(","));
		System.out.println(r3);
		System.out.println("______");
		var enteros = Stream.iterate(0,x->x+1);
		var ss2 = Streams2.limit(enteros, 4);
		var r4 = ss2.map(t->t.toString()).collect(Collectors.joining(","));
		System.out.println(r4);

	}

}
