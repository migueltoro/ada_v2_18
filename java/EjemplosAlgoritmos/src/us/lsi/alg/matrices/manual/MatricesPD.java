package us.lsi.alg.matrices.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import us.lsi.alg.matrices.Auxiliar;
import us.lsi.alg.matrices.MatrixEdge;
import us.lsi.alg.matrices.MatrixVertex;
import us.lsi.graphs.alg.Sp;
import us.lsi.hypergraphs.GraphTree;

public class MatricesPD {
	
	public static MatricesPD of(MatrixVertex startVertex) {
		return new MatricesPD(startVertex);
	}

	public Map<MatrixVertex,Sp<MatrixEdge>> solutionsTree;
	private MatrixVertex startVertex;
	
	private MatricesPD(MatrixVertex startVertex) {
		this.startVertex = startVertex;
		this.solutionsTree = new HashMap<>();
	}
	
	public String search(){
		search(this.startVertex);
		return Auxiliar.solucion(GraphTree.of(this.solutionsTree,this.startVertex));
	}

	public Sp<MatrixEdge> search(MatrixVertex actual) {
		Sp<MatrixEdge> r = null;
		if (this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (actual.isBaseCase()) {
			Double w = actual.baseCaseSolution();
			if(w!=null) r = Sp.of(w);
			else r = null;
			this.solutionsTree.put(actual, r);
		} else {
			List<Sp<MatrixEdge>> sps = new ArrayList<>();
			for (MatrixEdge edge : actual.edgesOf()) {
				List<Sp<MatrixEdge>> spNeighbors = new ArrayList<>();
				for (MatrixVertex neighbor : edge.targets) {
					Sp<MatrixEdge> nb = search(neighbor);
					if (nb == null) {spNeighbors = null; break;}
					spNeighbors.add(nb);
				}
				Sp<MatrixEdge> spa = null;
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
	
	public GraphTree<MatrixVertex,MatrixEdge,Integer> searchTree(MatrixVertex vertex){
		return GraphTree.of(this.solutionsTree,vertex);
	}
	
}
