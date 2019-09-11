// Generated from KSPLexer.g4 by ANTLR 4.7.2
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
public class KSPLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"EOL", "CR", "LF", "MULTI_LINE_DELIMITER", "Whitespace", "BlockComment", 
			"DECLARE", "CONST", "POLYPHONIC", "ON", "END", "FUNCTION", "IF", "ELSE", 
			"SELECT", "CASE", "TO", "WHILE", "CALL", "PREPROCESSOR_SET_COND", "PREPROCESSOR_RESET_COND", 
			"PREPROCESSOR_CODE_IF", "PREPROCESSOR_CODE_IF_NOT", "PREPROCESSOR_CODE_END_IF", 
			"BOOL_GT", "BOOL_LT", "BOOL_GE", "BOOL_LE", "BOOL_EQ", "BOOL_NE", "BOOL_NOT", 
			"BOOL_AND", "BOOL_OR", "ASSIGN", "PLUS", "MINUS", "MUL", "DIV", "MOD", 
			"BIT_AND", "BIT_OR", "BIT_NOT", "STRING_ADD", "INTEGER_LITERAL", "DECIMAL_LITERAL", 
			"HEX_LITERAL", "STRING_LITERAL", "EscapeSequence", "IDENTIFIER", "LETTER", 
			"LETTER_OR_DIGIT"
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


	public KSPLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "KSPLexer.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2.\u018c\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\3\2\3\2\3\2\3\2\3\2\5\2o\n\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\7"+
		"\5z\n\5\f\5\16\5}\13\5\3\5\3\5\3\6\6\6\u0082\n\6\r\6\16\6\u0083\3\6\3"+
		"\6\3\7\3\7\7\7\u008a\n\7\f\7\16\7\u008d\13\7\3\7\3\7\3\7\3\7\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3"+
		"\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3"+
		"\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\33\3"+
		"\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3 \3!\3"+
		"!\3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3(\3(\3)"+
		"\3)\3)\3)\3)\3)\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3,\3,\3-\3-\5-\u0162"+
		"\n-\3.\3.\7.\u0166\n.\f.\16.\u0169\13.\3/\3/\6/\u016d\n/\r/\16/\u016e"+
		"\3/\3/\3\60\3\60\3\60\7\60\u0176\n\60\f\60\16\60\u0179\13\60\3\60\3\60"+
		"\3\61\3\61\3\61\3\62\3\62\7\62\u0182\n\62\f\62\16\62\u0185\13\62\3\63"+
		"\3\63\3\64\3\64\5\64\u018b\n\64\3\u008b\2\65\3\3\5\2\7\2\t\4\13\5\r\6"+
		"\17\7\21\b\23\t\25\n\27\13\31\f\33\r\35\16\37\17!\20#\21%\22\'\23)\24"+
		"+\25-\26/\27\61\30\63\31\65\32\67\339\34;\35=\36?\37A C!E\"G#I$K%M&O\'"+
		"Q(S)U*W+Y,[\2]\2_-a\2c.e\2g\2\3\2\n\4\2\13\13\"\"\5\2\13\13\16\16\"\""+
		"\5\2\62;CHch\4\2JJjj\6\2\f\f\17\17$$^^\n\2$$))^^ddhhppttvv\5\2C\\aac|"+
		"\3\2\62;\2\u0190\2\3\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2"+
		"=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3"+
		"\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2"+
		"\2\2W\3\2\2\2\2Y\3\2\2\2\2_\3\2\2\2\2c\3\2\2\2\3n\3\2\2\2\5p\3\2\2\2\7"+
		"r\3\2\2\2\tt\3\2\2\2\13\u0081\3\2\2\2\r\u0087\3\2\2\2\17\u0092\3\2\2\2"+
		"\21\u009a\3\2\2\2\23\u00a0\3\2\2\2\25\u00ab\3\2\2\2\27\u00ae\3\2\2\2\31"+
		"\u00b2\3\2\2\2\33\u00bb\3\2\2\2\35\u00be\3\2\2\2\37\u00c3\3\2\2\2!\u00ca"+
		"\3\2\2\2#\u00cf\3\2\2\2%\u00d2\3\2\2\2\'\u00d8\3\2\2\2)\u00dd\3\2\2\2"+
		"+\u00eb\3\2\2\2-\u00fb\3\2\2\2/\u0107\3\2\2\2\61\u0117\3\2\2\2\63\u0124"+
		"\3\2\2\2\65\u0126\3\2\2\2\67\u0128\3\2\2\29\u012b\3\2\2\2;\u012e\3\2\2"+
		"\2=\u0130\3\2\2\2?\u0132\3\2\2\2A\u0136\3\2\2\2C\u013a\3\2\2\2E\u013d"+
		"\3\2\2\2G\u0140\3\2\2\2I\u0142\3\2\2\2K\u0144\3\2\2\2M\u0146\3\2\2\2O"+
		"\u0148\3\2\2\2Q\u014c\3\2\2\2S\u0152\3\2\2\2U\u0157\3\2\2\2W\u015d\3\2"+
		"\2\2Y\u0161\3\2\2\2[\u0163\3\2\2\2]\u016a\3\2\2\2_\u0172\3\2\2\2a\u017c"+
		"\3\2\2\2c\u017f\3\2\2\2e\u0186\3\2\2\2g\u018a\3\2\2\2ij\5\5\3\2jk\5\7"+
		"\4\2ko\3\2\2\2lo\5\7\4\2mo\5\t\5\2ni\3\2\2\2nl\3\2\2\2nm\3\2\2\2o\4\3"+
		"\2\2\2pq\7\17\2\2q\6\3\2\2\2rs\7\f\2\2s\b\3\2\2\2tu\7\60\2\2uv\7\60\2"+
		"\2vw\7\60\2\2w{\3\2\2\2xz\t\2\2\2yx\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2"+
		"\2\2|~\3\2\2\2}{\3\2\2\2~\177\5\3\2\2\177\n\3\2\2\2\u0080\u0082\t\3\2"+
		"\2\u0081\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084"+
		"\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0086\b\6\2\2\u0086\f\3\2\2\2\u0087"+
		"\u008b\7}\2\2\u0088\u008a\13\2\2\2\u0089\u0088\3\2\2\2\u008a\u008d\3\2"+
		"\2\2\u008b\u008c\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008e\3\2\2\2\u008d"+
		"\u008b\3\2\2\2\u008e\u008f\7\177\2\2\u008f\u0090\3\2\2\2\u0090\u0091\b"+
		"\7\2\2\u0091\16\3\2\2\2\u0092\u0093\7f\2\2\u0093\u0094\7g\2\2\u0094\u0095"+
		"\7e\2\2\u0095\u0096\7n\2\2\u0096\u0097\7c\2\2\u0097\u0098\7t\2\2\u0098"+
		"\u0099\7g\2\2\u0099\20\3\2\2\2\u009a\u009b\7e\2\2\u009b\u009c\7q\2\2\u009c"+
		"\u009d\7p\2\2\u009d\u009e\7u\2\2\u009e\u009f\7v\2\2\u009f\22\3\2\2\2\u00a0"+
		"\u00a1\7r\2\2\u00a1\u00a2\7q\2\2\u00a2\u00a3\7n\2\2\u00a3\u00a4\7{\2\2"+
		"\u00a4\u00a5\7r\2\2\u00a5\u00a6\7j\2\2\u00a6\u00a7\7q\2\2\u00a7\u00a8"+
		"\7p\2\2\u00a8\u00a9\7k\2\2\u00a9\u00aa\7e\2\2\u00aa\24\3\2\2\2\u00ab\u00ac"+
		"\7q\2\2\u00ac\u00ad\7p\2\2\u00ad\26\3\2\2\2\u00ae\u00af\7g\2\2\u00af\u00b0"+
		"\7p\2\2\u00b0\u00b1\7f\2\2\u00b1\30\3\2\2\2\u00b2\u00b3\7h\2\2\u00b3\u00b4"+
		"\7w\2\2\u00b4\u00b5\7p\2\2\u00b5\u00b6\7e\2\2\u00b6\u00b7\7v\2\2\u00b7"+
		"\u00b8\7k\2\2\u00b8\u00b9\7q\2\2\u00b9\u00ba\7p\2\2\u00ba\32\3\2\2\2\u00bb"+
		"\u00bc\7k\2\2\u00bc\u00bd\7h\2\2\u00bd\34\3\2\2\2\u00be\u00bf\7g\2\2\u00bf"+
		"\u00c0\7n\2\2\u00c0\u00c1\7u\2\2\u00c1\u00c2\7g\2\2\u00c2\36\3\2\2\2\u00c3"+
		"\u00c4\7u\2\2\u00c4\u00c5\7g\2\2\u00c5\u00c6\7n\2\2\u00c6\u00c7\7g\2\2"+
		"\u00c7\u00c8\7e\2\2\u00c8\u00c9\7v\2\2\u00c9 \3\2\2\2\u00ca\u00cb\7e\2"+
		"\2\u00cb\u00cc\7c\2\2\u00cc\u00cd\7u\2\2\u00cd\u00ce\7g\2\2\u00ce\"\3"+
		"\2\2\2\u00cf\u00d0\7v\2\2\u00d0\u00d1\7q\2\2\u00d1$\3\2\2\2\u00d2\u00d3"+
		"\7y\2\2\u00d3\u00d4\7j\2\2\u00d4\u00d5\7k\2\2\u00d5\u00d6\7n\2\2\u00d6"+
		"\u00d7\7g\2\2\u00d7&\3\2\2\2\u00d8\u00d9\7e\2\2\u00d9\u00da\7c\2\2\u00da"+
		"\u00db\7n\2\2\u00db\u00dc\7n\2\2\u00dc(\3\2\2\2\u00dd\u00de\7U\2\2\u00de"+
		"\u00df\7G\2\2\u00df\u00e0\7V\2\2\u00e0\u00e1\7a\2\2\u00e1\u00e2\7E\2\2"+
		"\u00e2\u00e3\7Q\2\2\u00e3\u00e4\7P\2\2\u00e4\u00e5\7F\2\2\u00e5\u00e6"+
		"\7K\2\2\u00e6\u00e7\7V\2\2\u00e7\u00e8\7K\2\2\u00e8\u00e9\7Q\2\2\u00e9"+
		"\u00ea\7P\2\2\u00ea*\3\2\2\2\u00eb\u00ec\7T\2\2\u00ec\u00ed\7G\2\2\u00ed"+
		"\u00ee\7U\2\2\u00ee\u00ef\7G\2\2\u00ef\u00f0\7V\2\2\u00f0\u00f1\7a\2\2"+
		"\u00f1\u00f2\7E\2\2\u00f2\u00f3\7Q\2\2\u00f3\u00f4\7P\2\2\u00f4\u00f5"+
		"\7F\2\2\u00f5\u00f6\7K\2\2\u00f6\u00f7\7V\2\2\u00f7\u00f8\7K\2\2\u00f8"+
		"\u00f9\7Q\2\2\u00f9\u00fa\7P\2\2\u00fa,\3\2\2\2\u00fb\u00fc\7W\2\2\u00fc"+
		"\u00fd\7U\2\2\u00fd\u00fe\7G\2\2\u00fe\u00ff\7a\2\2\u00ff\u0100\7E\2\2"+
		"\u0100\u0101\7Q\2\2\u0101\u0102\7F\2\2\u0102\u0103\7G\2\2\u0103\u0104"+
		"\7a\2\2\u0104\u0105\7K\2\2\u0105\u0106\7H\2\2\u0106.\3\2\2\2\u0107\u0108"+
		"\7W\2\2\u0108\u0109\7U\2\2\u0109\u010a\7G\2\2\u010a\u010b\7a\2\2\u010b"+
		"\u010c\7E\2\2\u010c\u010d\7Q\2\2\u010d\u010e\7F\2\2\u010e\u010f\7G\2\2"+
		"\u010f\u0110\7a\2\2\u0110\u0111\7K\2\2\u0111\u0112\7H\2\2\u0112\u0113"+
		"\7a\2\2\u0113\u0114\7P\2\2\u0114\u0115\7Q\2\2\u0115\u0116\7V\2\2\u0116"+
		"\60\3\2\2\2\u0117\u0118\7G\2\2\u0118\u0119\7P\2\2\u0119\u011a\7F\2\2\u011a"+
		"\u011b\7a\2\2\u011b\u011c\7W\2\2\u011c\u011d\7U\2\2\u011d\u011e\7G\2\2"+
		"\u011e\u011f\7a\2\2\u011f\u0120\7E\2\2\u0120\u0121\7Q\2\2\u0121\u0122"+
		"\7F\2\2\u0122\u0123\7G\2\2\u0123\62\3\2\2\2\u0124\u0125\7@\2\2\u0125\64"+
		"\3\2\2\2\u0126\u0127\7>\2\2\u0127\66\3\2\2\2\u0128\u0129\7@\2\2\u0129"+
		"\u012a\7?\2\2\u012a8\3\2\2\2\u012b\u012c\7>\2\2\u012c\u012d\7?\2\2\u012d"+
		":\3\2\2\2\u012e\u012f\7?\2\2\u012f<\3\2\2\2\u0130\u0131\7%\2\2\u0131>"+
		"\3\2\2\2\u0132\u0133\7p\2\2\u0133\u0134\7q\2\2\u0134\u0135\7v\2\2\u0135"+
		"@\3\2\2\2\u0136\u0137\7c\2\2\u0137\u0138\7p\2\2\u0138\u0139\7f\2\2\u0139"+
		"B\3\2\2\2\u013a\u013b\7q\2\2\u013b\u013c\7t\2\2\u013cD\3\2\2\2\u013d\u013e"+
		"\7<\2\2\u013e\u013f\7?\2\2\u013fF\3\2\2\2\u0140\u0141\7-\2\2\u0141H\3"+
		"\2\2\2\u0142\u0143\7/\2\2\u0143J\3\2\2\2\u0144\u0145\7,\2\2\u0145L\3\2"+
		"\2\2\u0146\u0147\7\61\2\2\u0147N\3\2\2\2\u0148\u0149\7o\2\2\u0149\u014a"+
		"\7q\2\2\u014a\u014b\7f\2\2\u014bP\3\2\2\2\u014c\u014d\7\60\2\2\u014d\u014e"+
		"\7c\2\2\u014e\u014f\7p\2\2\u014f\u0150\7f\2\2\u0150\u0151\7\60\2\2\u0151"+
		"R\3\2\2\2\u0152\u0153\7\60\2\2\u0153\u0154\7q\2\2\u0154\u0155\7t\2\2\u0155"+
		"\u0156\7\60\2\2\u0156T\3\2\2\2\u0157\u0158\7\60\2\2\u0158\u0159\7p\2\2"+
		"\u0159\u015a\7q\2\2\u015a\u015b\7v\2\2\u015b\u015c\7\60\2\2\u015cV\3\2"+
		"\2\2\u015d\u015e\7(\2\2\u015eX\3\2\2\2\u015f\u0162\5[.\2\u0160\u0162\5"+
		"]/\2\u0161\u015f\3\2\2\2\u0161\u0160\3\2\2\2\u0162Z\3\2\2\2\u0163\u0167"+
		"\4\63;\2\u0164\u0166\4\62;\2\u0165\u0164\3\2\2\2\u0166\u0169\3\2\2\2\u0167"+
		"\u0165\3\2\2\2\u0167\u0168\3\2\2\2\u0168\\\3\2\2\2\u0169\u0167\3\2\2\2"+
		"\u016a\u016c\7;\2\2\u016b\u016d\t\4\2\2\u016c\u016b\3\2\2\2\u016d\u016e"+
		"\3\2\2\2\u016e\u016c\3\2\2\2\u016e\u016f\3\2\2\2\u016f\u0170\3\2\2\2\u0170"+
		"\u0171\t\5\2\2\u0171^\3\2\2\2\u0172\u0177\7$\2\2\u0173\u0176\n\6\2\2\u0174"+
		"\u0176\5a\61\2\u0175\u0173\3\2\2\2\u0175\u0174\3\2\2\2\u0176\u0179\3\2"+
		"\2\2\u0177\u0175\3\2\2\2\u0177\u0178\3\2\2\2\u0178\u017a\3\2\2\2\u0179"+
		"\u0177\3\2\2\2\u017a\u017b\7$\2\2\u017b`\3\2\2\2\u017c\u017d\7^\2\2\u017d"+
		"\u017e\t\7\2\2\u017eb\3\2\2\2\u017f\u0183\5e\63\2\u0180\u0182\5g\64\2"+
		"\u0181\u0180\3\2\2\2\u0182\u0185\3\2\2\2\u0183\u0181\3\2\2\2\u0183\u0184"+
		"\3\2\2\2\u0184d\3\2\2\2\u0185\u0183\3\2\2\2\u0186\u0187\t\b\2\2\u0187"+
		"f\3\2\2\2\u0188\u018b\5e\63\2\u0189\u018b\t\t\2\2\u018a\u0188\3\2\2\2"+
		"\u018a\u0189\3\2\2\2\u018bh\3\2\2\2\16\2n{\u0083\u008b\u0161\u0167\u016e"+
		"\u0175\u0177\u0183\u018a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}