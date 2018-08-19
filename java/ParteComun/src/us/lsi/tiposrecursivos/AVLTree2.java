package us.lsi.tiposrecursivos;

import java.util.Comparator;

import us.lsi.common.Comparator2;
import us.lsi.common.Metricas;
import us.lsi.common.Preconditions;
import us.lsi.common.Strings2;
import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;

public class AVLTree2<E> extends AVLTree<E> {

	
	/**
	 * @param <E> Tipo de los elementos del &aacute;rbol
	 * @return Un árbol binario vacío cuyos elementos se ordenarán mediante el orden natural de E
	 */
	public static <E extends Comparable<? super E>> AVLTree2<E> create() {
		return new AVLTree2<E>(BinaryTree.empty(), Comparator.naturalOrder());
	}
	
	/**
	 * @param <E> Tipo de los elementos del &aacute;rbol
	 * @param comparator Un orden
	 * @return Un árbol binario vacío cuyos elementos se ordenarán mediante comparator
	 */
	public static <E> AVLTree2<E> create(Comparator<E> comparator) {
		return new AVLTree2<E>(BinaryTree.empty(), comparator);
	}
	
	
	public AVLTree2(BinaryTree<E> tree, Comparator<E> comparator) {
		super(tree, comparator);
	}
	
	
	protected static <E> Tuple2<Boolean,BinaryTree<E>> addWhere(BinaryTree<E> tree, E element, Comparator<E> comparator) {
		Tuple2<Boolean,BinaryTree<E>> r = null;
		switch(tree.getType()) {		
		case Empty: r = Tuple.create(true,tree); break;
		case Leaf: 
			Boolean add = !element.equals(tree.getLabel());
			r = Tuple.create(add,tree); 
			break;	
		case Binary: 
			switch(Comparator2.compare(element,tree.getLabel(),comparator)) {
			case EQ: r = Tuple.create(false,tree); break;			
			case LT: r = addWhere(tree.getLeft(),element,comparator); break;
			case GT: r = addWhere(tree.getRight(),element,comparator); break;
			}
			break;
		}
		return r;
	}

	@Override
	protected Tuple2<Boolean,BinaryTree<E>> add(BinaryTree<E> tree, E element, Comparator<E> comparator) {
		Tuple2<Boolean, BinaryTree<E>> find = addWhere(tree,element,comparator);
		Boolean changed = true;
		if(!find.v1) { 
			changed= false; 
			return Tuple.create(changed,tree); 
		}
		MutableBinaryTree<E> mFind = find.v2.mutableView();
		BinaryTree<E> r = tree;
		switch(find.v2.getType()) {		
		case Empty: 
			r = mFind.changeFor(BinaryTree.leaf(element));
			break;
		case Leaf: 
			switch(Comparator2.compare(element,find.v2.getLabel(),comparator)) {
			case EQ: Preconditions.checkState(false); break;
			case LT: r = mFind.changeFor(BinaryTree.binary(find.v2.getLabel(),BinaryTree.leaf(element),BinaryTree.empty())); break;
			case GT: r = mFind.changeFor(BinaryTree.binary(find.v2.getLabel(),BinaryTree.empty(),BinaryTree.leaf(element))); break;
			}
			
			break;	
		case Binary: Preconditions.checkState(false);break;
		}
		r = equilibrateToRoot(r);
		return Tuple.create(changed,r);
	}
	
	@Override
	protected Tuple2<Boolean,BinaryTree<E>> remove(BinaryTree<E> tree, E element, Comparator<E> comparator) {
		BinaryTree<E> find = find(tree,element,comparator);
		Boolean changed = true;
		MutableBinaryTree<E> mFind = find.mutableView();
		BinaryTree<E> r = null;;
		switch(find.getType()) {		
		case Empty: r = tree; changed = false; break;
		case Leaf: 
			r = mFind.changeFor(BinaryTree.empty());
			r = equilibrateToRoot(r);
			break;
		case Binary: 
			if (find.getLeft().isEmpty() && find.getRight().isEmpty()) {
				r = mFind.changeFor(BinaryTree.empty());
			} else {
				BinaryTree<E> e;
				if (!find.getLeft().isEmpty()) {
					e = getLast(find.getLeft());
				} else {
					e = getFirst(find.getRight());
				}
				mFind.setLabel(e.getLabel());
				r = e.mutableView().changeFor(BinaryTree.empty());
		    }
			r = equilibrateToRoot(r);
			break;
		}
		return Tuple.create(changed,r);
	}
	
	private static <E> BinaryTree<E> equilibrateToRoot(BinaryTree<E> r2){
		BinaryTree<E> r;
		while(true) {
			r = equilibrate(r2);
			if(r != r2) {
				r2 = r2.mutableView().changeFor(r);
			} 
			if(r2.isRoot()) break;
			else r2 = r2.getFather();			
		}
		return r2;

	}
	
	public static void main(String[] args) {

		Metricas m = new Metricas();
		m.setTiempoDeEjecucionInicial();
		AVLTree2<Integer> tree = AVLTree2.create();
//		tree.add(100);
//	Strings2.toConsole(String.format("%d,%s",tree.getHeight(), tree.toString()));
		for (int i = 0; i < 10 ; i++) {
			tree.add(i);
//			Strings2.toConsole(String.format("%d,%d,%d === %s",tree.getHeight(),tree.tree.getLeft().getHeight(), tree.tree.getRight().getHeight(), tree.toString()));
			Strings2.toConsole(String.format("Add = %d,%s,%s,%d,%s", i,tree.first(),tree.last(), tree.getHeight(),tree.toString()));
		}
//		for (int i = 0; i < 8 ; i++) {
//			tree.add(i);
//			Strings2.toConsole(String.format("%d,%d,%d === %s",tree.getHeight(),tree.tree.getLeft().getHeight(), tree.tree.getRight().getHeight(), tree.toString()));
//			Strings2.toConsole(String.format("Add = %d,%s,%s,%d,%s", i,tree.first(),tree.last(), tree.getHeight(),tree.toString()));
//		}
		for (int i = 11; i > 9 ; i--) {
			tree.remove(i);
//////			Strings2.toConsole(String.format("%d,%d,%d === %s",tree.getHeight(),tree.tree.getLeft().getHeight(), tree.tree.getRight().getHeight(), tree.toString()));
///			Strings2.toConsole(String.format("Delete = %d,%s,%s,%d,%s", i,tree.first(),tree.last(), tree.getHeight(),tree.toString()));
			Strings2.toConsole("Delete = %d,%s", tree.getHeight(),tree.toString());
		}
		m.setTiempoDeEjecucionFinal();
		
		Strings2.toConsole("%s,%s,%d,%s", tree.first(),tree.last(), tree.getHeight(),tree.toString());
		System.out.println(m);
	}

}
