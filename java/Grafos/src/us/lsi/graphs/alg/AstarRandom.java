package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jheaps.AddressableHeap.Handle;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;

public class AstarRandom<V, E> extends AStar<V, E>{
	
	public static <V, E> AstarRandom<V, E> of(EGraph<V, E> graph, Function<V, Integer> size) {
		return new AstarRandom<V, E>(graph, size);
	}

	public static Integer threshold;
	protected Function<V,Integer> size;
	public static Integer iterations = 0;

	AstarRandom(EGraph<V, E> graph, 
			Function<V,Integer> size) {
		super(graph,null,null);
		this.size = size;
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
			edges = List2.randomUnitary(edges);
		for (E backEdge : edges) {
			V v = graph.getEdgeTarget(backEdge);
			Double newDistance = graph.add(v,actualDistance,backEdge,edgeToOrigen);
			double newDistanceToEnd =  graph.estimatedWeightToEnd(v,newDistance);
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
	
	private Optional<GraphPath<V, E>> search_p() {
		EGraphPath<V,E> ePath = graph.initialPath();
		V startVertex = graph.startVertex();
		if(graph.goal().test(startVertex)) return Optional.of(ePath);
		Optional<V> last = this.stream().filter(graph.goal()).findFirst();
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
	
	public Optional<GraphPath<V, E>> search() {
		Optional<GraphPath<V, E>> gp = null;
		do {
			gp = search_p();
			AstarRandom.iterations++;
		} while(!gp.isPresent());
		return gp;
	}
	
	public <S> Optional<S> search(Function<GraphPath<V,E>,S> f) {
		Optional<GraphPath<V, E>> p = search();
		return p.map(f);
	}
	
}
