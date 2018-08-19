package us.lsi.tiposrecursivos;

public class MutableBinaryTree<E>  {
	
	public static <E> MutableBinaryTree<E> asMutable(BinaryTree<E> tree) {
		return new MutableBinaryTree<E>(tree);
	}

	private MutableTree<E> mTree;
	private BinaryTree<E> tree;

	private MutableBinaryTree(BinaryTree<E> tree) {
		super();
		this.mTree = tree.tree.mutableView();
		this.tree = tree;
	}
	
	public void setLabel(E label) {
		this.mTree.setLabel(label);
	}

	public void setLeft(BinaryTree<E> left) {
		this.mTree.setChild(0,left.tree);
	}
	
	public void setRight(BinaryTree<E> right) {
		this.mTree.setChild(1,right.tree);
	}
	
	public void setFather(BinaryTree<E> father) {
		if (father != null) {
			this.mTree.setFather(father.tree);
		} else {
			this.mTree.setFather(null);
		}
	}
	
	/**
	 * @post this pasa a ser un arbol raiz si no lo era antes. nTree pasa a ocupar el lugar de this. El padre de nTree
	 * es el antiguo padre de this
	 * @param nTree Un árbol binario
	 * @return  Devuelve nTree
	 */
	public BinaryTree<E> changeFor(BinaryTree<E> nTree) {
		switch(this.tree.getChildType()) {
		case Root: break;
		case Left: this.tree.getFather().mutableView().setLeft(nTree); break;
		case Right: this.tree.getFather().mutableView().setRight(nTree); break;
		}
		return nTree;
	}
	
	public String toString() {
		return tree.toString();
	}
}


