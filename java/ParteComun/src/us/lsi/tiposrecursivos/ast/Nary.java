package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.common.Preconditions;

public record Nary(List<Exp> operands, String name) implements Exp {

	public static Nary of(List<Exp> operands, String op) {
		return new Nary(operands, op);
	}
	
	public Operator operator() {
		List<Type> t = operands.stream().map(op->op.type()).toList();
		Preconditions.checkArgument(t.stream().allMatch(x->x.equals(t.get(0))));
		OperatorId id = OperatorId.ofN(name,t.get(0));
		return Operators.operators.get(id);
	}
	
	public Object value() {
		Operator.Nary op = (Operator.Nary) this.operator();
		List<Object> values = operands.stream().map(e->e.value()).toList();
		return op.function().apply(values);
	}
	
	public Type type() {
		return this.operator().resultType();
	}

	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		
	}
	
	@Override
	public String toString() {
		return String.format("%s(%s)", this.name(), this.operands().stream()
				.map(e->e.name()).collect(Collectors.joining("")));
	}
	
	@Override
	public Set<Var> vars() {
		return this.operands.stream().flatMap(e->e.vars().stream()).collect(Collectors.toSet());
	}

	@Override
	public void setValue(Map<String, Object> values) {
		this.operands().stream().forEach(e->e.setValue(values));
	}
}
