package questlangcompiler;

public class Symbol {
    private final String name;
    private final SymbolType type;
    private final int line;
    private final int column;

    public Symbol(String name, SymbolType type, int line, int column) {
        this.name = name;
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public SymbolType getType() {
        return type;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
