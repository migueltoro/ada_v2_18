package us.lsi.graphs.manual;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;


public class RecorridoEnProfundidadManual<V,E> {
	
	public static <V, E> RecorridoEnProfundidadManual<V, E> of(Graph<V, E> graph, V initial) {
		return new RecorridoEnProfundidadManual<V, E>(graph, initial);
	}

	private Map<V,E> tree;
	private Map<V,Integer> position;
	public Graph<V,E> graph;
	private V initial;
	
	private RecorridoEnProfundidadManual(Graph<V, E> graph, V initial) {
		super();
		this.tree = null;
		this.position = null;
		this.graph = graph;
		this.initial = initial;
	}

	private void depthWalks(){
		tree = new HashMap<>();
		position = new HashMap<>();
		Stack<V> stack = new Stack<>();
		stack.push(initial);
		Integer n = 0;
		tree.put(initial,null);
		while(!stack.isEmpty()) {
			System.out.println("Cola = "+stack.toString());
			V actual = stack.pop();
			position.put(actual,n);
			n++;
			for(V v:Graphs.neighborListOf(graph, actual)) {
				if(!tree.containsKey(v)) {
					stack.add(v);
					tree.put(v,graph.getEdge(actual, v));
				}
			}		
		}
	}
	
	public static <V,E> Integer level(V v, Map<V,E> tree, Graph<V,E> graph) {
		Integer n = 0;
		E e = tree.get(v);
		while(e!=null) {
			v = Graphs.getOppositeVertex(graph, e, v);
			e = tree.get(v);
			n++;
		}
		return n;
	}
	
	public E tree(V v) {
		if(tree == null) depthWalks();
		return tree.get(v);
	}

	public Integer position(V v) {
		if(position == null) depthWalks();
		return position.get(v);
	}

	public Integer level(V v) {
		if(tree == null) depthWalks();
		return level(v,tree,graph);
	}
	
	
}