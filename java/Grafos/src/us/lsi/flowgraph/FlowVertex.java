package us.lsi.flowgraph;

import java.util.List;

import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;

/**
 * Un vértice de una Red de Fujo.
 * Un vértice de este tipo tiene asociado un coste unitario, un flujo máximo, otro mínimo 
 * y el tipo de vértice
 * 
 * @author Miguel Toro
 *
 */
public class FlowVertex {
	
	public enum TipoDeVertice{Source,Sink,Intermediate};
	
	public static List<FlowVertex> vertices = Lists2.newList();
	
	private final String externalId;	
	private final TipoDeVertice tipo;
	private final Double min;
	private final Double max;
	private final Double cost;
	private final String name;
	private final Integer id;
	private static int nId = 0;

	public static FlowVertex create(String[] formato) {
		FlowVertex v = new FlowVertex(formato);
		FlowVertex.vertices.add(v);
		return v;
	}
	
	public static FlowVertex get(String variable) {
		Preconditions.checkArgument(variable.charAt(0) == 'v');
		String r = variable.substring(1);
		int index = Integer.parseInt(r);
		return FlowVertex.vertices.get(index);
	}
	
	
	private Double convert(String s) {
		Double r;
		if(s.equals("inf")) {
			r = Double.MAX_VALUE;
		}else {
			r = Double.parseDouble(s);
		}
		return r;
	}
	
	private FlowVertex(String[] formato) {		
		this.externalId = formato[0];
		if (formato.length==1) {
			this.tipo = TipoDeVertice.Intermediate;
			this.min = 0.;
			this.max = Double.MAX_VALUE;
			this.cost = 1.;
			this.name = "";
		}else if (formato.length==2) {
			this.tipo = Enum.valueOf(TipoDeVertice.class,formato[1]);
			this.min = 0.;
			this.max = Double.MAX_VALUE;
			this.cost = 1.;
			this.name = "";				
		}else if(formato.length==5) {
			this.tipo = Enum.valueOf(TipoDeVertice.class,formato[1]);
			this.min = convert(formato[2]);
			this.max = convert(formato[3]);
			this.cost = convert(formato[4]);
			this.name = "";	
		} else if(formato.length==6){
			this.tipo = Enum.valueOf(TipoDeVertice.class,formato[1]);
			this.min = convert(formato[2]);
			this.max = convert(formato[3]);
			this.cost = convert(formato[4]);
			this.name = formato[5];
		} else {
			throw new IllegalArgumentException("Formato incorrecto");
		}
		this.id = nId;
		nId++;
	}

	public String getName() {
		return this.name.equals("")?this.externalId:this.name;
	}

	public Integer getId() {
		return id;
	}

	public TipoDeVertice getTipo() {
		return tipo;
	}

	public Double getMin() {
		return min;
	}

	public Double getMax() {
		return max;
	}

	public Double getCost() {
		return cost;
	}

	public String getExternalId() {
		return externalId;
	}
	
	public String getVariable() {
		return "v"+id;
	}
	
	public Integer getColor() {
		Integer r = null;
		switch(this.tipo) {
		case Source:r=9;break;
		case Sink:r=4;break;
		case Intermediate:r=0;
		}
		return r;
	}
	
	public String toObjective() {
		String r = "";
		if(this.cost != 0.)
		 r = number(this.cost)+"*"+this.getVariable();
		return r;
	}
	
	private String number(Double d) {
		return String.format("%+.1f", d);
	}
	
	public String toConstraints() {
		String r = "";
		if (this.min.equals(this.max)) {
			r = r + this.getVariable() + " = " + number(this.min) + ";\n";
		} else {
			if (this.min > 0.) {
				r = r + this.getVariable() + " >= " + number(this.min) + ";\n";
			}
			if (this.max < Double.MAX_VALUE) {
				r = r + this.getVariable() + " <= " + number(this.max) + ";\n";
			}
		}
		return r;
	}

	public boolean isSource() {
		return this.tipo==TipoDeVertice.Source?true:false;
	}

	public boolean isSink() {
		return this.tipo==TipoDeVertice.Sink?true:false;
	}

	public boolean isIntermediate() {
		return this.tipo==TipoDeVertice.Intermediate?true:false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FlowVertex))
			return false;
		FlowVertex other = (FlowVertex) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name.equals("")?externalId:name;
	}

}
