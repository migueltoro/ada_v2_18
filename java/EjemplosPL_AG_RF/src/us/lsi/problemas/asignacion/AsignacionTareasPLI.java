package us.lsi.problemas.asignacion;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.pli.AlgoritmoPLI;
import us.lsi.pli.SolutionPLI;



public class AsignacionTareasPLI {

	public static Integer numeroDeReinas = 10;

	public static String getConstraints(AsignaciondeTareasRF a) {

		Integer n = a.getN();
		String r = "min: ";

		boolean first = true;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + "+";
				// r = r + String.format("%.2f * x_%d_%d", a.getCoste(i,j),i, j).replaceAll(",",
				// ".");
				r = r + String.format("%s * x_%d_%d", String.valueOf(a.getCoste(i, j)), i, j);
			}
		}

		r = r + "\n\n";

		for (int i = 0; i < n; i++) {
			first = true;
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + "+";
				r = r + String.format("x_%d_%d", i, j);
			}
			r = r + "=";
			r = r + 1;
			r = r + ";\n";
		}

		r = r + "\n\n";

		for (int i = 0; i < n; i++) {
			first = true;
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + "+";
				r = r + String.format("x_%d_%d", j, i);
			}
			r = r + "=";
			r = r + 1;
			r = r + ";\n";
		}

		r = r + "\n\n";

		r = r + "bin ";

		first = true;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + ",";
				r = r + String.format("x_%d_%d", i, j);
			}

		}

		r = r + ";\n\n";

		return r;
	}

	public static String getConstraints2(AsignaciondeTareasRF a) {

		String r = "";
		Integer n = a.getN();
		// r += allPairs(n, n).map(p -> String.format("%.2f *
		// x_%d_%d",a.getCoste(p.i,p.j), p.i, p.j).replaceAll(",", "."))
		r += allPairs(n, n).map(p -> String.format("%s * x_%d_%d", String.valueOf(a.getCoste(p.i, p.j)), p.i, p.j))
				.collect(Collectors.joining("+", "min: ", "; \n\n"));

		r += IntStream.range(0, n).boxed().map(j -> sum_i(j, n)).collect(Collectors.joining("", "", "\n"));

		r += IntStream.range(0, n).boxed().map(i -> sum_j(i, n)).collect(Collectors.joining("", "", "\n"));

		r += allPairs(n, n).map(p -> String.format("x_%d_%d", p.i, p.j))
				.collect(Collectors.joining(",", "bin ", "; \n"));

		return r;
	}

	public static void main(String[] args) {
//			System.out.println(getConstraints());

		AsignaciondeTareasRF a = AsignaciondeTareasRF.create("ficheros/asignacionDeTareas.txt");

		System.out.println(getConstraints(a));
		System.out.println("________");
		System.out.println(getConstraints2(a));
		System.out.println("________");

		SolutionPLI s = AlgoritmoPLI.getSolution(getConstraints2(a));
		System.out.println("-------------------");
		System.out.println("________");
		System.out.println(s.getGoal());
		System.out.println("________");
		System.out.println("________");
		for (int i = 0; i < s.getNumVar(); i++) {
			if (s.getSolution(i) == 1.0)
				System.out.println(s.getName(i));
		}

	}

	static class Pair {
		Integer i;
		Integer j;

		public static Pair of(Integer i, Integer j) {
			return new Pair(i, j);
		}

		public Pair(Integer i, Integer j) {
			super();
			this.i = i;
			this.j = j;
		}

	}

	static String sum_i(int j, int n) {
		return IntStream.range(0, n).boxed().map(i -> String.format("x_%d_%d", i, j))
				.collect(Collectors.joining("+", "", " = 1; \n"));
	}

	static String sum_j(int i, int n) {
		return IntStream.range(0, n).boxed().map(j -> String.format("x_%d_%d", i, j))
				.collect(Collectors.joining("+", "", " = 1; \n"));
	}

	static Stream<Pair> allPairs(Integer n, Integer m) {
		return IntStream.range(0, n).boxed().flatMap(i -> IntStream.range(0, m).mapToObj(j -> Pair.of(i, j)));
	}

}
