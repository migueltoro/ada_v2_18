package us.lsi.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import java.util.Map;

public class Multisets2<E>  {	
	/**
	 * @param <E> Tipo de los elementos del Multiset
	 * @return Un Multiset vacío
	 */
	public static <E> Multiset<E> create() {
		return Multiset.<E>create();
	}

	/**
	 * @param <E> Tipo de los elementos del Multiset
	 * @param it Un iterable
	 * @return Un Multiset construido a partir del iterable
	 */
	public static <E> Multiset<E> create(Collection<E> it){
		return Streams2.toMultiSet(it.stream());
	}
	
	/**
	 * @param <E> Tipo de los elementos del Multiset
	 * @param m Un Map
	 * @return Un Multiset construido a partir del Map
	 */
	public static <E> Multiset<E> create(Map<E,Integer> m){
		Multiset<E> r = Multiset.create();
		m.entrySet().stream().forEach(x->r.add(x.getKey(),x.getValue()));
		return r;
	}
	
	/**
	 * @param <E> Tipo de los elementos del Multiset
	 * @param m Un Multiset
	 * @return Un Map construido a partir del Multiset
	 */
	public static <E> Map<E,Integer> asMap(Multiset<E> m){
		Map<E,Integer> r = new HashMap<>();
		m.elementSet().stream().forEach(x->r.put(x,m.count(x)));
		return r;
	}
	
	/**
	 * @param <E> Tipo de los elementos del Multiset
	 * @param entries Una secuencia de elementos
	 * @return Un Multiset construidom a partir de la secuencia de elementos
	 */
	public static <E> Multiset<E> newMultiset(@SuppressWarnings("unchecked") E... entries){
		Multiset<E> s = Multiset.create();
		Arrays.asList(entries)
		.stream()
		.forEach(x->s.add(x)); 		
		return s;
	}

	
	
}
