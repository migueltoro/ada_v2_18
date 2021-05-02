package us.lsi.alg.tsp;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.Graph;

import us.lsi.common.IntPair;
import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.flujosparalelos.Stream2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.virtual.ActionVirtualVertex;
//import us.lsi.path.GraphPaths;
import us.lsi.path.GraphPaths;

public record TravelVertexInteger(List<Integer> camino) implements ActionVirtualVertex<TravelVertexInteger, TravelEdgeInteger, IntPair> {
	
	public static TravelVertexInteger of(List<Integer> camino) {
		return new TravelVertexInteger(List2.copy(camino));
	}

	public static Graph<Integer,SimpleEdge<Integer>> graph;

	@Override
	public Boolean isValid() {
		return true;
	}
	
	IntPair greedyAction() {
		return this.actions().stream().min(Comparator.comparing(a->this.neighbor(a).weight())).get();
	}
	
	TravelEdgeInteger geedyEdge() {
		return this.edge(this.greedyAction());
	}
	
	TravelVertexInteger geedyVertex() {
		return this.neighbor(this.greedyAction());
	}

	@Override
	public List<IntPair> actions() {
		Integer n = camino.size()-1;
		return Stream2.allPairs(1,n,0,n-1)
				.filter(p->p.second() > p.first() +2)
				.collect(Collectors.toList());
	}

	@Override
	public TravelVertexInteger neighbor(IntPair a) {
		return TravelVertexInteger.of(AuxiliaryTsp.neighborInteger(this.camino,a));
	}

	@Override
	public TravelEdgeInteger edge(IntPair a) {
		TravelVertexInteger v = this.neighbor(a);
		Preconditions.checkArgument(!this.equals(v),"No puede ser igual al vecino");
		return TravelEdgeInteger.of(this,v,a);
	}

	@Override
	public String toString() {
		return String.format("(%.2f,%s",this.weight(),this.camino());
	}
	
	public Double weight() {
		return GraphPaths.of(TravelVertexInteger.graph,this.camino()).getWeight();
	}

}
