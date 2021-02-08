package us.lsi.alg.floyd.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.GraphWalk;

import us.lsi.alg.floyd.FloydEdge;
import us.lsi.alg.floyd.FloydVertex;
import us.lsi.alg.floyd.FloydVertex.ActionFloyd;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.alg.Sp;
import us.lsi.hypergraphs.GraphTree;


public class FloydPD {

	public static FloydPD of(FloydVertex startVertex) {
		return new FloydPD(startVertex);
	}

	public Map<FloydVertex,Sp<FloydEdge>> solutionsTree;
	private FloydVertex startVertex;
	
	private FloydPD(FloydVertex startVertex) {
		this.startVertex = startVertex;
		this.solutionsTree = new HashMap<>();
	}
	
	public GraphWalk<Integer, SimpleEdge<Integer>> search(){
		search(this.startVertex);
		return FloydVertex.solution(this.solutionsTree,this.startVertex);
	}

	public Sp<FloydEdge> search(FloydVertex actual) {
		Sp<FloydEdge> r = null;
		if (this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (actual.isBaseCase()) {
			Double w = actual.baseCaseSolution();
			if(w!=null) r = Sp.of(w);
			else r = null;
			this.solutionsTree.put(actual, r);
		} else {
			List<Sp<FloydEdge>> sps = new ArrayList<>();
			for (FloydEdge edge : actual.edgesOf()) {
				List<Sp<FloydEdge>> spNeighbors = new ArrayList<>();
				for (FloydVertex neighbor : edge.targets) {
					Sp<FloydEdge> nb = search(neighbor);
					if (nb == null) {spNeighbors = null; break;}
					spNeighbors.add(nb);
				}
				Sp<FloydEdge> spa = null;
				if(spNeighbors != null) {
					spa = Sp.of(edge,edge.getWeight(v->this.solutionsTree.get(v).weight));
				}
				sps.add(spa);
			}
			r = sps.stream().filter(s -> s != null).min(Comparator.naturalOrder()).orElse(null);
			this.solutionsTree.put(actual, r);
		}
		return r;
	}
	
	public GraphTree<FloydVertex,FloydEdge,ActionFloyd> searchTree(FloydVertex vertex){
		return GraphTree.of(this.solutionsTree,vertex);
	}
	

}
