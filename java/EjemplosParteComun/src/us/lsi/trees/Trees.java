package us.lsi.trees;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TreeType;


public class Trees {
	
	public static <E> Integer size(Tree<E> tree) {
		TreeType type = tree.getType();
		Integer r = null;
		switch(type) {
		case Empty: r = 0 ; break;
		case Leaf: r = 1; break;
		case Nary: r = 1 + tree.getChildren().stream().mapToInt(x->size(x)).sum(); break;
		}
		return r;		
	}

	public static <E> E minLabel(Tree<E> tree, Comparator<E> cmp) {
		TreeType type = tree.getType();
		E r = null;
		switch(type) {
		case Empty: r = null ; break;
		case Leaf: r = tree.getLabel(); break;
		case Nary: 
			E rl = tree.getLabel();
			r = tree.getChildren().stream().map(x->minLabel(x,cmp)).filter(x->x!=null).min(cmp).get();
			r = r==null?rl:Stream.of(rl,r).min(cmp).get();
		}
		return r;		
	}
	
	
	public static <E> Boolean containsLabel(Tree<E> tree, E label) {
		TreeType type = tree.getType();
		Boolean r = null;
		switch(type) {
		case Empty: r = false; break;
		case Leaf: r = tree.getLabel().equals(label); break;
		case Nary: 
			r = tree.getLabel().equals(label) || tree.getChildren().stream().anyMatch(x->containsLabel(x,label));	
			break;
		}
		return r;		
	}
	
	public static <E> Boolean equals(Tree<E> t1, Tree<E> t2) {
		TreeType t1Type = t1.getType();
		TreeType t2Type = t2.getType();
		Boolean r = t1Type == t2Type;
		if(!r) return r;
		switch(t1Type) {
		case Empty: r = true; break;
		case Leaf: r = t1.getLabel().equals(t2.getLabel()); break;
		case Nary: 
			Integer n = t1.getNumOfChildren();
			r = t1.getLabel().equals(t2.getLabel()) && n == t2.getNumOfChildren() &&
			IntStream.range(0,n).allMatch(i->equals(t1.getChild(i),t2.getChild(i)));
			break;
		}
		return r;		
	}
	
	public static <E> Tree<E> copy(Tree<E> t1) {
		TreeType type = t1.getType();
		Tree<E> r = null;
		switch(type) {
		case Empty: r = Tree.empty(); break;
		case Leaf: r = Tree.leaf(t1.getLabel()); break;
		case Nary: 
			List<Tree<E>> ch = t1.getChildren().stream().map(x->copy(x)).collect(Collectors.toList());
			r = Tree.nary(t1.getLabel(),ch);	
			break;
		}
		return r;		
	}
	
	public static <E> List<E> toListPostOrden(Tree<E> tree) {
		TreeType type = tree.getType();
		List<E> r = null;
		switch(type) {
		case Empty: r = new ArrayList<>(); break;
		case Leaf: 
			r = new ArrayList<>();
			r.add(tree.getLabel());
			break;
		case Nary: 
			r = tree.getChildren().stream().flatMap(x->toListPostOrden(x).stream()).collect(Collectors.toList());
			r.add(tree.getLabel());	
			break;
		}
		return r;
	}
	
	public static Integer sumIfPredicate(Tree<Integer> tree, Predicate<Integer> predicate) {
		TreeType type = tree.getType();
		Integer r = null;
		switch(type) {
		case Empty: r = 0; break;
		case Leaf: r = predicate.test(tree.getLabel())?tree.getLabel():0; break;
		case Nary: 
			r = predicate.test(tree.getLabel())?tree.getLabel():0 +
			tree.getChildren().stream().mapToInt(x->sumIfPredicate(x,predicate)).sum();
			break;
		}
		return r;		
	}

}
