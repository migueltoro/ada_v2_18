package us.lsi.astar.mochila;

import java.util.List;
import java.util.Set;
import java.util.stream.*;

import org.jgrapht.GraphPath;
import us.lsi.graphs.*;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;
import us.lsi.pd.mochila.ProblemaMochilaPD;


public class MochilaVertex implements VirtualVertex<MochilaVertex, MochilaEdge> {

	public static MochilaVertex create() {
		return new MochilaVertex(ProblemaMochilaPD.createInitial());
	}
	
	public static MochilaVertex create(ProblemaMochilaPD problema) {
		return new MochilaVertex(problema);
	}

	private ProblemaMochilaPD problema;
	
	private MochilaVertex(ProblemaMochilaPD problema) {
		super();
		this.problema = problema;
	}
	
	
	public static SolucionMochila getSolucion(GraphPath<MochilaVertex, MochilaEdge> path){
		SolucionMochila s = SolucionMochila.create();		
		List<MochilaEdge> ls = path.getEdgeList();
		ls.stream().forEach(e->s.add(
				e.getSource().getObjeto(), 
				e.getAlternativa()));
		return s;
	}

	@Override
	public Set<MochilaVertex> getNeighborListOf() {
		return problema.getAlternativas()
				.stream()
				.map(a-> this.problema.getSubProblema(a))
				.map(p->MochilaVertex.create(p))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<MochilaEdge> edgesOf() {
		return problema.getAlternativas()
				.stream()
				.map(a-> edge(a))
				.collect(Collectors.toSet());				
	}

	@Override
	public boolean isNeighbor(MochilaVertex e) {
		return this.getNeighborListOf().contains(e);
	}
	
	public MochilaVertex neighbor(Integer a) {
		ProblemaMochilaPD p = this.problema.getSubProblema(a);
		return MochilaVertex.create(p);
	}
	
	public MochilaEdge edge(Integer a) {
		MochilaVertex p = neighbor(a);
		Double w = -(double)a*DatosMochila.getValor(this.problema.getIndex());
		return MochilaEdge.create(this, p, w, a);
	}
	
	public ProblemaMochilaPD getProblema() {
		return problema;
	}

	public Integer getIndex() {
		return problema.getIndex();
	}
	
	public ObjetoMochila getObjeto() {
		return DatosMochila.getObjeto(problema.getIndex());
	}
	@Override
	public String toString() {
		return problema.toString();
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((problema == null) ? 0 : problema.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MochilaVertex))
			return false;
		MochilaVertex other = (MochilaVertex) obj;
		if (problema == null) {
			if (other.problema != null)
				return false;
		} else if (!problema.equals(other.problema))
			return false;
		return true;
	}	

}
