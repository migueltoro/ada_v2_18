package us.lsi.common;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.math3.fraction.Fraction;


/**
 * @author migueltoro
 *
 * @param <E> El tipo de los elementos de la matriz que deben ser elementos de un campo num�rico
 */
public class Matrix<E> {
	
	
	public static <E> Matrix<E> of(E[] datos, Integer nf, Integer nc) {
		Preconditions.checkArgument(datos.length == nf * nc, "Datos no v�lidos");
		@SuppressWarnings("unchecked")
		E[][] dd = (E[][]) Array.newInstance(datos[0].getClass(), nf, nc);
		for (int f = 0; f < nf; f++) {
			for (int c = 0; c < nc; c++) {
				Integer i = f * nc + c;
				dd[f][c] = datos[i];
			}
		}
		Matrix<E> r = Matrix.of(dd);
		return r;
	}
	
	public static <E> Matrix<E> of(E[][] datos) {
		return new Matrix<E>(datos);
	}
	
	
	
	
	public static <E> Matrix<E> of(Integer nf, Integer nc, E value) {
		@SuppressWarnings("unchecked")
		E[][] datos = (E[][]) Array.newInstance(value.getClass(),nf,nc);
		Matrix<E> r = Matrix.of(datos);
		for (int i = 0; i < nf; i++) {
			for (int j = 0; j < nc; j++)
				r.set(i,j,value);
		}
		return r;
	}
	
	public static  Matrix<Integer> random(Integer nf, Integer nc, Integer a, Integer b){	
		Integer[][] datos = new Integer[nf][nc];
		Matrix<Integer> r = Matrix.of(datos);
		Random rr = new Random(System.nanoTime());
		for (int i = 0; i < nf; i++) {
			for (int j = 0; j < nc; j++)
				r.set(i,j,a+rr.nextInt(b-a));
		}
		return r;
		
	}
	
	public static <E> Matrix<E> of(Integer nf, E value) {
		@SuppressWarnings("unchecked")
		E[][] datos = (E[][]) Array.newInstance(value.getClass(),nf,nf);
		Matrix<E> r = Matrix.of(datos);
		for (int i = 0; i < nf; i++) {
			for (int j = 0; j < nf; j++)
				r.set(i,j,value);
		}
		return r;
	}
	
	protected E[][] datos;
	protected int nf;
	protected int nc;
	protected int iv;	
	protected int jv;
	

	protected Matrix(E[][] datos) {
		super();
		this.datos = datos;		
		this.nf = datos.length;
		this.nc = datos[0].length;
		this.iv = 0;
		this.jv = 0;
	}

	private Matrix(E[][] datos, Integer nf, Integer nc, Integer iv, Integer jv) {
		super();
		this.datos = datos;
		this.nf = nf;
		this.nc = nc;
		this.iv = iv;
		this.jv = jv;
	}

	public E get(Integer i, Integer j) {
		return this.datos[this.iv+i][this.jv+j];
	}
	
	public void set(Integer i, Integer j, E value) {
		this.datos[this.iv+i][this.jv+j] = value;
	}
	
	public Integer nf() {
		return this.nf;
	}
	
	public Integer nc() {
		return this.nc;
	}
	
	public <R> Matrix<R> map(Function<E,R> f){
		R v = f.apply(this.get(0, 0));
		@SuppressWarnings("unchecked")
		R[][] datos = (R[][]) Array.newInstance(v.getClass(),this.nf,this.nc);
		Matrix<R> r = Matrix.of(datos);
		for (int i = 0; i < this.nf; i++) {
			for (int j = 0; j < this.nc; j++)
				r.set(i,j,f.apply(this.get(i, j)));
		}
		return r;
	}
	
	public Matrix<E> view(Integer nf, Integer nc, Integer iv, Integer jv){
		Matrix<E> r = of(this.datos);
		r.iv = this.iv +iv; r.nf = nf; r.jv = this.jv +jv; r.nc = nc;
		return r;
	}
	
	public Matrix<E> view(Integer nv){
		Preconditions.checkArgument(this.nf>1 && this.nc>1,
				String.format("Las filas y las columnas deben ser mayor que 1 y son nf = %d, nc = %d",this.nf,this.nc));
		int nf = this.nf/2;
		int nc = this.nc/2;
		Matrix<E> r = of(this.datos);
		switch(nv){
		case 0:  r = view(nf,nc,0,0); break;
		case 1:  r = view(nf,this.nc-nc,0,nc); break;
		case 2:  r = view(this.nf-nf,nc,nf,0); break;
		case 3:  r = view(this.nf-nf,this.nc-nc,nf,nc); break;
		default: Preconditions.checkArgument(false, "Opci�n no v�lida");
		}
		return r;
	}
	
	public void print(String title) {
		System.out.println(String.format("\n%s = (nf = %d, nc = %d, iv = %d, , jv = %d)\n", title, this.nf, this.nc,
				this.iv, this.jv));
		Function<Integer, String> f = i -> IntStream.range(0, this.nc).boxed()
				.map(j -> this.get(i, j).toString())
				.collect(Collectors.joining(", ", "[", "]"));
		String s = IntStream.range(0, this.nf).boxed()
				.map(i -> f.apply(i)).collect(Collectors.joining(",\n", "[", "]"));
		System.out.println(s);

	}
	
	@Override
	public String toString() {
		Function<Integer, String> f = i -> IntStream.range(0, this.nc).boxed()
				.map(j -> this.get(i, j).toString())
				.collect(Collectors.joining(", ", "[", "]"));
		String s = IntStream.range(0, this.nf).boxed()
				.map(i -> f.apply(i)).collect(Collectors.joining(",\n", "[", "]"));
		return s;
	}
	
	public Matrix<E> copy(){
		return new Matrix<>(this.datos, this.nf, this.nc, this.iv,this.jv);
	}
	
	public void copy(Matrix<E> r){
		Boolean ch = this.nc==r.nc && this.nf==r.nf;
		Preconditions.checkArgument(ch, "No se cumplen condiciones de copia");
	    for (int i = 0; i < this.nf; i++) {
	        for (int j = 0; j < this.nc; j++) {
	            r.set(i,j,this.get(i, j));
	        }
	    }
	}
	
	public static <E> void copy(Matrix<E> out, Matrix<E> in){
		Boolean ch = in.nc==out.nc && in.nf==out.nf;
		Preconditions.checkArgument(ch, "No se cumplen condiciones de copia");
	    for (int i = 0; i < in.nf; i++) {
	        for (int j = 0; j < in.nc; j++) {
	            out.set(i,j,in.get(i, j));
	        }
	    }
	}
	
	public View4<Matrix<E>> views() {
		return View4.of(this.view(0),this.view(1),this.view(2),this.view(3));
	}
	
	public static <E> Matrix<E> compose(Matrix<E> a, Matrix<E> b, Matrix<E> c, Matrix<E> d) {
		int nf = a.nf+c.nf;
		int nc = a.nc+ b.nc;
		E e = a.get(0, 0);
		Matrix<E> r = Matrix.of(nf,nc,e);
		View4<Matrix<E>> vr = r.views();
		a.copy(vr.a());
		b.copy(vr.b());
		c.copy(vr.c());
		d.copy(vr.d());
		return r;
	}
	
	public List<E> corners() {
		return List.of(this.get(0, 0),this.get(0, this.nc()-1),this.get(this.nf()-1, 0),this.get(this.nf()-1, this.nc()-1));
	}
	
	public E center() {
		int nf = this.nf/2;
		int nc = this.nc/2;
		return this.get(nf, nc);
	}
	
	public static <E extends FieldElement<E>> Matrix<E> zero(Integer nf, Integer nc, Field<E> f) {
		return Matrix.of(nf,nc,f.getZero());		
	}
	
	public static <E extends FieldElement<E>> Matrix<E> one(Integer nf, Integer nc, Field<E> f) {
		return Matrix.of(nf,nc,f.getOne());		
	}
	
	public static <E extends FieldElement<E>> Matrix<E> unity(Integer n, Field<E> f) {
		Matrix<E> r = Matrix.zero(n, n, f);
		for(int i=0;i<n;i++) {
			r.set(i, i,f.getOne());
		}
		return r;
	}
	
	public static  Matrix<Fraction> zero(Integer nf, Integer nc) {
		return Matrix.zero(nf,nc,Fraction.ONE.getField());			
	}
	
	public static  Matrix<Fraction> one(Integer nf, Integer nc) {
		return Matrix.one(nf,nc,Fraction.ONE.getField());	
	}
	
	public static Matrix<Fraction> unity(Integer n) {
		return Matrix.unity(n,Fraction.ONE.getField());
	}
	
	public static  Matrix<BigFraction> zeroBig(Integer nf, Integer nc) {
		return Matrix.zero(nf,nc,BigFraction.ONE.getField());			
	}
	
	public static  Matrix<BigFraction> oneBig(Integer nf, Integer nc) {
		return Matrix.one(nf,nc,BigFraction.ONE.getField());	
	}
	
	public static Matrix<BigFraction> unityBig(Integer n) {
		return Matrix.unity(n,BigFraction.ONE.getField());
	}

	public static <E extends FieldElement<E>> Matrix<E> multiply(Matrix<E> in1, Matrix<E> in2){
		E zero = in1.get(0,0).getField().getZero();
		Matrix<E> r = Matrix.of(in1.nf,in2.nc,zero);
		Boolean ch = in1.nc==in2.nf && r.nf == in1.nf && r.nc == in2.nc;
		Preconditions.checkArgument(ch, "No se cumplen condiciones de multiplicaci�n");
	    for (int i = 0; i < in1.nf; i++) {
	        for (int j = 0; j < in2.nc; j++) {
	            r.set(i,j,zero);
	            for (int k = 0; k < in1.nc; k++){
	            	E val = (in1.get(i,k).multiply(in2.get(k,j))).add(r.get(i,j));
	            	r.set(i,j,val);
	            }
	        }
	    }
	    return r;
	}
	
	public static <E extends FieldElement<E>> Matrix<E> multiply_r(Matrix<E> m1, Matrix<E> m2){
		Matrix<E> r;
		Boolean ch = m1.nc==m2.nf;
		Preconditions.checkArgument(ch,
				String.format("No se cumplen condiciones de multiplicaci�n = (in1.nc = %d, in2.nf = %d)",m1.nc,m2.nf));
		if(m1.nc < 2 || m1.nf < 2 || m2.nf < 2 || m2.nc < 2) {
			r = Matrix.multiply(m1,m2);
		} else {
			View4<Matrix<E>> v1 = m1.views();
			View4<Matrix<E>> v2 = m2.views();
			Matrix<E> a = Matrix.add(Matrix.multiply_r(v1.a(),v2.a()),Matrix.multiply_r(v1.b(),v2.c()));
			Matrix<E> b = Matrix.add(Matrix.multiply_r(v1.a(),v2.b()),Matrix.multiply_r(v1.b(),v2.d()));
			Matrix<E> c = Matrix.add(Matrix.multiply_r(v1.c(),v2.a()),Matrix.multiply_r(v1.d(),v2.c()));	
			Matrix<E> d = Matrix.add(Matrix.multiply_r(v1.c(),v2.b()),Matrix.multiply_r(v1.d(),v2.d()));
			r = Matrix.compose(a, b, c, d);
		}
		return r;
	}
	
	public static <E extends FieldElement<E>> Matrix<E> add(Matrix<E> m1, Matrix<E> m2){
		E zero = m1.get(0,0).getField().getZero();
		Matrix<E> r = Matrix.of(m1.nf,m2.nc,zero);
		Boolean ch = m1.nc==m2.nc && m1.nf==m2.nf && r.nc == m1.nc && r.nf == m1.nf;
		Preconditions.checkArgument(ch, "No se cumplen condiciones de suma");
	    for (int i = 0; i < m1.nf; i++) {
	        for (int j = 0; j < m1.nc; j++) {
	        	E val = m1.get(i,j).add(m2.get(i,j));
	            r.set(i,j,val);
	        }
	    }
	    return r;
	}
	
	public static <E extends FieldElement<E>>  Matrix<E> add_r(Matrix<E> m1, Matrix<E> m2){
		Boolean ch = m1.nc==m2.nc && m1.nf==m2.nf;
		Preconditions.checkArgument(ch, "No se cumplen condiciones de suma");
		Matrix<E> r; 
		if(m1.nc > 1 && m1.nf > 1) {
			View4<Matrix<E>> v1 = m1.views();
			View4<Matrix<E>> v2 = m2.views();			
			Matrix<E> a = Matrix.add_r(v1.a(),v2.a());
			Matrix<E> b = Matrix.add_r(v1.b(),v2.b());
			Matrix<E> c = Matrix.add_r(v1.c(),v2.c());
			Matrix<E> d = Matrix.add_r(v1.d(),v2.d());
			r = Matrix.compose(a, b, c, d);
		} else {
			r = Matrix.add(m1,m2);
		}
		return r;
	}
	
	/**
	 * 
	 * @param base Base
	 * @param n Exponente
	 * @return Valor de base^n de forma iterativa
	 */
//	public static Long pot(Long base, Integer n){
//		Long r = base;
//		Long u = 1L;
//		while( n > 0){
//	       if(n%2==1){
//			     u = u * r;
//		   }
//		   r = r * r;
//		   n = n/2;
//		}
//		return u;
//	}
	
	public static <E extends FieldElement<E>> Matrix<E> pow(Matrix<E> m, Integer n) {
		Matrix<E> r = m;
		Matrix<E> u = Matrix.unity(m.nf(),m.get(0, 0).getField());
		while (n > 0) {
			if (n % 2 == 1) {
				u = Matrix.multiply(u, r);
			}
			r = Matrix.multiply(r, r);
			n = n / 2;
		}
		return u;
	}
	
	
	
	public static void test1() {
		Function<Integer, Character> f = e-> (char) (e + 'A' - 10);
		Matrix<Fraction> m1 = Matrix.of(7,7,new Fraction(7));
		m1.print("m1");
		Matrix<Fraction> m2 = Matrix.of(7, 7, Fraction.FOUR_FIFTHS);
		m2.print("m2");
		Matrix<Fraction> m3 = Matrix.add(m1,m2);
		m3.print("m3");
		Matrix<Fraction> m4 = Matrix.add_r(m1,m2);
		m4.print("m4");
		Matrix<Fraction> m5 = Matrix.multiply(m1,m2);
		m5.print("m5");
		Matrix<Fraction> m6 = Matrix.multiply_r(m1,m2);
		m6.print("m6");
		m6.view(0).print("view");
		Integer[][] a = {{1,2,3},{3,4,5},{5,6,7}};
		Matrix<Integer> m7 = Matrix.of(a);
		m7.print("m7");
		Matrix<Fraction> m8 = Matrix.of(7,7,7).map(e->new Fraction(e));
		m8.print("m8");
		Matrix<Fraction> m9 = Matrix.of(7, 7, -4).map(e->new Fraction(e));
		m9.print("m9");
		m9.view(0).print("view");
		Matrix<Fraction> m10 = Matrix.add(m8,m9);
		m10.print("m10");
		Matrix<Fraction> m11 = Matrix.add_r(m8,m9);
		m11.print("m11");
		Matrix<Fraction> m12 = Matrix.multiply(m8,m9);
		m12.print("m12");
		Matrix<Fraction> m13 = Matrix.multiply_r(m8,m9);
		m13.print("m13");
		m13.view(0).print("view");
		System.out.println(m13.corners());
		System.out.println(m13.center());
		Matrix<Integer> m14 = Matrix.random(7, 9, -10, 50);
		m14.print("m14");
		System.out.println("_______");
		System.out.println(m14.corners());
		System.out.println(m14.center());
		m14.view(0).print("view");
		System.out.println("_______");
		System.out.println(m14.view(0).corners());
		System.out.println(m14.view(0).center());
		Matrix<Character> m15 = Matrix.random(17, 29, 0, 60).map(f);
		m15.print("Chracter");
	}
	
	public static void test2() {
		Integer[] col = {1,1,1};
		Integer[] cu = {1,1,1,0,1,0,0,0,1};
		Matrix<Fraction> m1 = Matrix.of(col,3,1).map(e->new Fraction(e));
		Matrix<Fraction> m2 = Matrix.of(cu,3,3).map(e->new Fraction(e));
		String2.toConsole(m1.toString());
		String2.toConsole(m2.toString());
		String2.toConsole(multiply(m2,m1).toString());
	}
	
	
	public static void test3() {
		Integer [] d = {1,2,3,4,5,6,7,8,9};
		Matrix<Fraction> m = Matrix.of(d,3,3).map(e->new Fraction(e));
		String2.toConsole(m.toString());
//		String2.toConsole(Matrix.multiply(m,Matrix.unity(m.nc(),m.get(0,0).getField())).toString());
		String2.toConsole(Matrix.pow(m,3).toString());
	}
	
	public static void main(String[] args) {
		test3();
	}
}
