package us.lsi.astar.puzzle;

import java.util.*;
import java.util.stream.Collectors;





import us.lsi.common.*;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.VirtualVertex;

/**
 * Estado de un Puzzle como implementación de VirtualVertex
 * 
 * 
 * @author Miguel Toro
 *
 */
public class EstadoPuzzle implements VirtualVertex<EstadoPuzzle,SimpleEdge<EstadoPuzzle>> {
	
	public static EstadoPuzzle create(ProblemaPuzzle problema) {
		return new EstadoPuzzle(problema);
	}

	public static EstadoPuzzle create(Integer... d) {
		return new EstadoPuzzle(ProblemaPuzzle.create(d));
	}
	
	public static EstadoPuzzle create(Integer[][] datos, int i0, int j0) {
		return new EstadoPuzzle(ProblemaPuzzle.create(datos,i0,j0));
	}
	
	private ProblemaPuzzle problema;
		
	
	private EstadoPuzzle(ProblemaPuzzle problema) {
		super();
		this.problema = problema;
	}

	
	public ProblemaPuzzle getProblema() {
		return problema;
	}


	@Override
	public boolean isValid() {
		return problema.isValid();
	}
	
	@Override
	public Set<EstadoPuzzle> getNeighborListOf() {
		var ls = Lists2.newList(Tuple.create(1, 0),
				Tuple.create(0, 1), Tuple.create(-1, 0),
				Tuple.create(0, -1));
		return ls
				.stream()
				.filter(x -> x.v1 + problema.getI0() >= 0
						&& x.v1 + problema.getI0() < ProblemaPuzzle.numFilas
						&& x.v2 + problema.getJ0() >= 0
						&& x.v2 + problema.getJ0() < ProblemaPuzzle.numFilas)
				.map(x -> EstadoPuzzle.create(problema.getVecino(x.v1, x.v2)))
				.collect(Collectors.<EstadoPuzzle> toSet());
	}
	
	public Set<SimpleEdge<EstadoPuzzle>> edgesOf(){
		return this.getNeighborListOf().stream()
		   .map(x->SimpleEdge.create(this,x))
		   .collect(Collectors.<SimpleEdge<EstadoPuzzle>>toSet());
	}

	Integer getNumDiferentes(EstadoPuzzle e){
		return problema.getNumDiferentes(e.problema);
	}

	@Override
	public String toString() {
		return problema.toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((problema == null) ? 0 : problema.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EstadoPuzzle))
			return false;
		EstadoPuzzle other = (EstadoPuzzle) obj;
		if (problema == null) {
			if (other.problema != null)
				return false;
		} else if (!problema.equals(other.problema))
			return false;
		return true;
	}
	
	
}
