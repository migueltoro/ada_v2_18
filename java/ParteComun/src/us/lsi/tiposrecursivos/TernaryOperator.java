package us.lsi.tiposrecursivos;

import us.lsi.common.TriFunction;
import us.lsi.tiposrecursivos.Exp.ExpType;

public class TernaryOperator<T1, T2, T3, R> extends OperatorCode<TriFunction<T1,T2,T3,R>>{

	public TernaryOperator(String name, TriFunction<T1, T2, T3, R> code, ExpType[] operatorType, String format, Boolean isFunction) {
		super(name, code, operatorType, 12, format, isFunction);
	}

}
