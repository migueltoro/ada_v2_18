package us.lsi.streams;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ParallelList<E> implements Spliterator<E> {

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
	
	public int size() {
		return j - i;
	}

	public int limit() {
		return 10;
	}

	@Override
	public String toString() {
		return "[i=" + i + ", j=" + j + "]";
	}

	@Override
	public boolean tryAdvance(Consumer<? super E> action) {
		Boolean r = j - i > 0;
		if(r) {
			action.accept(this.ls.get(i));
			this.i = this.i + 1;
		}
		return r;
	}

	@Override
	public Spliterator<E> trySplit() {
		Spliterator<E> r = null;
		if(j-i > limit()) {
			Integer k = (i + j) / 2;
			r = ParallelList.create(i,k,ls);
			i = k;
		}
		return r;
	}

	@Override
	public long estimateSize() {
		return j-i;
	}

	@Override
	public int characteristics() {
		return 0;
	}
	
	Stream<E> stream() {
		return StreamSupport.stream(this,true);
	}
	
}