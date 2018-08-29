package us.lsi.dyv.ejemplos;

import java.util.List;

import us.lsi.dyv.AlgoritmoDyV;
import us.lsi.dyv.ProblemaDyV;

public class Ejemplos {

	public static class Fibonacci implements ProblemaDyV<Long,Long> {

		

		private int n;	
		
		public static Fibonacci create(int n) {
			return new Fibonacci(n);
		}
		
		public Fibonacci(int n) {
			super();
			this.n = n;
		}

		@Override
		public int size() {
			return n;
		}

		@Override
		public boolean esCasoBase() {
			return n <=1;
		}

		@Override
		public Long getSolucionCasoBase() {
			return (long) n;
		}

		@Override
		public Long combina(List<Long> soluciones) {
			return soluciones.get(0)+soluciones.get(1);
		}

		@Override
		public ProblemaDyV<Long, Long> getSubProblema(int i) {
			return Fibonacci.create(this.n-1-i);
		}

		@Override
		public int getNumeroDeSubProblemas() {
			return 2;
		}

		@Override
		public Long getSolucion(Long e) {
			return e;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + n;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Fibonacci other = (Fibonacci) obj;
			if (n != other.n)
				return false;
			return true;
		}
		
	}
	
	public static void main(String[] args) {	
		var a = AlgoritmoDyV.createDyVCM(Fibonacci.create(30));
		a.ejecuta();
		System.out.println(a.getSolucion());
	}

}
