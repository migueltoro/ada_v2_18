package us.lsi.graphs.alg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jgrapht.GraphPath;
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
	protected V end;
	protected TriFunction<V,Predicate<V>,V,Double> heuristic;
	public Map<V,Handle<Double,Data<V,E>>> tree;
	protected AddressableHeap<Double,Data<V,E>> heap; 
	protected EGraphPath<V,E> ePath;
	protected Boolean nonGoal;
	

	AStar(EGraph<V, E> graph, Predicate<V> goal, V end, TriFunction<V,Predicate<V>,V,Double> heuristic) {
		super();
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.goal = goal==null?v->v.equals(end):goal;
		this.end = end;
		this.heuristic = heuristic;
		this.tree = new HashMap<>();
		this.ePath = graph.initialPath();
		this.heap = new FibonacciHeap<>();
		Data<V,E> data = Data.of(startVertex);		
		Handle<Double, Data<V, E>> h = this.heap.insert(ePath.estimatedWeightToEnd(0.,goal,end,heuristic),data);
		this.tree.put(startVertex,h);
		this.nonGoal = true;
	}

	@Override
	public Stream<V> stream() {
		return Iterators.asStream(this.iterator());
	}
		
	@Override
	public AStar<V,E> copy(){
		return new AStar<V,E>(this.graph, this.goal, this.end, this.heuristic);
	}
	
	public Iterator<V> iterator() {
		return this;
	}
	
	public boolean isSeenVertex(V v) {
		return this.tree.containsKey(v);
	}
	
	public boolean hasNext() {
		return !heap.isEmpty() && nonGoal;
	}

	@Override
	public V next() {
		Handle<Double,Data<V,E>> dataActual = heap.deleteMin();
		V vertexActual = dataActual.getValue().vertex;
		for(E backEdge:graph.edgesListOf(vertexActual)) {
			V v = graph.getEdgeTarget(backEdge);
			Double newDistance = ePath.add(dataActual.getValue().distanceToOrigin,backEdge);
			double newDistanceToEnd = ePath.estimatedWeightToEnd(newDistance,goal,end,heuristic);
			if(!tree.containsKey(v)) {				
				Data<V,E> nd = Data.of(v,backEdge,newDistance);
				Handle<Double, Data<V, E>> h = heap.insert(newDistanceToEnd,nd);
				tree.put(v,h);
			} else if(!tree.get(v).getValue().closed){
				Double oldDistance = tree.get(v).getValue().distanceToOrigin;
				if(newDistance < oldDistance) {
					tree.get(v).getValue().distanceToOrigin = newDistance;
					tree.get(v).getValue().edge = backEdge;
					tree.get(v).decreaseKey(newDistanceToEnd);
				}				
			}
		}
		tree.get(vertexActual).getValue().closed = true;
		this.nonGoal = !this.goal.test(vertexActual);
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
	
	public Optional<GraphPath<V, E>> search() {
		return pathTo(this.goal);
	}
	
	
	public static class Data<V,E> {
		
		public V vertex;
		public E edge;
		public Double distanceToOrigin;
		public Boolean closed;
		
		public static <V,E> Data<V,E> of(V vertex, E edge, Double distance) {
			return new Data<>(vertex,edge, distance);
		}
		
		public static <V,E> Data<V,E> of(V vertex, E edge) {
			return new Data<>(vertex,edge,0.);
		}
		
		public static <V,E> Data<V,E> of(V vertex) {
			return new Data<>(vertex,null,0.);
		}
		
		public static <V,E> Data<V,E> of(Data<V,E> d) {
			return new Data<>(d.vertex,d.edge, d.distanceToOrigin);
		}
		
		public Data(V vertex, E edge, Double distance) {
			super();
			this.vertex = vertex;
			this.edge = edge;
			this.distanceToOrigin = distance;
			this.closed = false;
		}

	}

	
}
