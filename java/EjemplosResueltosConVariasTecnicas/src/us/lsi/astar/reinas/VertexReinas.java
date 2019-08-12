package us.lsi.astar.reinas;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Lists2;
import us.lsi.common.SetRangeInteger;
import us.lsi.common.Sets2;
import us.lsi.graphs.virtual.VirtualVertex;
import us.lsi.math.Math2;


public class VertexReinas implements VirtualVertex<VertexReinas, EdgeReinas> {
	
	public static Integer numeroDeReinas = 200;
	public static Integer resto = 10;
	
	public static VertexReinas of() {
		List<Integer> yO = Lists2.newList();
		SetRangeInteger dpO = SetRangeInteger.of(-numeroDeReinas, 10);
		SetRangeInteger dsO = SetRangeInteger.of();
		return new VertexReinas(0, yO, dpO, dsO);
	}
	
	public static VertexReinas of(Integer x, List<Integer> yO, SetRangeInteger dpO, SetRangeInteger dsO) {
		return new VertexReinas(x, yO, dpO, dsO);
	}

	Integer x;
	private List<Integer> yO;
	private SetRangeInteger dpO;
	private SetRangeInteger dsO;
//	private Set<VertexReinas> neighbors = null;
	
	private VertexReinas(Integer x,List<Integer> yO, SetRangeInteger dpO, SetRangeInteger dsO) {
		super();
		this.x = x;
		this.yO = Lists2.copy(yO);
		this.dpO = dpO.copy();
		this.dsO = dsO.copy();
	}
	

	@Override
	public boolean isValid() {
		return true;
	}
	
	@Override
	public Set<VertexReinas> getNeighborListOf() {
		Set<VertexReinas> neighbors;
//		if (neighbors == null) {
			List<Integer> ys = IntStream.range(0, numeroDeReinas).boxed().filter(y -> isApplicable(y))
					.collect(Collectors.toList());
			if (this.x < numeroDeReinas - resto && ys.size() > 1) {
				Integer index = Math2.getEnteroAleatorio(0, ys.size());
				neighbors = Sets2.newSet(neighbor(ys.get(index)));
			} else {
				neighbors = ys.stream().map(y -> neighbor(y)).collect(Collectors.toSet());
			}
//		}
		return neighbors;
	}
	
	@Override
	public Set<EdgeReinas> edgesOf() {
		return getNeighborListOf().stream()
				.map(v->EdgeReinas.of(this,v,v.yO.get(v.x-1)))
				.collect(Collectors.toSet());
	}
	
	public VertexReinas neighbor(Integer y) {
		Integer dp = y - this.x;
		Integer ds = y + this.x;
		List<Integer> yO = Lists2.copy(this.yO);
		yO.add(y);
		SetRangeInteger dpO = this.dpO.copy();
		dpO.add(dp);
		SetRangeInteger dsO = this.dsO.copy();
		dsO.add(ds);
		return VertexReinas.of(x+1, yO, dpO, dsO);
	}
	
	public boolean isApplicable(Integer y) {
		Integer dp = y - this.x;
		Integer ds = y + this.x;
		return !(this.yO.contains(y) || this.dpO.contains(dp) || this.dsO.contains(ds));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dpO == null) ? 0 : dpO.hashCode());
		result = prime * result + ((dsO == null) ? 0 : dsO.hashCode());
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((yO == null) ? 0 : yO.hashCode());
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
		VertexReinas other = (VertexReinas) obj;
		if (dpO == null) {
			if (other.dpO != null)
				return false;
		} else if (!dpO.equals(other.dpO))
			return false;
		if (dsO == null) {
			if (other.dsO != null)
				return false;
		} else if (!dsO.equals(other.dsO))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (yO == null) {
			if (other.yO != null)
				return false;
		} else if (!yO.equals(other.yO))
			return false;
		return true;
	}
	
	

}
