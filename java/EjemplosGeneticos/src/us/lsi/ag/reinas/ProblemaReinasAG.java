package us.lsi.ag.reinas;

import java.util.List;
import java.util.Set;



import us.lsi.ag.SeqNomalChromosome;
import us.lsi.ag.SeqNormalProblemAG;
import us.lsi.common.Lists2;
import us.lsi.common.Sets2;
import us.lsi.reinas.datos.Reina;


public class ProblemaReinasAG implements SeqNormalProblemAG<List<Reina>> {

public static int numeroDeReinas = 8;
	
	public static ProblemaReinasAG create() {
		return new ProblemaReinasAG();
	}

	private ProblemaReinasAG() {
	}

	@Override
	public List<Reina> getSolucion(SeqNomalChromosome chromosome) {
		List<Integer> ls = chromosome.decode();
		List<Reina> r = Lists2.newList();
		for (int i = 0; i < ls.size(); i++) {
			r.add(Reina.create(i, ls.get(i)));
		}
		return r;
	}

	@Override
	public Double fitnessFunction(SeqNomalChromosome chromosome) {
		List<Integer> ls = chromosome.decode();
		Set<Integer> dp = Sets2.newHashSet();
		Set<Integer> ds = Sets2.newHashSet();
		for (int i = 0; i < ls.size(); i++) {
			dp.add(ls.get(i)-i);
			ds.add(ls.get(i)+i);
		}
		Double r = 2.*ProblemaReinasAG.numeroDeReinas-dp.size()-ds.size();
		return -2000*r*r;
	}

	@Override
	public Integer getObjectsNumber() {
		return numeroDeReinas;
	}	
	

}
