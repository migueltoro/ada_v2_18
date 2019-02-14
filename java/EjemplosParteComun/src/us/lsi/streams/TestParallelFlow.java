package us.lsi.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import us.lsi.common.InmutableCollector;
import us.lsi.common.Lists2;
import us.lsi.common.MutableType;
import us.lsi.common.Streams2;


public class TestParallelFlow {

	public static void main(String[] args) {

		List<Integer> ls = Lists2
				.newList(Arrays.asList(1, 2, 3, 4, 5, 5, 5, 6, 7, 8, 9, 12, 10, 11, 12, 131, 45, 6, 7, 89));

		Collector<Integer, MutableType<Integer>, Integer> c2 = 
				InmutableCollector.toMutable(0, (x, y) -> x + y, (x, y) -> x + y, x -> x);

		ParallelList<Integer> ls2 = ParallelList.create(ls);
		
		Integer r = Streams2.accumulate(ls2.stream(),c2);

		System.out.println("Try = " + r);
		
		ls2 = ParallelList.create(ls);

		System.out.println(ls2.stream().mapToInt(x -> x).sum());
		
		ls2 = ParallelList.create(ls);

		Set<Integer> s = Streams2.accumulate(ls2.stream(),Collectors.toCollection(TreeSet::new));
		System.out.println(s);
	}

}

