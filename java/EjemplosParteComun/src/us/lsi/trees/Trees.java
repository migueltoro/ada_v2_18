package us.lsi.trees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import us.lsi.tiposrecursivos.Tree;


public class Trees {
	
	public static <E> Integer contarEtiquetas(Tree<E> arbol) {
		Integer res = null;
		if(arbol.isEmpty()) {
			res = 0;
		} else if(arbol.isLeaf()) {
			res = 1;
		} else {
			res = 1 + contarEtiquetas(arbol.getChildren().iterator()); 
		}			
		return res;
	}
	
	private static<E> Integer contarEtiquetas(Iterator<Tree<E>> itArboles ) {
		Integer res = 0;
		if(itArboles.hasNext()) {
			res = contarEtiquetas(itArboles.next()) + contarEtiquetas(itArboles);
		}
		return res;
	}

	public static <E> E etiquetaMenor(Tree<E> arbol, Comparator<E> cmp) {
		E res = null;
		if(arbol.isLeaf()) {
			res = arbol.getLabel();
		} else if(arbol.isNary()){
			E min = etiquetaMenor(arbol.getChildren().iterator(), cmp);
			res = menor(cmp, arbol.getLabel(), min); 
		}			
		return res;
	}
	
	private static <E> E etiquetaMenor(Iterator<Tree<E>> itArboles, Comparator<E> cmp) {
		E res = null;
		if(itArboles.hasNext()) {
			res = menor(cmp, etiquetaMenor(itArboles.next(), cmp), etiquetaMenor(itArboles, cmp));
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
	
	public static <E> Boolean contieneEtiqueta(Tree<E> arbol, E etq) {
		Boolean res = null;
		if(arbol.isEmpty()) {
			res = false;
		} else if(arbol.isLeaf()) {
			res = arbol.getLabel().equals(etq);
		} else {			
			res = arbol.getLabel().equals(etq) || existeEtiqueta(arbol.getChildren().iterator() , etq);
		}
		return res;
	}
	
	private static <E> Boolean existeEtiqueta(Iterator<Tree<E>> itArboles, E etq) {
		Boolean res = false;
		if(itArboles.hasNext()) {
			res = contieneEtiqueta(itArboles.next(), etq) || existeEtiqueta(itArboles, etq);
		}
		return res;
	}
	
	public static <E> Boolean sonIguales(Tree<E> a1, Tree<E> a2) {
		Boolean res = false;
		if (a1.isEmpty()) {
			res = a2.isEmpty();
		} else if (a1.isLeaf()) {
			res =  a2.isLeaf() && a1.getLabel().equals(a2.getLabel());
		} else {
			res = a1.getLabel().equals(a2.getLabel())
					&& sonIguales(a1.getChildren().iterator(), a2.getChildren().iterator());
		} 
		return res;
	}

	private static <E> Boolean sonIguales(Iterator<Tree<E>> it1, Iterator<Tree<E>> it2) {
		Boolean res = it1.hasNext() && it2.hasNext();
		if (res) {
			res = sonIguales(it1.next(), it2.next()) && sonIguales(it1, it2);
		}
		return res;
	}
	
	public static <E> Tree<E> copiaSimetrica(Tree<E> arbol) {
		Tree<E> res = null;
		if(arbol.isEmpty()) {
			res = Tree.empty();					
		} else if(arbol.isLeaf()) {
			res = Tree.leaf(arbol.getLabel());
		} else {
			res = Tree.nary(arbol.getLabel(), copiasSimetricas(arbol.getChildren().iterator(), new ArrayList<>()));
		}
		return res;
	}
	
	private static <E> List<Tree<E>> copiasSimetricas(Iterator<Tree<E>> itArboles, List<Tree<E>> res) {
		if(itArboles.hasNext()) {
			res.add(0, copiaSimetrica(itArboles.next()));
			copiasSimetricas(itArboles, res);
		}
		return res;
	}

	public static <E> List<E> recorridoPostOrden(Tree<E> arbol) {
		if(arbol.isEmpty()) {			
			return new ArrayList<>();
		} else if(arbol.isLeaf()) {
			List<E> res = new ArrayList<>();
			res.add(arbol.getLabel());
			return res;
		} else {
			List<E> res = recorridoPostOrden(arbol.getChildren().iterator());
			res.add(arbol.getLabel());

			return res;
		}
	}
	
	private static <E> List<E> recorridoPostOrden(Iterator<Tree<E>> itArboles) {
		List<E> res = new ArrayList<>();
		if(itArboles.hasNext()) {
			res.addAll(recorridoPostOrden(itArboles.next()));
			res.addAll(recorridoPostOrden(itArboles));
		}
		return res;
	}

}
