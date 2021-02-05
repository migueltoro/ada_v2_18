package us.lsi.alg.secuencias;

import java.util.List;

import java.util.stream.Collectors;
import us.lsi.graphs.virtual.ActionVirtualVertex;

public class SeqVertex extends ActionVirtualVertex<SeqVertex,SeqEdge,SeqAction>{
	
	public static SeqVertex of(Integer index, String s) {
		return new SeqVertex(index, s);
	}
	
	public static SeqVertex first() {
		return new SeqVertex(0, SeqVertex.s1);
	}
	
	public static SeqVertex last() {
		return new SeqVertex(SeqVertex.n2, SeqVertex.s2);
	}
	
	public static void data(String s1, String s2) {
		SeqVertex.s1 = s1;
		SeqVertex.s2 = s2;
		SeqVertex.n2 = s2.length();
	}
	
	Integer	index;
	String	s;
	Integer n; // tamaño de s, derivada
	Integer t; // derivada : n-i+n2-i, tamaño
	Integer t1; // Integer, drerivada, n-i, tamaño restante de s
	Integer t2; // integer,derivada, n2-i, tamaño restante de s2
	Integer nd; // número de caracteres diferentes en s[i:] y s2[i:]
	static String s1; //compartida
	static String s2; // compartida
//	private static Integer n1; // tamaño de s1, derivadas
	static Integer n2; // Integer, tamaño de s2, derivada
	
	
	private SeqVertex(Integer index, String s) {
		super();
		this.index = index;
		this.s = s;	
		this.n = s.length(); // tamaño de s, derivada
		this.t = n-index+n2-index; // derivada : n-i+n2-i, tamaño
		this.t1 = n-index; // Integer, drerivada, n-i, tamaño restante de s
		this.t2 = n2-index; // integer,derivada, n2-i, tamaño restante de s2
//		this.nd = (int) IntStream.range(index,Math.max(n,n2)).filter(i->getChar(s,i)!=getChar(s2,i)).count(); 
					// número de caracteres diferentes en s[i:] y s2[i:]
	}

	@Override
	public Boolean isValid() {
		return true;
	}

	@Override
	public List<SeqAction> actions() {
		return SeqAction.actions.stream().filter(a->a.isApplicable(this)).collect(Collectors.toList());
	}

	@Override
	public SeqVertex neighbor(SeqAction a) {
		return a.neighbor(this);
	}

	@Override
	public SeqEdge edge(SeqAction a) {
		return SeqEdge.of(this, this.neighbor(a), a);
	}	

	@Override
	public String toString() {
		return String.format("(%d,%s,%s)",index,s,SeqVertex.s2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((s == null) ? 0 : s.hashCode());
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
		SeqVertex other = (SeqVertex) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}
	
	
}
