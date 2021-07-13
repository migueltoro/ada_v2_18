package us.lsi.streams;

import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Stream;

import us.lsi.common.MutableType;

public class TestCollect {

	
	public static Collector<Double,MutableType<Double>,Double> suma(){
		return Collectors2.of(()->0.,
				(b,e)->b+e,
				(x,y)->x+y,
				x->x);
	}
	
	public static void main(String[] args) {
		List<Double> ls = List.of(1.,2.,56.,123.,-45.,567.,-2.,-89.,55.,67.,1.,2.,56.,123.,-45.,567.,-2.,-89.,55.,67.);
		Double r1 = Collect2.collect(ls.stream(), TestCollect.suma());
		Double r2 = ls.stream().reduce((x,y)->x+y).get();
		System.out.println("Sol = "+r1+","+r2);
		Stream<Double> s = new Random().doubles().limit(1000).boxed();
		Double r3 = Collect2.collect(s, TestCollect.suma());
		System.out.println("Sol = "+r3);
	}

}

