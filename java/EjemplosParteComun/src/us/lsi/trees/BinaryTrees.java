package us.lsi.trees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;



public class BinaryTrees {
	
	public static <E> Integer contarEtiquetas(BinaryTree<E> arbol) {
		Integer res = null;
		if(arbol.isEmpty()) {
			res = 0;
		} else if(arbol.isLeaf()) {
			res = 1;
		} else {
			res = 1 + contarEtiquetas(arbol.getLeft()) + contarEtiquetas(arbol.getRight()); 
		}			
		return res;
	}
	
	public static <E> E etiquetaMenor(BinaryTree<E> arbol, Comparator<E> cmp) {
		E res = null;
		if(arbol.isLeaf()) {
			res = arbol.getLabel();
		} else if(arbol.isBinary()){
			E e1 = etiquetaMenor(arbol.getLeft(), cmp);
			E e2 = etiquetaMenor(arbol.getRight(), cmp);
			res = menor(cmp, arbol.getLabel(), menor(cmp, e1, e2)); 
		}			
		return res;
	}
	
	private static <E> E menor(Comparator<E> cmp, E e1, E e2) {
		if(e1==null) {
			return e2;
		} else if(e2==null) {
			return e1;
		} else {			
			return cmp.compare(e1, e2)<=0? e1: e2;
		}
	}

	public static <E> Boolean contieneEtiqueta(BinaryTree<E> arbol, E etq) {
		Boolean res = null;
		if(arbol.isEmpty()) {
			res = false;					
		} else if(arbol.isLeaf()) {
			res = arbol.getLabel().equals(etq);
		} else {
			res = arbol.getLabel().equals(etq) || contieneEtiqueta(arbol.getLeft(), etq) || contieneEtiqueta(arbol.getRight(), etq);
		}
		return res;
	}
	
	public static <E> Boolean sonIguales(BinaryTree<E> a1, BinaryTree<E> a2) {
		Boolean res = false;
		if(a1.isEmpty()) {
			res = a2.isEmpty();					
		} else if(a1.isLeaf()) {
			res = a2.isLeaf() && a1.getLabel().equals(a2.getLabel());
		} else {
			res = a1.getLabel().equals(a2.getLabel()) && sonIguales(a1.getLeft(), a2.getLeft()) && sonIguales(a1.getRight(), a2.getRight());
		}
		return res;
	}
	
	public static <E> BinaryTree<E> copiaSimetrica(BinaryTree<E> arbol) {
		BinaryTree<E> res = null;
		if(arbol.isEmpty()) {
			res = BinaryTree.empty();					
		} else if(arbol.isLeaf()) {
			res = BinaryTree.leaf(arbol.getLabel());
		} else {
			res = BinaryTree.binary(arbol.getLabel(), copiaSimetrica(arbol.getRight()), copiaSimetrica(arbol.getLeft()));
		}
		return res;
	}
	
	public static <E> List<E> recorridoPostOrden(BinaryTree<E> arbol) {
		if(arbol.isEmpty()) {			
			return new ArrayList<>();
		} else if(arbol.isLeaf()) {
			List<E> res = new ArrayList<>();
			res.add(arbol.getLabel());
			return res;
		} else {
			List<E> res = recorridoPostOrden(arbol.getLeft());
			res.addAll(recorridoPostOrden(arbol.getRight()));
			res.add(arbol.getLabel());

			return res;
		}
	}
	
	
}
