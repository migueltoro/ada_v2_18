package us.lsi.tiposrecursivos;

import java.util.Map;
import us.lsi.common.Maps2;

public class UnaryExp<T,R> extends Exp<R> {	
	
	private Exp<T> op;	
	private UnaryOperator<T,R> operator;
	private int id;
	private static int lastId = 0;
	private Boolean flag = false;
	
	protected UnaryExp(Exp<T> op, UnaryOperator<T,R> operator) {
		super();
		this.op = op;
		this.operator = operator;
		this.id = lastId;
		lastId++;
	}


	@Override
	public Integer getArity() {
		return 1;
	}
	
	public Exp<T> getOp() {
		return op;
	}

	public void setOp(Exp<T> op) {
		this.op = op;
	}

	public UnaryOperator<T,R> getOperator() {
		return operator;
	}

	@Override
	public R getValue() {
		return operator.getCode().apply(op.getValue());
	} 
	
	public String toString(){
		String sOp = this.getOp().toString();
		if(op.getPriority() < this.getPriority()){
			sOp = "("+sOp+")";
		}
		return String.format(this.operator.getFormat(),sOp);
	}
	
	@Override
	public Exp<R> copy() {
		return Exp.unary(this.getOp().copy(), this.operator);
	}

	public Boolean isUnary() {
		return true;
	}
	
	@Override
	protected String getId() {
		return "U_"+id;
	}
	
	@Override
	public Boolean isConstant() {
		return this.getOp().isConstant();
	}	
	
	@Override
	public Exp<R> simplify() {
		Exp<R> r;
		if(this.isConstant()){
			r = Exp.constant(this.getValue());
		} else {
			r = Exp.unary(op.simplify(), operator);
		}		
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UnaryExp))
			return false;
		UnaryExp<?,?> other = (UnaryExp<?,?>) obj;
		if (op == null) {
			if (other.op != null)
				return false;
		} else if (!op.equals(other.op))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		return true;
	}

	@Override
	public Boolean match(Exp<?> exp) {
		Boolean r = false;
		if(exp.isFree()){
			exp.<R>asFree().setExp(this);
			r = true;
		} else if(exp.isUnary()){
			UnaryExp<?,?> t = exp.asUnary();
			r = this.operator.getId().equals(t.getOperator().getId());
			r = r && this.op.match(t.op);
		}
		return r;
	}
	
	@Override
	protected void toDOT(String file) {
		if (!flag) {
			String s1 = "    \"%s\" [label=\"%s\"];";
			Element.getFile().println(
					String.format(s1, getId(), this.operator.getName()));
			String s2 = "    \"%s\" -> \"%s\";";
			Element.getFile().println(
					String.format(s2, getId(), op.getId()));
			op.toDOT(file);
		}
		flag = true;
	}
	
	@Override
	protected void setFlagFalse() {
		flag = false;
		op.setFlagFalse();
	}

	@Override
	public ExpType getType() {
		return operator.getOperatorType()[1];
	}
	
	@Override
	protected Map<String, Exp<?>> vars() {
		Map<String, Exp<?>> r = Maps2.newHashMap(op.vars());
		return r;
	}


	@Override
	public Integer getPriority() {
		return 12;
	}

	@Override
	public Exp<R> ifMatchTransform(Exp<?> pattern, Map<String,Exp<?>> vars,String patternResult) {
		Exp<T> r1 = op.ifMatchTransform(pattern, vars, patternResult);
		Exp<R> r = Exp.unary(r1, this.operator);
		Exp<?> copy = pattern.copy();
		if(r.match(copy)){
			Map<String,Exp<?>> m = copy.getVars();
			Map<String,Exp<?>> m2 = 
					Maps2.<String,Exp<?>,Exp<?>>newHashMap(m, 
							x->x.isFree()?x.asFree().getExp():x);
			m2.putAll(vars);
			r = Exp.string(patternResult,m2);
		}
		return r;
	}
	
	@Override
	public Boolean isPattern() {
		return this.op.isPattern();
	}
	
}
