package us.lsi.bt.sudoku;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Files2;
import us.lsi.common.Preconditions;

public class DatosSudoku {
	
	/**
	 * Tamaño de un subcuadro
	 */
	public static Integer tamSubCuadro = 3;
	/**
	 * Número de filas
	 */
	public static Integer numeroDeFilas = tamSubCuadro*tamSubCuadro;
	/**
	 * Numero de casillas
	 */
	public static Integer numeroDeCasillas = numeroDeFilas*numeroDeFilas;

	private static Map<Casilla,Casilla> casillas = new HashMap<>();
	private static Map<Casilla,Set<Casilla>> casillasEnConflicto = new HashMap<>();
	private static List<Set<Casilla>> casillasEnFila;
	private static List<Set<Casilla>> casillasEnColumna;
	private static List<Set<Casilla>> casillasEnSubCuadro;
	private static List<Casilla> casillasLibres;
	private static Comparator<Casilla> orden = 
		Comparator.comparing(c->getValoresLibresEnCasilla(c).size());
	
	public static Casilla addCasilla(String s){
		Casilla c = Casilla.create(s);
		if(!casillas.containsKey(c)) {
			casillas.put(c, c);
		}
		return casillas.get(c);
	}
	
	public static Casilla addCasilla(Integer p){
		Casilla c = Casilla.create(p);
		if(!casillas.containsKey(c)) {
			casillas.put(c, c);
		}
		return casillas.get(c);
	}
	
	public static Casilla get(Integer p) {
		Casilla c = Casilla.create(p);
		Casilla r = casillas.get(c);
		return r;
	}

	public static Casilla get(Integer x, Integer y) {
		Casilla c = Casilla.create(x, y, null);
		Casilla r = casillas.get(c);
		return r;
	}
	
	public static Set<Casilla> getCasillasEnFila(int y){
		return casillasEnFila.get(y);
	}
	
	public static Set<Casilla> getCasillasEnColumna(int x){
		return casillasEnColumna.get(x);
	}
	
	public static Set<Casilla> getCasillasEnSubCuadro(int sc){
		return casillasEnSubCuadro.get(sc);
	}
	
	/**
	 * @param nf Fichero de datos
	 * @post Inicializa las variables del tipo
	 */
	public static void iniDatos(String nf) {
		Files2.streamFromFile(nf)
				.forEach(s -> DatosSudoku.addCasilla(s));
		IntStream.range(0,numeroDeCasillas)				
				.forEach(p -> DatosSudoku.addCasilla(p));		
		iniFilas();
		iniColumnas();
		iniSubCuadros();
		casillasLibres = casillasLibres();
		check();
	}

	private static void check() {		
		getCasillas()
		.stream()
		.filter(c->!c.isFree())
		.forEach(c->
			Preconditions.checkArgument(
				!getValoresOcupadosEnCasilla(c).contains(c.getValue()),
				"La casilla "+c+" está mal"));
	}

	public static Set<Casilla> getCasillas(){
		return casillas.keySet();
	}
	
	public static List<Casilla> getCasillasLibres(){
		return casillasLibres;
	}
	
	public static Casilla getCasillaLibre(int index){
		return casillasLibres.get(index);
	}
	
	public static Set<Casilla> getCasillasEnConflicto(Casilla c){
		if(!casillasEnConflicto.containsKey(c)) {
			Set<Casilla> r = new HashSet<>();
			r.addAll(getCasillasEnFila(c.getY()));
			r.addAll(getCasillasEnColumna(c.getX()));
			r.addAll(getCasillasEnSubCuadro(c.getSubCuadro()));
			casillasEnConflicto.put(c, r);
		}
		return casillasEnConflicto.get(c);
	}
	
	public static void ordena(Integer index) {
		List<Casilla> sl = casillasLibres.subList(index, casillasLibres.size());
		sl.sort(orden);
	}
	
	private static Set<Integer> todosLosValores =
			IntStream.rangeClosed(1, numeroDeFilas)
					 .boxed()
					 .collect(Collectors.toSet());
	
	public static List<Integer> getValoresLibresEnCasilla(Casilla c){
		List<Integer> r = new ArrayList<>(todosLosValores);
		r.removeAll(getValoresOcupadosEnCasilla(c));
		return r;
	}
	
	public static Set<Integer> getValoresOcupadosEnCasilla(Casilla c){
		return getCasillasEnConflicto(c)
				.stream()
				.filter(x->!x.isFree()&&!x.equals(c))
				.map(x->x.getValue())
				.collect(Collectors.toSet());
	}
	
	public static Set<Integer> getValoresOcupadosEnFila(Integer y){
		return getCasillas()
				.stream()
				.filter(c->c.getY()==y && !c.isFree())
				.map(c->c.getValue())
				.collect(Collectors.toSet());
	}
	
	public static Set<Integer> getValoresOcupadosEnColumna(Integer x){
		return getCasillas()
				.stream()
				.filter(c->c.getX()==x && !c.isFree())
				.map(c->c.getValue())
				.collect(Collectors.toSet());
	}
	
	public static Set<Integer> getValoresOcupadosEnSubCuadro(Integer sc){
		return getCasillas()
				.stream()
				.filter(c->c.getSubCuadro()==sc && !c.isFree())
				.map(c->c.getValue())
				.collect(Collectors.toSet());
	}
	
	private static List<Casilla> casillasLibres(){ 
		return DatosSudoku.getCasillas()
					   .stream()
					   .filter(c->c.isFree())
					   .collect(Collectors.toList());
	}
	/**
	 * @return Una lista de conjuntos vacios
	 */
	private static List<Set<Casilla>> getListOfEmptySet() {
		return IntStream.range(0, numeroDeFilas)
		.boxed()
		.map(x->new HashSet<Casilla>())
		.collect(Collectors.toList());
	}
	
	private static void iniFilas() {
		casillasEnFila = getListOfEmptySet();
		getCasillas().stream()
				.forEach(c->casillasEnFila.get(c.getY()).add(c));
	}	
	private static void iniSubCuadros() {
		casillasEnSubCuadro = getListOfEmptySet();
		getCasillas().stream()
			.forEach(c->casillasEnSubCuadro.get(c.getSubCuadro()).add(c));
	}
	
	private static void iniColumnas() {
		casillasEnColumna = getListOfEmptySet();
		getCasillas().stream()
				.forEach(c->casillasEnColumna.get(c.getX()).add(c));
	}
		
}
