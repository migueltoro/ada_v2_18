package us.lsi.tiposrecursivos;


import java.util.Arrays;

import us.lsi.tiposrecursivos.Exp.ExpType;

public class OperatorCode<C> implements Operator {

	protected final String name;
	protected final C code;	
	protected final ExpType[] operatorType;
	protected final ExpType[] parametersType;
	protected final ExpType resultType;
	protected final int priority;
	protected final int arity;
	protected final String format;
	protected final Boolean isFunction;
	protected final int id;
	protected static int lastId = 0;

	public OperatorCode(String name, C code, ExpType[] operatorType, Integer priority, String format, Boolean isFunction) {
		super();
		this.name = name;
		this.code = code;
		this.operatorType = operatorType;
		this.parametersType = Arrays.copyOfRange(operatorType, 0, operatorType.length-1);;
		this.resultType = operatorType[operatorType.length-1];
		this.priority = priority;
		this.arity = this.parametersType.length;
		this.format = format;
		this.isFunction = isFunction;
		this.id = lastId;
		lastId++;
	}

	public C getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getFormat() {
		return format;
	}

	public Integer getArity() {
		return arity;
	}

	public String getId() {
		return getName()+id;
	}


	public ExpType[] getOperatorType() {
		return operatorType;
	}

	public ExpType[] getParametersType() {
		return parametersType;
	}

	public ExpType getResultType() {
		return resultType;
	}

	@Override
	public Integer getPriority() {
		return priority;
	}

	public Boolean isFunction() {
		return isFunction;
	}

	
	@Override
	public String toString() {
		return "[" + name + "," + Arrays.toString(operatorType) + ","+ resultType +","+Arrays.toString(parametersType) +","+
				+ priority + "," + getArity() + "," + (format==null?"_":format) + "," + isFunction + "]";
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
		if (getClass() != obj.getClass())
			return false;
		OperatorCode<?> other = (OperatorCode<?>) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
