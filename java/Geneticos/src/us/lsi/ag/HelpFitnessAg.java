package us.lsi.ag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;


public class HelpFitnessAg {
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in &lt; 0.
	 * @return in*in si in no cumple la condic&oacute;n, si la cumple cero
	 */
	public static Double distanceToLeZero(Double in) {
		Double r = 0.;		
		if(in > 0) {
			r = in*in;
		}
		return r;
	}
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in &gt; 0.
	 * @return in*in si in no cumple la condic&oacute;n, si la cumple cero
	 */
	public static Double distanceToGeZero(Double in) {
		Double r = 0.;		
		if(in < 0) {
			r = in*in;
		}
		return r;
	}
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in = 0.
	 * @return in*in
	 */
	public static Double distanceToEqZero(Double in) {
		return in*in;
	}
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrdao de la diferencia entre el n&uacute;mero de el elementos diferentes y el de la lista
	 */
	public static <E> Boolean allDifferents(List<E> ls) {
		Integer n = ls.size();
		Integer m = ls.stream().collect(Collectors.toSet()).size();
		return n.equals(m);
	}
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrdao de la diferencia entre el n&uacute;mero de el elementos diferentes y el de la lista
	 */
	public static <E> Double distanceToAllDifferents(List<E> ls) {
		Integer n = ls.size();
		Integer m = ls.stream().collect(Collectors.toSet()).size();
		return (double)(n-m)*(n-m);
	}
	
	public static <V,E> List<V> vertices(Graph<V,E> graph, List<E> edges){
		Integer n = edges.size();
		List<V> vertices = IntStream.range(0,n).boxed()
				.map(i->graph.getEdgeSource(edges.get(i)))
				.collect(Collectors.toList());
		vertices.add(graph.getEdgeTarget(edges.get(n-1)));
		return vertices;
	}
	
	public static <V,E> Boolean isSimpleOpenPathVertices(Graph<V,E> graph, List<V> vertices){
		Integer n = vertices.size();
		return allDifferents(vertices) &&
				vertices.stream().allMatch(v->graph.containsVertex(v)) &&
				IntStream.range(0,n-1).boxed().allMatch(i->graph.containsEdge(vertices.get(i),vertices.get(i+1)));
	}
	
	public static <V,E> Boolean isSimpleOpenPathEdges(Graph<V,E> graph, List<E> edges){		
		return isSimpleOpenPathVertices(graph,vertices(graph,edges));
	}
	
	public static <V,E> Boolean isSimpleClosedPathVertices(Graph<V,E> graph, List<V> vertices){
		Integer n = vertices.size();
		return allDifferents(vertices) &&
				vertices.stream().allMatch(v->graph.containsVertex(v)) &&
				IntStream.range(0,n).boxed().allMatch(i->graph.containsEdge(vertices.get(i),vertices.get((i+1)%n)));
	}
	
	public static <V,E> Boolean isSimpleClosedPathEdges(Graph<V,E> graph, List<E> edges){		
		return isSimpleClosedPathVertices(graph,vertices(graph,edges));
	}
	
	public static <V,E> Double distanceToSimpleOpenPathVertices(Graph<V,E> graph, List<V> vertices){
		Integer n = vertices.size();
		Double d1 = distanceToAllDifferents(vertices);
		Double d2 =	(double) vertices.stream().filter(v->graph.containsVertex(v)).count();
		Double d3 =	(double) IntStream.range(0,n-1).boxed()
				.filter(i->graph.containsEdge(vertices.get(i),vertices.get((i+1)%n)))
				.count();
		return d1+(n-d2)*(n-d2)+(n-1-d3)*(n-1-d3);
	}
	
	public static <V,E> Double distanceToSimpleClosedPathVertices(Graph<V,E> graph, List<V> vertices){
		Integer n = vertices.size();
		Double d1 = distanceToAllDifferents(vertices);
		Double d2 =	(double) vertices.stream().filter(v->graph.containsVertex(v)).count();
		Double d3 =	(double) IntStream.range(0,n).boxed()
				.filter(i->graph.containsEdge(vertices.get(i),vertices.get((i+1)%n)))
				.count();
		return d1+(n-d2)*(n-d2)+(n-d3)*(n-d3);
	}
	
	public static <V,E> Double distanceToSimpleOpenPathEdges(Graph<V,E> graph, List<E> edges) {
		return distanceToSimpleOpenPathVertices(graph,vertices(graph,edges));
	}
	
	public static <V,E> Double distanceToSimpleClosedPathEdges(Graph<V,E> graph, List<E> edges) {
		return distanceToSimpleClosedPathVertices(graph,vertices(graph,edges));
	}

}
