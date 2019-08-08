package us.lsi.pd.floyd;

import java.util.Arrays;
import java.util.List;

import org.jgrapht.graph.GraphWalk;

import us.lsi.graphs.views.IntegerMappingGraphView;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;

public class FloydPD<V, E> implements ProblemaPD<GraphWalk<Integer, E>, FloydPD.Alternativa, FloydPD<V, E>> {

	
	private static <V1,E1> Double walkWeightCalculator(GraphWalk<V1,E1> path) {
		return path.getEdgeList().stream().mapToDouble(x->path.getGraph().getEdgeWeight(x)).sum();
	}
	
	public static enum Alternativa{Yes, No};
	
	public static <V, E> FloydPD<V, E> create(Integer i, Integer j, IntegerMappingGraphView<V, E> graph) {
		return new FloydPD<V, E>(i, j, 0, graph);
	}
	public static <V, E> FloydPD<V, E> create(int i, int j, int k, IntegerMappingGraphView<V, E> graph) {
		return new FloydPD<V, E>(i, j, k, graph);
	}
	
	private int i;
	private int j;
	private int k;
	private IntegerMappingGraphView<V,E> grafo;
	private int n;
	
	
	private FloydPD(int i, int j, int k, IntegerMappingGraphView<V, E> grafo) {
		super();
		this.i = i;
		this.j = j;
		this.k = k;
		this.grafo = grafo;
		this.n= grafo.vertexSet().size();
	}
	
	@Override
	public AlgoritmoPD.Tipo getTipo() {
		return AlgoritmoPD.Tipo.Min;
	}
	
	@Override
	public int size() {
		return 0;
	}
	@Override
	public boolean esCasoBase() {
			return grafo.containsEdge(i,j)  ||  k == n;
	}
	@Override
	public Sp<Alternativa> getSolucionParcialCasoBase() {
		Sp<Alternativa> r = null;
		if(grafo.containsEdge(i, j)){
			E e = grafo.getEdge(i, j);
			Double w = grafo.getEdgeWeight(e);
			r = Sp.<Alternativa>create(null,w);
		}
		return r;
	}
	
	@Override
	public FloydPD<V, E> getSubProblema(Alternativa a, int np) {
		FloydPD<V, E> r=null;
		switch(a){
		case No : r = FloydPD.create(i, j, k+1,grafo); break;
		case Yes : 
			switch(np){
				case 0 : r = FloydPD.create(i, k, k+1,grafo); break;
				case 1 : r = FloydPD.create(k, j, k+1,grafo); break;
				default : throw new IllegalArgumentException("El número del subproblema no puede ser "+np);
			}
		}
		return r;
	}
	@Override
	public Sp<Alternativa> getSolucionParcialPorAlternativa(Alternativa a,List<Sp<Alternativa>> ls) {
		Double r = null;
		Sp<Alternativa> r0  = ls.get(0);
		switch(a){
		case No : r = r0.valorDeObjetivo; break;
		case Yes : 
			Sp<Alternativa> r1  = ls.get(1);
			r = r0.valorDeObjetivo+r1.valorDeObjetivo;
		}
		return Sp.create(a, r);
	}
	@Override
	public List<Alternativa> getAlternativas() {
		return Arrays.asList(Alternativa.values());
	}
	@Override
	public int getNumeroSubProblemas(Alternativa a) {
		return a.equals(Alternativa.No)?1:2;
	}
	
	@Override
	public GraphWalk<Integer, E> getSolucionReconstruidaCasoBase(Sp<Alternativa> sp) {
		return new GraphWalk<>(grafo, Arrays.<Integer>asList(i, j), grafo.getEdgeWeight(i, j));
	}
		
	@Override
	public GraphWalk<Integer, E> getSolucionReconstruidaCasoRecursivo(Sp<Alternativa> sp, List<GraphWalk<Integer, E>> ls) {
		GraphWalk<Integer, E> r = null;
		switch(sp.alternativa){
		case No: r = ls.get(0); break;
		case Yes: r = ls.get(0).concat(ls.get(1),FloydPD::walkWeightCalculator); break;	
		}		
		return r;
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
		if (!(obj instanceof FloydPD))
			return false;
		FloydPD<?,?> other = (FloydPD<?,?>) obj;
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
}
