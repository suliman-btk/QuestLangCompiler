package questlangcompiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Builds and prints an indented parse tree for a QuestLang token stream.
 *
 * This class is used for demonstration only. It performs a second top-down
 * (recursive-descent) walk over the tokens that have ALREADY been accepted by
 * QuestLangParser, and prints the resulting parse tree to the terminal using
 * tree connectors. It never changes the compilation result: the caller wraps
 * printParseTree(...) in a try/catch so any issue here cannot stop the compiler.
 */
public final class QuestLangParseTree {

    /** Simple parse-tree node: a grammar symbol (label) with ordered children. */
    private static final class Node {
        final String label;
        final List<Node> children = new ArrayList<>();
        Node(String label) { this.label = label; }
        Node add(Node child) { children.add(child); return child; }
    }

    /** Internal signal used only for the GRIND look-ahead rollback. */
    private static final class TreeError extends RuntimeException {
        TreeError(String m) { super(m); }
    }

    private final List<Token> tokens;
    private int pos = 0;

    private QuestLangParseTree(List<Token> tokens) { this.tokens = tokens; }

    /**
     * Entry point called from the compiler after syntax analysis succeeds.
     * The parse tree is written to the given .txt file instead of the terminal.
     */
    public static void printParseTree(List<Token> tokens, Path targetFile) {
        QuestLangParseTree builder = new QuestLangParseTree(tokens);
        Node root = builder.program();

        StringBuilder sb = new StringBuilder();
        sb.append("QuestLang Parse Tree").append(System.lineSeparator());
        sb.append(root.label).append(System.lineSeparator());
        render(root, "", sb);

        try {
            if (targetFile.getParent() != null) {
                Files.createDirectories(targetFile.getParent());
            }
            Files.writeString(targetFile, sb.toString(), StandardCharsets.UTF_8);
            System.out.println("Parse tree written to:      " + targetFile.toAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not write parse tree file: " + ex.getMessage());
        }
    }

    // ---------- token cursor helpers ----------

    private Token peek() { return tokens.get(pos); }

    private boolean check(TokenType t) { return peek().getType() == t; }

    private boolean isAtEnd() { return peek().getType() == TokenType.EOF; }

    private Token advance() { return tokens.get(pos++); }

    private boolean match(TokenType t) {
        if (check(t)) { advance(); return true; }
        return false;
    }

    private Token expect(TokenType t) {
        if (check(t)) { return advance(); }
        throw new TreeError("Expected " + t + " but found " + peek().getType());
    }

    /** Leaf node showing the terminal type and its spelling. */
    private Node terminal() {
        Token tk = advance();
        String lex = tk.getLexeme();
        return new Node(tk.getType() + " \"" + lex + "\"");
    }

    private Node terminal(TokenType t) {
        Token tk = expect(t);
        return new Node(tk.getType() + " \"" + tk.getLexeme() + "\"");
    }

    // ---------- grammar rules ----------

    private Node program() {
        Node n = new Node("<program>");
        n.add(idSection());
        n.add(declSection());
        while (check(TokenType.DEFINE)) {
            n.add(trickDef());
        }
        n.add(stmtListUntil(n, TokenType.THEEND));
        n.add(terminal(TokenType.THEEND));
        return n;
    }

    private Node idSection() {
        Node n = new Node("<id-section>");
        n.add(terminal(TokenType.HERO));
        n.add(terminal(TokenType.NAME));
        if (check(TokenType.BADGE)) {
            n.add(terminal(TokenType.BADGE));
            n.add(terminal(TokenType.NUMBER));
        }
        return n;
    }

    private Node declSection() {
        Node n = new Node("<decl-section>");
        while (check(TokenType.STAT) || check(TokenType.TALE)) {
            n.add(decl());
        }
        return n;
    }

    private Node decl() {
        Node n = new Node("<decl>");
        if (match(TokenType.STAT)) {
            n.add(new Node("STAT"));
            n.add(terminal(TokenType.NUM_ID));
            while (match(TokenType.COMMA)) {
                n.add(new Node("\",\""));
                n.add(terminal(TokenType.NUM_ID));
            }
        } else {
            expect(TokenType.TALE);
            n.add(new Node("TALE"));
            n.add(terminal(TokenType.TEXT_ID));
            while (match(TokenType.COMMA)) {
                n.add(new Node("\",\""));
                n.add(terminal(TokenType.TEXT_ID));
            }
        }
        return n;
    }

    private Node trickDef() {
        Node n = new Node("<trick-def>");
        n.add(terminal(TokenType.DEFINE));
        n.add(terminal(TokenType.TRICK));
        n.add(terminal(TokenType.NAME));
        n.add(stmtListUntil(n, TokenType.ENDTRICK));
        n.add(terminal(TokenType.ENDTRICK));
        return n;
    }

    /** Parses a statement list until (but not consuming) any stop token. */
    private Node stmtListUntil(Node parent, TokenType... stops) {
        Node n = new Node("<stmt-list>");
        while (!isAtEnd() && !isStop(stops)) {
            n.add(statement());
        }
        return n;
    }

    private boolean isStop(TokenType[] stops) {
        for (TokenType s : stops) {
            if (check(s)) { return true; }
        }
        return false;
    }

    private Node statement() {
        Node n = new Node("<stmt>");
        switch (peek().getType()) {
            case NUM_ID:
            case TEXT_ID:
                n.add(assignment());
                break;
            case GRIND:
            case JOURNEY:
                n.add(loop());
                break;
            case DECIDE:
                n.add(decision());
                break;
            case CHOOSE:
                n.add(choose());
                break;
            case ASK:
                n.add(ask());
                break;
            case SAY:
                n.add(say());
                break;
            case TRADE:
                n.add(trade());
                break;
            case PERFORM:
                n.add(perform());
                break;
            case DRAW:
                n.add(draw());
                break;
            default:
                throw new TreeError("Unexpected statement start: " + peek().getType());
        }
        return n;
    }

    private Node assignment() {
        Node n = new Node("<assignment>");
        if (check(TokenType.TEXT_ID)) {
            n.add(terminal(TokenType.TEXT_ID));
            n.add(terminal(TokenType.ASSIGN));
            n.add(textAssignExpr());
        } else {
            n.add(terminal(TokenType.NUM_ID));
            n.add(terminal(TokenType.ASSIGN));
            n.add(expr());
        }
        return n;
    }

    private Node loop() {
        Node n = new Node("<loop>");
        if (match(TokenType.JOURNEY)) {
            n.add(new Node("JOURNEY"));
            n.add(terminal(TokenType.FROM));
            n.add(expr());
            n.add(terminal(TokenType.TO));
            n.add(expr());
            n.add(stmtListUntil(n, TokenType.ENDJOURNEY));
            n.add(terminal(TokenType.ENDJOURNEY));
            return n;
        }

        expect(TokenType.GRIND);
        n.add(new Node("GRIND"));
        int save = pos;
        // Try the GRIND <expr> TIMES form; roll back to GRIND ... UNTIL if it fails.
        try {
            Node exprNode = expr();
            if (check(TokenType.TIMES)) {
                n.add(exprNode);
                n.add(terminal(TokenType.TIMES));
                n.add(stmtListUntil(n, TokenType.ENDGRIND));
                n.add(terminal(TokenType.ENDGRIND));
                return n;
            }
        } catch (TreeError ignore) {
            // fall through to UNTIL form
        }
        pos = save;
        n.add(stmtListUntil(n, TokenType.UNTIL));
        n.add(terminal(TokenType.UNTIL));
        n.add(condition());
        return n;
    }

    private Node decision() {
        Node n = new Node("<decision>");
        n.add(terminal(TokenType.DECIDE));
        n.add(condition());
        n.add(stmtListUntil(n, TokenType.OTHERWISE, TokenType.ENDDECIDE));
        if (match(TokenType.OTHERWISE)) {
            n.add(new Node("OTHERWISE"));
            n.add(stmtListUntil(n, TokenType.ENDDECIDE));
        }
        n.add(terminal(TokenType.ENDDECIDE));
        return n;
    }

    private Node choose() {
        Node n = new Node("<choose>");
        n.add(terminal(TokenType.CHOOSE));
        n.add(expr());
        while (check(TokenType.OPTION)) {
            n.add(option());
        }
        if (match(TokenType.OTHERWISE)) {
            n.add(new Node("OTHERWISE"));
            n.add(stmtListUntil(n, TokenType.ENDCHOOSE));
        }
        n.add(terminal(TokenType.ENDCHOOSE));
        return n;
    }

    private Node option() {
        Node n = new Node("<option>");
        n.add(terminal(TokenType.OPTION));
        n.add(terminal(TokenType.NUMBER));
        n.add(terminal(TokenType.COLON));
        n.add(stmtListUntil(n, TokenType.OPTION, TokenType.OTHERWISE, TokenType.ENDCHOOSE));
        return n;
    }

    private Node ask() {
        Node n = new Node("<ask>");
        n.add(terminal(TokenType.ASK));
        n.add(terminal(TokenType.TEXT_LITERAL));
        n.add(terminal(TokenType.COMMA));
        n.add(idNode());
        return n;
    }

    private Node say() {
        Node n = new Node("<say>");
        n.add(terminal(TokenType.SAY));
        n.add(textExpr());
        return n;
    }

    private Node trade() {
        Node n = new Node("<trade>");
        n.add(terminal(TokenType.TRADE));
        n.add(idNode());
        n.add(terminal(TokenType.COMMA));
        n.add(idNode());
        return n;
    }

    private Node perform() {
        Node n = new Node("<perform>");
        n.add(terminal(TokenType.PERFORM));
        n.add(terminal(TokenType.NAME));
        return n;
    }

    private Node draw() {
        Node n = new Node("<draw>");
        n.add(terminal(TokenType.DRAW));
        Node shape = new Node("<shape>");
        if (match(TokenType.STAR)) { shape.add(new Node("STAR")); }
        else if (match(TokenType.BOX)) { shape.add(new Node("BOX")); }
        else { expect(TokenType.TRIANGLE); shape.add(new Node("TRIANGLE")); }
        n.add(shape);
        n.add(expr());
        return n;
    }

    private Node idNode() {
        Node n = new Node("<id>");
        if (check(TokenType.TEXT_ID)) { n.add(terminal(TokenType.TEXT_ID)); }
        else { n.add(terminal(TokenType.NUM_ID)); }
        return n;
    }

    // ---------- conditions ----------

    private Node condition() {
        Node n = new Node("<condition>");
        n.add(orCondition());
        return n;
    }

    private Node orCondition() {
        Node n = new Node("<or-condition>");
        n.add(andCondition());
        while (check(TokenType.OR)) {
            n.add(terminal(TokenType.OR));
            n.add(andCondition());
        }
        return n;
    }

    private Node andCondition() {
        Node n = new Node("<and-condition>");
        n.add(notCondition());
        while (check(TokenType.AND)) {
            n.add(terminal(TokenType.AND));
            n.add(notCondition());
        }
        return n;
    }

    private Node notCondition() {
        Node n = new Node("<not-condition>");
        if (match(TokenType.NOT)) {
            n.add(new Node("NOT"));
            n.add(notCondition());
        } else if (match(TokenType.LPAREN)) {
            n.add(new Node("\"(\""));
            n.add(condition());
            n.add(terminal(TokenType.RPAREN));
        } else {
            n.add(relation());
        }
        return n;
    }

    private Node relation() {
        Node n = new Node("<relation>");
        n.add(expr());
        n.add(relop());
        n.add(expr());
        return n;
    }

    private Node relop() {
        switch (peek().getType()) {
            case EQ: case NEQ: case LT: case GT: case LTE: case GTE:
                return terminal();
            default:
                throw new TreeError("Expected relational operator, found " + peek().getType());
        }
    }

    // ---------- text expressions ----------

    private Node textExpr() {
        Node n = new Node("<text-expr>");
        n.add(textItem());
        while (check(TokenType.AMPERSAND)) {
            n.add(terminal(TokenType.AMPERSAND));
            n.add(textItem());
        }
        return n;
    }

    private Node textItem() {
        Node n = new Node("<text-item>");
        if (check(TokenType.TEXT_LITERAL)) { n.add(terminal(TokenType.TEXT_LITERAL)); }
        else if (check(TokenType.TEXT_ID)) { n.add(terminal(TokenType.TEXT_ID)); }
        else { n.add(expr()); }
        return n;
    }

    private Node textAssignExpr() {
        Node n = new Node("<text-assign-expr>");
        n.add(textAssignItem());
        while (check(TokenType.AMPERSAND)) {
            n.add(terminal(TokenType.AMPERSAND));
            n.add(textAssignItem());
        }
        return n;
    }

    private Node textAssignItem() {
        Node n = new Node("<text-assign-item>");
        if (check(TokenType.TEXT_LITERAL)) { n.add(terminal(TokenType.TEXT_LITERAL)); }
        else { n.add(terminal(TokenType.TEXT_ID)); }
        return n;
    }

    // ---------- arithmetic expressions ----------

    private Node expr() {
        Node n = new Node("<expr>");
        n.add(term());
        while (check(TokenType.PLUS) || check(TokenType.MINUS)) {
            n.add(terminal());
            n.add(term());
        }
        return n;
    }

    private Node term() {
        Node n = new Node("<term>");
        n.add(factor());
        while (check(TokenType.MULTIPLY) || check(TokenType.DIVIDE)) {
            n.add(terminal());
            n.add(factor());
        }
        return n;
    }

    private Node factor() {
        Node n = new Node("<factor>");
        switch (peek().getType()) {
            case NUM_ID:
                n.add(terminal(TokenType.NUM_ID));
                break;
            case NUMBER:
                n.add(terminal(TokenType.NUMBER));
                break;
            case PI:
                n.add(terminal(TokenType.PI));
                break;
            case LPAREN:
                n.add(new Node("\"(\""));
                advance();
                n.add(expr());
                n.add(terminal(TokenType.RPAREN));
                break;
            case LUCK:
            case ROOT:
            case ROUND:
            case ABS:
            case BIGGER:
            case SMALLER:
                n.add(builtinCall());
                break;
            default:
                throw new TreeError("Expected a factor, found " + peek().getType());
        }
        return n;
    }

    private Node builtinCall() {
        Node n = new Node("<builtin-call>");
        Token fn = advance();
        n.add(new Node(fn.getType() + " \"" + fn.getLexeme() + "\""));
        n.add(terminal(TokenType.LPAREN));
        n.add(expr());
        if (fn.getType() == TokenType.LUCK
                || fn.getType() == TokenType.BIGGER
                || fn.getType() == TokenType.SMALLER) {
            n.add(terminal(TokenType.COMMA));
            n.add(expr());
        }
        n.add(terminal(TokenType.RPAREN));
        return n;
    }

    // ---------- rendering with tree connectors ----------

    private static void render(Node node, String prefix, StringBuilder sb) {
        for (int i = 0; i < node.children.size(); i++) {
            Node child = node.children.get(i);
            boolean last = (i == node.children.size() - 1);
            sb.append(prefix).append(last ? "\\-- " : "|-- ")
              .append(child.label).append(System.lineSeparator());
            render(child, prefix + (last ? "    " : "|   "), sb);
        }
    }
}
