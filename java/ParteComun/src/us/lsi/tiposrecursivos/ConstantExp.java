package us.lsi.tiposrecursivos;

import java.util.HashMap;
import java.util.Map;


public class ConstantExp<R> extends Exp<R> implements Operator {

	private String name;
	private R value;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;
	ExpType expType;

	ConstantExp(String name, R value, ExpType expType) {
		super();
		this.name = name;
		this.value = value;
		this.expType = expType;
		this.id = lastId;
		lastId++;
	}
	
	@Override
	public Integer getArity() {
		return 0;
	}

	
	@Override
	public R getValue() {
		return value;
	}
		
    public String toString(){
    	return this.value.toString();
    }

	@Override
	public Exp<R> copy() {
		return Exp.constant(name,value,expType);
	}


	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return "C_"+id;
	}
	
	public void setValue(R value) {
		this.value = value;
	}

	@Override
	public Boolean isConstant() {
		return true;
	}
	
	@Override
	public Exp<R> simplify() {
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ConstantExp))
			return false;
		ConstantExp<?> other = (ConstantExp<?>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public Boolean match(Exp<?> exp) {
		Boolean r = false;
		if(exp.isFree()){
			exp.<R>asFree().setExp(this);
			r = true;
		} else if(exp.isConstant()){
			if(this.getValue().equals(exp.asConstant().getValue())) r = true;
		}
		return r;
	}

	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s = "    \"%s\" [label=\"%s\"];";
			Element.getFile().println(
					String.format(s, getId(), getValue().toString()));
		}	
		flag = true;
	}
	
	
	@Override
	protected void setFlagFalse() {
		flag = false;
	}

	@Override
	public ExpType getType() {
		return expType;
	}
	
	
	@Override
	protected Map<String, Exp<Object>> vars() {
		return new HashMap<>();
	}
	
	@Override
	public Integer getPriority() {
		return 12;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Exp<Object> ifMatchTransform(Exp<Object> pattern, Map<String,Exp<Object>> vars,String result) {
		return (Exp<Object>) this;
	}
	
	@Override
	public Boolean isPattern() {
		return false;
	}

	@Override
	public Operator getOperator() {
		return this;
	}
}
