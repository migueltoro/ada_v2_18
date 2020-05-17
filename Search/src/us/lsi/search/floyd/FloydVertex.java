package us.lsi.search.floyd;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.graph.GraphWalk;

import us.lsi.common.Lists2;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.VirtualHyperVertex;
import us.lsi.path.EGraphPath;

public class FloydVertex<V,E> extends VirtualHyperVertex<FloydVertex<V,E>,
		FloydEdge<V,E>,FloydVertex.ActionFloyd>{
	
	public static enum ActionFloyd{Yes, No};	
	
	public static <V,E> FloydVertex<V,E> of(Graph<V,E> graph, V origen, V destino) {
		FloydVertex.n = graph.vertexSet().size();
		return new FloydVertex<>(graph,origen,destino);
	}
	
	public Integer i;
	public Integer j;
	public Integer k;
	public Graph<V,E> graph;
	public List<V> vertices;
	public Map<V,Integer> index;
	private static Integer n;
	
	public FloydVertex(Graph<V, E> graph,V origen, V destino) {
		super();
		this.graph = graph;
		this.vertices = graph.vertexSet().stream().collect(Collectors.toList());
		this.index = IntStream.range(0,n).boxed().collect(Collectors.toMap(e->this.vertices.get(e),e->e));
		this.i = this.index.get(origen);
		this.j = this.index.get(destino);
		this.k = 0;
	}
	
	public FloydVertex(Integer i, Integer j, Integer k, Graph<V, E> graph, List<V> vertices, Map<V, Integer> index) {
		super();
		this.i = i;
		this.j = j;
		this.k = k;
		this.graph = graph;
		this.vertices = vertices;
		this.index = index;
	}
	
	public FloydVertex<V,E> neigbord(Integer i, Integer j, Integer k){
		return new FloydVertex<>(i,j,k,this.graph, this.vertices,this.index);
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
	public List<ActionFloyd> actions() {
		if(this.isBaseCase()) return List.of();
		return List.of(ActionFloyd.No,ActionFloyd.Yes);
	}
	
	@Override
	public List<FloydVertex<V,E>> neighbors(ActionFloyd a) {
		List<FloydVertex<V,E>> r=null;
		switch(a){
		case No : r = List.of(this.neigbord(i,j,k+1)); break;
		case Yes : r = List.of(this.neigbord(i, k, k+1),this.neigbord(k, j, k+1)); break;
		}
		return r;
	}
	
	@Override
	public FloydEdge<V,E> edge(ActionFloyd a) {
		return FloydEdge.of(this,this.neighbors(a), a);
	}
	
	@Override
	public Boolean isBaseCase() {
		V vi = this.vertices.get(i);
		V vj = this.vertices.get(j);
		return this.graph.containsEdge(vi,vj)  ||  k == n;
	}
	@Override
	public Double baseCaseSolution() {
		Double r = null;
		V vi = this.vertices.get(i);
		V vj = this.vertices.get(j);
		if(this.graph.containsEdge(vi, vj)){
			E e = this.graph.getEdge(vi, vj);
			Double w = this.graph.getEdgeWeight(e);
			r = w;
		}
		return r;
	}

	public static <V,E> GraphWalk<V,E> solution(
			GraphTree<FloydVertex<V,E>,FloydEdge<V,E>,ActionFloyd> tree){
		GraphWalk<V,E> gp = null;		
		if(tree.isBaseCase()) {
			V origen = tree.vertex().vertices.get(tree.vertex().i);
			V destino = tree.vertex().vertices.get(tree.vertex().j);
			List<V> ls = Lists2.of(origen,destino);
			gp = new GraphWalk<>(tree.vertex().graph,ls,tree.weight());
		} else if(tree.action() == ActionFloyd.No){
			gp = solution(tree.children().get(0));
		} else {
			GraphWalk<V,E> gp1 = solution(tree.children().get(0));
			GraphWalk<V,E> gp2 = solution(tree.children().get(1));
			gp = gp1.concat(gp2,g->EGraphPath.weight(g));
		}
		return gp;
	}

}
