package us.lsi.tiposrecursivos;
import java.util.stream.Collector;

import us.lsi.tiposrecursivos.Exp.ExpType;



public class NaryOperator<T,R> extends OperatorCode<Collector<T,?,R>> {


	public NaryOperator(String name, Collector<T, ?, R> code, ExpType[] operatorType,
			Boolean isFunction) {
		super(name, code, operatorType, 12, null, isFunction);
	}

	@Override
	public Integer getArity() {
		return -1;
	}


}
