package us.lsi.bufete.datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import us.lsi.common.Pair;

public class SolucionAbogados extends TipoSolucion {

	public static SolucionAbogados create(List<Integer> ls) {
		return new SolucionAbogados(ls);
	}

	public static SolucionAbogados create(Double vo, Map<String, Double> vbles) {
		return new SolucionAbogados(vo, vbles);
	}	
	
	private double total;
	private SortedMap<String,Pair<Integer,List<String>>> solucion;

	private SolucionAbogados(List<Integer> ls) {
		super(ls);
		total = 0;
		solucion = new TreeMap<>();
		for(int i=0; i<ls.size(); i++) {
			int tiempo = DatosAbogados.getHoras(ls.get(i), i);
			String s = "Abogado_"+formato((ls.get(i)+1));
			Pair<Integer,List<String>> casos = solucion.get(s);
			if(casos!=null) {
				casos.second().add("Caso "+(i+1));
				casos = Pair.of(casos.first() + tiempo,casos.second());
			} else {
				List<String> ls_casos = new ArrayList<>();
				ls_casos.add("Caso "+(i+1));
				casos = Pair.of(tiempo, ls_casos);
				solucion.put(s, casos);
			}
			total += tiempo;
		}
	}

	private String formato(int i) {
		return i<10? "0"+i: i+"";
	}
	
	private SolucionAbogados(Double vo, Map<String, Double> vbles) {	
		super(vo, vbles);
		total = 0;
		solucion = new TreeMap<>();
		for(Map.Entry<String, Double> par: vbles.entrySet()) {
			System.out.println(par);
			if(par.getValue()>0 && par.getKey().startsWith("x")) {
				String[] tokens = par.getKey().substring(2).split("_");
				Integer x = Integer.parseInt(tokens[0].trim())+1;
				Integer y = Integer.parseInt(tokens[1].trim())+1;
				int tiempo = DatosAbogados.getHoras(x-1, y-1);
				Pair<Integer,List<String>> p = solucion.get("Abogado_"+formato(x));
				if(p!=null) {
					p.second().add("Caso "+y);
					p = Pair.of(p.first() + tiempo, p.second());
				} else {
					List<String> ls = new ArrayList<>();
					ls.add("Caso "+y);
					solucion.put("Abogado_"+formato(x), Pair.of(tiempo, ls));
				}
				total += tiempo;
			}
		}
	}	
	
	@Override
	public String toString() {
		String msg = "";
		for (Map.Entry<String,Pair<Integer,List<String>>> g: solucion.entrySet()) {
			msg += g.getKey()+"\n\tHoras empleadas: "+g.getValue().first()+"\n\t"+
			"Casos estudiados: "+g.getValue().second()+"\n\tMedia (horas/caso):"+
			String.format("%.2f", 1.*g.getValue().first()/g.getValue().second().size())+
			"\n===================================================================\n";
		}
		int tiempo = solucion.values().stream().mapToInt(p->p.first()).max().getAsInt();
		return msg+"El estudio de todos los casos ha supuesto un total de "+total+
		" horas de trabajo para el bufete,\nque al trabajar en paralelo se ha podido "+
		"llevar a cabo en "+tiempo+" horas";
	}

}

