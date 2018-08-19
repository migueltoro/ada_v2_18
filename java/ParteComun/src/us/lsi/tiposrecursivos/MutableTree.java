package us.lsi.tiposrecursivos;

import us.lsi.common.Preconditions;

public class MutableTree<E> {


	public static <E> MutableTree<E> mutableView(Tree<E> tree) {
		return new MutableTree<E>(tree);
	}
	
	Tree<E> tree;

	private MutableTree(Tree<E> tree) {
		this.tree = tree;
	}
	
	public Object getIdentity() {
		return tree;
	}
	
	public void setLabel(E label) {
		Preconditions.checkNotNull(label);
		this.tree.label = label;
	}
	
	public void setChild(int i, Tree<E> tree) {
		Preconditions.checkNotNull(tree);
		tree.father = this.tree;
		this.tree.elements.set(i, tree);
	}

	public void addChild(Tree<E> tree) {
		Preconditions.checkNotNull(tree);
		tree.father = this.tree;
		this.tree.elements.add(tree);
	}
	
	public void addChild(int i, Tree<E> tree) {
		Preconditions.checkNotNull(tree);
		tree.father = this.tree;
		this.tree.elements.add(i,tree);
	} 
	
	public void removeChild(int i) {
		this.tree.elements.get(i).father = null;
		this.tree.elements.remove(i);
	}
	
	public void setFather(Tree<E> father) {
		this.tree.father = father;
	}
	
	/**
	 * @post this pasa a ser un arbol raiz si no lo era antes
	 * @param nTree Un árbol 
	 * @return Si this no es raiz devuelve el arbol padre con el hijo this cambiado por nTree. Si this es raiz devuelve nTree
	 */
	public Tree<E> changeFor(Tree<E> nTree) {
		Tree<E> r;
		if(this.tree.isRoot()) {
			r = nTree;
		} else {
			r = this.tree.getFather();
			MutableTree<E> rm = r.mutableView();
			setFather(null);
			for (int i = 0; i < this.tree.getNumOfChildren(); i++) {
				if (this.tree.isChild(i)) {
					rm.setChild(i, nTree);
				}
			}
		}
		return r;
	}
}
