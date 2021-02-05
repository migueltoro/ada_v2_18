package us.lsi.alg.tsp;

import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.Graph;

import us.lsi.common.IntPair;
import us.lsi.common.Lists2;
import us.lsi.flujosparalelos.Streams2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.virtual.ActionVirtualVertex;
//import us.lsi.path.GraphPaths;
import us.lsi.path.GraphPaths;

public class TravelVertexInteger extends ActionVirtualVertex<TravelVertexInteger, TravelEdgeInteger, IntPair> {
	
	public static TravelVertexInteger of(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> camino) {
		return new TravelVertexInteger(graph, camino, GraphPaths.of(graph,camino).getWeight());
	}

	public final Graph<Integer,SimpleEdge<Integer>> graph;
	public final List<Integer> camino;
	public final Double weight;
	public Integer n;

	TravelVertexInteger(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> camino, Double weight) {
		super();
		this.graph = graph;
		this.camino = Lists2.copy(camino);
		this.weight = weight;
		this.n = camino.size()-1;
	}

	@Override
	public Boolean isValid() {
		return true;
	}

	@Override
	public List<IntPair> actions() {
		return Streams2.allPairs(1,n,0,n-1)
				.filter(p->p.second > p.first)
				.collect(Collectors.toList());
	}

	@Override
	public TravelVertexInteger neighbor(IntPair a) {
		return TravelVertexInteger.of(this.graph, AuxiliaryTsp.neighborInteger(this.graph,this.camino,a.first,a.second));
	}

	@Override
	public TravelEdgeInteger edge(IntPair a) {
		return TravelEdgeInteger.of(this,this.neighbor(a),a);
	}

	@Override
	public String toString() {
		return String.format("(%.2f,%s",this.getWeight(),camino);
	}
	
	public Double getWeight() {
		return GraphPaths.of(graph,camino).getWeight();
	}

}
