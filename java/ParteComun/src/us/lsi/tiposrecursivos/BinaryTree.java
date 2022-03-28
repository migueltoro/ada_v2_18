package us.lsi.tiposrecursivos;

import java.util.function.Function;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import us.lsi.common.MutableType;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.BinaryTrees.BinaryTreeLevel;
import us.lsi.tiposrecursivos.parsers.BinaryTreeLexer;
import us.lsi.tiposrecursivos.parsers.BinaryTreeParser;

public sealed interface BinaryTree<E> permits BEmpty<E>,BLeaf<E>,BTree<E> {
	
	public enum BinaryType{Empty,Leaf,Binary}
	
	BinaryType type();
	int size();
	int height();
	BinaryTree<E> copy();
	BinaryTree<E> reverse();
	<R> BinaryTree<R> map(Function<E, R> f);
	
	public default Stream<BinaryTree<E>> byDeph() {
		return BinaryTrees.byDeph(this);
	}
	
	public default Stream<BinaryTreeLevel<E>> byLevel() {
		return BinaryTrees.byLevel(this);
	}
	
	public default void toDot(String file) {
		BinaryTrees.toDot(this,file);
	}
	
	public static <E> BinaryTree<E> empty() {
		return new BEmpty<E>();
	}
	
	public static <E> BinaryTree<E> leaf(E label) {
		return new BLeaf<E>(label);
	}
	
	public static <E> BinaryTree<E> binary(E label, BinaryTree<E> left, BinaryTree<E> right) {
		return new BTree<E>(label,left,right);
	}
	
	@SuppressWarnings("unchecked")
	public static BinaryTree<String> parse(String s) {
		BinaryTreeLexer lexer = new BinaryTreeLexer(CharStreams.fromString(s));
		BinaryTreeParser parser = new BinaryTreeParser(new CommonTokenStream(lexer));
		ParseTree parseTree = parser.binary_tree();
		BinaryTree<String> tree = (BinaryTree<String>) parseTree.accept(new BinaryTreeVisitorC());
		return tree;
	}
	
	public static <E> BinaryTree<E> parse(String s, Function<String,E> f) {
		BinaryTree<String> tree = BinaryTree.parse(s);
		return tree.map(f);
	}
	
	public static <E,R> BinaryTree<R> map(BinaryTree<E> tree, Function<E,R> f) {
		return switch(tree) {
		case BEmpty<E> t -> BinaryTree.empty();
		case BLeaf<E> t -> BinaryTree.leaf(f.apply(t.label()));
		case BTree<E> t -> BinaryTree.binary(f.apply(t.label()),
				BinaryTree.map(t.left(),f),BinaryTree.map(t.right(),f));
		};
	}
	
	public default BinaryTree<E> equilibrate() {
		return BinaryTree.equilibrate(this);
	}

	public static <E> BinaryTree<E> equilibrate(BinaryTree<E> tree) {
		Patterns<E> pt = Patterns.of();
		MutableType<Boolean> r = MutableType.of(true);
		BinaryTree<E> t = BinaryPattern.transform(tree, pt.leftLeft, pt.result, r);
		if (r.value()) return t;
		t = BinaryPattern.transform(tree, pt.leftRight, pt.result, r);
		if (r.value()) return t;
		t = BinaryPattern.transform(tree, pt.rightLeft, pt.result, r);
		if (r.value()) return t;
		t = BinaryPattern.transform(tree, pt.rightRight, pt.result, r);
		if (r.value()) return t;
		return t;
	}   
	
	public enum TypeEquilibrate{LeftRight, LeftLeft, RightLeft, RightRight, Equilibrate} 
	
	public static <E> TypeEquilibrate equilibrateType(BinaryTree<E> tree) {
		Patterns<E> pt = Patterns.of();
		TypeEquilibrate r;
		if(pt.leftRight.match(tree).match) r = TypeEquilibrate.LeftRight;
		else if(pt.leftLeft.match(tree).match) r = TypeEquilibrate.LeftLeft;
		else if(pt.rightLeft.match(tree).match) r = TypeEquilibrate.RightLeft;
		else if(pt.rightRight.match(tree).match) r = TypeEquilibrate.RightRight;
		else r = TypeEquilibrate.Equilibrate;
		return r;
	}
	
	public default TypeEquilibrate equilibrateType() {
		return BinaryTree.equilibrateType(this);
	}
	
	static class Patterns<R> {
		BinaryPattern<R> leftRight; 
	    BinaryPattern<R> rightLeft;
	    BinaryPattern<R> leftLeft;
	    BinaryPattern<R> rightRight;
	    BinaryPattern<R> result;
	    private static Patterns<?> patterns = null;
	    @SuppressWarnings("unchecked")
		public static <R> Patterns<R> of(){
	    	if(patterns==null) patterns = new Patterns<R>();
	    	return (Patterns<R>)patterns;
	    }
	    public Patterns() {
			super();
			this.leftRight = BinaryPattern.parse("_e5(_e3(_A,_e4(_B,_C)),_D)");
			this.rightLeft = BinaryPattern.parse("_e3(_A,_e5(_e4(_B,_C),_D))");
			this.leftLeft = BinaryPattern.parse("_e5(_e4(_e3(_A,_B),_C),_D)");
			this.rightRight = BinaryPattern.parse("_e3(_A,_e4(_B,_e5(_C,_D)))");
			this.result = BinaryPattern.parse("_e4(_e3(_A,_B),_e5(_C,_D))");
		} 
	}
	
	public static record BEmpty<E>() implements BinaryTree<E> {
		public BinaryType type() { return BinaryType.Empty;}
		public int size() { return 0; }
		public int height() { return 0; }
		public BinaryTree<E> copy() { return BinaryTree.empty(); }
		public BinaryTree<E> reverse() { return BinaryTree.empty(); }
		public <R> BinaryTree<R> map(Function<E, R> f) { return BinaryTree.empty();}
		public String toString() { return "_"; }
	}
	
	public static record BLeaf<E>(E label) implements BinaryTree<E> {
		public BinaryType type() { return BinaryType.Leaf;}
		public int size() { return 1; }
		public int height() { return 0; }
		public BinaryTree<E> copy() { return BinaryTree.leaf(this.label()); }
		public BinaryTree<E> reverse() { return BinaryTree.leaf(this.label()); }
		public <R> BinaryTree<R> map(Function<E, R> f) { return BinaryTree.leaf(f.apply(this.label()));}
		public String toString() { return this.label().toString(); }
	}
	
	public static record BTree<E>(E label, BinaryTree<E> left, BinaryTree<E> right) 
			implements BinaryTree<E> {
		public BinaryType type() { return BinaryType.Binary;}
		public int size() { return 1+this.left().size()+this.right().size();}
		public int height() {return 1 + Math.max(this.left().height(), this.right().height());}
		public BinaryTree<E> copy() { return BinaryTree.binary(this.label(),this.left().copy(),this.right().copy()); }
		public BinaryTree<E> reverse() { return BinaryTree.binary(this.label(),this.right().copy(),this.left().copy()); }
		public <R> BinaryTree<R> map(Function<E, R> f) { return BinaryTree.binary(f.apply(this.label()),
				this.left().map(f),this.right().map(f));}
		public String toString() { return String.format("%s(%s,%s)",
				this.label().toString(),this.left().toString(),this.right().toString());}
	}
	
}