package us.lsi.alg.pack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.graphs.virtual.ActionVirtualVertex;

public class PackVertex extends ActionVirtualVertex<PackVertex,PackEdge,Integer> {


	public static PackVertex of(Integer index, List<Integer> as, Map<Integer, Integer> carga) {
		return new PackVertex(index, as, carga);
	}

	public final Integer index;
	public final List<Integer> as;
	public final Map<Integer,Integer> carga;
	public final List<Integer> sort_carga;
	public final Integer cmax;
	public final Integer cmin;
	public final Integer nc;
	public static Integer n = Data.n;
	public static Integer m = Data.m;
	public static final Predicate<PackVertex> goal = v->v.index == n;
	public static final PackVertex first = PackVertex.of(0, new ArrayList<>(), new HashMap<>());

	private PackVertex(Integer index, List<Integer> as, Map<Integer, Integer> carga) {
		super();
		this.index = index;
		this.as = as;
		this.carga = carga;
		this.sort_carga = IntStream.range(0,n).boxed().map(i->carga.getOrDefault(i,0)).collect(Collectors.toList());
		this.nc = this.carga.keySet().size();
		this.cmax = this.carga.isEmpty()?0:this.carga.entrySet().stream().max(Comparator.comparing(e->e.getValue())).get().getKey();
		this.cmin = this.carga.isEmpty()?0:this.carga.entrySet().stream().min(Comparator.comparing(e->e.getValue())).get().getKey();
//		System.out.println(this);
	}

	private Integer volumen(Integer i) {
		return Data.volumenesObjetos.get(i);
	}
	
	private Integer volumenContenedor() {
		return Data.volumenContenedor;
	}
	
	@Override
	public Boolean isValid() {		
		return this.index>=0 && this.index<=PackVertex.n && this.carga.getOrDefault(index,0) <= volumenContenedor();
	}

	@Override
	public List<Integer> actions() {
		if(this.index==n) return new ArrayList<>();
		List<Integer> r =  IntStream.range(0,PackVertex.m).boxed()
				.filter(c->carga.getOrDefault(c,0)+volumen(index)<= volumenContenedor())
				.collect(Collectors.toList());
		Collections.sort(r,Comparator.comparing(c->-carga.getOrDefault(c,0)));
		return r;
	}

	@Override
	public PackVertex neighbor(Integer a) {
		List<Integer> ls = new ArrayList<>(this.as);
		ls.add(a);
		Map<Integer,Integer> carga = new HashMap<>(this.carga);
		carga.put(a,this.carga.getOrDefault(a,0)+volumen(index));
		PackVertex r= PackVertex.of(this.index+1,ls,carga);
//		System.out.println(r);
		return r;
	}

	@Override
	public PackEdge edge(Integer a) {
		return PackEdge.of(this,neighbor(a),a);
	}
	
	public PackEdge greedyEdge() {
		Integer a = IntStream.range(0,PackVertex.m).boxed()
				.filter(c->carga.getOrDefault(c,0)+volumen(index)<= volumenContenedor())
				.max(Comparator.comparing(c->carga.getOrDefault(c,0)))
				.orElseGet(()->0);
		return PackEdge.of(this,this.neighbor(a),a);
	}
	
	public PackVertex copy() {
		List<Integer> as = new ArrayList<>(this.as);
		Map<Integer, Integer> carga = new HashMap<>(this.carga);
		return PackVertex.of(this.index, as, carga);
	}

	@Override
	public String toString() {
		return "PackVertex [index=" + index + ", as=" + as + ", carga=" + carga + ", cmax=" + cmax + ", cmin=" + cmin
				+ ", nc=" + nc + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((sort_carga == null) ? 0 : sort_carga.hashCode());
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
		PackVertex other = (PackVertex) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (sort_carga == null) {
			if (other.sort_carga != null)
				return false;
		} else if (!sort_carga.equals(other.sort_carga))
			return false;
		return true;
	}

	
	
	

}
