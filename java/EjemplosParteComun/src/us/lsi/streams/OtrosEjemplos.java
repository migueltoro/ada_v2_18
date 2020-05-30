package us.lsi.streams;


import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;

import us.lsi.common.Lists2;
import us.lsi.common.Pair;
import us.lsi.common.Sets2;
import us.lsi.flujosparalelos.Streams2;


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

		Streams2.join(()->IntStream.range(0, 5).boxed(),
				      ()->IntStream.range(10, 15).boxed(),
					  x -> x % 5, 
					  x -> x % 3, 
					  (x, y) -> x + y)
				.forEach(imprimeEnConsola());

	}

	public static void ejemplo5() {
		List<Long> s0 = List.of(1L, 2L, 3L, 4L, 5L);
		List<Long> s1 = List.of(4L, 10L, 9L, 29L);
		List<Long> s2 = List.of(5L, 15L, 20L, 39L);
		
		
		List<Long> r = Lists2.concat(s0,s1);
		Streams2.join(()->r.stream(),()->s2.stream(), x -> x, x -> x, (x, y) -> x).forEach(imprimeEnConsola());		
	}

	public static void ejemplo6() {
		Set<Integer> s1 = Sets2.of(1, 3, 5);
		Set<Integer> s2 = Sets2.of(16, 13, 15);
		Streams2.cartesianProduct(s1.stream(),s2.stream(),(x, y) -> Pair.of(x, y)).forEach(imprimeEnConsola());
	}

	public static void ejemplo7() {
		Stream.iterate(3L, x -> 81L - x >= 0, x -> x * x)
			  .forEach(imprimeEnConsola());
	}

	public static void ejemplo8() {
		Streams2.join(
				()->Stream.of(1L, 2L, 3L, 4L, 9L),
				()->Stream.of(4L, 10L, 9L, 29L), 
				x -> x, 
				x -> x, 
				(x, y) -> x)
			   .forEach(imprimeEnConsola());
	}

	public static void ejemplo9() {
		Stream.<Integer>iterate(0, x -> x <= 1000, x -> x + 4)
				.forEach(imprimeEnConsola());
	}

	public static void main(String[] args) {
		ejemplo8();
	}
	
}
