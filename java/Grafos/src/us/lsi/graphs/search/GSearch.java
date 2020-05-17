package us.lsi.graphs.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;


import us.lsi.common.Preconditions;
import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

/**
 * @author migueltoro
 *
 * @param <V> El tipo de los v&eacute;rtices
 * @param <E> El tipo de las aristas
 * 
 * Distintos tipos de b&uacute;squedas sobre grafos
 * 
 */
public interface GSearch<V,E>  {
	
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
	 * @return Una algoritmo de b&uacute;squeda en profundidad
	 */
	public static <V, E> DephtSearch<V, E> depth(Graph<V, E> g, V startVertex) {
		return new DephtSearch<V, E>(g, startVertex);
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
	public static <V, E> AStarSearch<V, E> dijsktra(EGraph<V, E> graph, Predicate<V> goal, V end) {
		return new AStarSearch<V, E>(graph, goal,end, (v1,v2,v3)->0.);
	}
	
	public static <V, E> AStarSearch<V, E> dijsktra(EGraph<V, E> graph, Predicate<V> goal) {
		return new AStarSearch<V, E>(graph, goal,null, (v1,v2,v3)->0.);
	}
	
	public static <V, E> AStarSearch<V, E> dijsktra(EGraph<V, E> graph, V end) {
		return new AStarSearch<V, E>(graph,e->e.equals(end),end,(v1,v2,v3)->0.);
	}
	
	/**
	 * @param <V> El tipo de los v&eacute;rtices
	 * @param <E> El tipo de las aristas 
	 * @param graph Un grafo
	 * @param end El v&eacute;rtice final
	 * @param heuristic La heur&iacute;stica 
	 * @return Una algoritmo de b&uacute;squeda de AStar
	 */
	public static <V, E> AStarSearch<V, E> aStar(EGraph<V, E> graph, Predicate<V> goal, V end,
			TriFunction<V,Predicate<V>,V,Double> heuristic) {
		return new AStarSearch<V, E>(graph, goal, end, heuristic);
	}
	
	public static <V, E> AStarSearch<V, E> aStarGoal(EGraph<V, E> graph, Predicate<V> goal,
			TriFunction<V,Predicate<V>,V,Double> heuristic) {
		return new AStarSearch<V, E>(graph, goal,null, heuristic);
	}
	
	public static <V, E> AStarSearch<V, E> aStarEnd(EGraph<V, E> graph, V end,
			TriFunction<V,Predicate<V>,V,Double> heuristic) {
		return new AStarSearch<V, E>(graph,e->e.equals(end), end, heuristic);
	}
	
	public static <V, E> AStarSearchRandom<V, E> aStarRandom(EGraph<V, E> graph, Predicate<V> goal, V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic, Function<V, Integer> size) {
		return new AStarSearchRandom<V, E>(graph, goal, end, heuristic, size);
	}
	
	public static <V, E> AStarSearchRandom<V, E> aStarRandomGoal(EGraph<V, E> graph, Predicate<V> goal,
			TriFunction<V, Predicate<V>, V, Double> heuristic, Function<V, Integer> size) {
		return new AStarSearchRandom<V, E>(graph, goal,null, heuristic, size);
	}
	
	public static <V, E> AStarSearchRandom<V, E> aStarRandomEnd(EGraph<V, E> graph, V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic, Function<V, Integer> size) {
		return new AStarSearchRandom<V, E>(graph,e->e.equals(end), end, heuristic, size);
	}
	
	public static <V, E> LocalSearch<V, E> local(EGraph<V, E> graph, Predicate<E> stop) {
		return new LocalSearch<V, E>(graph, stop);
	}
	
	public static <V, E> SimulatedAnnealingSearch<V, E> simulatedAnnealing(EGraph<V, E> graph, V startVertex,
			Function<V, Double> fitness) {
		return new SimulatedAnnealingSearch<V, E>(graph, startVertex, fitness);
	}
	
	E getEdgeToOrigin(V v);
	EGraph<V, E> getGraph();
	V startVertex();
	Stream<V> stream();
	GSearch<V,E> copy();
		
	
	/**
	 * @param goal Un predicado 
	 * @return Encuentra el priemr v&eacute;rtice que cumple el predicado seg&uacute;n la b&uacute;squeda seguida
	 */
	default public V find(Predicate<V> goal) {
		if(goal.test(startVertex())) return startVertex();
		Optional<V> r = this.stream().filter(goal).findFirst();
		Preconditions.checkArgument(r.isPresent(), "No se ha encontrado un vértice que cumpla el predicado");
		return r.get();
	}	
	
	/**
	 * @param end Un v&eacute;rtice 
	 * @return Encuentra el primer v&eacute;rtice que que es igual a v seg&uacute;n la b&uacute;squeda seguida
	 */
	default public V find(V end) {
		if(this.startVertex().equals(end)) return startVertex();
		Optional<V> r = this.stream().filter(e->e.equals(end)).findFirst();
		Preconditions.checkArgument(r.isPresent(), 
				String.format("No se ha encontrado el vértice %s",end));
		return r.get();
	}
	
	/**
	 * @return El ultimo vertice del recorrido
	 */
	default public V findEnd() {
		Optional<V> end = this.stream().reduce((first,second)->second);
		Preconditions.checkArgument(end.isPresent(), 
				String.format("No se ha encontrado el vértice %s",end));
		return end.get();
	}
	
	/**
	 * @param vertex Un vertice
	 * @return Camino desde el vertice inicial hasta el vertice vertex
	 */
	default public GraphPath<V, E> pathTo(V vertex) {
		V end = this.find(vertex);
		return GSearch.pathBackEdge(this.getGraph(),end,x->this.getEdgeToOrigin(x));
	}
	
	
	/**
	 * @param goal Un predicado
	 * @return Camino desde el vertice inicial hasta el primer vertice que cumple el predicado
	 */
	default public GraphPath<V, E> pathTo(Predicate<V> goal) {
		V end = this.find(goal);
		return GSearch.pathBackEdge(this.getGraph(), end, v->this.getEdgeToOrigin(v));
	}

	/**
	 * @return Camino desde el vertice inicial hasta el útimo vertice del recorrido
	 */
	default public GraphPath<V, E> pathToEnd() {
		V end = this.findEnd();
		return GSearch.pathBackEdge(this.getGraph(),end,v->this.getEdgeToOrigin(v));
	}
	
	
	/**
	 * @param end Un vertice 
	 * @return Peso del camino desde el vertice inicial hasta el vertice end
	 */
	default public Double weight(V end) {
		if(end.equals(startVertex())) return 0.;
		return this.pathTo(end).getWeight();
	}
	
	/**
	 * @param goal Un prdicado
	 * @return Peso del camino desde el vertice inicial hasta primer vertice que cumple el predicado
	 */
	default public Double weight(Predicate<V> goal) {
		if(goal.test(startVertex())) return 0.;
		return this.pathTo(goal).getWeight();
	}
	
	/**
	 * @return Peso del camino desde el vertice inicial hasta el útimo vertice del recorrido
	 */
	default public Double weightToEnd() {
		Optional<V> end = this.stream().reduce((first,second)->second);
		Preconditions.checkArgument(end.isPresent(), 
				String.format("No se ha encontrado el vértice %s",end));
		return GSearch.pathBackEdge(this.getGraph(),end.get(),v->this.getEdgeToOrigin(v)).getWeight();
	}
	
	
	/**
	 * @param graph Un grafo extendido
	 * @param end Un vértice
	 * @param backEdge Una función que para cada vertice nos proporciona el vertice anterior en el camino
	 * @return Camino desde el vertice inicial, definido en el grafo extendido, hasta el vertice end
	 */
	public static <V,E> EGraphPath<V,E> pathBackEdge(EGraph<V,E> graph, V end, Function<V,E> backEdge){
		EGraphPath<V,E> ePath = graph.initialPath();
		V startVertex = graph.startVertex();
		if(end.equals(startVertex)) return ePath;
		E edge = backEdge.apply(end);
		List<E> edges = new ArrayList<>();		
		while(edge!=null) {				
			edges.add(edge);
			end = Graphs.getOppositeVertex(graph, edge, end);
			edge = backEdge.apply(end);			
		}
		Collections.reverse(edges);
		for(E e:edges) {
			ePath.add(e);
		}
		return ePath;
	}
	/**
	 * @param graph Un grafo extendido
	 * @param vertex Un vértice
	 * @param forwardEdge Una función que para cada vertice nos proporciona el vertice siguiente en el camino
	 * @return Camino desde el vertice vertex y siguiendo los vertices proporcionados por forwardEdge hasta que el vertice 
	 * siguiente sea null
	 */
	public static <V,E> EGraphPath<V,E> pathForwardEdged(EGraph<V,E> graph, V vertex, Function<V,E> forwardEdge){
		EGraphPath<V,E> ePath = graph.initialPath();
		E edge = forwardEdge.apply(vertex);
		List<E> edges = new ArrayList<>();		
		while(edge != null) {
			edges.add(edge);
			vertex = Graphs.getOppositeVertex(graph,edge,vertex);
			edge = forwardEdge.apply(vertex);			
		}
		for(E e:edges) {
			ePath.add(e);
		}
		return ePath;
	}
	
	
}
