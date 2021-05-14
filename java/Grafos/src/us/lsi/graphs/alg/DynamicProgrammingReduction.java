package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import us.lsi.common.TriFunction;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

import java.util.Optional;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;



public class DynamicProgrammingReduction<V, E> implements DPR<V, E> {

	public EGraph<V, E> graph;
	private V startVertex;
	private Predicate<V> goal;
	private V end;
	private Predicate<V> constraint;
	public Double bestValue = null;
	public GraphPath<V, E> solutionPath = null;
	private TriFunction<V, Predicate<V>, V, Double> heuristic;
	private Comparator<Sp<E>> comparatorEdges;
	public Map<V, Sp<E>> solutionsTree;
	public Map<V, E> edgeToOrigen;
	private PDType type;
	private EGraphPath<V, E> path;
	public Graph<V,E> outGraph;
	public Optional<GraphPath<V,E>> optPath;
	public Boolean withGraph = false;
	public Function<E,Object> action;

	DynamicProgrammingReduction(EGraph<V, E> g, Predicate<V> goal, V end, Predicate<V> constraint,
			TriFunction<V, Predicate<V>, V, Double> heuristic, PDType type) {
		this.graph = g;
		this.startVertex = graph.startVertex();
		this.goal = goal == null ? v -> v.equals(v) : goal;
		this.end = end;
		this.constraint = constraint;
		this.heuristic = heuristic;
		this.type = type;
		if (this.type == PDType.Min) this.comparatorEdges = Comparator.naturalOrder();
		if (this.type == PDType.Max) this.comparatorEdges = Comparator.<Sp<E>>naturalOrder().reversed();
		this.solutionsTree = new HashMap<>();
		if (this.type == PDType.Max) this.bestValue = -Double.MAX_VALUE;
		if (this.type == PDType.Min) this.bestValue = Double.MAX_VALUE;
		this.path = graph.initialPath();
	}

	private Boolean forget(E edge, V actual,Double accumulateValue,Predicate<V> goal,V end) {
		Boolean r = false;
		Double w = this.path.boundWeight(accumulateValue, actual, edge, goal, end, this.heuristic);
		if (this.type == PDType.Max) r = w < this.bestValue;
		if (this.type == PDType.Min) r = w > this.bestValue;
		return r;
	}
	
	protected void update(Double accumulateValue) {
		if(this.bestValue == null ||
		   (this.type == PDType.Max && accumulateValue > this.bestValue) ||
		   (this.type == PDType.Min && accumulateValue < this.bestValue)) {
				this.bestValue = accumulateValue;
		}
	}


	@Override
	public Optional<GraphPath<V, E>> search() {
		return search(this.startVertex);
	}

	@Override
	public Optional<GraphPath<V, E>> search(V vertex) {
		if(this.withGraph) outGraph = new SimpleDirectedWeightedGraph<>(null,null);
		if (!this.solutionsTree.containsKey(vertex)) {
			this.solutionsTree = new HashMap<>();
			search(vertex,0., null);
		}
		GraphPath<V, E> gp = pathFrom(this.startVertex);
		if(gp != null) this.optPath = Optional.of(gp);
		else if(this.optPath == null) this.optPath = Optional.empty();
		return this.optPath;
	}
	
	private Sp<E> search(V actual, Double accumulateValue, E edgeToOrigin) {
//		System.out.printf("En search = %.1f,%s\n",accumulateValue,actual);
		Sp<E> r = null;
		if(this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (this.goal.test(actual)) {
			if(this.withGraph) outGraph.addVertex(actual);
			update(accumulateValue);
			if (this.constraint.test(actual)) {
				r = Sp.of(this.path.goalBaseSolution(actual), null);
			} else {
				r = null;
			}
			this.solutionsTree.put(actual, r);
			return r;
		} else {
			if(this.withGraph) outGraph.addVertex(actual);
			List<Sp<E>> rs = new ArrayList<>();			
			for (E edge : graph.edgesListOf(actual)) {					
				if (this.forget(edge,actual,accumulateValue,this.goal,this.end)) continue;
				V v = Graphs.getOppositeVertex(graph,edge,actual);
				Double ac = this.path.add(accumulateValue,actual,edge,edgeToOrigin); 
				Sp<E> s = search(v,ac,edge);
				if (s!=null) {
					E lastEdge = this.solutionsTree.get(v).edge;
					Double spv = this.path.fromNeighbordSolution(s.weight,v,edge,lastEdge);	
					Sp<E> sp = Sp.of(spv,edge);
					rs.add(sp);
				}
				if(this.withGraph) outGraph.addEdge(actual,Graphs.getOppositeVertex(graph, edge,actual), edge);
			}
			r = rs.stream().filter(s->s!=null).min(this.comparatorEdges).orElse(null);
			this.solutionsTree.put(actual, r);
		}
		return r;
	}

	private GraphPath<V, E> pathFrom(V vertex) {
		EGraphPath<V,E> ePath = graph.initialPath();
		if(!this.solutionsTree.containsKey(vertex) || 
				(this.solutionsTree.get(vertex) == null && this.solutionPath !=null)) 
			return this.solutionPath;
		if(this.solutionsTree.get(vertex) == null) return null;
		E edge = this.solutionsTree.get(vertex).edge;
		List<E> edges = new ArrayList<>();		
		while(edge != null) {
			edges.add(edge);
			vertex = Graphs.getOppositeVertex(graph,edge,vertex);
			edge = this.solutionsTree.get(vertex).edge;			
		}
		for(E e:edges) {
			ePath.add(e);
		}
		return ePath;
	}
	
	public record Sp<E>(Double weight, E edge) implements Comparable<Sp<E>> {
		
		public static <E> Sp<E> of(Double weight,E edge) {
			return new Sp<>(weight,edge);
		}

		@Override
		public int compareTo(Sp<E> sp) {
			return this.weight.compareTo(sp.weight);
		}

	}
}
