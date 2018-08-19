package us.lsi.bt.jarras;


import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;

import us.lsi.astar.jarras.ActionJarras;
import us.lsi.bt.AlgunosTestsBT;
import us.lsi.jarras.datos.DatosJarras;

public class Test2 {

	

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
		System.out.println("------");
		
		
		
		EstadoJarras e = EstadoJarras.create();
		
		List<ActionJarras> ls = 
				Arrays.asList(4,6,4,7,0,6)
				.stream()
				.map(x->ActionJarras.create(x))
				.collect(Collectors.toList());
		System.out.println("Test 1 =====");		
		AlgunosTestsBT.test1(e, ls);
		System.out.println("\n\n\nTest 2 =====\n\n\n");	
		AlgunosTestsBT.test2(e);
	}
	
	

}
