package us.lsi.trees;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.common.Set2;
import us.lsi.common.String2;
import us.lsi.iterativorecursivos.IterativosyRecursivosSimples;
import us.lsi.streams.Stream2;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TreeType;


public class Trees {
	
	public static <E> Integer size(Tree<E> tree) {
		TreeType type = tree.getType();
		return switch(type) {
		case Empty -> 0 ; 
		case Leaf -> 1; 
		case Nary -> 1 + tree.getChildren().stream().mapToInt(x->size(x)).sum(); 
		};	
	}
	
	public static <E> Integer size2(Tree<E> tree) {
		return (int)tree.stream().filter(t->!t.isEmpty()).count();
	}

	public static <E> E minLabel(Tree<E> tree, Comparator<E> cmp) {
		TreeType type = tree.getType();
		return switch (type) {
		case Empty -> null;
		case Leaf -> tree.getLabel();
		case Nary -> {
			E rl = tree.getLabel();
			E r = tree.getChildren().stream().map(x -> minLabel(x, cmp)).filter(x -> x != null).min(cmp).get();
			yield r == null ? rl : Stream.of(rl, r).min(cmp).get();
		}
		};
	}
	
	public static <E> E minLabel2(Tree<E> tree, Comparator<E> cmp) {
		return tree.stream().filter(t->!t.isEmpty()).map(t->t.getLabel()).min(cmp).get();
	}
	
	
	public static <E> Boolean containsLabel(Tree<E> tree, E label) {
		TreeType type = tree.getType();
		return switch (type) {
		case Empty -> false;
		case Leaf -> tree.getLabel().equals(label);
		case Nary -> tree.getLabel().equals(label)
				|| tree.getChildren().stream().anyMatch(x -> containsLabel(x, label));
		};
	}
	
	public static <E> Boolean containsLabel2(Tree<E> tree, E label)  {
		return tree.stream().filter(t->!t.isEmpty()).map(t->t.getLabel()).anyMatch(lb->lb.equals(label));
	}
	
	public static <E> Boolean equals(Tree<E> t1, Tree<E> t2) {
		TreeType t1Type = t1.getType();
		TreeType t2Type = t2.getType();
		Boolean r = t1Type == t2Type;
		if (!r)
			return r;
		return switch (t1Type) {
		case Empty -> true;
		case Leaf -> t1.getLabel().equals(t2.getLabel());
		case Nary -> {
			Integer n = t1.getNumOfChildren();
			yield t1.getLabel().equals(t2.getLabel()) && n == t2.getNumOfChildren()
					&& IntStream.range(0, n).allMatch(i -> equals(t1.getChild(i), t2.getChild(i)));
		}
		};
	}
	
	public static <E> Tree<E> copy(Tree<E> t1) {
		TreeType type = t1.getType();
		return switch (type) {
		case Empty -> Tree.empty();
		case Leaf -> Tree.leaf(t1.getLabel());
		case Nary -> {
			List<Tree<E>> ch = t1.getChildren().stream().map(x -> copy(x)).collect(Collectors.toList());
			yield Tree.nary(t1.getLabel(), ch);
		}
		};
	}
	
	public static <E> List<E> toListPostOrden(Tree<E> tree) {
		TreeType type = tree.getType();
		return switch(type) {
		case Empty -> new ArrayList<>(); 
		case Leaf -> {
			List<E> r = new ArrayList<>();
			r.add(tree.getLabel());
			yield r;
		}
		case Nary -> { 
			List<E> r = tree.getChildren().stream().flatMap(x->toListPostOrden(x).stream()).collect(Collectors.toList());
			r.add(tree.getLabel());	
			yield r;
		}
		};
	}
	
	public static Integer sumIfPredicate(Tree<Integer> tree, Predicate<Integer> predicate) {
		TreeType type = tree.getType();
		return switch (type) {
		case Empty -> 0;
		case Leaf -> predicate.test(tree.getLabel()) ? tree.getLabel() : 0;
		case Nary -> predicate.test(tree.getLabel()) ? tree.getLabel()
				: 0 + tree.getChildren().stream().mapToInt(x -> sumIfPredicate(x, predicate)).sum();
		};
	}
	
	public static <E> List<Boolean> niveles(Tree<E> tree, Predicate<Tree<E>> pd) {
		Map<Integer,Boolean> m = Stream2.of(()->tree.byLevel())
				.collect(Collectors.groupingBy(p->p.level(),
						Collectors.collectingAndThen(Collectors.mapping(p->p.tree(),Collectors.toList()),
								ls->ls.stream().allMatch(pd))));
		return m.entrySet()
				.stream().sorted(Comparator.comparing(e->e.getKey()))
				.map(e->e.getValue()).toList();
	}
	
	public static <E> Map<Integer,List<E>> parDeHijos(Tree<E> tree, Predicate<Tree<E>> pd) {
		return Stream2.of(()->tree.byLevel())
				.filter(t->!t.tree().isEmpty())
				.filter(t->t.tree().getNumOfChildren()%2==0)
				.collect(Collectors.groupingBy(p->p.level(),
						Collectors.mapping(p->p.tree().getLabel(),Collectors.toList())));
	}
	
	public static <E> Map<Integer,List<Tree<E>>> parNumeroDeHijos(Tree<E> tree) {
		return tree.stream().collect(Collectors.groupingBy(t->t.getNumOfChildren()));
	}
	
	public static void test1() {
		Tree<Integer> t7 = Tree.parse("34(2,27(_,2,3,4),9(8,_))").map(e->Integer.parseInt(e));	
		System.out.println(minLabel(t7,Comparator.naturalOrder())+"=="+minLabel2(t7,Comparator.naturalOrder()));
		Predicate<Tree<Integer>> pd = t->t.isEmpty() || t.getLabel()%2==0;
		String2.toConsole("%s",niveles(t7,pd));
	}
	
	public static Set<String> palindromas(Tree<Character> tree) {
		Set<String> st = Set2.of();
		palindromas(tree, "", st);
		return st;
	}
	
	public static void palindromas(Tree<Character> tree, String camino, Set<String> st) {
		TreeType type = tree.getType();
		switch(type) {	
		case Empty: break;
		case Leaf: 
			Character label = tree.getLabel();
			camino = camino+label;
			if(IterativosyRecursivosSimples.esPalindromo1(camino)) st.add(camino);
			break;
		case Nary: 
			label = tree.getLabel();
			camino = camino+label;
			for(Tree<Character> t: tree.getChildren()) 
				palindromas(t,camino,st);
			break;
		}
	}
	
	public static Set<String> palindromas2(Tree<Character> tree) {
		return palindromas2(tree, "");
	}
	
	public static Set<String> palindromas2(Tree<Character> tree, String camino) {
		TreeType type = tree.getType();
		return switch(type) {	
		case Empty->Set2.of();
		case Leaf -> {
			Character label = tree.getLabel();
			camino = camino+label;
			if(IterativosyRecursivosSimples.esPalindromo1(camino)) yield Set2.of(camino);
			else yield Set2.of();
		}
		case Nary -> {
			Character label = tree.getLabel();
			camino = camino+label;
			Set<String> st = Set2.of();
			for(Tree<Character> t: tree.getChildren()) {
				Set<String> st1 = palindromas2(t,camino);
				st.addAll(st1);
			}
			yield st;
		}
		};
	}
	
	public static void test2() {
		Tree<Character> tree = Tree.parse("r(a(p(a(r)),d(a(_,r),i(o,_)),t),t,u(t(a)))").map(t->t.charAt(0));
		String2.toConsole("%s",tree);
		String2.toConsole("%s",palindromas(tree));
		String2.toConsole("%s",palindromas2(tree));
		Tree<String> tree2 = Tree.parse("[2.;3.](a(p(a(r)),d(a(_,r),i(o,_)),t),t,u(t(a)))");
		String2.toConsole("%s",tree2);
	}
	
	public static void main(String[] args) {
		test2();
	}

}
