package us.lsi.trees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.common.Comparators;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BinaryType;


public class BinaryTrees {
	
	
	public static <E> Integer size(BinaryTree<E> tree) {
		BinaryType type = tree.getType();
		Integer r = null;
		switch(type) {
		case Empty: r = 0 ; break;
		case Leaf: r = 1; break;
		case Binary: r = 1 + size(tree.getLeft()) + size(tree.getRight()); break;
		}
		return r;		
	}
	
	
	public static <E> E minLabel(BinaryTree<E> tree, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		E r = null;
		switch(type) {
		case Empty: r = null ; break;
		case Leaf: r = tree.getLabel(); break;
		case Binary: 
			E e1 = minLabel(tree.getLeft(), cmp);
			E e2 = minLabel(tree.getRight(), cmp);
			r = Comparators.min(tree.getLabel(),Comparators.min(e1, e2,cmp),cmp); 			
			break;
		}
		return r;		
	}
	
	public static <E> E minLabelOrdered(BinaryTree<E> tree, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		E r = null;
		switch(type) {
		case Empty: r = null; break;
		case Leaf: r = tree.getLabel(); break;
		case Binary: 
			if(tree.getLeft().isEmpty()) r = tree.getLabel();
			else r = minLabelOrdered(tree.getLeft(),cmp);
			break;
		}
		return r;		
	}
	
	public static <E> Boolean containsLabel(BinaryTree<E> tree, E label) {
		BinaryType type = tree.getType();
		Boolean r = null;
		switch(type) {
		case Empty: r = false; break;
		case Leaf: r = tree.getLabel().equals(label); break;
		case Binary: 
			r = tree.getLabel().equals(label) || 
			containsLabel(tree.getLeft(), label) || 
			containsLabel(tree.getRight(), label);		
			break;
		}
		return r;		
	}
	
	public static <E> Boolean containsLabelOrdered(BinaryTree<E> tree, E label, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		Boolean r = null;
		switch(type) {
		case Empty: r = false; break;
		case Leaf: r = tree.getLabel().equals(label); break;
		case Binary: 
			E nLabel = tree.getLabel();
			r =  Comparators.isEQ(label,nLabel,cmp) || 
				(Comparators.isLT(label, nLabel, cmp) && containsLabelOrdered(tree.getLeft(),label,cmp)) ||	
				(Comparators.isGT(label, nLabel, cmp) && containsLabelOrdered(tree.getRight(),label,cmp));
			break;
		}
		return r;		
	}
	
	public static <E> Boolean equals(BinaryTree<E> t1, BinaryTree<E> t2) {
		BinaryType t1Type = t1.getType();
		BinaryType t2Type = t2.getType();
		Boolean r = t1Type == t2Type;
		if(!r) return r;
		switch(t1Type) {
		case Empty: r = true; break;
		case Leaf: r = t1.getLabel().equals(t2.getLabel()); break;
		case Binary: 
			r = t1.getLabel().equals(t2.getLabel()) && 
			equals(t1.getLeft(), t2.getLeft()) || 
			equals(t1.getRight(), t1.getRight());		
			break;
		}
		return r;		
	}
		
	public static <E> BinaryTree<E> copy(BinaryTree<E> t1) {
		BinaryType type = t1.getType();
		BinaryTree<E> r = null;
		switch(type) {
		case Empty: r = BinaryTree.empty(); break;
		case Leaf: r = BinaryTree.leaf(t1.getLabel()); break;
		case Binary: r = BinaryTree.binary(t1.getLabel(), copy(t1.getLeft()), copy(t1.getRight()));	
			break;
		}
		return r;		
	}
		
	public static <E> BinaryTree<E> reverseCopy(BinaryTree<E> t1) {
		BinaryType type = t1.getType();
		BinaryTree<E> r = null;
		switch(type) {
		case Empty: r = BinaryTree.empty(); break;
		case Leaf: r = BinaryTree.leaf(t1.getLabel()); break;
		case Binary: r = BinaryTree.binary(t1.getLabel(), reverseCopy(t1.getRight()), reverseCopy(t1.getLeft()));break;
		}
		return r;		
	}
	
	public static <E> List<E> toListPostOrden(BinaryTree<E> tree) {
		BinaryType type = tree.getType();
		List<E> r = null;
		switch(type) {
		case Empty: r = new ArrayList<>(); break;
		case Leaf: 
			r = new ArrayList<>();
			r.add(tree.getLabel());
			break;
		case Binary: 
			r = toListPostOrden(tree.getLeft());
			r.addAll(toListPostOrden(tree.getRight()));
			r.add(tree.getLabel());	
			break;
		}
		return r;
	}
	
	public static <E> BinaryTree<E> addOrdered(BinaryTree<E> tree, E label, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		BinaryTree<E> r = null;
		switch(type) {
		case Empty: r = BinaryTree.leaf(label); break;
		case Leaf: 
			E aLabel = tree.getLabel();
			switch(Comparators.compare(label, aLabel, cmp)) {
			case EQ: r = tree; break;
			case LT: r = BinaryTree.binary(aLabel,BinaryTree.leaf(label),BinaryTree.empty());break;
			case GT: r = BinaryTree.binary(aLabel,BinaryTree.empty(),BinaryTree.leaf(label)); break;
			}
			break;
		case Binary: 
			aLabel = tree.getLabel();
			switch(Comparators.compare(label, aLabel, cmp)) {
			case EQ: r = tree; break;
			case LT: r = BinaryTree.binary(aLabel,addOrdered(tree.getLeft(),label,cmp),tree.getRight()); break;
			case GT: r = BinaryTree.binary(aLabel,tree.getLeft(),addOrdered(tree.getRight(),label,cmp)); break;
			}
			break;
		}
		return r;		
	}
	
	public static <E> BinaryTree<E> addOrdered(List<E> labels, Comparator<E> cmp){
		BinaryTree<E> r = BinaryTree.empty();
		for(E e:labels) {
			r = addOrdered(r,e,cmp);
		}
		return r;
	}
	
	public static <E> BinaryTree<E> removeOrdered(BinaryTree<E> tree, E label, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		BinaryTree<E> r = null;
		switch(type) {
		case Empty: r = tree; break;
		case Leaf: 
			E aLabel = tree.getLabel();
			switch(Comparators.compare(label, aLabel, cmp)) {
			case EQ: r = BinaryTree.empty(); break;
			case LT: r = tree; break;
			case GT: r = tree; break;
			}
			break;
		case Binary: 
			aLabel = tree.getLabel();
			switch(Comparators.compare(label, aLabel, cmp)) {
			case EQ: 
				if(!tree.getLeft().isEmpty()) {
					E lLabel = tree.getLeft().getLabel();
					r = BinaryTree.binary(lLabel, removeOrdered(tree.getLeft(),lLabel,cmp),tree.getRight());
				} else if(!tree.getRight().isEmpty()) {
					E rLabel = tree.getRight().getLabel();
					r = BinaryTree.binary(rLabel, tree.getLeft(),removeOrdered(tree.getRight(),rLabel,cmp));
				} else {
					r = BinaryTree.empty();
				}	
				break;
			case LT: r = BinaryTree.binary(aLabel,removeOrdered(tree.getLeft(),label,cmp),tree.getRight()); break;
			case GT: r = BinaryTree.binary(aLabel,tree.getLeft(),removeOrdered(tree.getRight(),label,cmp)); break;
			}
			break;
		}
		return r;		
	}
	
	public static <E> Boolean isOrdered(BinaryTree<E> tree, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		Boolean r = null;
		switch(type) {
		case Empty: r = true; break;
		case Leaf: r = true; break;
		case Binary: 
			E aLabel = tree.getLabel();
			r = (tree.getLeft().isEmpty() || Comparators.isGT(aLabel,tree.getLeft().getLabel(), cmp)) &&
				(tree.getRight().isEmpty() || Comparators.isLT(aLabel,tree.getRight().getLabel(), cmp)) &&
				isOrdered(tree.getLeft(),cmp) &&
				isOrdered(tree.getRight(),cmp) ;
			break;
		}
		return r;		
	}
	
	public static Integer sumIfPredicate(BinaryTree<Integer> tree, Predicate<Integer> predicate) {
		BinaryType type = tree.getType();
		Integer r = null;
		switch(type) {
		case Empty: r = 0; break;
		case Leaf: r = predicate.test(tree.getLabel())?tree.getLabel():0; break;
		case Binary: 
			r = predicate.test(tree.getLabel())?tree.getLabel():0 +
			    sumIfPredicate(tree.getLeft(),predicate) +
			    sumIfPredicate(tree.getRight(),predicate);
			break;
		}
		return r;		
	}
	
	public static void main(String[] args) {
		BinaryTree<Integer> tree = addOrdered(List.of(1,7,2,5,10,9,-1,14,67,67), Comparator.naturalOrder());
		System.out.println(tree.toString());
		System.out.println(isOrdered(tree,Comparator.naturalOrder()));
		BinaryTree<Integer> tree2 = removeOrdered(tree, 10,Comparator.naturalOrder());
		System.out.println(tree2.toString());
		System.out.println(isOrdered(tree2,Comparator.naturalOrder()));
		BinaryTree<Integer> tree3 = removeOrdered(tree2,9,Comparator.naturalOrder());
		System.out.println(tree3.toString());
		System.out.println(isOrdered(tree3,Comparator.naturalOrder()));
		System.out.println(sumIfPredicate(tree3,x->x%2==0));
		BinaryTree<Integer> tree4 = copy(tree);
		System.out.println(equals(tree,tree4));
	}
	
}
