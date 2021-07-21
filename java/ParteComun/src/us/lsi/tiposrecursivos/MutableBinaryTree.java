package us.lsi.tiposrecursivos;

public interface MutableBinaryTree<E> extends BinaryTree<E> {
	
	/**
	 * @post isEmpty()
	 * @return Construye un árbol vacío
	 */
	public static <E> MutableBinaryTree<E> empty() {
		return BinaryTreeImpl.empty();
	}
	
	/**
	 * @param label Una etiqueta
	 * @post isLeaf()
	 * @return Construye un árbol hoja
	 */
	public static <E> MutableBinaryTree<E> leaf(E label) {
		return BinaryTreeImpl.leaf(label);
	}
	
	/**
	 * @param label Una etiqueta
	 * @param left Un arbol
	 * @param right Un arbol
	 * @post isBinary()
	 * @return Construye un árbol binario
	 */
	public static <E> MutableBinaryTree<E> binary(E label, MutableBinaryTree<E> left, MutableBinaryTree<E> right) {
		return BinaryTreeImpl.binary(label, left, right);
	}
	
	public static <E> MutableBinaryTree<E> mutable(BinaryTree<E> tree) {
		return (MutableBinaryTree<E>) tree;
	}
	
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
