package us.lsi.alg.typ.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;

public class TyPAStar {
	
	public static record AStarTyP(TyPProblem vertex, Integer a, TyPProblem lastVertex,
			Integer distanceToOrigin, Integer distanceToEnd) implements Comparable<AStarTyP>{
		public static AStarTyP of(TyPProblem vertex, Integer a, TyPProblem lastVertex,
			Integer distanceToOrigin, Integer distanceToEnd) {
			return new AStarTyP(vertex, a, lastVertex,distanceToOrigin, distanceToEnd);
		}
		@Override
		public int compareTo(AStarTyP other) {
			return this.distanceToEnd().compareTo(other.distanceToEnd());
		}	
	}
	
	public static TyPAStar of(TyPProblem start) {
		return new TyPAStar(start);
	}
	
	public TyPProblem start;
	public Map<TyPProblem,AStarTyP> tree;
	public PriorityQueue<AStarTyP> heap; 
	public Boolean goal;
	
	private TyPAStar(TyPProblem start) {
		super();
		this.start = start;
		AStarTyP a = AStarTyP.of(start,null,null,0,Heuristica.heuristica(start));
		this.tree = new HashMap<>();
		this.tree.put(start, a);
		this.heap = new PriorityQueue<>();
		this.heap.add(a);
		this.goal = false;
	}
	
	private List<Integer> acciones(TyPProblem v) {
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
		TyPProblem vertexActual = null;
		while (!heap.isEmpty() && !goal) {
			AStarTyP dataActual = heap.poll();
			vertexActual = dataActual.vertex();
			for (Integer a : vertexActual.acciones()) {
				TyPProblem v = vertexActual.vecino(a);
				Integer newDistance = v.maxCarga();
				Integer newDistanceToEnd = Heuristica.heuristica(v);				
				if (!tree.containsKey(v)) {	
					AStarTyP ac = AStarTyP.of(v, a, vertexActual, newDistance, newDistanceToEnd);
					heap.add(ac);
					tree.put(v, ac);	
				}else if (newDistance < tree.get(v).distanceToOrigin()) {
					heap.remove(tree.get(v));
					AStarTyP ac = AStarTyP.of(v, a, vertexActual, newDistance, newDistanceToEnd);
					heap.add(ac);
					tree.put(v, ac);
				}
			}
			this.goal = vertexActual.index() == DatosTyP.n;
		}
		return acciones(vertexActual);
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosTyP.datos("ficheros/tareas.txt",5);
		TyPProblem e1 = TyPProblem.first();
		TyPAStar a = of(e1);
		List<Integer> acciones = a.search();
		System.out.println(SolucionTyP.of(e1, acciones));
	}

}
