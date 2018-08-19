package us.lsi.java8ejemplos;


import java.util.Set;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;

import us.lsi.common.Sets2;
import us.lsi.common.Streams2;
import us.lsi.common.Tuple;




/**
 *
 * @author Miguel Toro
 */
public class OtrosEjemplos {

	private static <T> Consumer<T> imprimeEnConsola() {
		return x -> System.out.println(x);
	}

	private static IntConsumer imprimeEnConsolaInt() {
		return x -> System.out.println(x);
	}

	public static void ejemplo1() {

		Double r = DoubleStream.iterate(7.0, x -> x * x).filter(x -> x > 3000)
				.findFirst().getAsDouble();

		System.out.println("r= " + r);

	}

	public static void ejemplo2() {
		IntStream.range(23, 550).forEach(imprimeEnConsolaInt());
	}

	public static void ejemplo3() {
		Stream.concat(IntStream.range(50, 60).boxed(),
				IntStream.range(2, 20).boxed()).forEach(imprimeEnConsola());
	}

	public static void ejemplo4() {

		IntStream.range(0, 5).boxed().forEach(imprimeEnConsola());

		System.out.println("========");

		IntStream.range(10, 15).boxed().forEach(imprimeEnConsola());

		System.out.println("========");

		Streams2.joint(IntStream.range(0, 5).boxed(),
				      IntStream.range(10, 15).boxed(),
					  x -> x % 2 == 0, 
					  x -> x % 3 == 0, 
					  (x, y) -> x + y)
				.forEach(imprimeEnConsola());

	}

	public static void ejemplo5() {
		Stream<Long> s0  = Stream.of(1L, 2L, 3L, 4L, 5L);
		Stream<Long> s1 = Stream.of(4L, 10L, 9L, 29L);
		Stream<Long> s2 = Stream.of(5L, 15L, 20L, 39L);
		
		
		var r = Streams2.concat(s0,s1);
		var r2 = Streams2.joint(r,s2, x -> x % 2, x -> x % 3, (x, y) -> x + y);			
		r2.forEach(imprimeEnConsola());
	}

	public static void ejemplo6() {
		Set<Integer> s1 = Sets2.newSet(1, 3, 5);
		Set<Integer> s2 = Sets2.newSet(16, 13, 15);
		Streams2.cartesianProduct(s1.stream(),s2.stream(),
						(x, y) -> Tuple.create(x, y))
				.forEach(imprimeEnConsola());
	}

	public static void ejemplo7() {
		Stream.iterate(3L, x -> 81L - x >= 0, x -> x * x)
			  .forEach(imprimeEnConsola());
	}

	public static void ejemplos8() {
		Streams2.joint(
				Stream.of(1L, 2L, 3L, 4L, 5L),
				Stream.of(4L, 10L, 9L, 29L), 
				x -> x % 5, 
				x -> x % 3, 
				(x, y) -> x +  y)
			   .forEach(imprimeEnConsola());
	}

	public static void ejemplos9() {
		Stream.<Integer>iterate(0, x -> x <= 1000, x -> x + 4)
				.forEach(imprimeEnConsola());
	}

	
}
