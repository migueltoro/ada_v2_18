package us.lsi.graphs;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Graphs2 {

	
	public static <V,E> SimpleDirectedWeightedGraph<V,E> toDirectedGraph(SimpleWeightedGraph<V,E> graph){
		SimpleDirectedWeightedGraph<V,E> gs = 
				new SimpleDirectedWeightedGraph<V,E>(
						graph.getVertexSupplier(), 
						graph.getEdgeSupplier());
		for(V v:graph.vertexSet()){
			gs.addVertex(v);
		}
		for(E e:graph.edgeSet()){
			gs.addEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e));
			gs.addEdge(graph.getEdgeTarget(e), graph.getEdgeSource(e));
		}
		return gs;
	}
	
	public static <V,E> SimpleDirectedGraph<V,E> toDirectedGraph(SimpleGraph<V,E> graph){
		SimpleDirectedGraph<V,E> gs = 
				new SimpleDirectedGraph<V,E>(
						graph.getVertexSupplier(), 
						graph.getEdgeSupplier(),
						true);
		for(V v:graph.vertexSet()){
			gs.addVertex(v);
		}
		for(E e:graph.edgeSet()){
			gs.addEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e));
			gs.addEdge(graph.getEdgeTarget(e), graph.getEdgeSource(e));
		}
		return gs;
	}
	
	public static <V,E,G extends Graph<V,E>> G subGraph(G graph, 
			Predicate<V> pv, Predicate<E> pe,
			Supplier<G> creator){
		
		Set<V> vertices = null;
	    Set<E> edges = null;
		
	    if (pv!=null) vertices = graph.vertexSet().stream().filter(pv).collect(Collectors.toSet());
		else vertices = graph.vertexSet();
		
		if (pe!=null) edges = graph.edgeSet().stream().filter(pe).collect(Collectors.toSet());
		else edges = graph.edgeSet();
		
		G r = creator.get();
				
		vertices.stream().forEach(x->r.addVertex(x));
		edges.stream().forEach(x->r.addEdge(graph.getEdgeSource(x),graph.getEdgeTarget(x), x));
		
		return r;
	}

	public static <V, E, G extends Graph<V, E>> G completeGraph(
			G graph, 
			Double weight,
			Supplier<G> creator, 
			EdgeWeightFactory<V,E> edgeCreator) {

		G r = creator.get();

		graph.vertexSet().stream().forEach(x -> r.addVertex(x));
		graph.edgeSet().stream().forEach(x -> r.addEdge(graph.getEdgeSource(x), graph.getEdgeTarget(x), x));

		for (V v1 : graph.vertexSet()) {
			for (V v2 : graph.vertexSet()) {
				if (!v1.equals(v2)) {
					if (!graph.containsEdge(v1, v2)) {
						E e = edgeCreator.create(v1,v2,weight);
						r.addEdge(v1, v2, e);
					}
				}
			}
		}

		return r;
	}
	
	
}
