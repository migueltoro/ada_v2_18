package us.lsi.graphs;


import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import us.lsi.common.Preconditions;
import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.SimpleVirtualGraph;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.ActionVirtualVertex;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraphI;
import us.lsi.hypergraphs.SimpleHyperEdge;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.hypergraphs.VirtualHyperVertex;
import us.lsi.path.EGraphPath.PathType;


public class Graphs2 {
	
	public static <V, E> Double weightToVertex(Graph<V,E> graph, V v1, V v2) {
		E e = graph.getEdge(v1,v2); 
		Double w = graph.getEdgeWeight(e); 
		return w;
	}
	
	public static <V, E> V closestVertex(Graph<V,E> graph, V vertex) {
		return closestVertex(graph,vertex,v->true);
	}
	
	public static <V, E> V closestVertex(Graph<V,E> graph, V vertex, Predicate<V> p) {
		return (Graphs.neighborSetOf(graph,vertex)).stream()
				.filter(p)
				.min(Comparator.comparingDouble(v->weightToVertex(graph,vertex,v)))
				.get();
	}
	
	public static <V, E> V furthestVertex(Graph<V,E> graph, V vertex) {
		return furthestVertex(graph,vertex,v->true);
	}
	
	public static <V, E> V furthestVertex(Graph<V,E> graph, V vertex, Predicate<V> p) {
		return (Graphs.neighborSetOf(graph,vertex)).stream()
				.filter(p)
				.max(Comparator.comparingDouble(v->weightToVertex(graph,vertex,v)))
				.get();
	}
	
	public static <V extends ActionVirtualVertex<V, E, A>, E extends ActionSimpleEdge<V, A>, A> EGraph<V, E> simpleVirtualGraphSum(
			V startVertex, Predicate<V> goal, V endVertex, Predicate<V> constraint) {
		return new SimpleVirtualGraph<V, E>(startVertex, goal, endVertex, constraint, PathType.Sum, null, null, null);
	}

	public static <V extends ActionVirtualVertex<V, E, A>, E extends ActionSimpleEdge<V, A>, A> EGraph<V, E> simpleVirtualGraphSum(
			V startVertex, Predicate<V> goal, V endVertex, Predicate<V> constraint, Function<E, Double> edgeWeight) {
		return new SimpleVirtualGraph<V, E>(startVertex, goal, endVertex, constraint, PathType.Sum, edgeWeight, null,
				null);
	}

	public static <V extends ActionVirtualVertex<V, E, A>, E extends ActionSimpleEdge<V, A>, A> EGraph<V, E> simpleVirtualGraphLast(
			V startVertex, Predicate<V> goal, V endVertex, Predicate<V> constraint, Function<V, Double> vertexWeight) {
		return new SimpleVirtualGraph<V, E>(startVertex, goal, endVertex, constraint, PathType.Last, null, vertexWeight,
				null);
	}

	public static <V extends ActionVirtualVertex<V, E, A>, E extends ActionSimpleEdge<V, A>, A> EGraph<V, E> simpleVirtualGraph(
			V startVertex, Predicate<V> goal, V endVertex, Predicate<V> constraint, Function<E, Double> edgeWeight,
			Function<V, Double> vertexWeight, TriFunction<V, E, E, Double> vertexPassWeight, PathType type) {
		return new SimpleVirtualGraph<V, E>(startVertex, goal, endVertex, constraint, type, edgeWeight, vertexWeight,
				vertexPassWeight);
	}
	
	public static <V extends VirtualHyperVertex<V, E, A>, E extends SimpleHyperEdge<V,E,A>, A> SimpleVirtualHyperGraph<V, E, A> simpleVirtualHyperGraph(V start) {
		return new SimpleVirtualHyperGraph<V, E, A>(start);
	}
	
	public static <V,E> SimpleGraph<V, E> simpleGraph() {
		return new SimpleGraph<V,E>(null,null,false);
	}
	
    public static <V,E> SimpleGraph<V, E> simpleGraph(Supplier<V> vs, Supplier<E> es, boolean weighted) {
        return new SimpleGraph<V,E>(vs, es, weighted);
    }
    
    public static <V,E> SimpleWeightedGraph<V, E> simpleWeightedGraph() {
		return new SimpleWeightedGraph<>(null,null);
	}

    public static <V,E> SimpleWeightedGraph<V, E> simpleWeightedGraph(Supplier<V> vs, Supplier<E> es) {
        return new SimpleWeightedGraph<>(vs, es);
    }
    
    public static <V,E> SimpleDirectedWeightedGraph<V, E> simpleDirectedWeightedGraph() {
		return new SimpleDirectedWeightedGraph<>(null,null);
	}
    
    public static <V,E> DirectedWeightedMultigraph<V,E> directedWeightedMultigraph() {
    	return new DirectedWeightedMultigraph<>(null,null);
    }

	public static <V,E> Set<V> getVertices(Graph<V,E> graph, E edge){
		return Set.of(graph.getEdgeSource(edge),graph.getEdgeTarget(edge));
	}
	
	public static <V, E, G extends Graph<V, E>> EGraph<V,E> eGraph(G graph, V startVertex,  
			Predicate<V> goal,V endVertex,Predicate<V> constraint,
			Function<E, Double> edgeWeight,
			Function<V, Double> vertexWeight, 
			TriFunction<V, E, E, Double> vertexPassWeight,
			PathType type) {
		return new EGraphI<V, E, G>(graph, startVertex, goal,endVertex,constraint,type,edgeWeight,vertexWeight,vertexPassWeight);
	}
	
	public static <V, E, G extends Graph<V, E>> EGraph<V, E> eGraphSum(G graph, V startVertex) {
		return new EGraphI<V, E, G>(graph, startVertex, null, null, null, PathType.Sum, null, null, null);
	}

	public static <V, E, G extends Graph<V, E>> EGraph<V, E> eGraphSum(G graph, V startVertex, Predicate<V> goal,
			V endVertex, Predicate<V> constraint) {
		return new EGraphI<V, E, G>(graph, startVertex, goal, endVertex, constraint, PathType.Sum, null, null, null);
	}
	
	public static <V, E, G extends Graph<V, E>> EGraph<V, E> eGraphLast(G graph, V startVertex) {
		return new EGraphI<V, E, G>(graph, startVertex, null, null, null, PathType.Last, null, null, null);
	}

	public static <V, E, G extends Graph<V, E>> EGraph<V, E> eGraphLast(G graph, V startVertex, Predicate<V> goal,
			V endVertex, Predicate<V> constraint) {
		return new EGraphI<V, E, G>(graph, startVertex, goal, endVertex, constraint, PathType.Last, null, null, null);
	}
	
	public static <V, E> SimpleDirectedWeightedGraph<V, E> toDirectedWeightedGraph(SimpleWeightedGraph<V, E> graph,
			Function<E, E> edgeReverse) {
		SimpleDirectedWeightedGraph<V, E> gs = new SimpleDirectedWeightedGraph<V, E>(graph.getVertexSupplier(),
				graph.getEdgeSupplier());
		for (V v : graph.vertexSet()) {
			gs.addVertex(v);
		}
		for (E e : graph.edgeSet()) {
			V s = graph.getEdgeSource(e);
			V t = graph.getEdgeTarget(e);
			Double w = graph.getEdgeWeight(e);
			gs.addEdge(s, t, e);
			gs.setEdgeWeight(e, w);
			E e1 = edgeReverse.apply(e);
			gs.addEdge(t, s, e1);
			gs.setEdgeWeight(e1, w);
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
	
	public static <V,E,G extends Graph<V,E>> G subGraphOfVertices(G graph, 
			Predicate<V> pv,
			Supplier<G> creator) {
		return subGraph(graph,pv,null,creator);
	}
	
	public static <V, E, G extends Graph<V, E>> G subGraphOfEdges(G graph, Predicate<E> pe, Supplier<G> creator) {
		return subGraph(graph, null, pe, creator);
	}
	
	public static <V, E, G extends Graph<V, E>> G subGraph(G graph, Predicate<V> pv, Predicate<E> pe,
			Supplier<G> creator) {

		Predicate<V> npv = pv == null ? v -> true : pv;

		Set<V> vertices = graph.vertexSet().stream().filter(npv).collect(Collectors.toSet());

		Predicate<E> npe = e -> vertices.contains(graph.getEdgeSource(e)) && vertices.contains(graph.getEdgeTarget(e));

		Predicate<E> npe2 = pe == null ? npe : e -> npe.test(e) && pe.test(e);

		Set<E> edges = graph.edgeSet().stream().filter(npe2).collect(Collectors.toSet());

		G r = creator.get();

		vertices.stream().forEach(x -> r.addVertex(x));
		edges.stream().forEach(x -> r.addEdge(graph.getEdgeSource(x), graph.getEdgeTarget(x), x));

		return r;
	}

	public static <V, E, G extends Graph<V, E>> G explicitCompleteGraph(
			G graph, 
			Double weight,
			Supplier<G> creator, 
			TriFunction<V,V,Double,E> edgeCreator,
			Function<E,Double> edgeWeight) {

		G r = creator.get();

		graph.vertexSet().stream().forEach(x -> r.addVertex(x));
		graph.edgeSet().stream().forEach(x -> r.addEdge(graph.getEdgeSource(x), graph.getEdgeTarget(x), x));

		for (V v1 : graph.vertexSet()) {
			for (V v2 : graph.vertexSet()) {
				if (!v1.equals(v2)) {
					if (!graph.containsEdge(v1, v2)) {
						E e = edgeCreator.apply(v1,v2,weight);
						r.addEdge(v1, v2, e);
					}
				}
			}
		}
		r.edgeSet().forEach(e->r.setEdgeWeight(e, edgeWeight.apply(e)));
		return r;
	}

	public static <V, E extends SimpleEdge<V>> V getOppositeVertex(Graph<V, E> graph, E edge, V vertex) {
		V r = null;
		if (edge.source().equals(vertex)) r = edge.target();
		if (edge.target().equals(vertex)) r = edge.source();
		Preconditions.checkNotNull(r);
		return r;
	}
	
	public static <V, E, G extends Graph<V, E>> G sustituteEdge(G graph, E edge, GraphPath<V,E> graphPath) {
		Graph<V,E> origin = graphPath.getGraph();
		graph.removeEdge(edge);
		graphPath.getVertexList().stream().forEach(v->{if(!graph.containsVertex(v))graph.addVertex(v);});
		graphPath.getEdgeList().stream()
			.forEach(e->graph.addEdge(origin.getEdgeSource(e), origin.getEdgeTarget(e), e));
		return graph;
	}
	
	/**
	 * @param graph Un grafo no dirigido
	 * @param edgeReverse Una funci�n que produce una arista inversa con el mismo peso
	 * @param sources Los v�rtices que ser�n fuentes
	 * @param targets Los v�rtices que ser�n sumideros
	 * @return Un grafo dirigido donde los v�rtices fuente no tienen aristas de entrada y 
	 * los sumideros no tienen aristas de salida
	 */
	public static <V,E> SimpleDirectedWeightedGraph<V,E> toDirectedWeightedGraphFlow(
			SimpleWeightedGraph<V,E> graph, 
			Function<E,E> edgeReverse, 
			Set<V> sources, 
			Set<V> targets){
		SimpleDirectedWeightedGraph<V,E> gs = Graphs2.toDirectedWeightedGraph(graph, edgeReverse);
		Set<E> remove = new HashSet<>();
		for(E e:gs.edgeSet()) {
			V s = gs.getEdgeSource(e);
			V t = gs.getEdgeTarget(e);
			if(sources.contains(t) || targets.contains(s)) remove.add(e);
		}
		gs.removeAllEdges(remove);
		return gs;
	}

	
	
}
