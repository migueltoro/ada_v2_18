package us.lsi.bt.jarras;

import java.util.List;

import us.lsi.astar.jarras.ActionJarras;
import us.lsi.astar.jarras.VertexJarras;
import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;

public class SolucionJarras  {

	public static SolucionJarras create() {
		return new SolucionJarras();
	}

	private List<ActionJarras> listaAcciones;
	private List<VertexJarras> listaVertices;

	SolucionJarras(List<ActionJarras> ls1, List<VertexJarras> ls2) {
		super();
		this.listaAcciones = Lists2.newList(ls1);
		this.listaVertices = Lists2.newList(ls2);
	}

	private SolucionJarras() {
		super();
		this.listaAcciones = Lists2.newList();
		this.listaVertices = Lists2.newList();
	}

	public void add(ActionJarras a,VertexJarras v) {
		listaAcciones.add(a);
		listaVertices.add(v);
	}

	public void addAction(ActionJarras a) {
		listaAcciones.add(a);
	}
	
	public void addVertex(VertexJarras v) {
		listaVertices.add(v);
	}
	
	
	public void removeLast() {
		Preconditions.checkArgument(!listaAcciones.isEmpty());
		listaAcciones.remove(listaAcciones.size() - 1);
		listaVertices.remove(listaVertices.size() - 1);
	}
	
	public ActionJarras removeLastAction() {
		Preconditions.checkArgument(!listaAcciones.isEmpty());
		return listaAcciones.remove(listaAcciones.size() - 1);
	}

	public VertexJarras removeLastVertex() {
		Preconditions.checkArgument(!listaVertices.isEmpty());
		return listaVertices.remove(listaVertices.size() - 1);
	}
	
	
	public Integer getNumAcc() {
		return listaAcciones.size();
	}

	public List<ActionJarras> getListaAcciones() {
		return listaAcciones;
	}
	
	public ActionJarras getLastAction() {
		Preconditions.checkArgument(!listaAcciones.isEmpty());
		return listaAcciones.get(listaAcciones.size()-1);
	}

	public VertexJarras getLastVertex() {
		Preconditions.checkArgument(!listaVertices.isEmpty());
		return listaVertices.get(listaVertices.size()-1);
	}	

	public SolucionJarras copy() {
		return new SolucionJarras(this.listaAcciones,this.listaVertices);
	}
	
	public Double getObjetivo() {
		return (double) this.getNumAcc();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaAcciones == null) ? 0 : listaAcciones.hashCode());
		result = prime * result + ((listaVertices == null) ? 0 : listaVertices.hashCode());
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
		if (listaVertices == null) {
			if (other.listaVertices != null)
				return false;
		} else if (!listaVertices.equals(other.listaVertices))
			return false;
		return true;
	}

	public String toString() {
		return "SolucionJarras: \nNúmero de Acciones :"+this.getNumAcc()+"\nlistaAcciones=" + listaAcciones + "\nlistaVertices=" + listaVertices;
	}
	
	
}


