// Generated from KSPParser.g4 by ANTLR 4.7.2
package net.rkoubou.kspcompiler.generated.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KSPParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		EOL=1, MULTI_LINE_DELIMITER=2, Whitespace=3, BlockComment=4, DECLARE=5, 
		CONST=6, POLYPHONIC=7, ON=8, END=9, FUNCTION=10, IF=11, ELSE=12, SELECT=13, 
		CASE=14, TO=15, WHILE=16, CALL=17, PREPROCESSOR_SET_COND=18, PREPROCESSOR_RESET_COND=19, 
		PREPROCESSOR_CODE_IF=20, PREPROCESSOR_CODE_IF_NOT=21, PREPROCESSOR_CODE_END_IF=22, 
		BOOL_GT=23, BOOL_LT=24, BOOL_GE=25, BOOL_LE=26, BOOL_EQ=27, BOOL_NE=28, 
		BOOL_NOT=29, BOOL_AND=30, BOOL_OR=31, ASSIGN=32, PLUS=33, MINUS=34, MUL=35, 
		DIV=36, MOD=37, BIT_AND=38, BIT_OR=39, BIT_NOT=40, STRING_ADD=41, INTEGER_LITERAL=42, 
		STRING_LITERAL=43, IDENTIFIER=44;
	public static final int
		RULE_compilationUnit = 0, RULE_callbackDeclaration = 1, RULE_block = 2, 
		RULE_blockStatement = 3, RULE_statement = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "callbackDeclaration", "block", "blockStatement", 
			"statement"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'declare'", "'const'", "'polyphonic'", 
			"'on'", "'end'", "'function'", "'if'", "'else'", "'select'", "'case'", 
			"'to'", "'while'", "'call'", "'SET_CONDITION'", "'RESET_CONDITION'", 
			"'USE_CODE_IF'", "'USE_CODE_IF_NOT'", "'END_USE_CODE'", "'>'", "'<'", 
			"'>='", "'<='", "'='", "'#'", "'not'", "'and'", "'or'", "':='", "'+'", 
			"'-'", "'*'", "'/'", "'mod'", "'.and.'", "'.or.'", "'.not.'", "'&'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "EOL", "MULTI_LINE_DELIMITER", "Whitespace", "BlockComment", "DECLARE", 
			"CONST", "POLYPHONIC", "ON", "END", "FUNCTION", "IF", "ELSE", "SELECT", 
			"CASE", "TO", "WHILE", "CALL", "PREPROCESSOR_SET_COND", "PREPROCESSOR_RESET_COND", 
			"PREPROCESSOR_CODE_IF", "PREPROCESSOR_CODE_IF_NOT", "PREPROCESSOR_CODE_END_IF", 
			"BOOL_GT", "BOOL_LT", "BOOL_GE", "BOOL_LE", "BOOL_EQ", "BOOL_NE", "BOOL_NOT", 
			"BOOL_AND", "BOOL_OR", "ASSIGN", "PLUS", "MINUS", "MUL", "DIV", "MOD", 
			"BIT_AND", "BIT_OR", "BIT_NOT", "STRING_ADD", "INTEGER_LITERAL", "STRING_LITERAL", 
			"IDENTIFIER"
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
	public String getGrammarFileName() { return "KSPParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public KSPParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class CompilationUnitContext extends ParserRuleContext {
		public List<CallbackDeclarationContext> callbackDeclaration() {
			return getRuleContexts(CallbackDeclarationContext.class);
		}
		public CallbackDeclarationContext callbackDeclaration(int i) {
			return getRuleContext(CallbackDeclarationContext.class,i);
		}
		public List<TerminalNode> EOL() { return getTokens(KSPParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(KSPParser.EOL, i);
		}
		public List<TerminalNode> MULTI_LINE_DELIMITER() { return getTokens(KSPParser.MULTI_LINE_DELIMITER); }
		public TerminalNode MULTI_LINE_DELIMITER(int i) {
			return getToken(KSPParser.MULTI_LINE_DELIMITER, i);
		}
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KSPParserListener ) ((KSPParserListener)listener).enterCompilationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KSPParserListener ) ((KSPParserListener)listener).exitCompilationUnit(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EOL) | (1L << MULTI_LINE_DELIMITER) | (1L << ON))) != 0)) {
				{
				setState(13);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ON:
					{
					setState(10);
					callbackDeclaration();
					}
					break;
				case EOL:
					{
					setState(11);
					match(EOL);
					}
					break;
				case MULTI_LINE_DELIMITER:
					{
					setState(12);
					match(MULTI_LINE_DELIMITER);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(17);
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

	public static class CallbackDeclarationContext extends ParserRuleContext {
		public List<TerminalNode> ON() { return getTokens(KSPParser.ON); }
		public TerminalNode ON(int i) {
			return getToken(KSPParser.ON, i);
		}
		public TerminalNode IDENTIFIER() { return getToken(KSPParser.IDENTIFIER, 0); }
		public TerminalNode EOL() { return getToken(KSPParser.EOL, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode END() { return getToken(KSPParser.END, 0); }
		public List<TerminalNode> MULTI_LINE_DELIMITER() { return getTokens(KSPParser.MULTI_LINE_DELIMITER); }
		public TerminalNode MULTI_LINE_DELIMITER(int i) {
			return getToken(KSPParser.MULTI_LINE_DELIMITER, i);
		}
		public CallbackDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callbackDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KSPParserListener ) ((KSPParserListener)listener).enterCallbackDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KSPParserListener ) ((KSPParserListener)listener).exitCallbackDeclaration(this);
		}
	}

	public final CallbackDeclarationContext callbackDeclaration() throws RecognitionException {
		CallbackDeclarationContext _localctx = new CallbackDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_callbackDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			match(ON);
			setState(22);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTI_LINE_DELIMITER) {
				{
				{
				setState(19);
				match(MULTI_LINE_DELIMITER);
				}
				}
				setState(24);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(25);
			match(IDENTIFIER);
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTI_LINE_DELIMITER) {
				{
				{
				setState(26);
				match(MULTI_LINE_DELIMITER);
				}
				}
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(32);
			match(EOL);
			setState(33);
			block();
			setState(34);
			match(END);
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTI_LINE_DELIMITER) {
				{
				{
				setState(35);
				match(MULTI_LINE_DELIMITER);
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(41);
			match(ON);
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

	public static class BlockContext extends ParserRuleContext {
		public List<BlockStatementContext> blockStatement() {
			return getRuleContexts(BlockStatementContext.class);
		}
		public BlockStatementContext blockStatement(int i) {
			return getRuleContext(BlockStatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KSPParserListener ) ((KSPParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KSPParserListener ) ((KSPParserListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EOL || _la==MULTI_LINE_DELIMITER) {
				{
				{
				setState(43);
				blockStatement();
				}
				}
				setState(48);
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

	public static class BlockStatementContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public BlockStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KSPParserListener ) ((KSPParserListener)listener).enterBlockStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KSPParserListener ) ((KSPParserListener)listener).exitBlockStatement(this);
		}
	}

	public final BlockStatementContext blockStatement() throws RecognitionException {
		BlockStatementContext _localctx = new BlockStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_blockStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			statement();
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

	public static class StatementContext extends ParserRuleContext {
		public TerminalNode MULTI_LINE_DELIMITER() { return getToken(KSPParser.MULTI_LINE_DELIMITER, 0); }
		public TerminalNode EOL() { return getToken(KSPParser.EOL, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KSPParserListener ) ((KSPParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KSPParserListener ) ((KSPParserListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_la = _input.LA(1);
			if ( !(_la==EOL || _la==MULTI_LINE_DELIMITER) ) {
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3.8\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\7\2\20\n\2\f\2\16\2\23\13\2\3\3\3"+
		"\3\7\3\27\n\3\f\3\16\3\32\13\3\3\3\3\3\7\3\36\n\3\f\3\16\3!\13\3\3\3\3"+
		"\3\3\3\3\3\7\3\'\n\3\f\3\16\3*\13\3\3\3\3\3\3\4\7\4/\n\4\f\4\16\4\62\13"+
		"\4\3\5\3\5\3\6\3\6\3\6\2\2\7\2\4\6\b\n\2\3\3\2\3\4\29\2\21\3\2\2\2\4\24"+
		"\3\2\2\2\6\60\3\2\2\2\b\63\3\2\2\2\n\65\3\2\2\2\f\20\5\4\3\2\r\20\7\3"+
		"\2\2\16\20\7\4\2\2\17\f\3\2\2\2\17\r\3\2\2\2\17\16\3\2\2\2\20\23\3\2\2"+
		"\2\21\17\3\2\2\2\21\22\3\2\2\2\22\3\3\2\2\2\23\21\3\2\2\2\24\30\7\n\2"+
		"\2\25\27\7\4\2\2\26\25\3\2\2\2\27\32\3\2\2\2\30\26\3\2\2\2\30\31\3\2\2"+
		"\2\31\33\3\2\2\2\32\30\3\2\2\2\33\37\7.\2\2\34\36\7\4\2\2\35\34\3\2\2"+
		"\2\36!\3\2\2\2\37\35\3\2\2\2\37 \3\2\2\2 \"\3\2\2\2!\37\3\2\2\2\"#\7\3"+
		"\2\2#$\5\6\4\2$(\7\13\2\2%\'\7\4\2\2&%\3\2\2\2\'*\3\2\2\2(&\3\2\2\2()"+
		"\3\2\2\2)+\3\2\2\2*(\3\2\2\2+,\7\n\2\2,\5\3\2\2\2-/\5\b\5\2.-\3\2\2\2"+
		"/\62\3\2\2\2\60.\3\2\2\2\60\61\3\2\2\2\61\7\3\2\2\2\62\60\3\2\2\2\63\64"+
		"\5\n\6\2\64\t\3\2\2\2\65\66\t\2\2\2\66\13\3\2\2\2\b\17\21\30\37(\60";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}