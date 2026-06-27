package questlangcompiler;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuestLangSemanticAnalyzer {
    private final List<Token> tokens;
    private final Map<String, Symbol> symbols = new HashMap<>();
    private final Set<String> definedTricks = new HashSet<>();
    private int current = 0;

    public QuestLangSemanticAnalyzer(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void analyze() {
        analyzeProgram();
    }

    private void analyzeProgram() {
        consume(TokenType.HERO);
        consume(TokenType.NAME);

        if (match(TokenType.BADGE)) {
            consume(TokenType.NUMBER);
        }

        analyzeDeclarations();

        while (check(TokenType.DEFINE)) {
            analyzeTrickDef();
        }

        analyzeStatementList(EnumSet.of(TokenType.THEEND));
        consume(TokenType.THEEND);
        consume(TokenType.EOF);
    }

    private void analyzeDeclarations() {
        while (match(TokenType.STAT, TokenType.TALE)) {
            SymbolType type = previous().getType() == TokenType.STAT ? SymbolType.NUMERIC : SymbolType.TEXT;
            declare(consume(type == SymbolType.NUMERIC ? TokenType.NUM_ID : TokenType.TEXT_ID), type);

            while (match(TokenType.COMMA)) {
                declare(consume(type == SymbolType.NUMERIC ? TokenType.NUM_ID : TokenType.TEXT_ID), type);
            }
        }
    }

    private void analyzeTrickDef() {
        consume(TokenType.DEFINE);
        consume(TokenType.TRICK);
        Token trickName = consume(TokenType.NAME);

        if (definedTricks.contains(trickName.getLexeme())) {
            throw error(trickName, "Trick '" + trickName.getLexeme() + "' is already defined.");
        }

        analyzeStatementList(EnumSet.of(TokenType.ENDTRICK));
        consume(TokenType.ENDTRICK);
        definedTricks.add(trickName.getLexeme());
    }

    private void analyzeStatementList(Set<TokenType> stopTokens) {
        while (!isAtEnd() && !stopTokens.contains(peek().getType())) {
            analyzeStatement();
        }
    }

    private void analyzeStatement() {
        if (check(TokenType.NUM_ID) || check(TokenType.TEXT_ID)) {
            analyzeAssignment();
        } else if (check(TokenType.GRIND) || check(TokenType.JOURNEY)) {
            analyzeLoop();
        } else if (check(TokenType.DECIDE)) {
            analyzeDecide();
        } else if (check(TokenType.CHOOSE)) {
            analyzeChoose();
        } else if (check(TokenType.ASK)) {
            analyzeAsk();
        } else if (check(TokenType.SAY)) {
            analyzeSay();
        } else if (check(TokenType.TRADE)) {
            analyzeTrade();
        } else if (check(TokenType.PERFORM)) {
            analyzePerform();
        } else if (check(TokenType.DRAW)) {
            analyzeDraw();
        } else {
            advance();
        }
    }

    private void analyzeAssignment() {
        Token target = advance();
        Symbol targetSymbol = requireDeclared(target);
        consume(TokenType.ASSIGN);

        if (targetSymbol.getType() == SymbolType.NUMERIC) {
            analyzeExpression();
        } else {
            analyzeTextAssignmentExpression();
        }
    }

    private void analyzeLoop() {
        if (match(TokenType.JOURNEY)) {
            consume(TokenType.FROM);
            analyzeExpression();
            consume(TokenType.TO);
            analyzeExpression();
            analyzeStatementList(EnumSet.of(TokenType.ENDJOURNEY));
            consume(TokenType.ENDJOURNEY);
            return;
        }

        consume(TokenType.GRIND);
        int afterGrind = current;

        if (canReadGrindTimesForm()) {
            analyzeExpression();
            consume(TokenType.TIMES);
            analyzeStatementList(EnumSet.of(TokenType.ENDGRIND));
            consume(TokenType.ENDGRIND);
            return;
        }

        current = afterGrind;
        analyzeStatementList(EnumSet.of(TokenType.UNTIL));
        consume(TokenType.UNTIL);
        analyzeCondition();
    }

    private boolean canReadGrindTimesForm() {
        int saved = current;
        try {
            skipExpression();
            return match(TokenType.TIMES);
        } catch (RuntimeException ex) {
            return false;
        } finally {
            current = saved;
        }
    }

    private void analyzeDecide() {
        consume(TokenType.DECIDE);
        analyzeCondition();
        analyzeStatementList(EnumSet.of(TokenType.OTHERWISE, TokenType.ENDDECIDE));

        if (match(TokenType.OTHERWISE)) {
            analyzeStatementList(EnumSet.of(TokenType.ENDDECIDE));
        }

        consume(TokenType.ENDDECIDE);
    }

    private void analyzeChoose() {
        consume(TokenType.CHOOSE);
        analyzeExpression();

        Set<String> optionLabels = new HashSet<>();
        while (check(TokenType.OPTION)) {
            consume(TokenType.OPTION);
            Token label = consume(TokenType.NUMBER);
            if (!optionLabels.add(label.getLexeme())) {
                throw error(label, "Duplicate OPTION label '" + label.getLexeme() + "' in CHOOSE block.");
            }
            consume(TokenType.COLON);
            analyzeStatementList(EnumSet.of(TokenType.OPTION, TokenType.OTHERWISE, TokenType.ENDCHOOSE));
        }

        if (match(TokenType.OTHERWISE)) {
            analyzeStatementList(EnumSet.of(TokenType.ENDCHOOSE));
        }

        consume(TokenType.ENDCHOOSE);
    }

    private void analyzeAsk() {
        consume(TokenType.ASK);
        consume(TokenType.TEXT_LITERAL);
        consume(TokenType.COMMA);
        requireDeclared(consumeId());
    }

    private void analyzeSay() {
        consume(TokenType.SAY);
        analyzeTextExpression();
    }

    private void analyzeTrade() {
        consume(TokenType.TRADE);
        Symbol first = requireDeclared(consumeId());
        consume(TokenType.COMMA);
        Symbol second = requireDeclared(consumeId());

        if (first.getType() != second.getType()) {
            throw error(previous(), "TRADE variables must have the same type.");
        }
    }

    private void analyzePerform() {
        consume(TokenType.PERFORM);
        Token trickName = consume(TokenType.NAME);

        if (!definedTricks.contains(trickName.getLexeme())) {
            throw error(trickName, "Trick '" + trickName.getLexeme() + "' is not defined.");
        }
    }

    private void analyzeDraw() {
        consume(TokenType.DRAW);
        advance(); // STAR, BOX, or TRIANGLE was already checked by parser.
        analyzeExpression();
    }

    private void analyzeCondition() {
        analyzeOrCondition();
    }

    private void analyzeOrCondition() {
        analyzeAndCondition();
        while (match(TokenType.OR)) {
            analyzeAndCondition();
        }
    }

    private void analyzeAndCondition() {
        analyzeNotCondition();
        while (match(TokenType.AND)) {
            analyzeNotCondition();
        }
    }

    private void analyzeNotCondition() {
        if (match(TokenType.NOT)) {
            analyzeNotCondition();
        } else if (match(TokenType.LPAREN)) {
            analyzeCondition();
            consume(TokenType.RPAREN);
        } else {
            analyzeExpression();
            advance(); // relational operator
            analyzeExpression();
        }
    }

    private void analyzeTextExpression() {
        analyzeTextItem();
        while (match(TokenType.AMPERSAND)) {
            analyzeTextItem();
        }
    }

    private void analyzeTextAssignmentExpression() {
        analyzeTextAssignmentItem();
        while (match(TokenType.AMPERSAND)) {
            analyzeTextAssignmentItem();
        }
    }

    private void analyzeTextAssignmentItem() {
        if (match(TokenType.TEXT_LITERAL)) {
            return;
        }

        if (check(TokenType.TEXT_ID)) {
            requireDeclared(advance(), SymbolType.TEXT);
            return;
        }

        throw error(peek(), "Text assignment can only use text literals or text variables.");
    }

    private void analyzeTextItem() {
        if (match(TokenType.TEXT_LITERAL)) {
            return;
        }

        if (check(TokenType.TEXT_ID)) {
            requireDeclared(advance(), SymbolType.TEXT);
            return;
        }

        analyzeExpression();
    }

    private void analyzeExpression() {
        analyzeTerm();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            analyzeTerm();
        }
    }

    private void analyzeTerm() {
        analyzeFactor();
        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            analyzeFactor();
        }
    }

    private void analyzeFactor() {
        if (check(TokenType.NUM_ID)) {
            requireDeclared(advance(), SymbolType.NUMERIC);
        } else if (match(TokenType.NUMBER, TokenType.PI)) {
            return;
        } else if (match(TokenType.LPAREN)) {
            analyzeExpression();
            consume(TokenType.RPAREN);
        } else if (startsBuiltinCall(peek().getType())) {
            analyzeBuiltinCall();
        } else {
            throw error(peek(), "Expected numeric value.");
        }
    }

    private void analyzeBuiltinCall() {
        Token functionName = advance();
        consume(TokenType.LPAREN);
        analyzeExpression();

        if (functionName.getType() == TokenType.LUCK
                || functionName.getType() == TokenType.BIGGER
                || functionName.getType() == TokenType.SMALLER) {
            consume(TokenType.COMMA);
            analyzeExpression();
        }

        consume(TokenType.RPAREN);
    }

    private void skipExpression() {
        skipTerm();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            skipTerm();
        }
    }

    private void skipTerm() {
        skipFactor();
        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            skipFactor();
        }
    }

    private void skipFactor() {
        if (match(TokenType.NUM_ID, TokenType.NUMBER, TokenType.PI)) {
            return;
        }

        if (match(TokenType.LPAREN)) {
            skipExpression();
            consume(TokenType.RPAREN);
            return;
        }

        if (startsBuiltinCall(peek().getType())) {
            advance();
            consume(TokenType.LPAREN);
            skipExpression();
            if (match(TokenType.COMMA)) {
                skipExpression();
            }
            consume(TokenType.RPAREN);
            return;
        }

        throw error(peek(), "Expected numeric value.");
    }

    private void declare(Token token, SymbolType type) {
        Symbol existing = symbols.get(token.getLexeme());
        if (existing != null) {
            throw error(token, "Variable '" + token.getLexeme() + "' was already declared at "
                    + existing.getLine() + ":" + existing.getColumn() + ".");
        }

        symbols.put(token.getLexeme(), new Symbol(token.getLexeme(), type, token.getLine(), token.getColumn()));
    }

    private Symbol requireDeclared(Token token) {
        Symbol symbol = symbols.get(token.getLexeme());
        if (symbol == null) {
            throw error(token, "Variable '" + token.getLexeme() + "' is not declared.");
        }
        return symbol;
    }

    private Symbol requireDeclared(Token token, SymbolType expectedType) {
        Symbol symbol = requireDeclared(token);
        if (symbol.getType() != expectedType) {
            throw error(token, "Variable '" + token.getLexeme() + "' must be "
                    + expectedType.name().toLowerCase() + ".");
        }
        return symbol;
    }

    private Token consumeId() {
        if (check(TokenType.NUM_ID) || check(TokenType.TEXT_ID)) {
            return advance();
        }
        throw error(peek(), "Expected variable.");
    }

    private Token consume(TokenType type) {
        if (check(type)) {
            return advance();
        }
        throw error(peek(), "Expected " + type + ".");
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return type == TokenType.EOF;
        }
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean startsBuiltinCall(TokenType type) {
        return type == TokenType.LUCK
                || type == TokenType.ROOT
                || type == TokenType.ROUND
                || type == TokenType.ABS
                || type == TokenType.BIGGER
                || type == TokenType.SMALLER;
    }

    private SemanticException error(Token token, String message) {
        return new SemanticException("Semantic Error at " + token.getLine() + ":" + token.getColumn()
                + " near '" + token.getLexeme() + "' - " + message);
    }
}
