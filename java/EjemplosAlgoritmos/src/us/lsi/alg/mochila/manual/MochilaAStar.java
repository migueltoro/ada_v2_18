package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;


import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class MochilaAStar {
	
	public static record AStarMochila(Mochila vertex, Integer a, Mochila lastVertex,
			Double distanceToOrigin, Double distanceToEnd) implements Comparable<AStarMochila>{
		public static AStarMochila of(Mochila vertex, Integer a, Mochila lastVertex,
			Double distanceToOrigin, Double distanceToEnd) {
			return new AStarMochila(vertex, a, lastVertex,distanceToOrigin, distanceToEnd);
		}
		@Override
		public int compareTo(AStarMochila other) {
			return this.distanceToEnd().compareTo(other.distanceToEnd());
		}	
	}
	
	public static MochilaAStar of(Mochila start) {
		return new MochilaAStar(start);
	}
	
	public Mochila start;
	public Map<Mochila,AStarMochila> tree;
	public PriorityQueue<AStarMochila> heap; 
	public Boolean goal;
	
	private MochilaAStar(Mochila start) {
		super();
		this.start = start;
		AStarMochila a = AStarMochila.of(start, null,null,0., Heuristica.heuristica(start));
		this.tree = new HashMap<>();
		this.tree.put(start, a);
		this.heap = new PriorityQueue<>();
		this.heap.add(a);
		this.goal = false;
	}
	
	private List<Integer> path(Mochila v) {
		List<Integer> ls = new ArrayList<>();
		Integer a = this.tree.get(v).a();
		while (a != null) {
			ls.add(a);
			v = this.tree.get(v).lastVertex();
			a = this.tree.get(v).a();
		}
		Collections.reverse(ls);
		return ls;
	}


	public List<Integer> search() {
		Mochila vertexActual = null;
		while (!heap.isEmpty() && !goal) {
			AStarMochila dataActual = heap.poll();
			vertexActual = dataActual.vertex();
			for (Integer a : vertexActual.acciones()) {
				Mochila v = vertexActual.vecino(a);
				Double newDistance = dataActual.distanceToOrigin()-a*DatosMochila.getValor(vertexActual.index());
				Double newDistanceToEnd = newDistance - Heuristica.heuristica(v);				
				if (!tree.containsKey(v)) {	
					AStarMochila ac = AStarMochila.of(v, a, vertexActual, newDistance, newDistanceToEnd);
					heap.add(ac);
					tree.put(v, ac);	
				}else if (newDistance < tree.get(v).distanceToOrigin()) {
					heap.remove(tree.get(v));
					AStarMochila ac = AStarMochila.of(v, a, vertexActual, newDistance, newDistanceToEnd);
					heap.add(ac);
					tree.put(v, ac);
				}
			}
			this.goal = vertexActual.index() == DatosMochila.n;
		}
		return path(vertexActual);
	}

	public SolucionMochila solucion(List<Integer> ls) {
		SolucionMochila s = SolucionMochila.empty();
		Mochila v = this.start;
		for(Integer a: ls) {	
			s.add(DatosMochila.getObjeto(v.index()), a);
			v = v.vecino(a);
		}
		return s;
	}
	

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		Mochila v1 = Mochila.of(0, DatosMochila.capacidadInicial);
		MochilaAStar a = MochilaAStar.of(v1);
		List<Integer> ls = a.search();
		SolucionMochila s = a.solucion(ls);
		System.out.println(s);
	}

	

}
