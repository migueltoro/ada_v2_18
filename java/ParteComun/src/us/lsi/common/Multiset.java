package us.lsi.common;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Multiset<E>  {

	
	public static <E> Multiset<E> create() {
		return new Multiset<E>();
	}
	
	public static <E> Multiset<E> create(Multiset<E> m) {
		return new Multiset<E>(m.elements);
	}

	private Map<E,Integer>  elements;

	private Multiset() {
		super();
		this.elements = new HashMap<>();
	}

	public Multiset(Map<E, Integer> elements) {
		super();
		this.elements = new HashMap<>(elements);
	}

	public void clear() {
		elements.clear();
	}

	public boolean containsKey(Object arg0) {
		return elements.containsKey(arg0);
	}

	public boolean containsValue(Object arg0) {
		return elements.containsValue(arg0);
	}

	public Set<E> elementSet() {
		return elements.keySet();
	}

	public boolean equals(Object arg0) {
		return elements.equals(arg0);
	}

	public Integer count(Object e) {
		Integer r = 0;
		if(this.elements.containsKey(e)) {
		     r = elements.get(e);
		}
		return r;
	}

	public int hashCode() {
		return elements.hashCode();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}


	public Integer add(E e, Integer n) {
		Integer r = n;
		if(this.elements.containsKey(e)) {
		     r = elements.get(e) + r;
		}
		return elements.put(e, r);
	}

	public Integer add(E e) {
		Integer r = 1;
		if(this.elements.containsKey(e)) {
		     r = elements.get(e) + r;
		}
		return elements.put(e, r);
	}
	
	public static <E> Multiset<E> add(Multiset<E> m1, Multiset<E> m2) {
		Multiset<E> r = Multiset.create(m1);
		m2.elements.keySet().forEach(x->r.add(x,m2.count(x)));
		return r;
	}

	public Integer remove(Object e) {
		return elements.remove(e);
	}

	
	public int size() {
		return elements.size();
	}

	public String toString() {
		return elements
				.keySet()
				.stream()
				.map(x->String.format("%s:%d",x,this.count(x)))
				.collect(Collectors.joining(",","{","}"));
	}

}
