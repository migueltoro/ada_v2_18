package us.lsi.tiposrecursivos.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tiposrecursivos.program.Assign;
import us.lsi.tiposrecursivos.program.Binary;
import us.lsi.tiposrecursivos.program.Block;
import us.lsi.tiposrecursivos.program.CallFunction;
import us.lsi.tiposrecursivos.program.Declaration;
import us.lsi.tiposrecursivos.program.Exp;
import us.lsi.tiposrecursivos.program.FunDeclaration;
import us.lsi.tiposrecursivos.program.Id;
import us.lsi.tiposrecursivos.program.IdType;
import us.lsi.tiposrecursivos.program.IfThenElse;
import us.lsi.tiposrecursivos.program.Int;
import us.lsi.tiposrecursivos.program.Program;
import us.lsi.tiposrecursivos.program.Real;
import us.lsi.tiposrecursivos.program.Sentence;
import us.lsi.tiposrecursivos.program.Type;
import us.lsi.tiposrecursivos.program.Unary;
import us.lsi.tiposrecursivos.program.VarDeclaration;
import us.lsi.tiposrecursivos.program.While;

public class ProgramVisitorC extends ProgramBaseVisitor<Object> {

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Program visitProgram(ProgramParser.ProgramContext ctx) { 
		Integer n = ctx.declaration().size();
		List<Declaration> declarations = IntStream.range(0,n).boxed()
				.map(i->(Declaration)visit(ctx.declaration(i)))
				.collect(Collectors.toList());
		Block block = (Block) visit(ctx.block());
		return Program.of(declarations, block);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override
	public Declaration visitFunDeclarationSP(ProgramParser.FunDeclarationSPContext ctx) { 
		String id = ctx.id.getText();
		Type type = Type.valueOf(ctx.type.getText());
		List<IdType> parameters = new ArrayList<>();
		return FunDeclaration.of(id, type,parameters);
		
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Declaration visitFunDeclaration(ProgramParser.FunDeclarationContext ctx) { 
		String id = ctx.id.getText();
		Type type = Type.valueOf(ctx.type.getText());
		List<IdType> parameters = new ArrayList<>();
		if(ctx.formal_parameters() != null) parameters = (List<IdType>) visit(ctx.formal_parameters());
		return FunDeclaration.of(id, type,parameters);
		
	}
	
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Declaration visitVarDeclaration(ProgramParser.VarDeclarationContext ctx) { 
		String id = ctx.id.getText();
		Type type = Type.valueOf(ctx.type.getText());
		Exp value = null;
		if(ctx.exp() != null) value = (Exp) visit(ctx.exp());
		return VarDeclaration.of(id, type, value);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public List<IdType> visitFormal_parameters(ProgramParser.Formal_parametersContext ctx) { 
		Integer n = ctx.formal_parameter().size();
		List<IdType> r = IntStream.range(0, n).boxed()
				.map(i->(IdType)visit(ctx.formal_parameter(i)))
				.collect(Collectors.toList());
		return r;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public IdType visitFormal_parameter(ProgramParser.Formal_parameterContext ctx) { 
		String id = ctx.id.getText();
		Type type = Type.valueOf(ctx.type.getText());
		return IdType.of(id, type);
    }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	
	@Override 
	public Sentence visitAsignSentence(ProgramParser.AsignSentenceContext ctx) { 
		Exp id = Id.of(ctx.id.getText());
		Exp exp = (Exp) visit(ctx.exp());
		return Assign.of(id,exp); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Sentence visitIfSentence(ProgramParser.IfSentenceContext ctx) { 
		Exp guard = (Exp) visit(ctx.exp());
		Block trueBlock = (Block) visit(ctx.trueBlock);
		Block elseBlock = null;
		if(ctx.elseBlock != null) elseBlock = (Block) visit(ctx.elseBlock);
		return IfThenElse.of(guard,trueBlock,elseBlock);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Sentence visitWhileSentence(ProgramParser.WhileSentenceContext ctx) { 
		Exp guard = (Exp) visit(ctx.exp());
		Block block = (Block) visit(ctx.block());
		return While.of(guard,block); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Block visitBlock(ProgramParser.BlockContext ctx) { 
		Integer n = ctx.sentence().size();
		List<Sentence> sentences = IntStream.range(0,n).boxed()
				.map(i->(Sentence)visit(ctx.sentence(i)))
				.collect(Collectors.toList());
		return Block.of(sentences);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Exp visitUnaryExpr(ProgramParser.UnaryExprContext ctx) { 
		String op = ctx.op.getText();
		Exp operand = (Exp) visit(ctx.right);
		return Unary.of(op, operand); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Exp visitIntExpr(ProgramParser.IntExprContext ctx) { 
		return Int.of(ctx.getText()); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Exp visitBinaryExpr(ProgramParser.BinaryExprContext ctx) { 
		String op = ctx.op.getText();
		Exp left = (Exp) visit(ctx.left);
		Exp right = (Exp) visit(ctx.right);
		return Binary.of(op, left, right);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@SuppressWarnings("unchecked")
	@Override public Exp visitCallExpr(ProgramParser.CallExprContext ctx) { 
		String id = ctx.ID().getText();
		List<Exp> parameters = new ArrayList<>();
		if(ctx.real_parameters() != null) parameters = (List<Exp>) visit(ctx.real_parameters());
		return CallFunction.of(id, parameters);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Exp visitParenExpr(ProgramParser.ParenExprContext ctx) { 
		return (Exp) visit(ctx.exp());
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Exp visitDoubleExp(ProgramParser.DoubleExpContext ctx) { 
		return Real.of(ctx.getText()); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Exp visitIdExpr(ProgramParser.IdExprContext ctx) { 
		return Id.of(ctx.getText()); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public List<Exp> visitReal_parameters(ProgramParser.Real_parametersContext ctx) { 
		Integer n = ctx.exp().size();
		List<Exp> parameters = IntStream.range(0,n).boxed()
				.map(i->(Exp)visit(ctx.exp(i)))
				.collect(Collectors.toList());
		return parameters;
	}
}
