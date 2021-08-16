// Generated from PLIModel.g4 by ANTLR 4.9.2
package us.lsi.model_test;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PLIModelParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, ID=49, INT=50, DOUBLE=51, WS=52;
	public static final int
		RULE_model = 0, RULE_head = 1, RULE_declaration = 2, RULE_formal_parameters = 3, 
		RULE_formal_parameter = 4, RULE_goal = 5, RULE_constraints = 6, RULE_list = 7, 
		RULE_indx = 8, RULE_indexed_elem = 9, RULE_constraint = 10, RULE_pair = 11, 
		RULE_generate_exp = 12, RULE_s_factor = 13, RULE_factor = 14, RULE_var_id = 15, 
		RULE_index_var_id = 16, RULE_bound = 17, RULE_bounds = 18, RULE_bin_vars = 19, 
		RULE_int_vars = 20, RULE_free_vars = 21, RULE_semi_continuous_vars = 22, 
		RULE_exp = 23, RULE_call_function = 24, RULE_real_parameters = 25, RULE_rel_op = 26;
	private static String[] makeRuleNames() {
		return new String[] {
			"model", "head", "declaration", "formal_parameters", "formal_parameter", 
			"goal", "constraints", "list", "indx", "indexed_elem", "constraint", 
			"pair", "generate_exp", "s_factor", "factor", "var_id", "index_var_id", 
			"bound", "bounds", "bin_vars", "int_vars", "free_vars", "semi_continuous_vars", 
			"exp", "call_function", "real_parameters", "rel_op"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'head section'", "'='", "'('", "')'", "','", "'goal section'", 
			"'min'", "'max'", "'constraints section'", "'|'", "'in'", "'..'", "'->'", 
			"'or'", "'=>'", "'!='", "'allDifferent'", "'allDifferentInValues'", "';'", 
			"'MAX'", "'MIN'", "'OR'", "'AND'", "'ABS'", "'PWL'", "':'", "'sum'", 
			"'+'", "'-'", "'['", "']'", "'<='", "'bounds section'", "'bin'", "'int'", 
			"'free'", "'semi-continuous'", "'(int)'", "'(double)'", "'!'", "'*'", 
			"'/'", "'%'", "'<'", "'>='", "'>'", "'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "ID", "INT", "DOUBLE", "WS"
		};
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
	public String getGrammarFileName() { return "PLIModel.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PLIModelParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ModelContext extends ParserRuleContext {
		public GoalContext goal() {
			return getRuleContext(GoalContext.class,0);
		}
		public ConstraintsContext constraints() {
			return getRuleContext(ConstraintsContext.class,0);
		}
		public HeadContext head() {
			return getRuleContext(HeadContext.class,0);
		}
		public BoundsContext bounds() {
			return getRuleContext(BoundsContext.class,0);
		}
		public Bin_varsContext bin_vars() {
			return getRuleContext(Bin_varsContext.class,0);
		}
		public Int_varsContext int_vars() {
			return getRuleContext(Int_varsContext.class,0);
		}
		public Free_varsContext free_vars() {
			return getRuleContext(Free_varsContext.class,0);
		}
		public Semi_continuous_varsContext semi_continuous_vars() {
			return getRuleContext(Semi_continuous_varsContext.class,0);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitModel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(54);
				head();
				}
			}

			setState(57);
			goal();
			setState(58);
			constraints();
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__32) {
				{
				setState(59);
				bounds();
				}
			}

			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__33) {
				{
				setState(62);
				bin_vars();
				}
			}

			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__34) {
				{
				setState(65);
				int_vars();
				}
			}

			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__35) {
				{
				setState(68);
				free_vars();
				}
			}

			setState(72);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__36) {
				{
				setState(71);
				semi_continuous_vars();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HeadContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public HeadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_head; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitHead(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeadContext head() throws RecognitionException {
		HeadContext _localctx = new HeadContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_head);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(T__0);
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(75);
				declaration();
				}
				}
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	 
		public DeclarationContext() { }
		public void copyFrom(DeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FunDeclarContext extends DeclarationContext {
		public Token type;
		public Token name;
		public List<TerminalNode> ID() { return getTokens(PLIModelParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(PLIModelParser.ID, i);
		}
		public Formal_parametersContext formal_parameters() {
			return getRuleContext(Formal_parametersContext.class,0);
		}
		public FunDeclarContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitFunDeclar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarDeclarContext extends DeclarationContext {
		public Token type;
		public Token name;
		public ExpContext val;
		public List<TerminalNode> ID() { return getTokens(PLIModelParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(PLIModelParser.ID, i);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public VarDeclarContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitVarDeclar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaration);
		int _la;
		try {
			setState(92);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new VarDeclarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(81);
				((VarDeclarContext)_localctx).type = match(ID);
				setState(82);
				((VarDeclarContext)_localctx).name = match(ID);
				setState(83);
				match(T__1);
				setState(84);
				((VarDeclarContext)_localctx).val = exp(0);
				}
				break;
			case 2:
				_localctx = new FunDeclarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				((FunDeclarContext)_localctx).type = match(ID);
				setState(86);
				((FunDeclarContext)_localctx).name = match(ID);
				setState(87);
				match(T__2);
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(88);
					formal_parameters();
					}
				}

				setState(91);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Formal_parametersContext extends ParserRuleContext {
		public List<Formal_parameterContext> formal_parameter() {
			return getRuleContexts(Formal_parameterContext.class);
		}
		public Formal_parameterContext formal_parameter(int i) {
			return getRuleContext(Formal_parameterContext.class,i);
		}
		public Formal_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formal_parameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitFormal_parameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Formal_parametersContext formal_parameters() throws RecognitionException {
		Formal_parametersContext _localctx = new Formal_parametersContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_formal_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			formal_parameter();
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(95);
				match(T__4);
				setState(96);
				formal_parameter();
				}
				}
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Formal_parameterContext extends ParserRuleContext {
		public Token type;
		public Token name;
		public List<TerminalNode> ID() { return getTokens(PLIModelParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(PLIModelParser.ID, i);
		}
		public Formal_parameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formal_parameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitFormal_parameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Formal_parameterContext formal_parameter() throws RecognitionException {
		Formal_parameterContext _localctx = new Formal_parameterContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_formal_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			((Formal_parameterContext)_localctx).type = match(ID);
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(103);
				((Formal_parameterContext)_localctx).name = match(ID);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GoalContext extends ParserRuleContext {
		public GoalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goal; }
	 
		public GoalContext() { }
		public void copyFrom(GoalContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class GoalSectionContext extends GoalContext {
		public Token obj;
		public Generate_expContext generate_exp() {
			return getRuleContext(Generate_expContext.class,0);
		}
		public GoalSectionContext(GoalContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitGoalSection(this);
			else return visitor.visitChildren(this);
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
			setState(106);
			match(T__5);
			setState(107);
			((GoalSectionContext)_localctx).obj = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__6 || _la==T__7) ) {
				((GoalSectionContext)_localctx).obj = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(108);
			generate_exp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}
		public ListContext list(int i) {
			return getRuleContext(ListContext.class,i);
		}
		public ConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraints; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitConstraints(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstraintsContext constraints() throws RecognitionException {
		ConstraintsContext _localctx = new ConstraintsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_constraints);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(T__8);
			setState(112); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(111);
				list();
				}
				}
				setState(114); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListContext extends ParserRuleContext {
		public Indexed_elemContext indexed_elem() {
			return getRuleContext(Indexed_elemContext.class,0);
		}
		public List<IndxContext> indx() {
			return getRuleContexts(IndxContext.class);
		}
		public IndxContext indx(int i) {
			return getRuleContext(IndxContext.class,i);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			indexed_elem();
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(117);
				match(T__4);
				setState(118);
				indx();
				}
				}
				setState(123);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(124);
				match(T__9);
				setState(125);
				exp(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndxContext extends ParserRuleContext {
		public Token index_name;
		public ExpContext li;
		public ExpContext ls;
		public TerminalNode ID() { return getToken(PLIModelParser.ID, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public IndxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indx; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitIndx(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndxContext indx() throws RecognitionException {
		IndxContext _localctx = new IndxContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_indx);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			((IndxContext)_localctx).index_name = match(ID);
			setState(129);
			match(T__10);
			setState(130);
			((IndxContext)_localctx).li = exp(0);
			setState(131);
			match(T__11);
			setState(132);
			((IndxContext)_localctx).ls = exp(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Indexed_elemContext extends ParserRuleContext {
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public BoundContext bound() {
			return getRuleContext(BoundContext.class,0);
		}
		public Generate_expContext generate_exp() {
			return getRuleContext(Generate_expContext.class,0);
		}
		public Indexed_elemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexed_elem; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitIndexed_elem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Indexed_elemContext indexed_elem() throws RecognitionException {
		Indexed_elemContext _localctx = new Indexed_elemContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_indexed_elem);
		try {
			setState(137);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(134);
				constraint(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(135);
				bound();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(136);
				generate_exp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintContext extends ParserRuleContext {
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
	 
		public ConstraintContext() { }
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
			return getRuleContext(Var_idContext.class,i);
		}
		public DifferentValueConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitDifferentValueConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtomConstraintContext extends ConstraintContext {
		public Generate_expContext generate_exp() {
			return getRuleContext(Generate_expContext.class,0);
		}
		public Rel_opContext rel_op() {
			return getRuleContext(Rel_opContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public AtomConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitAtomConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MaxConstraintContext extends ConstraintContext {
		public Var_idContext left;
		public ListContext vars;
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public MaxConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitMaxConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrBinConstraintContext extends ConstraintContext {
		public Var_idContext left;
		public ListContext vars;
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public OrBinConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitOrBinConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MinConstraintContext extends ConstraintContext {
		public Var_idContext left;
		public ListContext vars;
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public MinConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitMinConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AllDifferentValuesConstraintContext extends ConstraintContext {
		public ListContext vars;
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public AllDifferentValuesConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitAllDifferentValuesConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AbsConstraintContext extends ConstraintContext {
		public Var_idContext left;
		public Var_idContext right;
		public List<Var_idContext> var_id() {
			return getRuleContexts(Var_idContext.class);
		}
		public Var_idContext var_id(int i) {
			return getRuleContext(Var_idContext.class,i);
		}
		public AbsConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitAbsConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AllDifferentInValuesConstraintContext extends ConstraintContext {
		public ListContext vars;
		public ExpContext values;
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public AllDifferentInValuesConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitAllDifferentInValuesConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IndicatorConstraintContext extends ConstraintContext {
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public IndicatorConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitIndicatorConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ImplyConstraintContext extends ConstraintContext {
		public ConstraintContext left;
		public ConstraintContext right;
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public ImplyConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitImplyConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrConstraintContext extends ConstraintContext {
		public Token n;
		public Rel_opContext rel_op() {
			return getRuleContext(Rel_opContext.class,0);
		}
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public TerminalNode INT() { return getToken(PLIModelParser.INT, 0); }
		public OrConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitOrConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PiecewiseConstraintContext extends ConstraintContext {
		public Var_idContext left;
		public Var_idContext right;
		public PairContext data;
		public List<Var_idContext> var_id() {
			return getRuleContexts(Var_idContext.class);
		}
		public Var_idContext var_id(int i) {
			return getRuleContext(Var_idContext.class,i);
		}
		public List<PairContext> pair() {
			return getRuleContexts(PairContext.class);
		}
		public PairContext pair(int i) {
			return getRuleContext(PairContext.class,i);
		}
		public PiecewiseConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitPiecewiseConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndBinConstraintContext extends ConstraintContext {
		public Var_idContext left;
		public ListContext vars;
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public AndBinConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitAndBinConstraint(this);
			else return visitor.visitChildren(this);
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
			setState(229);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				_localctx = new AtomConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(140);
				generate_exp();
				setState(141);
				rel_op();
				setState(142);
				exp(0);
				}
				break;
			case 2:
				{
				_localctx = new IndicatorConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(144);
				var_id();
				setState(145);
				match(T__12);
				setState(146);
				constraint(12);
				}
				break;
			case 3:
				{
				_localctx = new OrConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(148);
				match(T__13);
				setState(149);
				match(T__2);
				setState(150);
				rel_op();
				setState(151);
				((OrConstraintContext)_localctx).n = match(INT);
				setState(152);
				match(T__4);
				setState(153);
				constraint(0);
				setState(156); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(154);
					match(T__9);
					setState(155);
					constraint(0);
					}
					}
					setState(158); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__9 );
				setState(160);
				match(T__3);
				}
				break;
			case 4:
				{
				_localctx = new DifferentValueConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(162);
				((DifferentValueConstraintContext)_localctx).left = var_id();
				setState(163);
				match(T__15);
				setState(164);
				((DifferentValueConstraintContext)_localctx).right = var_id();
				}
				break;
			case 5:
				{
				_localctx = new AllDifferentValuesConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(166);
				match(T__16);
				setState(167);
				match(T__2);
				setState(168);
				((AllDifferentValuesConstraintContext)_localctx).vars = list();
				setState(169);
				match(T__3);
				}
				break;
			case 6:
				{
				_localctx = new AllDifferentInValuesConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(171);
				match(T__17);
				setState(172);
				match(T__2);
				setState(173);
				((AllDifferentInValuesConstraintContext)_localctx).vars = list();
				setState(174);
				match(T__18);
				setState(176); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(175);
					((AllDifferentInValuesConstraintContext)_localctx).values = exp(0);
					}
					}
					setState(178); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__27) | (1L << T__28) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
				setState(180);
				match(T__3);
				}
				break;
			case 7:
				{
				_localctx = new MaxConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(182);
				((MaxConstraintContext)_localctx).left = var_id();
				setState(183);
				match(T__1);
				setState(184);
				match(T__19);
				setState(185);
				match(T__2);
				setState(186);
				((MaxConstraintContext)_localctx).vars = list();
				setState(187);
				match(T__3);
				}
				break;
			case 8:
				{
				_localctx = new MinConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(189);
				((MinConstraintContext)_localctx).left = var_id();
				setState(190);
				match(T__1);
				setState(191);
				match(T__20);
				setState(192);
				match(T__2);
				setState(193);
				((MinConstraintContext)_localctx).vars = list();
				setState(194);
				match(T__3);
				}
				break;
			case 9:
				{
				_localctx = new OrBinConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(196);
				((OrBinConstraintContext)_localctx).left = var_id();
				setState(197);
				match(T__1);
				setState(198);
				match(T__21);
				setState(199);
				match(T__2);
				setState(200);
				((OrBinConstraintContext)_localctx).vars = list();
				setState(201);
				match(T__3);
				}
				break;
			case 10:
				{
				_localctx = new AndBinConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(203);
				((AndBinConstraintContext)_localctx).left = var_id();
				setState(204);
				match(T__1);
				setState(205);
				match(T__22);
				setState(206);
				match(T__2);
				setState(207);
				((AndBinConstraintContext)_localctx).vars = list();
				setState(208);
				match(T__3);
				}
				break;
			case 11:
				{
				_localctx = new AbsConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(210);
				((AbsConstraintContext)_localctx).left = var_id();
				setState(211);
				match(T__1);
				setState(212);
				match(T__23);
				setState(213);
				match(T__2);
				setState(214);
				((AbsConstraintContext)_localctx).right = var_id();
				setState(215);
				match(T__3);
				}
				break;
			case 12:
				{
				_localctx = new PiecewiseConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(217);
				((PiecewiseConstraintContext)_localctx).left = var_id();
				setState(218);
				match(T__1);
				setState(219);
				match(T__24);
				setState(220);
				match(T__2);
				setState(221);
				((PiecewiseConstraintContext)_localctx).right = var_id();
				setState(222);
				match(T__3);
				setState(223);
				match(T__25);
				setState(225); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(224);
						((PiecewiseConstraintContext)_localctx).data = pair();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(227); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(236);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ImplyConstraintContext(new ConstraintContext(_parentctx, _parentState));
					((ImplyConstraintContext)_localctx).left = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_constraint);
					setState(231);
					if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
					setState(232);
					match(T__14);
					setState(233);
					((ImplyConstraintContext)_localctx).right = constraint(11);
					}
					} 
				}
				setState(238);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PairContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(PLIModelParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(PLIModelParser.INT, i);
		}
		public PairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pair; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitPair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairContext pair() throws RecognitionException {
		PairContext _localctx = new PairContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_pair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(T__2);
			setState(240);
			match(INT);
			setState(241);
			match(T__4);
			setState(242);
			match(INT);
			setState(243);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Generate_expContext extends ParserRuleContext {
		public Generate_expContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_generate_exp; }
	 
		public Generate_expContext() { }
		public void copyFrom(Generate_expContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FactorGenerateExpContext extends Generate_expContext {
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public List<S_factorContext> s_factor() {
			return getRuleContexts(S_factorContext.class);
		}
		public S_factorContext s_factor(int i) {
			return getRuleContext(S_factorContext.class,i);
		}
		public FactorGenerateExpContext(Generate_expContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitFactorGenerateExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SumGenerateExpContext extends Generate_expContext {
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public List<S_factorContext> s_factor() {
			return getRuleContexts(S_factorContext.class);
		}
		public S_factorContext s_factor(int i) {
			return getRuleContext(S_factorContext.class,i);
		}
		public SumGenerateExpContext(Generate_expContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitSumGenerateExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Generate_expContext generate_exp() throws RecognitionException {
		Generate_expContext _localctx = new Generate_expContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_generate_exp);
		try {
			int _alt;
			setState(262);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case T__27:
			case T__28:
			case T__37:
			case T__38:
			case T__39:
			case ID:
			case INT:
			case DOUBLE:
				_localctx = new FactorGenerateExpContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(245);
				factor();
				setState(249);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(246);
						s_factor();
						}
						} 
					}
					setState(251);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				}
				}
				break;
			case T__26:
				_localctx = new SumGenerateExpContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(252);
				match(T__26);
				setState(253);
				match(T__2);
				setState(254);
				list();
				setState(255);
				match(T__3);
				setState(259);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(256);
						s_factor();
						}
						} 
					}
					setState(261);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class S_factorContext extends ParserRuleContext {
		public S_factorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_s_factor; }
	 
		public S_factorContext() { }
		public void copyFrom(S_factorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MinusSumContext extends S_factorContext {
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public MinusSumContext(S_factorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitMinusSum(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusFactorContext extends S_factorContext {
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public PlusFactorContext(S_factorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitPlusFactor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MinusFactorContext extends S_factorContext {
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public MinusFactorContext(S_factorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitMinusFactor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusSumContext extends S_factorContext {
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public PlusSumContext(S_factorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitPlusSum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final S_factorContext s_factor() throws RecognitionException {
		S_factorContext _localctx = new S_factorContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_s_factor);
		try {
			setState(280);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				_localctx = new PlusFactorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(264);
				match(T__27);
				setState(265);
				factor();
				}
				break;
			case 2:
				_localctx = new PlusSumContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(266);
				match(T__27);
				setState(267);
				match(T__26);
				setState(268);
				match(T__2);
				setState(269);
				list();
				setState(270);
				match(T__3);
				}
				break;
			case 3:
				_localctx = new MinusSumContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(272);
				match(T__28);
				setState(273);
				match(T__26);
				setState(274);
				match(T__2);
				setState(275);
				list();
				setState(276);
				match(T__3);
				}
				break;
			case 4:
				_localctx = new MinusFactorContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(278);
				match(T__28);
				setState(279);
				factor();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FactorContext extends ParserRuleContext {
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
	 
		public FactorContext() { }
		public void copyFrom(FactorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VarFactorContext extends FactorContext {
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public VarFactorContext(FactorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitVarFactor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_factor);
		try {
			_localctx = new VarFactorContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(282);
				exp(0);
				}
				break;
			}
			setState(285);
			var_id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_idContext extends ParserRuleContext {
		public Token name;
		public Index_var_idContext index_var_id() {
			return getRuleContext(Index_var_idContext.class,0);
		}
		public TerminalNode ID() { return getToken(PLIModelParser.ID, 0); }
		public Var_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_id; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitVar_id(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_idContext var_id() throws RecognitionException {
		Var_idContext _localctx = new Var_idContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_var_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			((Var_idContext)_localctx).name = match(ID);
			setState(288);
			index_var_id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Index_var_idContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public Index_var_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index_var_id; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitIndex_var_id(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Index_var_idContext index_var_id() throws RecognitionException {
		Index_var_idContext _localctx = new Index_var_idContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_index_var_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(290);
				match(T__29);
				setState(291);
				exp(0);
				setState(296);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(292);
					match(T__4);
					setState(293);
					exp(0);
					}
					}
					setState(298);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(299);
				match(T__30);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoundContext extends ParserRuleContext {
		public BoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bound; }
	 
		public BoundContext() { }
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
			return getRuleContext(ExpContext.class,i);
		}
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public TwoSideBoundContext(BoundContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitTwoSideBound(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OneSideBoundContext extends BoundContext {
		public Var_idContext name;
		public Rel_opContext op;
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public Rel_opContext rel_op() {
			return getRuleContext(Rel_opContext.class,0);
		}
		public OneSideBoundContext(BoundContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitOneSideBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoundContext bound() throws RecognitionException {
		BoundContext _localctx = new BoundContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_bound);
		try {
			setState(313);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				_localctx = new OneSideBoundContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(303);
				((OneSideBoundContext)_localctx).name = var_id();
				setState(304);
				((OneSideBoundContext)_localctx).op = rel_op();
				setState(305);
				exp(0);
				}
				break;
			case 2:
				_localctx = new TwoSideBoundContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(307);
				((TwoSideBoundContext)_localctx).li = exp(0);
				setState(308);
				match(T__31);
				setState(309);
				((TwoSideBoundContext)_localctx).name = var_id();
				setState(310);
				match(T__31);
				setState(311);
				((TwoSideBoundContext)_localctx).ls = exp(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoundsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}
		public ListContext list(int i) {
			return getRuleContext(ListContext.class,i);
		}
		public BoundsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bounds; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitBounds(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoundsContext bounds() throws RecognitionException {
		BoundsContext _localctx = new BoundsContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_bounds);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			match(T__32);
			setState(317); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(316);
				list();
				}
				}
				setState(319); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Bin_varsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}
		public ListContext list(int i) {
			return getRuleContext(ListContext.class,i);
		}
		public Bin_varsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bin_vars; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitBin_vars(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Bin_varsContext bin_vars() throws RecognitionException {
		Bin_varsContext _localctx = new Bin_varsContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_bin_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(321);
			match(T__33);
			setState(323); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(322);
				list();
				}
				}
				setState(325); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Int_varsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}
		public ListContext list(int i) {
			return getRuleContext(ListContext.class,i);
		}
		public Int_varsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_int_vars; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitInt_vars(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Int_varsContext int_vars() throws RecognitionException {
		Int_varsContext _localctx = new Int_varsContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_int_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			match(T__34);
			setState(329); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(328);
				list();
				}
				}
				setState(331); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Free_varsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}
		public ListContext list(int i) {
			return getRuleContext(ListContext.class,i);
		}
		public Free_varsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_free_vars; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitFree_vars(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Free_varsContext free_vars() throws RecognitionException {
		Free_varsContext _localctx = new Free_varsContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_free_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			match(T__35);
			setState(335); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(334);
				list();
				}
				}
				setState(337); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Semi_continuous_varsContext extends ParserRuleContext {
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}
		public ListContext list(int i) {
			return getRuleContext(ListContext.class,i);
		}
		public Semi_continuous_varsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_semi_continuous_vars; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitSemi_continuous_vars(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Semi_continuous_varsContext semi_continuous_vars() throws RecognitionException {
		Semi_continuous_varsContext _localctx = new Semi_continuous_varsContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_semi_continuous_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(339);
			match(T__36);
			setState(341); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(340);
				list();
				}
				}
				setState(343); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__13) | (1L << T__16) | (1L << T__17) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
	 
		public ExpContext() { }
		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class UnaryOpExprContext extends ExpContext {
		public Token op;
		public ExpContext right;
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public UnaryOpExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitUnaryOpExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntExprContext extends ExpContext {
		public TerminalNode INT() { return getToken(PLIModelParser.INT, 0); }
		public IntExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitIntExpr(this);
			else return visitor.visitChildren(this);
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
			return getRuleContext(ExpContext.class,i);
		}
		public OpExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitOpExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunExprContext extends ExpContext {
		public Call_functionContext call_function() {
			return getRuleContext(Call_functionContext.class,0);
		}
		public FunExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitFunExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenExprContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ParenExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DoubleExpContext extends ExpContext {
		public TerminalNode DOUBLE() { return getToken(PLIModelParser.DOUBLE, 0); }
		public DoubleExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitDoubleExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdExprContext extends ExpContext {
		public Token id;
		public TerminalNode ID() { return getToken(PLIModelParser.ID, 0); }
		public IdExprContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitIdExpr(this);
			else return visitor.visitChildren(this);
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
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				_localctx = new UnaryOpExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(346);
				((UnaryOpExprContext)_localctx).op = match(T__37);
				setState(347);
				((UnaryOpExprContext)_localctx).right = exp(13);
				}
				break;
			case 2:
				{
				_localctx = new UnaryOpExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(348);
				((UnaryOpExprContext)_localctx).op = match(T__38);
				setState(349);
				((UnaryOpExprContext)_localctx).right = exp(12);
				}
				break;
			case 3:
				{
				_localctx = new UnaryOpExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(350);
				((UnaryOpExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__27) | (1L << T__28) | (1L << T__39))) != 0)) ) {
					((UnaryOpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(351);
				((UnaryOpExprContext)_localctx).right = exp(11);
				}
				break;
			case 4:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(352);
				match(T__2);
				setState(353);
				exp(0);
				setState(354);
				match(T__3);
				}
				break;
			case 5:
				{
				_localctx = new FunExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(356);
				call_function();
				}
				break;
			case 6:
				{
				_localctx = new IdExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(357);
				((IdExprContext)_localctx).id = match(ID);
				}
				break;
			case 7:
				{
				_localctx = new DoubleExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(358);
				match(DOUBLE);
				}
				break;
			case 8:
				{
				_localctx = new IntExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(359);
				match(INT);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(379);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(377);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
					case 1:
						{
						_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(362);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(363);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__40) | (1L << T__41) | (1L << T__42))) != 0)) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(364);
						((OpExprContext)_localctx).right = exp(11);
						}
						break;
					case 2:
						{
						_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(365);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(366);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__27 || _la==T__28) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(367);
						((OpExprContext)_localctx).right = exp(10);
						}
						break;
					case 3:
						{
						_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(368);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(369);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__31) | (1L << T__43) | (1L << T__44) | (1L << T__45))) != 0)) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(370);
						((OpExprContext)_localctx).right = exp(9);
						}
						break;
					case 4:
						{
						_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(371);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(372);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__1 || _la==T__15) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(373);
						((OpExprContext)_localctx).right = exp(8);
						}
						break;
					case 5:
						{
						_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(374);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(375);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__46 || _la==T__47) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(376);
						((OpExprContext)_localctx).right = exp(7);
						}
						break;
					}
					} 
				}
				setState(381);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Call_functionContext extends ParserRuleContext {
		public Token name;
		public TerminalNode ID() { return getToken(PLIModelParser.ID, 0); }
		public Real_parametersContext real_parameters() {
			return getRuleContext(Real_parametersContext.class,0);
		}
		public Call_functionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call_function; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitCall_function(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Call_functionContext call_function() throws RecognitionException {
		Call_functionContext _localctx = new Call_functionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_call_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			((Call_functionContext)_localctx).name = match(ID);
			setState(383);
			match(T__2);
			setState(385);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__27) | (1L << T__28) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0)) {
				{
				setState(384);
				real_parameters();
				}
			}

			setState(387);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Real_parametersContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public Real_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_real_parameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitReal_parameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Real_parametersContext real_parameters() throws RecognitionException {
		Real_parametersContext _localctx = new Real_parametersContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_real_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(389);
			exp(0);
			setState(394);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(390);
				match(T__4);
				setState(391);
				exp(0);
				}
				}
				setState(396);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rel_opContext extends ParserRuleContext {
		public Rel_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rel_op; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitRel_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rel_opContext rel_op() throws RecognitionException {
		Rel_opContext _localctx = new Rel_opContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_rel_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__31) | (1L << T__43) | (1L << T__44) | (1L << T__45))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return constraint_sempred((ConstraintContext)_localctx, predIndex);
		case 23:
			return exp_sempred((ExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean constraint_sempred(ConstraintContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 9);
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 7);
		case 5:
			return precpred(_ctx, 6);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\66\u0192\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\3\2\5\2:\n\2\3\2\3\2\3\2\5\2?\n\2\3\2\5"+
		"\2B\n\2\3\2\5\2E\n\2\3\2\5\2H\n\2\3\2\5\2K\n\2\3\3\3\3\7\3O\n\3\f\3\16"+
		"\3R\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\\\n\4\3\4\5\4_\n\4\3\5\3"+
		"\5\3\5\7\5d\n\5\f\5\16\5g\13\5\3\6\3\6\5\6k\n\6\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\6\bs\n\b\r\b\16\bt\3\t\3\t\3\t\7\tz\n\t\f\t\16\t}\13\t\3\t\3\t\5\t"+
		"\u0081\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\5\13\u008c\n\13\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\6\f\u009f"+
		"\n\f\r\f\16\f\u00a0\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\6\f\u00b3\n\f\r\f\16\f\u00b4\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\6\f\u00e4\n\f\r\f\16\f\u00e5\5\f\u00e8\n\f\3\f\3\f\3\f"+
		"\7\f\u00ed\n\f\f\f\16\f\u00f0\13\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\7"+
		"\16\u00fa\n\16\f\16\16\16\u00fd\13\16\3\16\3\16\3\16\3\16\3\16\7\16\u0104"+
		"\n\16\f\16\16\16\u0107\13\16\5\16\u0109\n\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u011b\n\17"+
		"\3\20\5\20\u011e\n\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\7\22"+
		"\u0129\n\22\f\22\16\22\u012c\13\22\3\22\3\22\5\22\u0130\n\22\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u013c\n\23\3\24\3\24\6\24"+
		"\u0140\n\24\r\24\16\24\u0141\3\25\3\25\6\25\u0146\n\25\r\25\16\25\u0147"+
		"\3\26\3\26\6\26\u014c\n\26\r\26\16\26\u014d\3\27\3\27\6\27\u0152\n\27"+
		"\r\27\16\27\u0153\3\30\3\30\6\30\u0158\n\30\r\30\16\30\u0159\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31"+
		"\u016b\n\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\7\31\u017c\n\31\f\31\16\31\u017f\13\31\3\32\3\32\3\32"+
		"\5\32\u0184\n\32\3\32\3\32\3\33\3\33\3\33\7\33\u018b\n\33\f\33\16\33\u018e"+
		"\13\33\3\34\3\34\3\34\2\4\26\60\35\2\4\6\b\n\f\16\20\22\24\26\30\32\34"+
		"\36 \"$&(*,.\60\62\64\66\2\n\3\2\t\n\4\2\36\37**\3\2+-\3\2\36\37\4\2\""+
		"\".\60\4\2\4\4\22\22\3\2\61\62\5\2\4\4\"\".\60\2\u01b2\29\3\2\2\2\4L\3"+
		"\2\2\2\6^\3\2\2\2\b`\3\2\2\2\nh\3\2\2\2\fl\3\2\2\2\16p\3\2\2\2\20v\3\2"+
		"\2\2\22\u0082\3\2\2\2\24\u008b\3\2\2\2\26\u00e7\3\2\2\2\30\u00f1\3\2\2"+
		"\2\32\u0108\3\2\2\2\34\u011a\3\2\2\2\36\u011d\3\2\2\2 \u0121\3\2\2\2\""+
		"\u012f\3\2\2\2$\u013b\3\2\2\2&\u013d\3\2\2\2(\u0143\3\2\2\2*\u0149\3\2"+
		"\2\2,\u014f\3\2\2\2.\u0155\3\2\2\2\60\u016a\3\2\2\2\62\u0180\3\2\2\2\64"+
		"\u0187\3\2\2\2\66\u018f\3\2\2\28:\5\4\3\298\3\2\2\29:\3\2\2\2:;\3\2\2"+
		"\2;<\5\f\7\2<>\5\16\b\2=?\5&\24\2>=\3\2\2\2>?\3\2\2\2?A\3\2\2\2@B\5(\25"+
		"\2A@\3\2\2\2AB\3\2\2\2BD\3\2\2\2CE\5*\26\2DC\3\2\2\2DE\3\2\2\2EG\3\2\2"+
		"\2FH\5,\27\2GF\3\2\2\2GH\3\2\2\2HJ\3\2\2\2IK\5.\30\2JI\3\2\2\2JK\3\2\2"+
		"\2K\3\3\2\2\2LP\7\3\2\2MO\5\6\4\2NM\3\2\2\2OR\3\2\2\2PN\3\2\2\2PQ\3\2"+
		"\2\2Q\5\3\2\2\2RP\3\2\2\2ST\7\63\2\2TU\7\63\2\2UV\7\4\2\2V_\5\60\31\2"+
		"WX\7\63\2\2XY\7\63\2\2Y[\7\5\2\2Z\\\5\b\5\2[Z\3\2\2\2[\\\3\2\2\2\\]\3"+
		"\2\2\2]_\7\6\2\2^S\3\2\2\2^W\3\2\2\2_\7\3\2\2\2`e\5\n\6\2ab\7\7\2\2bd"+
		"\5\n\6\2ca\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2f\t\3\2\2\2ge\3\2\2\2"+
		"hj\7\63\2\2ik\7\63\2\2ji\3\2\2\2jk\3\2\2\2k\13\3\2\2\2lm\7\b\2\2mn\t\2"+
		"\2\2no\5\32\16\2o\r\3\2\2\2pr\7\13\2\2qs\5\20\t\2rq\3\2\2\2st\3\2\2\2"+
		"tr\3\2\2\2tu\3\2\2\2u\17\3\2\2\2v{\5\24\13\2wx\7\7\2\2xz\5\22\n\2yw\3"+
		"\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|\u0080\3\2\2\2}{\3\2\2\2~\177\7\f"+
		"\2\2\177\u0081\5\60\31\2\u0080~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\21\3"+
		"\2\2\2\u0082\u0083\7\63\2\2\u0083\u0084\7\r\2\2\u0084\u0085\5\60\31\2"+
		"\u0085\u0086\7\16\2\2\u0086\u0087\5\60\31\2\u0087\23\3\2\2\2\u0088\u008c"+
		"\5\26\f\2\u0089\u008c\5$\23\2\u008a\u008c\5\32\16\2\u008b\u0088\3\2\2"+
		"\2\u008b\u0089\3\2\2\2\u008b\u008a\3\2\2\2\u008c\25\3\2\2\2\u008d\u008e"+
		"\b\f\1\2\u008e\u008f\5\32\16\2\u008f\u0090\5\66\34\2\u0090\u0091\5\60"+
		"\31\2\u0091\u00e8\3\2\2\2\u0092\u0093\5 \21\2\u0093\u0094\7\17\2\2\u0094"+
		"\u0095\5\26\f\16\u0095\u00e8\3\2\2\2\u0096\u0097\7\20\2\2\u0097\u0098"+
		"\7\5\2\2\u0098\u0099\5\66\34\2\u0099\u009a\7\64\2\2\u009a\u009b\7\7\2"+
		"\2\u009b\u009e\5\26\f\2\u009c\u009d\7\f\2\2\u009d\u009f\5\26\f\2\u009e"+
		"\u009c\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2"+
		"\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a3\7\6\2\2\u00a3\u00e8\3\2\2\2\u00a4"+
		"\u00a5\5 \21\2\u00a5\u00a6\7\22\2\2\u00a6\u00a7\5 \21\2\u00a7\u00e8\3"+
		"\2\2\2\u00a8\u00a9\7\23\2\2\u00a9\u00aa\7\5\2\2\u00aa\u00ab\5\20\t\2\u00ab"+
		"\u00ac\7\6\2\2\u00ac\u00e8\3\2\2\2\u00ad\u00ae\7\24\2\2\u00ae\u00af\7"+
		"\5\2\2\u00af\u00b0\5\20\t\2\u00b0\u00b2\7\25\2\2\u00b1\u00b3\5\60\31\2"+
		"\u00b2\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b4\u00b5"+
		"\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b7\7\6\2\2\u00b7\u00e8\3\2\2\2\u00b8"+
		"\u00b9\5 \21\2\u00b9\u00ba\7\4\2\2\u00ba\u00bb\7\26\2\2\u00bb\u00bc\7"+
		"\5\2\2\u00bc\u00bd\5\20\t\2\u00bd\u00be\7\6\2\2\u00be\u00e8\3\2\2\2\u00bf"+
		"\u00c0\5 \21\2\u00c0\u00c1\7\4\2\2\u00c1\u00c2\7\27\2\2\u00c2\u00c3\7"+
		"\5\2\2\u00c3\u00c4\5\20\t\2\u00c4\u00c5\7\6\2\2\u00c5\u00e8\3\2\2\2\u00c6"+
		"\u00c7\5 \21\2\u00c7\u00c8\7\4\2\2\u00c8\u00c9\7\30\2\2\u00c9\u00ca\7"+
		"\5\2\2\u00ca\u00cb\5\20\t\2\u00cb\u00cc\7\6\2\2\u00cc\u00e8\3\2\2\2\u00cd"+
		"\u00ce\5 \21\2\u00ce\u00cf\7\4\2\2\u00cf\u00d0\7\31\2\2\u00d0\u00d1\7"+
		"\5\2\2\u00d1\u00d2\5\20\t\2\u00d2\u00d3\7\6\2\2\u00d3\u00e8\3\2\2\2\u00d4"+
		"\u00d5\5 \21\2\u00d5\u00d6\7\4\2\2\u00d6\u00d7\7\32\2\2\u00d7\u00d8\7"+
		"\5\2\2\u00d8\u00d9\5 \21\2\u00d9\u00da\7\6\2\2\u00da\u00e8\3\2\2\2\u00db"+
		"\u00dc\5 \21\2\u00dc\u00dd\7\4\2\2\u00dd\u00de\7\33\2\2\u00de\u00df\7"+
		"\5\2\2\u00df\u00e0\5 \21\2\u00e0\u00e1\7\6\2\2\u00e1\u00e3\7\34\2\2\u00e2"+
		"\u00e4\5\30\r\2\u00e3\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e3\3"+
		"\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e8\3\2\2\2\u00e7\u008d\3\2\2\2\u00e7"+
		"\u0092\3\2\2\2\u00e7\u0096\3\2\2\2\u00e7\u00a4\3\2\2\2\u00e7\u00a8\3\2"+
		"\2\2\u00e7\u00ad\3\2\2\2\u00e7\u00b8\3\2\2\2\u00e7\u00bf\3\2\2\2\u00e7"+
		"\u00c6\3\2\2\2\u00e7\u00cd\3\2\2\2\u00e7\u00d4\3\2\2\2\u00e7\u00db\3\2"+
		"\2\2\u00e8\u00ee\3\2\2\2\u00e9\u00ea\f\f\2\2\u00ea\u00eb\7\21\2\2\u00eb"+
		"\u00ed\5\26\f\r\u00ec\u00e9\3\2\2\2\u00ed\u00f0\3\2\2\2\u00ee\u00ec\3"+
		"\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\27\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f1"+
		"\u00f2\7\5\2\2\u00f2\u00f3\7\64\2\2\u00f3\u00f4\7\7\2\2\u00f4\u00f5\7"+
		"\64\2\2\u00f5\u00f6\7\6\2\2\u00f6\31\3\2\2\2\u00f7\u00fb\5\36\20\2\u00f8"+
		"\u00fa\5\34\17\2\u00f9\u00f8\3\2\2\2\u00fa\u00fd\3\2\2\2\u00fb\u00f9\3"+
		"\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u0109\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fe"+
		"\u00ff\7\35\2\2\u00ff\u0100\7\5\2\2\u0100\u0101\5\20\t\2\u0101\u0105\7"+
		"\6\2\2\u0102\u0104\5\34\17\2\u0103\u0102\3\2\2\2\u0104\u0107\3\2\2\2\u0105"+
		"\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0109\3\2\2\2\u0107\u0105\3\2"+
		"\2\2\u0108\u00f7\3\2\2\2\u0108\u00fe\3\2\2\2\u0109\33\3\2\2\2\u010a\u010b"+
		"\7\36\2\2\u010b\u011b\5\36\20\2\u010c\u010d\7\36\2\2\u010d\u010e\7\35"+
		"\2\2\u010e\u010f\7\5\2\2\u010f\u0110\5\20\t\2\u0110\u0111\7\6\2\2\u0111"+
		"\u011b\3\2\2\2\u0112\u0113\7\37\2\2\u0113\u0114\7\35\2\2\u0114\u0115\7"+
		"\5\2\2\u0115\u0116\5\20\t\2\u0116\u0117\7\6\2\2\u0117\u011b\3\2\2\2\u0118"+
		"\u0119\7\37\2\2\u0119\u011b\5\36\20\2\u011a\u010a\3\2\2\2\u011a\u010c"+
		"\3\2\2\2\u011a\u0112\3\2\2\2\u011a\u0118\3\2\2\2\u011b\35\3\2\2\2\u011c"+
		"\u011e\5\60\31\2\u011d\u011c\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u011f\3"+
		"\2\2\2\u011f\u0120\5 \21\2\u0120\37\3\2\2\2\u0121\u0122\7\63\2\2\u0122"+
		"\u0123\5\"\22\2\u0123!\3\2\2\2\u0124\u0125\7 \2\2\u0125\u012a\5\60\31"+
		"\2\u0126\u0127\7\7\2\2\u0127\u0129\5\60\31\2\u0128\u0126\3\2\2\2\u0129"+
		"\u012c\3\2\2\2\u012a\u0128\3\2\2\2\u012a\u012b\3\2\2\2\u012b\u012d\3\2"+
		"\2\2\u012c\u012a\3\2\2\2\u012d\u012e\7!\2\2\u012e\u0130\3\2\2\2\u012f"+
		"\u0124\3\2\2\2\u012f\u0130\3\2\2\2\u0130#\3\2\2\2\u0131\u0132\5 \21\2"+
		"\u0132\u0133\5\66\34\2\u0133\u0134\5\60\31\2\u0134\u013c\3\2\2\2\u0135"+
		"\u0136\5\60\31\2\u0136\u0137\7\"\2\2\u0137\u0138\5 \21\2\u0138\u0139\7"+
		"\"\2\2\u0139\u013a\5\60\31\2\u013a\u013c\3\2\2\2\u013b\u0131\3\2\2\2\u013b"+
		"\u0135\3\2\2\2\u013c%\3\2\2\2\u013d\u013f\7#\2\2\u013e\u0140\5\20\t\2"+
		"\u013f\u013e\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0142"+
		"\3\2\2\2\u0142\'\3\2\2\2\u0143\u0145\7$\2\2\u0144\u0146\5\20\t\2\u0145"+
		"\u0144\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u0145\3\2\2\2\u0147\u0148\3\2"+
		"\2\2\u0148)\3\2\2\2\u0149\u014b\7%\2\2\u014a\u014c\5\20\t\2\u014b\u014a"+
		"\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u014b\3\2\2\2\u014d\u014e\3\2\2\2\u014e"+
		"+\3\2\2\2\u014f\u0151\7&\2\2\u0150\u0152\5\20\t\2\u0151\u0150\3\2\2\2"+
		"\u0152\u0153\3\2\2\2\u0153\u0151\3\2\2\2\u0153\u0154\3\2\2\2\u0154-\3"+
		"\2\2\2\u0155\u0157\7\'\2\2\u0156\u0158\5\20\t\2\u0157\u0156\3\2\2\2\u0158"+
		"\u0159\3\2\2\2\u0159\u0157\3\2\2\2\u0159\u015a\3\2\2\2\u015a/\3\2\2\2"+
		"\u015b\u015c\b\31\1\2\u015c\u015d\7(\2\2\u015d\u016b\5\60\31\17\u015e"+
		"\u015f\7)\2\2\u015f\u016b\5\60\31\16\u0160\u0161\t\3\2\2\u0161\u016b\5"+
		"\60\31\r\u0162\u0163\7\5\2\2\u0163\u0164\5\60\31\2\u0164\u0165\7\6\2\2"+
		"\u0165\u016b\3\2\2\2\u0166\u016b\5\62\32\2\u0167\u016b\7\63\2\2\u0168"+
		"\u016b\7\65\2\2\u0169\u016b\7\64\2\2\u016a\u015b\3\2\2\2\u016a\u015e\3"+
		"\2\2\2\u016a\u0160\3\2\2\2\u016a\u0162\3\2\2\2\u016a\u0166\3\2\2\2\u016a"+
		"\u0167\3\2\2\2\u016a\u0168\3\2\2\2\u016a\u0169\3\2\2\2\u016b\u017d\3\2"+
		"\2\2\u016c\u016d\f\f\2\2\u016d\u016e\t\4\2\2\u016e\u017c\5\60\31\r\u016f"+
		"\u0170\f\13\2\2\u0170\u0171\t\5\2\2\u0171\u017c\5\60\31\f\u0172\u0173"+
		"\f\n\2\2\u0173\u0174\t\6\2\2\u0174\u017c\5\60\31\13\u0175\u0176\f\t\2"+
		"\2\u0176\u0177\t\7\2\2\u0177\u017c\5\60\31\n\u0178\u0179\f\b\2\2\u0179"+
		"\u017a\t\b\2\2\u017a\u017c\5\60\31\t\u017b\u016c\3\2\2\2\u017b\u016f\3"+
		"\2\2\2\u017b\u0172\3\2\2\2\u017b\u0175\3\2\2\2\u017b\u0178\3\2\2\2\u017c"+
		"\u017f\3\2\2\2\u017d\u017b\3\2\2\2\u017d\u017e\3\2\2\2\u017e\61\3\2\2"+
		"\2\u017f\u017d\3\2\2\2\u0180\u0181\7\63\2\2\u0181\u0183\7\5\2\2\u0182"+
		"\u0184\5\64\33\2\u0183\u0182\3\2\2\2\u0183\u0184\3\2\2\2\u0184\u0185\3"+
		"\2\2\2\u0185\u0186\7\6\2\2\u0186\63\3\2\2\2\u0187\u018c\5\60\31\2\u0188"+
		"\u0189\7\7\2\2\u0189\u018b\5\60\31\2\u018a\u0188\3\2\2\2\u018b\u018e\3"+
		"\2\2\2\u018c\u018a\3\2\2\2\u018c\u018d\3\2\2\2\u018d\65\3\2\2\2\u018e"+
		"\u018c\3\2\2\2\u018f\u0190\t\t\2\2\u0190\67\3\2\2\2(9>ADGJP[^ejt{\u0080"+
		"\u008b\u00a0\u00b4\u00e5\u00e7\u00ee\u00fb\u0105\u0108\u011a\u011d\u012a"+
		"\u012f\u013b\u0141\u0147\u014d\u0153\u0159\u016a\u017b\u017d\u0183\u018c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}