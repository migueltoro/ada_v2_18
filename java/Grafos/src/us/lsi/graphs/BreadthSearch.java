package us.lsi.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.traverse.BreadthFirstIterator;

import us.lsi.common.Preconditions;
import us.lsi.flujossecuenciales.Iterators;

public class BreadthSearch<V,E> extends BreadthFirstIterator<V,E> {

	
	public static <V, E> BreadthSearch<V, E> of(Graph<V, E> g, V startVertex) {
		return new BreadthSearch<V, E>(g, startVertex);
	}
	
	public static <V, E> BreadthSearch<V, E> of(Graph<V, E> g, Iterable<V> startVertices) {
		return new BreadthSearch<V, E>(g, startVertices);
	}

	private BreadthSearch(Graph<V, E> g, Iterable<V> startVertices) {
		super(g, startVertices);
	}

	private BreadthSearch(Graph<V, E> g, V startVertex) {
		super(g, startVertex);
	}

	/**
	 * @pre El flujo no puede haber sido consumido
	 * @return Un flujo con los v&eacute;rtices del grafo recorridos en anchura
	 */
	public Stream<V> stream(){
		Preconditions.checkArgument(this.hasNext(), "El flujo ha sido consumido");
		return Iterators.asStream(this);
	}
	/**
	 * @pre El flujo no puede haber sido consumido
	 * @param n La distancia al origen
	 * @return Un flujo con los v&eacute;rtices del grafo, que distan menos o igual que n al origen, recorridos en anchura
	 */
	public Stream<V> stream(Integer n){
		Preconditions.checkArgument(this.hasNext(), "El flujo ha sido consumido");
		return Iterators.asStream(this).takeWhile(v->super.getDepth(v) <=n);
	}
	
	/**
	 * @pre El flujo no puede haber sido consumido
	 * @param n La distancia al origen
	 * @return Un flujo con los v&eacute;rtices del grafo que distan n al origen
	 */
	public Stream<V> level(Integer n){
		Preconditions.checkArgument(this.hasNext(), "El flujo ha sido consumido");
		return this.stream(n).filter(v->this.getDepth(v) == n);
	}
	
	/**
	 * @pre El v&eacute;rtice v tiene que haber sido visitado
	 * @param v Un v&eacute;rtice
	 * @return El camino m&aacute; corto en pasos hacia el origen 
	 */
	public GraphPath<V,E> path(V v){
		Preconditions.checkArgument(this.isSeenVertex(v), String.format("El vertice %s no ha sido visitado", v.toString()));
		E edge = this.getSpanningTreeEdge(v);
		List<V> path = new ArrayList<>();
		path.add(v);
		while(edge!=null) {			
			v = this.getParent(v);	
			path.add(v);
			edge = this.getSpanningTreeEdge(v);				
		}
		Collections.reverse(path);
		return new GraphWalk<V,E>(this.getGraph(),path,path.size());
	}
	
	@Override
	public boolean isSeenVertex(V v) {
		return super.isSeenVertex(v);
	}
	
	@Override
	public int getDepth(V v) {
		Preconditions.checkArgument(super.isSeenVertex(v), String.format("El vertice %s no ha sido visitado", v.toString()));
		return super.getDepth(v);
	}
	
}
