package us.lsi.alg.jarras;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.graphs.virtual.ActionVirtualVertex;

public class JarrasVertex extends ActionVirtualVertex<JarrasVertex,JarrasEdge,JarrasAction> {

	public static void data(
			Integer cantidadFinalEnJarra1,
			Integer cantidadFinalEnJarra2,
			Integer cantidadInicialEnJarra1,
			Integer cantidadInicialEnJarra2,
			Integer capacidadJarra1,
			Integer capacidadJarra2) {
		JarrasVertex.cF1 = cantidadFinalEnJarra1;
		JarrasVertex.cF2 = cantidadFinalEnJarra2;
		JarrasVertex.cI1 = cantidadInicialEnJarra1;
		JarrasVertex.cI2 = cantidadInicialEnJarra2;
		JarrasVertex.cP1 = capacidadJarra1;
		JarrasVertex.cP2 = capacidadJarra2;
	}
	
	
	public static JarrasVertex of(Integer cJ1, Integer cJ2) {
		return new JarrasVertex(cJ1, cJ2);
	}
	
	public static JarrasVertex first() {
		return new JarrasVertex(JarrasVertex.cI1,JarrasVertex.cI2);
	}
	
	public static JarrasVertex last() {
		return new JarrasVertex(JarrasVertex.cF1,JarrasVertex.cF2);
	}

	final Integer c1;
	final Integer c2;
	static Integer cF1;
	static Integer cF2;
	static Integer cI1;
	static Integer cI2;
	static Integer cP1;
	static Integer cP2;
	
	private JarrasVertex(Integer cJ1, Integer cJ2) {
		super();
		this.c1 = cJ1;
		this.c2 = cJ2;
	}

	@Override
	public Boolean isValid() {
		return this.c1 >=0 && this.c1 <= JarrasVertex.cP1 && 
				this.c2 >=0 && this.c2 <= JarrasVertex.cP2;
	}

	@Override
	public List<JarrasAction> actions() {
		return JarrasAction.actions.stream().filter(a->a.isApplicable(this)).collect(Collectors.toList());
	}

	@Override
	public JarrasVertex neighbor(JarrasAction a) {
		return a.neighbor(this);
	}

	@Override
	public JarrasEdge edge(JarrasAction a) {
		return JarrasEdge.of(this,a.neighbor(this),a);
	}

	@Override
	public String toString() {
		return String.format("(%d,%d)",c1,c2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
		result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
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
		JarrasVertex other = (JarrasVertex) obj;
		if (c1 == null) {
			if (other.c1 != null)
				return false;
		} else if (!c1.equals(other.c1))
			return false;
		if (c2 == null) {
			if (other.c2 != null)
				return false;
		} else if (!c2.equals(other.c2))
			return false;
		return true;
	}
	
	
	
}
