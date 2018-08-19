package us.lsi.astar.mochila;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.astar.AlgoritmoAStar;
import us.lsi.mochila.datos.DatosMochila;


public class TestMochilaGrafo {

	public static void main(String[] args) {
		
		DatosMochila.iniDatos("./ficheros/objetosmochila.txt");
		System.out.println(DatosMochila.getObjetos());
		DatosMochila.capacidadInicial = 78;
		MochilaVertex p1 = MochilaVertex.create();
		System.out.println("Problema Inicial = "+p1);
		Predicate<MochilaVertex> goal = 
				(MochilaVertex p)-> 
				DatosMochila.getObjetos().size()==p.getProblema().getIndex();
		var graph = MochilaGrafo.create(p1);
		Long r = System.nanoTime();
		var a = AlgoritmoAStar.create(graph, p1, goal);
		GraphPath<MochilaVertex,MochilaEdge> path = a.getPath();
		System.out.println("Tiempo = "+(System.nanoTime()-r));
		System.out.println(a.getPathVertexList());
		System.out.println(MochilaVertex.getSolucion(path));
		System.out.println(path);
		System.out.println(AlgoritmoAStar.metricas);
	}
}
