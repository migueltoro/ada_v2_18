package us.lsi.common;

import java.lang.reflect.Array;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.fraction.Fraction;


/**
 * @author migueltoro
 *
 * @param <E> El tipo de los elementos de la matriz que deben ser elementos de un campo numérico
 */
public class Matrix<E extends FieldElement<E>> {
	
	public static Matrix<Fraction> of(Integer[][] datos) {
		int n = datos.length;
		int m = datos[0].length;
		Fraction[][] d = new Fraction[n][m];		
		for(int i =0; i<n; i++)
			for(int j = 0; j<m;j++)
				d[i][j] = new Fraction(datos[i][j]);
		return new Matrix<>(Fraction.ZERO.getField(),d);
	}
	
	public static Matrix<Fraction> of(Fraction[][] datos) {
		return new Matrix<>(Fraction.ZERO.getField(),datos);
	}
	
	public static <T extends FieldElement<T>> Matrix<T> of(Field<T> f, T[][] datos) {
		return new Matrix<>(f,datos);
	}
	
	public static Matrix<Fraction> ofValue(int nf, int nc, Fraction value){
		return ofValue(Fraction.ZERO.getField(),nf,nc,value);
	}
	
	public static <T extends FieldElement<T>> Matrix<T> ofValue(Field<T> f, int nf, int nc, T value) {
		@SuppressWarnings("unchecked")
		T[][] datos = (T[][]) Array.newInstance(f.getZero().getClass(),nf,nc);
		Matrix<T> r = Matrix.of(f,datos);
		for (int i = 0; i < nf; i++) {
			for (int j = 0; j < nc; j++)
				r.set(i,j,value);
		}
		return r;
	}
	
	public static  Matrix<Fraction> zero(int nf, int nc) {
		return zero(Fraction.ZERO.getField(),nf,nc);
	}
	
	public static <T extends FieldElement<T>> Matrix<T> zero(Field<T> f, int nf, int nc) {
		@SuppressWarnings("unchecked")
		T[][] datos = (T[][]) Array.newInstance(f.getZero().getClass(),nf,nc);
		Matrix<T> r = Matrix.of(f,datos);
		for (int i = 0; i < nf; i++) {
			for (int j = 0; j < nc; j++)
				r.set(i,j,f.getZero());
		}
		return r;
	}
	
	public static  Matrix<Fraction> one(int nf) {
		return one(Fraction.ZERO.getField(),nf);
	}
	
	public static  <T extends FieldElement<T>> Matrix<T> one(Field<T> f, int nf){
		@SuppressWarnings("unchecked")
		T[][] datos = (T[][]) Array.newInstance(f.getZero().getClass(),nf,nf);
		Matrix<T> r = Matrix.of(f,datos);
		for (int i = 0; i < nf; i++) {
			for (int j = 0; j < nf; j++) {
				if (i == j) {
					r.set(i,j,f.getOne());
				} else {
					r.set(i,j,f.getZero());
				}
			}
		}
		return  r;
	}
	
	protected E[][] datos;
	protected int nf;
	protected int nc;
	protected int iv;	
	protected int jv;
	protected Field<E> field; 
	

	protected Matrix(Field<E> f, E[][] datos) {
		super();
		this.datos = datos;		
		this.nf = datos.length;
		this.nc = datos[0].length;
		this.iv = 0;
		this.jv = 0;
		this.field = f;
	}

	private Matrix(Field<E> f, E[][] datos, int nf, int nc, int iv, int jv) {
		super();
		this.datos = datos;
		this.nf = nf;
		this.nc = nc;
		this.iv = iv;
		this.jv = jv;
		this.field = f;
	}

	E get(int i, int j) {
		return this.datos[this.iv+i][this.jv+j];
	}
	void set(int i, int j, E value) {
		this.datos[this.iv+i][this.jv+j] = value;
	}
	
	Matrix<E> view(int nf, int nc, int iv, int jv){
		Matrix<E> r = of(this.field,this.datos);
		r.iv = this.iv +iv; r.nf = nf; r.jv = this.jv +jv; r.nc = nc;
		return r;
	}
	
	Matrix<E> view(int nv){
		int nf = this.nf/2;
		int nc = this.nc/2;
		Matrix<E> r = of(this.field,this.datos);
		switch(nv){
		case 0:  r = view(nf,nc,0,0); break;
		case 1:  r = view(nf,this.nc-nc,0,nc); break;
		case 2:  r = view(this.nf-nf,nc,nf,0); break;
		case 3:  r = view(this.nf-nf,this.nc-nc,nf,nc); break;
		default: Preconditions.checkArgument(false, "Opción no válida");
		}
		return r;
	}
	
	void print(String title) {
		System.out.println(String.format("\n%s = (nf = %d, nc = %d, iv = %d, , jv = %d)\n", title, this.nf, this.nc,
				this.iv, this.jv));
		Function<Fraction,String> fs = f->f.getNumerator()+
				(f.getDenominator() == 1? "": "/"+f.getDenominator());
		Function<Integer, String> f = i -> IntStream.range(0, this.nc).boxed()
				.map(j -> fs.apply((Fraction)this.get(i, j)))
				.collect(Collectors.joining(", ", "[", "]"));
		String s = IntStream.range(0, this.nc).boxed()
				.map(i -> f.apply(i)).collect(Collectors.joining(",\n", "[", "]"));
		System.out.println(s);

	}
	
	Matrix<E> copy(){
		return new Matrix<>(this.field,this.datos, this.nf, this.nc, this.iv,this.jv);
	}
	
	void copy(Matrix<E> r){
		Boolean ch = this.nc==r.nc && this.nf==r.nf;
		Preconditions.checkArgument(ch, "No se cumplen condiciones de copia");
	    for (int i = 0; i < this.nf; i++) {
	        for (int j = 0; j < this.nc; j++) {
	            r.set(i,j,this.get(i, j));
	        }
	    }
	}
	
	public static <T extends FieldElement<T>> void copy(Matrix<T> out, Matrix<T> in){
		Boolean ch = in.nc==out.nc && in.nf==out.nf;
		Preconditions.checkArgument(ch, "No se cumplen condiciones de copia");
	    for (int i = 0; i < in.nf; i++) {
	        for (int j = 0; j < in.nc; j++) {
	            out.set(i,j,in.get(i, j));
	        }
	    }
	}
	
	View4<Matrix<E>> views() {
		return View4.of(this.view(0),this.view(1),this.view(2),this.view(3));
	}
	
	public static <E extends FieldElement<E>> Matrix<E> compose(Matrix<E> a, Matrix<E> b, Matrix<E> c, Matrix<E> d) {
		int nf = a.nf+c.nf;
		int nc = a.nc+ b.nc;
		Matrix<E> r = Matrix.zero(a.field,nf,nc);
		View4<Matrix<E>> vr = r.views();
		a.copy(vr.a);
		b.copy(vr.b);
		c.copy(vr.c);
		d.copy(vr.d);
		return r;
	}

	Matrix<E> multiply(Matrix<E> in2){
		Matrix<E> r = Matrix.zero(this.field,this.nf,in2.nc);
		Boolean ch = this.nc==in2.nf && r.nf == this.nf && r.nc == in2.nc;
		Preconditions.checkArgument(ch, "No se cumplen condiciones de multiplicación");
	    for (int i = 0; i < this.nf; i++) {
	        for (int j = 0; j < in2.nc; j++) {
	            r.set(i,j,this.field.getZero());
	            for (int k = 0; k < this.nc; k++){
	            	E val = (this.get(i,k).multiply(in2.get(k,j))).add(r.get(i,j));
	            	r.set(i,j,val);
	            }
	        }
	    }
	    return r;
	}
	
	Matrix<E> multiply_r(Matrix<E> m2){
		Matrix<E> r;
		Boolean ch = this.nc==m2.nf;
		Preconditions.checkArgument(ch,String.format("No se cumplen condiciones de multiplicación = (in1.nc = %d, in2.nf = %d)",this.nc,m2.nf));
		if(this.nc < 2 || this.nf < 2 || m2.nf < 2 || m2.nc < 2) {
			r = this.multiply(m2);
		} else {
			View4<Matrix<E>> v1 = this.views();
			View4<Matrix<E>> v2 = m2.views();
			Matrix<E> a = v1.a.multiply_r(v2.a).add(v1.b.multiply_r(v2.c));
			Matrix<E> b = v1.a.multiply_r(v2.b).add(v1.b.multiply_r(v2.d));
			Matrix<E> c = v1.c.multiply_r(v2.a).add(v1.d.multiply_r(v2.c));	
			Matrix<E> d = v1.c.multiply_r(v2.b).add(v1.d.multiply_r(v2.d));
			r = Matrix.compose(a, b, c, d);
		}
		return r;
	}
	
	Matrix<E> add(Matrix<E> m2){
		Matrix<E> r = Matrix.zero(this.field,this.nf,m2.nc);
		Boolean ch = this.nc==m2.nc && this.nf==m2.nf && r.nc == this.nc && r.nf == this.nf;
		Preconditions.checkArgument(ch, "No se cumplen condiciones de suma");
	    for (int i = 0; i < this.nf; i++) {
	        for (int j = 0; j < this.nc; j++) {
	        	E val = this.get(i,j).add(m2.get(i,j));
	            r.set(i,j,val);
	        }
	    }
	    return r;
	}
	
	Matrix<E> add_r(Matrix<E> m2){
		Boolean ch = this.nc==m2.nc && this.nf==m2.nf;
		Preconditions.checkArgument(ch, "No se cumplen condiciones de suma");
		Matrix<E> r; 
		if(this.nc > 1 && this.nf > 1) {
			View4<Matrix<E>> v1 = this.views();
			View4<Matrix<E>> v2 = m2.views();			
			Matrix<E> a = v1.a.add_r(v2.a);
			Matrix<E> b = v1.b.add_r(v2.b);
			Matrix<E> c = v1.c.add_r(v2.c);
			Matrix<E> d = v1.d.add_r(v2.d);
			r = Matrix.compose(a, b, c, d);
		} else {
			r = this.add(m2);
		}
		return r;
	}
	
	
	
	
	public static void main(String[] args) {
		Matrix<Fraction> m1 = Matrix.one(7);
		m1.print("m1");
		Matrix<Fraction> m2 = Matrix.ofValue(7, 7, Fraction.FOUR_FIFTHS);
		m2.print("m2");
		Matrix<Fraction> m3 = m1.add(m2);
		m3.print("m3");
		Matrix<Fraction> m4 = m1.add_r(m2);
		m4.print("m4");
		Matrix<Fraction> m5 = m1.multiply(m2);
		m5.print("m5");
		Matrix<Fraction> m6 = m1.multiply_r(m2);
		m6.print("m6");
		m1.view(0).print("view");
		Integer[][] a = {{1,2,3},{3,4,5},{5,6,7}};
		Matrix<Fraction> m7 = Matrix.of(a);
		m7.print("m7");
	}
}
