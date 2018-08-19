package us.lsi.astar;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import us.lsi.common.Metricas;

/**
 * Interface y factoría de un algoritmo A*. 
 * 
 * @author Miguel Toro
 *
 * @param <V> Tipo de los vértices
 * @param <E> Tipo de las aristas
 */
public interface AlgoritmoAStar<V, E> {
	
	public static Metricas metricas = new Metricas();
	public static Boolean metricasOK = false;
	
	/**
	 * Un algoritmo AStar para ir del vértice de inicio hasta el vértice destino
	 * 
	 * @param <V> Tipo del vértice
	 * @param <E> Tipo de la arista
	 * @param graph Grafo 
	 * @param startVertex Vértice origen
	 * @param endVertex Vértice destino
	 * @return Algoritmo AStar
	 * 
	 */
	public static <V, E> AlgoritmoAStar<V, E> create(
			AStarGraph<V, E> graph, V startVertex, V endVertex) {
		return new AStarAlgorithm2<V,E>(graph,startVertex,endVertex); 
	}
	/**
	 * Un algoritmo AStar para ir del vértice de inicio hasta el  primer vértice que cumple el predicado
	 * @param <V> Tipo del vértice
	 * @param <E> Tipo de la arista
	 * @param graph Grafo
	 * @param startVertex Vértice origen
	 * @param goal Predicate que especifica el objetivo
	 * @return Algoritmo AStar
	 */
	
	public static <V, E> AlgoritmoAStar<V, E> create(
			AStarGraph<V, E> graph, V startVertex, Predicate<V> goal) {
		return new AStarAlgorithm2<V,E>(graph,startVertex,goal);
	}

	/**
	 * Un algoritmo  DijkstraShortestPath para ir del vértice de inicio hasta el vértice destino
	 * 
	 * @param <V> Tipo del vértice
	 * @param <E> Tipo de la arista
	 * @param graph Grafo 
	 * @return Algoritmo DijkstraShortestPath
	 * 
	 */
	public static <V, E> DijkstraShortestPath<V,E> createDijkstra(Graph<V, E> graph) {
		return new DijkstraShortestPath<V,E>(graph);
	}
	
	GraphPath<V, E> getPath();  
    
	List<V> getPathVertexList();
}
