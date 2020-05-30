package us.lsi.graphs.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jgrapht.GraphPath;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import us.lsi.common.TriFunction;
import us.lsi.flujossecuenciales.Iterators;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;
import us.lsi.walks.manual.Data;

public class AStarSearch<V, E> implements GSearch<V,E>, Iterator<V>, Iterable<V> {

	public EGraph<V,E> graph; 
	protected V startVertex;
	public Predicate<V> goal;
	protected V end;
	protected TriFunction<V,Predicate<V>,V,Double> heuristic;
	public Map<V,FibonacciHeapNode<Data<V,E>>> tree;
	protected FibonacciHeap<Data<V,E>> heap = new FibonacciHeap<>();
	protected FibonacciHeapNode<Data<V,E>> data = Data.node(startVertex,null);
	protected EGraphPath<V,E> ePath;
	
	AStarSearch(EGraph<V, E> graph, Predicate<V> goal, V end, TriFunction<V,Predicate<V>,V,Double> heuristic) {
		super();
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.goal = goal==null?v->v.equals(end):goal;
		this.end = end;
		this.heuristic = heuristic;
		this.tree = new HashMap<>();
		this.ePath = graph.initialPath();
		heap = new FibonacciHeap<>();
		data = Data.node(startVertex,null);		
		this.heap.insert(data,ePath.estimatedWeightToEnd(0.,goal,end,heuristic));
		this.tree.put(startVertex,data);
	}

	@Override
	public Stream<V> stream() {
		return Iterators.asStream(this.iterator());
	}
	@Override
	public AStarSearch<V, E> copy(){
		return GSearch.aStar(this.graph, this.goal, this.end, this.heuristic);
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
		FibonacciHeapNode<Data<V,E>> dataActual = heap.removeMin();
		V vertexActual = dataActual.getData().vertex;
		for(E backEdge:graph.edgesListOf(vertexActual)) {
			V v = graph.getEdgeTarget(backEdge);
			Double newDistance = ePath.add(dataActual.getData().distance,backEdge);
			double newDistanceToEnd = ePath.estimatedWeightToEnd(newDistance,goal,end,heuristic);
			if(!tree.containsKey(v)) {				
				Data<V,E> nd = Data.of(v,backEdge,newDistance);
				FibonacciHeapNode<Data<V, E>> nf = Data.node(nd);
//				newDistanceToEnd = ePath.estimatedWeightToEnd(newDistance,goal,end,heuristic);
				heap.insert(nf,newDistanceToEnd);
				tree.put(v,nf);
			} else if(!tree.get(v).getData().closed){
				Double oldDistance = tree.get(v).getData().distance;
				if(newDistance < oldDistance) {
//					newDistanceToEnd = ePath.estimatedWeightToEnd(newDistance,goal,end,heuristic);
//					System.out.println(String.format("1--- %.2f,%.2f",newDistance,newDistanceToEnd));
					tree.get(v).getData().distance = newDistance;
					tree.get(v).getData().edge = backEdge;
					heap.decreaseKey(tree.get(v), newDistanceToEnd);
				}				
			}
		}
		tree.get(vertexActual).getData().closed = true;	
		return vertexActual;
	}

	public E getEdgeToOrigin(V v) {
		return tree.get(v).getData().edge;
	}

	@Override
	public EGraph<V, E> getGraph() {
		return this.graph;
	}

	@Override
	public V startVertex() {
		return this.startVertex;
	}
	
	@Override
	public GraphPath<V, E> pathToEnd() {
		return pathTo(this.goal);
	}
	
}
