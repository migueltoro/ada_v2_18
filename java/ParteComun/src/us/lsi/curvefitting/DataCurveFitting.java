package us.lsi.curvefitting;

import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

import us.lsi.streams.Stream2;

public class DataCurveFitting {

	public static WeightedObservedPoint parse(String line) {
		String[] p = line.split(",");
		return new WeightedObservedPoint(1.,Double.parseDouble(p[0]),Double.parseDouble(p[1]));
	}

	public static List<WeightedObservedPoint> points(String file){
		Stream<String> s = Stream2.file(file);
		List<WeightedObservedPoint> points = s.map(ln->parse(ln)).toList();
		return points;
	}

	public static void testFilePolinomialLog(String file) {
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
	
	public static void testFileExponential(String file) {
		Locale.setDefault(new Locale("en", "us"));
		Double a = 1.;
		Double b = 2.5;
		Double c = 2.;
		Double d = 10.;
		double[] p = {a,b,c,d};
		List<String> points = IntStream.range(0, 100).boxed()
				.map(n->n+10.)
				.map(n->new WeightedObservedPoint(1.,n,Exponential.of().value(n,p)))
				.map(n->String.format("%f,%f",n.getX(),n.getY()))
				.toList();
		Stream2.writeStream(points.stream(), file);
	}
	public static void testFilePolynomial(String file) {
		Locale.setDefault(new Locale("en", "us"));
		Double a = 5.;
		Double b = 2.5;
		Double c = 2.;
		List<String> points = IntStream.range(0, 100).boxed()
				.map(n->n+10.)
				.map(n->new WeightedObservedPoint(1.,n,a*n*n+b*n+c))
				.map(n->String.format("%f,%f",n.getX(),n.getY()))
				.toList();
		Stream2.writeStream(points.stream(), file);
	}
}
