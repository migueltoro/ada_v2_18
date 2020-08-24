package us.lsi.problemas.asignacion;

import java.util.List;

import us.lsi.ag.SeqNormalProblemAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agchromosomes.SeqNomalChromosome;



public class AsignacionTareasAG implements SeqNormalProblemAG<List<Integer>> {

	public static AsignaciondeTareasRF a = null;

	public static AsignacionTareasAG create(String file) {
		a = AsignaciondeTareasRF.create(file);
		return new AsignacionTareasAG();
	}

	private AsignacionTareasAG() {
	}

	@Override
	public List<Integer> getSolucion(SeqNomalChromosome chromosome) {
		return chromosome.decode();
	}

	@Override
	public Double fitnessFunction(SeqNomalChromosome chromosome) {
		List<Integer> ls = chromosome.decode();
		Double coste = 0.;
		for (int i = 0; i < ls.size(); i++) {
			coste = coste + a.getCoste(i, ls.get(i));
		}
		return -coste;
	}

	@Override
	public Integer getIndexNumber() {
		return a.getN();
	}

	@Override
	public ChromosomeType getType() {
		return ChromosomeType.Permutation;
	}

}
