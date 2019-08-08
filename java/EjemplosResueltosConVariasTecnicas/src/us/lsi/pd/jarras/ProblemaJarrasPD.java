package us.lsi.pd.jarras;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.astar.jarras.ActionJarras;
import us.lsi.astar.jarras.VertexJarras;
import us.lsi.jarras.datos.DatosJarras;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPDR;

public class ProblemaJarrasPD implements 
	ProblemaPDR<SolucionJarras, ActionJarras,ProblemaJarrasPD> {

	public static ProblemaJarrasPD objetivo;	
	
	public static ProblemaJarrasPD create() {
		return new ProblemaJarrasPD();
	}

	public static ProblemaJarrasPD create(VertexJarras v, Integer numOp) {
		return new ProblemaJarrasPD(v, numOp);
	}
	
	private Integer numOp;
	private VertexJarras vertex;
	
	private ProblemaJarrasPD() {
		super();
		this.numOp =0;
		this.vertex = VertexJarras.createOrigen();
	}

	private ProblemaJarrasPD(VertexJarras v, int numOp) {
		super();
		this.numOp = numOp;
		this.vertex = v;
	}

	public Integer getCantidadEnJ1() {
		return vertex.getCantidadEnJ1();
	}

	public Integer getCantidadEnJ2() {
		return vertex.getCantidadEnJ2();
	}

	@Override
	public AlgoritmoPD.Tipo getTipo() {
		return AlgoritmoPD.Tipo.Min;
	}

	@Override
	public int size() {
		return DatosJarras.numMaxAcciones-this.numOp;
	}

	@Override
	public boolean esCasoBase() {
		return (DatosJarras.cantidadFinalEnJarra1.equals(this.getCantidadEnJ1())
				&& DatosJarras.cantidadFinalEnJarra2.equals(this.getCantidadEnJ2()));
	}

	@Override
	public boolean estaFueraDeRango() {	
		return this.numOp > DatosJarras.numMaxAcciones;
	}
	
	@Override
	public Sp<ActionJarras> getSolucionParcialCasoBase() {
		return  Sp.create(null, 0.);
	}

	@Override
	public ProblemaJarrasPD getSubProblema(ActionJarras a) {
		var r = a.neighbor(this.vertex);
	    return ProblemaJarrasPD.create(r,this.numOp+1);
	}

	@Override
	public Sp<ActionJarras> getSolucionParcialPorAlternativa(ActionJarras a, Sp<ActionJarras> r) {
		return Sp.create(a, r.valorDeObjetivo+1);
	}

	@Override
	public List<ActionJarras> getAlternativas() {
		if(this.esCasoBase()|| this.estaFueraDeRango()) return null;
		return IntStream.range(0,DatosJarras.getAcciones().size())
				.mapToObj(id->ActionJarras.create(id))
				.filter(x->x.isApplicable(this.vertex))
				.collect(Collectors.toList());
	}

	@Override
	public SolucionJarras getSolucionReconstruidaCasoBase(Sp<ActionJarras> sp) {
		return SolucionJarras.create();
	}

	@Override
	public SolucionJarras getSolucionReconstruidaCasoRecursivo(Sp<ActionJarras> sp, SolucionJarras r) {
		r.addFirst(sp.alternativa);
		return r;
	}

	@Override
	public Double getObjetivoEstimado(ActionJarras a) {
		return (double)this.numOp+1;
	}

	@Override
	public Double getObjetivo() {
		Double r = null;		
		if (esCasoBase()) {
			r = (double) this.numOp;
		} 
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numOp == null) ? 0 : numOp.hashCode());
		result = prime * result + ((vertex == null) ? 0 : vertex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProblemaJarrasPD))
			return false;
		ProblemaJarrasPD other = (ProblemaJarrasPD) obj;
		if (numOp == null) {
			if (other.numOp != null)
				return false;
		} else if (!numOp.equals(other.numOp))
			return false;
		if (vertex == null) {
			if (other.vertex != null)
				return false;
		} else if (!vertex.equals(other.vertex))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProblemaJarrasPD [numOp=" + numOp + ", vertex=" + vertex + "]";
	}
	
	
	
}
