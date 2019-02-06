package us.lsi.common;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Miguel Toro
 *
 * @param <E> El tipo de lo elementos del Stream a acumular
 * @param <B> El tipo de la la base del acumulador
 * @param <R> El tipo del resultado del acumulador
 */
public interface SeqCollector<E, B, R> {	
	
	/**
	 * @return La función que actualiza la base a partir de su estado y el valor de entrada
	 */
	BiConsumer<B,E> accumulator();
	
	/**
	 * @return La función que calcula el resultado a partir de la base
	 */
	Function<B,R> finisher();
	/**
	 * @return El predicado que indica si el acumulador ha terminado el cálculo
	 */
	Predicate<B> isDone();
	/**
	 * @return Un supplier que proporciona al valor inicial de la base
	 */
	Supplier<B> supplier();
	
	public static <E, B, R> SeqCollector<E, B, R> of(
			Supplier<B> supplier, 
			BiConsumer<B,E> accumulator,
			Function<B, R> finisher, 
			Predicate<B> isDone) {
		return new SeqCollectors.SeqMutableCollectorImpl<>(supplier, accumulator, finisher, isDone);
	}
	
	public static <E, B> SeqCollector<E, B, B> of(
			Supplier<B> supplier, 
			BiConsumer<B,E> accumulator) {
		return new SeqCollectors.SeqMutableCollectorImpl<>(supplier, accumulator, x->x, x->false);
	}
}
