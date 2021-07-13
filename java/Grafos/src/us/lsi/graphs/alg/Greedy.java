package us.lsi.graphs.alg;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import us.lsi.streams.Stream2;

public class Greedy<V> implements  Iterator<V>, Iterable<V> {
	
	public static <V> Greedy<V> of(V start, Function<V, V> next, Predicate<V> end) {
		return new Greedy<V>(start, next, end);
	}

	private V state;
	private Function<V,V> next;
	private Predicate<V> end;

	private Greedy(V start, Function<V, V> next, Predicate<V> end) {
		super();
		this.state = start;
		this.next = next;
		this.end = end;
	}

	public Stream<V> stream() {
		return Stream2.asStream(this);
	}
	
	public Greedy<V> copy() {
		return of(state, next, end);
	}
	
	@Override
	public Iterator<V> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return this.end.test(state);
	}

	@Override
	public V next() {
		V old = state;
		state = this.next.apply(state);
		return old;
	}
	
	/**
	 * @param <R> Tipo del resultado
	 * @param a0 Valor inicial del acumualdor
	 * @param f Función de transformación del loe elementos del flujo
	 * @param b Un operador binario
	 * @return Resulatdo acumulado
	 */
	public <R> R accumulate(R a0, Function<V,R> f, BinaryOperator<R> b){
		return this.stream().map(f).reduce(a0,b);
	}
	
	/**
	 * @param <R> Tipo del resultado
	 * @param f Función de transformación del loe elementos del flujo
	 * @param b Un operador binario
	 * @return Resulatdo acumulado
	 */
	public <R> Optional<R> accumulate(Function<V,R> f, BinaryOperator<R> b){
		return this.stream().map(f).reduce(b);
	}

}
