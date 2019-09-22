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
		RULE_statement = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "callbackDeclaration", "block", "statement"
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
		public CallbackDeclarationContext callbackDeclaration() {
			return getRuleContext(CallbackDeclarationContext.class,0);
		}
		public TerminalNode EOL() { return getToken(KSPParser.EOL, 0); }
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
		try {
			setState(10);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ON:
				enterOuterAlt(_localctx, 1);
				{
				setState(8);
				callbackDeclaration();
				}
				break;
			case EOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(9);
				match(EOL);
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
			setState(12);
			match(ON);
			setState(16);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTI_LINE_DELIMITER) {
				{
				{
				setState(13);
				match(MULTI_LINE_DELIMITER);
				}
				}
				setState(18);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(19);
			match(IDENTIFIER);
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTI_LINE_DELIMITER) {
				{
				{
				setState(20);
				match(MULTI_LINE_DELIMITER);
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(26);
			match(EOL);
			setState(27);
			block();
			setState(28);
			match(END);
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTI_LINE_DELIMITER) {
				{
				{
				setState(29);
				match(MULTI_LINE_DELIMITER);
				}
				}
				setState(34);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(35);
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
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
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
		enterRule(_localctx, 6, RULE_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3.,\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\3\2\3\2\5\2\r\n\2\3\3\3\3\7\3\21\n\3\f\3\16\3\24\13\3"+
		"\3\3\3\3\7\3\30\n\3\f\3\16\3\33\13\3\3\3\3\3\3\3\3\3\7\3!\n\3\f\3\16\3"+
		"$\13\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\2\2\6\2\4\6\b\2\3\3\2\3\4\2+\2\f\3"+
		"\2\2\2\4\16\3\2\2\2\6\'\3\2\2\2\b)\3\2\2\2\n\r\5\4\3\2\13\r\7\3\2\2\f"+
		"\n\3\2\2\2\f\13\3\2\2\2\r\3\3\2\2\2\16\22\7\n\2\2\17\21\7\4\2\2\20\17"+
		"\3\2\2\2\21\24\3\2\2\2\22\20\3\2\2\2\22\23\3\2\2\2\23\25\3\2\2\2\24\22"+
		"\3\2\2\2\25\31\7.\2\2\26\30\7\4\2\2\27\26\3\2\2\2\30\33\3\2\2\2\31\27"+
		"\3\2\2\2\31\32\3\2\2\2\32\34\3\2\2\2\33\31\3\2\2\2\34\35\7\3\2\2\35\36"+
		"\5\6\4\2\36\"\7\13\2\2\37!\7\4\2\2 \37\3\2\2\2!$\3\2\2\2\" \3\2\2\2\""+
		"#\3\2\2\2#%\3\2\2\2$\"\3\2\2\2%&\7\n\2\2&\5\3\2\2\2\'(\5\b\5\2(\7\3\2"+
		"\2\2)*\t\2\2\2*\t\3\2\2\2\6\f\22\31\"";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}