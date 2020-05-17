package us.lsi.astar.reinas;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Lists2;
import us.lsi.common.SetRangeInteger;
import us.lsi.graphs.virtual.ActionVirtualVertex;


public class VertexReinas extends ActionVirtualVertex<VertexReinas, EdgeReinas, ActionReinas> {
	
	public static Integer numeroDeReinas = 200;
	public static Integer resto = 10;
	
	public static VertexReinas of() {
		List<Integer> yO = Lists2.empty();
		SetRangeInteger dpO = SetRangeInteger.of(-numeroDeReinas, 10);
		SetRangeInteger dsO = SetRangeInteger.of();
		return new VertexReinas(0, yO, dpO, dsO);
	}
	
	public static VertexReinas of(Integer x, List<Integer> yO, SetRangeInteger dpO, SetRangeInteger dsO) {
		return new VertexReinas(x, yO, dpO, dsO);
	}

	Integer x;
	List<Integer> yO;
	SetRangeInteger dpO;
	SetRangeInteger dsO;
	
	private VertexReinas(Integer x,List<Integer> yO, SetRangeInteger dpO, SetRangeInteger dsO) {
		super();
		this.x = x;
		this.yO = Lists2.copy(yO);
		this.dpO = dpO.copy();
		this.dsO = dsO.copy();
	}
	

	@Override
	public Boolean isValid() {
		return true;
	}
	
	@Override
	public List<ActionReinas> actions() {
		return IntStream.range(0,VertexReinas.numeroDeReinas)
				.boxed()
				.map(e->ActionReinas.of(e))
				.filter(a->a.isApplicable(this))
				.collect(Collectors.toList());
	}

	@Override
	public VertexReinas neighbor(ActionReinas a) {
		return a.neighbor(this);
	}
	
	@Override
	public EdgeReinas edge(ActionReinas a) {
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
