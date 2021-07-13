package us.lsi.streams;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ListSpliterator<E> implements Spliterator<E> {

	public static <E> ListSpliterator<E> of(List<E> ls) {
		return new ListSpliterator<E>(ls, 0, ls.size());
	}

	private static <E> ListSpliterator<E> of(List<E> ls, Integer i, Integer j) {
		return new ListSpliterator<E>(ls, i, j);
	}
	
//	private Integer tam = 1;
	private List<E> ls;
	private Integer i;
	private Integer j;
	
	private ListSpliterator(List<E> ls, Integer i, Integer j) {
		super();
		this.ls = ls;
		this.i = i;
		this.j = j;
	}

	@Override
	public boolean tryAdvance(Consumer<? super E> action) {
		Boolean r = false;
		if(j - i > 0) {
			r = true;
			action.accept(ls.get(i));
			this.i = this.i +1;			
		}
		return r;
	}

	@Override
	public Spliterator<E> trySplit() {
		Spliterator<E> r = null;
		if(j - i > 1) {
			int k = (i+j)/2;
			r = of(ls,0,k);
			this.i = k;
		}
		return r;
	}

	@Override
	public long estimateSize() {
		return ls.size();
	}

	@Override
	public int characteristics() {
		return 0;
	}
	
	public Stream<E> stream(){
		return Spliterators2.asStream(this);
	}

}
