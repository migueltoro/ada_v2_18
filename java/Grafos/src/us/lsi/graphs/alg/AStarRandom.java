package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jheaps.AddressableHeap.Handle;

import us.lsi.common.Lists2;
import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class AStarRandom<V, E> extends AStar<V, E>{

	public static Integer threshold;
	protected Function<V,Integer> size;
	public static Integer iterations;

	AStarRandom(EGraph<V, E> graph, Predicate<V> goal, V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<V,Integer> size) {
		super(graph, goal, end, heuristic);
		this.size = size;
	}
	
	@Override
	public AStarRandom<V, E> copy() {
		return GraphAlg.aStarRandom(this.graph, this.goal, this.end, this.heuristic,this.size);
	}
	
	
	@Override
	public V next() {
		Handle<Double, Data<V, E>> hActual = heap.deleteMin();
		Data<V, E> dActual = hActual.getValue();
		V vertexActual = dActual.vertex();
		Double actualDistance = tree.get(vertexActual).getValue().distanceToOrigin();
		E edgeToOrigen = tree.get(vertexActual).getValue().edge();
		List<E> edges = graph.edgesListOf(vertexActual);
		if (size.apply(vertexActual) > threshold)
			edges = Lists2.randomUnitary(edges);
		for (E backEdge : edges) {
			V v = graph.getEdgeTarget(backEdge);
			Double newDistance = ePath.add(actualDistance,v,backEdge,edgeToOrigen);
			double newDistanceToEnd =  ePath.estimatedWeightToEnd(newDistance,v, goal, end, heuristic);
			if (!tree.containsKey(v)) {
				Data<V, E> nd = Data.of(v, backEdge, newDistance);
				Handle<Double, Data<V, E>> h = heap.insert(newDistanceToEnd, nd);
				tree.put(v, h);
			} else if (newDistance < tree.get(v).getValue().distanceToOrigin()) {
				Data<V, E> dv = Data.of(v, backEdge, newDistance);
				Handle<Double, Data<V, E>> hv = tree.get(v);
				hv.setValue(dv);
				hv.decreaseKey(newDistanceToEnd);
			}
		}
//		this.nonGoal = !this.goal.test(vertexActual);
		return vertexActual;
	}
	
	public Optional<GraphPath<V, E>> search() {
		EGraphPath<V,E> ePath = graph.initialPath();
		V startVertex = graph.startVertex();
		if(this.goal.test(startVertex)) return Optional.of(ePath);
		Optional<V> last = this.stream().filter(this.goal).findFirst();
		if(last.isPresent()) {
			V end = last.get();
			E edge = this.getEdgeToOrigin(end);
			List<E> edges = new ArrayList<>();		
			while(edge!=null) {				
				edges.add(edge);
				end = Graphs.getOppositeVertex(graph, edge, end);
				edge = this.getEdgeToOrigin(end);			
			}
			Collections.reverse(edges);
			for(E e:edges) {
				ePath.add(e);
			}
			return Optional.of(ePath);
		} else {
			return Optional.ofNullable(null);
		}
	}
	

}
