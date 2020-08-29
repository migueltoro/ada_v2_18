package us.lsi.graphs.alg;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;
import org.jheaps.AddressableHeap.Handle;

import us.lsi.common.Lists2;
import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.math.Math2;

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
		Handle<Double, Data<V, E>> dataActual = heap.deleteMin();
		V vertexActual = dataActual.getValue().vertex;
		List<E> edges = graph.edgesListOf(vertexActual);
		if(size.apply(vertexActual) > threshold) edges = Lists2.randomUnitary(edges);
		for(E backEdge: edges) {
			V v = graph.getEdgeTarget(backEdge);
			Double newDistance = ePath.add(dataActual.getValue().distanceToOrigin,backEdge);
			Double newDistanceToEnd = ePath.estimatedWeightToEnd(newDistance,goal,end,heuristic);
			if(!tree.containsKey(v)) {				
				Data<V,E> nd = Data.of(v,backEdge,newDistance);
				Handle<Double,Data<V, E>> h = heap.insert(newDistanceToEnd,nd);
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
		return vertexActual;
	}
	
	
	public GraphPath<V, E> pathToEnd() {
		GraphPath<V, E> path;
		AStarRandom.iterations = 0;
		Math2.initRandom();
		do {			
			AStarRandom<V, E> ms = this.copy();
			try {
				path = ms.pathTo(goal);
			} catch (IllegalArgumentException e) {
				path = null;
			}
			AStarRandom.iterations++;
		} while (path == null);
		return path;
	}
	
	public GraphPath<V, E> search() {
		return pathToEnd();
	}
	

}
