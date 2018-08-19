package us.lsi.pd.jarras;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.astar.jarras.ActionJarras;
import us.lsi.common.Lists2;


public class SolucionJarras {

	public static SolucionJarras create() {
		return new SolucionJarras();
	}

	private List<ActionJarras> listaAcciones;

	private SolucionJarras() {
		super();
		this.listaAcciones = Lists2.newList();
	}

	public void add(ActionJarras op) {
		listaAcciones.add(op);
	}

	public void addFirst(ActionJarras op) {
		listaAcciones.add(0,op);
	}

	public Integer getNumAcc() {
		return listaAcciones.size();
	}

	public List<ActionJarras> getListaAcciones() {
		return listaAcciones;
	}	

	public Double getObjetivo() {
		return (double) this.getNumAcc();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((listaAcciones == null) ? 0 : listaAcciones.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SolucionJarras))
			return false;
		SolucionJarras other = (SolucionJarras) obj;
		if (listaAcciones == null) {
			if (other.listaAcciones != null)
				return false;
		} else if (!listaAcciones.equals(other.listaAcciones))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SolucionJarra [numOp=" + this.getNumAcc() + "\n" + 
	listaAcciones.stream().map(x->x.toString()).collect(Collectors.joining("\n")) + "]";
	}	

}

