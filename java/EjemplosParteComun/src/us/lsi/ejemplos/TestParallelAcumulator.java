package us.lsi.ejemplos;


import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import us.lsi.common.Collectors2;
import us.lsi.common.Strings2;

public class ParallelAcumulator {

	public static void main(String[] args) {
		Random r = new Random(System.nanoTime());
		Stream<Integer> s = r.ints(5000).boxed();
		List<Integer> rr = s.parallel().collect(Collectors2.mergeSort());

		Strings2.toConsole(rr, "Ordenada");
	}

}
