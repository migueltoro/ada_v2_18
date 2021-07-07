package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jheaps.AddressableHeap;
import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;

import us.lsi.common.TriFunction;
import us.lsi.flujossecuenciales.Iterators;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;


public class AStar<V,E> implements GraphAlg<V,E>, Iterator<V>, Iterable<V> {

	public EGraph<V,E> graph; 
	protected V startVertex;
	public Predicate<V> goal;
	public V end;
	public Predicate<V> constraint;
	protected TriFunction<V,Predicate<V>,V,Double> heuristic;
	public Map<V,Handle<Double,Data<V,E>>> tree;
	public AddressableHeap<Double,Data<V,E>> heap; 
	protected EGraphPath<V,E> ePath;
	public Graph<V,E> outGraph;
	public Boolean withGraph = false;
	public Optional<GraphPath<V,E>> optPath;
	

	AStar(EGraph<V, E> graph,TriFunction<V,Predicate<V>, V,Double> heuristic) {
		super();
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.goal = graph.goal();
		this.end = graph.endVertex();
		this.constraint = graph.constraint();
		this.heuristic = heuristic;
		this.tree = new HashMap<>();
		this.ePath = graph.initialPath();
		this.heap = new FibonacciHeap<>(Comparator.naturalOrder());
		Data<V,E> data = Data.of(startVertex,null,ePath.getWeight());	
		Double d = ePath.estimatedWeightToEnd(data.distanceToOrigin,startVertex,goal,end,heuristic);
		Handle<Double, Data<V, E>> h = this.heap.insert(d,data);
		this.tree.put(startVertex,h);
	}

	@Override
	public Stream<V> stream() {
		if(this.withGraph) outGraph = new SimpleDirectedWeightedGraph<>(null,null);
		return Iterators.asStream(this.iterator());
	}
		
	@Override
	public AStar<V,E> copy(){
		return new AStar<V,E>(this.graph, this.heuristic);
	}
	
	public Iterator<V> iterator() {
		return this;
	}
	
	public boolean isSeenVertex(V v) {
		return this.tree.containsKey(v);
	}
	
	public boolean hasNext() {
		return !heap.isEmpty(); 
	}

	@Override
	public V next() {
		Handle<Double, Data<V, E>> hActual = heap.deleteMin();
		Data<V, E> dActual = hActual.getValue();
		V vertexActual = dActual.vertex;
		if(this.withGraph) outGraph.addVertex(vertexActual);
		Double actualDistance = dActual.distanceToOrigin;
		E edgeToOrigen = dActual.edge;
		for (E backEdge : graph.edgesListOf(vertexActual)) {
			V v = Graphs.getOppositeVertex(graph,backEdge,vertexActual);
			Double newDistance = ePath.add(actualDistance,v,backEdge,edgeToOrigen);
			Double newDistanceToEnd =  ePath.estimatedWeightToEnd(newDistance,v, goal, end, heuristic);
			if (!tree.containsKey(v)) {
				Data<V, E> dv = Data.of(v, backEdge, newDistance);
				Handle<Double, Data<V, E>> hv = heap.insert(newDistanceToEnd, dv);
				tree.put(v, hv);
			} else if (newDistance < tree.get(v).getValue().distanceToOrigin()) {
				Data<V, E> dv = Data.of(v, backEdge, newDistance);
				Handle<Double, Data<V, E>> hv = tree.get(v);
				Double oldDistanceToEnd = hv.getKey();
				Double oldDistanceToOrigen = tree.get(v).getValue().distanceToOrigin();
				hv.setValue(dv);
				try {
						hv.decreaseKey(newDistanceToEnd);
				} catch (IllegalArgumentException e) {
					System.out.println(e);
					System.out.printf("%s,%s, Nueva distancia al origen = %.2f, Nueva distancia al al final = %.2f\n",vertexActual,v,newDistance,newDistanceToEnd);
					System.out.printf("Antigua distancia al origen = %.2f, Antigua distancia al final = %.2f\n",oldDistanceToOrigen,oldDistanceToEnd);
					System.out.println("______________________________");
				}
			}
			if(this.withGraph) {
				outGraph.addVertex(v);
				outGraph.addEdge(vertexActual,v,backEdge);
			}
		}
		if(this.goal.test(vertexActual)) this.optPath = this.path(startVertex,Optional.of(vertexActual));
		return vertexActual;
	}

	public E getEdgeToOrigin(V v) {
		return tree.get(v).getValue().edge;
	}

	@Override
	public EGraph<V, E> getGraph() {
		return this.graph;
	}

	@Override
	public V startVertex() {
		return this.startVertex;
	}
	
	public Optional<GraphPath<V, E>> path(V startVertex, Optional<V> last) {
		if (!last.isPresent() || !this.constraint.test(last.get())) return Optional.empty();
		V endVertex = last.get();
		Handle<Double, Data<V, E>> hav = this.tree.get(endVertex);
		Data<V, E> dav = hav.getValue();
		Double weight = dav.distanceToOrigin;
		E edge = dav.edge;
		List<E> edges = new ArrayList<>();
		while (edge != null) {
			edges.add(edge);
			endVertex = Graphs.getOppositeVertex(graph, edge, endVertex);
			edge = this.getEdgeToOrigin(endVertex);
		}
		Collections.reverse(edges);
		List<V> vertices = new ArrayList<>();
		V v = startVertex;
		vertices.add(v);
		for (E e : edges) {
			v = Graphs.getOppositeVertex(graph, e, v);
			vertices.add(v);
		}
		GraphPath<V, E> gp = new GraphWalk<>(graph, startVertex, endVertex, vertices, edges, weight);
		return Optional.of(gp);
	}

	public Optional<GraphPath<V, E>> search() {
		V startVertex = graph.startVertex();
		if(this.goal.test(startVertex)) return Optional.of(ePath);
		Optional<V> last = this.stream().filter(this.goal).findFirst();	
		return path(startVertex,last);
	}
	
	public List<GraphPath<V, E>> searchAll() {
		V startVertex = graph.startVertex();
		if(this.goal.test(startVertex)) return List.of(ePath);
		List<V> lasts = this.stream().filter(this.goal).toList();	
		return lasts.stream().map(v->path(startVertex,Optional.of(v)))
				.filter(p->p.isPresent())
				.map(p->p.get())
				.collect(Collectors.toList());
	}
	
	public static record Data<V, E> (V vertex, E edge, Double distanceToOrigin) {

		public static <V, E> Data<V, E> of(V vertex, E edge, Double distance) {
			return new Data<>(vertex, edge, distance);
		}

		public static <V, E> Data<V, E> of(Data<V, E> d) {
			return new Data<>(d.vertex, d.edge, d.distanceToOrigin);
		}

	}
	
}
