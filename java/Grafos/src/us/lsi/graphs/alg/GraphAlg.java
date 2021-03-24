package us.lsi.graphs.alg;



import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jgrapht.Graph;

import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.EGraph;

/**
 * @author migueltoro
 *
 * @param <V> El tipo de los v&eacute;rtices
 * @param <E> El tipo de las aristas
 * 
 * Distintos tipos de b&uacute;squedas sobre grafos
 * 
 */
public interface GraphAlg<V,E>  {
	
	/**
	 * @param <V> El tipo de los v&eacute;rtices
	 * @param <E> El tipo de las aristas
	 * @param graph Un grafo 
	 * @param nextEdge
	 * @return Un algoritmo de b&uacute;squeda voraz siguiendo las aristas del grafo
	 */
	public static <V, E> GreedySearch<V, E> greedy(
			EGraph<V,E> graph,
			Function<V,E> nextEdge,
			Predicate<V> goal) {
		return new GreedySearch<V, E>(graph,nextEdge,goal);
	}
	/**
	 * @param <V> El tipo de los v&eacute;rtices
	 * @param <E> El tipo de las aristas
	 * @param g Un grafo 
	 * @param startVertex El vértice inicial
	 * @return Una algoritmo de b&uacute;squeda en profundidad en preorden
	 */
	public static <V, E> DephtSearch<V, E> depth(Graph<V, E> g, V startVertex) {
		return new DephtSearch<V, E>(g, startVertex);
	}	
	/**
	 * @param <V> El tipo de los v&eacute;rtices
	 * @param <E> El tipo de las aristas
	 * @param g Un grafo 
	 * @param startVertex El vértice inicial
	 * @return Una algoritmo de b&uacute;squeda en profundidad en postorden
	 */
	public static <V, E> DephtPostSearch<V, E> depthPost(Graph<V, E> g, V startVertex) {
		return new DephtPostSearch<V, E>(g, startVertex);
	}
	/**
	 * @param <V> El tipo de los v&eacute;rtices
	 * @param <E> El tipo de las aristas
	 * @param g Un grafo 
	 * @param startVertex El vértice inicial
	 * @return Una algoritmo de b&uacute;squeda en orden topologico
	 */
	public static <V, E> TopologicalSearch<V, E> topological(Graph<V, E> g, V startVertex) {
		return new TopologicalSearch<V, E>(g, startVertex);
	}
	
	/**
	 * @param <V> El tipo de los v&eacute;rtices
	 * @param <E> El tipo de las aristas
	 * @param g Un grafo 
	 * @param startVertex El vértice inicial
	 * @return Una algoritmo de b&uacute;squeda en anchura
	 */
	public static <V, E> BreadthSearch<V, E> breadth(Graph<V, E> g, V startVertex) {
		return new BreadthSearch<V, E>(g, startVertex);
	}		
	/**
	 * @param <V> El tipo de los v&eacute;rtices
	 * @param <E> El tipo de las aristas 
	 * @param graph Un grafo extendido
	 * @param goal Un predicado que define el objetivo
	 * @param end Un vertice que cumple el objetivo
	 * @return Una algoritmo de b&uacute;squeda de Dijsktra
	 */
	public static <V, E> AStar<V, E> dijsktra(EGraph<V, E> graph, Predicate<V> goal, V end) {
		return new AStar<V, E>(graph, goal,end, (v1,v2,v3)->0.);
	}
	
	public static <V, E> AStar<V, E> dijsktra(EGraph<V, E> graph, Predicate<V> goal) {
		return new AStar<V, E>(graph, goal,null, (v1,v2,v3)->0.);
	}
	
	public static <V, E> AStar<V, E> dijsktra(EGraph<V, E> graph, V end) {
		return new AStar<V, E>(graph,e->e.equals(end),end,(v1,v2,v3)->0.);
	}
	
	/**
	 * @param <V> El tipo de los v&eacute;rtices
	 * @param <E> El tipo de las aristas 
	 * @param graph Un grafo
	 * @param end El v&eacute;rtice final
	 * @param heuristic La heur&iacute;stica 
	 * @return Una algoritmo de b&uacute;squeda de AStar
	 */
	public static <V, E> AStar<V, E> aStar(EGraph<V, E> graph, Predicate<V> goal, V end,
			TriFunction<V,Predicate<V>,V,Double> heuristic) {
		return new AStar<V, E>(graph, goal, end, heuristic);
	}
	
	public static <V, E> AStar<V, E> aStarGoal(EGraph<V, E> graph, Predicate<V> goal,
			TriFunction<V,Predicate<V>,V,Double> heuristic) {
		return new AStar<V, E>(graph, goal,null, heuristic);
	}
	
	public static <V, E> AStar<V, E> aStarEnd(EGraph<V, E> graph, V end,
			TriFunction<V,Predicate<V>,V,Double> heuristic) {
		return new AStar<V, E>(graph,e->e.equals(end), end, heuristic);
	}
	
	public static <V, E> AStarRandom<V, E> aStarRandom(EGraph<V, E> graph, Predicate<V> goal, V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic, Function<V, Integer> size) {
		return new AStarRandom<V, E>(graph, goal, end, heuristic, size);
	}
	
	public static <V, E> AStarRandom<V, E> aStarRandomGoal(EGraph<V, E> graph, Predicate<V> goal,
			TriFunction<V, Predicate<V>, V, Double> heuristic, Function<V, Integer> size) {
		return new AStarRandom<V, E>(graph, goal,null, heuristic, size);
	}
	
	public static <V, E> AStarRandom<V, E> aStarRandomEnd(EGraph<V, E> graph, V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic, Function<V, Integer> size) {
		return new AStarRandom<V, E>(graph,e->e.equals(end), end, heuristic, size);
	}
	
	public static <V, E> LocalSearch<V,E> local(EGraph<V, E> graph, Double error) {
		return new LocalSearch<V, E>(graph, error);
	}
	
	public static <V, E> SimulatedAnnealingSearch<V, E> simulatedAnnealing(EGraph<V, E> graph, V startVertex,
			Function<V, Double> fitness) {
		return new SimulatedAnnealingSearch<V, E>(graph, startVertex, fitness);
	}
	
	E getEdgeToOrigin(V v);
	EGraph<V, E> getGraph();
	V startVertex();
	Stream<V> stream();
	GraphAlg<V,E> copy();
	
	/**
	 * @return El ultimo vertice del recorrido
	 */
	default public V findEnd() {
		return this.stream().reduce((first,second)->second).get();
	}

}
