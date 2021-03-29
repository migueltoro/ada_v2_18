package us.lsi.sa;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.random.JDKRandomGenerator;
import us.lsi.math.Math2;



	/**
	 * <p> Implementación del Algoritmo de Simulated Annealing. Un problema que se quiera resolver por este algortimo
	 * debe implementar el interface ProblemaSA &lt; E,S,A &gt; </p>
	 * 
	 * <p> Para usar esta técnica hay que considerar un conjunto de estado y unas alternativas para pasar de unos a otros.
	 * El estado que minimice el objetivo debe ser alcanzable desde el estado inicial a través de una secuencia de 
	 * alternativas. </p>
	 * 
	 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema16.pdf" target="_blank">Tema16</a></p>
	 * 
	 * <p>Un resumen de la a documentación puede encontarse en el: <a href="../../../document/SimulatedAnn.html" target="_blank">Tema16</a></p>
	 * 
	 * @author Miguel Toro
	 *
	 */
public class AlgoritmoSA {

	/**
	 *
	 * @param estado El estado del cromosoma
	 * @return AlgoritmoSA
	 */

	public static AlgoritmoSA of(StateSa estado) {
			return new AlgoritmoSA(estado);
	}

	/**
	 * Conjunto de soluciones encontradas
	 */
	public Set<StateSa> soluciones;
	/**
	 * Mejor solución encontrada
	 */
	public StateSa mejorSolucionEncontrada = null;
	/**
	 * Número de intentos. En cada intento se parte del estado incial y se llevan a
	 * cabo un número de iteraciones por intento. En cada iteración se llevan a cabo
	 * un número de iteraciones sin disminuir la temperatura.
	 */
	public static Integer numeroDeIntentos = 10;
	/**
	 * El número iteraciones por intento. Los designaremos por n.
	 */
	public static Integer numeroDeIteracionesPorIntento = 200;
	/**
	 * El número iteraciones a la misma temperatura. Lo designaremos por m.
	 */
	public static Integer numeroDeIteracionesALaMismaTemperatura = 10;
	/**
	 * La temperatura fijada inicialmente. Lo designaremos por t0.
	 */
	public static double temperaturaInicial = 1000;
	/**
	 * 
	 * <p>
	 * La temperatura disminuye en ese factor en cada iteración: t = &alpha;&#42;t.
	 * Por lo que t = t0&#42;&alpha; &#94; i.
	 * </p>
	 * <p>
	 * El número total de iteraciones es m&#42;n. Este número es una medida del
	 * tiempo de ejecución del algoritmo.
	 * </p>
	 * <p>
	 * La probabilidad p de aceptar un cambio de estado de tamaño &Delta; depende de
	 * la temperatura en la forma p = e &#94; (-&Delta;/(t0&#42;&alpha; &#94; i))
	 * con 0 &lt; i &lt; = n.
	 * </p>
	 * <p>
	 * Sea p0 la probabilidad de aceptar un cambio en i = 0 y pf la probabilidad de
	 * aceptarlo en i = n.
	 * </p>
	 * <p>
	 * Escogiendo t0 = 100*&Delta; tenemos p0 = e &#94; (-&Delta;/t0) =e &#94;
	 * (-/100) = 0.99 Y a partir de pf = e &#94; (-1/(100*&alpha; &#94; n) o &alpha;
	 * &#94; n = -1/(100&#42;ln(pf)) obtenemos valores para n, &alpha;, pf. Un caso
	 * típico &alpha; = 0.97, n = 200, pf = 0.01
	 * </p>
	 * 
	 * @constraint p = e &#94; (-&Delta;/(t0&#42;&alpha; &#94; i)) con 0 &lt; i &lt;
	 *             = n.
	 * @constraint 0 &lt; &alpha; &lt; 1
	 */
	public static double alfa = 0.97;

	private double temperatura;
//	private boolean parar = false;
	private StateSa estado;
	private StateSa nextEstado;

	
	private AlgoritmoSA(StateSa estado) {
		this.estado = estado;
		this.mejorSolucionEncontrada = estado;
		this.soluciones = new HashSet<>();
		JDKRandomGenerator random = new JDKRandomGenerator();
		random.setSeed((int) System.currentTimeMillis());
		Math2.rnd = random;
	}

	/**
	 * Ejecución del algoritmo
	 */
	public void ejecuta() {
		this.mejorSolucionEncontrada = this.estado.random();
		for (Integer n = 0; n < numeroDeIntentos; n++) {
			this.temperatura = temperaturaInicial;
			this.estado = this.estado.random();
			for (int numeroDeIteraciones = 0;
				     numeroDeIteraciones < numeroDeIteracionesPorIntento; numeroDeIteraciones++) {
				for (int s = 0; s < numeroDeIteracionesALaMismaTemperatura; s++) {
					this.nextEstado = this.estado.mutate();
					double incr = nextEstado.fitness() - estado.fitness();
					if (aceptaCambio(incr)) {
						estado = nextEstado;
						actualizaMejorValor();
					}
				}
				this.temperatura = nexTemperatura(numeroDeIteraciones);
			}
			soluciones.add(this.estado);
		}
	}

	private void actualizaMejorValor() {
		if (estado.fitness() < mejorSolucionEncontrada.fitness()) {
			mejorSolucionEncontrada = estado;
		}
	}

	private double nexTemperatura(int numeroDeIteraciones) {
		return alfa * temperatura;
//		return temperaturaInicial/Math.log(2+3*numeroDeIteraciones);
	}

	private boolean aceptaCambio(double incr) {
		return Math2.aceptaBoltzmann(incr, temperatura);
	}

}
