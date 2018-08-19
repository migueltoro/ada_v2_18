package us.lsi.astar.mochila;

import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleVirtualGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.pd.mochila.ProblemaMochilaPD;


public class MochilaGrafo extends
		SimpleVirtualGraph<MochilaVertex, MochilaEdge>
		implements
		AStarGraph<MochilaVertex, MochilaEdge> {

	
	
	public static MochilaGrafo create(MochilaVertex... v) {
		return new MochilaGrafo(v);
	}


	private MochilaGrafo(MochilaVertex... v) {
		super(v);
	}


	@Override
	public double getWeightToEnd(MochilaVertex startVertex, MochilaVertex endVertex) {
		ProblemaMochilaPD actual = startVertex.getProblema();		
		return -DatosMochila.getCotaSuperior(actual.getIndex(), 
				actual.getCapacidadRestante());
	}

	@Override
	public double getEdgeWeight(MochilaEdge e){		
		return e.getEdgeWeight();
	}
}
