package us.lsi.search.test;

import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

import us.lsi.graphs.search.DPSearch;
import us.lsi.graphs.search.DynamicProgrammingSearch.PDType;
import us.lsi.graphs.hypergraphs.SimpleHyperEdge;
import us.lsi.graphs.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.graphs.hypergraphs.GraphTree;

import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.views.IntegerVertexGraphView;
import us.lsi.search.test.FloydPDSearch.Alternativa;


public class TestPD {
	
	public static SimpleWeightedGraph<Ciudad, Carretera> leeDatos(String fichero) {
		SimpleWeightedGraph<Ciudad, Carretera> graph = GraphsReader.newGraph(fichero, 
				Ciudad::ofFormat, 
				Carretera::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Carretera::getKm);
		return graph;
	}
	
	public static Double weight(GraphWalk<Integer,SimpleEdge<Integer>> gp) {
		return gp.getEdgeList().stream().mapToDouble(e->e.weight).sum();
	}
	
	public static GraphWalk<Integer,SimpleEdge<Integer>> solucionTree(
			GraphTree<FloydPDSearch,SimpleHyperEdge<FloydPDSearch,Alternativa>,Alternativa> tree, FloydPDSearch vertex){
		GraphWalk<Integer,SimpleEdge<Integer>> gp = null;
		if(tree.isBaseCase(vertex)) {
			List<Integer> ls = Arrays.asList(vertex.i,vertex.j);
			gp = new GraphWalk<>(vertex.grafo,ls,tree.weight(vertex));
		} else if(tree.action(vertex) == Alternativa.No){
			gp = solucionTree(tree,tree.neigbors(vertex).get(0));
		} else {
			GraphWalk<Integer,SimpleEdge<Integer>> gp1 = solucionTree(tree,tree.neigbors(vertex).get(0));
			GraphWalk<Integer,SimpleEdge<Integer>> gp2 = solucionTree(tree,tree.neigbors(vertex).get(1));
			gp = gp1.concat(gp2,g->weight(g));
		}
		return gp;
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		SimpleWeightedGraph<Ciudad, Carretera> graph = leeDatos("./ficheros/andalucia.txt");
		
		System.out.println(graph);
		
		IntegerVertexGraphView<Ciudad, Carretera> gv = IntegerVertexGraphView.of(graph);
		
		System.out.println(gv);
		Function<List<Double>,Double> addSolution = ls->ls.stream().mapToDouble(x->x).sum();
		
		Integer origen = gv.getIndex(Ciudad.ofName("Cadiz"));
		Integer destino = gv.getIndex(Ciudad.ofName("Almeria"));
		FloydPDSearch p = FloydPDSearch.create(origen,destino,gv);
		SimpleVirtualHyperGraph<FloydPDSearch,SimpleHyperEdge<FloydPDSearch,Alternativa>,Alternativa> graph2 = 
				Graphs2.simpleVirtualHyperGraph();
		
		DPSearch<FloydPDSearch,SimpleHyperEdge<FloydPDSearch,Alternativa>,Alternativa> a = 
				DPSearch.dynamicProgrammingSearch(graph2,addSolution,PDType.Min);
		a.search(p);
		System.out.println(a.getSolutionsTree());
		System.out.println(TestPD.solucionTree(a.tree(p),p).getVertexList().stream().map(v->gv.getVertex(v)).collect(Collectors.toList()));
	}

}
