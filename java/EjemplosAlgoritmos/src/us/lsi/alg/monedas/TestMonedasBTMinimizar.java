package us.lsi.alg.monedas;

	
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;



	import us.lsi.graphs.Graphs2;
	import us.lsi.graphs.alg.BT;
	import us.lsi.graphs.alg.BackTracking;
	import us.lsi.graphs.alg.BackTracking.BTType;

	import us.lsi.graphs.virtual.EGraph;

	public class TestMonedasBTMinimizar {

		public static void main(String[] args) {
			Locale.setDefault(new Locale("en", "US"));
			MonedaVertex.datosIniciales("ficheros/monedas2.txt", 78);
			
//			Collections.sort(Moneda.monedas,Comparator.comparing(m -> m.pesoUnitario()));
			Collections.shuffle(Moneda.monedas);

			MonedaVertex e3 = MonedaVertex.first();
		
			EGraph<MonedaVertex, MonedaEdge> graph = Graphs2.simpleVirtualGraphSum(e3);

			BackTracking<MonedaVertex, MonedaEdge,SolucionMonedas> ms2 = BT.backTrackingGoal(
					graph, 
					v->v.index()==MonedaVertex.n,
					(v1,p,v2)->0., 
					SolucionMonedas::of,
					MonedaVertex::copy,
					BTType.Min);
			
			
			ms2.search();
			System.out.println("Minimiza peso: \n " + ms2.getSolution());
			System.out.println("Todas: \n " + ms2.getSolutions());

		}
}

