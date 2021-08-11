package us.lsi.grafos.datos;



public class Carretera {
	
	public static Carretera of() {
		return new Carretera();
	}

	public static Carretera ofVertex(Ciudad c1, Ciudad c2) {
		return new Carretera(c1,c2);
	}

	public static Carretera ofFormat(String[] formato) {
		return new Carretera(formato);
	}

	public static Carretera ofWeight(Ciudad c1, Ciudad c2, Double km) {
		return new Carretera(c1, c2, km);
	}
	
	public static Carretera reverse(Carretera c) {
		return new Carretera(c.target, c.source, c.km, c.nombre);
	}

	private static int num =0;
	private Ciudad source;
	private Ciudad target;
	private Double km;
	private String nombre;
	private int id;

	private Carretera(Ciudad c1, Ciudad c2) {
		this.source = c1;
		this.target = c2;
		this.km = 0.;
		this.id = num;
		num++;
	}
	
	private Carretera() {
		this.source = null;
		this.target = null;
		this.km = 0.;
		this.id = num;
		num++;
	} 
	
	private Carretera(Ciudad source, Ciudad target, Double km, String nombre) {
		super();
		this.source = source;
		this.target = target;
		this.km = km;
		this.nombre = nombre;
		this.id = num;
		num++;
	}

	private Carretera(String[] nombre) {
		this.km = Double.parseDouble(nombre[3]);
		this.nombre = nombre[2];		
		this.id = num;
		num++;
	}
	
	private Carretera(Ciudad c1, Ciudad c2, Double km) {
		this.source = c1;
		this.target = c2;
		this.nombre = null;
		this.km = km;
		this.id = num;
		num++;
	}

	public String getNombre() {
		return nombre;
	}


	public Double getKm() {
		return this.km;
	}

	
	public Ciudad getSource(){
		return source;
	}
	
	public Ciudad getTarget(){
		return target;
	}

	@Override
	public String toString() {
		String nn = this.nombre==null?"":this.nombre+",";
		return nn+this.km+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Carretera))
			return false;
		Carretera other = (Carretera) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
