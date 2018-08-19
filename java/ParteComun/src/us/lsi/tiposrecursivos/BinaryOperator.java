package us.lsi.tiposrecursivos;
import java.util.function.BiFunction;

import us.lsi.tiposrecursivos.Exp.ExpType;



public class BinaryOperator<T1,T2,R> extends OperatorCode<BiFunction<T1,T2,R>> {

	
	public BinaryOperator(String name, BiFunction<T1, T2, R> code, ExpType[] operatorType, int priority, String format,
			Boolean isFunction) {
		super(name, code, operatorType, priority, format, isFunction);
	}
	
	
}
