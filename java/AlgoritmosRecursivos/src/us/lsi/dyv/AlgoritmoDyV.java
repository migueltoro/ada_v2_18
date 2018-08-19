package us.lsi.dyv;

public class AlgoritmoDyV {
	
	/**
	 * 
	 * @param <E> El tipo de la solución parcial
	 * @param <S> El tipo de la solución
	 * @param p - Problema a resolver
	 * @return Algoritmo de Divide y Vencerás Sin Memoria para resolver el problema
	 */
	public static <S, E> AlgoritmoDyVSM<S,E> createDyVSM(ProblemaDyV<S, E> p) {
		return new AlgoritmoDyVSM<S,E>(p);
	}
	
	/**
	 * 
	 * @param <E> El tipo de la solución parcial
	 * @param <S> El tipo de la solución
	 * @param p - Problema a resolver
	 * @return Algoritmo de Divide y Vencerás Con Memoria para resolver el problema
	 */
	public static <S, E> AlgoritmoDyVCM<S,E> createDyVCM(ProblemaDyV<S, E> p) {
		return new AlgoritmoDyVCM<S,E>(p);
	}
	

}
