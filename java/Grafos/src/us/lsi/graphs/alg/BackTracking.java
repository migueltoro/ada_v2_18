package us.lsi.graphs.alg;


import java.util.ArrayList;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.common.TriFunction;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class BackTracking<V,E,S> {

	public static <V, E, S> BackTracking<V, E, S> of(
			EGraph<V, E> graph,
			TriFunction<V, Predicate<V>, V, Double> heuristic, 
			Function<GraphPath<V, E>, S> solution,
			BTType type) {
		return new BackTracking<V, E, S>(graph, heuristic, solution, type);
	}
	
	public enum BTType {Min,Max,All,One}
	private Comparator<Double> comparator = Comparator.naturalOrder();
	
	public BTType type = BTType.Min;
	public EGraph<V,E> graph;
	public Double bestValue;
	public GraphPath<V,E> optimalPath;
	protected TriFunction<V, Predicate<V>, V, Double> heuristic;
	public Set<S> solutions;
	protected Function<GraphPath<V,E>,S> solution;
	private SimpleDirectedGraph<V,E> outGraph;
	public Boolean withGraph = false;
	
	BackTracking(EGraph<V, E> graph, TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V, E>, S> solution, BTType type) {
		this.type = type;
		this.graph = graph;
		this.heuristic = heuristic;
		this.solutions = new HashSet<>();
		this.solution = solution;
		this.comparator = switch(this.type) {
		case All -> null;
		case Max -> Comparator.reverseOrder();
		case Min -> Comparator.naturalOrder();
		case One -> null;
		};
		Preconditions.checkNotNull(graph.goal(), "El predicado no puede ser null");
	}
	
	protected Boolean forget(State<V,E> state, E edge) {
		Boolean r = false;
		Double w = state.getPath().boundedValue(state.getAccumulateValue(),state.getActualVertex(),
				edge,graph.goal(),graph.endVertex(), heuristic);
		if(this.bestValue != null) r = comparator.compare(w,this.bestValue) >= 0;
		return r;
	}
	
	protected void update(State<V, E> state) {
		if (graph.constraint().test(state.getActualVertex())) {
			if (this.type == BTType.All || this.type == BTType.One) {
				S s = solution.apply(state.getPath());
				this.solutions.add(s);
			} else if (this.type == BTType.Min || this.type == BTType.Max) {
				if (this.bestValue == null || this.comparator.compare(state.getAccumulateValue(),this.bestValue) < 0) {
					this.bestValue = state.getAccumulateValue();
					this.optimalPath = state.getPath();
				}
			}
		}
	}
	
	private void initialGraph() {
		if (this.withGraph) this.outGraph = Graphs2.simpleDirectedGraph();
	}
	
	private void addGraph(V v, E edge) {
		if(withGraph) {
			V v2 = Graphs.getOppositeVertex(graph,edge,v);
			if(!this.outGraph.containsVertex(v)) this.outGraph.addVertex(v);
			if(!this.outGraph.containsVertex(v2)) this.outGraph.addVertex(v2);
			if(!this.outGraph.containsEdge(edge)) this.outGraph.addEdge(v, v2, edge);
		}
	}
	
	public SimpleDirectedGraph<V,E> graph() {
		return this.outGraph;
	}
	
	public void search() {	
		initialGraph();
		State<V,E> initialState = StatePath.of(graph,graph.goal(),graph.endVertex());
		search(initialState);
	}
	
	public void search(State<V, E> state) {
		V actual = state.getActualVertex();
		if (graph.goal().test(actual)) {
			this.update(state);
		} else {
			for (E edge : graph.edgesListOf(actual)) {	
				if (this.forget(state,edge)) continue;
				state.forward(edge);
				search(state);
				addGraph(actual,edge);
				state.back(edge);
			}
		}
	}
	
	public Optional<S> getSolution(){
		return switch(this.type) {
		case All -> this.solutions.stream().findAny();
		case Max -> this.optimalPath().map(x->this.solution.apply(x));
		case Min -> this.optimalPath().map(x->this.solution.apply(x));
		case One -> this.solutions.stream().findAny();
		};
	}

	public Set<S> getSolutions(){
		return this.solutions;
	}
	
	public Optional<GraphPath<V, E>> optimalPath(){
		return Optional.ofNullable(this.optimalPath);		
	}
	
	public String toStringSolutions() {
		return this.solutions.stream().sorted().map(e->e.toString()).collect(Collectors.joining("\n"));
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
		private List<E> edges;
		private List<Double> weights;
		private Double accumulateValue;
		
		public static <V,E> State<V, E> of(EGraph<V,E> graph, Predicate<V> goal, V end){
			return new StatePath<>(graph,goal,end);
		}		
		
		public StatePath(EGraph<V,E> graph, Predicate<V> goal, V end) {
			super();
			this.actualVertex = graph.startVertex();
			this.graph = graph;
			this.path = graph.initialPath();
			this.edges = new ArrayList<>();
			this.weights = new ArrayList<>();
			this.accumulateValue = this.path.getWeight();
		}		
	
		@Override
		public void forward(E edge) {
			E lastEdge = edges.isEmpty()?null:List2.last(edges);
			this.accumulateValue = this.path.add(this.accumulateValue,this.actualVertex,edge,lastEdge);
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,this.actualVertex);
			this.edges.add(edge);
			this.weights.add(this.accumulateValue);
		}
		
		@Override
		public void back(E edge) {
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,this.actualVertex);	
			this.edges.remove(this.edges.size()-1);
			this.weights.remove(this.weights.size()-1);
			this.accumulateValue = !this.weights.isEmpty()? List2.last(this.weights): graph.initialPath().getWeight();
		}
		
		@Override
		public Double getAccumulateValue() {
			return this.accumulateValue;
		}	
		
		@Override
		public EGraphPath<V, E> getPath() {				
			EGraphPath<V,E> ePath = graph.initialPath();
			for(E e:this.edges) {
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
