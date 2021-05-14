package us.lsi.graphs.alg;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class BackTracking<V,E,S extends Comparable<S>> implements BT<V, E, S> {
	
	public enum BTType {Min,Max,All,One}
	
	public EGraph<V,E> graph;
	protected V startVertex; 
	protected Predicate<V> goal;
	protected V end;
	protected Predicate<V> constraint;
	public Double bestValue;
	public GraphPath<V,E> optimalPath;
	protected TriFunction<V, Predicate<V>, V, Double> heuristic;
	public Set<S> solutions;
	protected Function<GraphPath<V,E>,S> solution;
	protected Function<V,V> copy;
	protected BTType type;
	public Graph<V,E> outGraph;
	public Boolean withGraph = false;
	public Function<E,Object> action;
	
	BackTracking(EGraph<V, E> graph, 
			Predicate<V> goal,
			V end,
			Predicate<V> constraint,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V,E>,S> solution,
			Function<V,V> copy,
			BTType type) {
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.goal = goal;
		this.end = end;
		this.constraint = constraint;
		this.heuristic = heuristic;
		this.copy = copy;
		this.type = type;
		this.solutions = new HashSet<>();
		this.solution = solution;
		if(this.type == BTType.Max) this.bestValue = -Double.MAX_VALUE;
		if(this.type == BTType.Min) this.bestValue = Double.MAX_VALUE;
	}
	
	protected Boolean forget(State<V,E> state, E edge) {
		Boolean r = false;
		Double w = state.getPath().boundWeight(state.getAccumulateValue(),state.getActualVertex(),edge,goal,end, heuristic);
		if(this.bestValue != null && this.type == BTType.Max) r = w < this.bestValue;
		if(this.bestValue != null && this.type == BTType.Min) r = w > this.bestValue;
		return r;
	}
	
	protected void update(State<V, E> state) {
		if(this.type == BTType.All ||
		   (this.bestValue == null)||
		   (this.type == BTType.Max && state.getAccumulateValue() > this.bestValue) ||
		   (this.type == BTType.Min && state.getAccumulateValue() < this.bestValue)) {
				this.bestValue = state.getAccumulateValue();
				if (this.constraint.test(state.getActualVertex())) {
					this.optimalPath = state.getPath();
					S s = solution.apply(this.optimalPath);
					this.solutions.add(s);
				}
		}
	}
	
	
	@Override
	public void search() {	
		if(this.withGraph) outGraph = new SimpleDirectedWeightedGraph<>(null,null);
		State<V,E> initialState = StatePath.of(graph,this.goal,this.end);
		search(initialState);
	}
	
	public void search(State<V, E> state) {
		V actual = this.copy.apply(state.getActualVertex());
		if(this.withGraph) outGraph.addVertex(actual);
		if (goal.test(actual)) {
//			System.out.println(state);
			update(state);
		}
		else {
			for (E edge : graph.edgesListOf(actual)) {	
//				System.out.println(state);
				if (this.forget(state,edge)) continue;
				state.forward(edge);
				search(state);
				if(this.withGraph) outGraph.addEdge(actual,Graphs.getOppositeVertex(graph, edge,actual), edge);
				state.back(edge);
			}
		}
	}
	
	@Override
	public Optional<S> getSolution(){
		return switch(this.type) {
		case All -> this.solutions.stream().findAny();
		case Max -> this.solutions.stream().max(Comparator.naturalOrder());
		case Min -> this.solutions.stream().min(Comparator.naturalOrder());
		case One -> this.solutions.stream().findAny();
		};
	}
	
	@Override
	public Set<S> getSolutions(){
		return this.solutions;
	}
	
	public String toStringSolutions() {
		return this.solutions.stream().sorted().map(e->e.toString()).collect(Collectors.joining("\n"));
	}
	
	public V getStartVertex() {
		return startVertex;
	}

	public interface State<V, E> {
		void forward(E edge);
		void back(E edge);
		Double getAccumulateValue();
		EGraphPath<V, E> getPath();
		V getActualVertex();
	}
	
	
	public static class StatePath<V,E> implements State<V, E> {
		private V actualVertex;
		private EGraphPath<V, E> path;
		private EGraph<V,E> graph;
		private Map<V,E> edgeToOrigin;
		private Double accumulateValue;
		
		public static <V,E> State<V, E> of(EGraph<V,E> graph, Predicate<V> goal, V end){
			return new StatePath<>(graph,goal,end);
		}		
		
		public StatePath(EGraph<V,E> graph, Predicate<V> goal, V end) {
			super();
			this.actualVertex = graph.startVertex();
			this.graph = graph;
			this.path = graph.initialPath();
			this.edgeToOrigin = new HashMap<>();
			this.accumulateValue = this.path.getWeight();
		}		
	
		@Override
		public void forward(E edge) {
//			System.out.println(this.edgeToOrigin.size());
			E lastEdge = this.edgeToOrigin.get(this.actualVertex);
			this.accumulateValue = this.path.add(this.accumulateValue,this.actualVertex,edge,lastEdge);
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,this.actualVertex);
			this.edgeToOrigin.put(this.actualVertex,edge);		
		}
		
		@Override
		public void back(E edge) {
//			System.out.println(this.actualVertex);
//			System.out.println(this.edgeToOrigin.size());
			E lastEdge = this.edgeToOrigin.get(this.actualVertex);
			this.accumulateValue = this.path.removeLast(this.accumulateValue,this.actualVertex,edge,lastEdge);
			this.edgeToOrigin.remove(this.actualVertex); // repasar
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,this.actualVertex);			
//			System.out.println(this.edgeToOrigin.size());
		}
		
		@Override
		public Double getAccumulateValue() {
			return this.accumulateValue;
		}	
		
		private E getEdgeToOrigin(V vertex) {
			return this.edgeToOrigin.get(vertex);
		}
		
		public Map<V,E> edgeToOrigin() {
			return this.edgeToOrigin;
		}
		
		@Override
		public EGraphPath<V, E> getPath() {				
			V end = this.actualVertex;
			EGraphPath<V,E> ePath = graph.initialPath();
			V startVertex = graph.startVertex();
			if(end.equals(startVertex)) return ePath;
			E edge = this.getEdgeToOrigin(end);
			List<E> edges = new ArrayList<>();		
			while(edge!=null) {				
				edges.add(edge);
				end = Graphs.getOppositeVertex(graph, edge, end);
				edge = this.getEdgeToOrigin(end);			
			}
			Collections.reverse(edges);
			for(E e:edges) {
				ePath.add(e);
			}
			return ePath;
		}
					
		@Override
		public V getActualVertex() {
			return actualVertex;
		}
		
		@Override
		public String toString() {
			return String.format("%s,\n%.2f,\n%s",
					this.actualVertex,this.getAccumulateValue(),
					this.getPath().getEdgeList().stream().map(e->e.toString()).collect(Collectors.joining(",","{","}")));
		}	
	}	
}
