package us.lsi.pd;



import java.util.*;
import java.util.stream.Collectors;

import us.lsi.common.BiMap;
import us.lsi.common.Files2;
import us.lsi.common.Lists2;
import us.lsi.common.Maps2;
import us.lsi.common.Metricas;
import us.lsi.common.Sets2;
import us.lsi.tiposrecursivos.Tree;

/**
 * <p> Algoritmo que implementa la técnica de Programación Dinámica con sus variantes. 
 * Un problema que se quiera resolver con esta técnica debe implementar el interface ProblemaPD &lt; S,A &gt; y asumimos que 
 * queremos minimizar la propiedad getObjetivo() </p>
 * 
 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema14.pdf" target="_blank">Tema14</a></p>
 * 
 * 
 * @author Miguel Toro
 *
 * @param <S> El tipo de la solución
 * @param <A> El tipo de la alternativa
 */
public class AlgoritmoPD<S,A,P extends ProblemaPD<S,A,P>> {
	
	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param <P> El tipo del problema
	 * @param p - Problema a resolver 
	 * @return Algoritmo de Programación Dinámica para resolver le problema
	 */
	public static <S, A, P extends ProblemaPD<S, A, P>> AlgoritmoPD<S,A,P> createPD(P p) {
		List<P> lis = Lists2.empty();
		lis.add(p);
		return new AlgoritmoPD<S, A,P>(lis);
	}

	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param <P> El tipo de problema
	 * @param p - Problema a resolver 
	 * @return Algoritmo de Programación Dinámica para resolver le problema
	 */
	public static <S, A,P extends ProblemaPDR<S,A,P>> AlgoritmoPD<S,A,ProblemaPDRAdapt<S,A,P>> createPDR(P p) {
		List<ProblemaPDRAdapt<S, A, P>> lis = Lists2.empty();
		lis.add(p.asPD());
		return new AlgoritmoPD<S, A,ProblemaPDRAdapt<S,A,P>>(lis);
	}
	
	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param <P> El tipo del problema
	 * @param p - Conjunto de problemas a resolver 
	 * @return Algoritmo de Programación Dinámica para resolver los problemas
	 */
	public static <S, A, P extends ProblemaPD<S, A, P> > AlgoritmoPD<S,A,P> createPD(List<P> p) {
		return new AlgoritmoPD<S, A,P>(p);
	}
	
	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param <P> El tipo del problema
	 * @param p - Conjunto de problemas a resolver 
	 * @return Algoritmo de Programación Dinámica para resolver los problemas
	 */
	public static <S,A,P extends ProblemaPDR<S, A, P>> AlgoritmoPD<S,A,ProblemaPDRAdapt<S,A,P>> createPDR(List<P> p) {
		List<ProblemaPDRAdapt<S,A,P>> ls = p.stream().map(x->x.asPD()).collect(Collectors.toList());
		return new AlgoritmoPD<S,A,ProblemaPDRAdapt<S,A,P>>(ls);
	}
	
       
	public static enum Tipo{Min,Max,Sum,Otro}	
	
	public static boolean conFiltro = false;
	/**
	 * Si se quiere aplicar la técnica aleatoria para escoger una de las alternativas
	 */
	public static boolean isRandomize = false;
	/**
	 * Tamaño umbral a partir del cual se escoge aleatoriamente una de las alternativas
	 */
	public static Integer sizeRef = 10;
	
	private Map<P,Sp<A>> solucionesParciales;
	private P problema;    
    private Iterable<P> problemas;
    private Double mejorValor;
    private Tipo tipo;
    private Integer numeroDeProblemas;
	public static Metricas metricas = new Metricas();
   	public static boolean metricasOK = false;
    private BiMap<P,Integer> idsDeProblemas;
    private BiMap<Integer,P> problemasDesdeId;
    
    private boolean isMin(){
    	return tipo.equals(AlgoritmoPD.Tipo.Min);
    }
    
    private boolean isMax(){
    	return tipo.equals(AlgoritmoPD.Tipo.Max);
    }
    
    /**
	 * @return El mejor valor de la propiedade objetivo del problema inicial encontrado por el algoritmo hasta este momento
	 */
	public Double getMejorValor() {
		return mejorValor;
	}
    
	/**
	 * @return Las soluciones parciales de los problemas resueltos
	 */
	public Map<P, Sp<A>> getSolucionesParciales() {
		return solucionesParciales;
	}

	/**
	 * 
	 * @return Solución parcial del problema o null si no tiene solución o no ha ha sido encontrado por el algoritmo
	 */
	public Sp<A> getSolucionParcial() {
		return getSolucionParcial(problema) ;
	}
		
	/**
	 * @param p - Problema del que se quiere obtener la solución parcial
	 * @return Solución parcial del problema o null si no tiene solución o no ha ha sido encontrado por el algoritmo
	 */
	public Sp<A> getSolucionParcial(P p) {
			return solucionesParciales.get(p);
	}
	
	public AlgoritmoPD.Tipo getTipo() {
		return tipo;
	}

	/**
	 * @return Número de problemas resueltos
	 */
	public Integer getNumeroDeProblemas() {
		return numeroDeProblemas;
	}
	
	/**
	 * @param p Un problema
	 * @return El identificador del problema
	 */
	public Integer getIdDeProblema(P p) {
		return idsDeProblemas.get(p);
	}

	/**
	 * @return Número de subproblemas encontrado al resolver el problema inicial
	 */
	public int getNumeroDeSubproblemas(){
		return this.solucionesParciales.keySet().size();
	}
	
	/**
	 * @param id Un identificador de un problema
	 * @return El problema asociado al identificador
	 */
	public P getProblemasDesdeId(Integer id) {
		return problemasDesdeId.get(id);
	}

	/**
	 * @param p Un problema 
	 * @return Si p es un subproblema ya resuelto al resolver el problema inicial
	 */
	public boolean haSidoResueltoYa(P p){
		return this.solucionesParciales.containsKey(p);
	}

	/**
	 * @pre el problema es de minimización o de maximización
	 * @return Un árbol con las alternativas que están dentro de la solución
	 */
	public Tree<A> getAlternativasDeSolucion(){	
		return getAlternativasDeSolucion(problema);
	}
	
	/**
	 * @pre el problema es de minimización o de maximización
	 * @param p Un problema
	 * @return Un árbol con las alternativas que están dentro de la solución
	 */
	public Tree<A> getAlternativasDeSolucion(P p){
		Tree<A> r = null;
		if (!p.esCasoBase()) {
			Sp<A> sp = this.getSolucionParcial(p);
			List<Tree<A>> la = Lists2.empty();
			Integer np = p.getNumeroSubProblemas(sp.alternativa);
			for (Integer i = 0; i < np; i++) {
				Tree<A> rh = this.getAlternativasDeSolucion(p.getSubProblema(sp.alternativa, i));
				if (rh!=null) {
					la.add(rh);
				}
			}
			r = Tree.nary(sp.alternativa,la);
		}
		return r;
	}
	
	public AlgoritmoPD(List<P> ps){		
	    problema = ps.get(0);
	    problemas = ps;
	    tipo = problema.getTipo();
	    this.numeroDeProblemas = 0;
	    this.idsDeProblemas = BiMap.empty();
	    this.problemasDesdeId = this.idsDeProblemas.inverse();
	    mejorValor = isMin()? Double.MAX_VALUE: Double.MIN_VALUE;  
	}
	
	public void ejecuta() {
		if (AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.setTiempoDeEjecucionInicial();
		solucionesParciales = Maps2.newHashMap();
		for (P p : problemas) {
			do {
				pD(p);
			} while (isRandomize && solucionesParciales.get(problema) == null);
		}
		if (AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.setTiempoDeEjecucionFinal();
	}
	
	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param p Un problema
	 * @param alternativas Sus alternativas
	 * @return Una alternativa escogida al azar o todas ellas dependiendo del tamaño del problam
	 */
	private List<A> randomize(P p, List<A> alternativas){
		List<A> alt;
		if(isRandomize && p.size()>sizeRef){
			List<A> ls = Lists2.ofCollection(alternativas);			
			alt = Lists2.randomUnitary(ls);
		}else{
			alt = alternativas;
		}
		return alt;
	}
	
	private void actualizaMejorValor(P p){
			Double objetivo = p.getObjetivo();
			if (objetivo!=null &&
					(isMin() && objetivo < mejorValor || 
					 isMax()&& objetivo > mejorValor)) {
				if (AlgoritmoPD.metricasOK)
					AlgoritmoPD.metricas.addActualizacionMejorValor();
				mejorValor = objetivo;
			}
	}
	
	protected void guardaEnMemoria(P p, Sp<A> e) {
		this.numeroDeProblemas++;
		this.idsDeProblemas.put(p,numeroDeProblemas);
		if(e!=null) e.problema = p;
		solucionesParciales.put(p, e);
	}
	
	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param p El problema a resolver 
	 * @return Su solución parcial
	 */
	private Sp<A> pD(P p){
		if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addLLamadaRecursiva();		
		Sp<A> e = null;	
		if (haSidoResueltoYa(p)){
			if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addUsoDeLaMemoria();
			
			e = getSolucionParcial(p);
		} else if(p.esCasoBase()) {
			if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addCasoBase();
			e = p.getSolucionParcialCasoBase();
			guardaEnMemoria(p, e); 
		} else if(p.estaFueraDeRango()){
			if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addFueraDeRango();
		} else {
			List<Sp<A>> solucionesDeAlternativas = Lists2.empty(); 
			for(A a: randomize(p,p.getAlternativas())){
				if(conFiltro && isMin() && p.getObjetivoEstimado(a) >= mejorValor) {
					if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addUnFiltro();
					continue;
				}
				if(conFiltro && isMax() && p.getObjetivoEstimado(a) <= mejorValor) {
					if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addUnFiltro();
					continue;
				}
				int numeroDeSubProblemas = p.getNumeroSubProblemas(a);
				List<Sp<A>> solucionesDeSubProblemas = Lists2.empty();  
				boolean haySolucion = true;
				for(int i = 0; i < numeroDeSubProblemas; i++){
					P pr = p.getSubProblema(a,i); 
					Sp<A> sp = pD(pr);
					if(sp==null) { haySolucion=false; break;}					
					solucionesDeSubProblemas.add(sp);   	    		
				}
				Sp<A> sa = haySolucion?p.getSolucionParcialPorAlternativa(a, solucionesDeSubProblemas): null;
				solucionesDeAlternativas.add(sa);
			}
			solucionesDeAlternativas = solucionesDeAlternativas.stream().
					filter(x -> x != null).collect(Collectors.toList());
			if (!solucionesDeAlternativas.isEmpty()) {
				e = p.getSolucionParcial(solucionesDeAlternativas);
			}
			if(e!=null) {
				e.solucionesDeAlternativas = solucionesDeAlternativas;
			}
			guardaEnMemoria(p, e); 			
		}
		if (conFiltro && solucionesParciales.get(p)!=null) {
			actualizaMejorValor(p);
		}
		return e;
	}	
	
	/**
	 * 
	 * @return Solución del problema inicial o null si no tiene solución o no ha ha sido encontrado por el algoritmo
	 */
	
	public S getSolucion() {
		return getSolucion(problema);
	}
	
	/**
	 * @param pd - Problema del que se quiere obtener la solución
	 * @return Solución del problema o null si no tiene solución o no ha ha sido encontrado por el algoritmo
	 */
	@SuppressWarnings("unchecked")
	public S getSolucion(P pd) {	
		S s = null;
		if (solucionesParciales.containsKey(pd)) {
			Sp<A> e = solucionesParciales.get(pd);
			if (e != null) {
				if (pd.esCasoBase()) {					
					s = pd.getSolucionReconstruidaCasoBase(e);
				} else if (e.alternativa != null) {
					List<S> soluciones = Lists2.<S> empty();
					for (int i = 0; i < pd.getNumeroSubProblemas(e.alternativa); i++) {
						soluciones.add(getSolucion(pd.getSubProblema(e.alternativa, i)));
					}
					s = pd.getSolucionReconstruidaCasoRecursivo(e, soluciones);
				} else if (e.alternativa == null) {
					s = (S) e.valorDeObjetivo;
				}
			}
		}
		return s;
	}

	/**
	 * @param nombre - Fichero dónde se almacenará el grafo para ser representado
	 * @param titulo - Título del gráfico
	 * 
	 */
	public void showAllGraph(String nombre,String titulo){
		showAllGraph(nombre,titulo,problema);
	}
	
	/**
	 * @param nombre - Fichero dónde se almacenará el grafo para ser representado
	 * @param titulo - Título del gráfico
	 * @param pd - Problema y sus subproblemas que forman el grafo
	 */
	public void showAllGraph(String nombre,String titulo,P pd){
			
		if (solucionesParciales.get(pd).alternativa!=null) {
			Files2.setFile(nombre);
			Files2.getFile().println("digraph "+titulo+" {  \n size=\"100,100\"; ");	
			marcarEnSolucion(pd);
			Set<P> visitados = Sets2.newHashSet();
			showAll(pd,visitados);
			Files2.getFile().println("}");
		}
	}
		
	
	private void marcarEnSolucion(P pd){
		if(solucionesParciales.containsKey(pd)){
			Sp<A> e = solucionesParciales.get(pd);		
			if(e!=null){
				e.estaEnLaSolucion =true;
				A alternativa = e.alternativa;			
				if (!pd.esCasoBase()) {
					for (int i = 0; i < pd.getNumeroSubProblemas(alternativa); i++) {
						var pds = pd.getSubProblema(alternativa, i);
						marcarEnSolucion(pds);
					}
				}	
			}
		}
	}

	private String problema(P p, Sp<A> e){
		String s= "    "+"\""+this.idsDeProblemas.get(p)+"\"";
		if(e!=null){
			if (p.esCasoBase()) {
				s = s + " [shape=box, style=dotted, label=\"" + p + "\"]";
			}else{
				s = s + " [shape=box, label=\"" + p + "\"]";
			}
		} else{
			s = s+" [shape=diamond, label=\""+p+"\"]";
		}
		return s+";";
	}
	
	private String alternativa(P p, A alternativa){
		String s = "    "+"\""+this.idsDeProblemas.get(p)+","+alternativa+"\""+" [label="+alternativa+"]";
		return s+";";
	}
	
	private String aristaProblemaToAlternativa(P p, A alternativa, Sp<A> e){
		String s = "    "+"\""+this.idsDeProblemas.get(p)+"\""+" -> "+"\""+this.idsDeProblemas.get(p)+","+alternativa+"\"";
		if(e.estaEnLaSolucion && e.alternativa.equals(alternativa)){
			s = s+ "[style=bold,arrowhead=dot]";
		}
		return s+";";
	}
	
	private String aristaAlternativaToProblema(P p, A alternativa, P ps, Sp<A> e){
		String s = "    "+"\""+this.idsDeProblemas.get(p)+","+alternativa+"\""+" -> "+"\""+this.idsDeProblemas.get(ps)+"\"";
		if(e.estaEnLaSolucion && e.alternativa.equals(alternativa)){
			s = s+ "[style=bold,arrowhead=dot]";
		}
		return s+";";
	}


	private void showAll(P p, Set<P> visitados) {
		if(visitados.contains(p)) return;
		visitados.add(p);
		Sp<A> e = solucionesParciales.get(p);
		Files2.getFile().println(problema(p, e));
		if (!p.esCasoBase()) {
				for (Sp<A> solParAlt : e.solucionesDeAlternativas) {
					Files2.getFile().println(alternativa(p, solParAlt.alternativa));
					Files2.getFile().println(aristaProblemaToAlternativa(p,solParAlt.alternativa, e));
					for (int i = 0; i < p.getNumeroSubProblemas(solParAlt.alternativa); i++) {
						var pds = p.getSubProblema(solParAlt.alternativa, i);
						Files2.getFile().println(aristaAlternativaToProblema(p,solParAlt.alternativa, pds, e));
						showAll(pds,visitados);
					}
				}
		}
	}
	
	/**
	 * Un tipo diseñado para representar soluciones parciales a partir de las cuales 
	 * se puede reconstruir la solución del problema. 
	 * Esta formado por un par: una alternativa y el valor de una propiedad. La solución del problema 
	 * es la que se obtendría tomando la alternativa y el valor estaría en la propiedad
	 * 
	 * El valor null para este tipo representará la no existencia de solución
	 * 
	 * @param <A1> Tipo de la alternativa
	 */
	public static class Sp<A1> implements Comparable<Sp<A1>> {
		

		public A1 alternativa;
		public Double valorDeObjetivo;
		private Object problema;
		private List<Sp<A1>> solucionesDeAlternativas = null; 
		private boolean estaEnLaSolucion = false;
					
		public static <A2> Sp<A2> create(A2 alternativa,Double propiedad){
			return new Sp<A2>(alternativa, propiedad);
		}

		protected Sp(A1 alternativa, Double propiedad) {
			super();
			if(propiedad==null) throw new IllegalArgumentException("Propiedad es null");
			this.alternativa = alternativa;
			this.valorDeObjetivo = propiedad;	
		}		
		
		@Override
		public String toString(){
			return "("+alternativa+","+valorDeObjetivo+")";
		}

		@Override
		public int compareTo(Sp<A1> ob) {
			return this.valorDeObjetivo.compareTo(ob.valorDeObjetivo);
		}

		@SuppressWarnings("unchecked")
		public <P> P getProblema() {
			return (P) problema;
		}
		
		
		
	}
	
}
