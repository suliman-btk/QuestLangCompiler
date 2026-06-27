package questlangcompiler;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class QuestLangParser {
    private final List<Token> tokens;
    private int current = 0;

    public QuestLangParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        parseProgram();
    }

    private void parseProgram() {
        parseIdSection();
        parseDeclSection();

        while (check(TokenType.DEFINE)) {
            parseTrickDef();
        }

        parseStatementList(EnumSet.of(TokenType.THEEND), "main program", true);
        consume(TokenType.THEEND, "Expected THEEND at the end of the program.");
        consume(TokenType.EOF, "Expected end of file after THEEND.");
    }

    private void parseIdSection() {
        consume(TokenType.HERO, "Program must start with HERO.");
        consume(TokenType.NAME, "Expected programmer name after HERO.");

        if (match(TokenType.BADGE)) {
            Token badge = consume(TokenType.NUMBER, "Expected badge number after BADGE.");
            requireInteger(badge, "BADGE must use an integer number.");
        }
    }

    private void parseDeclSection() {
        if (!check(TokenType.STAT) && !check(TokenType.TALE)) {
            throw error(peek(), "Expected declaration section starting with STAT or TALE.");
        }

        while (match(TokenType.STAT, TokenType.TALE)) {
            TokenType declarationType = previous().getType();
            if (declarationType == TokenType.STAT) {
                parseIdList(TokenType.NUM_ID, "Expected numeric variable after STAT.");
            } else {
                parseIdList(TokenType.TEXT_ID, "Expected text variable after TALE.");
            }
        }
    }

    private void parseIdList(TokenType idType, String message) {
        consume(idType, message);
        while (match(TokenType.COMMA)) {
            consume(idType, "Expected another variable after comma.");
        }
    }

    private void parseTrickDef() {
        consume(TokenType.DEFINE, "Expected DEFINE.");
        consume(TokenType.TRICK, "Expected TRICK after DEFINE.");
        consume(TokenType.NAME, "Expected trick name after DEFINE TRICK.");
        parseStatementList(EnumSet.of(TokenType.ENDTRICK), "trick body", true);
        consume(TokenType.ENDTRICK, "Expected ENDTRICK after trick body.");
    }

    private void parseStatementList(Set<TokenType> stopTokens, String context, boolean requireOne) {
        int count = 0;
        while (!isAtEnd() && !stopTokens.contains(peek().getType())) {
            if (isClosingToken(peek().getType())) {
                throw error(peek(), "Expected " + formatExpectedStopTokens(stopTokens)
                        + " before " + peek().getType() + ".");
            }
            parseStatement(stopTokens);
            count++;
        }

        if (requireOne && count == 0) {
            throw error(peek(), "Expected at least one statement in " + context + ".");
        }
    }

    private void parseStatement(Set<TokenType> activeStopTokens) {
        if (activeStopTokens.contains(peek().getType())) {
            return;
        }

        if (check(TokenType.NUM_ID) || check(TokenType.TEXT_ID)) {
            parseAssignment();
        } else if (check(TokenType.GRIND) || check(TokenType.JOURNEY)) {
            parseLoop();
        } else if (check(TokenType.DECIDE)) {
            parseConditionStatement();
        } else if (check(TokenType.CHOOSE)) {
            parseChooseStatement();
        } else if (check(TokenType.ASK)) {
            parseInput();
        } else if (check(TokenType.SAY)) {
            parseOutput();
        } else if (check(TokenType.TRADE)) {
            parseTrade();
        } else if (check(TokenType.PERFORM)) {
            parsePerform();
        } else if (check(TokenType.DRAW)) {
            parseDraw();
        } else {
            throw error(peek(), "Expected a statement.");
        }
    }

    private void parseAssignment() {
        Token id = advance();
        consume(TokenType.ASSIGN, "Expected '=' after variable name.");

        if (id.getType() == TokenType.NUM_ID) {
            parseExpression();
        } else {
            parseTextExpression();
        }
    }

    private void parseLoop() {
        if (match(TokenType.JOURNEY)) {
            consume(TokenType.FROM, "Expected FROM after JOURNEY.");
            parseExpression();
            consume(TokenType.TO, "Expected TO in JOURNEY loop.");
            parseExpression();
            parseStatementList(EnumSet.of(TokenType.ENDJOURNEY), "JOURNEY body", true);
            consume(TokenType.ENDJOURNEY, "Expected ENDJOURNEY after JOURNEY body.");
            return;
        }

        consume(TokenType.GRIND, "Expected GRIND.");
        int afterGrind = current;

        try {
            parseExpression();
            if (match(TokenType.TIMES)) {
                parseStatementList(EnumSet.of(TokenType.ENDGRIND), "GRIND TIMES body", true);
                consume(TokenType.ENDGRIND, "Expected ENDGRIND after GRIND TIMES body.");
                return;
            }
        } catch (ParserException ex) {
            // If GRIND is not followed by an expression TIMES form, try GRIND ... UNTIL.
        }

        current = afterGrind;
        parseStatementList(EnumSet.of(TokenType.UNTIL), "GRIND UNTIL body", true);
        consume(TokenType.UNTIL, "Expected UNTIL after GRIND body.");
        parseCondition();
    }

    private void parseConditionStatement() {
        consume(TokenType.DECIDE, "Expected DECIDE.");
        parseCondition();
        parseStatementList(EnumSet.of(TokenType.OTHERWISE, TokenType.ENDDECIDE), "DECIDE body", true);

        if (match(TokenType.OTHERWISE)) {
            parseStatementList(EnumSet.of(TokenType.ENDDECIDE), "OTHERWISE body", true);
        }

        consume(TokenType.ENDDECIDE, "Expected ENDDECIDE after DECIDE statement.");
    }

    private void parseChooseStatement() {
        consume(TokenType.CHOOSE, "Expected CHOOSE.");
        parseExpression();

        if (!check(TokenType.OPTION)) {
            throw error(peek(), "Expected at least one OPTION inside CHOOSE.");
        }

        while (check(TokenType.OPTION)) {
            parseOption();
        }

        if (match(TokenType.OTHERWISE)) {
            parseStatementList(EnumSet.of(TokenType.ENDCHOOSE), "CHOOSE OTHERWISE body", true);
        }

        consume(TokenType.ENDCHOOSE, "Expected ENDCHOOSE after CHOOSE statement.");
    }

    private void parseOption() {
        consume(TokenType.OPTION, "Expected OPTION.");
        Token label = consume(TokenType.NUMBER, "Expected integer label after OPTION.");
        requireInteger(label, "OPTION label must be an integer.");
        consume(TokenType.COLON, "Expected ':' after OPTION label.");
        parseStatementList(EnumSet.of(TokenType.OPTION, TokenType.OTHERWISE, TokenType.ENDCHOOSE), "OPTION body", true);
    }

    private void parseInput() {
        consume(TokenType.ASK, "Expected ASK.");
        consume(TokenType.TEXT_LITERAL, "Expected prompt text after ASK.");
        consume(TokenType.COMMA, "Expected comma after ASK prompt.");
        consumeId("Expected input variable after comma.");
    }

    private void parseOutput() {
        consume(TokenType.SAY, "Expected SAY.");
        parseTextExpression();
    }

    private void parseTrade() {
        consume(TokenType.TRADE, "Expected TRADE.");
        consumeId("Expected first variable after TRADE.");
        consume(TokenType.COMMA, "Expected comma between TRADE variables.");
        consumeId("Expected second variable after comma.");
    }

    private void parsePerform() {
        consume(TokenType.PERFORM, "Expected PERFORM.");
        consume(TokenType.NAME, "Expected trick name after PERFORM.");
    }

    private void parseDraw() {
        consume(TokenType.DRAW, "Expected DRAW.");
        if (!match(TokenType.STAR, TokenType.BOX, TokenType.TRIANGLE)) {
            throw error(peek(), "Expected STAR, BOX, or TRIANGLE after DRAW.");
        }
        parseExpression();
    }

    private void parseCondition() {
        parseOrCondition();
    }

    private void parseOrCondition() {
        parseAndCondition();
        while (match(TokenType.OR)) {
            parseAndCondition();
        }
    }

    private void parseAndCondition() {
        parseNotCondition();
        while (match(TokenType.AND)) {
            parseNotCondition();
        }
    }

    private void parseNotCondition() {
        if (match(TokenType.NOT)) {
            parseNotCondition();
        } else if (match(TokenType.LPAREN)) {
            parseCondition();
            consume(TokenType.RPAREN, "Expected ')' after condition.");
        } else {
            parseRelation();
        }
    }

    private void parseRelation() {
        parseExpression();
        if (!match(TokenType.EQ, TokenType.NEQ, TokenType.LT, TokenType.GT, TokenType.LTE, TokenType.GTE)) {
            throw error(peek(), "Expected relational operator in condition.");
        }
        parseExpression();
    }

    private void parseTextExpression() {
        parseTextItem();
        while (match(TokenType.AMPERSAND)) {
            parseTextItem();
        }
    }

    private void parseTextItem() {
        if (match(TokenType.TEXT_LITERAL, TokenType.TEXT_ID)) {
            return;
        }

        if (startsExpression(peek().getType())) {
            parseExpression();
            return;
        }

        throw error(peek(), "Expected text, variable, or expression.");
    }

    private void parseExpression() {
        parseTerm();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            parseTerm();
        }
    }

    private void parseTerm() {
        parseFactor();
        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            parseFactor();
        }
    }

    private void parseFactor() {
        if (match(TokenType.NUM_ID, TokenType.NUMBER, TokenType.PI)) {
            return;
        }

        if (match(TokenType.LPAREN)) {
            parseExpression();
            consume(TokenType.RPAREN, "Expected ')' after expression.");
            return;
        }

        if (startsBuiltinCall(peek().getType())) {
            parseBuiltinCall();
            return;
        }

        throw error(peek(), "Expected numeric expression.");
    }

    private void parseBuiltinCall() {
        Token functionName = advance();
        consume(TokenType.LPAREN, "Expected '(' after " + functionName.getLexeme() + ".");
        parseExpression();

        if (functionName.getType() == TokenType.LUCK
                || functionName.getType() == TokenType.BIGGER
                || functionName.getType() == TokenType.SMALLER) {
            consume(TokenType.COMMA, "Expected comma between arguments.");
            parseExpression();
        }

        consume(TokenType.RPAREN, "Expected ')' after function arguments.");
    }

    private Token consumeId(String message) {
        if (check(TokenType.NUM_ID) || check(TokenType.TEXT_ID)) {
            return advance();
        }
        throw error(peek(), message);
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) {
            return advance();
        }
        throw error(peek(), message);
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

    private boolean startsExpression(TokenType type) {
        return type == TokenType.NUM_ID
                || type == TokenType.NUMBER
                || type == TokenType.PI
                || type == TokenType.LPAREN
                || startsBuiltinCall(type);
    }

    private boolean startsBuiltinCall(TokenType type) {
        return type == TokenType.LUCK
                || type == TokenType.ROOT
                || type == TokenType.ROUND
                || type == TokenType.ABS
                || type == TokenType.BIGGER
                || type == TokenType.SMALLER;
    }

    private boolean isClosingToken(TokenType type) {
        return type == TokenType.THEEND
                || type == TokenType.ENDTRICK
                || type == TokenType.ENDGRIND
                || type == TokenType.ENDJOURNEY
                || type == TokenType.ENDDECIDE
                || type == TokenType.ENDCHOOSE
                || type == TokenType.OTHERWISE
                || type == TokenType.OPTION
                || type == TokenType.UNTIL
                || type == TokenType.EOF;
    }

    private String formatExpectedStopTokens(Set<TokenType> stopTokens) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (TokenType tokenType : stopTokens) {
            if (index > 0) {
                builder.append(" or ");
            }
            builder.append(tokenType);
            index++;
        }
        return builder.toString();
    }

    private void requireInteger(Token token, String message) {
        if (token.getLexeme().contains(".")) {
            throw error(token, message);
        }
    }

    private ParserException error(Token token, String message) {
        return new ParserException("Syntax Error at " + token.getLine() + ":" + token.getColumn()
                + " near '" + token.getLexeme() + "' - " + message);
    }
}
