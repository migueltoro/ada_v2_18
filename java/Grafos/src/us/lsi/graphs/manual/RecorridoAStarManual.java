package us.lsi.graphs.manual;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;
import us.lsi.common.Lists2;


public class RecorridoAStarManual<V,E> {
	
	public static <V, E> RecorridoAStarManual<V, E> of(Graph<V, E> graph, V initial, V end, BiFunction<V,V,Double> heuristic) {
		return new RecorridoAStarManual<V, E>(graph, initial, end, heuristic);
	}

	private Map<V,FibonacciHeapNode<Data<V,E>>> tree;
	private Map<V,Integer> position;
	private Graph<V,E> graph;
	private V initial;
	private V end;
	private BiFunction<V,V,Double> heuristic;
	
	private RecorridoAStarManual(Graph<V, E> graph, V initial, V end, BiFunction<V,V,Double> heuristic) {
		super();
		this.tree = null;
		this.position = null;
		this.graph = graph;
		this.initial = initial;
		this.end = end;
		this.heuristic = heuristic;
	}

	private Double toEnd(V actual) {
		Double r = 0.;
		if(heuristic!=null) r = heuristic.apply(actual, end);
		return r;
	}
	
	private void aStarWalks(){
		tree = new HashMap<>();
		position = new HashMap<>();
		FibonacciHeap<Data<V,E>> heap = new FibonacciHeap<>();
		FibonacciHeapNode<Data<V,E>> data = Data.node(initial,null);
		heap.insert(data,toEnd(initial));
		Integer n = 0;
		tree.put(initial,data);
		while(!heap.isEmpty()) {
			FibonacciHeapNode<Data<V,E>> dataActual = heap.removeMin();
			V vertexActual = dataActual.getData().vertex;
			position.put(vertexActual,n);
			n++;
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
			if(vertexActual.equals(end)) break;
		}
	}
	
	public E tree(V v) {
		if(tree == null) aStarWalks();
		return tree.get(v).getData().edge;
	}

	public Integer position(V v) {
		if(position == null) aStarWalks();
		if(position.get(v)==null) return -1;
		return position.get(v);
	}

	public Double distanceToOrigin(V v) {
		if(tree == null) aStarWalks();
		if(tree.get(v)==null) return -1.;
		return tree.get(v).getData().distance;
	}
	
	public GraphPath<V,E> minPathToOrigin(V v) {
		if(tree == null) aStarWalks();
		List<V> vertexList = Lists2.newList(v);
		FibonacciHeapNode<Data<V,E>> data = tree.get(v);
		while(data.getData().edge != null) {
			v = Graphs.getOppositeVertex(graph,data.getData().edge, v);
			vertexList.add(0,v);
			data = tree.get(v);
		}
		GraphPath<V,E> path = new GraphWalk<>(graph,vertexList,distanceToOrigin(v));
		return path;
	}
	
	
	
	static class Data<V,E> {
		V vertex;
		E edge;
		Double distance;
		Boolean closed;
		
		public static <V,E> FibonacciHeapNode<Data<V,E>> node(V vertex, E edge) {
			return new FibonacciHeapNode<>(new Data<>(vertex,edge,0.));
		}
		public static <V,E> FibonacciHeapNode<Data<V,E>> node(V vertex, E edge, Double distance) {
			return new FibonacciHeapNode<>(new Data<>(vertex,edge,distance));
		}
		public static <V,E> FibonacciHeapNode<Data<V,E>> node(Data<V,E> data) {
			return new FibonacciHeapNode<>(data);
		}
		public static <V,E> Data<V,E> of(V vertex, E edge, Double distance) {
			return new Data<>(vertex,edge, distance);
		}
		public Data(V vertex, E edge, Double distance) {
			super();
			this.vertex = vertex;
			this.edge = edge;
			this.distance = distance;
			this.closed = false;
		}
		
	}

}


