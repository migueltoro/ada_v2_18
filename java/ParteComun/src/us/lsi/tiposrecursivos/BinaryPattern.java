package us.lsi.tiposrecursivos;

import java.util.Map;
import java.util.function.Function;

import us.lsi.common.Maps2;
import us.lsi.common.Preconditions;
import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;


public class BinaryPattern<E> {
	
	
	public enum PatternType{Empty,Leaf,LeafFree,Binary,BinaryFree,Free}
	
	public static <E> BinaryPattern<E> binary(E label, BinaryPattern<E> left, BinaryPattern<E> right) {
		Preconditions.checkNotNull(label);
		return new BinaryPattern<E>(PatternType.Binary,null, label, left, right, null);
	}
	
	public static <E> BinaryPattern<E> binaryFree(String name,BinaryPattern<E> left, BinaryPattern<E> right) {
		Preconditions.checkNotNull(name);
		return new BinaryPattern<E>(PatternType.BinaryFree,name, null, left, right, null);
	}
	
	public static <E> BinaryPattern<E> leaf(E label) {
		Preconditions.checkNotNull(label);
		return new BinaryPattern<E>(PatternType.Leaf,null, label, null, null, null);
	}
	
	public static <E> BinaryPattern<E> leafFree(String name) {
		Preconditions.checkNotNull(name);
		return new BinaryPattern<E>(PatternType.LeafFree,name,null, null, null, null);
	}
	
	public static <E> BinaryPattern<E> empty() {
		return new BinaryPattern<E>(PatternType.Empty,null,null, null, null, null);
	}
	
	public static <E> BinaryPattern<E> free(String name) {
		Preconditions.checkNotNull(name);
		return new BinaryPattern<E>(PatternType.Free,name, null, null, null, null);
	}
	
	public static <E> BinaryPattern<E> parse(String s, Function<String,E> f) {
		Preconditions.checkNotNull(s);
		BinaryTree<String> tree = BinaryTree.parse(s);
		return BinaryPattern.toPattern(tree, f);
	}
	
	public static <E> BinaryPattern<E> parse(String s) {
		Preconditions.checkNotNull(s);
		BinaryTree<String> tree = BinaryTree.parse(s);
		return BinaryPattern.toPattern(tree);
	}
	
	private final String name;
	private E label;
	private final BinaryPattern<E> left;
	private final BinaryPattern<E> right;
	private BinaryTree<E> tree;
	private PatternType type;
	
	
	private BinaryPattern(PatternType type, String name, E label, BinaryPattern<E> left, BinaryPattern<E> right, 
			BinaryTree<E> tree) {
		super();
		this.type = type;
		this.name = name;
		this.label = label;
		this.left = left;
		this.right = right;
		this.tree = tree;		
	}

	public E getLabel() {
		return label;
	}

	public void setLabel(E label) {
		Preconditions.checkState(
				 this.getType().equals(PatternType.LeafFree) ||
				 this.getType().equals(PatternType.BinaryFree)
				 , "Es un patrón fijo");
		this.label = label;
	}

	public BinaryTree<E> getTree() {
		Preconditions.checkState(this.getType().equals(PatternType.Free), "Es un patrón fijo");
		return tree;
	}

	public void setTree(BinaryTree<E> tree) {
		Preconditions.checkState(this.getType().equals(PatternType.Free), "Es un patrón fijo");
		this.tree = tree;
	}

	public String getName() {
		Preconditions.checkState(this.getType().equals(PatternType.Free) || 
								 this.getType().equals(PatternType.LeafFree) ||
								 this.getType().equals(PatternType.BinaryFree)
								 , "Es un patrón fijo");
		return name;
	}

	public BinaryPattern<E> getLeft() {
		return left;
	}

	public BinaryPattern<E> getRight() {
		return right;
	}
	
	public PatternType getType() {
		return type;
	}
	
	public Map<String,E> labelValues() {
		return labelValues(this);
	}
	
	public Map<String,BinaryTree<E>> treeValues() {
		return treeValues(this);
	}
	
	public BinaryTree<E> getTree(String name){
		return this.treeValues().get(name);
	}
	
	public E getLabel(String name){
		return this.labelValues().get(name);
	}
	
	private static <E> Map<String,E> labelValues(BinaryPattern<E> pattern){
		Map<String,E> r = null;
		switch(pattern.getType()) {
		case Empty: r =  Maps2.newHashMap(); break;
		case Free: r = Maps2.newHashMap(); break;
		case Leaf: r = Maps2.newHashMap(); break;
		case LeafFree: r = Maps2.newHashMap(pattern.getName(),pattern.getLabel()); break;
		case Binary: 
			r = Maps2.newHashMap(labelValues(pattern.getLeft()));
			r.putAll(labelValues(pattern.getRight()));
			break;
		case BinaryFree:
			r = Maps2.newHashMap(labelValues(pattern.getLeft()));
			r.putAll(labelValues(pattern.getRight()));
			r.put(pattern.getName(),pattern.getLabel());
		}
		return r;
	}
	
	private static <E> Map<String,BinaryTree<E>> treeValues(BinaryPattern<E> pattern){
		Map<String,BinaryTree<E>> r = null;
		switch(pattern.getType()) {
		case Free: r = Maps2.newHashMap(pattern.getName(),pattern.getTree()); break;
		case Empty:
		case Leaf: r = Maps2.newHashMap(); break;
		case LeafFree: r = Maps2.newHashMap(); break;
		case Binary: 
		case BinaryFree:
			r = Maps2.newHashMap(treeValues(pattern.getLeft()));
			r.putAll(treeValues(pattern.getRight()));
		}
		return r;
	}
	
	public String toString(){
		String r = null;;
		switch(this.getType()) {
		case Empty: r ="_"; break;
		case Free: r = this.getName(); break;		
		case Leaf: r = label.toString(); break;
		case LeafFree: r = this.getName(); break;
		case Binary: r = label.toString() + "(" + this.getLeft().toString() + "," + this.getRight().toString() + ")"; break;
		case BinaryFree: r = this.getName() + "(" + this.getLeft().toString() + "," + this.getRight().toString() + ")"; break;
		}
		return r;
	}

	public static <E> Boolean match(BinaryTree<E> tree, BinaryPattern<E> pt) {
		Boolean r = null; 
		switch(pt.getType()) {
		case Empty: 
			r = tree.isEmpty();
			break;
		case Free: 
			r = true;
			pt.setTree(tree);  
			break;	
		case Leaf: 
			r = tree.isLeaf() && tree.getLabel().equals(pt.label); 
			break;
		case LeafFree: 
			r = tree.isLeaf();
			if (r) pt.setLabel(tree.getLabel());
			break;
		case Binary: 
			r = tree.isBinary() && 
				tree.getLabel().equals(pt.label) && 
				match(tree.getLeft(),pt.getLeft()) && 						 
				match(tree.getRight(),pt.getRight()); 
			break;
		case BinaryFree:  
			r = tree.isBinary() &&  
				match(tree.getLeft(),pt.getLeft()) && 						 
				match(tree.getRight(),pt.getRight()); 
			if(r) pt.setLabel(tree.getLabel());
			break;				
		}
		return r;	
	}
	
	public static <R> BinaryPattern<R> toPattern(BinaryTree<String> tree, Function<String,R> f){
		BinaryPattern<R> r = null;
		switch(tree.getType()) {
		case Empty: r = BinaryPattern.empty(); break;	
		case Leaf: 
			if (tree.getLabel().charAt(0) != '_') {
				r = BinaryPattern.leaf(f.apply(tree.getLabel()));
			} else if  (Character.isLowerCase(tree.getLabel().charAt(1))){
				r = BinaryPattern.leafFree(tree.getLabel()); 
			} else {
				r = BinaryPattern.free(tree.getLabel()); 
			}
			break;
		case Binary: 
			if (tree.getLabel().charAt(0) != '_') {
				r = BinaryPattern.binary(f.apply(tree.getLabel()), toPattern(tree.getLeft(),f), toPattern(tree.getRight(),f));
			} else if(Character.isLowerCase(tree.getLabel().charAt(1))) {
				r = BinaryPattern.binaryFree(tree.getLabel(), toPattern(tree.getLeft(),f), toPattern(tree.getRight(),f));
			} else {
				Preconditions.checkState(false, "No puede haber una etiqueta de árbol en este lugar");
			}
			break;
		}
		return r;
	}
	
	private static <R> BinaryPattern<R> toPattern(BinaryTree<String> tree){
		BinaryPattern<R> r = null;
		switch(tree.getType()) {
		case Empty: r = BinaryPattern.empty(); break;	
		case Leaf: 
			if (tree.getLabel().charAt(0) != '_') {
				Preconditions.checkState(false, "El patrón no puede tener etiquetas fijas");
			} else if  (Character.isLowerCase(tree.getLabel().charAt(1))){
				r = BinaryPattern.leafFree(tree.getLabel()); 
			} else {
				r = BinaryPattern.free(tree.getLabel()); 
			}
			break;
		case Binary: 
			if (tree.getLabel().charAt(0) != '_') {
				Preconditions.checkState(false, "El patrón no puede tener etiquetas fijas");
			} else if(Character.isLowerCase(tree.getLabel().charAt(1))) {
				r = BinaryPattern.binaryFree(tree.getLabel(), toPattern(tree.getLeft()), toPattern(tree.getRight()));
			} else {
				Preconditions.checkState(false, "No puede haber una etiqueta de árbol en este lugar");
			}
			break;
		}
		return r;
	}
	
	public <R> BinaryPattern<R> map(Function<E,R> f){
		BinaryPattern<R> r = null;
		switch(this.getType()) {
		case Empty: r = BinaryPattern.empty();; break;
		case Free: r = BinaryPattern.free(this.getName()); break;		
		case Leaf: r = BinaryPattern.leaf(f.apply(this.getLabel())); break;
		case LeafFree: r = BinaryPattern.leafFree(this.getName()); break;
		case Binary: r = BinaryPattern.binary(f.apply(this.getLabel()), this.getLeft().map(f), this.getRight().map(f)); break;
		case BinaryFree:  r = BinaryPattern.binaryFree(this.getName(), this.getLeft().map(f), this.getRight().map(f)); break;
		}
		return r;
	}
	
	public BinaryTree<E> toTree(){
		BinaryTree<E> r = null;
		switch(this.getType()) {
		case Empty: r = BinaryTree.empty();; break;
		case Free: r = this.getTree(); break;		
		case Leaf: 
		case LeafFree: r = BinaryTree.leaf(this.getLabel()); break;
		case Binary: 
		case BinaryFree:  r = BinaryTree.binary(this.getLabel(), this.getLeft().toTree(), this.getRight().toTree()); break;
		}
		return r;
	}
	
	public BinaryTree<E> toTree(Map<String,E> labels, Map<String,BinaryTree<E>> trees){
		BinaryTree<E> r = null;
		switch(this.getType()) {
		case Empty: r = BinaryTree.empty();; break;
		case Free: r = trees.get(this.getName()); break;		
		case Leaf: r = BinaryTree.leaf(this.getLabel()); break;
		case LeafFree: r = BinaryTree.leaf(labels.get(this.getName())); break;
		case Binary: r = BinaryTree.binary(this.getLabel(), 
						this.getLeft().toTree(labels,trees), 
						this.getRight().toTree(labels,trees)); break;
		case BinaryFree:  r = BinaryTree.binary(labels.get(this.getName()), 
						this.getLeft().toTree(labels,trees), 
						this.getRight().toTree(labels,trees)); break;
		}
		return r;
	}
	
	public static <E> Tuple2<Boolean,BinaryTree<E>> transform(BinaryTree<E> tree, BinaryPattern<E> pattern, BinaryPattern<E> result){
		BinaryTree<E> r = tree;
		Boolean m = tree.match(pattern);
		if(m) {
			r = result.toTree(pattern.labelValues(), pattern.treeValues());
		}
		return Tuple.create(m,r);
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
//		BinaryTree<Double> t = BinaryTree.parse("-43.7(2.1,56(-27.3(_,2),78.2(3,4)))", x->Double.parseDouble(x));		
//		BinaryPattern<Double> p = BinaryPattern.parse("-43.7(2.1,56(_e0(_,2),_T0))",x->Double.parseDouble(x));
//		BinaryPattern<Double> r = BinaryPattern.parse("-43.7(_T0,56(_e0(_,_T0),_T0))",x->Double.parseDouble(x));
//        BinaryPattern<Double> leftRight = BinaryPattern.parse("_e5(_e3(_A,_e4(_B,_C)),_D)");
//        BinaryTree<Double> tree1 = BinaryTree.parse("54.5(39.2(20.1,50.5(40.2,51.0)),78.9)",x->Double.parseDouble(x));
//        BinaryPattern<Double> rightLeft = BinaryPattern.parse("_e3(_A,_e5(_e4(_B,_C),_D))");
//		  BinaryTree<Double> tree2 = BinaryTree.parse("54.5(39.2,20.1(50.5(40.2,51.0),78.9))",x->Double.parseDouble(x));
          BinaryPattern<Double> leftLeft = BinaryPattern.parse("_e5(_e4(_e3(_A,_B),_C),_D)");
          BinaryTree<Double> tree3 = BinaryTree.parse("54.5(39.2(20.1(50.5,40.2),51.0),78.9)",x->Double.parseDouble(x));
//        BinaryPattern<Double> rightRight = BinaryPattern.parse("_e3(_A,_e4(_B,_e5(_C,_D)))");
//        BinaryTree<Double> tree4 = BinaryTree.parse("54.5(39.2,20.1(50.5,40.2(51.0,78.9)))",x->Double.parseDouble(x));
          BinaryPattern<Double> result = BinaryPattern.parse("_e4(_e3(_A,_B),_e5(_C,_D))");
		
		
		
//      System.out.println(t);
//		System.out.println(p);
//		System.out.println(r);
		
		
//		System.out.println(result);
//		System.out.println(tree1);
//		System.out.println(leftRight);
//		System.out.println("Aqui = "+tree1.transform(pt.leftRight, pt.result));
//		System.out.println(tree2);
//		System.out.println(rightLeft);
//		System.out.println("Aqui = "+tree2.transform(pt.rightLeft, pt.result));
//		System.out.println(tree3);
//		System.out.println(leftLeft);
		System.out.println("Aqui = "+tree3.transform(leftLeft, result));
//		System.out.println(tree4);
//		System.out.println(rightRight);
//		System.out.println("Aqui = "+tree4.transform(pt.rightRight, pt.result));
//		System.out.println(t.match(p));
//		System.out.println(p.labelValues()+"----"+p.treeValues());
//		System.out.println(map(t,p,r));
//		BinaryPattern<Integer> p0 = BinaryPattern.free("_t0");
//		BinaryPattern<Integer> pt = BinaryPattern.binaryFree("_e0",BinaryPattern.leaf(2),p0);
//		System.out.println(pt);
//		System.out.println(pt.values());
//		System.out.println(match(t6,pt));
//		System.out.println(pt.values());
	}
}
