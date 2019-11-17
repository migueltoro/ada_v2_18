package us.lsi.tiposrecursivos;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;
import us.lsi.common.Streams2;
import us.lsi.regularexpressions.Token;
import us.lsi.regularexpressions.Tokenizer;
import us.lsi.regularexpressions.Tokenizer.TokenType;

public class BinaryTree<E> {
	
	public enum BinaryType{Empty,Leaf,Binary}
	public enum ChildType{Left,Right,Root}
	
	public static <E> BinaryTree<E> create(BinaryTree<E> tree) {
		Preconditions.checkNotNull(tree);
		return tree.copy();
	}

	private static BinaryTree<Object> empty = new BinaryTree<Object>();
			
	@SuppressWarnings("unchecked")
	public static <E> BinaryTree<E> empty() {
		return (BinaryTree<E>) empty;
	}
	
	public static <E> BinaryTree<E> leaf(E label) {
		return new BinaryTree<E>(label);
	}
	
	public static <E> BinaryTree<E> binary(E label, BinaryTree<E> left, BinaryTree<E> right) {
		BinaryTree<E> r = null;
		if (left.isEmpty() && right.isEmpty()) {
			r = new BinaryTree<E>(label);
		} else {
			r = new BinaryTree<E>(label, left, right);
		}
		return r;
	}
	
	public static BinaryTree<String> parse(String s){
		Tokenizer tk = Tokenizer.create(s);
		BinaryTree<String> tree = BinaryTree.parse(tk);
		if (tk.hasMoreTokens()) {
			Preconditions.checkState(false,
					String.format("Cadena no consumida. \nError en cadena %s\n == posición %d Sufijo %s", s, tk.index(),tk.suffix()));
		}
		return tree;
	}
	
	public static <E> BinaryTree<E> parse(String s, Function<String,E> f) {
		Preconditions.checkNotNull(s);
		BinaryTree<String> tree = BinaryTree.parse(s);
		return tree.map(f);
	}
	
	
	private static BinaryTree<String> parse(Tokenizer tk) {
		BinaryTree<String> r = null;
		Token token = label_parse(tk);
		if (token.text.equals("_")) {
			r = BinaryTree.empty();
		} else {
			r = BinaryTree.leaf(token.text);
			if (tk.hasMoreTokens() && tk.isNextInTokens("(")) {
				tk.matchTokens("(");
				BinaryTree<String> left = parse(tk);
				tk.matchTokens(",");
				BinaryTree<String> right = parse(tk);
				tk.matchTokens(")");
				r = BinaryTree.binary(token.text, left, right);
			}
		}
		return r;
	}

	private static Token label_parse(Tokenizer tk) {	
		Token token = tk.nextToken();
		switch (token.type) {			
		case Operator:
			if(tk.isCurrentInTokens("+","-")) {
				String op = token.text;
				token = tk.matchTokens(TokenType.Integer,TokenType.Double); 
				String nb = token.text;
				token.text = op+nb;
			} else tk.error("+","-");
			break;
		case Integer:
		case Double:	
		case VariableIdentifier:
			break;
		default: tk.error();
		}
		return token;	
	}

	protected E label;
	protected BinaryTree<E> left;
	protected BinaryTree<E> right;
	protected BinaryTree<E> father;
	private final BinaryType type;
	

	protected BinaryTree() {
		super();
		this.label = null;
		this.left = null;
		this.right = null;
		this.type = BinaryType.Empty;
		this.father = null;
	}

	
	protected BinaryTree(E label) {
		super();
		this.label = label;
		this.left = null;
		this.right = null;		
		this.type = BinaryType.Leaf;
		this.father = null;
	}
	
	protected BinaryTree(E label, BinaryTree<E> left, BinaryTree<E> right) {
		super();
		this.label = label;
		this.left = left;
		this.right = right;
		this.type = BinaryType.Binary;	
		this.father = null;
		this.left.father = this;
		this.right.father = this;
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
		return this.father;
	}

	public boolean isRoot() {
		return this.father == null;
	}
	
	public Boolean isLeftChild() {
		Boolean r;
		if(isRoot()) {
			r = false;
		} else {
			r = getFather().getLeft() == this;
		}
		return r;
	}
	
	public Boolean isRightChild() {
		Boolean r;
		if(isRoot()) {
			r = false;
		} else {
			r = getFather().getRight() == this;
		}
		return r;
	}

	public ChildType getChildType() {
		ChildType r = null;
		if(isRoot()) {
			r = ChildType.Root;
		} else if(this.getFather().getLeft() == this) {
			r = ChildType.Left;
		} else if(this.getFather().getRight() == this) {
			r = ChildType.Right;
		} else {
			Preconditions.checkState(false, "Fallo interno");
		}
		return r;
	}
	
	public E getLabel() {
		Preconditions.checkState(!isEmpty(), String.format("El árbol es vacío"));
		return this.label;
	}
	
	public BinaryTree<E> getLeft(){
		Preconditions.checkState(isBinary(), String.format("El árbol no es binario"));
		return this.left;
	}

	public BinaryTree<E> getRight(){
		Preconditions.checkState(isBinary(), String.format("El árbol no es binario"));
		return this.right;
	}
	
	public MutableBinaryTree<E> mutableView(){
		return MutableBinaryTree.asMutable(this);
	}
	
	public int size() {
		int r =0;
		switch(this.getType()) {
		case Empty: r = 0; break;
		case Leaf:  r = 1; break;
		case Binary: r = 1+this.getLeft().size()+ this.getRight().size(); break;
		}
		return r;
	}
	
	public int getHeight() {
		Integer r=0;
		switch(this.getType()) {
		case Empty: r = -1; break;
		case Leaf:  r = 0; break;
		case Binary: r = 1+ Math.max(this.getLeft().getHeight(),this.getRight().getHeight()); break;
		}
		return r;
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
		BinaryTree<E> r= null;
		switch(this.getType()) {
		case Empty: r = BinaryTree.empty(); break;
		case Leaf:  r = BinaryTree.leaf(label); break;
		case Binary: r = BinaryTree.binary(label,this.getLeft().copy(),this.getRight().copy()); break;
		}
		return r;
	}

	public <R> BinaryTree<R> copy(Function<E, R> f) {
		BinaryTree<R> r= null;
		switch(this.getType()) {
		case Empty: r = BinaryTree.empty(); break;
		case Leaf:  r = BinaryTree.leaf(f.apply(label)); break;
		case Binary: r = BinaryTree.binary(f.apply(label),this.getLeft().copy(f),this.getRight().copy(f)); break;
		}
		return r;
	}

	public BinaryTree<E> getReverse() {
		BinaryTree<E> r= null;
		switch(this.getType()) {
		case Empty: r = BinaryTree.empty(); break;
		case Leaf:  r = BinaryTree.leaf(label); break;
		case Binary: r = BinaryTree.binary(label,this.getRight().copy(),this.getLeft().copy()); break;
		}
		return r;
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
		
	public BinaryTree<E> transform(BinaryPattern<E> pattern, BinaryPattern<E> result){
		return BinaryPattern.transform(this, pattern, result);
	}
	
	public BinaryPattern<E> match(BinaryPattern<E> pt) {
		return BinaryPattern.match(this, pt);
	}

	public String toString() {
		String r = null;
		switch (this.getType()) {
		case Empty: r ="_"; break;
		case Leaf: r = label.toString(); break;
		case Binary: r = label.toString()+"("+this.getLeft().toString()+","+this.getRight().toString()+")";
		}
		return r;
	}
	
	public List<E> getPreOrder() {
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = Lists2.newList(); break;
		case Leaf: r = Lists2.newList(this.label); break;
		case Binary:
			r = Lists2.newList(this.label);
			r.addAll(this.getLeft().getPreOrder());
			r.addAll(this.getRight().getPreOrder());
		}
		return r;
	}

	public List<E> getPostOrder() {
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = Lists2.newList(); break;
		case Leaf: r = Lists2.newList(this.label); break;
		case Binary:
			r = this.getLeft().getPostOrder();
			r.addAll(this.getRight().getPostOrder());
			r.add(this.getLabel());
		}
		return r;
	}

	public List<E> getInOrder() {
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = Lists2.newList(); break;
		case Leaf: r = Lists2.newList(this.label); break;
		case Binary:
			r = this.getLeft().getInOrder();
			r.add(this.getLabel());
			r.addAll(this.getRight().getInOrder());			
		}
		return r;
	}
	
	public static void test1() {
		List<String> filas = Streams2.fromFile("ficheros/test2.txt").collect(Collectors.toList());
		BinaryTree<String> tree = null;
		for (String fila : filas) {
			tree = BinaryTree.parse(fila);
			System.out.println(tree);
		}
	}
	
	public static void main(String[] args) {
//		BinaryTree<Integer> t1 = BinaryTree.empty();
//		BinaryTree<Integer> t2 = BinaryTree.leaf(2);
//		BinaryTree<Integer> t3 = BinaryTree.leaf(3);
//		BinaryTree<Integer> t4 = BinaryTree.leaf(4);
//		BinaryTree<Integer> t5 = BinaryTree.binary(27,BinaryTree.binary(42,t1,t2),BinaryTree.binary(59,t3,t4));
//		BinaryTree<Integer> t6 = BinaryTree.binary(39, t2,t5);
//		System.out.println(t1);
//		System.out.println(t2);
//		System.out.println(t6);		
//		String ex = "-43.7(2.1,abc(-27.3(_,2),78.2(3,4)))";
//		BinaryTree<String> t7 = BinaryTree.parse(ex);
//		System.out.println(t7);
//		System.out.println(Lists2.reverse(Lists2.newList(1,2,3,4,5,6,7,8,9)));
//		BinaryTree<String> t8 = t7.getReverse();
//		System.out.println(t8);
//		MutableBinaryTree<String> t = t8.mutableView();
//		t.setLabel("578.");
//		t.setLeft(t8.getLeft().getLeft());
//		System.out.println(t8);
//		Strings2.toConsole(t8.getLevel(3).stream().map(x->x.getHeight()).collect(Collectors.toList()).toString());
//		BinaryTree<String> t10 = t8.getRight();
//		System.out.println(t8);
//		System.out.println(t10);
//		System.out.println(t10.getFather().getRight() == t10);
//		System.out.println(t10.isRightChild());
//		MutableBinaryTree<String> mt10 = t10.mutableView();
//		mt10.changeFor(t8.getLeft());
//		System.out.println(t8);
//		System.out.println(t10);
//		System.out.println(t8.isRoot());
//		System.out.println(t10.isRoot());
		test1();
	}

}
