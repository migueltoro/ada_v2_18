package us.lsi.astar.jarras;

import us.lsi.graphs.virtual.Action;
import us.lsi.jarras.datos.DatosJarras;

public class ActionJarras implements Action<VertexJarras> {

	public static ActionJarras create(Integer id) {
		return new ActionJarras(id);
	}

	private Integer id;
	
	private ActionJarras(Integer id) {
		super();
		this.id = id;
	}

	public VertexJarras neighbor(VertexJarras v) {
		Integer j1 = v.getCantidadEnJ1();
		Integer j2 = v.getCantidadEnJ2();
		var p = DatosJarras.getAccion(id).result(j1, j2);
		return VertexJarras.create(p.v1, p.v2);
	}

	public boolean isApplicable(VertexJarras v) {
		Integer j1 = v.getCantidadEnJ1();
		Integer j2 = v.getCantidadEnJ2();
		return DatosJarras.getAccion(id).isAplicable(j1, j2);
	}
	
}
