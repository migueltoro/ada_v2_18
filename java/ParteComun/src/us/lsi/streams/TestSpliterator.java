package us.lsi.streams;

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
		
		Spliterator<String> spliterator11 = ListSpliterator.of(list).stream().spliterator();
		Spliterator<String> spliterator21 = spliterator11.trySplit();
		Spliterator<String> spliterator31 = spliterator21.trySplit();
		Spliterator<String> spliterator41 = spliterator31.trySplit();
		Spliterator<String> spliterator51 = spliterator41.trySplit();
		 
		spliterator1.forEachRemaining(System.out::println);
		 
		System.out.println("========");
		 
		spliterator2.forEachRemaining(System.out::println);
		
		System.out.println("========");
		
		spliterator3.forEachRemaining(System.out::println);
		
		System.out.println("========");
		 
		spliterator4.forEachRemaining(System.out::println);
		
		System.out.println("========");
		 
		spliterator5.forEachRemaining(System.out::println);
		
		System.out.println("AAA========AAA");
//		 
//		spliterator6.forEachRemaining(System.out::println);
		
		spliterator11.forEachRemaining(System.out::println);
		 
		System.out.println("========");
		 
		spliterator21.forEachRemaining(System.out::println);
		
		System.out.println("========");
		 
		spliterator31.forEachRemaining(System.out::println);
		
		System.out.println("========");
		 
		spliterator41.forEachRemaining(System.out::println);
		
		System.out.println("========");
		 
		spliterator51.forEachRemaining(System.out::println);

//		System.out.println("========");
//		 
//		spliterator6.forEachRemaining(System.out::println);
	}

}

