package us.lsi.tiposrecursivos;
import java.util.function.Function;

import us.lsi.tiposrecursivos.Exp.ExpType;


public class UnaryOperator<T,R> extends OperatorCode<Function<T,R>> {

	public UnaryOperator(String name, Function<T, R> code, ExpType[] operatorType, String format,Boolean isFunction) {
		super(name, code, operatorType,12, format, isFunction);
	}

	
}
