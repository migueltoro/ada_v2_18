package curvefitting;

import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

import us.lsi.streams.Stream2;

import org.apache.commons.math3.fitting.SimpleCurveFitter;

/**
 * @author migueltoro
 * 
 * Una curva de la forma a*n^b*(ln n)^c + d
 *
 */
public class PolinomialLog implements ParametricUnivariateFunction {
	
	private static PolinomialLog pl = null;
	
	public static PolinomialLog of() {
		if(pl == null) pl = new PolinomialLog();
		return pl;
	}
	
	public static WeightedObservedPoint parse(String line) {
		String[] p = line.split(",");
		return new WeightedObservedPoint(1.,Double.parseDouble(p[0]),Double.parseDouble(p[1]));
	}
	
	public static List<WeightedObservedPoint> points(String file){
		Stream<String> s = Stream2.file(file);
		List<WeightedObservedPoint> points = s.map(ln->PolinomialLog.parse(ln)).toList();
		return points;
	}

	@Override
	public double[] gradient(double n, double... p) {
		Double a = p[0];
		Double b = p[1];
		Double c = p[2];
		Double a0 = Math.pow(n,b)*Math.pow(Math.log(n),c);
		Double b0 = a*Math.pow(n,b)*Math.pow(Math.log(n),c+1);
		Double c0 = a*Math.pow(n,b)*Math.log(Math.log(n))*Math.pow(Math.log(n),c);
		Double d0 = 1.;
		double[] r = {a0,b0,c0,d0};
		return r;
	}

	@Override
	public double value(double n, double... p) {
		Double a = p[0];
		Double b = p[1];
		Double c = p[2];
		Double d = p[3];
		return a*Math.pow(n,b)*Math.pow(Math.log(n),c) + d;
	}
	
	public double[] fit(List<WeightedObservedPoint> points, double[] start) {
		final SimpleCurveFitter fitter = SimpleCurveFitter.create(PolinomialLog.of(),start);
		return fitter.fit(points);
	}
	
	public void print(double n, double... p) {
		String r = String.format("Values: n = %.2f,a = %.2f,b = %.2f,c = %.2f,d = %.2f",n, p[0],p[1],p[2],p[3]);
		System.out.println(r);
		System.out.println("Value = "+this.value(n, p));
		double[] g = this.gradient(n, p);
		System.out.println("Gradiente = "+String.format("%.2f,%.2f,%.2f,%.2f",g[0],g[1],g[2],g[3]));
	}
	
	public static void testFile(String file) {
		Locale.setDefault(new Locale("en", "us"));
		Double a = 1.;
		Double b = 2.5;
		Double c = 2.;
		Double d = 10.;
		double[] p = {a,b,c,d};
		List<String> points = IntStream.range(0, 100).boxed()
				.map(n->n+10.)
				.map(n->new WeightedObservedPoint(1.,n,PolinomialLog.of().value(n,p)))
				.map(n->String.format("%f,%f",n.getX(),n.getY()))
				.toList();
		Stream2.writeStream(points.stream(), file);
	}
	
	public static void main(String[] args) {
		PolinomialLog.testFile("ficheros/datos_fit.txt");
		List<WeightedObservedPoint> points = PolinomialLog.points("ficheros/datos_fit.txt");
		double[] start = {1.,1.,1.,1.};
		final double[] s_coeff = PolinomialLog.of().fit(points,start);
		System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f,d = %.2f",s_coeff[0],s_coeff[1],s_coeff[2],s_coeff[3]));
	}

}
