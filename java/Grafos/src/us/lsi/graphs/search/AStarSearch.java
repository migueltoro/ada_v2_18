package us.lsi.graphs.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import us.lsi.graphs.manual.Data;

public class AStarSearch<V, E> implements GSearch<V,E> {

	public Graph<V,E> graph; 
	private V initial;
	private V end;
	private BiFunction<V,V,Double> heuristic;
	private Map<V,FibonacciHeapNode<Data<V,E>>> tree;
	private FibonacciHeap<Data<V,E>> heap = new FibonacciHeap<>();
	private FibonacciHeapNode<Data<V,E>> data = Data.node(initial,null);
	
	AStarSearch(Graph<V, E> graph, V initial, V end, BiFunction<V, V, Double> heuristic) {
		super();
		this.graph = graph;
		this.initial = initial;
		this.end = end;
		this.heuristic = heuristic;
		this.tree = new HashMap<>();
		heap = new FibonacciHeap<>();
		data = Data.node(initial,null);
		this.heap.insert(data,toEnd(initial));
		this.tree.put(initial,data);
	}

	@Override
	public Iterator<V> iterator() {
		return this;
	}
	
	private Double toEnd(V actual) {
		Double r = 0.;
		if(heuristic!=null) r = heuristic.apply(actual, end);
		return r;
	}
	
	@Override
	public boolean isSeenVertex(V v) {
		return this.tree.containsKey(v);
	}
	
	public boolean hasNext() {
		return !heap.isEmpty();
	}

	@Override
	public V next() {
		FibonacciHeapNode<Data<V,E>> dataActual = heap.removeMin();
		V vertexActual = dataActual.getData().vertex;
		for(V v:Graphs.neighborListOf(graph, vertexActual)) {
			E backEdge = graph.getEdge(vertexActual, v);
			Double weightEdge = graph.getEdgeWeight(backEdge);
			Double newDistance = dataActual.getData().distance+weightEdge;
			Double toEnd = toEnd(vertexActual);
			if(!tree.containsKey(v)) {				
				Data<V,E> nd = Data.of(v,backEdge,newDistance);
				FibonacciHeapNode<Data<V, E>> nf = Data.node(nd);
				heap.insert(nf,newDistance+toEnd);
				tree.put(v,nf);
			} else if(!tree.get(v).getData().closed){
				Double oldDistance = tree.get(v).getData().distance;
				if(newDistance < oldDistance) {
					tree.get(v).getData().distance = newDistance;
					tree.get(v).getData().edge = backEdge;
					heap.decreaseKey(tree.get(v), newDistance+toEnd);
				}				
			}
		}
		tree.get(vertexActual).getData().closed = true;	
		return vertexActual;
	}

	@Override
	public E getEdgeToOrigin(V v) {
		return tree.get(v).getData().edge;
	}

	@Override
	public Graph<V, E> getGraph() {
		return this.graph;
	}

	@Override
	public V initialVertex() {
		return this.initial;
	}	

}
