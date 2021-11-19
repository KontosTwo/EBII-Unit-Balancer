// Generated from com/ebii/shoebopp/Edu.g4 by ANTLR 4.7.1
package com.ebii.shoebopp;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EduLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DecimalLiteral=1, StringLiteral=2, ASSIGN=3, ADD=4, SUB=5, MUL=6, DIV=7, 
		TERNTHEN=8, TERNELSE=9, EXIST=10, LPAREN=11, RPAREN=12, SEMI=13, KEEP=14, 
		MASS=15, MORALE=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"DecimalLiteral", "DecimalDigits", "DecimalDigit", "StringLiteral", "StringCharacter", 
		"ASSIGN", "ADD", "SUB", "MUL", "DIV", "TERNTHEN", "TERNELSE", "EXIST", 
		"LPAREN", "RPAREN", "SEMI", "KEEP", "MASS", "MORALE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, "'='", "'+'", "'-'", "'*'", "'/'", null, "':'", null, 
		"'('", "')'", "';'", "'keep'", "'mass'", "'morale'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "DecimalLiteral", "StringLiteral", "ASSIGN", "ADD", "SUB", "MUL", 
		"DIV", "TERNTHEN", "TERNELSE", "EXIST", "LPAREN", "RPAREN", "SEMI", "KEEP", 
		"MASS", "MORALE"
	};
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


	public EduLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Edu.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\22c\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\2\3\2\3\3\6\3/\n\3\r\3\16\3\60\3\4\3\4"+
		"\3\5\6\5\66\n\5\r\5\16\5\67\3\6\5\6;\n\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3"+
		"\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\2\2\25\3\3\5\2\7\2\t\4\13\2\r\5\17\6\21\7\23\b\25\t\27\n"+
		"\31\13\33\f\35\r\37\16!\17#\20%\21\'\22\3\2\4\3\2\62;\5\2C\\aac|\2a\2"+
		"\3\3\2\2\2\2\t\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2"+
		"\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2"+
		"\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\3)\3\2\2\2"+
		"\5.\3\2\2\2\7\62\3\2\2\2\t\65\3\2\2\2\13:\3\2\2\2\r<\3\2\2\2\17>\3\2\2"+
		"\2\21@\3\2\2\2\23B\3\2\2\2\25D\3\2\2\2\27F\3\2\2\2\31H\3\2\2\2\33J\3\2"+
		"\2\2\35L\3\2\2\2\37N\3\2\2\2!P\3\2\2\2#R\3\2\2\2%W\3\2\2\2\'\\\3\2\2\2"+
		")*\5\5\3\2*+\7\60\2\2+,\5\5\3\2,\4\3\2\2\2-/\5\7\4\2.-\3\2\2\2/\60\3\2"+
		"\2\2\60.\3\2\2\2\60\61\3\2\2\2\61\6\3\2\2\2\62\63\t\2\2\2\63\b\3\2\2\2"+
		"\64\66\5\13\6\2\65\64\3\2\2\2\66\67\3\2\2\2\67\65\3\2\2\2\678\3\2\2\2"+
		"8\n\3\2\2\29;\t\3\2\2:9\3\2\2\2;\f\3\2\2\2<=\7?\2\2=\16\3\2\2\2>?\7-\2"+
		"\2?\20\3\2\2\2@A\7/\2\2A\22\3\2\2\2BC\7,\2\2C\24\3\2\2\2DE\7\61\2\2E\26"+
		"\3\2\2\2FG\7A\2\2G\30\3\2\2\2HI\7<\2\2I\32\3\2\2\2JK\7A\2\2K\34\3\2\2"+
		"\2LM\7*\2\2M\36\3\2\2\2NO\7+\2\2O \3\2\2\2PQ\7=\2\2Q\"\3\2\2\2RS\7m\2"+
		"\2ST\7g\2\2TU\7g\2\2UV\7r\2\2V$\3\2\2\2WX\7o\2\2XY\7c\2\2YZ\7u\2\2Z[\7"+
		"u\2\2[&\3\2\2\2\\]\7o\2\2]^\7q\2\2^_\7t\2\2_`\7c\2\2`a\7n\2\2ab\7g\2\2"+
		"b(\3\2\2\2\6\2\60\67:\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}