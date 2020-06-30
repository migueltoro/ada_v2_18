package us.lsi.model;

// Generated from PLIModel.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class PLIModelParser extends Parser {
	static {
		RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION);
	}

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
			T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17, T__17 = 18,
			T__18 = 19, T__19 = 20, T__20 = 21, T__21 = 22, T__22 = 23, T__23 = 24, T__24 = 25, T__25 = 26, T__26 = 27,
			T__27 = 28, T__28 = 29, T__29 = 30, T__30 = 31, T__31 = 32, T__32 = 33, T__33 = 34, T__34 = 35, ID = 36,
			INT = 37, DOUBLE = 38, WS = 39;
	public static final int RULE_model = 0, RULE_head = 1, RULE_declaration = 2, RULE_formal_parameters = 3,
			RULE_formal_parameter = 4, RULE_goal = 5, RULE_constraints = 6, RULE_list = 7, RULE_index = 8,
			RULE_indexed_elem = 9, RULE_constraint = 10, RULE_generate_exp = 11, RULE_s_factor = 12, RULE_factor = 13,
			RULE_var_id = 14, RULE_bound = 15, RULE_bounds = 16, RULE_bin_vars = 17, RULE_int_vars = 18,
			RULE_free_vars = 19, RULE_exp = 20, RULE_call_function = 21, RULE_real_parameters = 22, RULE_predicate = 23,
			RULE_rel_op = 24;

	private static String[] makeRuleNames() {
		return new String[] { "model", "head", "declaration", "formal_parameters", "formal_parameter", "goal",
				"constraints", "list", "index", "indexed_elem", "constraint", "generate_exp", "s_factor", "factor",
				"var_id", "bound", "bounds", "bin_vars", "int_vars", "free_vars", "exp", "call_function",
				"real_parameters", "predicate", "rel_op" };
	}

	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] { null, "'head section'", "'='", "'('", "')'", "','", "'goal section'", "'min'", "'max'",
				"'constraints section'", "'|'", "'in'", "'..'", "'->'", "'or'", "'=>'", "'!='", "'allDifferent'",
				"'sum'", "'+'", "'-'", "'['", "']'", "'<='", "'bounds section'", "'bin'", "'int'", "'free'", "'(int)'",
				"'(double)'", "'*'", "'/'", "'%'", "'>='", "'>'", "'<'" };
	}

	private static final String[] _LITERAL_NAMES = makeLiteralNames();

	private static String[] makeSymbolicNames() {
		return new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, "ID", "INT", "DOUBLE", "WS" };
	}

	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() {
		return "PLIModel.g4";
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String getSerializedATN() {
		return _serializedATN;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	public PLIModelParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	public static class ModelContext extends ParserRuleContext {
		public GoalContext goal() {
			return getRuleContext(GoalContext.class, 0);
		}

		public ConstraintsContext constraints() {
			return getRuleContext(ConstraintsContext.class, 0);
		}

		public BoundsContext bounds() {
			return getRuleContext(BoundsContext.class, 0);
		}

		public Bin_varsContext bin_vars() {
			return getRuleContext(Bin_varsContext.class, 0);
		}

		public Int_varsContext int_vars() {
			return getRuleContext(Int_varsContext.class, 0);
		}

		public Free_varsContext free_vars() {
			return getRuleContext(Free_varsContext.class, 0);
		}

		public HeadContext head() {
			return getRuleContext(HeadContext.class, 0);
		}

		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_model;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterModel(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitModel(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitModel(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == T__0) {
					{
						setState(50);
						head();
					}
				}

				setState(53);
				goal();
				setState(54);
				constraints();
				setState(55);
				bounds();
				setState(56);
				bin_vars();
				setState(57);
				int_vars();
				setState(58);
				free_vars();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HeadContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}

		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class, i);
		}

		public HeadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_head;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterHead(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitHead(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitHead(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final HeadContext head() throws RecognitionException {
		HeadContext _localctx = new HeadContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_head);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(60);
				match(T__0);
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == ID) {
					{
						{
							setState(61);
							declaration();
						}
					}
					setState(66);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_declaration;
		}

		public DeclarationContext() {
		}

		public void copyFrom(DeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class FunDeclarContext extends DeclarationContext {
		public Token type;
		public Token name;

		public List<TerminalNode> ID() {
			return getTokens(PLIModelParser.ID);
		}

		public TerminalNode ID(int i) {
			return getToken(PLIModelParser.ID, i);
		}

		public Formal_parametersContext formal_parameters() {
			return getRuleContext(Formal_parametersContext.class, 0);
		}

		public FunDeclarContext(DeclarationContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterFunDeclar(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitFunDeclar(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitFunDeclar(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class VarDeclarContext extends DeclarationContext {
		public Token type;
		public Token name;
		public ExpContext val;

		public List<TerminalNode> ID() {
			return getTokens(PLIModelParser.ID);
		}

		public TerminalNode ID(int i) {
			return getToken(PLIModelParser.ID, i);
		}

		public ExpContext exp() {
			return getRuleContext(ExpContext.class, 0);
		}

		public VarDeclarContext(DeclarationContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterVarDeclar(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitVarDeclar(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitVarDeclar(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaration);
		int _la;
		try {
			setState(78);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
			case 1:
				_localctx = new VarDeclarContext(_localctx);
				enterOuterAlt(_localctx, 1); {
				setState(67);
				((VarDeclarContext) _localctx).type = match(ID);
				setState(68);
				((VarDeclarContext) _localctx).name = match(ID);
				setState(69);
				match(T__1);
				setState(70);
				((VarDeclarContext) _localctx).val = exp(0);
			}
				break;
			case 2:
				_localctx = new FunDeclarContext(_localctx);
				enterOuterAlt(_localctx, 2); {
				setState(71);
				((FunDeclarContext) _localctx).type = match(ID);
				setState(72);
				((FunDeclarContext) _localctx).name = match(ID);
				setState(73);
				match(T__2);
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == ID) {
					{
						setState(74);
						formal_parameters();
					}
				}

				setState(77);
				match(T__3);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Formal_parametersContext extends ParserRuleContext {
		public List<Formal_parameterContext> formal_parameter() {
			return getRuleContexts(Formal_parameterContext.class);
		}

		public Formal_parameterContext formal_parameter(int i) {
			return getRuleContext(Formal_parameterContext.class, i);
		}

		public Formal_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_formal_parameters;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterFormal_parameters(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitFormal_parameters(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitFormal_parameters(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Formal_parametersContext formal_parameters() throws RecognitionException {
		Formal_parametersContext _localctx = new Formal_parametersContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_formal_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(80);
				formal_parameter();
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__4) {
					{
						{
							setState(81);
							match(T__4);
							setState(82);
							formal_parameter();
						}
					}
					setState(87);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Formal_parameterContext extends ParserRuleContext {
		public Token type;
		public Token name;

		public List<TerminalNode> ID() {
			return getTokens(PLIModelParser.ID);
		}

		public TerminalNode ID(int i) {
			return getToken(PLIModelParser.ID, i);
		}

		public Formal_parameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_formal_parameter;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterFormal_parameter(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitFormal_parameter(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitFormal_parameter(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Formal_parameterContext formal_parameter() throws RecognitionException {
		Formal_parameterContext _localctx = new Formal_parameterContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_formal_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(88);
				((Formal_parameterContext) _localctx).type = match(ID);
				setState(90);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == ID) {
					{
						setState(89);
						((Formal_parameterContext) _localctx).name = match(ID);
					}
				}

			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GoalContext extends ParserRuleContext {
		public GoalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_goal;
		}

		public GoalContext() {
		}

		public void copyFrom(GoalContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class GoalSectionContext extends GoalContext {
		public Token obj;

		public Generate_expContext generate_exp() {
			return getRuleContext(Generate_expContext.class, 0);
		}

		public GoalSectionContext(GoalContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterGoalSection(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitGoalSection(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitGoalSection(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final GoalContext goal() throws RecognitionException {
		GoalContext _localctx = new GoalContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_goal);
		int _la;
		try {
			_localctx = new GoalSectionContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
				setState(92);
				match(T__5);
				setState(93);
				((GoalSectionContext) _localctx).obj = _input.LT(1);
				_la = _input.LA(1);
				if (!(_la == T__6 || _la == T__7)) {
					((GoalSectionContext) _localctx).obj = (Token) _errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(94);
				generate_exp();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}

		public ListContext list(int i) {
			return getRuleContext(ListContext.class, i);
		}

		public ConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_constraints;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterConstraints(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitConstraints(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitConstraints(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final ConstraintsContext constraints() throws RecognitionException {
		ConstraintsContext _localctx = new ConstraintsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_constraints);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(96);
				match(T__8);
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(97);
							list();
						}
					}
					setState(100);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ((((_la) & ~0x3f) == 0 && ((1L << _la)
						& ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19)
								| (1L << T__27) | (1L << T__28) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0));
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListContext extends ParserRuleContext {
		public Indexed_elemContext indexed_elem() {
			return getRuleContext(Indexed_elemContext.class, 0);
		}

		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}

		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class, i);
		}

		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class, 0);
		}

		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_list;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterList(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitList(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitList(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(102);
				indexed_elem();
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__4) {
					{
						{
							setState(103);
							match(T__4);
							setState(104);
							index();
						}
					}
					setState(109);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(112);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == T__9) {
					{
						setState(110);
						match(T__9);
						setState(111);
						predicate();
					}
				}

			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexContext extends ParserRuleContext {
		public Token index_name;
		public ExpContext li;
		public ExpContext ls;

		public TerminalNode ID() {
			return getToken(PLIModelParser.ID, 0);
		}

		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}

		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class, i);
		}

		public IndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_index;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterIndex(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitIndex(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitIndex(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final IndexContext index() throws RecognitionException {
		IndexContext _localctx = new IndexContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(114);
				((IndexContext) _localctx).index_name = match(ID);
				setState(115);
				match(T__10);
				setState(116);
				((IndexContext) _localctx).li = exp(0);
				setState(117);
				match(T__11);
				setState(118);
				((IndexContext) _localctx).ls = exp(0);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Indexed_elemContext extends ParserRuleContext {
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class, 0);
		}

		public BoundContext bound() {
			return getRuleContext(BoundContext.class, 0);
		}

		public Generate_expContext generate_exp() {
			return getRuleContext(Generate_expContext.class, 0);
		}

		public Indexed_elemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_indexed_elem;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterIndexed_elem(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitIndexed_elem(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitIndexed_elem(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Indexed_elemContext indexed_elem() throws RecognitionException {
		Indexed_elemContext _localctx = new Indexed_elemContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_indexed_elem);
		try {
			setState(123);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(120);
				constraint(0);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(121);
				bound();
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(122);
				generate_exp();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintContext extends ParserRuleContext {
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_constraint;
		}

		public ConstraintContext() {
		}

		public void copyFrom(ConstraintContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class DifferentValueConstraintContext extends ConstraintContext {
		public Var_idContext left;
		public Var_idContext right;

		public List<Var_idContext> var_id() {
			return getRuleContexts(Var_idContext.class);
		}

		public Var_idContext var_id(int i) {
			return getRuleContext(Var_idContext.class, i);
		}

		public DifferentValueConstraintContext(ConstraintContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterDifferentValueConstraint(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitDifferentValueConstraint(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitDifferentValueConstraint(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class AllDifferentValuesConstraintContext extends ConstraintContext {
		public ListContext list() {
			return getRuleContext(ListContext.class, 0);
		}

		public AllDifferentValuesConstraintContext(ConstraintContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterAllDifferentValuesConstraint(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitAllDifferentValuesConstraint(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitAllDifferentValuesConstraint(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class IndicatorConstraintContext extends ConstraintContext {
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class, 0);
		}

		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class, 0);
		}

		public IndicatorConstraintContext(ConstraintContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterIndicatorConstraint(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitIndicatorConstraint(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitIndicatorConstraint(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class ImplyConstraintContext extends ConstraintContext {
		public ConstraintContext left;
		public ConstraintContext right;

		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}

		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class, i);
		}

		public ImplyConstraintContext(ConstraintContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterImplyConstraint(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitImplyConstraint(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitImplyConstraint(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class AtomConstraintContext extends ConstraintContext {
		public Generate_expContext generate_exp() {
			return getRuleContext(Generate_expContext.class, 0);
		}

		public Rel_opContext rel_op() {
			return getRuleContext(Rel_opContext.class, 0);
		}

		public ExpContext exp() {
			return getRuleContext(ExpContext.class, 0);
		}

		public AtomConstraintContext(ConstraintContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterAtomConstraint(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitAtomConstraint(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitAtomConstraint(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class OrConstraintContext extends ConstraintContext {
		public Token n;

		public Rel_opContext rel_op() {
			return getRuleContext(Rel_opContext.class, 0);
		}

		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}

		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class, i);
		}

		public TerminalNode INT() {
			return getToken(PLIModelParser.INT, 0);
		}

		public OrConstraintContext(ConstraintContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterOrConstraint(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitOrConstraint(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitOrConstraint(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		return constraint(0);
	}

	private ConstraintContext constraint(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConstraintContext _localctx = new ConstraintContext(_ctx, _parentState);
		ConstraintContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_constraint, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(157);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
				case 1: {
					_localctx = new AtomConstraintContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;

					setState(126);
					generate_exp();
					setState(127);
					rel_op();
					setState(128);
					exp(0);
				}
					break;
				case 2: {
					_localctx = new IndicatorConstraintContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(130);
					var_id();
					setState(131);
					match(T__12);
					setState(132);
					constraint(5);
				}
					break;
				case 3: {
					_localctx = new OrConstraintContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(134);
					match(T__13);
					setState(135);
					match(T__2);
					setState(136);
					rel_op();
					setState(137);
					((OrConstraintContext) _localctx).n = match(INT);
					setState(138);
					match(T__4);
					setState(139);
					constraint(0);
					setState(142);
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
							{
								setState(140);
								match(T__9);
								setState(141);
								constraint(0);
							}
						}
						setState(144);
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while (_la == T__9);
					setState(146);
					match(T__3);
				}
					break;
				case 4: {
					_localctx = new DifferentValueConstraintContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(148);
					((DifferentValueConstraintContext) _localctx).left = var_id();
					setState(149);
					match(T__15);
					setState(150);
					((DifferentValueConstraintContext) _localctx).right = var_id();
				}
					break;
				case 5: {
					_localctx = new AllDifferentValuesConstraintContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(152);
					match(T__16);
					setState(153);
					match(T__2);
					setState(154);
					list();
					setState(155);
					match(T__3);
				}
					break;
				}
				_ctx.stop = _input.LT(-1);
				setState(164);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 12, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						if (_parseListeners != null)
							triggerExitRuleEvent();
						_prevctx = _localctx;
						{
							{
								_localctx = new ImplyConstraintContext(new ConstraintContext(_parentctx, _parentState));
								((ImplyConstraintContext) _localctx).left = _prevctx;
								pushNewRecursionContext(_localctx, _startState, RULE_constraint);
								setState(159);
								if (!(precpred(_ctx, 3)))
									throw new FailedPredicateException(this, "precpred(_ctx, 3)");
								setState(160);
								match(T__14);
								setState(161);
								((ImplyConstraintContext) _localctx).right = constraint(4);
							}
						}
					}
					setState(166);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 12, _ctx);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Generate_expContext extends ParserRuleContext {
		public Generate_expContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_generate_exp;
		}

		public Generate_expContext() {
		}

		public void copyFrom(Generate_expContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class FactorGenerateExpContext extends Generate_expContext {
		public FactorContext factor() {
			return getRuleContext(FactorContext.class, 0);
		}

		public List<S_factorContext> s_factor() {
			return getRuleContexts(S_factorContext.class);
		}

		public S_factorContext s_factor(int i) {
			return getRuleContext(S_factorContext.class, i);
		}

		public FactorGenerateExpContext(Generate_expContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterFactorGenerateExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitFactorGenerateExp(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitFactorGenerateExp(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class SumGenerateExpContext extends Generate_expContext {
		public ListContext list() {
			return getRuleContext(ListContext.class, 0);
		}

		public List<S_factorContext> s_factor() {
			return getRuleContexts(S_factorContext.class);
		}

		public S_factorContext s_factor(int i) {
			return getRuleContext(S_factorContext.class, i);
		}

		public SumGenerateExpContext(Generate_expContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterSumGenerateExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitSumGenerateExp(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitSumGenerateExp(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Generate_expContext generate_exp() throws RecognitionException {
		Generate_expContext _localctx = new Generate_expContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_generate_exp);
		try {
			int _alt;
			setState(184);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case T__18:
			case T__19:
			case T__27:
			case T__28:
			case ID:
			case INT:
			case DOUBLE:
				_localctx = new FactorGenerateExpContext(_localctx);
				enterOuterAlt(_localctx, 1); {
				setState(167);
				factor();
				setState(171);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 13, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						{
							{
								setState(168);
								s_factor();
							}
						}
					}
					setState(173);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 13, _ctx);
				}
			}
				break;
			case T__17:
				_localctx = new SumGenerateExpContext(_localctx);
				enterOuterAlt(_localctx, 2); {
				setState(174);
				match(T__17);
				setState(175);
				match(T__2);
				setState(176);
				list();
				setState(177);
				match(T__3);
				setState(181);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						{
							{
								setState(178);
								s_factor();
							}
						}
					}
					setState(183);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
				}
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class S_factorContext extends ParserRuleContext {
		public S_factorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_s_factor;
		}

		public S_factorContext() {
		}

		public void copyFrom(S_factorContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class PlusFactorContext extends S_factorContext {
		public FactorContext factor() {
			return getRuleContext(FactorContext.class, 0);
		}

		public PlusFactorContext(S_factorContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterPlusFactor(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitPlusFactor(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitPlusFactor(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class MinusFactorContext extends S_factorContext {
		public FactorContext factor() {
			return getRuleContext(FactorContext.class, 0);
		}

		public MinusFactorContext(S_factorContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterMinusFactor(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitMinusFactor(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitMinusFactor(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class PlusSumContext extends S_factorContext {
		public ListContext list() {
			return getRuleContext(ListContext.class, 0);
		}

		public PlusSumContext(S_factorContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterPlusSum(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitPlusSum(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitPlusSum(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final S_factorContext s_factor() throws RecognitionException {
		S_factorContext _localctx = new S_factorContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_s_factor);
		try {
			setState(196);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
			case 1:
				_localctx = new PlusFactorContext(_localctx);
				enterOuterAlt(_localctx, 1); {
				setState(186);
				match(T__18);
				setState(187);
				factor();
			}
				break;
			case 2:
				_localctx = new PlusSumContext(_localctx);
				enterOuterAlt(_localctx, 2); {
				setState(188);
				match(T__18);
				setState(189);
				match(T__17);
				setState(190);
				match(T__2);
				setState(191);
				list();
				setState(192);
				match(T__3);
			}
				break;
			case 3:
				_localctx = new MinusFactorContext(_localctx);
				enterOuterAlt(_localctx, 3); {
				setState(194);
				match(T__19);
				setState(195);
				factor();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FactorContext extends ParserRuleContext {
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_factor;
		}

		public FactorContext() {
		}

		public void copyFrom(FactorContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class NumFactorContext extends FactorContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class, 0);
		}

		public NumFactorContext(FactorContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterNumFactor(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitNumFactor(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitNumFactor(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class VarFactorContext extends FactorContext {
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class, 0);
		}

		public ExpContext exp() {
			return getRuleContext(ExpContext.class, 0);
		}

		public VarFactorContext(FactorContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterVarFactor(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitVarFactor(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitVarFactor(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_factor);
		try {
			setState(203);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 18, _ctx)) {
			case 1:
				_localctx = new VarFactorContext(_localctx);
				enterOuterAlt(_localctx, 1); {
				setState(199);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
				case 1: {
					setState(198);
					exp(0);
				}
					break;
				}
				setState(201);
				var_id();
			}
				break;
			case 2:
				_localctx = new NumFactorContext(_localctx);
				enterOuterAlt(_localctx, 2); {
				setState(202);
				exp(0);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_idContext extends ParserRuleContext {
		public Token name;

		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}

		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class, i);
		}

		public TerminalNode ID() {
			return getToken(PLIModelParser.ID, 0);
		}

		public Var_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_var_id;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterVar_id(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitVar_id(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitVar_id(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Var_idContext var_id() throws RecognitionException {
		Var_idContext _localctx = new Var_idContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_var_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(205);
				((Var_idContext) _localctx).name = match(ID);
				setState(206);
				match(T__20);
				setState(207);
				exp(0);
				setState(212);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__4) {
					{
						{
							setState(208);
							match(T__4);
							setState(209);
							exp(0);
						}
					}
					setState(214);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(215);
				match(T__21);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoundContext extends ParserRuleContext {
		public BoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_bound;
		}

		public BoundContext() {
		}

		public void copyFrom(BoundContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class TwoSideBoundContext extends BoundContext {
		public ExpContext li;
		public Var_idContext name;
		public ExpContext ls;

		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}

		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class, i);
		}

		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class, 0);
		}

		public TwoSideBoundContext(BoundContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterTwoSideBound(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitTwoSideBound(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitTwoSideBound(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class OneSideBoundContext extends BoundContext {
		public Var_idContext name;
		public Rel_opContext op;

		public ExpContext exp() {
			return getRuleContext(ExpContext.class, 0);
		}

		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class, 0);
		}

		public Rel_opContext rel_op() {
			return getRuleContext(Rel_opContext.class, 0);
		}

		public OneSideBoundContext(BoundContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterOneSideBound(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitOneSideBound(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitOneSideBound(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final BoundContext bound() throws RecognitionException {
		BoundContext _localctx = new BoundContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_bound);
		try {
			setState(227);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
			case 1:
				_localctx = new OneSideBoundContext(_localctx);
				enterOuterAlt(_localctx, 1); {
				setState(217);
				((OneSideBoundContext) _localctx).name = var_id();
				setState(218);
				((OneSideBoundContext) _localctx).op = rel_op();
				setState(219);
				exp(0);
			}
				break;
			case 2:
				_localctx = new TwoSideBoundContext(_localctx);
				enterOuterAlt(_localctx, 2); {
				setState(221);
				((TwoSideBoundContext) _localctx).li = exp(0);
				setState(222);
				match(T__22);
				setState(223);
				((TwoSideBoundContext) _localctx).name = var_id();
				setState(224);
				match(T__22);
				setState(225);
				((TwoSideBoundContext) _localctx).ls = exp(0);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoundsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}

		public ListContext list(int i) {
			return getRuleContext(ListContext.class, i);
		}

		public BoundsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_bounds;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterBounds(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitBounds(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitBounds(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final BoundsContext bounds() throws RecognitionException {
		BoundsContext _localctx = new BoundsContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_bounds);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(229);
				match(T__23);
				setState(231);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(230);
							list();
						}
					}
					setState(233);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ((((_la) & ~0x3f) == 0 && ((1L << _la)
						& ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19)
								| (1L << T__27) | (1L << T__28) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0));
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Bin_varsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}

		public ListContext list(int i) {
			return getRuleContext(ListContext.class, i);
		}

		public Bin_varsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_bin_vars;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterBin_vars(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitBin_vars(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitBin_vars(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Bin_varsContext bin_vars() throws RecognitionException {
		Bin_varsContext _localctx = new Bin_varsContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_bin_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(235);
				match(T__24);
				setState(239);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la)
						& ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19)
								| (1L << T__27) | (1L << T__28) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0)) {
					{
						{
							setState(236);
							list();
						}
					}
					setState(241);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Int_varsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}

		public ListContext list(int i) {
			return getRuleContext(ListContext.class, i);
		}

		public Int_varsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_int_vars;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterInt_vars(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitInt_vars(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitInt_vars(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Int_varsContext int_vars() throws RecognitionException {
		Int_varsContext _localctx = new Int_varsContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_int_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(242);
				match(T__25);
				setState(246);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la)
						& ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19)
								| (1L << T__27) | (1L << T__28) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0)) {
					{
						{
							setState(243);
							list();
						}
					}
					setState(248);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Free_varsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}

		public ListContext list(int i) {
			return getRuleContext(ListContext.class, i);
		}

		public Free_varsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_free_vars;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterFree_vars(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitFree_vars(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitFree_vars(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Free_varsContext free_vars() throws RecognitionException {
		Free_varsContext _localctx = new Free_varsContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_free_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(249);
				match(T__26);
				setState(253);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la)
						& ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19)
								| (1L << T__27) | (1L << T__28) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0)) {
					{
						{
							setState(250);
							list();
						}
					}
					setState(255);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_exp;
		}

		public ExpContext() {
		}

		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class UnaryOpExprContext extends ExpContext {
		public Token op;
		public ExpContext right;

		public ExpContext exp() {
			return getRuleContext(ExpContext.class, 0);
		}

		public UnaryOpExprContext(ExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterUnaryOpExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitUnaryOpExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitUnaryOpExpr(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class IntExprContext extends ExpContext {
		public TerminalNode INT() {
			return getToken(PLIModelParser.INT, 0);
		}

		public IntExprContext(ExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterIntExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitIntExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitIntExpr(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class OpExprContext extends ExpContext {
		public ExpContext left;
		public Token op;
		public ExpContext right;

		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}

		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class, i);
		}

		public OpExprContext(ExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterOpExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitOpExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitOpExpr(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class FunExprContext extends ExpContext {
		public Call_functionContext call_function() {
			return getRuleContext(Call_functionContext.class, 0);
		}

		public FunExprContext(ExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterFunExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitFunExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitFunExpr(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class ParenExprContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class, 0);
		}

		public ParenExprContext(ExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterParenExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitParenExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitParenExpr(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class DoubleExpContext extends ExpContext {
		public TerminalNode DOUBLE() {
			return getToken(PLIModelParser.DOUBLE, 0);
		}

		public DoubleExpContext(ExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterDoubleExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitDoubleExp(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitDoubleExp(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public static class IdExprContext extends ExpContext {
		public Token id;

		public TerminalNode ID() {
			return getToken(PLIModelParser.ID, 0);
		}

		public IdExprContext(ExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterIdExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitIdExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitIdExpr(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(273);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 25, _ctx)) {
				case 1: {
					_localctx = new UnaryOpExprContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;

					setState(257);
					((UnaryOpExprContext) _localctx).op = match(T__27);
					setState(258);
					((UnaryOpExprContext) _localctx).right = exp(11);
				}
					break;
				case 2: {
					_localctx = new UnaryOpExprContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(259);
					((UnaryOpExprContext) _localctx).op = match(T__28);
					setState(260);
					((UnaryOpExprContext) _localctx).right = exp(10);
				}
					break;
				case 3: {
					_localctx = new UnaryOpExprContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(261);
					((UnaryOpExprContext) _localctx).op = match(T__18);
					setState(262);
					((UnaryOpExprContext) _localctx).right = exp(9);
				}
					break;
				case 4: {
					_localctx = new UnaryOpExprContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(263);
					((UnaryOpExprContext) _localctx).op = match(T__19);
					setState(264);
					((UnaryOpExprContext) _localctx).right = exp(8);
				}
					break;
				case 5: {
					_localctx = new ParenExprContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(265);
					match(T__2);
					setState(266);
					exp(0);
					setState(267);
					match(T__3);
				}
					break;
				case 6: {
					_localctx = new FunExprContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(269);
					call_function();
				}
					break;
				case 7: {
					_localctx = new IdExprContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(270);
					((IdExprContext) _localctx).id = match(ID);
				}
					break;
				case 8: {
					_localctx = new DoubleExpContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(271);
					match(DOUBLE);
				}
					break;
				case 9: {
					_localctx = new IntExprContext(_localctx);
					_ctx = _localctx;
					_prevctx = _localctx;
					setState(272);
					match(INT);
				}
					break;
				}
				_ctx.stop = _input.LT(-1);
				setState(283);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						if (_parseListeners != null)
							triggerExitRuleEvent();
						_prevctx = _localctx;
						{
							setState(281);
							_errHandler.sync(this);
							switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
							case 1: {
								_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
								((OpExprContext) _localctx).left = _prevctx;
								pushNewRecursionContext(_localctx, _startState, RULE_exp);
								setState(275);
								if (!(precpred(_ctx, 7)))
									throw new FailedPredicateException(this, "precpred(_ctx, 7)");
								setState(276);
								((OpExprContext) _localctx).op = _input.LT(1);
								_la = _input.LA(1);
								if (!((((_la) & ~0x3f) == 0
										&& ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31))) != 0))) {
									((OpExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
								} else {
									if (_input.LA(1) == Token.EOF)
										matchedEOF = true;
									_errHandler.reportMatch(this);
									consume();
								}
								setState(277);
								((OpExprContext) _localctx).right = exp(8);
							}
								break;
							case 2: {
								_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
								((OpExprContext) _localctx).left = _prevctx;
								pushNewRecursionContext(_localctx, _startState, RULE_exp);
								setState(278);
								if (!(precpred(_ctx, 6)))
									throw new FailedPredicateException(this, "precpred(_ctx, 6)");
								setState(279);
								((OpExprContext) _localctx).op = _input.LT(1);
								_la = _input.LA(1);
								if (!(_la == T__18 || _la == T__19)) {
									((OpExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
								} else {
									if (_input.LA(1) == Token.EOF)
										matchedEOF = true;
									_errHandler.reportMatch(this);
									consume();
								}
								setState(280);
								((OpExprContext) _localctx).right = exp(7);
							}
								break;
							}
						}
					}
					setState(285);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Call_functionContext extends ParserRuleContext {
		public Token name;

		public TerminalNode ID() {
			return getToken(PLIModelParser.ID, 0);
		}

		public Real_parametersContext real_parameters() {
			return getRuleContext(Real_parametersContext.class, 0);
		}

		public Call_functionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_call_function;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterCall_function(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitCall_function(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitCall_function(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Call_functionContext call_function() throws RecognitionException {
		Call_functionContext _localctx = new Call_functionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_call_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(286);
				((Call_functionContext) _localctx).name = match(ID);
				setState(287);
				match(T__2);
				setState(289);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__18) | (1L << T__19)
						| (1L << T__27) | (1L << T__28) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0)) {
					{
						setState(288);
						real_parameters();
					}
				}

				setState(291);
				match(T__3);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Real_parametersContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}

		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class, i);
		}

		public Real_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_real_parameters;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterReal_parameters(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitReal_parameters(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitReal_parameters(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Real_parametersContext real_parameters() throws RecognitionException {
		Real_parametersContext _localctx = new Real_parametersContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_real_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(293);
				exp(0);
				setState(298);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__4) {
					{
						{
							setState(294);
							match(T__4);
							setState(295);
							exp(0);
						}
					}
					setState(300);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateContext extends ParserRuleContext {
		public ExpContext left;
		public Rel_opContext op;
		public ExpContext right;

		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}

		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class, i);
		}

		public Rel_opContext rel_op() {
			return getRuleContext(Rel_opContext.class, 0);
		}

		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_predicate;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterPredicate(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitPredicate(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitPredicate(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_predicate);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(301);
				((PredicateContext) _localctx).left = exp(0);
				setState(302);
				((PredicateContext) _localctx).op = rel_op();
				setState(303);
				((PredicateContext) _localctx).right = exp(0);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rel_opContext extends ParserRuleContext {
		public Rel_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_rel_op;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).enterRel_op(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PLIModelListener)
				((PLIModelListener) listener).exitRel_op(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof PLIModelVisitor)
				return ((PLIModelVisitor<? extends T>) visitor).visitRel_op(this);
			else
				return visitor.visitChildren(this);
		}
	}

	public final Rel_opContext rel_op() throws RecognitionException {
		Rel_opContext _localctx = new Rel_opContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_rel_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(305);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la)
						& ((1L << T__1) | (1L << T__22) | (1L << T__32) | (1L << T__33) | (1L << T__34))) != 0))) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return constraint_sempred((ConstraintContext) _localctx, predIndex);
		case 20:
			return exp_sempred((ExpContext) _localctx, predIndex);
		}
		return true;
	}

	private boolean constraint_sempred(ConstraintContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		}
		return true;
	}

	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		}
		return true;
	}

	public static final String _serializedATN = "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3)\u0136\4\2\t\2\4"
			+ "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
			+ "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
			+ "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
			+ "\4\32\t\32\3\2\5\2\66\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\7\3A\n\3"
			+ "\f\3\16\3D\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4N\n\4\3\4\5\4Q\n\4"
			+ "\3\5\3\5\3\5\7\5V\n\5\f\5\16\5Y\13\5\3\6\3\6\5\6]\n\6\3\7\3\7\3\7\3\7"
			+ "\3\b\3\b\6\be\n\b\r\b\16\bf\3\t\3\t\3\t\7\tl\n\t\f\t\16\to\13\t\3\t\3"
			+ "\t\5\ts\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\5\13~\n\13\3\f\3\f"
			+ "\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\6\f\u0091"
			+ "\n\f\r\f\16\f\u0092\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00a0"
			+ "\n\f\3\f\3\f\3\f\7\f\u00a5\n\f\f\f\16\f\u00a8\13\f\3\r\3\r\7\r\u00ac\n"
			+ "\r\f\r\16\r\u00af\13\r\3\r\3\r\3\r\3\r\3\r\7\r\u00b6\n\r\f\r\16\r\u00b9"
			+ "\13\r\5\r\u00bb\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"
			+ "\5\16\u00c7\n\16\3\17\5\17\u00ca\n\17\3\17\3\17\5\17\u00ce\n\17\3\20\3"
			+ "\20\3\20\3\20\3\20\7\20\u00d5\n\20\f\20\16\20\u00d8\13\20\3\20\3\20\3"
			+ "\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00e6\n\21\3\22"
			+ "\3\22\6\22\u00ea\n\22\r\22\16\22\u00eb\3\23\3\23\7\23\u00f0\n\23\f\23"
			+ "\16\23\u00f3\13\23\3\24\3\24\7\24\u00f7\n\24\f\24\16\24\u00fa\13\24\3"
			+ "\25\3\25\7\25\u00fe\n\25\f\25\16\25\u0101\13\25\3\26\3\26\3\26\3\26\3"
			+ "\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u0114"
			+ "\n\26\3\26\3\26\3\26\3\26\3\26\3\26\7\26\u011c\n\26\f\26\16\26\u011f\13"
			+ "\26\3\27\3\27\3\27\5\27\u0124\n\27\3\27\3\27\3\30\3\30\3\30\7\30\u012b"
			+ "\n\30\f\30\16\30\u012e\13\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\2\4\26"
			+ "*\33\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\2\6\3\2\t\n"
			+ "\3\2 \"\3\2\25\26\5\2\4\4\31\31#%\2\u0146\2\65\3\2\2\2\4>\3\2\2\2\6P\3"
			+ "\2\2\2\bR\3\2\2\2\nZ\3\2\2\2\f^\3\2\2\2\16b\3\2\2\2\20h\3\2\2\2\22t\3"
			+ "\2\2\2\24}\3\2\2\2\26\u009f\3\2\2\2\30\u00ba\3\2\2\2\32\u00c6\3\2\2\2"
			+ "\34\u00cd\3\2\2\2\36\u00cf\3\2\2\2 \u00e5\3\2\2\2\"\u00e7\3\2\2\2$\u00ed"
			+ "\3\2\2\2&\u00f4\3\2\2\2(\u00fb\3\2\2\2*\u0113\3\2\2\2,\u0120\3\2\2\2."
			+ "\u0127\3\2\2\2\60\u012f\3\2\2\2\62\u0133\3\2\2\2\64\66\5\4\3\2\65\64\3"
			+ "\2\2\2\65\66\3\2\2\2\66\67\3\2\2\2\678\5\f\7\289\5\16\b\29:\5\"\22\2:"
			+ ";\5$\23\2;<\5&\24\2<=\5(\25\2=\3\3\2\2\2>B\7\3\2\2?A\5\6\4\2@?\3\2\2\2"
			+ "AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2C\5\3\2\2\2DB\3\2\2\2EF\7&\2\2FG\7&\2\2"
			+ "GH\7\4\2\2HQ\5*\26\2IJ\7&\2\2JK\7&\2\2KM\7\5\2\2LN\5\b\5\2ML\3\2\2\2M"
			+ "N\3\2\2\2NO\3\2\2\2OQ\7\6\2\2PE\3\2\2\2PI\3\2\2\2Q\7\3\2\2\2RW\5\n\6\2"
			+ "ST\7\7\2\2TV\5\n\6\2US\3\2\2\2VY\3\2\2\2WU\3\2\2\2WX\3\2\2\2X\t\3\2\2"
			+ "\2YW\3\2\2\2Z\\\7&\2\2[]\7&\2\2\\[\3\2\2\2\\]\3\2\2\2]\13\3\2\2\2^_\7"
			+ "\b\2\2_`\t\2\2\2`a\5\30\r\2a\r\3\2\2\2bd\7\13\2\2ce\5\20\t\2dc\3\2\2\2"
			+ "ef\3\2\2\2fd\3\2\2\2fg\3\2\2\2g\17\3\2\2\2hm\5\24\13\2ij\7\7\2\2jl\5\22"
			+ "\n\2ki\3\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2nr\3\2\2\2om\3\2\2\2pq\7\f"
			+ "\2\2qs\5\60\31\2rp\3\2\2\2rs\3\2\2\2s\21\3\2\2\2tu\7&\2\2uv\7\r\2\2vw"
			+ "\5*\26\2wx\7\16\2\2xy\5*\26\2y\23\3\2\2\2z~\5\26\f\2{~\5 \21\2|~\5\30"
			+ "\r\2}z\3\2\2\2}{\3\2\2\2}|\3\2\2\2~\25\3\2\2\2\177\u0080\b\f\1\2\u0080"
			+ "\u0081\5\30\r\2\u0081\u0082\5\62\32\2\u0082\u0083\5*\26\2\u0083\u00a0"
			+ "\3\2\2\2\u0084\u0085\5\36\20\2\u0085\u0086\7\17\2\2\u0086\u0087\5\26\f"
			+ "\7\u0087\u00a0\3\2\2\2\u0088\u0089\7\20\2\2\u0089\u008a\7\5\2\2\u008a"
			+ "\u008b\5\62\32\2\u008b\u008c\7\'\2\2\u008c\u008d\7\7\2\2\u008d\u0090\5"
			+ "\26\f\2\u008e\u008f\7\f\2\2\u008f\u0091\5\26\f\2\u0090\u008e\3\2\2\2\u0091"
			+ "\u0092\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\3\2"
			+ "\2\2\u0094\u0095\7\6\2\2\u0095\u00a0\3\2\2\2\u0096\u0097\5\36\20\2\u0097"
			+ "\u0098\7\22\2\2\u0098\u0099\5\36\20\2\u0099\u00a0\3\2\2\2\u009a\u009b"
			+ "\7\23\2\2\u009b\u009c\7\5\2\2\u009c\u009d\5\20\t\2\u009d\u009e\7\6\2\2"
			+ "\u009e\u00a0\3\2\2\2\u009f\177\3\2\2\2\u009f\u0084\3\2\2\2\u009f\u0088"
			+ "\3\2\2\2\u009f\u0096\3\2\2\2\u009f\u009a\3\2\2\2\u00a0\u00a6\3\2\2\2\u00a1"
			+ "\u00a2\f\5\2\2\u00a2\u00a3\7\21\2\2\u00a3\u00a5\5\26\f\6\u00a4\u00a1\3"
			+ "\2\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"
			+ "\27\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00ad\5\34\17\2\u00aa\u00ac\5\32"
			+ "\16\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad"
			+ "\u00ae\3\2\2\2\u00ae\u00bb\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00b1\7\24"
			+ "\2\2\u00b1\u00b2\7\5\2\2\u00b2\u00b3\5\20\t\2\u00b3\u00b7\7\6\2\2\u00b4"
			+ "\u00b6\5\32\16\2\u00b5\u00b4\3\2\2\2\u00b6\u00b9\3\2\2\2\u00b7\u00b5\3"
			+ "\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00ba"
			+ "\u00a9\3\2\2\2\u00ba\u00b0\3\2\2\2\u00bb\31\3\2\2\2\u00bc\u00bd\7\25\2"
			+ "\2\u00bd\u00c7\5\34\17\2\u00be\u00bf\7\25\2\2\u00bf\u00c0\7\24\2\2\u00c0"
			+ "\u00c1\7\5\2\2\u00c1\u00c2\5\20\t\2\u00c2\u00c3\7\6\2\2\u00c3\u00c7\3"
			+ "\2\2\2\u00c4\u00c5\7\26\2\2\u00c5\u00c7\5\34\17\2\u00c6\u00bc\3\2\2\2"
			+ "\u00c6\u00be\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c7\33\3\2\2\2\u00c8\u00ca"
			+ "\5*\26\2\u00c9\u00c8\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb"
			+ "\u00ce\5\36\20\2\u00cc\u00ce\5*\26\2\u00cd\u00c9\3\2\2\2\u00cd\u00cc\3"
			+ "\2\2\2\u00ce\35\3\2\2\2\u00cf\u00d0\7&\2\2\u00d0\u00d1\7\27\2\2\u00d1"
			+ "\u00d6\5*\26\2\u00d2\u00d3\7\7\2\2\u00d3\u00d5\5*\26\2\u00d4\u00d2\3\2"
			+ "\2\2\u00d5\u00d8\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7"
			+ "\u00d9\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d9\u00da\7\30\2\2\u00da\37\3\2\2"
			+ "\2\u00db\u00dc\5\36\20\2\u00dc\u00dd\5\62\32\2\u00dd\u00de\5*\26\2\u00de"
			+ "\u00e6\3\2\2\2\u00df\u00e0\5*\26\2\u00e0\u00e1\7\31\2\2\u00e1\u00e2\5"
			+ "\36\20\2\u00e2\u00e3\7\31\2\2\u00e3\u00e4\5*\26\2\u00e4\u00e6\3\2\2\2"
			+ "\u00e5\u00db\3\2\2\2\u00e5\u00df\3\2\2\2\u00e6!\3\2\2\2\u00e7\u00e9\7"
			+ "\32\2\2\u00e8\u00ea\5\20\t\2\u00e9\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb"
			+ "\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec#\3\2\2\2\u00ed\u00f1\7\33\2\2"
			+ "\u00ee\u00f0\5\20\t\2\u00ef\u00ee\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1\u00ef"
			+ "\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2%\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f4"
			+ "\u00f8\7\34\2\2\u00f5\u00f7\5\20\t\2\u00f6\u00f5\3\2\2\2\u00f7\u00fa\3"
			+ "\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\'\3\2\2\2\u00fa\u00f8"
			+ "\3\2\2\2\u00fb\u00ff\7\35\2\2\u00fc\u00fe\5\20\t\2\u00fd\u00fc\3\2\2\2"
			+ "\u00fe\u0101\3\2\2\2\u00ff\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100)\3"
			+ "\2\2\2\u0101\u00ff\3\2\2\2\u0102\u0103\b\26\1\2\u0103\u0104\7\36\2\2\u0104"
			+ "\u0114\5*\26\r\u0105\u0106\7\37\2\2\u0106\u0114\5*\26\f\u0107\u0108\7"
			+ "\25\2\2\u0108\u0114\5*\26\13\u0109\u010a\7\26\2\2\u010a\u0114\5*\26\n"
			+ "\u010b\u010c\7\5\2\2\u010c\u010d\5*\26\2\u010d\u010e\7\6\2\2\u010e\u0114"
			+ "\3\2\2\2\u010f\u0114\5,\27\2\u0110\u0114\7&\2\2\u0111\u0114\7(\2\2\u0112"
			+ "\u0114\7\'\2\2\u0113\u0102\3\2\2\2\u0113\u0105\3\2\2\2\u0113\u0107\3\2"
			+ "\2\2\u0113\u0109\3\2\2\2\u0113\u010b\3\2\2\2\u0113\u010f\3\2\2\2\u0113"
			+ "\u0110\3\2\2\2\u0113\u0111\3\2\2\2\u0113\u0112\3\2\2\2\u0114\u011d\3\2"
			+ "\2\2\u0115\u0116\f\t\2\2\u0116\u0117\t\3\2\2\u0117\u011c\5*\26\n\u0118"
			+ "\u0119\f\b\2\2\u0119\u011a\t\4\2\2\u011a\u011c\5*\26\t\u011b\u0115\3\2"
			+ "\2\2\u011b\u0118\3\2\2\2\u011c\u011f\3\2\2\2\u011d\u011b\3\2\2\2\u011d"
			+ "\u011e\3\2\2\2\u011e+\3\2\2\2\u011f\u011d\3\2\2\2\u0120\u0121\7&\2\2\u0121"
			+ "\u0123\7\5\2\2\u0122\u0124\5.\30\2\u0123\u0122\3\2\2\2\u0123\u0124\3\2"
			+ "\2\2\u0124\u0125\3\2\2\2\u0125\u0126\7\6\2\2\u0126-\3\2\2\2\u0127\u012c"
			+ "\5*\26\2\u0128\u0129\7\7\2\2\u0129\u012b\5*\26\2\u012a\u0128\3\2\2\2\u012b"
			+ "\u012e\3\2\2\2\u012c\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012d/\3\2\2\2"
			+ "\u012e\u012c\3\2\2\2\u012f\u0130\5*\26\2\u0130\u0131\5\62\32\2\u0131\u0132"
			+ "\5*\26\2\u0132\61\3\2\2\2\u0133\u0134\t\5\2\2\u0134\63\3\2\2\2 \65BMP"
			+ "W\\fmr}\u0092\u009f\u00a6\u00ad\u00b7\u00ba\u00c6\u00c9\u00cd\u00d6\u00e5"
			+ "\u00eb\u00f1\u00f8\u00ff\u0113\u011b\u011d\u0123\u012c";
	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}