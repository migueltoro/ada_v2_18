// Generated from Tree.g4 by ANTLR 4.8
package us.lsi.tiposrecursivos.parsers;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TreeParser}.
 */
public interface TreeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code emptyTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void enterEmptyTree(TreeParser.EmptyTreeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void exitEmptyTree(TreeParser.EmptyTreeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code labelTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void enterLabelTree(TreeParser.LabelTreeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code labelTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void exitLabelTree(TreeParser.LabelTreeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code naryTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void enterNaryTree(TreeParser.NaryTreeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code naryTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void exitNaryTree(TreeParser.NaryTreeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void enterIntLabel(TreeParser.IntLabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void exitIntLabel(TreeParser.IntLabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void enterDoubleLabel(TreeParser.DoubleLabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void exitDoubleLabel(TreeParser.DoubleLabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void enterIdLabel(TreeParser.IdLabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void exitIdLabel(TreeParser.IdLabelContext ctx);
}