package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.List;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.common.Lists2;
import us.lsi.mochila.datos.SolucionMochila;

public class EstadoMochila {
	
	public static EstadoMochila initial(Integer capacidadInicial) {
		MochilaVertex.capacidadInicial = capacidadInicial;
		return new EstadoMochila(MochilaVertex.initialVertex(), 0, new ArrayList<>());
	}

	public static EstadoMochila of(MochilaVertex vertex, Integer valorAcumulado, List<Integer> alternativas) {
		return new EstadoMochila(vertex, valorAcumulado, alternativas);
	}

	public MochilaVertex vertex;
	public Integer valorAcumulado;
	public List<Integer> alternativas;

	private EstadoMochila(MochilaVertex vertex, Integer valorAcumulado, List<Integer> alternativas) {
		super();
		this.vertex = vertex;
		this.valorAcumulado = valorAcumulado;
		this.alternativas = alternativas;
	}

	public void forward(Integer a) {
		MochilaVertex old = this.vertex;
		this.vertex = this.vertex.neighbor(a);
		this.alternativas.add(a);
		this.valorAcumulado += MochilaEdge.of(old,this.vertex, a).weight.intValue();
	}
	
	public void back(Integer a) {
		MochilaVertex old = this.vertex;
		Integer index = this.vertex.index;
		Integer capacidadRestante = this.vertex.capacidadRestante;
		this.vertex = MochilaVertex.of(index, capacidadRestante);
		Lists2.removeLast(this.alternativas);
		this.valorAcumulado -= MochilaEdge.of(this.vertex,old, a).weight.intValue();
	}
	
	public SolucionMochila solucion() {
		List<MochilaEdge> edges = new ArrayList<>();
		MochilaVertex v = MochilaVertex.initialVertex();
		MochilaVertex vn;
		for(Integer a:this.alternativas) {
			vn = v.neighbor(a);
			MochilaEdge e= MochilaEdge.of(v, vn, a);
			edges.add(e);
			v = vn;
		}
		return MochilaVertex.getSolucion(edges);
	}

}
