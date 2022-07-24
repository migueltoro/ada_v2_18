package us.lsi.curvefitting;

import java.util.List;
import java.util.Locale;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

public abstract class TestPolinomialLog {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "us"));
		List<WeightedObservedPoint> points = DataCurveFitting.points("ficheros/datos_polinomiallog.txt");
		double[] start = {1.,1.,1.,1.};
		PolinomialLog.of().print(30,start);		
		final double[] s_coeff = PolinomialLog.of().fit(points,start);
		System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f,d = %.2f",s_coeff[0],s_coeff[1],s_coeff[2],s_coeff[3]));
	}

}
