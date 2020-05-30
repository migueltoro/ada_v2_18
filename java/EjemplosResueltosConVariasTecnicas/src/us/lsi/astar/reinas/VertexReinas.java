package us.lsi.astar.reinas;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Lists2;
import us.lsi.common.RangeIntegerSet;
import us.lsi.graphs.virtual.ActionVirtualVertex;


public class VertexReinas extends ActionVirtualVertex<VertexReinas, EdgeReinas, Integer> {
	
	public static Integer numeroDeReinas = 200;
	public static Integer resto = 10;
	
	public static VertexReinas of() {
		List<Integer> yO = Lists2.empty();
		RangeIntegerSet dpO = RangeIntegerSet.of(-numeroDeReinas, 10);
		RangeIntegerSet dsO = RangeIntegerSet.of();
		return new VertexReinas(0, yO, dpO, dsO);
	}
	
	public static VertexReinas of(Integer x, List<Integer> yO, RangeIntegerSet dpO, RangeIntegerSet dsO) {
		return new VertexReinas(x, yO, dpO, dsO);
	}

	Integer x;
	List<Integer> yO;
	RangeIntegerSet dpO;
	RangeIntegerSet dsO;
	
	private VertexReinas(Integer x,List<Integer> yO, RangeIntegerSet dpO, RangeIntegerSet dsO) {
		super();
		this.x = x;
		this.yO = yO;
		this.dpO = dpO;
		this.dsO = dsO;
	}
	

	@Override
	public Boolean isValid() {
		return true;
	}
	
	@Override
	public List<Integer> actions() {
		return IntStream.range(0,VertexReinas.numeroDeReinas)
				.boxed()
				.filter(y-> !this.yO.contains(y) && !this.dpO.contains(y-this.x) && !this.dsO.contains(y+this.x))
				.collect(Collectors.toList());
	}

	@Override
	public VertexReinas neighbor(Integer y) {
		List<Integer> yO = Lists2.copy(this.yO);
		yO.add(y);
		RangeIntegerSet dpO = this.dpO.copy();
		dpO.add(y+this.x);
		RangeIntegerSet dsO = this.dsO.copy();
		dpO.add(y-this.x);
		return VertexReinas.of(this.x+1,yO,dpO,dsO);
	}
	
	@Override
	public EdgeReinas edge(Integer a) {
		VertexReinas v = this.neighbor(a);
		return EdgeReinas.of(this,v,a);
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
