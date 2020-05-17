package us.lsi.problemas.coloreado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.GraphsReader;
import us.lsi.pli.AlgoritmoPLI;
import us.lsi.pli.SolutionPLI;
import static us.lsi.pli.AuxiliaryPLI.*;
import us.lsi.common.Streams2;

public class ProblemaColorPLI {

	private static Graph<Ciudad, Carretera> grafo;
	private static List<Ciudad> ciudades;

	private static void cargaGrafo(String f) {
		grafo = GraphsReader.newGraph(f, Ciudad::ofFormat, Carretera::ofFormat,
				() -> new SimpleWeightedGraph<>(Ciudad::of, Carretera::of), Carretera::getKm);
		ciudades = new ArrayList<>(grafo.vertexSet());
	}

	@SuppressWarnings("unused")
	private static String getConstraints(Graph<Ciudad, Carretera> g, List<Ciudad> lc) {

		Map<Ciudad, Integer> indexOf = new HashMap<>();
		lc.stream().forEach(c -> indexOf.put(c, lc.indexOf(c)));

		String r = "min: ";
		Integer n = lc.size();
		boolean first = true;

		// y_color = 0 no se usa, 1 se usa
		for (int k = 0; k < n; k++) {
			if (first)
				first = false;
			else
				r = r + "+";
			r = r + String.format("y_%d", k);

		}

		r = r + ";\n\n";

		// X_ciudad_color = 1 se usa el color k con la ciudad i.
		for (int i = 0; i < n; i++) {
			first = true;
			for (int k = 0; k < n; k++) {
				if (first)
					first = false;
				else
					r = r + "+";
				r = r + String.format("x_%d_%d", i, k);
			}
			r = r + "=";
			r = r + 1;
			r = r + ";\n";
		}
		r = r + "\n";

		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				r = r + String.format("x_%d_%d - y_%d<=0;\n", i, k, k);
			}
		}
		r = r + "\n";

		for (Carretera c : g.edgeSet()) {
			Ciudad cs = c.getSource();
			Ciudad ct = c.getTarget();
			Integer ids = lc.indexOf(cs);
			Integer idt = lc.indexOf(ct);

			for (int k = 0; k < n; k++) {
				r = r + String.format("x_%d_%d + x_%d_%d<=1;\n", ids, k, idt, k);
			}

		}
		r = r + "\n";

		r = r + "bin ";

		first = true;

		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				if (first)
					first = false;
				else
					r = r + ",";
				r = r + String.format("x_%d_%d", i, k);
			}
			r = r + ",";
			r = r + String.format("y_%d", i);
		}

		r = r + ";\n\n";

		return r;
	}

	private static String getConstraints2(Graph<Ciudad, Carretera> g, List<Ciudad> lc) {

		Map<Ciudad, Integer> indexOf = new HashMap<>();
		lc.stream().forEach(c -> indexOf.put(c, lc.indexOf(c)));

		String r = "";
		Integer n = lc.size(); // Numero colores = numero de nodos
		r += IntStream.range(0, n).boxed().map(k -> String.format("y_%d", k))
				.collect(Collectors.joining("+", "min: ", "; \n\n"));

		// Para cada nodo i debe haber un solo color asignado.
		r += IntStream.range(0, n).boxed().map(i -> constraintEq(sum_2_1(n,i,"x"),"1"))
				.collect(Collectors.joining("", "", "\n\n"));

		// Si se asigna el color k al nodo i , el color k no puede esta deasctivado.
		r += Streams2.allPairs(n, n).map(p -> String.format("x_%d_%d - y_%d<=0;\n", p.first, p.second, p.second))
				.collect(Collectors.joining("", "", "\n"));

		// Dos vecinos no pueden tener el mismo color
		r += g.edgeSet().stream().map(
				e -> vecinosConColoresDistintos(indexOf.get(g.getEdgeSource(e)), indexOf.get(g.getEdgeTarget(e)), n))
				.collect(Collectors.joining("\n", "", "\n"));

		// Definicion de las variables
		r += Stream
				.concat(Streams2.allPairs(n, n).map(p -> String.format("x_%d_%d", p.first, p.second)),
						IntStream.range(0, n).boxed().map(k -> String.format("y_%d", k)))
				.collect(Collectors.joining(",", "bin ", ";\n"));

		return r;
	}

	public static void main(String[] args) {
//		System.out.println(getConstraints());

		ProblemaColorPLI.cargaGrafo("./ficheros/Andalucia.txt");

		System.out.println(getConstraints2(grafo, ciudades));
		System.out.println("________");

		SolutionPLI s = AlgoritmoPLI.getSolution(getConstraints2(grafo, ciudades));
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

	static String vecinosConColoresDistintos(Integer ids, Integer idt, int n) {
		return IntStream.range(0, n).boxed().map(k -> String.format("x_%d_%d + x_%d_%d<=1;\n", ids, k, idt, k))
				.collect(Collectors.joining("", "", "\n"));
	}

}
