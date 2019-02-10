package us.lsi.astar.mochila;

import java.util.List;

import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.astar.AStarSimpleVirtualGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;



public class TestAstarMochila {
	
	public static Double voraz(int index, Double capacidadRestante) {
		Double r = 0.;
		while (index < DatosMochila.numeroDeObjetos) {			
			Double a = Math.min(capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			r = r + a*DatosMochila.getValor(index);
			capacidadRestante = capacidadRestante-a*DatosMochila.getPeso(index);
			index = index+1;			
		}
		return  r;
	}
	
	public static void main(String[] args) {
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;		
		MochilaVertex e1 = MochilaVertex.of(78);
		Integer n = DatosMochila.getObjetos().size();
		
		AStarGraph<MochilaVertex,MochilaEdge> graph = 
				AStarSimpleVirtualGraph.of(x->x.getEdgeWeight(),(x,y)->-voraz(x.index,(double)x.capacidadRestante));
		AStarAlgorithm<MochilaVertex,MochilaEdge> a = AStarAlgorithm.create(graph,e1,(MochilaVertex v)->v.index==n);
		List<MochilaEdge> vertices = a.getPath().getEdgeList();
		SolucionMochila s = MochilaVertex.getSolucion(vertices);
		System.out.println(s);
	}

}
