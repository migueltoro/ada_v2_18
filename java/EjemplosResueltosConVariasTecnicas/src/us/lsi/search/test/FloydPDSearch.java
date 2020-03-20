package us.lsi.search.test;

import java.util.List;

import org.jgrapht.Graph;

import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.hypergraphs.SimpleHyperEdge;
import us.lsi.graphs.hypergraphs.VirtualHyperVertex;


public class FloydPDSearch
	extends VirtualHyperVertex<FloydPDSearch,
		SimpleHyperEdge<FloydPDSearch,FloydPDSearch.Alternativa>,FloydPDSearch.Alternativa> {
	
	public static enum Alternativa{Yes, No};
	
	public static FloydPDSearch end(Graph<Integer,SimpleEdge<Integer>> graph) {
		Integer n = graph.vertexSet().size();
		return new FloydPDSearch(n, n, n, graph);
	}	
	public static FloydPDSearch create(Integer i, Integer j, Graph<Integer,SimpleEdge<Integer>> graph) {
		return new FloydPDSearch(i, j, 0, graph);
	}
	public static FloydPDSearch create(Integer i, Integer j, int k, Graph<Integer,SimpleEdge<Integer>> graph) {
		return new FloydPDSearch(i, j, k, graph);
	}
	
	public Integer i;
	public Integer j;
	public Integer k;
	public Graph<Integer,SimpleEdge<Integer>> grafo;
	private Integer n;
	
	
	FloydPDSearch(int i, int j, int k, Graph<Integer,SimpleEdge<Integer>> grafo) {
		super();
		this.i = i;
		this.j = j;
		this.k = k;
		this.grafo = grafo;
		this.n= grafo.vertexSet().size();
	}
	
	public boolean esCasoBase() {
			return grafo.containsEdge(i,j)  ||  k == n;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		result = prime * result + k;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FloydPDSearch other = (FloydPDSearch) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		if (k != other.k)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "(" + i + "," + j + "," + k + ")";
	}
	@Override
	public Boolean isValid() {
		return true;
	}
	@Override
	public List<Alternativa> actions() {
		if(this.esCasoBase()) return List.of();
		return List.of(Alternativa.No,Alternativa.Yes);
	}	
	@Override
	public FloydPDSearch getThis() {
		return this;
	}
	@Override
	public List<FloydPDSearch> neighbors(Alternativa a) {
		List<FloydPDSearch> r=null;
		switch(a){
		case No : r = List.of(FloydPDSearch.create(i,j,k+1,grafo)); break;
		case Yes : r = List.of(FloydPDSearch.create(i, k, k+1,grafo),FloydPDSearch.create(k, j, k+1,grafo)); break;
		}
		return r;
	}
	@Override
	public Boolean isBaseCase() {
		return grafo.containsEdge(i,j)  ||  k == n;
	}
	@Override
	public Double baseCaseSolution() {
		Double r = null;
		if(grafo.containsEdge(i, j)){
			SimpleEdge<Integer> e = grafo.getEdge(i, j);
			Double w = grafo.getEdgeWeight(e);
			r = w;
		}
		return r;
	}
}

