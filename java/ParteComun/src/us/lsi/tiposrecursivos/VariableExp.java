package us.lsi.tiposrecursivos;

import java.util.Map;
import us.lsi.common.Maps2;
import us.lsi.common.Preconditions;

public class VariableExp<R> extends Exp<R> implements Operator {

	private R value;
	private String name;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;
	ExpType expType;	
	
	public static ExpType getVariableType(String s){
		ExpType r;
		Character c = s.charAt(0);
		if(c<='d') r = ExpType.Boolean;
		else if(c<='k') r = ExpType.Integer;
		else r = ExpType.Double;
		return r;
	}
	
	VariableExp(String name, R value, ExpType expType) {
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
		Preconditions.checkState(value!=null,String.format("La variable %s tiene valor null",name));
		return value;
	}


	public void setValue(R value) {
		this.value = value;
	}
	
	public String getId() {
		return "V_"+id;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Exp<R> copy() {
		return Exp.variable(this.name, this.value, this.expType);
	}


	public String getName() {
		return name;
	}
	
	public Boolean isVariable() {
		return true;
	}

	@Override
	public Boolean match(Exp<?> exp) {
		Boolean r = false;
		if(exp.isFree()){
			exp.<R>asFree().setExp(this);
			r = true;
		} else if(exp.isVariable()){
			if(this.getName().equals(exp.asVariable().getName())) r = true;
		}
		return r;
	}
	
	@Override
	public Boolean isConstant() {
		return false;
	}

	@Override
	public Exp<R> simplify() {
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VariableExp))
			return false;
		VariableExp<?> other = (VariableExp<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s = "    \"%s\" [label=\"%s\"];";
			Element.getFile().println(
					String.format(s, getId(), getName()));
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
	

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, Exp<Object>> vars() {
		return Maps2.newHashMap(name, (Exp<Object>)this);
	}

	@Override
	public Integer getPriority() {
		return 12;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Exp<Object> ifMatchTransform(Exp<Object> pattern,Map<String,Exp<Object>> vars,String result) {
		return (Exp<Object>)this;
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
