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
		RULE_compilationUnit = 0, RULE_callbackDeclaration = 1;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "callbackDeclaration"
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
			setState(6);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ON:
				enterOuterAlt(_localctx, 1);
				{
				setState(4);
				callbackDeclaration();
				}
				break;
			case EOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(5);
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
			setState(8);
			match(ON);
			setState(12);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTI_LINE_DELIMITER) {
				{
				{
				setState(9);
				match(MULTI_LINE_DELIMITER);
				}
				}
				setState(14);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(15);
			match(IDENTIFIER);
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTI_LINE_DELIMITER) {
				{
				{
				setState(16);
				match(MULTI_LINE_DELIMITER);
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(22);
			match(END);
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTI_LINE_DELIMITER) {
				{
				{
				setState(23);
				match(MULTI_LINE_DELIMITER);
				}
				}
				setState(28);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(29);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3.\"\4\2\t\2\4\3\t"+
		"\3\3\2\3\2\5\2\t\n\2\3\3\3\3\7\3\r\n\3\f\3\16\3\20\13\3\3\3\3\3\7\3\24"+
		"\n\3\f\3\16\3\27\13\3\3\3\3\3\7\3\33\n\3\f\3\16\3\36\13\3\3\3\3\3\3\3"+
		"\2\2\4\2\4\2\2\2#\2\b\3\2\2\2\4\n\3\2\2\2\6\t\5\4\3\2\7\t\7\3\2\2\b\6"+
		"\3\2\2\2\b\7\3\2\2\2\t\3\3\2\2\2\n\16\7\n\2\2\13\r\7\4\2\2\f\13\3\2\2"+
		"\2\r\20\3\2\2\2\16\f\3\2\2\2\16\17\3\2\2\2\17\21\3\2\2\2\20\16\3\2\2\2"+
		"\21\25\7.\2\2\22\24\7\4\2\2\23\22\3\2\2\2\24\27\3\2\2\2\25\23\3\2\2\2"+
		"\25\26\3\2\2\2\26\30\3\2\2\2\27\25\3\2\2\2\30\34\7\13\2\2\31\33\7\4\2"+
		"\2\32\31\3\2\2\2\33\36\3\2\2\2\34\32\3\2\2\2\34\35\3\2\2\2\35\37\3\2\2"+
		"\2\36\34\3\2\2\2\37 \7\n\2\2 \5\3\2\2\2\6\b\16\25\34";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}