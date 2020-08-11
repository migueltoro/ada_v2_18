package us.lsi.tiposrecursivos.parsers;

import us.lsi.tiposrecursivos.BinaryTree;

public class BinaryTreeVisitorC extends BinaryTreeBaseVisitor<Object> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Object visitEmptyTree(BinaryTreeParser.EmptyTreeContext ctx) { 
		return BinaryTree.empty();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Object visitLabelTree(BinaryTreeParser.LabelTreeContext ctx) { 
		Object label = visit(ctx.label());
		return BinaryTree.leaf(label);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@SuppressWarnings("unchecked")
	@Override public Object visitBinaryTree(BinaryTreeParser.BinaryTreeContext ctx) { 
		String label = (String) visit(ctx.label());
		BinaryTree<String> left = (BinaryTree<String>) visit(ctx.left);
		BinaryTree<String> right = (BinaryTree<String>) visit(ctx.right);
		BinaryTree<String> tree = BinaryTree.binary(label, left, right);
		return tree;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Object visitIntLabel(BinaryTreeParser.IntLabelContext ctx) { 
		return ctx.getText();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Object visitDoubleLabel(BinaryTreeParser.DoubleLabelContext ctx) { 
		return ctx.getText();
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Object visitIdLabel(BinaryTreeParser.IdLabelContext ctx) { 
		return ctx.getText();
	}
		
}
