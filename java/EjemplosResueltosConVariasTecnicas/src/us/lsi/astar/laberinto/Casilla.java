package us.lsi.astar.laberinto;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.ActionVirtualVertex;
import us.lsi.graphs.virtual.VirtualVertex;



public class Casilla extends ActionVirtualVertex<Casilla,CasillaEdge,IntPair> implements VirtualVertex<Casilla,CasillaEdge>{
	
	private IntPair position;
	private Integer info;
	
	public static Casilla of(IntPair position,Integer info) {
		return new Casilla(position,info);
	}
	
	private Casilla(IntPair position, Integer info) {
		super();
		this.position = position;
		this.info = info;
	}
	
	public Integer getFila() {
		return this.position.a;
	}
	
	public Integer getColumna() {
		return this.position.b;
	}
	
	public IntPair getPosition() {
		return position;
	}
	
	public Integer getInfo() {
		return info;
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)",this.position,this.info);
	}

	@Override
	public Boolean isNeighbor(Casilla e) {
		return Math.abs(this.getFila()-e.getFila()) + Math.abs(this.getColumna()-e.getColumna()) == 1;
	}
	
	@Override
	public Boolean isValid() {
		return  Laberinto.casillas.containsKey(this.position) && this.getInfo()>=0;
	}
	
	@Override
	public CasillaEdge getEdgeFromAction(IntPair a) {
		Casilla v = this.neighbor(a);
		return CasillaEdge.of(this,v,a);
	}
	
	@Override
	protected List<IntPair> actions() {
		return Laberinto.actions.stream()
				.filter(a->Laberinto.casillas.keySet().contains(this.position.add(a)))
				.filter(a->Laberinto.get(this.position.add(a)).isValid())
				.collect(Collectors.toList());
	}

	@Override
	protected Casilla neighbor(IntPair a) {
		return Laberinto.get(this.position.add(a));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		Casilla other = (Casilla) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
	
	
}
