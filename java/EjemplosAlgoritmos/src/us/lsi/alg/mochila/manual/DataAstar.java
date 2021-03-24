package us.lsi.alg.mochila.manual;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaVertex;

public class DataAstar {
	
	public static DataAstar of(MochilaVertex vertex, MochilaEdge edge, Double distance) {
		return new DataAstar(vertex, edge, distance);
	}
	
	
	public static DataAstar of(MochilaVertex vertex, MochilaEdge edge) {
		return new DataAstar(vertex, edge, 0.);
	}
	
	
	public static DataAstar of(MochilaVertex vertex) {
		return new DataAstar(vertex, null, 0.);
	}

	public MochilaVertex vertex;
	public MochilaEdge edge;
	public Double distanceToOrigin;
	
	private DataAstar(MochilaVertex vertex, MochilaEdge edge, Double distance) {
		super();
		this.vertex = vertex;
		this.edge = edge;
		this.distanceToOrigin = distance;
	}


	@Override
	public String toString() {
		return "DataAstar [vertex=" + vertex + ", edge=" + edge + ", distanceToOrigin=" + distanceToOrigin+ "]";
	}
	
	
	
}
