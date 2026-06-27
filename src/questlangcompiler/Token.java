package questlangcompiler;

public class Token {
    private final TokenType type;
    private final String lexeme;
    private final int line;
    private final int column;

    public Token(TokenType type, String lexeme, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        if (lexeme == null || lexeme.isEmpty()) {
            return String.format("%-14s at %d:%d", type, line, column);
        }
        return String.format("%-14s %-20s at %d:%d", type, lexeme, line, column);
    }
}
