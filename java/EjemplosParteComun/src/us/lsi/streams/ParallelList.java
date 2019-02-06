package us.lsi.ejemplos;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.ParallelFlow;

public class ParallelList<E> implements ParallelFlow<E> {

	private Integer i;
	private Integer j;
	private List<E> ls;

	public static <E> ParallelList<E> create(List<E> ls) {
		return new ParallelList<>(0, ls.size(), ls);
	}

	private static <E> ParallelList<E> create(Integer i, Integer j, List<E> ls) {
		return new ParallelList<>(i, j, ls);
	}

	public ParallelList(Integer i, Integer j, List<E> ls) {
		super();
		this.i = i;
		this.j = j;
		this.ls = ls;
	}

	@Override
	public int size() {
		return j - i;
	}

	@Override
	public int numberOfParts() {
		return 2;
	}

	@Override
	public int limit() {
		return 10;
	}

	@Override
	public Boolean hasNext() {
		return j - i > 0;
	}

	@Override
	public E next() {
		E r = this.ls.get(i);
		this.i = this.i + 1;
		return r;
	}

	@Override
	public List<ParallelFlow<E>> split() {
		List<ParallelFlow<E>> pf = new ArrayList<>();
		Integer k = (this.i + this.j) / 2;
		pf.add(ParallelList.create(i, k, ls));
		pf.add(ParallelList.create(k, j, ls));
		return pf;
	}

	@Override
	public String toString() {
		return "[i=" + i + ", j=" + j + "]";
	}
	
	

	
}