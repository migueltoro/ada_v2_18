package us.lsi.graphs.alg;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import us.lsi.common.Lists2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.math.Math2;

public class SimulatedAnnealingSearch<V,E> {
	

	private EGraph<V,E> graph;
	private V actualVertex;
	private V startVertex;
	private Function<V,Double> fitness;
	private double temperatura;
	public V bestVertex;
	public Double bestWeight;
	
		
	SimulatedAnnealingSearch(EGraph<V, E> graph, V startVertex,Function<V, Double> fitness) {
		super();
		this.graph = graph;
		this.actualVertex = null;
		this.startVertex = startVertex;
		this.fitness = fitness;
	}

	public EGraph<V, E> getGraph() {
		return graph;
	}
	
	private V nextVertex(V vertex) {
		 List<E> edges = this.graph.edgesListOf(this.actualVertex);
		 List<E> edge = Lists2.randomUnitary(edges);
		 return this.graph.getEdgeTarget(edge.get(0));
	}
	
	public static Integer numIntentos = 10;
	public static Integer numPorIntento = 200;
	public static Integer numMismaTemperatura = 10;
	public static double temperaturaInicial = 1000;
	public static double alfa = 0.97;
	public static Predicate<Double> stop = e->false;
	
	public void search() {
		Math2.initRandom();
		for (Integer n = 0; n < numIntentos; n++) {
			this.temperatura = temperaturaInicial;
			this.actualVertex = this.startVertex;
			for (int i = 0; i < numPorIntento; i++) {
				for (int s = 0; s < numMismaTemperatura; s++) {
					V nv = nextVertex(this.actualVertex);
					Double incr = fitness.apply(nv) - fitness.apply(this.actualVertex);
					if (Math2.aceptaBoltzmann(incr,temperatura)) {
						this.actualVertex = nv;
						actualizaMejorValor();
						if(SimulatedAnnealingSearch.stop.test(this.bestWeight)) return;
					}
				}
				this.temperatura = nexTemperatura(i);
			}
		}
	}

	private void actualizaMejorValor() {
		Double w = this.fitness.apply(this.actualVertex);
		if (this.bestWeight == null ||  w < this.bestWeight) {
			this.bestVertex= this.actualVertex;	
			this.bestWeight = w;
		}
	}

	private double nexTemperatura(Integer i) {
		return alfa * temperatura;
		// return temperaturaInicial/Math.log(2+3*i);
	}
	
	
}
