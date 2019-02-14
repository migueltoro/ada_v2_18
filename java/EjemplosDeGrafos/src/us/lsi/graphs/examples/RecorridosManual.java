package us.lsi.graphs.examples;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
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

public class RecorridosManual<V, E> {

	static class FP<E> {
		E father;
		Integer position;
		public FP(E father, Integer position) {
			super();
			this.father = father;
			this.position = position;
		}
		public static <E> FP<E> of(E father, Integer position){
			return new FP<>(father,position);
		}
	}

	/**
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph Grafo de entrada
	 * @param initial Vertice inicial
	 * @return Un Map que asocia a cada vertice la arista al padre y su posicion en un recorrido en anchura
	 */
	public static <V,E> Map<V,FP<E>> breadthSpanningTree(Graph<V,E> graph, V initial){
		Map<V,FP<E>> tree = new HashMap<>();
		Queue<V> queue = new LinkedList<>();
		queue.add(initial);
		Integer n = 0;
		tree.put(initial, FP.of(null,n));
		while(!queue.isEmpty()) {
			System.out.println("Cola = "+queue.toString());
			V actual = queue.remove();
			tree.get(actual).position = n;
			n++;
			for(V v:Graphs.neighborListOf(graph, actual)) {
				if(!tree.containsKey(v)) {
					queue.add(v);
					tree.put(v,FP.of(graph.getEdge(actual, v),null));
				}
			}		
		}
		return tree;
	}
	/**
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph Grafo de entrada
	 * @param initial Vertice inicial
	 * @return Un Map que asocia a cada vertice la arista al padre y su posicion en un recorrido en profundidad
	 */
	public static <V,E> Map<V,FP<E>> depthSpanningTree(Graph<V,E> graph, V initial){
		Map<V,FP<E>> tree = new HashMap<>();
		Stack<V> stack = new Stack<>();
		stack.push(initial);
		Integer n = 0;
		tree.put(initial,FP.of(null,n));
		while(!stack.isEmpty()) {
			System.out.println("Pila = "+stack.toString());
			V actual = stack.pop();
			tree.get(actual).position= n;
			n++;
			for(V v:Graphs.neighborListOf(graph, actual)) {
				if(!tree.containsKey(v)) {
					stack.push(v);
					tree.put(v, FP.of(graph.getEdge(actual, v),null));
				}
			}		
		}
		return tree;
	}
	
	
	/**
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph Grafo de entrada
	 * @param v Un vertice
	 * @param tree Un Map que asocia a cada vertice la arista al padre y su posicion en un recorrido
	 * @return La distancia de v a la raiz del arbol de recubrimiento
	 */
	public static <V,E> Integer level(V v, Map<V,FP<E>> tree, Graph<V,E> graph) {
		Integer n = 0;
		E e = tree.get(v).father;
		while(e!=null) {
			v = Graphs.getOppositeVertex(graph, e, v);
			e = tree.get(v).father;
			n++;
		}
		return n;
	}
	
	public static void main(String[] args) {
		
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::create, 
						Carretera::create,
						()->new SimpleWeightedGraph<Ciudad,Carretera>(
								Ciudad::create,Carretera::create),
						Carretera::getKm);
		
		Map<Ciudad,FP<Carretera>> breadthTree = breadthSpanningTree(graph,Ciudad.create("Sevilla"));
		Set<Carretera> setAnchura =breadthTree.values().stream().filter(x->x!=null).map(x->x.father)
				.collect(Collectors.toSet());
		Map<Ciudad,FP<Carretera>> depthTree = depthSpanningTree(graph,Ciudad.create("Sevilla"));
		Set<Carretera> setProfundidad =depthTree.values().stream().filter(x->x!=null).map(x->x.father)
				.collect(Collectors.toSet());
		
		
		DOTExporter<Ciudad,Carretera> de1 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(), 
				x->x.getNombre()+"--"+x.getKm());
		
		DOTExporter<Ciudad,Carretera> de2 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre()+"/"+breadthTree.get(x).position+"/"+level(x,breadthTree,graph), 
				x->""+x.getKm(),
				null,
				e->GraphColors.getStyleIf("bold",e,x->setAnchura.contains(x)));
		
		DOTExporter<Ciudad,Carretera> de3 = new DOTExporter<Ciudad,Carretera>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre()+"/"+depthTree.get(x).position+"/"+level(x,breadthTree,graph), 
				x->""+x.getKm(),
				null,
				e->GraphColors.getStyleIf("bold",e,x->setProfundidad.contains(x)));
		
		PrintWriter f1 = Files2.getWriter("ficheros/anadalucia.gv");
		de1.exportGraph(graph, f1);
		
		PrintWriter f2 = Files2.getWriter("ficheros/andaluciaAnchura.gv");
		de2.exportGraph(graph, f2);
		
		PrintWriter f3 = Files2.getWriter("ficheros/andaluciaProfundidad.gv");
		de3.exportGraph(graph, f3);
	}

}
