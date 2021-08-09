package us.lsi.recursivos.problemasdelistas;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import us.lsi.common.String2;

public abstract class Test extends us.lsi.basictypes.Test {
	
	public static void test1() {
		List<Integer> ls = new ArrayList<>(Arrays.asList(1,2,3,4,5,5,5,5,5,5));
		String2.toConsole("%s",ProblemasDeListas.masDeLaMitad(ls));
		String2.toConsole("%s",ProblemasDeListas.masDeLaMitad2(ls,Comparator.naturalOrder()));
	}
	
	public static void test2() {
		List<Double> ls = List.of(-1.,6.,-2.,5.,-1.,4.,3.,-4.,3.);
		String2.toConsole("%s",ProblemasDeListas.getSubSecuenciaMaxima(ls));
	}

	public static void main(String[] args) {
		test2();
	}

}
