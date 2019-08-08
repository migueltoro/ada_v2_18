package us.lsi.flujosparalelos;

import java.util.List;
import java.util.Spliterator;

public class TestSpliterator {

	public static void main(String[] args) {
		List<String> list = List.of("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17");
		 
		Spliterator<String> spliterator1 = list.stream().spliterator();
		Spliterator<String> spliterator2 = spliterator1.trySplit();
		Spliterator<String> spliterator3 = spliterator2.trySplit();
		Spliterator<String> spliterator4 = spliterator3.trySplit();
		Spliterator<String> spliterator5 = spliterator4.trySplit();
//		Spliterator<String> spliterator6 = spliterator4.trySplit();
		 
		spliterator1.forEachRemaining(System.out::println);
		 
		System.out.println("========");
		 
		spliterator2.forEachRemaining(System.out::println);
		
		System.out.println("========");
		 
		spliterator3.forEachRemaining(System.out::println);
		
		System.out.println("========");
		 
		spliterator4.forEachRemaining(System.out::println);
		
		System.out.println("========");
		 
		spliterator5.forEachRemaining(System.out::println);

		System.out.println("========");
		 
//		spliterator6.forEachRemaining(System.out::println);
	}

}

