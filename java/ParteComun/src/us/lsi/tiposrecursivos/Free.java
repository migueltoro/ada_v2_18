package us.lsi.tiposrecursivos;

import java.util.Map;

import us.lsi.common.Maps2;
import us.lsi.common.Preconditions;

public class Free<R> extends Exp<R> implements Operator {

	private Exp<R> exp = null;
	private String name;
	private Integer id;
	ExpType expType;
	private static int lastId = 0;
	private Boolean flag = false;

	Free(String name, ExpType expType) {
		super();
		this.name = name;
		this.expType = expType;
		this.exp = null;
		this.id = lastId;
		lastId++;
	}

	public Exp<R> getExp() {
		return exp;
	}

	public void setExp(Exp<R> exp) {
		this.exp = exp;
	}

	@Override
	public Integer getArity() {
		return 0;
	}

	
	public String getName() {
		return name;
	}
	
	@Override
	public R getValue() {
		Preconditions.checkState(false, String.format("La variable %s no es evaluable", name));
		return null;
	}

	@Override
	public Exp<R> copy() {
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
		if (!(obj instanceof Free))
			return false;
		Free<?> other = (Free<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public Boolean match(Exp<?> exp) {
		return false;
	}
	@Override
	public Boolean isFree() {
		return true;
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
	public String toString() {
		return name;
	}

	@Override
	public String getId() {
		return "F_"+id;
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
	public Exp<Object> ifMatchTransform(Exp<Object> pattern,Map<String,Exp<Object>> vars, String result) {
		return (Exp<Object>)this;
	}
	
	@Override
	public Boolean isPattern() {
		return true;
	}
	
	@Override
	public Operator getOperator() {
		return this;
	}
	
	
}
