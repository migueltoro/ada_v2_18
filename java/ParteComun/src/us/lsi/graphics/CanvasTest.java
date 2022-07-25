package us.lsi.graphics;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

import us.lsi.curvefitting.DataCurveFitting;
import us.lsi.curvefitting.PolinomialLog;

public class CanvasTest {

	public static void main(String[] args) {
		Random r = new Random();
		Canvas c = Canvas.of("Ajuste","a*n^b*(ln n)^c + d");
		List<WeightedObservedPoint> points = DataCurveFitting.points("ficheros/datos_polinomiallog.txt");
		List<Double> lx = points.stream().map(x->x.getX()).toList();
		double[] p = {1.00,2.50,2.00,10.00};
		List<Double> ajuste = lx.stream().map(x->PolinomialLog.of().value(x,p)).toList();
		List<Double> datos = points.stream().map(x->x.getY()+300000*r.nextDouble()-150000).map(x->x>0?x:0).toList();	
		c.drawData("Datos", Color.RED, lx, datos, true);
		c.drawData("Ajuste", Color.BLUE, lx, ajuste, false);
	}

}
