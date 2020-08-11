package us.lsi.tiposrecursivos.parsers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tiposrecursivos.Tree;

public class TreeVisitorC extends TreeBaseVisitor<Object> {
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Object visitEmptyTree(TreeParser.EmptyTreeContext ctx) { 
		return Tree.empty();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Object visitLabelTree(TreeParser.LabelTreeContext ctx) { 
		Object label = visit(ctx.label());
		return Tree.leaf(label); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@SuppressWarnings("unchecked")
	@Override public Object visitNaryTree(TreeParser.NaryTreeContext ctx) { 
		String label = (String) visit(ctx.label());
		Integer n = ctx.nary_tree().size();
		List<Tree<String>> children = IntStream.range(0,n).boxed()
				.map(i-> (Tree<String>) visit(ctx.nary_tree(i)))
				.collect(Collectors.toList());				
		Tree<String> tree = Tree.nary(label, children);
		return tree;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Object visitIntLabel(TreeParser.IntLabelContext ctx) { 
		return ctx.getText(); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Object visitDoubleLabel(TreeParser.DoubleLabelContext ctx) { 
		return ctx.getText();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Object visitIdLabel(TreeParser.IdLabelContext ctx) { 
		return ctx.getText();
	}
}
