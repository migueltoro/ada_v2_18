package us.lsi.graphs.search;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;
import org.jgrapht.util.FibonacciHeapNode;

import us.lsi.common.Lists2;
import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.math.Math2;
import us.lsi.walks.manual.Data;

public class AStarSearchRandom<V, E> extends AStarSearch<V, E>{

	public static Integer threshold;
	protected Function<V,Integer> size;
	public static Integer iterations;

	AStarSearchRandom(EGraph<V, E> graph, Predicate<V> goal, V end,
			TriFunction<V, Predicate<V>, V, Double> heuristic,
			Function<V,Integer> size) {
		super(graph, goal, end, heuristic);
		this.size = size;
	}
	
	@Override
	public AStarSearchRandom<V, E> copy() {
		return GSearch.aStarRandom(this.graph, this.goal, this.end, this.heuristic,this.size);
	}

	@Override
	public V next() {
		FibonacciHeapNode<Data<V,E>> dataActual = heap.removeMin();
		V vertexActual = dataActual.getData().vertex;
		List<E> edges = graph.edgesListOf(vertexActual);
		if(size.apply(vertexActual) > threshold) edges = Lists2.randomUnitary(edges);
		for(E backEdge: edges) {
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
	
	@Override
	public GraphPath<V, E> pathToEnd() {
		GraphPath<V, E> path;
		AStarSearchRandom.iterations = 0;
		Math2.initRandom();
		do {			
			AStarSearchRandom<V, E> ms = this.copy();
			try {
				path = ms.pathTo(goal);
			} catch (IllegalArgumentException e) {
				path = null;
			}
			AStarSearchRandom.iterations++;
		} while (path == null);
		return path;
	}
	
	
	

}
