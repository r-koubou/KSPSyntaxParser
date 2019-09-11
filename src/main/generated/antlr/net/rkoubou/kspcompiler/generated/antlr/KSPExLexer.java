// Generated from KSPExLexer.g4 by ANTLR 4.7.2
package net.rkoubou.kspcompiler.generated.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KSPExLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INTEGER_LITERAL=1, EOL=2, MULTI_LINE_DELIMITER=3, Whitespace=4, BlockComment=5, 
		DECLARE=6, CONST=7, POLYPHONIC=8, ON=9, END=10, FUNCTION=11, IF=12, ELSE=13, 
		SELECT=14, CASE=15, TO=16, WHILE=17, CALL=18, PREPROCESSOR_SET_COND=19, 
		PREPROCESSOR_RESET_COND=20, PREPROCESSOR_CODE_IF=21, PREPROCESSOR_CODE_IF_NOT=22, 
		PREPROCESSOR_CODE_END_IF=23, BOOL_GT=24, BOOL_LT=25, BOOL_GE=26, BOOL_LE=27, 
		BOOL_EQ=28, BOOL_NE=29, BOOL_NOT=30, BOOL_AND=31, BOOL_OR=32, ASSIGN=33, 
		PLUS=34, MINUS=35, MUL=36, DIV=37, MOD=38, BIT_AND=39, BIT_OR=40, BIT_NOT=41, 
		STRING_ADD=42, STRING_LITERAL=43, IDENTIFIER=44;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INTEGER_LITERAL", "DECIMAL_LITERAL", "HEX_LITERAL", "EXT_HEX_LITERAL", 
			"EXT_BIN_LITERAL", "EOL", "CR", "LF", "MULTI_LINE_DELIMITER", "Whitespace", 
			"BlockComment", "DECLARE", "CONST", "POLYPHONIC", "ON", "END", "FUNCTION", 
			"IF", "ELSE", "SELECT", "CASE", "TO", "WHILE", "CALL", "PREPROCESSOR_SET_COND", 
			"PREPROCESSOR_RESET_COND", "PREPROCESSOR_CODE_IF", "PREPROCESSOR_CODE_IF_NOT", 
			"PREPROCESSOR_CODE_END_IF", "BOOL_GT", "BOOL_LT", "BOOL_GE", "BOOL_LE", 
			"BOOL_EQ", "BOOL_NE", "BOOL_NOT", "BOOL_AND", "BOOL_OR", "ASSIGN", "PLUS", 
			"MINUS", "MUL", "DIV", "MOD", "BIT_AND", "BIT_OR", "BIT_NOT", "STRING_ADD", 
			"STRING_LITERAL", "EscapeSequence", "IDENTIFIER", "LETTER", "LETTER_OR_DIGIT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, "'declare'", "'const'", "'polyphonic'", 
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
			null, "INTEGER_LITERAL", "EOL", "MULTI_LINE_DELIMITER", "Whitespace", 
			"BlockComment", "DECLARE", "CONST", "POLYPHONIC", "ON", "END", "FUNCTION", 
			"IF", "ELSE", "SELECT", "CASE", "TO", "WHILE", "CALL", "PREPROCESSOR_SET_COND", 
			"PREPROCESSOR_RESET_COND", "PREPROCESSOR_CODE_IF", "PREPROCESSOR_CODE_IF_NOT", 
			"PREPROCESSOR_CODE_END_IF", "BOOL_GT", "BOOL_LT", "BOOL_GE", "BOOL_LE", 
			"BOOL_EQ", "BOOL_NE", "BOOL_NOT", "BOOL_AND", "BOOL_OR", "ASSIGN", "PLUS", 
			"MINUS", "MUL", "DIV", "MOD", "BIT_AND", "BIT_OR", "BIT_NOT", "STRING_ADD", 
			"STRING_LITERAL", "IDENTIFIER"
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


	public KSPExLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "KSPExLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2.\u01a2\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\3\2\3\2\3\2\3\2\5\2r\n\2\3\3\3\3\7\3v\n\3\f\3"+
		"\16\3y\13\3\3\4\3\4\6\4}\n\4\r\4\16\4~\3\4\3\4\3\5\3\5\3\5\3\5\6\5\u0087"+
		"\n\5\r\5\16\5\u0088\3\6\3\6\3\6\3\6\6\6\u008f\n\6\r\6\16\6\u0090\3\7\3"+
		"\7\3\7\3\7\3\7\5\7\u0098\n\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\7\n\u00a3"+
		"\n\n\f\n\16\n\u00a6\13\n\3\n\3\n\3\13\6\13\u00ab\n\13\r\13\16\13\u00ac"+
		"\3\13\3\13\3\f\3\f\7\f\u00b3\n\f\f\f\16\f\u00b6\13\f\3\f\3\f\3\f\3\f\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21"+
		"\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26"+
		"\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31"+
		"\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\3$\3$"+
		"\3%\3%\3%\3%\3&\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,"+
		"\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\3\61\3\61\3\62\3\62\3\62\7\62\u018c\n\62\f\62\16\62\u018f\13\62"+
		"\3\62\3\62\3\63\3\63\3\63\3\64\3\64\7\64\u0198\n\64\f\64\16\64\u019b\13"+
		"\64\3\65\3\65\3\66\3\66\5\66\u01a1\n\66\3\u00b4\2\67\3\3\5\2\7\2\t\2\13"+
		"\2\r\4\17\2\21\2\23\5\25\6\27\7\31\b\33\t\35\n\37\13!\f#\r%\16\'\17)\20"+
		"+\21-\22/\23\61\24\63\25\65\26\67\279\30;\31=\32?\33A\34C\35E\36G\37I"+
		" K!M\"O#Q$S%U&W\'Y([)]*_+a,c-e\2g.i\2k\2\3\2\13\5\2\62;CHch\4\2JJjj\3"+
		"\2\62\63\4\2\13\13\"\"\5\2\13\13\16\16\"\"\6\2\f\f\17\17$$^^\n\2$$))^"+
		"^ddhhppttvv\5\2C\\aac|\3\2\62;\2\u01a8\2\3\3\2\2\2\2\r\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2"+
		"\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2"+
		"\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2"+
		"O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3"+
		"\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2g\3\2\2\2\3q\3\2\2"+
		"\2\5s\3\2\2\2\7z\3\2\2\2\t\u0082\3\2\2\2\13\u008a\3\2\2\2\r\u0097\3\2"+
		"\2\2\17\u0099\3\2\2\2\21\u009b\3\2\2\2\23\u009d\3\2\2\2\25\u00aa\3\2\2"+
		"\2\27\u00b0\3\2\2\2\31\u00bb\3\2\2\2\33\u00c3\3\2\2\2\35\u00c9\3\2\2\2"+
		"\37\u00d4\3\2\2\2!\u00d7\3\2\2\2#\u00db\3\2\2\2%\u00e4\3\2\2\2\'\u00e7"+
		"\3\2\2\2)\u00ec\3\2\2\2+\u00f3\3\2\2\2-\u00f8\3\2\2\2/\u00fb\3\2\2\2\61"+
		"\u0101\3\2\2\2\63\u0106\3\2\2\2\65\u0114\3\2\2\2\67\u0124\3\2\2\29\u0130"+
		"\3\2\2\2;\u0140\3\2\2\2=\u014d\3\2\2\2?\u014f\3\2\2\2A\u0151\3\2\2\2C"+
		"\u0154\3\2\2\2E\u0157\3\2\2\2G\u0159\3\2\2\2I\u015b\3\2\2\2K\u015f\3\2"+
		"\2\2M\u0163\3\2\2\2O\u0166\3\2\2\2Q\u0169\3\2\2\2S\u016b\3\2\2\2U\u016d"+
		"\3\2\2\2W\u016f\3\2\2\2Y\u0171\3\2\2\2[\u0175\3\2\2\2]\u017b\3\2\2\2_"+
		"\u0180\3\2\2\2a\u0186\3\2\2\2c\u0188\3\2\2\2e\u0192\3\2\2\2g\u0195\3\2"+
		"\2\2i\u019c\3\2\2\2k\u01a0\3\2\2\2mr\5\5\3\2nr\5\7\4\2or\5\t\5\2pr\5\13"+
		"\6\2qm\3\2\2\2qn\3\2\2\2qo\3\2\2\2qp\3\2\2\2r\4\3\2\2\2sw\4\63;\2tv\4"+
		"\62;\2ut\3\2\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2x\6\3\2\2\2yw\3\2\2\2z|"+
		"\7;\2\2{}\t\2\2\2|{\3\2\2\2}~\3\2\2\2~|\3\2\2\2~\177\3\2\2\2\177\u0080"+
		"\3\2\2\2\u0080\u0081\t\3\2\2\u0081\b\3\2\2\2\u0082\u0083\7\62\2\2\u0083"+
		"\u0084\7z\2\2\u0084\u0086\3\2\2\2\u0085\u0087\t\2\2\2\u0086\u0085\3\2"+
		"\2\2\u0087\u0088\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089"+
		"\n\3\2\2\2\u008a\u008b\7\62\2\2\u008b\u008c\7d\2\2\u008c\u008e\3\2\2\2"+
		"\u008d\u008f\t\4\2\2\u008e\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u008e"+
		"\3\2\2\2\u0090\u0091\3\2\2\2\u0091\f\3\2\2\2\u0092\u0093\5\17\b\2\u0093"+
		"\u0094\5\21\t\2\u0094\u0098\3\2\2\2\u0095\u0098\5\21\t\2\u0096\u0098\5"+
		"\23\n\2\u0097\u0092\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0096\3\2\2\2\u0098"+
		"\16\3\2\2\2\u0099\u009a\7\17\2\2\u009a\20\3\2\2\2\u009b\u009c\7\f\2\2"+
		"\u009c\22\3\2\2\2\u009d\u009e\7\60\2\2\u009e\u009f\7\60\2\2\u009f\u00a0"+
		"\7\60\2\2\u00a0\u00a4\3\2\2\2\u00a1\u00a3\t\5\2\2\u00a2\u00a1\3\2\2\2"+
		"\u00a3\u00a6\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7"+
		"\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00a8\5\r\7\2\u00a8\24\3\2\2\2\u00a9"+
		"\u00ab\t\6\2\2\u00aa\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00aa\3\2"+
		"\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00af\b\13\2\2\u00af"+
		"\26\3\2\2\2\u00b0\u00b4\7}\2\2\u00b1\u00b3\13\2\2\2\u00b2\u00b1\3\2\2"+
		"\2\u00b3\u00b6\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5\u00b7"+
		"\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b7\u00b8\7\177\2\2\u00b8\u00b9\3\2\2\2"+
		"\u00b9\u00ba\b\f\2\2\u00ba\30\3\2\2\2\u00bb\u00bc\7f\2\2\u00bc\u00bd\7"+
		"g\2\2\u00bd\u00be\7e\2\2\u00be\u00bf\7n\2\2\u00bf\u00c0\7c\2\2\u00c0\u00c1"+
		"\7t\2\2\u00c1\u00c2\7g\2\2\u00c2\32\3\2\2\2\u00c3\u00c4\7e\2\2\u00c4\u00c5"+
		"\7q\2\2\u00c5\u00c6\7p\2\2\u00c6\u00c7\7u\2\2\u00c7\u00c8\7v\2\2\u00c8"+
		"\34\3\2\2\2\u00c9\u00ca\7r\2\2\u00ca\u00cb\7q\2\2\u00cb\u00cc\7n\2\2\u00cc"+
		"\u00cd\7{\2\2\u00cd\u00ce\7r\2\2\u00ce\u00cf\7j\2\2\u00cf\u00d0\7q\2\2"+
		"\u00d0\u00d1\7p\2\2\u00d1\u00d2\7k\2\2\u00d2\u00d3\7e\2\2\u00d3\36\3\2"+
		"\2\2\u00d4\u00d5\7q\2\2\u00d5\u00d6\7p\2\2\u00d6 \3\2\2\2\u00d7\u00d8"+
		"\7g\2\2\u00d8\u00d9\7p\2\2\u00d9\u00da\7f\2\2\u00da\"\3\2\2\2\u00db\u00dc"+
		"\7h\2\2\u00dc\u00dd\7w\2\2\u00dd\u00de\7p\2\2\u00de\u00df\7e\2\2\u00df"+
		"\u00e0\7v\2\2\u00e0\u00e1\7k\2\2\u00e1\u00e2\7q\2\2\u00e2\u00e3\7p\2\2"+
		"\u00e3$\3\2\2\2\u00e4\u00e5\7k\2\2\u00e5\u00e6\7h\2\2\u00e6&\3\2\2\2\u00e7"+
		"\u00e8\7g\2\2\u00e8\u00e9\7n\2\2\u00e9\u00ea\7u\2\2\u00ea\u00eb\7g\2\2"+
		"\u00eb(\3\2\2\2\u00ec\u00ed\7u\2\2\u00ed\u00ee\7g\2\2\u00ee\u00ef\7n\2"+
		"\2\u00ef\u00f0\7g\2\2\u00f0\u00f1\7e\2\2\u00f1\u00f2\7v\2\2\u00f2*\3\2"+
		"\2\2\u00f3\u00f4\7e\2\2\u00f4\u00f5\7c\2\2\u00f5\u00f6\7u\2\2\u00f6\u00f7"+
		"\7g\2\2\u00f7,\3\2\2\2\u00f8\u00f9\7v\2\2\u00f9\u00fa\7q\2\2\u00fa.\3"+
		"\2\2\2\u00fb\u00fc\7y\2\2\u00fc\u00fd\7j\2\2\u00fd\u00fe\7k\2\2\u00fe"+
		"\u00ff\7n\2\2\u00ff\u0100\7g\2\2\u0100\60\3\2\2\2\u0101\u0102\7e\2\2\u0102"+
		"\u0103\7c\2\2\u0103\u0104\7n\2\2\u0104\u0105\7n\2\2\u0105\62\3\2\2\2\u0106"+
		"\u0107\7U\2\2\u0107\u0108\7G\2\2\u0108\u0109\7V\2\2\u0109\u010a\7a\2\2"+
		"\u010a\u010b\7E\2\2\u010b\u010c\7Q\2\2\u010c\u010d\7P\2\2\u010d\u010e"+
		"\7F\2\2\u010e\u010f\7K\2\2\u010f\u0110\7V\2\2\u0110\u0111\7K\2\2\u0111"+
		"\u0112\7Q\2\2\u0112\u0113\7P\2\2\u0113\64\3\2\2\2\u0114\u0115\7T\2\2\u0115"+
		"\u0116\7G\2\2\u0116\u0117\7U\2\2\u0117\u0118\7G\2\2\u0118\u0119\7V\2\2"+
		"\u0119\u011a\7a\2\2\u011a\u011b\7E\2\2\u011b\u011c\7Q\2\2\u011c\u011d"+
		"\7P\2\2\u011d\u011e\7F\2\2\u011e\u011f\7K\2\2\u011f\u0120\7V\2\2\u0120"+
		"\u0121\7K\2\2\u0121\u0122\7Q\2\2\u0122\u0123\7P\2\2\u0123\66\3\2\2\2\u0124"+
		"\u0125\7W\2\2\u0125\u0126\7U\2\2\u0126\u0127\7G\2\2\u0127\u0128\7a\2\2"+
		"\u0128\u0129\7E\2\2\u0129\u012a\7Q\2\2\u012a\u012b\7F\2\2\u012b\u012c"+
		"\7G\2\2\u012c\u012d\7a\2\2\u012d\u012e\7K\2\2\u012e\u012f\7H\2\2\u012f"+
		"8\3\2\2\2\u0130\u0131\7W\2\2\u0131\u0132\7U\2\2\u0132\u0133\7G\2\2\u0133"+
		"\u0134\7a\2\2\u0134\u0135\7E\2\2\u0135\u0136\7Q\2\2\u0136\u0137\7F\2\2"+
		"\u0137\u0138\7G\2\2\u0138\u0139\7a\2\2\u0139\u013a\7K\2\2\u013a\u013b"+
		"\7H\2\2\u013b\u013c\7a\2\2\u013c\u013d\7P\2\2\u013d\u013e\7Q\2\2\u013e"+
		"\u013f\7V\2\2\u013f:\3\2\2\2\u0140\u0141\7G\2\2\u0141\u0142\7P\2\2\u0142"+
		"\u0143\7F\2\2\u0143\u0144\7a\2\2\u0144\u0145\7W\2\2\u0145\u0146\7U\2\2"+
		"\u0146\u0147\7G\2\2\u0147\u0148\7a\2\2\u0148\u0149\7E\2\2\u0149\u014a"+
		"\7Q\2\2\u014a\u014b\7F\2\2\u014b\u014c\7G\2\2\u014c<\3\2\2\2\u014d\u014e"+
		"\7@\2\2\u014e>\3\2\2\2\u014f\u0150\7>\2\2\u0150@\3\2\2\2\u0151\u0152\7"+
		"@\2\2\u0152\u0153\7?\2\2\u0153B\3\2\2\2\u0154\u0155\7>\2\2\u0155\u0156"+
		"\7?\2\2\u0156D\3\2\2\2\u0157\u0158\7?\2\2\u0158F\3\2\2\2\u0159\u015a\7"+
		"%\2\2\u015aH\3\2\2\2\u015b\u015c\7p\2\2\u015c\u015d\7q\2\2\u015d\u015e"+
		"\7v\2\2\u015eJ\3\2\2\2\u015f\u0160\7c\2\2\u0160\u0161\7p\2\2\u0161\u0162"+
		"\7f\2\2\u0162L\3\2\2\2\u0163\u0164\7q\2\2\u0164\u0165\7t\2\2\u0165N\3"+
		"\2\2\2\u0166\u0167\7<\2\2\u0167\u0168\7?\2\2\u0168P\3\2\2\2\u0169\u016a"+
		"\7-\2\2\u016aR\3\2\2\2\u016b\u016c\7/\2\2\u016cT\3\2\2\2\u016d\u016e\7"+
		",\2\2\u016eV\3\2\2\2\u016f\u0170\7\61\2\2\u0170X\3\2\2\2\u0171\u0172\7"+
		"o\2\2\u0172\u0173\7q\2\2\u0173\u0174\7f\2\2\u0174Z\3\2\2\2\u0175\u0176"+
		"\7\60\2\2\u0176\u0177\7c\2\2\u0177\u0178\7p\2\2\u0178\u0179\7f\2\2\u0179"+
		"\u017a\7\60\2\2\u017a\\\3\2\2\2\u017b\u017c\7\60\2\2\u017c\u017d\7q\2"+
		"\2\u017d\u017e\7t\2\2\u017e\u017f\7\60\2\2\u017f^\3\2\2\2\u0180\u0181"+
		"\7\60\2\2\u0181\u0182\7p\2\2\u0182\u0183\7q\2\2\u0183\u0184\7v\2\2\u0184"+
		"\u0185\7\60\2\2\u0185`\3\2\2\2\u0186\u0187\7(\2\2\u0187b\3\2\2\2\u0188"+
		"\u018d\7$\2\2\u0189\u018c\n\7\2\2\u018a\u018c\5e\63\2\u018b\u0189\3\2"+
		"\2\2\u018b\u018a\3\2\2\2\u018c\u018f\3\2\2\2\u018d\u018b\3\2\2\2\u018d"+
		"\u018e\3\2\2\2\u018e\u0190\3\2\2\2\u018f\u018d\3\2\2\2\u0190\u0191\7$"+
		"\2\2\u0191d\3\2\2\2\u0192\u0193\7^\2\2\u0193\u0194\t\b\2\2\u0194f\3\2"+
		"\2\2\u0195\u0199\5i\65\2\u0196\u0198\5k\66\2\u0197\u0196\3\2\2\2\u0198"+
		"\u019b\3\2\2\2\u0199\u0197\3\2\2\2\u0199\u019a\3\2\2\2\u019ah\3\2\2\2"+
		"\u019b\u0199\3\2\2\2\u019c\u019d\t\t\2\2\u019dj\3\2\2\2\u019e\u01a1\5"+
		"i\65\2\u019f\u01a1\t\n\2\2\u01a0\u019e\3\2\2\2\u01a0\u019f\3\2\2\2\u01a1"+
		"l\3\2\2\2\20\2qw~\u0088\u0090\u0097\u00a4\u00ac\u00b4\u018b\u018d\u0199"+
		"\u01a0\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}