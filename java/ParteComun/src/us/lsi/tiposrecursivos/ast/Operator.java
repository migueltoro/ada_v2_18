package us.lsi.tiposrecursivos.ast;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Operator {
	
	OperatorId id();
	Type resultType();
	
	public static Operator of0(String name,Type top,Function<Object,Object> function) {
		OperatorId id = OperatorId.of0(name, top);
		return new Unary(id,top,function);
	}
	
	public static Operator of1(String name,Type top,Function<Object,Object> function) {
		OperatorId id = OperatorId.of1(name, top);
		return new Unary(id,top,function);
	}
	
	public static Operator of1(String name,Type top,Type resultType,Function<Object,Object> function) {
		OperatorId id = OperatorId.of1(name, top);
		return new Unary(id,resultType,function);
	}
	
	public static Operator of2(String name,Type top,Type top2,BiFunction<Object,Object,Object> function) {
		OperatorId id = OperatorId.of2(name, top,top2);
		return new Binary(id,function);
	}
	
	public static Operator ofN(String name,Type top,Function<List<Object>,Object> function) {
		OperatorId id = OperatorId.ofN(name, top);
		return new Nary(id,function);
	}
	
	public record Identity(OperatorId id, Type resultType, Function<Object,Object> function) implements Operator {}
	
	public record Unary(OperatorId id, Type resultType, Function<Object,Object> function) implements Operator {}
	
	public record Binary(OperatorId id, BiFunction<Object,Object,Object> function) implements Operator {
		public Type resultType() {return id.tp(); }
	}
	
	public record Nary(OperatorId id, Function<List<Object>,Object> function) implements Operator {
		public Type resultType() {return id.tp(); }
	}
	
}
