package us.lsi.graphs.alg;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import us.lsi.common.List2;
import us.lsi.flujossecuenciales.Iterators;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.math.Math2;

public class SimulatedAnnealingSearch<V,E> implements GraphAlg<V,E>, Iterator<V>, Iterable<V> {
	

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
		this.n = 0;
		this.i = 0;
		this.s = 0;
		this.temperatura = temperaturaInicial;
		this.actualVertex = this.startVertex;
	}

	public EGraph<V, E> getGraph() {
		return graph;
	}
	
	private V nextVertex(V vertex) {
		 List<E> edges = this.graph.edgesListOf(this.actualVertex);
		 List<E> edge = List2.randomUnitary(edges);
		 return this.graph.getEdgeTarget(edge.get(0));
	}
	
	public static Integer numIntentos = 10;
	public static Integer numPorIntento = 200;
	public static Integer numMismaTemperatura = 10;
	public static double temperaturaInicial = 1000;
	public static double alfa = 0.97;
	public static Predicate<Double> stop = e->false;
	public Integer n;
	public Integer i;
	public Integer s;
	
	

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
	
	@Override
	public Stream<V> stream() {
		return Iterators.asStream(this.iterator());
	}
	
	@Override
	public Iterator<V> iterator() {
		return this;
	}


	@Override
	public boolean hasNext() {
		return this.n < numIntentos || SimulatedAnnealingSearch.stop.test(this.bestWeight);
	}

	@Override
	public V next() {
		this.temperatura = nexTemperatura(i);
		V nv = nextVertex(this.actualVertex);	
		Double incr = fitness.apply(nv) - fitness.apply(this.actualVertex);
		if (Math2.aceptaBoltzmann(incr,temperatura)) {
			this.actualVertex = nv;
			actualizaMejorValor();
		}
		this.s++;
		if(this.s >= numMismaTemperatura) {
			this.s = 0;
			this.i++;
			if(this.i >= numPorIntento) {
				this.i = 0;
				this.n++;
			}
		}
		return this.actualVertex;
	}

	@Override
	public E getEdgeToOrigin(V v) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V startVertex() {
		return this.startVertex;
	}


	@Override
	public GraphAlg<V, E> copy() {
		return new SimulatedAnnealingSearch<>(graph,startVertex,fitness);
	}
	
	
}
