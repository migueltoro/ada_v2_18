package us.lsi.alg.equipo;


import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import us.lsi.common.List2;

public class EquipoHeuristic {

	
	
	public static Double heuristica2(EquipoVertex v1, Predicate<EquipoVertex> goal, EquipoVertex v2) {
		Integer p = v1.index();
		Double r = 0.;
		while (p < DatosEquipo.M) {
			Optional<Integer> a = v1.mejorEnPosicion(p);
			if(!a.isPresent()) r += 10.;
			else r += DatosEquipo.getR(a.get(), p);			
//			System.out.println(String.format("jugador = %d, posicion = %d, rendimiento = %.2f, r = %.2f,",
//					a.get(),p,DatosEquipo.getR(a.get(), p),r));
			p = p+1;
		}
		return r;
	}
	
	public static Double heuristica_neg(EquipoVertex v1, Predicate<EquipoVertex> goal, EquipoVertex v2) {
		return -heuristica2(v1,goal,v2);
	}
	
	public static Double voraz(EquipoVertex v) {
		Integer p = v.index();
		Double r = 0.;
		while (p < DatosEquipo.M) {
			Optional<Integer> a = v.mejorEnPosicion(p);
			if(a.isPresent()) r += DatosEquipo.getR(a.get(), v.index());
//			System.out.println(r+","+v.index());
			else a = Optional.of(0);
			v = v.neighbor(a.get());
			p = v.index();
		}
		return r;
	}
	
	public static List<Integer> solucionVoraz(EquipoVertex v) {
		Integer p = v.index();
		List<Integer> ls = List2.of();
		while (p < DatosEquipo.M) {
			Optional<Integer> a = v.mejorEnPosicion(p);
			if(a.isPresent()) ls.add(a.get());
//			System.out.println(r+","+v.index());
			else a = Optional.of(0);
			v = v.neighbor(a.get());
			p = v.index();
		}
		return ls;
	}
	
	public static Double vorazRandom(EquipoVertex v) {
		Integer p = v.index();
		Double r = 0.;
		while (p < DatosEquipo.M) {
			Optional<Integer> a = v.random(p);
			if(a.isPresent()) r += DatosEquipo.getR(a.get(), v.index());
//			System.out.println(r+","+v.index());
			else a = v.randomSin(p);
			v = v.neighbor(a.get());
			p = v.index();
		}
		return r;
	}

}
