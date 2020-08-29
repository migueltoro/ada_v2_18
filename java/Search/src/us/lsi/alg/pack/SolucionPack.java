package us.lsi.alg.pack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import us.lsi.common.IntPair;
import us.lsi.common.Lists2;

public class SolucionPack {
	
	public static SolucionPack of(GraphPath<PackVertex, PackEdge> path) {
		return new SolucionPack(path);
	}

	public Map<Integer,List<Integer>> asignacion;

	private SolucionPack(GraphPath<PackVertex,PackEdge> path) {
		super();
		List<Integer> as = path.getVertexList().get(path.getLength()).as;
		this.asignacion = IntStream.range(0,as.size()).boxed()
				.map(i->IntPair.of(i, as.get(i)))
				.collect(Collectors.toMap(
						e->e.second,
						e->Lists2.of(e.first),
						(List<Integer> a, List<Integer> b)->Lists2.concat(a,b)));
	}
	
	
	public Integer nc() {
		return this.asignacion.keySet().size();
	}
	

	@Override
	public String toString() {
		return "SolucionPack [ nc ="+nc()+", carga ="+asignacion + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asignacion == null) ? 0 : asignacion.hashCode());
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
		SolucionPack other = (SolucionPack) obj;
		if (asignacion == null) {
			if (other.asignacion != null)
				return false;
		} else if (!asignacion.equals(other.asignacion))
			return false;
		return true;
	}
	
	

}
