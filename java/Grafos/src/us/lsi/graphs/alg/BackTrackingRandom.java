package us.lsi.graphs.alg;

import java.util.List;
import java.util.function.Function;


import org.jgrapht.GraphPath;

import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.math.Math2;

public class BackTrackingRandom<V,E,S extends Comparable<S>> extends BackTracking<V,E,S> {
	
	public static <V, E, S extends Comparable<S>> BackTrackingRandom<V, E, S> of(
			EGraph<V, E> graph, 
			Function<GraphPath<V, E>, S> solution, 
			BTType type, 
			Function<V, Integer> size) {
		return new BackTrackingRandom<V, E, S>(graph,solution,type, size);
	}
	
	public static Integer threshold;
	public static Integer solutionsNumber;
	private static Boolean work = true;

	BackTrackingRandom(EGraph<V, E> graph, 
			Function<GraphPath<V, E>, S> solution,
			BTType type,
			Function<V,Integer> size) {
		super(graph,null, solution, type);
		this.size = size;
		Preconditions.checkNotNull(graph.goal(),"El predicado no puede ser null");
	}
		
	protected Function<V,Integer> size;
	public Integer iterations;
	
	@Override
	protected void update(State<V, E> state) {
				S s = solution.apply(state.getPath());
				if(s!=null) this.solutions.add(s);
				BackTrackingRandom.work = super.solutions.size() < BackTrackingRandom.solutionsNumber;
	}
	
	@Override
	public void search() {
		State<V,E> initialState = StatePath.of(graph,graph.goal(),graph.endVertex());
		this.iterations = 0;
		Math2.initRandom();
		while (BackTrackingRandom.work) {
			this.iterations++;
			search(initialState);
		}
	}
	
	@Override
	public void search(State<V, E> state) {
		V actual = state.getActualVertex();
		if (graph.goal().test(actual)) update(state);		
		else {
			List<E> edges = graph.edgesListOf(actual);
			if(size.apply(actual) > BackTrackingRandom.threshold) edges = List2.randomUnitary(edges);
			for (E edge : edges) {				
				state.forward(edge);
				search(state);
				if(!BackTrackingRandom.work) return;
				state.back(edge);
			}
		}
	}

	

}
