package us.lsi.search.reinas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Lists2;
import us.lsi.common.Sets2;
import us.lsi.graphs.virtual.ActionVirtualVertex;

public class ReinasVertex extends ActionVirtualVertex<ReinasVertex,ReinasEdge,Integer>{
	
	public static ReinasVertex copy(ReinasVertex reinas) {
		return new ReinasVertex(reinas.index,Lists2.copy(reinas.fo),Sets2.copy(reinas.dpo),Sets2.copy(reinas.dso));
	}

	public static ReinasVertex of(Integer index, List<Integer> fo, Set<Integer> dpo, Set<Integer> dso) {
		return new ReinasVertex(index, fo, dpo, dso);
	}

	public static ReinasVertex first() {
		return new ReinasVertex(0,Lists2.empty(),new HashSet<>(),new HashSet<>());
	}
	
	public final Integer index; // numero de columna
	public final List<Integer> fo; //
	public final Set<Integer> dpo; //
	public final Set<Integer> dso; //
	public final Integer errores;
	public static Integer n; // numero de reinas
	
	
	ReinasVertex(Integer index, List<Integer> fo, Set<Integer> dpo, Set<Integer> dso) {
		super();
		this.index = index;
		this.fo = fo;
		this.dpo = dpo;
		this.dso = dso;
		this.errores = 3*ReinasVertex.n-this.fo.size()-this.dpo.size()-this.dso.size();
	}

	@Override
	public Boolean isValid() {
		return this.index >=0 && this.index <= ReinasVertex.n;
	}
	
	public Integer size() {
		return ReinasVertex.n - this.index;
	}

	@Override
	public List<Integer> actions() {
		List<Integer> r = IntStream.range(0,ReinasVertex.n).boxed()
				.filter(e->!this.fo.contains(e) && !this.dpo.contains(e+this.index) && !this.dso.contains(e-this.index))
				.collect(Collectors.toList());
		return r;
	}

	@Override
	public ReinasVertex neighbor(Integer a) {
		Integer index = this.index+1;
		List<Integer> fo = new ArrayList<>(this.fo); fo.add(a);
		Set<Integer> dpo = Sets2.copy(this.dpo); dpo.add(a+this.index);
		Set<Integer> dso = Sets2.copy(this.dso); dso.add(a-this.index);
		return ReinasVertex.of(index, fo, dpo, dso);
	}

	@Override
	public ReinasEdge edge(Integer a) {
		return ReinasEdge.of(this,this.neighbor(a), a);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dpo == null) ? 0 : dpo.hashCode());
		result = prime * result + ((dso == null) ? 0 : dso.hashCode());
		result = prime * result + ((fo == null) ? 0 : fo.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
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
		ReinasVertex other = (ReinasVertex) obj;
		if (dpo == null) {
			if (other.dpo != null)
				return false;
		} else if (!dpo.equals(other.dpo))
			return false;
		if (dso == null) {
			if (other.dso != null)
				return false;
		} else if (!dso.equals(other.dso))
			return false;
		if (fo == null) {
			if (other.fo != null)
				return false;
		} else if (!fo.equals(other.fo))
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReinasVertex [index=" + index + ", fo=" + fo + ", dpo=" + dpo + ", dso=" + dso + ", errores=" + errores
				+ "]";
	}

	
}
