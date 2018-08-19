package us.lsi.astar.jarras;


import org.jgrapht.GraphPath;

import us.lsi.astar.AlgoritmoAStar;
import us.lsi.jarras.datos.DatosJarras;


public class Test {

	

	public static void main(String[] args) {
		DatosJarras.iniDatos("acciones.txt");
		DatosJarras.getAcciones().forEach(x->System.out.println(x));
		DatosJarras.capacidadJarra1 = 4;
		DatosJarras.capacidadJarra2 = 3;
		DatosJarras.cantidadFinalEnJarra1 = 2;
		DatosJarras.cantidadFinalEnJarra2 = 0;
		DatosJarras.cantidadInicialEnJarra1 = 0;
		DatosJarras.cantidadInicialEnJarra2 = 0;
		DatosJarras.numMaxAcciones = 10;
		
		VertexJarras origen = VertexJarras.createOrigen();
		VertexJarras destino = VertexJarras.createDestino();
		GraphJarras g = new GraphJarras(origen, destino);
		var d = AlgoritmoAStar.create(g,origen, destino);
		GraphPath<VertexJarras,EdgeJarras> p = d.getPath();
		System.out.println(p);
		System.out.println(p.getWeight());
		System.out.println(p.getVertexList());
	}

}
