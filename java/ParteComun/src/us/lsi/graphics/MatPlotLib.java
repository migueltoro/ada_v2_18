package us.lsi.graphics;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import us.lsi.curvefitting.DataCurveFitting;

public class MatPlotLib {
	
	public static void show(String dataFile, Function<Double,Double> f, String title) {
		List<WeightedObservedPoint> points = DataCurveFitting.points(dataFile);
		List<Double> lx = points.stream().map(p->p.getX()).toList();
		List<Double> ajuste = points.stream().map(p->f.apply(p.getX())).toList();
		List<Double> datos = points.stream().map(p->p.getY()).toList();		
		Plot plt = Plot.create();
		
		plt.plot()
		    .add(lx,ajuste)
		    .label("ajuste")
		    .linestyle("--");
		    
		plt.plot()
			.add(lx,datos)
			.label("datos")
			.linestyle(":")
			.linewidth(3.5);

		plt.title(title);
		plt.legend();	
		plt.xlabel("tamaño");
		plt.ylabel("tiempo");
		try {
			plt.show();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PythonExecutionException e) {
			e.printStackTrace();
		}
	}

}
