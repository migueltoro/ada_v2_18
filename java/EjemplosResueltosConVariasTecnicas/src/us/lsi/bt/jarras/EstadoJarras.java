package us.lsi.bt.jarras;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.astar.jarras.ActionJarras;
import us.lsi.astar.jarras.VertexJarras;
import us.lsi.bt.EstadoBT;
import us.lsi.jarras.datos.DatosJarras;

public class EstadoJarras implements 
	EstadoBT<SolucionJarras, ActionJarras, EstadoJarras> {
	
	public static EstadoJarras create() {
		return new EstadoJarras();
	}

	private SolucionJarras sol;
	private VertexJarras vertex;
	
	private EstadoJarras() {
		super();
		this.sol = SolucionJarras.create();
		this.vertex = VertexJarras.createOrigen();
	}
	
	public Integer getJ1() {
		return vertex.getCantidadEnJ1();
	}

	public Integer getJ2() {
		return vertex.getCantidadEnJ2();
	}

	public SolucionJarras getSol() {
		return sol;
	}

	public VertexJarras getVertex() {
		return vertex;
	}

	@Override
	public Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public EstadoJarras getEstadoInicial() {
		return create();
	}
	
	@Override
	public List<ActionJarras> getAlternativas() {
		return IntStream.range(0,DatosJarras.getAcciones().size())
				.mapToObj(id->ActionJarras.create(id))
				.filter(x->x.isApplicable(this.vertex))
				.collect(Collectors.toList());
	}

	
	@Override
	public EstadoJarras avanza(ActionJarras a) {
		VertexJarras vertex = a.neighbor(this.vertex);
		this.sol.add(a,this.vertex);
		this.vertex = vertex;
		return this;
	}

	@Override
	public EstadoJarras retrocede(ActionJarras a) {
		this.vertex = this.sol.getLastVertex();
		this.sol.removeLast();	
		return this;
	}

	@Override
	public int size() {
		return DatosJarras.numMaxAcciones-this.sol.getNumAcc();
	}


	@Override
	public boolean esCasoBase() {
		return DatosJarras.cantidadFinalEnJarra1.equals(this.getJ1())
				&& DatosJarras.cantidadFinalEnJarra2.equals(this.getJ2());
	}
	
	@Override
	public boolean estaFueraDeRango(){
		return this.sol.getNumAcc() > DatosJarras.numMaxAcciones;
	}

	@Override
	public SolucionJarras getSolucion() {
		return this.sol.copy();
	}	

	@Override
	public Double getObjetivoEstimado(ActionJarras a) {
		return (double) this.sol.getNumAcc();
	}

	@Override
	public String toString() {
		return "[J1=" + getJ1() + ",J2="
				+ getJ2() + "]";
	}

	

}
