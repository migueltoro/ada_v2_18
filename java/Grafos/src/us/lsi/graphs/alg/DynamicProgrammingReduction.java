package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.TriFunction;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;


public class DynamicProgrammingReduction<V, E, S> implements DPR<V, E, S> {
	
	public EGraph<V,E> graph;
	private V startVertex; 
	private Predicate<V> goal;
	private V end;
	public Double bestValue;
	private TriFunction<V, Predicate<V>, V, Double> heuristic;
	private Comparator<Sp<E>> comparatorEdges;
	private Function<GraphPath<V,E>, S> solution;
	public Map<V,Sp<E>> solutionsTree;
	private PDType type;
	private EGraphPath<V,E> path;
	
	DynamicProgrammingReduction(EGraph<V, E> g, 
			Predicate<V> goal, V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<GraphPath<V,E>, S> solution,
			PDType type) {
		this.graph = g;
		this.startVertex = graph.startVertex();
		this.goal = goal;
		this.end = end;
		this.heuristic = heuristic;
		this.solution = solution;
		this.type = type;
		if(this.type == PDType.Min) this.comparatorEdges = Comparator.naturalOrder();
		if(this.type == PDType.Max) this.comparatorEdges = Comparator.<Sp<E>>naturalOrder().reversed();
		this.solutionsTree = new HashMap<>();
		if(this.type == PDType.Max) this.bestValue = -Double.MAX_VALUE;
		if(this.type == PDType.Min) this.bestValue = Double.MAX_VALUE;
		this.path = graph.initialPath();
	}
	
	private Boolean forget(Double accumulateValue, Predicate<V> goal, V end, E edge) {
		Boolean r = false;
		Double w = this.path.boundWeight(accumulateValue,goal,end,edge,this.heuristic);
		if(this.type == PDType.Max) r = w < this.bestValue;
		if(this.type == PDType.Min) r = w > this.bestValue;
		return  r;
	}
	
	public Sp<E> search() {
		this.solutionsTree = new HashMap<>();
		return search(this.startVertex,0.);
	}
	
	private Sp<E> search(V actual, Double accumulateValue) {
		Sp<E> r = null;
		if(this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (this.goal.test(actual)) {
			r = Sp.empty();
			this.solutionsTree.put(actual, r);
			return r;
		} else {
			List<Sp<E>> rs = new ArrayList<>();
			for (E edge : graph.edgesListOf(actual)) {				
				V v = graph.getEdgeTarget(edge);
				if (this.forget(accumulateValue,this.goal,this.end,edge)) continue;
				Sp<E> s = search(v,this.path.add(accumulateValue, edge));
				if (s!=null) {					
					Sp<E> sp = Sp.of(edge,this.path.add(s.weight, edge));
					rs.add(sp);
				}
			}
			r = rs.stream().filter(s->s!=null).min(this.comparatorEdges).orElse(null);
			this.solutionsTree.put(actual, r);
		}
		return r;
	}
	
	private E getEdgeToGoal(V vertex) {
		return this.solutionsTree.get(vertex).edge;
	}
	
	@Override
	public S getSolution(EGraphPath<V, E> path){
		return this.solution.apply(path);
	}

	@Override
	public EGraphPath<V, E> pathFrom(V vertex) {
		return GraphAlg.pathForwardEdged(graph,vertex,v->getEdgeToGoal(v));
	}
}
