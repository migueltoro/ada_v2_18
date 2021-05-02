package us.lsi.common;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author migueltoro
 *
 * Un conjunto de un rango de enteros
 */
public class RangeIntegerSet implements Set<Integer> {
	
	/**
	 * @param infLimit Limite inferior del conjunto de enteros
	 * @param numDigits Número de digitos usados para representar el conjunto
	 * @return Un RangeIntegerSet
	 */
	public static RangeIntegerSet empty(Integer infLimit, Integer numDigits) {
		return new RangeIntegerSet(infLimit, numDigits);
	}
	
	public static RangeIntegerSet empty() {
		return RangeIntegerSet.empty(0, 10);
	}
	
	public static RangeIntegerSet of(Integer... elems) {
		RangeIntegerSet r = RangeIntegerSet.empty();
		r.addAll(Arrays.asList(elems));
		return r;
	}
	
	public static RangeIntegerSet copy(RangeIntegerSet s) {
		return new RangeIntegerSet(s);
	}
	
	private final Integer infLimit;
	private final BitSet bits;

	private RangeIntegerSet(Integer infLimit, Integer numDigits) {
		super();
		this.infLimit = infLimit;
		this.bits = new BitSet(numDigits);
		this.bits.clear();
	}

	private RangeIntegerSet(RangeIntegerSet s) {
		super();
		this.infLimit = s.infLimit;
		this.bits = s.bits.get(0, s.bits.length());
	}
	
	public RangeIntegerSet(BitSet bits, Integer infLimit) {
		this.infLimit = infLimit;
		this.bits = (BitSet) bits.clone();
	}

	public RangeIntegerSet copy() {
		return new RangeIntegerSet(this);
	}

	@Override
	public int size() {
		return this.bits.cardinality();
	}

	@Override
	public boolean isEmpty() {
		return this.bits.isEmpty();
	}

	@Override
	public boolean contains(Object obj) {
		Integer e = (int) obj;
		Preconditions.checkArgument(e>=infLimit, "Fuera de rango "+e);
		Integer ne = e-infLimit;
		return this.bits.get(ne);
	}

	@Override
	public Iterator<Integer> iterator() {
		return IteratorSet.of(this);
	}

	@Override
	public Object[] toArray() {
		return this.stream().toArray();
	}
	
	
	@Override
	public <T> T[] toArray(T[] a) {		
		@SuppressWarnings("unchecked")
		T[] r = (T[]) this.stream().toArray();
		for(int i = 0 ; i <r.length; i++) {
			a[i] = r[i];
		}
		return r;
	}

	@Override
	public boolean add(Integer e) {
		Preconditions.checkArgument(e>=infLimit, "Fuera de rango");
		Integer ne = e-infLimit;
		Boolean c = this.bits.get(ne);
		this.bits.set(ne, true);
		return c != this.bits.get(ne);
	}
	
	public RangeIntegerSet addNew(Integer e) {
		RangeIntegerSet cp = this.copy();
		Preconditions.checkArgument(e>=infLimit, "Fuera de rango");
		Integer ne = e-infLimit;
		this.bits.get(ne);
		cp.bits.set(ne, true);
		return cp;
	}

	@Override
	public boolean remove(Object ob) {
		Integer e = (int) ob;
		Preconditions.checkArgument(e>=infLimit, "Fuera de rango");
		Integer ne = e-infLimit;
		Boolean c = this.bits.get(ne);
		this.bits.set(ne, false);
		return c != this.bits.get(ne);
	}
	
	public RangeIntegerSet removeNew(Object ob) {
		RangeIntegerSet cp = this.copy();
		Integer e = (int) ob;
		Preconditions.checkArgument(e>=infLimit, "Fuera de rango");
		Integer ne = e-infLimit;
		cp.bits.set(ne, false);
		return cp;
	}
	

	@Override
	public boolean containsAll(Collection<?> c) {
		return c.stream().allMatch(x->this.contains(x));
	}
	
	public boolean containsAll(RangeIntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));		
		return c.difference(this).isEmpty();
	}

	public boolean intersect(RangeIntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		return this.bits.intersects(c.bits);
	}
	
	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		MutableType<Boolean> change = MutableType.of(false);
		c.stream().forEach(x->change.value = this.add(x));
		return change.value;
	}
	
	public boolean addAll(RangeIntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		Integer n = this.bits.cardinality();
		this.bits.or(c.bits);
		return n != this.bits.cardinality();
	}

	public RangeIntegerSet union(Collection<? extends Integer> c) {
		RangeIntegerSet r = this.copy();
		r.addAll(c);
		return r;
	}
	
	public RangeIntegerSet union(RangeIntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		BitSet cp = (BitSet) this.bits.clone();
		cp.or(c.bits);
		return new RangeIntegerSet(cp, this.infLimit);
	}

	public boolean addAll(Integer... elems) {
		MutableType<Boolean> change = MutableType.of(false);
		Arrays.stream(elems).forEach(x->change.value = this.add(x));
		return change.value;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		MutableType<Boolean> change = MutableType.of(false);
		RangeIntegerSet cp = this.copy();
		cp.stream().filter(x->!c.contains(x))
			.forEach(x->change.value = this.remove(x));
		return change.value;
	}
	
	public boolean retainAll(RangeIntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		Integer n = this.bits.cardinality();
		this.bits.and(c.bits);
		return n != this.bits.cardinality();
	}
	
	public RangeIntegerSet intersection(Collection<? extends Integer> c) {
		RangeIntegerSet r = this.copy();
		r.retainAll(c);
		return r;
	}
	
	public RangeIntegerSet intersection(RangeIntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		BitSet cp = (BitSet) this.bits.clone();
		cp.and(c.bits);
		return new RangeIntegerSet(cp, this.infLimit);
	}
	
	public boolean retainAll(Integer... elems) {
		MutableType<Boolean> change = MutableType.of(false);		
		IntStream.range(0,bits.length()).map(x->x+infLimit).boxed()
			.filter(x->!this.contains(x))
			.forEach(x->change.value = this.remove(x));
		return change.value;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		MutableType<Boolean> change = MutableType.of(false);
		c.stream().forEach(x->change.value = this.remove(x));
		return change.value;
	}
	
	public boolean removeAll(RangeIntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		Integer n = this.bits.cardinality();
		this.bits.andNot(c.bits);
		return n != this.bits.cardinality();
	}
	
	public RangeIntegerSet difference(Collection<? extends Integer> c) {
		RangeIntegerSet r = this.copy();
		r.removeAll(c);
		return r;
	}
	
	public RangeIntegerSet difference(RangeIntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		BitSet cp = (BitSet) this.bits.clone();
		cp.andNot(c.bits);
		return new RangeIntegerSet(cp, this.infLimit);
	}
	
	public RangeIntegerSet simetricDifference(RangeIntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		BitSet cp = (BitSet) this.bits.clone();
		cp.xor(c.bits);
		return new RangeIntegerSet(cp, this.infLimit);
	}
	
	public boolean removeAll(Integer... elems) {
		MutableType<Boolean> change = MutableType.of(false);
		Arrays.stream(elems).forEach(x->change.value = this.remove(x));
		return change.value;
	}

	@Override
	public void clear() {
		this.bits.clear();	
	}
	
	@Override
	public String toString() {
		return this.stream().map(x->x.toString()).collect(Collectors.joining(",","{","}"));
	}

	public static void main(String[] args) {
		RangeIntegerSet s1 = RangeIntegerSet.of(20,25,43,457);
		RangeIntegerSet s2 = RangeIntegerSet.of(25,43,20);
		RangeIntegerSet s3 = RangeIntegerSet.of(25,45);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		System.out.println(s3.addNew(54));
		System.out.println("Union");
		System.out.println(s2.union(s1));
		System.out.println("Interseccion");
		System.out.println(s2.intersection(s1));
		System.out.println("Difference");
		System.out.println(s1.difference(s2));
		System.out.println("SimetricDifference");
		System.out.println(s1.simetricDifference(s2));	
		System.out.println("ContainsAll");
		System.out.println(s1.containsAll(s2));
		System.out.println("Difference");
		System.out.println(s2.difference(s1));
		System.out.println("simetricDifference");
		System.out.println(s2.simetricDifference(s1));	
		System.out.println("ContainsAll");
		System.out.println(s2.containsAll(s1));
		Object[] a = new Integer[s2.size()];
		System.out.println(Arrays.asList(s2.toArray(a)));
		System.out.println(Arrays.asList(a));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((infLimit == null) ? 0 : infLimit.hashCode());
		result = prime * result + ((bits == null) ? 0 : bits.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RangeIntegerSet other = (RangeIntegerSet) obj;
		if (infLimit == null) {
			if (other.infLimit != null)
				return false;
		} else if (!infLimit.equals(other.infLimit))
			return false;
		if (bits == null) {
			if (other.bits != null)
				return false;
		} else if (!bits.equals(other.bits))
			return false;
		return true;
	}



	static class IteratorSet implements Iterator<Integer> {
		
		public static IteratorSet of(RangeIntegerSet s) {
			return new IteratorSet(s);
		}	
		
		private int index;
		private RangeIntegerSet s;
		
		public IteratorSet(RangeIntegerSet s) {
			super();
			this.index = 0;
			this.s = s;
		}

		@Override
		public boolean hasNext() {
			return this.index < s.bits.length();
		}

		@Override
		public Integer next() {
			Integer r = s.bits.nextSetBit(index);
			this.index = r+1;
			return r+s.infLimit;
		}
		
	}
}
