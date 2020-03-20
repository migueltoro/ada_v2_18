package us.lsi.graphs.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


import us.lsi.graphs.hypergraphs.SimpleHyperEdge;
import us.lsi.graphs.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.graphs.hypergraphs.GraphTree;
import us.lsi.graphs.hypergraphs.VirtualHyperVertex;



public class DynamicProgrammingSearch<V extends VirtualHyperVertex<V,E,A>,
			E extends SimpleHyperEdge<V,A>,A> implements DPSearch<V, E, A> {
	

	public enum PDType{Min,Max}

	public SimpleVirtualHyperGraph<V,E, A> graph;
	private Comparator<Sp<E>> comparatorSp;
	private Function<List<Double>,Double> addSolution;
	public Map<V,Sp<E>> solutionsTree;
	private PDType type;
	
	DynamicProgrammingSearch(
			SimpleVirtualHyperGraph<V,E, A> graph, 
			Function<List<Double>,Double> addSolution,
			PDType type) {
		this.graph = graph;
		this.addSolution = addSolution;
		this.type = type;
		if(this.type == PDType.Min) this.comparatorSp = Comparator.naturalOrder();
		if(this.type == PDType.Max) this.comparatorSp = Comparator.<Sp<E>>naturalOrder().reversed();
		this.solutionsTree = new HashMap<>();
	}
	
	@Override
	public Sp<E> search(V actual) {
		Sp<E> r = null;
		if (this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (actual.isBaseCase()) {
			Double w = actual.baseCaseSolution();
			if(w!=null) r = Sp.of(w,null);
			else r = null;
			this.solutionsTree.put(actual, r);
		} else {
			List<Sp<E>> sps = new ArrayList<>();
			for (E edge : graph.edgesOf(actual)) {
				List<Sp<E>> spNeighbors = new ArrayList<>();
				Boolean isNull = false;
				for (V neighbor : edge.targets) {
					Sp<E> nb = search(neighbor);
					if (nb == null) {
						isNull = true;
						break;
					}
					spNeighbors.add(nb);
				}
				Sp<E> spa = null;
				if(!isNull) {
					List<Double> weights = spNeighbors.stream().map(sp->sp.weight).collect(Collectors.toList());
					Double weight = this.addSolution.apply(weights);
					spa = Sp.of(weight, edge);
				}
				sps.add(spa);
			}
			r = sps.stream().filter(s -> s != null).min(this.comparatorSp).orElse(null);
			this.solutionsTree.put(actual, r);
		}
		return r;
	}
	@Override
	public SimpleVirtualHyperGraph<V, E, A> getGraph() {
		return graph;
	}
	@Override
	public Map<V, Sp<E>> getSolutionsTree() {
		return solutionsTree;
	}
	@Override
	public PDType getType() {
		return type;
	}
	@Override
	public GraphTree<V,E,A> tree(V vertex){
		return GraphTree.of(this.solutionsTree);
	}
	
	public static class Sp<E> implements Comparable<Sp<E>>{
		public Double weight;
		public E edge;
		public static <E> Sp<E> of(Double weight, E edge){
			return new Sp<>(weight,edge);
		}
		public static <E> Sp<E> empty(){
			return new Sp<>(0.,null);
		}
		public Sp(Double weight, E edge) {
			super();
			this.weight = weight;
			this.edge = edge;
		}
		@Override
		public int compareTo(Sp<E> sp) {
			return this.weight.compareTo(sp.weight);
		}
		@Override
		public String toString() {
			return String.format("(%.2f,%s)",weight,edge!=null?edge.toString():"_");
		}
		
	}

}