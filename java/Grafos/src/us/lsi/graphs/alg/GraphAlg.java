package us.lsi.graphs.alg;

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
	 * @param goal Un predicado 
	 * @return Encuentra el priemr v&eacute;rtice que cumple el predicado seg&uacute;n la b&uacute;squeda seguida
	 */
	default public Optional<V> find(Predicate<V> goal) {
		Preconditions.checkNotNull(this.startVertex(),"No puede ser null startVertex");
		Preconditions.checkNotNull(goal,"No puede ser null goal");
		if(goal.test(this.startVertex())) return Optional.of(this.startVertex());
		return this.stream().filter(goal).findFirst();
	}	
	
	/**
	 * @param end Un v&eacute;rtice 
	 * @return Encuentra el primer v&eacute;rtice que que es igual a v seg&uacute;n la b&uacute;squeda seguida
	 */
	default public Optional<V> find(V end) {
		Preconditions.checkNotNull(this.startVertex(),"No puede ser null");
		if(this.startVertex().equals(end)) return Optional.of(startVertex());
		return this.stream().filter(e->e.equals(end)).findFirst();
	}
	
	/**
	 * @return El ultimo vertice del recorrido
	 */
	default public Optional<V> findEnd() {
		return this.stream().reduce((first,second)->second);
	}
	
	/**
	 * @param vertex Un vertice
	 * @return Camino desde el vertice inicial hasta el vertice vertex
	 */
	default public Optional<GraphPath<V, E>> pathTo(V vertex) {
		Optional<V> end = this.find(vertex);
		Optional<GraphPath<V, E>> r = Optional.empty();
		if(end.isPresent()) {
			GraphPath<V, E> s =GraphAlg.pathBackEdge(this.getGraph(),end.get(),x->this.getEdgeToOrigin(x));
			r = Optional.of(s);
		}
		return r;
	}
	
	
	/**
	 * @param goal Un predicado
	 * @return Camino desde el vertice inicial hasta el primer vertice que cumple el predicado
	 */
	default public Optional<GraphPath<V, E>> pathTo(Predicate<V> goal) {
		Optional<V> end = this.find(goal);
		Optional<GraphPath<V, E>> r = Optional.empty();
		if(end.isPresent()) {
			GraphPath<V, E> s = GraphAlg.pathBackEdge(this.getGraph(), end.get(), v->this.getEdgeToOrigin(v));
			r = Optional.of(s);
		}
		return r;
	}

	/**
	 * @return Camino desde el vertice inicial hasta el útimo vertice del recorrido
	 */
	default public Optional<GraphPath<V, E>> pathToEnd() {
		Optional<V> end = this.findEnd();
		Optional<GraphPath<V, E>> r = Optional.empty();
		if(end.isPresent()) {
			GraphPath<V, E> s = GraphAlg.pathBackEdge(this.getGraph(),end.get(),v->this.getEdgeToOrigin(v));
			r = Optional.of(s);
		}
		return r;
	}
	
	
	/**
	 * @param end Un vertice 
	 * @return Peso del camino desde el vertice inicial hasta el vertice end
	 */
	default public Optional<Double> weight(V end) {
		if(end.equals(startVertex())) return Optional.of(0.);
		Optional<Double> r = Optional.empty();
		Optional<GraphPath<V, E>> p = this.pathTo(end);
		if(p.isPresent()) {
			Double s = p.get().getWeight();
			r = Optional.of(s);
		}
		return r;
	}
	
	/**
	 * @param goal Un prdicado
	 * @return Peso del camino desde el vertice inicial hasta primer vertice que cumple el predicado
	 */
	default public Optional<Double> weight(Predicate<V> goal) {
		if(goal.test(startVertex())) return Optional.of(0.);
		Optional<Double> r = Optional.empty();
		Optional<GraphPath<V, E>> p = this.pathTo(goal);
		if(p.isPresent()) {
			Double s = p.get().getWeight();
			r = Optional.of(s);
		}
		return r;
	}
	
	/**
	 * @return Peso del camino desde el vertice inicial hasta el útimo vertice del recorrido
	 */
	default public Optional<Double> weightToEnd() {
		Optional<V> end = this.stream().reduce((first,second)->second);
		Optional<Double> r = Optional.empty();
		if(end.isPresent()) {
			Double s = GraphAlg.pathBackEdge(this.getGraph(),end.get(),v->this.getEdgeToOrigin(v)).getWeight();
			r = Optional.of(s);
		}
		return r;
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
