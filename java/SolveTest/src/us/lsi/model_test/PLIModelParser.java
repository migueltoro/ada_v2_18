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
		T__45=46, T__46=47, T__47=48, T__48=49, ID=50, INT=51, DOUBLE=52, WS=53;
	public static final int
		RULE_model = 0, RULE_head = 1, RULE_declaration = 2, RULE_formal_parameters = 3, 
		RULE_formal_parameter = 4, RULE_goal = 5, RULE_constraints = 6, RULE_list = 7, 
		RULE_g_list = 8, RULE_indx = 9, RULE_indexed_elem = 10, RULE_constraint = 11, 
		RULE_pair = 12, RULE_generate_exp = 13, RULE_s_factor = 14, RULE_factor = 15, 
		RULE_var_id = 16, RULE_var_ids = 17, RULE_index_var_id = 18, RULE_bound = 19, 
		RULE_bounds = 20, RULE_bin_vars = 21, RULE_int_vars = 22, RULE_free_vars = 23, 
		RULE_semi_continuous_vars = 24, RULE_exp = 25, RULE_call_function = 26, 
		RULE_exps = 27, RULE_rel_op = 28;
	private static String[] makeRuleNames() {
		return new String[] {
			"model", "head", "declaration", "formal_parameters", "formal_parameter", 
			"goal", "constraints", "list", "g_list", "indx", "indexed_elem", "constraint", 
			"pair", "generate_exp", "s_factor", "factor", "var_id", "var_ids", "index_var_id", 
			"bound", "bounds", "bin_vars", "int_vars", "free_vars", "semi_continuous_vars", 
			"exp", "call_function", "exps", "rel_op"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'head section'", "'='", "'('", "')'", "','", "'goal section'", 
			"'min'", "'max'", "'constraints section'", "'|'", "'in'", "'..'", "'->'", 
			"'=='", "'or'", "'=>'", "'!='", "'allDifferent'", "'allDifferentInValues'", 
			"';'", "'MAX'", "'MIN'", "'OR'", "'AND'", "'ABS'", "'PWL'", "':'", "'sum'", 
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
			null, null, "ID", "INT", "DOUBLE", "WS"
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
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(58);
				head();
				}
			}

			setState(61);
			goal();
			setState(62);
			constraints();
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__33) {
				{
				setState(63);
				bounds();
				}
			}

			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__34) {
				{
				setState(66);
				bin_vars();
				}
			}

			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__35) {
				{
				setState(69);
				int_vars();
				}
			}

			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__36) {
				{
				setState(72);
				free_vars();
				}
			}

			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__37) {
				{
				setState(75);
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
			setState(78);
			match(T__0);
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(79);
				declaration();
				}
				}
				setState(84);
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
			setState(96);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new VarDeclarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(85);
				((VarDeclarContext)_localctx).type = match(ID);
				setState(86);
				((VarDeclarContext)_localctx).name = match(ID);
				setState(87);
				match(T__1);
				setState(88);
				((VarDeclarContext)_localctx).val = exp(0);
				}
				break;
			case 2:
				_localctx = new FunDeclarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				((FunDeclarContext)_localctx).type = match(ID);
				setState(90);
				((FunDeclarContext)_localctx).name = match(ID);
				setState(91);
				match(T__2);
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(92);
					formal_parameters();
					}
				}

				setState(95);
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
			setState(98);
			formal_parameter();
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(99);
				match(T__4);
				setState(100);
				formal_parameter();
				}
				}
				setState(105);
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
			setState(106);
			((Formal_parameterContext)_localctx).type = match(ID);
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(107);
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
			setState(110);
			match(T__5);
			setState(111);
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
			setState(112);
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
			setState(114);
			match(T__8);
			setState(116); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(115);
				list();
				}
				}
				setState(118); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
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
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			indexed_elem();
			setState(125);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(121);
					match(T__4);
					setState(122);
					indx();
					}
					} 
				}
				setState(127);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(130);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(128);
				match(T__9);
				setState(129);
				exp(0);
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

	public static class G_listContext extends ParserRuleContext {
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public Var_idsContext var_ids() {
			return getRuleContext(Var_idsContext.class,0);
		}
		public ExpsContext exps() {
			return getRuleContext(ExpsContext.class,0);
		}
		public G_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_g_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitG_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final G_listContext g_list() throws RecognitionException {
		G_listContext _localctx = new G_listContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_g_list);
		try {
			setState(135);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				list();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(133);
				var_ids();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(134);
				exps();
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
		enterRule(_localctx, 18, RULE_indx);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			((IndxContext)_localctx).index_name = match(ID);
			setState(138);
			match(T__10);
			setState(139);
			((IndxContext)_localctx).li = exp(0);
			setState(140);
			match(T__11);
			setState(141);
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
		enterRule(_localctx, 20, RULE_indexed_elem);
		try {
			setState(146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				constraint(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				bound();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(145);
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
		public Var_idsContext vars;
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public Var_idsContext var_ids() {
			return getRuleContext(Var_idsContext.class,0);
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
		public Var_idsContext vars;
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public Var_idsContext var_ids() {
			return getRuleContext(Var_idsContext.class,0);
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
		public Var_idsContext vars;
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public Var_idsContext var_ids() {
			return getRuleContext(Var_idsContext.class,0);
		}
		public MinConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitMinConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualsConstraintContext extends ConstraintContext {
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public Generate_expContext generate_exp() {
			return getRuleContext(Generate_expContext.class,0);
		}
		public EqualsConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitEqualsConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValueInValuesConstraintContext extends ConstraintContext {
		public Var_idContext var;
		public ListContext values;
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public ValueInValuesConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitValueInValuesConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AllInValuesConstraintContext extends ConstraintContext {
		public G_listContext vars;
		public G_listContext values;
		public List<G_listContext> g_list() {
			return getRuleContexts(G_listContext.class);
		}
		public G_listContext g_list(int i) {
			return getRuleContext(G_listContext.class,i);
		}
		public AllInValuesConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitAllInValuesConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AllDifferentValuesConstraintContext extends ConstraintContext {
		public G_listContext vars;
		public G_listContext g_list() {
			return getRuleContext(G_listContext.class,0);
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
	public static class IndicatorConstraintContext extends ConstraintContext {
		public Token values;
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public TerminalNode INT() { return getToken(PLIModelParser.INT, 0); }
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
		public Var_idsContext vars;
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public Var_idsContext var_ids() {
			return getRuleContext(Var_idsContext.class,0);
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
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_constraint, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				_localctx = new AtomConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(149);
				generate_exp();
				setState(150);
				rel_op();
				setState(151);
				exp(0);
				}
				break;
			case 2:
				{
				_localctx = new IndicatorConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(153);
				var_id();
				setState(154);
				match(T__1);
				setState(155);
				((IndicatorConstraintContext)_localctx).values = match(INT);
				setState(156);
				match(T__12);
				setState(157);
				constraint(14);
				}
				break;
			case 3:
				{
				_localctx = new EqualsConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(159);
				var_id();
				setState(160);
				match(T__13);
				setState(161);
				generate_exp();
				}
				break;
			case 4:
				{
				_localctx = new OrConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(163);
				match(T__14);
				setState(164);
				match(T__2);
				setState(165);
				rel_op();
				setState(166);
				((OrConstraintContext)_localctx).n = match(INT);
				setState(167);
				match(T__4);
				setState(168);
				constraint(0);
				setState(171); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(169);
					match(T__9);
					setState(170);
					constraint(0);
					}
					}
					setState(173); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__9 );
				setState(175);
				match(T__3);
				}
				break;
			case 5:
				{
				_localctx = new DifferentValueConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(177);
				((DifferentValueConstraintContext)_localctx).left = var_id();
				setState(178);
				match(T__16);
				setState(179);
				((DifferentValueConstraintContext)_localctx).right = var_id();
				}
				break;
			case 6:
				{
				_localctx = new AllDifferentValuesConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(181);
				match(T__17);
				setState(182);
				match(T__2);
				setState(183);
				((AllDifferentValuesConstraintContext)_localctx).vars = g_list();
				setState(184);
				match(T__3);
				}
				break;
			case 7:
				{
				_localctx = new AllInValuesConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(186);
				match(T__18);
				setState(187);
				match(T__2);
				setState(188);
				((AllInValuesConstraintContext)_localctx).vars = g_list();
				setState(189);
				match(T__19);
				setState(190);
				((AllInValuesConstraintContext)_localctx).values = g_list();
				setState(191);
				match(T__3);
				}
				break;
			case 8:
				{
				_localctx = new ValueInValuesConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(193);
				((ValueInValuesConstraintContext)_localctx).var = var_id();
				setState(194);
				match(T__10);
				setState(195);
				((ValueInValuesConstraintContext)_localctx).values = list();
				}
				break;
			case 9:
				{
				_localctx = new MaxConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(197);
				((MaxConstraintContext)_localctx).left = var_id();
				setState(198);
				match(T__1);
				setState(199);
				match(T__20);
				setState(200);
				match(T__2);
				setState(201);
				((MaxConstraintContext)_localctx).vars = var_ids();
				setState(202);
				match(T__3);
				}
				break;
			case 10:
				{
				_localctx = new MinConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(204);
				((MinConstraintContext)_localctx).left = var_id();
				setState(205);
				match(T__1);
				setState(206);
				match(T__21);
				setState(207);
				match(T__2);
				setState(208);
				((MinConstraintContext)_localctx).vars = var_ids();
				setState(209);
				match(T__3);
				}
				break;
			case 11:
				{
				_localctx = new OrBinConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(211);
				((OrBinConstraintContext)_localctx).left = var_id();
				setState(212);
				match(T__1);
				setState(213);
				match(T__22);
				setState(214);
				match(T__2);
				setState(215);
				((OrBinConstraintContext)_localctx).vars = var_ids();
				setState(216);
				match(T__3);
				}
				break;
			case 12:
				{
				_localctx = new AndBinConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(218);
				((AndBinConstraintContext)_localctx).left = var_id();
				setState(219);
				match(T__1);
				setState(220);
				match(T__23);
				setState(221);
				match(T__2);
				setState(222);
				((AndBinConstraintContext)_localctx).vars = var_ids();
				setState(223);
				match(T__3);
				}
				break;
			case 13:
				{
				_localctx = new AbsConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(225);
				((AbsConstraintContext)_localctx).left = var_id();
				setState(226);
				match(T__1);
				setState(227);
				match(T__24);
				setState(228);
				match(T__2);
				setState(229);
				((AbsConstraintContext)_localctx).right = var_id();
				setState(230);
				match(T__3);
				}
				break;
			case 14:
				{
				_localctx = new PiecewiseConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(232);
				((PiecewiseConstraintContext)_localctx).left = var_id();
				setState(233);
				match(T__1);
				setState(234);
				match(T__25);
				setState(235);
				match(T__2);
				setState(236);
				((PiecewiseConstraintContext)_localctx).right = var_id();
				setState(237);
				match(T__3);
				setState(238);
				match(T__26);
				setState(240); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(239);
						((PiecewiseConstraintContext)_localctx).data = pair();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(242); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(251);
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
					setState(246);
					if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
					setState(247);
					match(T__15);
					setState(248);
					((ImplyConstraintContext)_localctx).right = constraint(12);
					}
					} 
				}
				setState(253);
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
		enterRule(_localctx, 24, RULE_pair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(T__2);
			setState(255);
			match(INT);
			setState(256);
			match(T__4);
			setState(257);
			match(INT);
			setState(258);
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
		enterRule(_localctx, 26, RULE_generate_exp);
		try {
			int _alt;
			setState(277);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case T__28:
			case T__29:
			case T__38:
			case T__39:
			case T__40:
			case ID:
			case INT:
			case DOUBLE:
				_localctx = new FactorGenerateExpContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(260);
				factor();
				setState(264);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(261);
						s_factor();
						}
						} 
					}
					setState(266);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				}
				}
				break;
			case T__27:
				_localctx = new SumGenerateExpContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(267);
				match(T__27);
				setState(268);
				match(T__2);
				setState(269);
				list();
				setState(270);
				match(T__3);
				setState(274);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(271);
						s_factor();
						}
						} 
					}
					setState(276);
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
		enterRule(_localctx, 28, RULE_s_factor);
		try {
			setState(295);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				_localctx = new PlusFactorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(279);
				match(T__28);
				setState(280);
				factor();
				}
				break;
			case 2:
				_localctx = new MinusFactorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(281);
				match(T__29);
				setState(282);
				factor();
				}
				break;
			case 3:
				_localctx = new PlusSumContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(283);
				match(T__28);
				setState(284);
				match(T__27);
				setState(285);
				match(T__2);
				setState(286);
				list();
				setState(287);
				match(T__3);
				}
				break;
			case 4:
				_localctx = new MinusSumContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(289);
				match(T__29);
				setState(290);
				match(T__27);
				setState(291);
				match(T__2);
				setState(292);
				list();
				setState(293);
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
	public static class ExpFactorContext extends FactorContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ExpFactorContext(FactorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitExpFactor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarIdFactorContext extends FactorContext {
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
		}
		public VarIdFactorContext(FactorContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitVarIdFactor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarFactorContext extends FactorContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public Var_idContext var_id() {
			return getRuleContext(Var_idContext.class,0);
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
		enterRule(_localctx, 30, RULE_factor);
		try {
			setState(302);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				_localctx = new VarFactorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(297);
				exp(0);
				setState(298);
				var_id();
				}
				break;
			case 2:
				_localctx = new ExpFactorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(300);
				exp(0);
				}
				break;
			case 3:
				_localctx = new VarIdFactorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(301);
				var_id();
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

	public static class Var_idContext extends ParserRuleContext {
		public Token name;
		public TerminalNode ID() { return getToken(PLIModelParser.ID, 0); }
		public Index_var_idContext index_var_id() {
			return getRuleContext(Index_var_idContext.class,0);
		}
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
		enterRule(_localctx, 32, RULE_var_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			((Var_idContext)_localctx).name = match(ID);
			setState(305);
			match(T__30);
			setState(307);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__28) | (1L << T__29) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0)) {
				{
				setState(306);
				index_var_id();
				}
			}

			setState(309);
			match(T__31);
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

	public static class Var_idsContext extends ParserRuleContext {
		public List<Var_idContext> var_id() {
			return getRuleContexts(Var_idContext.class);
		}
		public Var_idContext var_id(int i) {
			return getRuleContext(Var_idContext.class,i);
		}
		public Var_idsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_ids; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitVar_ids(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_idsContext var_ids() throws RecognitionException {
		Var_idsContext _localctx = new Var_idsContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_var_ids);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
			var_id();
			{
			setState(312);
			match(T__4);
			setState(313);
			var_id();
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
		enterRule(_localctx, 36, RULE_index_var_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			exp(0);
			setState(320);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(316);
				match(T__4);
				setState(317);
				exp(0);
				}
				}
				setState(322);
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
		enterRule(_localctx, 38, RULE_bound);
		try {
			setState(333);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				_localctx = new OneSideBoundContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(323);
				((OneSideBoundContext)_localctx).name = var_id();
				setState(324);
				((OneSideBoundContext)_localctx).op = rel_op();
				setState(325);
				exp(0);
				}
				break;
			case 2:
				_localctx = new TwoSideBoundContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(327);
				((TwoSideBoundContext)_localctx).li = exp(0);
				setState(328);
				match(T__32);
				setState(329);
				((TwoSideBoundContext)_localctx).name = var_id();
				setState(330);
				match(T__32);
				setState(331);
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
		enterRule(_localctx, 40, RULE_bounds);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			match(T__33);
			setState(337); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(336);
				list();
				}
				}
				setState(339); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
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
		enterRule(_localctx, 42, RULE_bin_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
			match(T__34);
			setState(343); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(342);
				list();
				}
				}
				setState(345); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
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
		enterRule(_localctx, 44, RULE_int_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(347);
			match(T__35);
			setState(349); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(348);
				list();
				}
				}
				setState(351); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
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
		enterRule(_localctx, 46, RULE_free_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			match(T__36);
			setState(355); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(354);
				list();
				}
				}
				setState(357); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
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
		enterRule(_localctx, 48, RULE_semi_continuous_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(359);
			match(T__37);
			setState(361); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(360);
				list();
				}
				}
				setState(363); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0) );
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
		int _startState = 50;
		enterRecursionRule(_localctx, 50, RULE_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(380);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				_localctx = new UnaryOpExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(366);
				((UnaryOpExprContext)_localctx).op = match(T__38);
				setState(367);
				((UnaryOpExprContext)_localctx).right = exp(13);
				}
				break;
			case 2:
				{
				_localctx = new UnaryOpExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(368);
				((UnaryOpExprContext)_localctx).op = match(T__39);
				setState(369);
				((UnaryOpExprContext)_localctx).right = exp(12);
				}
				break;
			case 3:
				{
				_localctx = new UnaryOpExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(370);
				((UnaryOpExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__28) | (1L << T__29) | (1L << T__40))) != 0)) ) {
					((UnaryOpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(371);
				((UnaryOpExprContext)_localctx).right = exp(11);
				}
				break;
			case 4:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(372);
				match(T__2);
				setState(373);
				exp(0);
				setState(374);
				match(T__3);
				}
				break;
			case 5:
				{
				_localctx = new FunExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(376);
				call_function();
				}
				break;
			case 6:
				{
				_localctx = new IdExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(377);
				((IdExprContext)_localctx).id = match(ID);
				}
				break;
			case 7:
				{
				_localctx = new DoubleExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(378);
				match(DOUBLE);
				}
				break;
			case 8:
				{
				_localctx = new IntExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(379);
				match(INT);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(399);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(397);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
					case 1:
						{
						_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(382);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(383);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__41) | (1L << T__42) | (1L << T__43))) != 0)) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(384);
						((OpExprContext)_localctx).right = exp(11);
						}
						break;
					case 2:
						{
						_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(385);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(386);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__28 || _la==T__29) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(387);
						((OpExprContext)_localctx).right = exp(10);
						}
						break;
					case 3:
						{
						_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(388);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(389);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__32) | (1L << T__44) | (1L << T__45) | (1L << T__46))) != 0)) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(390);
						((OpExprContext)_localctx).right = exp(9);
						}
						break;
					case 4:
						{
						_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(391);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(392);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__1 || _la==T__16) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(393);
						((OpExprContext)_localctx).right = exp(8);
						}
						break;
					case 5:
						{
						_localctx = new OpExprContext(new ExpContext(_parentctx, _parentState));
						((OpExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(394);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(395);
						((OpExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__47 || _la==T__48) ) {
							((OpExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(396);
						((OpExprContext)_localctx).right = exp(7);
						}
						break;
					}
					} 
				}
				setState(401);
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
		public ExpsContext exps() {
			return getRuleContext(ExpsContext.class,0);
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
		enterRule(_localctx, 52, RULE_call_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			((Call_functionContext)_localctx).name = match(ID);
			setState(403);
			match(T__2);
			setState(405);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__28) | (1L << T__29) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << ID) | (1L << INT) | (1L << DOUBLE))) != 0)) {
				{
				setState(404);
				exps();
				}
			}

			setState(407);
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

	public static class ExpsContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ExpsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exps; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PLIModelVisitor ) return ((PLIModelVisitor<? extends T>)visitor).visitExps(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpsContext exps() throws RecognitionException {
		ExpsContext _localctx = new ExpsContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_exps);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			exp(0);
			setState(414);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(410);
				match(T__4);
				setState(411);
				exp(0);
				}
				}
				setState(416);
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
		enterRule(_localctx, 56, RULE_rel_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(417);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__32) | (1L << T__44) | (1L << T__45) | (1L << T__46))) != 0)) ) {
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
		case 11:
			return constraint_sempred((ConstraintContext)_localctx, predIndex);
		case 25:
			return exp_sempred((ExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean constraint_sempred(ConstraintContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\67\u01a6\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\5\2>\n\2\3\2\3"+
		"\2\3\2\5\2C\n\2\3\2\5\2F\n\2\3\2\5\2I\n\2\3\2\5\2L\n\2\3\2\5\2O\n\2\3"+
		"\3\3\3\7\3S\n\3\f\3\16\3V\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4`\n"+
		"\4\3\4\5\4c\n\4\3\5\3\5\3\5\7\5h\n\5\f\5\16\5k\13\5\3\6\3\6\5\6o\n\6\3"+
		"\7\3\7\3\7\3\7\3\b\3\b\6\bw\n\b\r\b\16\bx\3\t\3\t\3\t\7\t~\n\t\f\t\16"+
		"\t\u0081\13\t\3\t\3\t\5\t\u0085\n\t\3\n\3\n\3\n\5\n\u008a\n\n\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\5\f\u0095\n\f\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\6"+
		"\r\u00ae\n\r\r\r\16\r\u00af\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\6\r\u00f3\n\r\r\r\16\r\u00f4\5\r\u00f7\n\r\3\r\3\r\3\r\7\r\u00fc"+
		"\n\r\f\r\16\r\u00ff\13\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\7\17"+
		"\u0109\n\17\f\17\16\17\u010c\13\17\3\17\3\17\3\17\3\17\3\17\7\17\u0113"+
		"\n\17\f\17\16\17\u0116\13\17\5\17\u0118\n\17\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u012a\n\20"+
		"\3\21\3\21\3\21\3\21\3\21\5\21\u0131\n\21\3\22\3\22\3\22\5\22\u0136\n"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\7\24\u0141\n\24\f\24"+
		"\16\24\u0144\13\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5"+
		"\25\u0150\n\25\3\26\3\26\6\26\u0154\n\26\r\26\16\26\u0155\3\27\3\27\6"+
		"\27\u015a\n\27\r\27\16\27\u015b\3\30\3\30\6\30\u0160\n\30\r\30\16\30\u0161"+
		"\3\31\3\31\6\31\u0166\n\31\r\31\16\31\u0167\3\32\3\32\6\32\u016c\n\32"+
		"\r\32\16\32\u016d\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3"+
		"\33\3\33\3\33\3\33\3\33\5\33\u017f\n\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\7\33\u0190\n\33\f\33\16"+
		"\33\u0193\13\33\3\34\3\34\3\34\5\34\u0198\n\34\3\34\3\34\3\35\3\35\3\35"+
		"\7\35\u019f\n\35\f\35\16\35\u01a2\13\35\3\36\3\36\3\36\2\4\30\64\37\2"+
		"\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:\2\n\3\2\t"+
		"\n\4\2\37 ++\3\2,.\3\2\37 \4\2##/\61\4\2\4\4\23\23\3\2\62\63\5\2\4\4#"+
		"#/\61\2\u01c8\2=\3\2\2\2\4P\3\2\2\2\6b\3\2\2\2\bd\3\2\2\2\nl\3\2\2\2\f"+
		"p\3\2\2\2\16t\3\2\2\2\20z\3\2\2\2\22\u0089\3\2\2\2\24\u008b\3\2\2\2\26"+
		"\u0094\3\2\2\2\30\u00f6\3\2\2\2\32\u0100\3\2\2\2\34\u0117\3\2\2\2\36\u0129"+
		"\3\2\2\2 \u0130\3\2\2\2\"\u0132\3\2\2\2$\u0139\3\2\2\2&\u013d\3\2\2\2"+
		"(\u014f\3\2\2\2*\u0151\3\2\2\2,\u0157\3\2\2\2.\u015d\3\2\2\2\60\u0163"+
		"\3\2\2\2\62\u0169\3\2\2\2\64\u017e\3\2\2\2\66\u0194\3\2\2\28\u019b\3\2"+
		"\2\2:\u01a3\3\2\2\2<>\5\4\3\2=<\3\2\2\2=>\3\2\2\2>?\3\2\2\2?@\5\f\7\2"+
		"@B\5\16\b\2AC\5*\26\2BA\3\2\2\2BC\3\2\2\2CE\3\2\2\2DF\5,\27\2ED\3\2\2"+
		"\2EF\3\2\2\2FH\3\2\2\2GI\5.\30\2HG\3\2\2\2HI\3\2\2\2IK\3\2\2\2JL\5\60"+
		"\31\2KJ\3\2\2\2KL\3\2\2\2LN\3\2\2\2MO\5\62\32\2NM\3\2\2\2NO\3\2\2\2O\3"+
		"\3\2\2\2PT\7\3\2\2QS\5\6\4\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2U"+
		"\5\3\2\2\2VT\3\2\2\2WX\7\64\2\2XY\7\64\2\2YZ\7\4\2\2Zc\5\64\33\2[\\\7"+
		"\64\2\2\\]\7\64\2\2]_\7\5\2\2^`\5\b\5\2_^\3\2\2\2_`\3\2\2\2`a\3\2\2\2"+
		"ac\7\6\2\2bW\3\2\2\2b[\3\2\2\2c\7\3\2\2\2di\5\n\6\2ef\7\7\2\2fh\5\n\6"+
		"\2ge\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2j\t\3\2\2\2ki\3\2\2\2ln\7\64"+
		"\2\2mo\7\64\2\2nm\3\2\2\2no\3\2\2\2o\13\3\2\2\2pq\7\b\2\2qr\t\2\2\2rs"+
		"\5\34\17\2s\r\3\2\2\2tv\7\13\2\2uw\5\20\t\2vu\3\2\2\2wx\3\2\2\2xv\3\2"+
		"\2\2xy\3\2\2\2y\17\3\2\2\2z\177\5\26\f\2{|\7\7\2\2|~\5\24\13\2}{\3\2\2"+
		"\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\u0084\3\2\2\2\u0081"+
		"\177\3\2\2\2\u0082\u0083\7\f\2\2\u0083\u0085\5\64\33\2\u0084\u0082\3\2"+
		"\2\2\u0084\u0085\3\2\2\2\u0085\21\3\2\2\2\u0086\u008a\5\20\t\2\u0087\u008a"+
		"\5$\23\2\u0088\u008a\58\35\2\u0089\u0086\3\2\2\2\u0089\u0087\3\2\2\2\u0089"+
		"\u0088\3\2\2\2\u008a\23\3\2\2\2\u008b\u008c\7\64\2\2\u008c\u008d\7\r\2"+
		"\2\u008d\u008e\5\64\33\2\u008e\u008f\7\16\2\2\u008f\u0090\5\64\33\2\u0090"+
		"\25\3\2\2\2\u0091\u0095\5\30\r\2\u0092\u0095\5(\25\2\u0093\u0095\5\34"+
		"\17\2\u0094\u0091\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0093\3\2\2\2\u0095"+
		"\27\3\2\2\2\u0096\u0097\b\r\1\2\u0097\u0098\5\34\17\2\u0098\u0099\5:\36"+
		"\2\u0099\u009a\5\64\33\2\u009a\u00f7\3\2\2\2\u009b\u009c\5\"\22\2\u009c"+
		"\u009d\7\4\2\2\u009d\u009e\7\65\2\2\u009e\u009f\7\17\2\2\u009f\u00a0\5"+
		"\30\r\20\u00a0\u00f7\3\2\2\2\u00a1\u00a2\5\"\22\2\u00a2\u00a3\7\20\2\2"+
		"\u00a3\u00a4\5\34\17\2\u00a4\u00f7\3\2\2\2\u00a5\u00a6\7\21\2\2\u00a6"+
		"\u00a7\7\5\2\2\u00a7\u00a8\5:\36\2\u00a8\u00a9\7\65\2\2\u00a9\u00aa\7"+
		"\7\2\2\u00aa\u00ad\5\30\r\2\u00ab\u00ac\7\f\2\2\u00ac\u00ae\5\30\r\2\u00ad"+
		"\u00ab\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00ad\3\2\2\2\u00af\u00b0\3\2"+
		"\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\7\6\2\2\u00b2\u00f7\3\2\2\2\u00b3"+
		"\u00b4\5\"\22\2\u00b4\u00b5\7\23\2\2\u00b5\u00b6\5\"\22\2\u00b6\u00f7"+
		"\3\2\2\2\u00b7\u00b8\7\24\2\2\u00b8\u00b9\7\5\2\2\u00b9\u00ba\5\22\n\2"+
		"\u00ba\u00bb\7\6\2\2\u00bb\u00f7\3\2\2\2\u00bc\u00bd\7\25\2\2\u00bd\u00be"+
		"\7\5\2\2\u00be\u00bf\5\22\n\2\u00bf\u00c0\7\26\2\2\u00c0\u00c1\5\22\n"+
		"\2\u00c1\u00c2\7\6\2\2\u00c2\u00f7\3\2\2\2\u00c3\u00c4\5\"\22\2\u00c4"+
		"\u00c5\7\r\2\2\u00c5\u00c6\5\20\t\2\u00c6\u00f7\3\2\2\2\u00c7\u00c8\5"+
		"\"\22\2\u00c8\u00c9\7\4\2\2\u00c9\u00ca\7\27\2\2\u00ca\u00cb\7\5\2\2\u00cb"+
		"\u00cc\5$\23\2\u00cc\u00cd\7\6\2\2\u00cd\u00f7\3\2\2\2\u00ce\u00cf\5\""+
		"\22\2\u00cf\u00d0\7\4\2\2\u00d0\u00d1\7\30\2\2\u00d1\u00d2\7\5\2\2\u00d2"+
		"\u00d3\5$\23\2\u00d3\u00d4\7\6\2\2\u00d4\u00f7\3\2\2\2\u00d5\u00d6\5\""+
		"\22\2\u00d6\u00d7\7\4\2\2\u00d7\u00d8\7\31\2\2\u00d8\u00d9\7\5\2\2\u00d9"+
		"\u00da\5$\23\2\u00da\u00db\7\6\2\2\u00db\u00f7\3\2\2\2\u00dc\u00dd\5\""+
		"\22\2\u00dd\u00de\7\4\2\2\u00de\u00df\7\32\2\2\u00df\u00e0\7\5\2\2\u00e0"+
		"\u00e1\5$\23\2\u00e1\u00e2\7\6\2\2\u00e2\u00f7\3\2\2\2\u00e3\u00e4\5\""+
		"\22\2\u00e4\u00e5\7\4\2\2\u00e5\u00e6\7\33\2\2\u00e6\u00e7\7\5\2\2\u00e7"+
		"\u00e8\5\"\22\2\u00e8\u00e9\7\6\2\2\u00e9\u00f7\3\2\2\2\u00ea\u00eb\5"+
		"\"\22\2\u00eb\u00ec\7\4\2\2\u00ec\u00ed\7\34\2\2\u00ed\u00ee\7\5\2\2\u00ee"+
		"\u00ef\5\"\22\2\u00ef\u00f0\7\6\2\2\u00f0\u00f2\7\35\2\2\u00f1\u00f3\5"+
		"\32\16\2\u00f2\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f4"+
		"\u00f5\3\2\2\2\u00f5\u00f7\3\2\2\2\u00f6\u0096\3\2\2\2\u00f6\u009b\3\2"+
		"\2\2\u00f6\u00a1\3\2\2\2\u00f6\u00a5\3\2\2\2\u00f6\u00b3\3\2\2\2\u00f6"+
		"\u00b7\3\2\2\2\u00f6\u00bc\3\2\2\2\u00f6\u00c3\3\2\2\2\u00f6\u00c7\3\2"+
		"\2\2\u00f6\u00ce\3\2\2\2\u00f6\u00d5\3\2\2\2\u00f6\u00dc\3\2\2\2\u00f6"+
		"\u00e3\3\2\2\2\u00f6\u00ea\3\2\2\2\u00f7\u00fd\3\2\2\2\u00f8\u00f9\f\r"+
		"\2\2\u00f9\u00fa\7\22\2\2\u00fa\u00fc\5\30\r\16\u00fb\u00f8\3\2\2\2\u00fc"+
		"\u00ff\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\31\3\2\2"+
		"\2\u00ff\u00fd\3\2\2\2\u0100\u0101\7\5\2\2\u0101\u0102\7\65\2\2\u0102"+
		"\u0103\7\7\2\2\u0103\u0104\7\65\2\2\u0104\u0105\7\6\2\2\u0105\33\3\2\2"+
		"\2\u0106\u010a\5 \21\2\u0107\u0109\5\36\20\2\u0108\u0107\3\2\2\2\u0109"+
		"\u010c\3\2\2\2\u010a\u0108\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u0118\3\2"+
		"\2\2\u010c\u010a\3\2\2\2\u010d\u010e\7\36\2\2\u010e\u010f\7\5\2\2\u010f"+
		"\u0110\5\20\t\2\u0110\u0114\7\6\2\2\u0111\u0113\5\36\20\2\u0112\u0111"+
		"\3\2\2\2\u0113\u0116\3\2\2\2\u0114\u0112\3\2\2\2\u0114\u0115\3\2\2\2\u0115"+
		"\u0118\3\2\2\2\u0116\u0114\3\2\2\2\u0117\u0106\3\2\2\2\u0117\u010d\3\2"+
		"\2\2\u0118\35\3\2\2\2\u0119\u011a\7\37\2\2\u011a\u012a\5 \21\2\u011b\u011c"+
		"\7 \2\2\u011c\u012a\5 \21\2\u011d\u011e\7\37\2\2\u011e\u011f\7\36\2\2"+
		"\u011f\u0120\7\5\2\2\u0120\u0121\5\20\t\2\u0121\u0122\7\6\2\2\u0122\u012a"+
		"\3\2\2\2\u0123\u0124\7 \2\2\u0124\u0125\7\36\2\2\u0125\u0126\7\5\2\2\u0126"+
		"\u0127\5\20\t\2\u0127\u0128\7\6\2\2\u0128\u012a\3\2\2\2\u0129\u0119\3"+
		"\2\2\2\u0129\u011b\3\2\2\2\u0129\u011d\3\2\2\2\u0129\u0123\3\2\2\2\u012a"+
		"\37\3\2\2\2\u012b\u012c\5\64\33\2\u012c\u012d\5\"\22\2\u012d\u0131\3\2"+
		"\2\2\u012e\u0131\5\64\33\2\u012f\u0131\5\"\22\2\u0130\u012b\3\2\2\2\u0130"+
		"\u012e\3\2\2\2\u0130\u012f\3\2\2\2\u0131!\3\2\2\2\u0132\u0133\7\64\2\2"+
		"\u0133\u0135\7!\2\2\u0134\u0136\5&\24\2\u0135\u0134\3\2\2\2\u0135\u0136"+
		"\3\2\2\2\u0136\u0137\3\2\2\2\u0137\u0138\7\"\2\2\u0138#\3\2\2\2\u0139"+
		"\u013a\5\"\22\2\u013a\u013b\7\7\2\2\u013b\u013c\5\"\22\2\u013c%\3\2\2"+
		"\2\u013d\u0142\5\64\33\2\u013e\u013f\7\7\2\2\u013f\u0141\5\64\33\2\u0140"+
		"\u013e\3\2\2\2\u0141\u0144\3\2\2\2\u0142\u0140\3\2\2\2\u0142\u0143\3\2"+
		"\2\2\u0143\'\3\2\2\2\u0144\u0142\3\2\2\2\u0145\u0146\5\"\22\2\u0146\u0147"+
		"\5:\36\2\u0147\u0148\5\64\33\2\u0148\u0150\3\2\2\2\u0149\u014a\5\64\33"+
		"\2\u014a\u014b\7#\2\2\u014b\u014c\5\"\22\2\u014c\u014d\7#\2\2\u014d\u014e"+
		"\5\64\33\2\u014e\u0150\3\2\2\2\u014f\u0145\3\2\2\2\u014f\u0149\3\2\2\2"+
		"\u0150)\3\2\2\2\u0151\u0153\7$\2\2\u0152\u0154\5\20\t\2\u0153\u0152\3"+
		"\2\2\2\u0154\u0155\3\2\2\2\u0155\u0153\3\2\2\2\u0155\u0156\3\2\2\2\u0156"+
		"+\3\2\2\2\u0157\u0159\7%\2\2\u0158\u015a\5\20\t\2\u0159\u0158\3\2\2\2"+
		"\u015a\u015b\3\2\2\2\u015b\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c-\3"+
		"\2\2\2\u015d\u015f\7&\2\2\u015e\u0160\5\20\t\2\u015f\u015e\3\2\2\2\u0160"+
		"\u0161\3\2\2\2\u0161\u015f\3\2\2\2\u0161\u0162\3\2\2\2\u0162/\3\2\2\2"+
		"\u0163\u0165\7\'\2\2\u0164\u0166\5\20\t\2\u0165\u0164\3\2\2\2\u0166\u0167"+
		"\3\2\2\2\u0167\u0165\3\2\2\2\u0167\u0168\3\2\2\2\u0168\61\3\2\2\2\u0169"+
		"\u016b\7(\2\2\u016a\u016c\5\20\t\2\u016b\u016a\3\2\2\2\u016c\u016d\3\2"+
		"\2\2\u016d\u016b\3\2\2\2\u016d\u016e\3\2\2\2\u016e\63\3\2\2\2\u016f\u0170"+
		"\b\33\1\2\u0170\u0171\7)\2\2\u0171\u017f\5\64\33\17\u0172\u0173\7*\2\2"+
		"\u0173\u017f\5\64\33\16\u0174\u0175\t\3\2\2\u0175\u017f\5\64\33\r\u0176"+
		"\u0177\7\5\2\2\u0177\u0178\5\64\33\2\u0178\u0179\7\6\2\2\u0179\u017f\3"+
		"\2\2\2\u017a\u017f\5\66\34\2\u017b\u017f\7\64\2\2\u017c\u017f\7\66\2\2"+
		"\u017d\u017f\7\65\2\2\u017e\u016f\3\2\2\2\u017e\u0172\3\2\2\2\u017e\u0174"+
		"\3\2\2\2\u017e\u0176\3\2\2\2\u017e\u017a\3\2\2\2\u017e\u017b\3\2\2\2\u017e"+
		"\u017c\3\2\2\2\u017e\u017d\3\2\2\2\u017f\u0191\3\2\2\2\u0180\u0181\f\f"+
		"\2\2\u0181\u0182\t\4\2\2\u0182\u0190\5\64\33\r\u0183\u0184\f\13\2\2\u0184"+
		"\u0185\t\5\2\2\u0185\u0190\5\64\33\f\u0186\u0187\f\n\2\2\u0187\u0188\t"+
		"\6\2\2\u0188\u0190\5\64\33\13\u0189\u018a\f\t\2\2\u018a\u018b\t\7\2\2"+
		"\u018b\u0190\5\64\33\n\u018c\u018d\f\b\2\2\u018d\u018e\t\b\2\2\u018e\u0190"+
		"\5\64\33\t\u018f\u0180\3\2\2\2\u018f\u0183\3\2\2\2\u018f\u0186\3\2\2\2"+
		"\u018f\u0189\3\2\2\2\u018f\u018c\3\2\2\2\u0190\u0193\3\2\2\2\u0191\u018f"+
		"\3\2\2\2\u0191\u0192\3\2\2\2\u0192\65\3\2\2\2\u0193\u0191\3\2\2\2\u0194"+
		"\u0195\7\64\2\2\u0195\u0197\7\5\2\2\u0196\u0198\58\35\2\u0197\u0196\3"+
		"\2\2\2\u0197\u0198\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u019a\7\6\2\2\u019a"+
		"\67\3\2\2\2\u019b\u01a0\5\64\33\2\u019c\u019d\7\7\2\2\u019d\u019f\5\64"+
		"\33\2\u019e\u019c\3\2\2\2\u019f\u01a2\3\2\2\2\u01a0\u019e\3\2\2\2\u01a0"+
		"\u01a1\3\2\2\2\u01a19\3\2\2\2\u01a2\u01a0\3\2\2\2\u01a3\u01a4\t\t\2\2"+
		"\u01a4;\3\2\2\2(=BEHKNT_binx\177\u0084\u0089\u0094\u00af\u00f4\u00f6\u00fd"+
		"\u010a\u0114\u0117\u0129\u0130\u0135\u0142\u014f\u0155\u015b\u0161\u0167"+
		"\u016d\u017e\u018f\u0191\u0197\u01a0";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}