package us.lsi.bt.anuncios;

import us.lsi.anuncios.datos.DatosAnuncios;
import us.lsi.bt.AlgoritmoBT;

public class TestAnunciosBT {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatosAnuncios.leeYOrdenaAnuncios("ficheros/anuncios.txt");
		DatosAnuncios.tiempoTotal = 30;
		AlgoritmoBT.numeroDeSoluciones = 6;
		AlgoritmoBT.conFiltro = true;
		System.out.println(DatosAnuncios.todosLosAnunciosDisponibles);
		EstadoAnunciosBT e = EstadoAnunciosBT.create();
		var a = AlgoritmoBT.create(e);
		a.ejecuta();
//		for(ListaDeAnunciosAEmitir s: a.getSoluciones()){
//			System.out.println(s);
//		}
		System.out.println("Mejor ="+a.getSolucion());
	}
}
