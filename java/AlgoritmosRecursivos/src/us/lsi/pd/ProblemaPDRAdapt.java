package us.lsi.pd;

import java.util.List;

import us.lsi.pd.AlgoritmoPD.Sp;

public class ProblemaPDRAdapt<S,A,P extends ProblemaPDR<S,A,P>> 
        implements ProblemaPD<S, A, ProblemaPDRAdapt<S,A,P>> {

	public static <S, A, P extends ProblemaPDR<S,A,P>> ProblemaPDRAdapt<S,A,P> create(ProblemaPDR<S, A, P> problemaPDR) {
		return new ProblemaPDRAdapt<S,A,P>(problemaPDR);
	}

	private ProblemaPDR<S,A,P> p;
	
	
	private ProblemaPDRAdapt(ProblemaPDR<S, A, P> problemaPDR) {
		super();
		this.p = problemaPDR;
	}

	@Override
	public us.lsi.pd.AlgoritmoPD.Tipo getTipo() {
		return p.getTipo();
	}

	@Override
	public int size() {
		return p.size();
	}

	@Override
	public boolean esCasoBase() {
		return p.esCasoBase();
	}

	@Override
	public Sp<A> getSolucionParcialCasoBase() {
		return p.getSolucionParcialCasoBase();
	}

	@Override
	public Sp<A> getSolucionParcial(List<Sp<A>> ls) {
		return p.getSolucionParcial(ls);
	}

	@Override
	public ProblemaPDRAdapt<S,A,P> getSubProblema(A a, int np) {
		return ProblemaPDRAdapt.create(p.getSubProblema(a));
	}

	@Override
	public Sp<A> getSolucionParcialPorAlternativa(A a, List<Sp<A>> ls) {
		return p.getSolucionParcialPorAlternativa(a, ls.get(0));
	}

	@Override
	public List<A> getAlternativas() {
		return p.getAlternativas();
	}

	@Override
	public int getNumeroSubProblemas(A a) {
		return 1;
	}

	@Override
	public S getSolucionReconstruidaCasoBase(Sp<A> sp) {
		return p.getSolucionReconstruidaCasoBase(sp);
	}

	@Override
	public S getSolucionReconstruidaCasoRecursivo(Sp<A> sp, List<S> ls) {
		return p.getSolucionReconstruidaCasoRecursivo(sp, ls.get(0));
	}
	@Override
	public Double getObjetivoEstimado(A a){
		return p.getObjetivoEstimado(a);
	}
	@Override
	public Double getObjetivo() {
		return p.getObjetivo();
	}
	@Override
	public boolean estaFueraDeRango() {
		return p.estaFueraDeRango();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProblemaPDRAdapt))
			return false;
		ProblemaPDRAdapt<?,?,?> other = (ProblemaPDRAdapt<?,?,?>) obj;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return p.toString();
	}
	
	
	
}
