package us.lsi.tiposrecursivos.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.Preconditions;

public class Operators {
	
	public static Map<OperatorId,Operator> operators = new HashMap<>();
	
	public static Integer toInt(Object value) {
		return ((Number)value).intValue();
	}
	
	public static Double toDouble(Object value) {
		return ((Number)value).doubleValue();
	}
	
	public static Operator get0(String name,Type top) {
		OperatorId id = OperatorId.of0(name, top);
		Operator op = Operators.operators.getOrDefault(id,null);
		Preconditions.checkNotNull(op,String.format("No existe el operador %s,%s",name,top));
		return op;
	}
	
	public static Operator get(String name,Type top) {
		OperatorId id = OperatorId.of1(name, top);
		Operator op = Operators.operators.getOrDefault(id,null);
		Preconditions.checkNotNull(op,String.format("No existe el operador %s,%s",name,top));
		return op;
	}
	
	public static Operator getN(String name,Type top) {
		OperatorId id = OperatorId.ofN(name, top);
		Operator op = Operators.operators.getOrDefault(id,null);
		Preconditions.checkNotNull(op,String.format("No existe el operador %s,%s",name,top));
		return op;
	}
	
	public static Operator get(String name,Type top,Type top2) {
		OperatorId id = OperatorId.of2(name, top, top2);
		Operator op = Operators.operators.getOrDefault(id,null);
		Preconditions.checkNotNull(op,String.format("No existe el operador %s,%s,%s",name,top, top2));
		return op;
	}
	
	public static void addOperators() {
		Operator plusInt = Operator.of2("+",Type.Int,Type.Int,(e1,e2)->toInt(e1)+toInt(e2));
		Operators.operators.put(plusInt.id(), plusInt);
		Operator multInt = Operator.of2("*",Type.Int,Type.Int,(e1,e2)->toInt(e1)*toInt(e2));
		Operators.operators.put(multInt.id(), multInt);
		Operator plusDouble = Operator.of2("+",Type.Double,Type.Double,(e1,e2)->toDouble(e1)+toDouble(e2));
		Operators.operators.put(plusDouble.id(), plusDouble);
		Operator multDouble = Operator.of2("*",Type.Double,Type.Double,(e1,e2)->toDouble(e1)*toDouble(e2));
		Operators.operators.put(multDouble.id(), multDouble);
		Operator toDouble = Operator.of1("toDouble",Type.Int,Type.Double,e->toDouble(e));
		Operators.operators.put(toDouble.id(),toDouble);
		Operator toInt = Operator.of1("toInt",Type.Double,Type.Int,e->toInt(e));
		Operators.operators.put(toInt.id(),toInt);
		Operator sqrt = Operator.of1("sqrt",Type.Double,e->Math.sqrt(toDouble(e)));
		Operators.operators.put(sqrt.id(),sqrt);
		Operator pot2 = Operator.of1("^2",Type.Double,e->toDouble(e)*toDouble(e));
		Operators.operators.put(pot2.id(),pot2);
		Operator pot3 = Operator.of1("^3",Type.Double,e->toDouble(e)*toDouble(e)*toDouble(e));
		Operators.operators.put(pot3.id(),pot3);
		Operator plusN = Operator.ofN("+",Type.Double,(List<Object> ls)->ls.stream()
				.mapToDouble(e->Operators.toDouble(e)).sum());
		Operators.operators.put(plusN.id(),plusN);
	}
	
	

}
