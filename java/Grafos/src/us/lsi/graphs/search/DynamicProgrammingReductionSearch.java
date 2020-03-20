package us.lsi.graphs.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.jgrapht.graph.GraphWalk;

import us.lsi.common.Preconditions;
import us.lsi.graphs.virtual.EGraph;



public class DynamicProgrammingReductionSearch<V, E, S> implements DPRSearch<V, E, S> {
	
	public enum PDRType{Min,Max}
	
	public EGraph<V,E> graph;
	private V startVertex; 
	private V end;
	public Double bestValue;
	private BiFunction<V, V, Double> heuristic;
	private Comparator<SpR<E>> comparatorEdges;
	private Function<List<E>,S> solution;
	public Map<V,SpR<E>> solutionsTree;
	private PDRType type;
	
	DynamicProgrammingReductionSearch(EGraph<V, E> g, V startVertex, V end, 
			BiFunction<V,V,Double> heuristic,
			Function<List<E>,S> solution,
			PDRType type) {
		this.graph = g;
		this.startVertex = startVertex;
		this.end = end;
		this.heuristic = heuristic;
		this.solution = solution;
		this.type = type;
		if(this.type == PDRType.Min) this.comparatorEdges = Comparator.naturalOrder();
		if(this.type == PDRType.Max) this.comparatorEdges = Comparator.<SpR<E>>naturalOrder().reversed();
		this.solutionsTree = new HashMap<>();
		if(this.type == PDRType.Max) this.bestValue = -Double.MAX_VALUE;
		if(this.type == PDRType.Min) this.bestValue = Double.MAX_VALUE;
	}
	
	private Boolean forget(V actual, Double accumulateValue, E edge, V v) {
		Double w2 = this.graph.getEdgeWeight(edge);
		Double w3 = this.heuristic.apply(v,this.end);
		return accumulateValue+w2+w3 < this.bestValue;
	}
	
	public SpR<E> search() {
		this.solutionsTree = new HashMap<>();
		return search(this.startVertex,0.);
	}
	
	private SpR<E> search(V actual, Double accumulateValue) {
		SpR<E> r = null;
		if(this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (actual.equals(this.end)) {
			r = SpR.empty();
			this.solutionsTree.put(actual, r);
			return r;
		} else {
			List<SpR<E>> rs = new ArrayList<>();
			for (E edge : graph.edgesListOf(actual)) {				
				Double w = graph.getEdgeWeight(edge);
				V v = graph.getEdgeTarget(edge);
				Preconditions.checkState(edge != null,String.format("No hay arista entre %s y %s",actual,v));
				if (this.forget(actual,accumulateValue,edge,v)) continue;
				SpR<E> s = search(v,accumulateValue+w);
				if (s!=null) {					
					SpR<E> sp = SpR.of(w+s.weight, edge);
					rs.add(sp);
				}
			}
			r = rs.stream().filter(s->s!=null).min(this.comparatorEdges).get();
			this.solutionsTree.put(actual, r);
		}
		return r;
	}
	
	public GraphWalk<V,E> path(V v){
		SpR<E> sp = this.solutionsTree.get(v);
		Double w = sp.weight;
		List<V> ls = new ArrayList<>();
		ls.add(v);
		while(sp.edge != null) {
			V actual = this.graph.getEdgeTarget(sp.edge);	
			ls.add(actual);
			sp = this.solutionsTree.get(actual);
			Preconditions.checkNotNull(sp);
		}
		return new GraphWalk<>(this.graph,ls,w);
	}
	
	public S getSolution(V v){
		List<E> edges = this.path(v).getEdgeList();
		return this.solution.apply(edges);
	}
	
	public static class SpR<E> implements Comparable<SpR<E>> {
		public Double weight;
		public E edge;

		public static <E> SpR<E> of(Double weight, E edge) {
			return new SpR<>(weight, edge);
		}

		public static <E> SpR<E> empty() {
			return new SpR<>(0., null);
		}

		public SpR(Double weight, E edge) {
			super();
			this.weight = weight;
			this.edge = edge;
		}

		@Override
		public int compareTo(SpR<E> sp) {
			return this.weight.compareTo(sp.weight);
		}

		@Override
		public String toString() {
			return String.format("(%.2f,%s)", weight, edge != null ? edge.toString() : "_");
		}

	}

}
