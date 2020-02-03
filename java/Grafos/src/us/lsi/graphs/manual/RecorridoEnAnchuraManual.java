package us.lsi.graphs.manual;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

public class RecorridoEnAnchuraManual<V,E> {
	
	public static <V, E> RecorridoEnAnchuraManual<V, E> of(Graph<V, E> graph, V initial) {
		return new RecorridoEnAnchuraManual<V, E>(graph, initial);
	}

	private Map<V,E> tree;
	private Map<V,Integer> position;
	public Graph<V,E> graph;
	private V initial;
	
	private RecorridoEnAnchuraManual(Graph<V, E> graph, V initial) {
		super();
		this.tree = null;
		this.position = null;
		this.graph = graph;
		this.initial = initial;
	}

	private void breadthWalks(){
		tree = new HashMap<>();
		position = new HashMap<>();
		Queue<V> queue = new LinkedList<>();
		queue.add(initial);
		Integer n = 0;
		tree.put(initial,null);
		while(!queue.isEmpty()) {
			System.out.println("Cola = "+queue.toString());
			V actual = queue.remove();
			position.put(actual,n);
			n++;
			for(V v:Graphs.neighborListOf(graph, actual)) {
				if(!tree.containsKey(v)) {
					queue.add(v);
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
		if(tree == null) breadthWalks();
		return tree.get(v);
	}

	public Integer position(V v) {
		if(position == null) breadthWalks();
		return position.get(v);
	}

	public Integer level(V v) {
		if(tree == null) breadthWalks();
		return level(v,tree,graph);
	}
	

}
