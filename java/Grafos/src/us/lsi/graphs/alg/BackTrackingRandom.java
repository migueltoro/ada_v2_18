package us.lsi.graphs.alg;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jgrapht.GraphPath;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.math.Math2;

public class BackTrackingRandom<V,E,S extends Comparable<S>> extends BackTracking<V,E,S> {
	
	public static Integer threshold;
	public static Integer solutionsNumber;
	private static Boolean work = true;

	BackTrackingRandom(EGraph<V, E> graph, Predicate<V> goal, V end, Predicate<V> constraint,
			Function<GraphPath<V, E>, S> solution,
			Function<V, V> copy,
			BTType type,
			Function<V,Integer> size) {
		super(graph, goal, end, constraint,null, solution, copy, type);
		this.size = size;
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
		State<V,E> initialState = StatePath.of(super.graph,super.goal,super.end);
		this.iterations = 0;
		Math2.initRandom();
		while (BackTrackingRandom.work) {
			this.iterations++;
			search(initialState);
		}
	}
	
	@Override
	public void search(State<V, E> state) {
		V actual = this.copy.apply(state.getActualVertex());
		if (goal.test(actual)) update(state);		
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
