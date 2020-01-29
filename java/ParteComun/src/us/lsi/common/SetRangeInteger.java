package us.lsi.common;

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
public class SetRangeInteger implements Set<Integer>{
	
	
	public static SetRangeInteger of(Integer a, Integer numDigits) {
		return new SetRangeInteger(a, numDigits);
	}
	
	public static SetRangeInteger of() {
		return new SetRangeInteger(0, 10);
	}
	
	public static SetRangeInteger of(SetRangeInteger s) {
		return new SetRangeInteger(s);
	}
	
	private final Integer a;
	private BitSet bits;
	

	private SetRangeInteger(Integer a, Integer numDigits) {
		super();
		this.a = a;
		this.bits = new BitSet(numDigits);
		this.bits.clear();
	}

	private SetRangeInteger(SetRangeInteger s) {
		super();
		this.a = s.a;
		this.bits = s.bits.get(0, s.bits.length());
	}
	
	public SetRangeInteger copy() {
		return new SetRangeInteger(this);
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
		Preconditions.checkArgument(e>=a, "Fuera de rango "+e);
		Integer ne = e-a;
		return this.bits.get(ne);
	}


	@Override
	public Iterator<Integer> iterator() {
		return IteratorSet.of(this);
	}



	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}


	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean add(Integer e) {
		Preconditions.checkArgument(e>=a, "Fuera de rango");
		Integer ne = e-a;
		Boolean c = this.bits.get(ne);
		this.bits.set(ne, true);
		return c != this.bits.get(ne);
	}



	@Override
	public boolean remove(Object object) {
		Integer e = (int) object;
		Preconditions.checkArgument(e>=a, "Fuera de rango");
		Integer ne = e-a;
		Boolean c = this.bits.get(ne);
		this.bits.set(ne, false);
		return c != this.bits.get(ne);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return c.stream().allMatch(x->this.contains(x));
	}


	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		MutableType<Boolean> change = MutableType.of(false);
		c.stream().forEach(x->change.newValue(this.add(x)));
		return change.value;
	}



	@Override
	public boolean retainAll(Collection<?> c) {
		MutableType<Boolean> change = MutableType.of(false);		
		IntStream.range(0,bits.length()).map(x->x+a).boxed().filter(x->!this.contains(x)).forEach(x->change.newValue(this.remove(x)));
		return change.value;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		MutableType<Boolean> change = MutableType.of(false);
		c.stream().forEach(x->change.newValue(this.remove(x)));
		return change.value;
	}

	@Override
	public void clear() {
		this.bits.clear();	
	}
	
	public static void main(String[] args) {
		SetRangeInteger s = SetRangeInteger.of(10, 150);
		for(int i = s.a;i<150;i= i+7) {
			s.add(i);
		}
		s.add(149);
		s.add(500);
		System.out.println(s.a+","+s.bits.length());
		String sout = s.stream().map(x->x.toString()).collect(Collectors.joining(",","{","}"));
		System.out.println(sout);
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
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
		SetRangeInteger other = (SetRangeInteger) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (bits == null) {
			if (other.bits != null)
				return false;
		} else if (!bits.equals(other.bits))
			return false;
		return true;
	}



	static class IteratorSet implements Iterator<Integer> {
		
		public static IteratorSet of(SetRangeInteger s) {
			return new IteratorSet(s);
		}
		
		
		private int index;
		private SetRangeInteger s;
		
		public IteratorSet(SetRangeInteger s) {
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
			return r+s.a;
		}
		
	}
}
