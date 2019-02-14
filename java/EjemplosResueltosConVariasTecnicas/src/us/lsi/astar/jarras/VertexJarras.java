package us.lsi.astar.jarras;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.graphs.ActionVirtualVertex;
import us.lsi.jarras.datos.DatosJarras;

public class VertexJarras extends ActionVirtualVertex<VertexJarras, EdgeJarras, ActionJarras> {
	
	public static VertexJarras create(Integer cantidadEnJ1, Integer cantidadEnJ2) {
		return new VertexJarras(cantidadEnJ1, cantidadEnJ2);
	}

	public static VertexJarras createOrigen() {
		return new VertexJarras(DatosJarras.cantidadInicialEnJarra1, DatosJarras.cantidadInicialEnJarra2);
	}
	
	public static VertexJarras createDestino() {
		return new VertexJarras(DatosJarras.cantidadFinalEnJarra1, DatosJarras.cantidadFinalEnJarra2);
	}
	
	private static List<ActionJarras> acciones = null;	
	private Integer cantidadEnJ1;
	private Integer cantidadEnJ2;
	
	private VertexJarras(Integer cantidadEnJ1, Integer cantidadEnJ2) {
		super();
		this.cantidadEnJ1 = cantidadEnJ1;
		this.cantidadEnJ2 = cantidadEnJ2;
	}

	public Integer getCantidadEnJ1() {
		return cantidadEnJ1;
	}

	public Integer getCantidadEnJ2() {
		return cantidadEnJ2;
	}

	@Override
	public boolean isValid() {
		return this.cantidadEnJ1>=0 && this.cantidadEnJ2>=0;
	}
	
	

	@Override
	public List<ActionJarras> actions() {
		if(acciones == null) acciones = 
				DatosJarras.getAcciones()
					.stream()
					.map(a->ActionJarras.create(a.getId()))
					.collect(Collectors.toList());
		return acciones;
	}
	
	@Override
	public VertexJarras getThis() {
		return this;
	}

	@Override
	protected EdgeJarras getEdge(ActionJarras a) {		
		return EdgeJarras.create(this, a.neighbor(this), a);
	}


	@Override
	protected VertexJarras neighbor(ActionJarras a) {
		return a.neighbor(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cantidadEnJ1 == null) ? 0 : cantidadEnJ1.hashCode());
		result = prime * result
				+ ((cantidadEnJ2 == null) ? 0 : cantidadEnJ2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VertexJarras))
			return false;
		VertexJarras other = (VertexJarras) obj;
		if (cantidadEnJ1 == null) {
			if (other.cantidadEnJ1 != null)
				return false;
		} else if (!cantidadEnJ1.equals(other.cantidadEnJ1))
			return false;
		if (cantidadEnJ2 == null) {
			if (other.cantidadEnJ2 != null)
				return false;
		} else if (!cantidadEnJ2.equals(other.cantidadEnJ2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[J1=" + cantidadEnJ1 + ", J2="
				+ cantidadEnJ2 + "]";
	}

	
}
