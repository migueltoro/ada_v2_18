package us.lsi.iterators;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import us.lsi.common.MutableType;

public class Test {

	public static void main(String[] args) {
		List<Double> ls = List.of(1.,2.,56.,123.,-45.,567.,-2.,-89.,55.,67.);
		Iterator<Double> i1 = ls.stream().iterator();
		Iterator<Double> i2 = IteratorFilter.of(i1,e->e<0.);
		i2.forEachRemaining(x->System.out.print(x+","));
		List<Set<Integer>> ls1 = List.of(Set.of(23,45),Set.of(),Set.of(23,45),Set.of(23,45),Set.of(23,45));
		Iterator<Set<Integer>> h1 = ls1.stream().iterator();
		Iterator<Integer> h2 = IteratorFlatMap.of(h1,x->x.stream().iterator());
		Iterator<Integer> h3 = IteratorWithSeeNext.of(h2);
		MutableType<Integer> sum = MutableType.of(0);
		h3.forEachRemaining(x->sum.newValue(sum.value+x));
		Integer sum2 = ls1.stream().flatMap(x->x.stream()).mapToInt(Integer::intValue).sum();
		System.out.print("\n"+sum.value+","+sum2);
	}

}

