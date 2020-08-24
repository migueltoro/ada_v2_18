package us.lsi.problemas.reinas;

import java.util.List;
import java.util.Set;

import us.lsi.ag.SeqNormalProblemAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agchromosomes.SeqNomalChromosome;
import us.lsi.ag.AuxiliaryAg;
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
		List<Reina> r = Lists2.empty();
		for (int i = 0; i < ls.size(); i++) {
			r.add(Reina.create(i, ls.get(i)));
		}
		return r;
	}

	@Override
	public Double fitnessFunction(SeqNomalChromosome chromosome) {
		List<Integer> ls = chromosome.decode();
		Set<Integer> dp = Sets2.empty();
		Set<Integer> ds = Sets2.empty();
		for (int i = 0; i < ls.size(); i++) {
			dp.add(ls.get(i)-i);
			ds.add(ls.get(i)+i);
		}
		return -AuxiliaryAg.distanceToEqZero(2.*ProblemaReinasAG.numeroDeReinas-dp.size()-ds.size());
	}

	@Override
	public Integer getIndexNumber() {
		return numeroDeReinas;
	}

	@Override
	public ChromosomeType getType() {
		return ChromosomeType.Permutation;
	}	
	
}
