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

public class GraphWalkSum<V, E> extends GraphWalk<V,E> implements EGraphPath<V,E> {
	
	
	public static <V, E> GraphWalkSum<V, E> ofMap(EGraph<V, E> graph, V vertex, Map<V,Sp<E>> solutions){
		Preconditions.checkArgument(graph.pathType() == PathType.Sum, 
				String.format("El tipo del EGraphPath debe ser Sum y es %s",graph.pathType()));
		Sp<E> sp = solutions.get(vertex);
		GraphWalkSum<V, E> gp = GraphWalkSum.ofEdge(graph,sp.edge);
		while(sp.edge != null) {
			vertex = Graphs.getOppositeVertex(graph,sp.edge,vertex);
			sp = solutions.get(vertex);
			gp.add(sp.edge);
		}
		return gp;
	}
	
	public static <V, E> GraphWalkSum<V, E> ofEdge(EGraph<V, E> graph, E edge) {
		Preconditions.checkArgument(graph.pathType() == PathType.Sum, 
				String.format("El tipo del EGraphPath debe ser Sum y es %s",graph.pathType()));
		V startVertex = graph.getEdgeSource(edge);
		V endVertex = graph.getEdgeTarget(edge);
		List<V> vertexList = Lists2.of(startVertex,endVertex);
		List<E> edgeList = Lists2.of(edge);
		Double weight = graph.getEdgeWeight(edge);
	    weight += graph.getVertexWeight(startVertex);
		weight += graph.getVertexWeight(endVertex);
		return new GraphWalkSum<V, E>(graph, startVertex, endVertex, vertexList, edgeList, weight);
	}
	
	public static <V, E> GraphWalkSum<V, E> ofVertex(EGraph<V, E> graph, V vertex) {
		Preconditions.checkArgument(graph.pathType() == PathType.Sum, 
				String.format("El tipo del EGraphPath debe ser Sum y es %s",graph.pathType()));
		V startVertex = vertex;
		V endVertex = vertex;
		List<V> vertexList = Lists2.of(vertex);
		List<E> edgeList = Lists2.of();
		Double weight = 0.;
	    weight += graph.getVertexWeight(vertex);
		return new GraphWalkSum<V, E>(graph, startVertex, endVertex, vertexList, edgeList, weight);
	}

	private EGraph<V,E> graph;
	
	protected GraphWalkSum(EGraph<V, E> graph, V startVertex, V endVertex, 
			List<V> vertexList, List<E> edgeList,
			double weight) {
		super(graph, startVertex, endVertex, vertexList, edgeList, weight);
		this.graph = graph;
	}

	
	public GraphWalkSum<V,E> add(E edge) {
		if (!super.edgeList.isEmpty()) {
			E e = Lists2.last(super.edgeList);
			V v = Lists2.last(super.vertexList);
			super.weight -= graph.getVertexPassWeight(v, e, edge);
		}
		V target = graph.getEdgeTarget(edge);
		super.edgeList.add(edge);
		super.vertexList.add(target);
		super.weight += graph.getEdgeWeight(edge);
		super.weight += graph.getVertexWeight(target);		
		return this;
	}
	
	@Override
	public Double add(Double weight, E edge) {
		if (!super.edgeList.isEmpty()) {
			E e = Lists2.last(super.edgeList);
			V v = Lists2.last(super.vertexList);
			weight -= graph.getVertexPassWeight(v, e, edge);
		}
		V target = graph.getEdgeTarget(edge);
		weight += graph.getEdgeWeight(edge);
		weight += graph.getVertexWeight(target);		
		return weight;
	}
	
	
	public GraphWalkSum<V,E> removeLast() {
		E edge = this.lastEdge();
		V target = super.graph.getEdgeTarget(edge);
		super.edgeList.remove(edge);
		super.vertexList.remove(target);
		super.weight -= graph.getEdgeWeight(edge);
		super.weight -= graph.getVertexWeight(target);
		if (!super.edgeList.isEmpty()) {
			E e = Lists2.last(super.edgeList);
			V v = Lists2.last(super.vertexList);
			super.weight -= graph.getVertexPassWeight(v, e, edge);
		}
		return this;
	}
	
	@Override
	public Double removeLast(Double weight, E edge, E e2) {
		V target = super.graph.getEdgeTarget(edge);
		weight -= graph.getEdgeWeight(edge);
		weight -= graph.getVertexWeight(target);
		if (e2 != null) {
			V source = super.graph.getEdgeSource(edge);
			weight -= graph.getVertexPassWeight(source, e2, edge);
		}
		return weight;
	}
	
	public GraphWalkSum<V,E> copy() {
		return new GraphWalkSum<V, E>(this.graph, 
				this.startVertex, this.endVertex, 
				new ArrayList<>(this.vertexList), 
				new ArrayList<>(this.edgeList), 
				this.weight);
	}
	
	public GraphWalkSum<V,E> concat(GraphPath<V,E> path) {
		path.getEdgeList().stream().forEach(e->this.add(e));
		return this;
	}

	@Override
	public Double boundWeight(Predicate<V> goal, V end, E edge, TriFunction<V,Predicate<V>,V,Double> heuristic) {
		Double weight = super.weight;
		if (!super.edgeList.isEmpty()) {
			E e = Lists2.last(super.edgeList);
			V v = Lists2.last(super.vertexList);
			weight += graph.getVertexPassWeight(v, e, edge);
		}
		V target = graph.getEdgeTarget(edge);
		weight += graph.getEdgeWeight(edge);
		weight += graph.getVertexWeight(target);	
		return weight + heuristic.apply(target, goal, end);
	}
	
	@Override
	public Double boundWeight(Double weight,Predicate<V> goal, V end, E edge, TriFunction<V,Predicate<V>,V,Double> heuristic) {
		Double w = weight;
		if (!super.edgeList.isEmpty()) {
			E e = Lists2.last(super.edgeList);
			V v = Lists2.last(super.vertexList);
			w += graph.getVertexPassWeight(v, e, edge);
		}
		V target = graph.getEdgeTarget(edge);
		w += graph.getEdgeWeight(edge);
		w += graph.getVertexWeight(target);	
		return w + heuristic.apply(target, goal,end);
	}

	@Override
	public Double estimatedWeightToEnd(Predicate<V> goal, V end, TriFunction<V,Predicate<V>,V,Double> heuristic) {
		V last = super.getEndVertex();
		return super.getWeight()+heuristic.apply(last, goal, end);
	}
	
	@Override
	public Double estimatedWeightToEnd(Double weight, Predicate<V> goal, V end, TriFunction<V,Predicate<V>,V,Double> heuristic) {
		V last = super.getEndVertex();
		Double r =weight+heuristic.apply(last, goal, end);
//		System.out.println(String.format("w = %.2f,wToEnd = %.2f",weight,r));
//		Preconditions.checkArgument(r, message);
		return r;
	}

	@Override
	public E lastEdge() {
		return Lists2.last(this.edgeList);
	}

	@Override
	public PathType type() {
		return PathType.Sum;
	}	
	
}
