package us.lsi.graphs.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.common.Preconditions;


public class DynamicProgrammingSearch<V, E, S> {
	
	public enum PDType{Min,Max}
	

	public Graph<V,E> graph;
	private V startVertex; 
	private V end;
	public Double bestValue;
	private BiFunction<V, V, Double> heuristic;
	private Comparator<List<E>> comparatorEdges;
	private Function<List<E>,S> solution;
	public Map<V,List<E>> solutions;
	private PDType type;
	
	DynamicProgrammingSearch(Graph<V, E> g, V startVertex, V end, 
			BiFunction<V,V,Double> heuristic,
			Function<List<E>,S> solution,
			PDType type) {
		this.graph = g;
		this.startVertex = startVertex;
		this.end = end;
		this.heuristic = heuristic;
		this.solution = solution;
		this.type = type;
		if(this.type == PDType.Min) this.comparatorEdges = Comparator.comparing(ls->this.listWeight(ls));
		if(this.type == PDType.Max) this.comparatorEdges = Comparator.<List<E>,Double>comparing(ls->this.listWeight(ls)).reversed();
		this.solutions = new HashMap<>();
		if(this.type == PDType.Max) this.bestValue = -Double.MAX_VALUE;
		if(this.type == PDType.Min) this.bestValue = Double.MAX_VALUE;
	}
	
	private Boolean forget(V actual, Double accumulateValue, E edge, V v) {
		Double w2 = this.graph.getEdgeWeight(edge);
		Double w3 = this.heuristic.apply(v,this.end);
		return accumulateValue+w2+w3 < this.bestValue;
	}
	
	private Double listWeight(List<E> ls) {
		return ls.stream().mapToDouble(e->this.graph.getEdgeWeight(e)).sum();
	}
	
	public S search() {
		this.solutions = new HashMap<>();
		List<E> edges = search(this.startVertex,0.);
		return this.solution.apply(edges);
	}
	
	private List<E> search(V actual, Double accumulateValue) {
		List<E> r = null;
		if(this.solutions.containsKey(actual)) {
			r = this.solutions.get(actual);
		} else if (actual.equals(this.end)) {
			return new ArrayList<>();
		} else {
			List<List<E>> rs = new ArrayList<>();
			for (V v : Graphs.neighborListOf(graph,actual)) {				
				E edge = graph.getEdge(actual, v);
				Double w = graph.getEdgeWeight(edge);
				Preconditions.checkState(edge != null,String.format("No hay arista entre %s y %s",actual,v));
				if (this.forget(actual,accumulateValue,edge,v)) continue;
				List<E> s = search(v,accumulateValue+w);
				if (s!=null) {
					s.add(0, edge);
					rs.add(s);
				}
			}
			r = rs.stream().filter(s->s!=null).min(this.comparatorEdges).get();
			this.solutions.put(actual, r);
		}
		return new ArrayList<>(r);
	}
	
	public S getSolution(V v){
		return this.solution.apply(solutions.get(v));
	}

}
