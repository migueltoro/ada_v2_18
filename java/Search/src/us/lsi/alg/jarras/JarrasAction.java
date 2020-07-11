package us.lsi.alg.jarras;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import us.lsi.graphs.virtual.ActionI;

public class JarrasAction extends ActionI<JarrasVertex> {

	public static JarrasAction of(Integer id, String name, Predicate<JarrasVertex> isApplicable,
			Function<JarrasVertex, JarrasVertex> neighbor) {
		return new JarrasAction(id, name, isApplicable, neighbor, v->1.);
	}

	private JarrasAction(Integer id, String name, Predicate<JarrasVertex> isApplicable,
			Function<JarrasVertex, JarrasVertex> neighbor, Function<JarrasVertex, Double> weight) {
		super(id, name, isApplicable, neighbor, weight);
	}
	

	@Override
	public String toString() {
		return String.format("(%d,%s)",id,name);
	}

	public static JarrasAction a0 = JarrasAction.of(0,"Vaciar J1",
			v->v.c1>0,
			v->JarrasVertex.of(0,v.c2));
	public static JarrasAction a1 = JarrasAction.of(1,"Volcar J1 en J2", //J1 queda vacía.
			v->v.c1>0 && v.c2 < JarrasVertex.cP2,
			v->JarrasVertex.of(0,Math.min(v.c1+v.c2,JarrasVertex.cP2)));
	public static JarrasAction a2 = JarrasAction.of(2,"Echar J1 en J2",  // J1 conserva el liquido restante 
		v->v.c1>0 && v.c2 < JarrasVertex.cP2,
		v->JarrasVertex.of(Math.max(0,v.c1+v.c2-JarrasVertex.cP2), 
				Math.min(v.c1+v.c2,JarrasVertex.cP2)));
	public static JarrasAction a3 = JarrasAction.of(3,"Llenar J1",
			v->v.c1 < JarrasVertex.cP1,
			v->JarrasVertex.of(JarrasVertex.cP1,v.c2));
	public static JarrasAction a4 = JarrasAction.of(4,"Llenar J2",
			v->v.c2 < JarrasVertex.cP2,
			v->JarrasVertex.of(v.c1,JarrasVertex.cP2));
	public static JarrasAction a5 = JarrasAction.of(5,"Vaciar J2",
			v->v.c2 > 0,
			v->JarrasVertex.of(v.c1, 0));
	public static JarrasAction a6 = JarrasAction.of(6,"Volcar J2 en J1",   // J2 siempre queda vacía.
			v->v.c2 > 0 && v.c1 < JarrasVertex.cP1,
			v->JarrasVertex.of(Math.min(v.c1+v.c2,JarrasVertex.cP1),0));
	public static JarrasAction a7 = JarrasAction.of(7,"Echar J2 en J1",    // J2 se conserva liquido restante
			v->v.c2 > 0 && v.c1 < JarrasVertex.cP1,
			v->JarrasVertex.of(Math.min(v.c1+v.c2,JarrasVertex.cP1), 
					Math.max(0,v.c1+v.c2-JarrasVertex.cP1)));
	
	public static List<JarrasAction> actions = List.of(a0,a1,a2,a3,a4,a5,a6,a7);
}
