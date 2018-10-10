package us.lsi.common;

/**
 * @author Miguel Toro
 *
 * @param <E> El tipo de lo elementos del Stream a acumular
 * @param <B> El tipo de la la base del acumulador
 * @param <R> El tipo del resultado del acumulador
 */
public interface SeqAccumulator<E, B, R> {
	
	/**
	 * @post El elemento queda acumulado en la base
	 * @param e Un elemento
	 */
	void add(E e);
	/**
	 * @return Transforma el valor de la base. Es el valor que devolverá el acumulador
	 */
	R result();
	/**
	 * @return Si el acumulador ha terminado el cálculo
	 */
	Boolean isDone();
}
