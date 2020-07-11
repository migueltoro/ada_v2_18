package us.lsi.alg.monedas;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Multiset;


public class SolucionMonedas {

	private Multiset<Moneda> s;
	private Integer valor;
	private Integer peso;
	
	public static SolucionMonedas of(List<MonedasEdge> edges) {
		Multiset<Moneda> s = Multiset.empty();
		edges.stream().forEach(e->s.add(Moneda.monedas.get(e.source.index), e.action));
		return new SolucionMonedas(s);
	}

	public SolucionMonedas(Multiset<Moneda> s) {
		super();
		this.s = s;
		this.valor = s.elementSet().stream().mapToInt(m->m.valor*s.count(m)).sum();
		this.peso = s.elementSet().stream().mapToInt(m->m.peso*s.count(m)).sum();
	}

	
	
	@Override
	public String toString() {
		return String.format("%d,%d == %s",
				this.valor,this.peso,s.elementSet().stream()
				.filter(m->s.count(m)>0)
				.map(m->String.format("%s:%d",m,s.count(m)))
				.collect(Collectors.joining(",","{","}")));
	}

}
