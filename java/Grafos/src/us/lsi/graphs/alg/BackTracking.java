package us.lsi.graphs.alg;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;

import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class BackTracking<V,E,S> implements BT<V, E, S> {
	
	public enum BTType {Min,Max,All,One}
	
	public EGraph<V,E> graph;
	protected V startVertex; 
	protected Predicate<V> goal;
	protected V end;
	public Double bestValue;
	protected TriFunction<V, Predicate<V>, V, Double> heuristic;
	public Set<S> solutions;
	protected Function<GraphPath<V,E>,S> solution;
	protected Function<V,V> copy;
	protected BTType type;
	
	BackTracking(EGraph<V, E> graph, 
			Predicate<V> goal,
			V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V,E>,S> solution,
			Function<V,V> copy,
			BTType type) {
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.goal = goal;
		this.end = end;
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
		Double w = state.getPath().boundWeight(goal,end, edge, heuristic);
		if(this.type == BTType.Max) r = w <= this.bestValue;
		if(this.type == BTType.Min) r = w >= this.bestValue;
		return r;
	}
	
	protected void update(State<V, E> state) {
		if(this.type == BTType.All ||
		   (this.type == BTType.Max && state.getAccumulateValue() >= this.bestValue) ||
		   (this.type == BTType.Min && state.getAccumulateValue() <= this.bestValue)) {
				S s = solution.apply(state.getPath());
				this.solutions.add(s);
				this.bestValue = state.getAccumulateValue();
				System.out.println("Update "+this.bestValue);
		}
	}
	
	@Override
	public void search() {	
		State<V,E> initialState = StatePath.of(graph,this.goal,this.end);
		search(initialState);
	}
	
	public void search(State<V, E> state) {
		V actual = this.copy.apply(state.getActualVertex());
		if (goal.test(actual))	update(state);
		else {
			for (E edge : graph.edgesListOf(actual)) {				
				if (this.forget(state,edge)) continue;
				state.forward(edge);
				search(state);
				state.back(edge);
			}
		}
	}
	
	@Override
	public S getSolution(){
		return this.solutions.stream().findAny().get();
	}
	
	@Override
	public Set<S> getSolutions(){
		return this.solutions;
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
		private Map<V,E> edges;
		private Double accumulateValue;
		
		public static <V,E> State<V, E> of(EGraph<V,E> graph, Predicate<V> goal, V end){
			return new StatePath<>(graph,goal,end);
		}		
		
		public StatePath(EGraph<V,E> graph, Predicate<V> goal, V end) {
			super();
			this.actualVertex = graph.startVertex();
			this.graph = graph;
			this.path = graph.initialPath();
			this.edges = new HashMap<>();
			this.accumulateValue = this.path.getWeight();
		}		
	
		@Override
		public void forward(E edge) {
			this.edges.put(this.actualVertex,edge);
			this.accumulateValue = this.path.add(this.accumulateValue,edge);
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,this.actualVertex);
		}
		
		@Override
		public void back(E edge) {
			V source = graph.getEdgeSource(edge);
			E e2 = this.edges.get(source);
			this.accumulateValue = this.path.removeLast(this.accumulateValue,edge,e2);
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,this.actualVertex);
		}
		
		@Override
		public Double getAccumulateValue() {
			return this.accumulateValue;
		}	
		
		private E getEdgeToGoal(V vertex) {
			return this.edges.get(vertex);
		}
		
		@Override
		public EGraphPath<V, E> getPath() {			
			return GraphAlg.pathForwardEdged(graph,graph.startVertex(),v->getEdgeToGoal(v));
		}
					
		@Override
		public V getActualVertex() {
			return actualVertex;
		}
		
		@Override
		public String toString() {
			return String.format("%s,%.2f,\n%s",
					this.actualVertex,this.getAccumulateValue(),
					this.getPath().getEdgeList().stream().map(e->e.toString()).collect(Collectors.joining(",","{","}")));
		}		
	}	
}
