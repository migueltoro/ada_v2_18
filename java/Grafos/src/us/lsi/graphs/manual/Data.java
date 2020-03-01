package us.lsi.graphs.manual;

import org.jgrapht.util.FibonacciHeapNode;

public class Data<V,E> {
	public V vertex;
	public E edge;
	public Double distance;
	public Boolean closed;
	
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
