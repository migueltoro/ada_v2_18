package us.lsi.astar.mochila;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStack {
	
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<>();
		stack.add(23);
		stack.add(67);
		stack.add(123);
		stack.add(456);
		Stream<Integer> st = Stream.of(1000);
		List<Integer> path = Stream.concat(stack.stream(),st).collect(Collectors.toList());
		System.out.println(path);
		System.out.println(stack.size());
		System.out.println(stack.peek());
		System.out.println(stack.get(3));
	}

}
