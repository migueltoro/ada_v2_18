package us.lsi.tiposrecursivos.ast;

import java.io.IOException;
import java.util.List;

import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.String2;

public class TestExp {
	
	public static void test1() {
		Operators.addOperators();
		String2.toConsole("%s",Operators.operators.keySet().stream().map(e->e.longName()).toList());
		Var a = Var.of("a",Type.Int);
		Var b = Var.of("b",Type.Int);
		Const c = Const.of(34,Type.Int);
		Binary be1 = Binary.of(a, b, "+");
		Binary be2 = Binary.of(be1, c, "*");
		Unary ue1 = Unary.of(be2,"toDouble");
		Unary ue2 = Unary.of(ue1,"sqrt");
		String2.toConsole("%s",ue2.type());
		String2.toConsole("%s",ue2.vars());
		ue2.setValue(Map2.of("a",34,"b",65));
		String2.toConsole("%s",ue2.value());
	}
	
	public static void test2() {
		Operators.addOperators();
		Operator a = Var.of("a",Type.Double);
		Operator b = Const.of(3.45,Type.Double);
		List<Operator> lv2 = List2.of(a,b,a);
		List<Operator> lv1 = List2.of(Operators.get("+",Type.Double,Type.Double),Operators.get("sqrt",Type.Double));
		List<Operator> lv0 = List2.of(Operators.get("+",Type.Double,Type.Double));
		String2.toConsole("%s",Exp.ofOperatorsInLevels(List.of(lv0,lv1,lv2)));
	}
	
	public static void main(String[] args) throws IOException {
		test2();
	}

}
