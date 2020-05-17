package us.lsi.path;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.GraphWalk;

import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;
import us.lsi.common.TriFunction;
import us.lsi.graphs.search.Sp;
import us.lsi.graphs.virtual.EGraph;

public class GraphWalkLast<V, E> extends GraphWalk<V,E> implements EGraphPath<V,E> {
	
	public static <V, E> GraphWalkLast<V, E> ofMap(EGraph<V, E> graph, V vertex, Map<V,Sp<E>> solutions){
		Preconditions.checkArgument(graph.pathType() == PathType.Last, 
				String.format("El tipo del EGraphPath debe ser Last y es %s",graph.pathType()));
		Sp<E> sp = solutions.get(vertex);
		GraphWalkLast<V, E> gp = GraphWalkLast.ofEdge(graph,sp.edge);
		while(sp.edge != null) {
			vertex = Graphs.getOppositeVertex(graph,sp.edge,vertex);
			sp = solutions.get(vertex);
			gp.add(sp.edge);
		}
		return gp;
	}
	
	public static <V, E> GraphWalkLast<V, E> ofEdge(EGraph<V, E> graph, E edge) {
		Preconditions.checkArgument(graph.pathType() == PathType.Last, 
				String.format("El tipo del EGraphPath debe ser Last y es %s",graph.pathType()));
		V startVertex = graph.getEdgeSource(edge);
		V endVertex = graph.getEdgeTarget(edge);
		List<V> vertexList =Lists2.of(startVertex,endVertex);
		List<E> edgeList = Lists2.of(edge);
		Double weight = graph.getVertexWeight(endVertex);
		return new GraphWalkLast<V, E>(graph, startVertex, endVertex, vertexList, edgeList, weight);
	}
	
	public static <V, E> GraphWalkLast<V, E> ofVertex(EGraph<V, E> graph, V vertex) {
		Preconditions.checkArgument(graph.pathType() == PathType.Last, 
				String.format("El tipo del EGraphPath debe ser Last y es %s",graph.pathType()));
		V startVertex = vertex;
		V endVertex = vertex;
		List<V> vertexList = Lists2.of(vertex);
		List<E> edgeList = Lists2.of();
		Double weight = 0.;
	    weight += graph.getVertexWeight(vertex);
		return new GraphWalkLast<V, E>(graph, startVertex, endVertex, vertexList, edgeList, weight);
	}

	private EGraph<V,E> graph;
	
	protected GraphWalkLast(EGraph<V, E> graph, V startVertex, V endVertex, 
			List<V> vertexList, List<E> edgeList,
			double weight) {
		super(graph, startVertex, endVertex, vertexList, edgeList, weight);
		this.graph = graph;
	}

	
	public GraphWalkLast<V,E> add(E edge) {
		V target = graph.getEdgeTarget(edge);
		super.edgeList.add(edge);
		super.vertexList.add(target);
		super.weight = graph.getVertexWeight(target);	
		return this;
	}
	
	@Override
	public Double add(Double weight, E edge) {
		V target = super.graph.getEdgeTarget(edge);
		return graph.getVertexWeight(target);		
	}
	
	public GraphWalkLast<V,E> removeLast() {
		E edge = this.lastEdge();
		V target = graph.getEdgeTarget(edge);
		V source = graph.getEdgeSource(edge);
		super.edgeList.remove(edge);
		super.vertexList.remove(target);
		super.weight = graph.getVertexWeight(source);
		return this;
	}
	
	@Override
	public Double removeLast(Double weight, E edge, E e2) {
		V source = graph.getEdgeSource(edge);
		weight = graph.getVertexWeight(source);
		return weight;
	}
	
	public GraphWalkLast<V,E> copy() {
		return new GraphWalkLast<V, E>(this.graph, 
				this.startVertex, this.endVertex, 
				new ArrayList<>(this.vertexList), 
				new ArrayList<>(this.edgeList), 
				this.weight);
	}
	
	public GraphWalkLast<V,E> concat(GraphPath<V,E> path) {
		path.getEdgeList().stream().forEach(e->this.add(e));
		return this;
	}

	@Override
	public Double boundWeight(Predicate<V> goal, V end, E edge, TriFunction<V,Predicate<V>,V,Double> heuristic) {
		V target = super.graph.getEdgeTarget(edge);
		Double weight = graph.getVertexWeight(target);	
		return weight + heuristic.apply(target, goal, end);
	}
	
	@Override
	public Double boundWeight(Double weight, Predicate<V> goal, V end, E edge, TriFunction<V,Predicate<V>,V,Double> heuristic) {
		V target = super.graph.getEdgeTarget(edge);
		Double w = graph.getVertexWeight(target);	
		return w + heuristic.apply(target, goal, end);
	}

	@Override
	public Double estimatedWeightToEnd(Predicate<V> goal, V end, TriFunction<V,Predicate<V>,V,Double> heuristic) {
		V last = super.getEndVertex();
		return super.getWeight()+heuristic.apply(last, goal, end);
	}
	
	@Override
	public Double estimatedWeightToEnd(Double weight,Predicate<V> goal, V end, TriFunction<V,Predicate<V>,V,Double> heuristic) {
		V last = super.getEndVertex();
		return weight+heuristic.apply(last, goal, end);
	}
	
	@Override
	public E lastEdge() {
		return Lists2.last(this.edgeList);
	}
	
	@Override
	public double getWeight() {
		return super.weight;
	}

	@Override
	public PathType type() {
		return PathType.Last;
	}

	
	
}

