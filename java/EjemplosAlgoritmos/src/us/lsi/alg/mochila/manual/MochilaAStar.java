package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.jheaps.AddressableHeap;
import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.common.TriFunction;


public class MochilaAStar {
	
	public static MochilaAStar of(MochilaVertex startVertex, MochilaVertex end,
			TriFunction<MochilaVertex, Predicate<MochilaVertex>, MochilaVertex, Double> heuristic) {
		return new MochilaAStar(startVertex, end, heuristic);
	}
		 
	public MochilaVertex startVertex;
	public Predicate<MochilaVertex> goal;
	protected MochilaVertex end;
	protected TriFunction<MochilaVertex,Predicate<MochilaVertex>,MochilaVertex,Double> heuristic;
	public Map<MochilaVertex,Handle<Double,DataAstar>> tree;
	protected AddressableHeap<Double,DataAstar> heap; 
	protected Boolean nonGoal;
	
	
	private MochilaAStar(MochilaVertex startVertex, MochilaVertex end, 
			TriFunction<MochilaVertex,Predicate<MochilaVertex>,MochilaVertex,Double> heuristic) {
		super();
		this.startVertex = startVertex;		
		this.end = end;
		this.goal = goal==null?v->v.equals(this.end):goal;
		this.heuristic = heuristic;
		this.tree = new HashMap<>();
		this.heap = new FibonacciHeap<>();
		DataAstar data = DataAstar.of(startVertex);		
		Handle<Double, DataAstar> h = this.heap.insert(-heuristic.apply(startVertex, goal, end),data);
		this.tree.put(startVertex,h);
		this.nonGoal = true;
	}
	
	public Optional<List<MochilaEdge>> search() {
		MochilaVertex vertexActual = null;
		while(!heap.isEmpty() && nonGoal) {
			Handle<Double,DataAstar> dataActual = heap.deleteMin();
			vertexActual = dataActual.getValue().vertex;
			for(MochilaEdge backEdge:vertexActual.edgesListOf()) { 
				MochilaVertex v = backEdge.target; 
				Double newDistance = dataActual.getValue().distanceToOrigin-backEdge.weight;
				Double newDistanceToEnd = newDistance-heuristic.apply(v, goal, end);
				if(!tree.containsKey(v)) {				
					DataAstar nd = DataAstar.of(v,backEdge,newDistance);
					Handle<Double, DataAstar> h = heap.insert(newDistanceToEnd,nd);
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
		}
		if(this.nonGoal) return Optional.empty();
		else return Optional.of(edges(vertexActual));
	}

	public List<MochilaEdge> edges(MochilaVertex end){
		MochilaEdge edge = tree.get(end).getValue().edge;
		List<MochilaEdge> edges = new ArrayList<>();
		if(end.equals(startVertex)) return edges;
		while(edge!=null) {				
			edges.add(edge);
			end = oppositeVertex(edge, end);
			edge = tree.get(end).getValue().edge;			
		}
		Collections.reverse(edges);
		return edges;
	}

	public static MochilaVertex oppositeVertex(MochilaEdge edge, MochilaVertex v) {
		if(edge.source.equals(v)) return edge.target;
		else return edge.source;
	}

	public static void printHandle(Handle<Double, DataAstar> h) {
		System.out.println(String.format("(%.2f,%s)", h.getKey(),h.getValue().toString()));
	}
	
}
