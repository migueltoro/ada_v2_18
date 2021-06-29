package us.lsi.alg.equipo;

import java.util.Locale;
public class Test2 {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosEquipo.iniDatos("ficheros/DatosEquipo3.txt");
		
		EquipoVertex v = EquipoVertex.first();
		
		while (v.index()<DatosEquipo.M) {
			Double vr = EquipoHeuristic.voraz(v);
			Double h = EquipoHeuristic.heuristica2(v, EquipoVertex::goal, null);
			SolucionEquipo s = SolucionEquipo.create(EquipoHeuristic.solucionVoraz(EquipoVertex.first()));
			System.out.printf("%d,%6.2f,%6.2f\n",v.index(),vr,h);
			System.out.printf("%s\n",s);
			v = v.neighbor(v.mejorEnPosicion(v.index()).orElse(v.mejorEnPosicionSin(v.index()).get()));
		}
	}

}
