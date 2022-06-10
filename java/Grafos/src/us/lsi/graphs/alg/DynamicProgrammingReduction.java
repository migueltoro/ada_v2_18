package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import us.lsi.common.Preconditions;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

import java.util.Optional;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;

public class DynamicProgrammingReduction<V, E> {
	
	public static <V, E> DynamicProgrammingReduction<V, E> of(
			EGraph<V, E> graph) {
		GreedyOnGraph<V, E> ga = GreedyOnGraph.of(graph);
		Optional<GraphPath<V, E>> gp = ga.solutionPath();
		if(gp.isPresent()) return new DynamicProgrammingReduction<V, E>(graph,gp.get().getWeight(),gp.get(),false);
		else return new DynamicProgrammingReduction<V, E>(graph,null,null,false);
	}
	
	public static <V, E> DynamicProgrammingReduction<V, E> of(
			EGraph<V, E> graph, Double bestValue, GraphPath<V, E> optimalPath, Boolean withGraph) {
		return new DynamicProgrammingReduction<V, E>(graph,bestValue,optimalPath,withGraph);
	}

	private EGraph<V, E> graph;
	private Double bestValue = null;
	private Comparator<Sp<E>> comparatorEdges;
	private Comparator<Double> comparator;
	public Map<V, Sp<E>> solutionsTree;
	public GraphPath<V, E> optimalPath;
	public Graph<V,E> outGraph;
	private Boolean withGraph = false;

	DynamicProgrammingReduction(EGraph<V, E> g, Double bestValue, GraphPath<V, E> optimalPath, Boolean withGraph) {
		this.graph = g;
		Preconditions.checkArgument(this.graph.type().equals(EGraph.Type.Min) || 
				this.graph.type().equals(EGraph.Type.Max), "El tipo debe ser Min o Max");
		this.comparatorEdges = this.graph.type() == EGraph.Type.Min?Comparator.naturalOrder():Comparator.reverseOrder();
		this.comparator = this.graph.type() == EGraph.Type.Min?Comparator.naturalOrder():Comparator.reverseOrder();
		this.solutionsTree = new HashMap<>();
		this.bestValue = bestValue;
		this.optimalPath = optimalPath;
		this.withGraph = withGraph;
	}
	
	public Optional<GraphPath<V,E>> optimalPath(){
		return Optional.ofNullable(this.optimalPath);
	}

	private Boolean forget(E edge, V actual,Double accumulateValue,Predicate<V> goal,V end) {
		Boolean r = false;
		Double w = this.graph.boundedValue(actual, accumulateValue, edge);
		if(this.bestValue != null) r = comparator.compare(w, this.bestValue) >= 0;
		return r;
	}
	
	protected void update(Double accumulateValue) {
		if(this.bestValue == null || comparator.compare(accumulateValue,this.bestValue) < 0) {
				this.bestValue = accumulateValue;
		}
	}
	
	public void iniciaGraph() {
		if(this.withGraph) outGraph = Graphs2.simpleDirectedGraph();
	}
	
	private void addGraph(V v, E edge) {
		if(withGraph) {
			V v2 = Graphs.getOppositeVertex(graph,edge,v);
			if(!this.outGraph.containsVertex(v)) this.outGraph.addVertex(v);
			if(!this.outGraph.containsVertex(v2)) this.outGraph.addVertex(v2);
			if(!this.outGraph.containsEdge(edge)) this.outGraph.addEdge(v, v2, edge);
		}
	}

	public Optional<GraphPath<V, E>> search() {
		iniciaGraph();
		this.solutionsTree = new HashMap<>();
		search(graph.startVertex(),0., null);	
		return pathFrom(graph.startVertex());
	}
	
	
	
	private Sp<E> search(V actual, Double accumulateValue, E edgeToOrigin) {
		Sp<E> r = null;
		if(this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (graph.goal().test(actual)) {
			if (graph.goalHasSolution().test(actual)) {
				update(accumulateValue);
				r = Sp.of(graph.goalSolution(actual), null);
			} else r = null;
			this.solutionsTree.put(actual, r);
		} else {
			List<Sp<E>> rs = new ArrayList<>();	
			for (E edge : graph.edgesListOf(actual)) {					
				if (this.forget(edge,actual,accumulateValue,graph.goal(),graph.endVertex())) continue;
				V v = Graphs.getOppositeVertex(graph,edge,actual);
				Double ac = this.graph.add(actual,accumulateValue,edge,edgeToOrigin); 
				Sp<E> s = search(v,ac,edge);
				if (s!=null) {
					E lastEdge = this.solutionsTree.get(v).edge;
					Double spv = this.graph.fromNeighbordSolution(v,s.weight,edge,lastEdge);	
					Sp<E> sp = Sp.of(spv,edge);
					rs.add(sp);
				}
				addGraph(actual, edge);
			}
			r = rs.stream().filter(s->s!=null).min(this.comparatorEdges).orElse(null);
			this.solutionsTree.put(actual, r);
		}
		return r;
	}

	private Optional<GraphPath<V, E>> pathFrom(V vertex) {	
		if(this.solutionsTree.get(vertex) == null && this.optimalPath !=null) 
			return Optional.of(this.optimalPath);
		if(this.solutionsTree.get(vertex) == null) return Optional.empty();
		E edge = this.solutionsTree.get(vertex).edge;	
		EGraphPath<V,E> ePath = graph.initialPath();
		while(edge != null) {
			ePath.add(edge);
			vertex = Graphs.getOppositeVertex(graph,edge,vertex);
			edge = this.solutionsTree.get(vertex).edge;	
		}
		this.optimalPath = ePath;
		return Optional.of(ePath);
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
