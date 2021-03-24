package us.lsi.alg.typ;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import us.lsi.common.IntPair;
import us.lsi.common.Lists2;
import us.lsi.graphs.virtual.ActionSimpleEdge;
import us.lsi.graphs.virtual.ActionVirtualVertex;
import us.lsi.tareasyprocesadores.datos.Tarea;

public class TyPVertex extends ActionVirtualVertex<TyPVertex, ActionSimpleEdge<TyPVertex,Integer>, Integer>{

	
	public static Integer numeroDeProcesadores;
	public static Integer numeroDeTareas;
	public static TyPVertex inicial;
	
	public static void datos(String fichero, Integer np) {
		Tarea.leeTareas(fichero);
		numeroDeProcesadores = np;
		numeroDeTareas = Tarea.tareas.size();
		n = numeroDeTareas;
		m = numeroDeProcesadores;
	}
	
	public static TyPVertex first() {
		return new TyPVertex(0,Lists2.copy(0.,m));
	}
	
	public static TyPVertex last() {
		n = numeroDeTareas;
		m = numeroDeProcesadores;
		return new TyPVertex(n,Lists2.copy(0.,m));
	}

	private Integer index;
	private List<Double> cargas;
	private List<Double> sort_cargas;
	private Double maxCarga;
	private Integer npMax;
	private Integer npMin;
	public static Integer n;
	public static Integer m;
	
	private TyPVertex(Integer index, List<Double> cargas) {
		super();
		this.index = index;
		this.cargas = Lists2.copy(cargas);
		this.npMax = IntStream.range(0,m)
				.boxed()
				.max(Comparator.comparing(i->this.cargas.get(i)))
				.get();
		this.npMin = IntStream.range(0,m)
				.boxed()
				.min(Comparator.comparing(i->this.cargas.get(i)))
				.get();	
		this.maxCarga = this.cargas.get(this.npMax);			
	}
	
	public Integer getIndex() {
		return index;
	}
	
	public List<Double> getCargas() {
		return Lists2.copy(cargas);
	}

	public Double getMaxCarga() {
		return maxCarga;
	}

	public Integer getNpMax() {
		return npMax;
	}

	public Integer getNpMin() {
		return npMin;
	}

	@Override
	public Boolean isValid() {
		return true;
	}

	@Override
	public List<Integer> actions() {
		if(this.index == TyPVertex.n) return Lists2.of();
		return Lists2.rangeList(0,m);
	}
	
	public Integer greadyAction() {
		return this.npMin;
	}
	
	public  ActionSimpleEdge<TyPVertex, Integer> greadyEdge() {
		return this.edge(this.greadyAction());
	}

	@Override
	public TyPVertex neighbor(Integer a) {
		List<Double> nc = this.getCargas();
		Double d = Tarea.getDuracion(this.index) + nc.get(a); 
		nc.set(a,d);
		TyPVertex v = new TyPVertex(index+1, nc);
//		System.out.println(v);
		return v;
	}
	
	@Override
	public ActionSimpleEdge<TyPVertex, Integer> edge(Integer a) {
		return ActionSimpleEdge.of(this,this.neighbor(a), a);
	}
	
	public static TyPVertex copy(TyPVertex vertex) {
		return new TyPVertex(vertex.index,vertex.cargas);
	}
	
	
	public static SolucionTyP getSolucion(GraphPath<TyPVertex, ActionSimpleEdge<TyPVertex,Integer>> path){	
		Map<Integer,List<Tarea>> carga = path.getEdgeList().stream()
				.map(e->IntPair.of(e.action,e.source.getIndex()))
				.collect(Collectors.groupingBy(p->p.first,Collectors.mapping(p->Tarea.getTarea(p.second), Collectors.toList())));
		
		TyPVertex v = Lists2.last(path.getVertexList());
		return SolucionTyP.of(carga, v.maxCarga, v.npMin, v.npMax);
	}

	@Override
	public String toString() {
		return String.format("(%d,%s,%.2f,%d)",index,cargas,maxCarga,npMax);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cargas == null) ? 0 : cargas.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
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
		TyPVertex other = (TyPVertex) obj;
		if (cargas == null) {
			if (other.cargas != null)
				return false;
		} else if (!cargas.equals(other.cargas))
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		return true;
	}

	
	

}
