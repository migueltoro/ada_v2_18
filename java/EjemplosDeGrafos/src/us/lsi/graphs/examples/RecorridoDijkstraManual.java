package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import us.lsi.common.Files2;
import us.lsi.common.Lists2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphcolors.GraphColors;
import us.lsi.graphs.GraphsReader;

public class RecorridoDijkstraManual<V,E> {
	
	public static <V, E> RecorridoDijkstraManual<V, E> of(Graph<V, E> graph, V initial, V end) {
		return new RecorridoDijkstraManual<V, E>(graph, initial, end);
	}
	
	public static <V, E> RecorridoDijkstraManual<V, E> of(Graph<V, E> graph, V initial) {
		return new RecorridoDijkstraManual<V, E>(graph, initial, null);
	}

	private Map<V,FibonacciHeapNode<Data<V,E>>> tree;
	private Map<V,Integer> position;
	private Graph<V,E> graph;
	private V initial;
	private V end;
	
	private RecorridoDijkstraManual(Graph<V, E> graph, V initial, V end) {
		super();
		this.tree = null;
		this.position = null;
		this.graph = graph;
		this.initial = initial;
		this.end = end;
	}

	private void dijkstraWalks(){
		tree = new HashMap<>();
		position = new HashMap<>();
		FibonacciHeap<Data<V,E>> heap = new FibonacciHeap<>();
		FibonacciHeapNode<Data<V,E>> data = Data.node(initial,null);
		heap.insert(data,0.);
		Integer n = 0;
		tree.put(initial,data);
		while(!heap.isEmpty()) {
			System.out.println("Cola = "+heap.toString());
			FibonacciHeapNode<Data<V,E>> dataActual = heap.removeMin();
			V vertexActual = dataActual.getData().vertex;
			position.put(vertexActual,n);
			n++;
			for(V v:Graphs.neighborListOf(graph, vertexActual)) {
				E backEdge = graph.getEdge(vertexActual, v);
				Double weightEdge = graph.getEdgeWeight(backEdge);
				Double newDistance = dataActual.getData().distance+weightEdge;
				if(!tree.containsKey(v)) {				
					Data<V,E> nd = Data.of(v,backEdge,newDistance);
					FibonacciHeapNode<Data<V, E>> nf = Data.node(nd);
					heap.insert(nf,newDistance);
					tree.put(v,nf);
				} else if(!tree.get(v).getData().closed){
					Double oldDistance = tree.get(v).getData().distance;
					if(newDistance < oldDistance) {
						tree.get(v).getData().distance = newDistance;
						tree.get(v).getData().edge = backEdge;
						heap.decreaseKey(tree.get(v), newDistance);
					}				
				}
			}
			tree.get(vertexActual).getData().closed = true;	
			if(vertexActual.equals(end)) break;
		}
	}
	
	public E tree(V v) {
		if(tree == null) dijkstraWalks();
		return tree.get(v).getData().edge;
	}

	public Integer position(V v) {
		if(position == null) dijkstraWalks();
		if(position.get(v)==null) return -1;
		return position.get(v);
	}

	private Double distanceToOrigin(V v) {
		if(tree == null) dijkstraWalks();
		if(tree.get(v)==null) return -1.;
		return tree.get(v).getData().distance;
	}
	
	public GraphPath<V,E> minPathToOrigin(V v) {
		if(tree == null) dijkstraWalks();
		List<V> vertexList = Lists2.newList(v);
		FibonacciHeapNode<Data<V,E>> data = tree.get(v);
		while(data.getData().edge != null) {
			v = Graphs.getOppositeVertex(graph,data.getData().edge, v);
			vertexList.add(0,v);
			data = tree.get(v);
		}
		GraphPath<V,E> path = new GraphWalk<>(graph,vertexList,distanceToOrigin(v));
		return path;
	}
	
	public static void main(String[] args) {
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::create, 
						Carretera::create,
						()->new SimpleWeightedGraph<Ciudad,Carretera>(
								Ciudad::create,Carretera::create),
						Carretera::getKm);
		
		RecorridoDijkstraManual<Ciudad,Carretera> ra = 
				RecorridoDijkstraManual.of(graph,Ciudad.create("Sevilla"),Ciudad.create("Cordoba"));
		List<Carretera> carreteras = ra.minPathToOrigin(Ciudad.create("Cordoba")).getEdgeList();
		
		DOTExporter<Ciudad,Carretera> de1 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(), 
				x->x.getNombre()+"--"+x.getKm());
		
		DOTExporter<Ciudad,Carretera> de2 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre()+"/"+ra.position(x)+"/"+ra.distanceToOrigin(x), 
				x->""+x.getKm(),
				null,
				e->GraphColors.getStyleIf("bold",e,x->carreteras.contains(x)));
		
		PrintWriter f1 = Files2.getWriter("ficheros/andalucia.gv");
		de1.exportGraph(graph, f1);
		
		PrintWriter f2 = Files2.getWriter("ficheros/andaluciaDijkstra.gv");
		de2.exportGraph(graph, f2);
		
		System.out.println(ra.minPathToOrigin(Ciudad.create("Antequera")));
		
	}
	
	static class Data<V,E> {
		V vertex;
		E edge;
		Double distance;
		Boolean closed;
		
		public static <V,E> FibonacciHeapNode<Data<V,E>> node(V vertex, E edge) {
			return new FibonacciHeapNode<>(new Data<>(vertex,edge,0.));
		}
		public static <V,E> FibonacciHeapNode<Data<V,E>> node(V vertex, E edge, Double distance) {
			return new FibonacciHeapNode<>(new Data<>(vertex,edge,distance));
		}
		public static <V,E> FibonacciHeapNode<Data<V,E>> node(Data<V,E> data) {
			return new FibonacciHeapNode<>(data);
		}
		public static <V,E> Data<V,E> of(V vertex, E edge, Double distance) {
			return new Data<>(vertex,edge, distance);
		}
		public Data(V vertex, E edge, Double distance) {
			super();
			this.vertex = vertex;
			this.edge = edge;
			this.distance = distance;
			this.closed = false;
		}
		
	}

}

