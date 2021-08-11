package us.lsi.trees;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.common.String2;
import us.lsi.streams.Stream2;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TreeLevel;
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
		Map<Integer,Boolean> m = Stream2.asStream(()->tree.byLevel())
				.collect(Collectors.groupingBy(p->p.level(),
						Collectors.collectingAndThen(Collectors.mapping(p->p.tree(),Collectors.toList()),
								ls->ls.stream().allMatch(pd))));
		return m.entrySet()
				.stream().sorted(Comparator.comparing(e->e.getKey()))
				.map(e->e.getValue()).toList();
	}
	
	public static <E> Map<Integer,List<E>> parDeHijos(Tree<E> tree, Predicate<Tree<E>> pd) {
		return Stream2.asStream(()->tree.byLevel())
				.filter(t->!t.tree().isEmpty())
				.filter(t->t.tree().getNumOfChildren()%2==0)
				.collect(Collectors.groupingBy(p->p.level(),
						Collectors.mapping(p->p.tree().getLabel(),Collectors.toList())));
	}
	
	public static <E> Map<Integer,List<Tree<E>>> parNumeroDeHijos(Tree<E> tree) {
		return tree.stream().collect(Collectors.groupingBy(t->t.getNumOfChildren()));
	}
	
	public static void main(String[] args) {
		Tree<Integer> t7 = Tree.parse("40(2,27(_,2,3,4),9(8,_))").map(e->Integer.parseInt(e));	
		System.out.println(minLabel(t7,Comparator.naturalOrder())+"=="+minLabel2(t7,Comparator.naturalOrder()));
		Predicate<Tree<Integer>> pd = t->t.isEmpty() || t.getLabel()%2==0;
		String2.toConsole("%s",niveles(t7,pd));
	}

}
