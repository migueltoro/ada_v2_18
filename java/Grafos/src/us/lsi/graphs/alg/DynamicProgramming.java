package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleHyperEdge;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.hypergraphs.VirtualHyperVertex;


public class DynamicProgramming<V extends VirtualHyperVertex<V,E,A>,
			E extends SimpleHyperEdge<V,E,A>,A> implements DP<V, E, A> {
	

	public enum PDType{Min,Max}

	public SimpleVirtualHyperGraph<V,E, A> graph;
	private Comparator<Sp<E>> comparatorSp;
	public Map<V,Sp<E>> solutionsTree;
	private PDType type;
	private V startVertex;
	
	DynamicProgramming(
			SimpleVirtualHyperGraph<V,E, A> graph, 
			PDType type) {
		this.graph = graph;
		this.startVertex = graph.getStartVertex();
		this.type = type;
		if(this.type == PDType.Min) this.comparatorSp = Comparator.naturalOrder();
		if(this.type == PDType.Max) this.comparatorSp = Comparator.<Sp<E>>naturalOrder().reversed();
		this.solutionsTree = new HashMap<>();
	}
	
	@Override
	public Sp<E> search(){
		return search(this.startVertex);
	}

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
				for (V neighbor : edge.targets()) {
					Sp<E> nb = search(neighbor);
					if (nb == null) {
						spNeighbors = null;
						break;
					}
					spNeighbors.add(nb);
				}
				Sp<E> spa = null;
				if(spNeighbors != null) {
					List<Double> solutions = spNeighbors.stream().map(sp->sp.weight()).toList();
					spa = Sp.of(edge.weight(solutions), edge);
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
	public PDType getType() {
		return type;
	}
	
	@Override
	public GraphTree<V,E,A> searchTree(V vertex){
		return GraphTree.of(this.solutionsTree,vertex);
	}
	
	public record Sp<E>(Double weight, E edge) implements Comparable<Sp<E>> {
		
		public static <E> Sp<E> of(Double weight,E edge) {
			return new Sp<>(weight,edge);
		}

		@Override
		public int compareTo(Sp<E> sp) {
			return this.weight.compareTo(sp.weight);
		}

	}

}