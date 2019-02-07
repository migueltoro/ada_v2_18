package us.lsi.pl;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.lpsolve.solution.AlgoritmoPLI;
import us.lsi.lpsolve.solution.SolutionPLI;
import us.lsi.mochila.datos.DatosMochila;

public class MochilaPLI{

		
		public static String getConstraints(){
			DatosMochila.iniDatos("ficheros/objetosMochila.txt");
			DatosMochila.capacidadInicial = 78;
			int num = DatosMochila.getObjetos().size();
			String r = "";
			r = r+"max: ";
			for(int i =0;i<num;i++){
				if (i!=0) r = r+"+";
				r = r +String.format("%d*x%d",DatosMochila.getValor(i),i);
			}		
			r = r+";\n\n";
			for(int i =0;i<num;i++){
				if (i!=0) r = r+"+";
				r = r +String.format("%d*x%d",DatosMochila.getPeso(i),i);
			}
			r = r +"<=";
			r = r +DatosMochila.capacidadInicial;
			r = r+";\n\n";
			for(int i =0;i<num;i++){
				r = r + String.format("x%d",i) + "<="+DatosMochila.getNumMaxDeUnidades(i)+";\n";
			}
			r = r +"int ";
			for(int i =0;i<num;i++){
				if (i!=0) r = r+",";
				r = r + String.format("x%d",i);
			}
			r = r+";\n\n\n";		
			return r;
		}

		public static String getConstraints2(){
			DatosMochila.iniDatos("ficheros/objetosMochila.txt");
			DatosMochila.capacidadInicial = 78;
			int num = DatosMochila.getObjetos().size();
			String r = "max: ";
			r = r + IntStream.range(0, num)
					.boxed()
					.map(i->String.format("%d*x%d",DatosMochila.getValor(i),i))
					.collect(Collectors.joining("+", "", ";\n\n"));
			
			r = r + IntStream.range(0, num)
					.boxed()
					.map(i->String.format("%d*x%d",DatosMochila.getPeso(i),i))
					.collect(Collectors.joining("+", "", " <= "+ DatosMochila.capacidadInicial+";\n\n"));
			
			r = r + IntStream.range(0, num)
					.boxed()
					.map(i->String.format("x%d",i)+"<="+DatosMochila.getNumMaxDeUnidades(i)+";\n")
					.collect(Collectors.joining("","","\n"));
			
			r = r +"int ";
			r = r + IntStream.range(0, num)
					.boxed()
					.map(i->" x"+i)
					.collect(Collectors.joining(",","",";\n"));		
			r = r +"\n\n";
			return r;
		}

	
	public static void main(String[] args) {	
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;		
		System.out.println(DatosMochila.getObjetos());
		System.out.println(getConstraints());
		System.out.println(getConstraints2());
		SolutionPLI s = AlgoritmoPLI.getSolution(getConstraints2());
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println(s.getGoal());
		System.out.println("________");
		System.out.println("________");
		for(int i=0;i<s.getNumVar();i++){
			System.out.println(s.getName(i)+" = "+s.getSolution(i));
		}

	}

}
