package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.flujossecuenciales.Iterators;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TopologicalSearch<V, E> implements GraphAlg<V,E>, Iterator<V>, Iterable<V> {
	
	protected Map<V,E> edgeToOrigin;
	public Graph<V,E> graph;
	protected Stack<V> stackPre;
	protected List<V> stackPost;
	protected V startVertex; 
	protected Integer n;
	protected Integer i;


	TopologicalSearch(Graph<V, E> g, V startVertex) {
		this.graph = g;
		this.startVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(startVertex, null);
		this.stackPre = new Stack<>();
		this.stackPre.add(startVertex);
		this.stackPost = new ArrayList<>();
		this.preorder();
		this.n = this.stackPost.size();
		this.i = 0;
	}
	
	@Override
	public Stream<V> stream() {
		return Iterators.asStream(this.iterator());
	}
	
	@Override
	public DephtSearch<V, E> copy() {
		return GraphAlg.depth(this.graph, this.startVertex);
	}
	
	public Iterator<V> iterator() {
		return this;
	}
	
	public boolean isSeenVertex(V v) {
		return this.edgeToOrigin.containsKey(v);
	}
	
	public boolean hasNextP() {
		return !stackPre.isEmpty();
	}

	public V nextP() {
		V actual = stackPre.pop();
		this.stackPost.add(actual);
		for(V v:Graphs.neighborListOf(graph, actual)) {
			if(!this.edgeToOrigin.containsKey(v)) {
				stackPre.add(v);
				this.edgeToOrigin.put(v,graph.getEdge(actual, v));			
			}
		}
		return actual;
	}
	
	private void preorder() {
		while(this.hasNextP()) {
			this.nextP();
		}
	}
	
	@Override
	public boolean hasNext() {
		return this.i < this.n;
	}

	@Override
	public V next() {
		Integer old = this.i;
		this.i ++;
		return this.stackPost.get(old);
	}	

	public E getEdgeToOrigin(V v) {
		return this.edgeToOrigin.get(v);
	}

	@Override
	public EGraph<V, E> getGraph() {
		return Graphs2.eGraph(this.graph,startVertex(),PathType.Sum);
	}
	
	@Override
	public V startVertex() {
		return this.startVertex;
	}

	

}
