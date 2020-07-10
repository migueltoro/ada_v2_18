package us.lsi.path;

import java.util.Map;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.TriFunction;
import us.lsi.graphs.alg.Sp;
import us.lsi.graphs.virtual.EGraph;

public interface EGraphPath<V, E> extends GraphPath<V, E> {	
	E lastEdge();
	EGraphPath<V, E> add(E edge);
	Double add(Double weight, E edge);
	EGraphPath<V, E> removeLast();
	Double removeLast(Double weight, E edge, E e2);
	EGraphPath<V, E> copy();
	Double boundWeight(Predicate<V> goal,V end,E edge, TriFunction<V,Predicate<V>,V,Double> heuristic);
	Double boundWeight(Double weight, Predicate<V> goal, V end, E edge, TriFunction<V,Predicate<V>,V,Double> heuristic);
	Double estimatedWeightToEnd(Predicate<V> goal,V end,TriFunction<V,Predicate<V>,V,Double> heuristic);
	Double estimatedWeightToEnd(Double weight,Predicate<V> goal, V end, TriFunction<V,Predicate<V>,V,Double> heuristic);
	EGraphPath<V, E> concat(GraphPath<V,E> path);
	GraphPath<V, E> reverse();
	PathType type();
	
	public static enum PathType{Sum,Last}	
	
	public static <V,E> Double weight(GraphPath<V,E> path) {
		return path.getEdgeList().stream().mapToDouble(e->path.getGraph().getEdgeWeight(e)).sum();
	}
	
	public static <V, E> GraphWalkSum<V, E> ofMap(EGraph<V,E> graph, V vertex, Map<V,Sp<E>> solutions) {
		return GraphWalkSum.ofMap(graph, vertex, solutions);
	}
	
//	public static <V, E> GraphWalkSum<V, E> ofEdge(EGraph<V, E> graph, E edge){
//		return GraphWalkSum.ofEdge(graph, edge);
//	}
	
//	public static <V, E> EGraphPath<V, E> ofVertex(EGraph<V, E> graph, V vertex){
//		return EGraphPath.ofVertex(graph, vertex,PathType.Sum);
//	}
	
	public static <V, E> EGraphPath<V, E> ofVertex(EGraph<V, E> graph, V vertex, PathType type){
		EGraphPath<V, E> r = null;
		switch(type) {
		case Sum: r =  GraphWalkSum.ofVertex(graph, vertex); break;
		case Last: r = GraphWalkLast.ofVertex(graph, vertex); break;
		}
		return r;
	}

}
