package us.lsi.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;

public class Collectors2 {
	
	public static <E> Collector<E,Multiset<E>,Multiset<E>> toMultiset() {
		return Collector.of(
				()->Multiset.create(), 
				(x,e)->x.add(e), 
				(x,y)->Multiset.add(x, y), 
				x->x, 
				Collector.Characteristics.UNORDERED, 
				Collector.Characteristics.IDENTITY_FINISH);
	}
	
	public static <E> Collector<E,List<E>,List<E>> mergeSort(Comparator<? super E> cmp) {
		return Collector.of(
				()->new ArrayList<>(), 
				(x,e)->Lists2.insertOrdered(x,e,cmp), 
				(x,y)->Lists2.mergeOrdered(x,y,cmp), 
				x->x, 
				Collector.Characteristics.UNORDERED, 
				Collector.Characteristics.IDENTITY_FINISH
				);
	}
	
	public static <E extends Comparable<? super E>> Collector<E,List<E>,List<E>> mergeSort() {
		return mergeSort(Comparator.naturalOrder());
	}
	
	
}
