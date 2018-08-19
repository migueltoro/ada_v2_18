package us.lsi.tiposrecursivos;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;
import us.lsi.common.Strings2;
import us.lsi.common.Tuple2;
import us.lsi.regularexpressions.Tokenizer;
import us.lsi.regularexpressions.Tokenizer.TokenType;

public class BinaryTree<E> {
	
	public enum BinaryType{Empty,Leaf,Binary}
	public enum ChildType{Left,Right,Root}
	
	static <E> BinaryTree<E> create(Tree<E> tree) {
		Preconditions.checkNotNull(tree);
		return new BinaryTree<E>(tree);
	}

	public static <E> BinaryTree<E> empty() {
		return new BinaryTree<E>(Tree.empty());
	}
	
	public static <E> BinaryTree<E> leaf(E label) {
		return new BinaryTree<E>(Tree.leaf(label));
	}
	
	public static <E> BinaryTree<E> binary(E label, BinaryTree<E> left, BinaryTree<E> right) {
		BinaryTree<E> r = null;
		if (left.isEmpty() && right.isEmpty()) {
			r = new BinaryTree<E>(Tree.leaf(label));
		} else {
			r = new BinaryTree<E>(Tree.nary(label, left.tree, right.tree));
		}
		return r;
	}
	
	public static BinaryTree<String> parse(String s){
		Tokenizer tk = Tokenizer.create(s);
		BinaryTree<String> tree = BinaryTree.tree(tk);
		if (tk.hasMoreTokens()) {
			Preconditions.checkState(false,String.format("Cadena no consumida. Error en posición %d Sufijo %s", tk.getPosition(),tk.getSufix()));
		}
		return tree;
	}
	
	public static <E> BinaryTree<E> parse(String s, Function<String,E> f) {
		Preconditions.checkNotNull(s);
		BinaryTree<String> tree = BinaryTree.parse(s);
		return tree.map(f);
	}
	
	private static BinaryTree<String> tree(Tokenizer tk) {
		BinaryTree<String> r= null;
		String label;
		switch (tk.seeNextTokenType()) {			
		case Operator:
		case Integer:
		case Double:	
		case VariableIdentifier:
			label = label(tk);
				if (label.equals("_")) {
					r = BinaryTree.empty();
				} else if (!tk.seeNextToken().equals("(")) {
					r = BinaryTree.leaf(label);
				} else {
					tk.matchTokens("(");
					BinaryTree<String> left = tree(tk);
					tk.matchTokens(",");
					BinaryTree<String> right = tree(tk);
					tk.matchTokens(")");
					r = BinaryTree.binary(label, left, right);
				} 
			
			break;	
		default:
			Preconditions.checkState(false, String.format("Token %s 99 no reconocido en la posición %d",tk.getToken(),tk.getPosition()));
		}
		return r;
	}
	
	private static String label(Tokenizer tk) {
		String token = "";
		String label = null;
		switch (tk.seeNextTokenType()) {			
		case Operator:
			token = tk.getToken();
			if(!token.equals("+") && !token.equals("-")) {
				Preconditions.checkState(false, String.format("Token %s 11 no reconocido en la posición %d",token,tk.getPosition()));
			}
			tk.matchTokenTypes(TokenType.Operator);
			label = tk.matchTokenTypes(TokenType.Integer,TokenType.Double); 
			label = token+label;
			break;
		case Integer:
		case Double:	
		case VariableIdentifier:
			label = tk.matchTokenTypes(TokenType.VariableIdentifier, TokenType.Integer,TokenType.Double); 
			break;
		default:
			Preconditions.checkState(false, String.format("Token %s no reconocido en la posición %d",token,tk.getPosition()));
		}
		return label;	
	}
	
	protected Tree<E> tree;
	private BinaryType type;
	
	protected BinaryTree(Tree<E> tree) {
		super();
		this.tree = tree;
		switch(tree.getType()) {
		case Empty: this.type = BinaryType.Empty; break;
		case Leaf: this.type = BinaryType.Leaf; break;
		case Nary: this.type = BinaryType.Binary;break;		
		}
	}

	public Object getIdentity() {
		return tree;
	}
	
	public boolean isEmpty() {
		return type.equals(BinaryType.Empty);
	}

	public boolean isLeaf() {
		return type.equals(BinaryType.Leaf);
	}

	public boolean isBinary() {
		return type.equals(BinaryType.Binary);
	}	
	
	public BinaryType getType() {
		return type;
	}
	
	public BinaryTree<E> getFather() {
		return create(tree.getFather());
	}

	public boolean isRoot() {
		return tree.isRoot();
	}
	
	public Boolean isLeftChild() {
		Boolean r;
		if(isRoot()) {
			r = false;
		} else {
			r = getFather().getLeft().getIdentity() == this.getIdentity();
		}
		return r;
	}
	
	public Boolean isRightChild() {
		Boolean r;
		if(isRoot()) {
			r = false;
		} else {
			r = getFather().getRight().getIdentity() == this.getIdentity();
		}
		return r;
	}

	public ChildType getChildType() {
		ChildType r = null;
		if(isRoot()) {
			r = ChildType.Root;
		} else if(this.getFather().getLeft().getIdentity() == this.getIdentity()) {
			r = ChildType.Left;
		} else if(this.getFather().getRight().getIdentity() == this.getIdentity()) {
			r = ChildType.Right;
		} else {
			Preconditions.checkState(false, "Fallo interno");
		}
		return r;
	}
	
	public E getLabel() {
		Preconditions.checkState(!isEmpty(), String.format("El árbol %s es vacío", this));
		return tree.getLabel();
	}
	
	public BinaryTree<E> getLeft(){
		Preconditions.checkState(isBinary(), String.format("El árbol %s no es binario", this));
		return BinaryTree.create(tree.getChild(0));
	}

	public BinaryTree<E> getRight(){
		Preconditions.checkState(isBinary(), String.format("El árbol %s no es binario", this));
		return BinaryTree.create(tree.getChild(1));
	}
	
	
	
	public MutableBinaryTree<E> mutableView(){
		return MutableBinaryTree.asMutable(this);
	}
	
	public int size() {
		return tree.size();
	}
	
	public int getHeight() {
		return tree.getHeight();
	}
	
	private static <E> List<BinaryTree<E>> nextLevel(List<BinaryTree<E>> ls){
		List<BinaryTree<E>> r = Lists2.newList();
		for(BinaryTree<E> tree: ls) {
			switch(tree.getType()) {			
			case Empty:
			case Leaf: r.add(BinaryTree.empty()); r.add(BinaryTree.empty()); break;
			case Binary:
				r.add(tree.getLeft()); r.add(tree.getRight()); break;
			}
		}
		return r;
	}
	
	public List<BinaryTree<E>> getLevel(int n){
		List<BinaryTree<E>> r = Lists2.newList(this);
		for(int i=0; i < n ; i++) {
			r = nextLevel(r);
		}
		return r;
	}
	
	public List<Integer> getHeights(int n){
		return this.getLevel(n).stream().map(x->x.getHeight()).collect(Collectors.toList());
	}
	
	public BinaryTree<E> copy() {
		return BinaryTree.create(tree.copy());
	}

	public <R> BinaryTree<R> copy(Function<E, R> f) {
		return BinaryTree.create(tree.copy(f));
	}

	public BinaryTree<E> getReverse() {
		return BinaryTree.create(tree.getReverse());
	}

	public void toDOT(String file, String titulo) {
		tree.toDOT(file, titulo);
	}

	public List<E> getPreOrder() {
		return tree.getPreOrder();
	}

	public List<E> getPostOrder() {
		return tree.getPostOrder();
	}

	public List<E> getLabelByLevel() {
		return tree.getLabelByLevel();
	}

	public List<BinaryTree<E>> getLevel(Integer n) {
		return tree.getLevel(n)
				.stream()
				.map(t->BinaryTree.create(t))
				.collect(Collectors.toList());
	}

	public int getDepth(Tree<E> root) {
		return tree.getDepth(root);
	}
	
	
	public <R> BinaryTree<R> map(Function<E,R> f){
		BinaryTree<R> r = null;
		switch(this.getType()) {	
		case Empty: r = BinaryTree.empty(); break;
		case Leaf: r = BinaryTree.leaf(f.apply(this.getLabel())); break;
		case Binary: r = BinaryTree.binary(f.apply(this.getLabel()), this.getLeft().map(f), this.getRight().map(f)); break;
		}
		return r;
	}
	
	public Tuple2<Boolean,BinaryTree<E>> transform(BinaryPattern<E> pattern, BinaryPattern<E> result){
		return BinaryPattern.transform(this, pattern, result);
	}
	
	public Boolean match(BinaryPattern<E> pt) {
		return BinaryPattern.match(this, pt);
	}

	public String toString() {
		return tree.toString();
	}
	
	public static void main(String[] args) {
		BinaryTree<Integer> t1 = BinaryTree.empty();
		BinaryTree<Integer> t2 = BinaryTree.leaf(2);
		BinaryTree<Integer> t3 = BinaryTree.leaf(3);
		BinaryTree<Integer> t4 = BinaryTree.leaf(4);
		BinaryTree<Integer> t5 = BinaryTree.binary(27,BinaryTree.binary(42,t1,t2),BinaryTree.binary(59,t3,t4));
		BinaryTree<Integer> t6 = BinaryTree.binary(39, t2,t5);
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t6);		
		String ex = "-43.7(2.1,abc(-27.3(_,2),78.2(3,4)))";
		BinaryTree<String> t7 = BinaryTree.parse(ex);
		System.out.println(t7);
		System.out.println(Lists2.reverse(Lists2.newList(1,2,3,4,5,6,7,8,9)));
		BinaryTree<String> t8 = t7.getReverse();
		System.out.println(t8);
		MutableBinaryTree<String> t = t8.mutableView();
		t.setLabel("578.");
		t.setLeft(t8.getLeft().getLeft());
		System.out.println(t8);
		Strings2.toConsole(t8.getLevel(3).stream().map(x->x.getHeight()).collect(Collectors.toList()).toString());
		BinaryTree<String> t10 = t8.getRight();
		System.out.println(t8);
		System.out.println(t10);
		System.out.println(t10.getFather().getRight() == t10);
		System.out.println(t10.isRightChild());
		MutableBinaryTree<String> mt10 = t10.mutableView();
		mt10.changeFor(t8.getLeft());
		System.out.println(t8);
		System.out.println(t10);
		System.out.println(t8.isRoot());
		System.out.println(t10.isRoot());
		
	}

}
