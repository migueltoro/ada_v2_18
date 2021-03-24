package us.lsi.bufete;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.bufete.datos.Abogado;
import us.lsi.bufete.datos.DatosAbogados;
import us.lsi.bufete.datos.SolucionAbogados;

public class DatosBufeteAG implements ValuesInRangeData<Integer, SolucionAbogados> {

	public static DatosBufeteAG create(String fichero) {
		return new DatosBufeteAG(fichero);
	}

	private DatosBufeteAG(String fichero) {
		DatosAbogados.iniDatos(fichero);
		DatosAbogados.toConsole();
	}	

	@Override
	public ChromosomeType getType() {
		return ChromosomeType.Range;
	}

	@Override
	public Integer size() {
		return DatosAbogados.NUM_CASOS;
	}

	@Override
	public Integer getMax(Integer i) {
		return DatosAbogados.NUM_ABOGADOS;
	}

	@Override
	public Integer getMin(Integer i) {
		return 0;
	}	

	public Map<Abogado,Integer> horasAbogado;
	
	@Override
	public Double fitnessFunction(List<Integer> ls) {
		this.horasAbogado = new HashMap<>();
		for(int i=0; i<this.size(); i++) {
			Abogado a = DatosAbogados.getAbogado(ls.get(i));
			Integer t_a = horasAbogado.get(a);
			if(t_a==null) {
				this.horasAbogado.put(a, a.getHoras(i));
			} else {
				this.horasAbogado.put(a, t_a + a.getHoras(i));
			}
		}		
		return objetivo(horasAbogado) ;
	}

	private double objetivo(Map<Abogado, Integer> horasAbogado) {
		Integer obj = this.horasAbogado.values().stream().max(Comparator.naturalOrder()).get();
		return -obj;
	}

	@Override
	public SolucionAbogados getSolucion(List<Integer> cr) {
		return SolucionAbogados.create(cr);
	}
	
	
}

