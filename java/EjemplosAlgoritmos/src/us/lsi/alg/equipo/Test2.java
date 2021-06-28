package us.lsi.alg.equipo;

import java.util.Locale;
public class Test2 {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosEquipo.iniDatos("ficheros/DatosEquipo1.txt");
		
		EquipoVertex v1 = EquipoVertex.first();
		for(int i =0;i<DatosEquipo.M;i++) {
			Integer m = v1.mejorEnPosicion(i).get();
			System.out.println(i+" = " +m+" = "+DatosEquipo.getR(m,i));
		}
		EquipoVertex v = EquipoVertex.first();
		System.out.println(DatosEquipo.getR(0,1));
		System.out.println(EquipoHeuristic.voraz(EquipoVertex.first()));
		System.out.println(EquipoHeuristic.heuristica2(v,EquipoVertex::goal,null));
		System.out.println(EquipoHeuristic.heuristica_neg(v,EquipoVertex::goal,null));
		v = v.neighbor(v.mejorEnPosicion(0).get());
		System.out.println(EquipoHeuristic.heuristica2(v,EquipoVertex::goal,null));
		System.out.println(EquipoHeuristic.heuristica_neg(v,EquipoVertex::goal,null));

	}

}
