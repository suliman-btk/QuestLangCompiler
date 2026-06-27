package questlangcompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestLangScanner {
    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();

    static {
        KEYWORDS.put("HERO", TokenType.HERO);
        KEYWORDS.put("BADGE", TokenType.BADGE);
        KEYWORDS.put("STAT", TokenType.STAT);
        KEYWORDS.put("TALE", TokenType.TALE);
        KEYWORDS.put("DEFINE", TokenType.DEFINE);
        KEYWORDS.put("TRICK", TokenType.TRICK);
        KEYWORDS.put("ENDTRICK", TokenType.ENDTRICK);
        KEYWORDS.put("PERFORM", TokenType.PERFORM);
        KEYWORDS.put("GRIND", TokenType.GRIND);
        KEYWORDS.put("TIMES", TokenType.TIMES);
        KEYWORDS.put("UNTIL", TokenType.UNTIL);
        KEYWORDS.put("ENDGRIND", TokenType.ENDGRIND);
        KEYWORDS.put("JOURNEY", TokenType.JOURNEY);
        KEYWORDS.put("FROM", TokenType.FROM);
        KEYWORDS.put("TO", TokenType.TO);
        KEYWORDS.put("ENDJOURNEY", TokenType.ENDJOURNEY);
        KEYWORDS.put("DECIDE", TokenType.DECIDE);
        KEYWORDS.put("OTHERWISE", TokenType.OTHERWISE);
        KEYWORDS.put("ENDDECIDE", TokenType.ENDDECIDE);
        KEYWORDS.put("CHOOSE", TokenType.CHOOSE);
        KEYWORDS.put("OPTION", TokenType.OPTION);
        KEYWORDS.put("ENDCHOOSE", TokenType.ENDCHOOSE);
        KEYWORDS.put("ASK", TokenType.ASK);
        KEYWORDS.put("SAY", TokenType.SAY);
        KEYWORDS.put("TRADE", TokenType.TRADE);
        KEYWORDS.put("DRAW", TokenType.DRAW);
        KEYWORDS.put("STAR", TokenType.STAR);
        KEYWORDS.put("BOX", TokenType.BOX);
        KEYWORDS.put("TRIANGLE", TokenType.TRIANGLE);
        KEYWORDS.put("LUCK", TokenType.LUCK);
        KEYWORDS.put("ROOT", TokenType.ROOT);
        KEYWORDS.put("ROUND", TokenType.ROUND);
        KEYWORDS.put("ABS", TokenType.ABS);
        KEYWORDS.put("BIGGER", TokenType.BIGGER);
        KEYWORDS.put("SMALLER", TokenType.SMALLER);
        KEYWORDS.put("PI", TokenType.PI);
        KEYWORDS.put("AND", TokenType.AND);
        KEYWORDS.put("OR", TokenType.OR);
        KEYWORDS.put("NOT", TokenType.NOT);
        KEYWORDS.put("THEEND", TokenType.THEEND);
    }

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int current = 0;
    private int line = 1;
    private int column = 1;

    public QuestLangScanner(String source) {
        this.source = source == null ? "" : source;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "", line, column));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        int tokenLine = line;
        int tokenColumn = column - 1;

        switch (c) {
            case ' ':
            case '\t':
            case '\r':
                return;
            case '\n':
                line++;
                column = 1;
                return;
            case '=':
                addToken(match('=') ? TokenType.EQ : TokenType.ASSIGN, tokenLine, tokenColumn, matchLexeme("=", "=="));
                return;
            case '<':
                if (match('>')) {
                    addToken(TokenType.NEQ, "<>", tokenLine, tokenColumn);
                } else if (match('=')) {
                    addToken(TokenType.LTE, "<=", tokenLine, tokenColumn);
                } else {
                    addToken(TokenType.LT, "<", tokenLine, tokenColumn);
                }
                return;
            case '>':
                addToken(match('=') ? TokenType.GTE : TokenType.GT, tokenLine, tokenColumn, previousLexeme(">", ">="));
                return;
            case '+':
                addToken(TokenType.PLUS, "+", tokenLine, tokenColumn);
                return;
            case '-':
                addToken(TokenType.MINUS, "-", tokenLine, tokenColumn);
                return;
            case '*':
                addToken(TokenType.MULTIPLY, "*", tokenLine, tokenColumn);
                return;
            case '/':
                addToken(TokenType.DIVIDE, "/", tokenLine, tokenColumn);
                return;
            case '(':
                addToken(TokenType.LPAREN, "(", tokenLine, tokenColumn);
                return;
            case ')':
                addToken(TokenType.RPAREN, ")", tokenLine, tokenColumn);
                return;
            case ',':
                addToken(TokenType.COMMA, ",", tokenLine, tokenColumn);
                return;
            case ':':
                addToken(TokenType.COLON, ":", tokenLine, tokenColumn);
                return;
            case '&':
                addToken(TokenType.AMPERSAND, "&", tokenLine, tokenColumn);
                return;
            case '"':
                scanString(tokenLine, tokenColumn);
                return;
            default:
                if (isDigit(c)) {
                    scanNumber(tokenLine, tokenColumn);
                } else if (isLetter(c)) {
                    scanWord(tokenLine, tokenColumn);
                } else {
                    throw error("Unexpected character '" + c + "'.", tokenLine, tokenColumn);
                }
        }
    }

    private void scanString(int tokenLine, int tokenColumn) {
        StringBuilder value = new StringBuilder();

        while (!isAtEnd() && peek() != '"') {
            char c = advance();
            if (c == '\n') {
                throw error("Text literal cannot continue to the next line.", tokenLine, tokenColumn);
            }
            value.append(c);
        }

        if (isAtEnd()) {
            throw error("Unterminated text literal.", tokenLine, tokenColumn);
        }

        advance();
        addToken(TokenType.TEXT_LITERAL, value.toString(), tokenLine, tokenColumn);
    }

    private void scanNumber(int tokenLine, int tokenColumn) {
        int start = current - 1;

        while (isDigit(peek())) {
            advance();
        }

        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) {
                advance();
            }
        }

        String lexeme = source.substring(start, current);
        addToken(TokenType.NUMBER, lexeme, tokenLine, tokenColumn);
    }

    private void scanWord(int tokenLine, int tokenColumn) {
        int start = current - 1;

        while (isLetter(peek())) {
            advance();
        }

        if (peek() == '$') {
            advance();
            String lexeme = source.substring(start, current);
            if (!lexeme.matches("[A-Za-z]{2}\\$")) {
                throw error("Invalid text-id '" + lexeme + "'. Text variables must be 2 letters followed by $.", tokenLine, tokenColumn);
            }
            addToken(TokenType.TEXT_ID, lexeme, tokenLine, tokenColumn);
            return;
        }

        if (isDigit(peek())) {
            advance();
            String lexeme = source.substring(start, current);
            if (!lexeme.matches("[A-Za-z]{2}[0-9]")) {
                throw error("Invalid num-id '" + lexeme + "'. Numeric variables must be 2 letters followed by 1 digit.", tokenLine, tokenColumn);
            }
            addToken(TokenType.NUM_ID, lexeme, tokenLine, tokenColumn);
            return;
        }

        String word = source.substring(start, current);
        if ("NOTE".equals(word)) {
            skipComment();
            return;
        }

        TokenType keywordType = KEYWORDS.get(word);
        if (keywordType != null) {
            addToken(keywordType, word, tokenLine, tokenColumn);
        } else if (word.matches("[A-Za-z]+")) {
            addToken(TokenType.NAME, word, tokenLine, tokenColumn);
        } else {
            throw error("Invalid word '" + word + "'.", tokenLine, tokenColumn);
        }
    }

    private void skipComment() {
        while (!isAtEnd() && peek() != '\n') {
            advance();
        }
    }

    private char advance() {
        char c = source.charAt(current);
        current++;
        column++;
        return c;
    }

    private boolean match(char expected) {
        if (isAtEnd() || source.charAt(current) != expected) {
            return false;
        }
        current++;
        column++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) {
            return '\0';
        }
        return source.charAt(current + 1);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private void addToken(TokenType type, int tokenLine, int tokenColumn, String lexeme) {
        tokens.add(new Token(type, lexeme, tokenLine, tokenColumn));
    }

    private void addToken(TokenType type, String lexeme, int tokenLine, int tokenColumn) {
        tokens.add(new Token(type, lexeme, tokenLine, tokenColumn));
    }

    private String matchLexeme(String single, String doubleLexeme) {
        return source.charAt(current - 1) == '=' && current >= 2 && source.charAt(current - 2) == '=' ? doubleLexeme : single;
    }

    private String previousLexeme(String single, String doubleLexeme) {
        return current >= 2 && source.charAt(current - 1) == '=' ? doubleLexeme : single;
    }

    private ScannerException error(String message, int tokenLine, int tokenColumn) {
        return new ScannerException("Lexical Error at " + tokenLine + ":" + tokenColumn + " - " + message);
    }
}
