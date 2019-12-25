package us.lsi.tiposrecursivos;

public interface MutableBinaryTree<E> extends BinaryTree<E> {
	
	void setLabel(E label);

	void setLeft(BinaryTree<E> left);
	
	void setRight(BinaryTree<E> right);
	
	void setFather(BinaryTree<E> father); 
	
	/**
	 * @post this pasa a ser un arbol raiz si no lo era antes. nTree pasa a ocupar el lugar de this. El padre de nTree
	 * es el antiguo padre de this
	 * @param nTree Un árbol binario
	 * @return  Devuelve nTree
	 */
	BinaryTree<E> changeFor(BinaryTree<E> nTree); 
	
}
