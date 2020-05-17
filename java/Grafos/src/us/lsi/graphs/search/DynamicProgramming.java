package us.lsi.graphs.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.graphs.search.Sp;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleHyperEdge;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.hypergraphs.VirtualHyperVertex;


public class DynamicProgramming<V extends VirtualHyperVertex<V,E,A>,
			E extends SimpleHyperEdge<V,A>,A> implements DPSearch<V, E, A> {
	

	public enum PDType{Min,Max}

	public SimpleVirtualHyperGraph<V,E, A> graph;
	private Comparator<Sp<E>> comparatorSp;
	public Map<V,Sp<E>> solutionsTree;
	private PDType type;
	
	DynamicProgramming(
			SimpleVirtualHyperGraph<V,E, A> graph, 
			PDType type) {
		this.graph = graph;
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
			if(w!=null) r = Sp.of(w);
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
					spa = Sp.of(edge,edge.getWeight(v->this.solutionsTree.get(v).weight));
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
	public GraphTree<V,E,A> tree(V vertex){
		return GraphTree.of(this.solutionsTree,vertex);
	}

}