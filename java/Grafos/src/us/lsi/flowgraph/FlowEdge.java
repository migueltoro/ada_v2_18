package us.lsi.flowgraph;

import java.util.List;

import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;

/**
 * Una arista simple de una Red de Fujo.
 * Cada arista de este tipo tiene asociado un coste unitario, un flujo máximo y otro mínimo 
 * 
 * @author Miguel Toro
 *
 */
public class FlowEdge {
	
	public static List<FlowEdge> edges = Lists2.empty();
	
	private final FlowVertex source;
	private final FlowVertex target;
	private final Double min;
	private final Double max;
	private final Double cost;
	private final String name;
	private final Integer id;
	
	private static Integer nId = 0;
	
	public static FlowEdge create(FlowVertex v1, FlowVertex v2, String[] formato) {
		FlowEdge r = new FlowEdge(v1,v2,formato);
		FlowEdge.edges.add(r);
		return r;
	}
	
	public static FlowEdge get(String variable) {
		Preconditions.checkArgument(variable.charAt(0) == 'e');
		String r = variable.substring(1);
		int index = Integer.parseInt(r);
		return FlowEdge.edges.get(index);
	}
	
	private Double convert(String s) {
		Double r;
		if(s.equals("inf")) {
			r = FlowVertex.maxDouble;
		}else {
			r = Double.parseDouble(s);
		}
		return r;
	}
	
	private FlowEdge(FlowVertex from, FlowVertex to,String[] formato) {
		super();
		if(formato.length == 2) {
			this.source=from;
			this.target =to;
			this.min = 0.;
			this.max = FlowVertex.maxDouble;
			this.cost = 0.;
			this.name = "";
		} else if(formato.length == 5) {
			this.source=from;
			this.target =to;
			this.min = convert(formato[2]);
			this.max = convert(formato[3]);
			this.cost = convert(formato[4]);
			this.name = "";
		} else if(formato.length == 6) {
			this.source=from;
			this.target =to;
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
	
	public Double getMin() {
		return min;
	}

	public Double getMax() {
		return max;
	}

	public Double getCost() {
		return cost;
	}
	
	public FlowVertex getFrom() {
		return  this.source;
	}

	public FlowVertex getTo() {
		return  this.target;
	}
	
	
	public String getName() {
		return name;
	}

	public FlowVertex getSource() {
		return source;
	}

	public FlowVertex getTarget() {
		return target;
	}

	public Integer getId() {
		return id;
	}

	public String getVariable() {
		return "e"+id;
	}
	private String number(Double d) {
		return String.format("%+.1f", d);
	}
	public String toObjective() {
		String r = "";
		if(this.cost != 0.)
		 r = number(this.cost)+"*"+this.getVariable();
		return r;
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
	
	
	@Override
	public String toString() {
		return source.toString() + "--" + target.toString()
		+(name.equals("")?"":" = "+name);
	}
	
	public String toStringLong() {
		return String.format("(%s,%d,%.2f,%.2f,%2.f)",this.getVariable(),this.getId(),this.getMin(),this.getMax(),this.getCost());
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
		if (!(obj instanceof FlowEdge))
			return false;
		FlowEdge other = (FlowEdge) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
