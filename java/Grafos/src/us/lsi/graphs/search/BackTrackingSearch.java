package us.lsi.graphs.search;

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.common.Preconditions;

public class BackTrackingSearch<V,E,S extends Comparable<S>> implements BTSearch<V, E, S> {
	
	public enum BTType {Min,Max,All}
	
	public Graph<V,E> graph;
	private V startVertex; 
	private V end;
	public Double bestValue;
	private BiFunction<V, V, Double> heuristic;
	private SortedSet<S> solutions;
	private Function<List<E>,S> solution;
	private Comparator<S> comparator;
	private Function<V,V> copy;
	private BTType type;
	
	BackTrackingSearch(Graph<V, E> g, V startVertex, V end, 
			BiFunction<V,V,Double> heuristic,
			Function<List<E>,S> solution,
			Function<V,V> copy,
			BTType type) {
		this.graph = g;
		this.startVertex = startVertex;
		this.end = end;
		this.heuristic = heuristic;
		this.copy = copy;
		this.type = type;
		if(this.type == BTType.Min || this.type == BTType.All) this.comparator = Comparator.<S>naturalOrder();
		if(this.type == BTType.Max) this.comparator = Comparator.<S>naturalOrder().reversed();
		this.solutions = new TreeSet<>(this.comparator);
		this.solution = solution;
		if(this.type == BTType.Max) this.bestValue = -Double.MAX_VALUE;
		if(this.type == BTType.Min) this.bestValue = Double.MAX_VALUE;
	}
	
	private Boolean forget(State<V,E> state, E edge, V v) {
		Boolean r = false;
		Double w2 = this.graph.getEdgeWeight(edge);
		Double w3 = this.heuristic.apply(v,this.end);
		if(this.type == BTType.Max) r = state.accumulateValue+w2+w3 < this.bestValue;
		if(this.type == BTType.Min) r = state.accumulateValue+w2+w3 > this.bestValue;
		return r;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.graphs.search.BTSearch#search()
	 */
	@Override
	public void search() {
		State<V,E> state = State.of(this.graph,this.startVertex);
		search(state);
	}
	
	private void search(State<V, E> state) {
		V actual = this.copy.apply(state.actualVertex);
		if (actual.equals(this.end) && 
				(this.type == BTType.All ||
				this.type == BTType.Max && state.accumulateValue > this.bestValue ||
				this.type == BTType.Min && state.accumulateValue < this.bestValue)) {
			S s = solution.apply(state.getEdges());
			this.solutions.add(s);
			this.bestValue = state.accumulateValue;
		} else {
			for (V v : Graphs.neighborListOf(graph,actual)) {				
				E edge = graph.getEdge(actual, v);
				Preconditions.checkState(edge != null,String.format("No hay arista entre %s y %s",actual,v));
				if (this.forget(state,edge,v)) continue;
				state.forward(edge);
				search(state);
				state.back();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.graphs.search.BTSearch#getSolution()
	 */
	@Override
	public S getSolution(){
		return this.solutions.first();
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.graphs.search.BTSearch#getSolutions()
	 */
	@Override
	public SortedSet<S> getSolutions(){
		return this.solutions;
	}
	
	public static class State<V,E> {
		public Double accumulateValue;
		public V actualVertex;
		public Stack<E> edges;
		public Graph<V,E> graph;
		public static <V,E> State<V,E>of(Graph<V,E> graph, V initial){
			return new State<>(graph,initial);
		}		
		public State(Graph<V,E> graph, V actual) {
			super();
			this.actualVertex = actual;
			this.graph = graph;
			this.accumulateValue = 0.;
			this.edges = new Stack<>();
		}		
		public void forward(E edge) {
			Double w = this.graph.getEdgeWeight(edge);
			this.accumulateValue+= w;
			this.edges.add(edge);
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,this.actualVertex);
		}
		public void back() {
			E edge = this.edges.pop();
			Double w = this.graph.getEdgeWeight(edge);
			this.accumulateValue-= w;
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,this.actualVertex);
		}				
		public List<E> getEdges() {
			return this.edges.stream().collect(Collectors.toList());
		}
		public List<V> getVertices(){
			Integer n = this.edges.size();
			List<V> vertices = this.edges.stream().map(e->graph.getEdgeSource(e)).collect(Collectors.toList());
			vertices.add(graph.getEdgeTarget(this.edges.get(n-1)));
			return vertices;
		}
		@Override
		public String toString() {
			return String.format("%s,%.2f,\n%s",
					this.actualVertex,this.accumulateValue,
					this.edges.stream().map(e->e.toString()).collect(Collectors.joining(",","{","}")));
		}		
	}	
}
