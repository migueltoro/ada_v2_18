package us.lsi.ejemplos;

import java.util.stream.Stream;

import us.lsi.common.Accumulators;
import us.lsi.common.Streams2;
import us.lsi.common.Tuple;
import us.lsi.math.Math2;

public class EjemplosDeStreams {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		var s1 = Stream.of(1,2,3,4,5,6,7);
		var s2 = Stream.of(11,12,13,14,15,16);
		var s = "Antonio sa-lio al patio";
		Stream.generate(()->Math2.getEnteroAleatorio(0, 100))
			.limit(100)
			.forEach(System.out::println);
		var s3 = s2.map(x->x.toString());
		var a = Accumulators.joining(" ","{","}");
		var r = Streams2.accumulateRight(s3, a);
		System.out.println(r);	
		var s4 = Stream.iterate(0, x->x+1);
//		var s5 = Streams2.zip(s2,s4,(x,y)->Tuple.create(x, y));
//		s5.forEach(System.out::println);
		var n = 14;
		var b = 7;
		var s6 = Stream.iterate(n,x->x>0,x->x/2);
		var a6 = Accumulators
				.reduce(1, (x,y)->y%2==0?x*x:x*x*b);
		var r1 = Streams2.accumulateRight(s6, a6);
		var s7 = Stream.iterate(Tuple.create(n,b),
								t->t.v1>0,
								t->Tuple.create(t.v1/2,t.v2*t.v2))
						.filter(t->t.v1%2!=0)
						.map(t->t.v2)
						.reduce(1,(x,y)->x*y);
					   
		System.out.println(r+","+Math.pow(b,n));

	}

}
