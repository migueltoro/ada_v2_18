package us.lsi.common;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface SeqInmutableCollector<E, B, R> {

	/**
	 * @return La función que actualiza la base a partir de su estado y el valor de entrada
	 */
	BiFunction<B,E,B> accumulator();
	
	/**
	 * @return La función que calcula el resultado a partir de la base
	 */
	Function<B,R> finisher();
	/**
	 * @return El predicado que indica si el acumulador ha terminado el cálculo
	 */
	Predicate<B> isDone();
	/**
	 * @return Proporciona el valor inicial de la base
	 */
	B initial();
	
	public static <E, B, R> SeqInmutableCollector<E, B, R> of(
			B initial, 
			BiFunction<B, E, B> accumulator,
			Function<B, R> finisher, 
			Predicate<B> isDone) {
		return new SeqCollectors.SeqInmutableCollectorImpl<E, B, R>(initial, accumulator, finisher, isDone);
	}
	
	public static <E, B> SeqInmutableCollector<E, B, B> of(
			B initial, 
			BiFunction<B, E, B> accumulator) {
		return new SeqCollectors.SeqInmutableCollectorImpl<E, B, B>(initial, accumulator, x->x, x->false);
	}
}
