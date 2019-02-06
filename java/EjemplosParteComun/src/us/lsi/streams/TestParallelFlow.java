package us.lsi.ejemplos;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import us.lsi.common.InmutableCollector;
import us.lsi.common.Lists2;
import us.lsi.common.ParallelFlow;


public class TestParallelFlow {

	public static void main(String[] args) {

		List<Integer> ls = Lists2
				.newList(Arrays.asList(1, 2, 3, 4, 5, 5, 5, 6, 7, 8, 9, 12, 10, 11, 12, 131, 45, 6, 7, 89));

		InmutableCollector<Integer, Integer, Integer> c2 = InmutableCollector.of(0, (x, y) -> x + y, (x, y) -> x + y,
				x -> x);

		ParallelFlow<Integer> p = ParallelList.create(ls);

		Integer r = p.acummulate(c2);

		System.out.println("Try = " + r);

		System.out.println(ls.stream().mapToInt(x -> x).sum());

		p = ParallelList.create(ls);
		Set<Integer> s = p.acummulate(Collectors.toCollection(TreeSet::new));
		System.out.println(s);
	}

}

