package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.common.Files2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphcolors.GraphColors;
import us.lsi.graphs.GraphsReader;

public class RecorridoEnAnchuraManual<V,E> {
	
	public static <V, E> RecorridoEnAnchuraManual<V, E> of(Graph<V, E> graph, V initial) {
		return new RecorridoEnAnchuraManual<V, E>(graph, initial);
	}

	private Map<V,E> tree;
	private Map<V,Integer> position;
	private Graph<V,E> graph;
	private V initial;
	
	private RecorridoEnAnchuraManual(Graph<V, E> graph, V initial) {
		super();
		this.tree = null;
		this.position = null;
		this.graph = graph;
		this.initial = initial;
	}

	private void breadthWalks(){
		tree = new HashMap<>();
		position = new HashMap<>();
		Queue<V> queue = new LinkedList<>();
		queue.add(initial);
		Integer n = 0;
		tree.put(initial,null);
		while(!queue.isEmpty()) {
			System.out.println("Cola = "+queue.toString());
			V actual = queue.remove();
			position.put(actual,n);
			n++;
			for(V v:Graphs.neighborListOf(graph, actual)) {
				if(!tree.containsKey(v)) {
					queue.add(v);
					tree.put(v,graph.getEdge(actual, v));
				}
			}		
		}
	}
	
	public static <V,E> Integer level(V v, Map<V,E> tree, Graph<V,E> graph) {
		Integer n = 0;
		E e = tree.get(v);
		while(e!=null) {
			v = Graphs.getOppositeVertex(graph, e, v);
			e = tree.get(v);
			n++;
		}
		return n;
	}
	
	public E tree(V v) {
		if(tree == null) breadthWalks();
		return tree.get(v);
	}

	public Integer position(V v) {
		if(position == null) breadthWalks();
		return position.get(v);
	}

	private Integer level(V v) {
		if(tree == null) breadthWalks();
		return level(v,tree,graph);
	}
	
	public static void main(String[] args) {
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::create, 
						Carretera::create,
						()->new SimpleWeightedGraph<Ciudad,Carretera>(
								Ciudad::create,Carretera::create),
						Carretera::getKm);
		
		RecorridoEnAnchuraManual<Ciudad,Carretera> ra = RecorridoEnAnchuraManual.of(graph,Ciudad.create("Sevilla"));
		Set<Ciudad> ciudades = ra.graph.vertexSet();
		Set<Carretera> setAnchura = ciudades.stream().map(v->ra.tree(v)).filter(x->x!=null)
				.collect(Collectors.toSet());
		
		DOTExporter<Ciudad,Carretera> de1 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(), 
				x->x.getNombre()+"--"+x.getKm());
		
		DOTExporter<Ciudad,Carretera> de2 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre()+"/"+ra.position(x)+"/"+ra.level(x), 
				x->""+x.getKm(),
				null,
				e->GraphColors.getStyleIf("bold",e,x->setAnchura.contains(x)));
		
		PrintWriter f1 = Files2.getWriter("ficheros/andalucia.gv");
		de1.exportGraph(graph, f1);
		
		PrintWriter f2 = Files2.getWriter("ficheros/andaluciaAnchura.gv");
		de2.exportGraph(graph, f2);

	}

	


}
