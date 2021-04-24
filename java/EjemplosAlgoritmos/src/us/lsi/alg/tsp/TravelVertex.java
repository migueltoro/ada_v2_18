package us.lsi.alg.tsp;

import java.util.List;
import java.util.stream.Collectors;
import org.jgrapht.Graph;

import us.lsi.common.IntPair;
import us.lsi.common.Lists2;
import us.lsi.flujosparalelos.Streams2;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.grafos.datos.Carretera;
import us.lsi.graphs.virtual.ActionVirtualVertex;
import us.lsi.path.GraphPaths;

public class TravelVertex implements ActionVirtualVertex<TravelVertex, TravelEdge, IntPair> {
	
	public static TravelVertex of(Graph<Ciudad, Carretera> graph, List<Ciudad> camino) {
		return new TravelVertex(graph, camino);
	}

	public final Graph<Ciudad,Carretera> graph;
	public final List<Ciudad> camino;
	public final Double weight;
	public Integer n;

	TravelVertex(Graph<Ciudad,Carretera> graph, List<Ciudad> camino) {
		super();
		this.graph = graph;
		this.camino = Lists2.copy(camino);
		this.weight = GraphPaths.of(graph,camino).getWeight();
		this.n = camino.size();
	}

	@Override
	public Boolean isValid() {
		return true;
	}

	@Override
	public List<IntPair> actions() {
		return Streams2.allPairs(0,n,0,n)
				.filter(p->p.second > p.first)
				.collect(Collectors.toList());
	}

	@Override
	public TravelVertex neighbor(IntPair a) {
		return TravelVertex.of(this.graph, AuxiliaryTsp.neighbor(this.graph,this.camino,a.first,a.second));
	}

	@Override
	public TravelEdge edge(IntPair a) {
		return TravelEdge.of(this,this.neighbor(a),a);
	}

	@Override
	public String toString() {
		return String.format("(%.2f,%s",weight,camino);
	}

}
