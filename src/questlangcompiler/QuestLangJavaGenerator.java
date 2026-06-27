package questlangcompiler;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuestLangJavaGenerator {
    private final List<Token> tokens;
    private final Map<String, SymbolType> variables = new LinkedHashMap<>();
    private final List<String> trickMethods = new ArrayList<>();
    private int current = 0;
    private int indentLevel = 0;
    private int tempCounter = 0;
    private int loopCounter = 0;
    private StringBuilder out;

    public QuestLangJavaGenerator(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String generate() {
        skipIdentification();
        readDeclarations();

        while (check(TokenType.DEFINE)) {
            trickMethods.add(generateTrickMethod());
        }

        StringBuilder mainBody = new StringBuilder();
        out = mainBody;
        indentLevel = 2;
        generateStatementList(EnumSet.of(TokenType.THEEND));
        consume(TokenType.THEEND);
        consume(TokenType.EOF);

        return buildJavaSource(mainBody.toString());
    }

    private void skipIdentification() {
        consume(TokenType.HERO);
        consume(TokenType.NAME);
        if (match(TokenType.BADGE)) {
            consume(TokenType.NUMBER);
        }
    }

    private void readDeclarations() {
        while (match(TokenType.STAT, TokenType.TALE)) {
            SymbolType type = previous().getType() == TokenType.STAT ? SymbolType.NUMERIC : SymbolType.TEXT;
            Token first = consume(type == SymbolType.NUMERIC ? TokenType.NUM_ID : TokenType.TEXT_ID);
            variables.put(first.getLexeme(), type);

            while (match(TokenType.COMMA)) {
                Token next = consume(type == SymbolType.NUMERIC ? TokenType.NUM_ID : TokenType.TEXT_ID);
                variables.put(next.getLexeme(), type);
            }
        }
    }

    private String generateTrickMethod() {
        consume(TokenType.DEFINE);
        consume(TokenType.TRICK);
        Token name = consume(TokenType.NAME);

        StringBuilder previousOut = out;
        int previousIndent = indentLevel;

        out = new StringBuilder();
        indentLevel = 1;
        emit("private static void " + trickMethodName(name.getLexeme()) + "() {");
        indentLevel++;
        generateStatementList(EnumSet.of(TokenType.ENDTRICK));
        indentLevel--;
        emit("}");
        consume(TokenType.ENDTRICK);

        String method = out.toString();
        out = previousOut;
        indentLevel = previousIndent;
        return method;
    }

    private String buildJavaSource(String mainBody) {
        StringBuilder code = new StringBuilder();
        code.append("import java.util.*;\n\n");
        code.append("public class GeneratedProgram {\n");
        code.append("    private static final Scanner input = new Scanner(System.in);\n");
        code.append("    private static final Random random = new Random();\n\n");

        for (Map.Entry<String, SymbolType> entry : variables.entrySet()) {
            if (entry.getValue() == SymbolType.NUMERIC) {
                code.append("    private static double ").append(javaVariableName(entry.getKey())).append(";\n");
            } else {
                code.append("    private static String ").append(javaVariableName(entry.getKey())).append(" = \"\";\n");
            }
        }
        code.append("\n");

        for (String method : trickMethods) {
            code.append(method).append("\n");
        }

        code.append("    public static void main(String[] args) {\n");
        code.append(mainBody);
        code.append("    }\n\n");
        appendHelpers(code);
        code.append("}\n");
        return code.toString();
    }

    private void appendHelpers(StringBuilder code) {
        code.append("    private static double readNumber() {\n");
        code.append("        while (true) {\n");
        code.append("            String line = input.nextLine().replace(\"\\uFEFF\", \"\").trim();\n");
        code.append("            if (!line.isEmpty()) {\n");
        code.append("                return Double.parseDouble(line);\n");
        code.append("            }\n");
        code.append("        }\n");
        code.append("    }\n\n");

        code.append("    private static String readText() {\n");
        code.append("        return input.nextLine().replace(\"\\uFEFF\", \"\");\n");
        code.append("    }\n\n");

        code.append("    private static double luck(double low, double high) {\n");
        code.append("        int min = (int) Math.round(Math.min(low, high));\n");
        code.append("        int max = (int) Math.round(Math.max(low, high));\n");
        code.append("        return random.nextInt(max - min + 1) + min;\n");
        code.append("    }\n\n");

        code.append("    private static int normalizeSize(double size) {\n");
        code.append("        return Math.max(0, (int) Math.round(size));\n");
        code.append("    }\n\n");

        code.append("    private static void drawStar(double size) {\n");
        code.append("        int n = normalizeSize(size);\n");
        code.append("        System.out.println(\"*\".repeat(n));\n");
        code.append("    }\n\n");

        code.append("    private static void drawBox(double size) {\n");
        code.append("        int n = normalizeSize(size);\n");
        code.append("        for (int row = 0; row < n; row++) {\n");
        code.append("            System.out.println(\"*\".repeat(n));\n");
        code.append("        }\n");
        code.append("    }\n\n");

        code.append("    private static void drawTriangle(double size) {\n");
        code.append("        int n = normalizeSize(size);\n");
        code.append("        for (int row = 1; row <= n; row++) {\n");
        code.append("            System.out.println(\"*\".repeat(row));\n");
        code.append("        }\n");
        code.append("    }\n");
    }

    private void generateStatementList(Set<TokenType> stopTokens) {
        while (!isAtEnd() && !stopTokens.contains(peek().getType())) {
            generateStatement();
        }
    }

    private String captureStatementList(Set<TokenType> stopTokens, int bodyIndentLevel) {
        StringBuilder previousOut = out;
        int previousIndent = indentLevel;

        out = new StringBuilder();
        indentLevel = bodyIndentLevel;
        generateStatementList(stopTokens);
        String captured = out.toString();

        out = previousOut;
        indentLevel = previousIndent;
        return captured;
    }

    private void generateStatement() {
        if (check(TokenType.NUM_ID) || check(TokenType.TEXT_ID)) {
            generateAssignment();
        } else if (check(TokenType.GRIND) || check(TokenType.JOURNEY)) {
            generateLoop();
        } else if (check(TokenType.DECIDE)) {
            generateDecide();
        } else if (check(TokenType.CHOOSE)) {
            generateChoose();
        } else if (check(TokenType.ASK)) {
            generateAsk();
        } else if (check(TokenType.SAY)) {
            generateSay();
        } else if (check(TokenType.TRADE)) {
            generateTrade();
        } else if (check(TokenType.PERFORM)) {
            generatePerform();
        } else if (check(TokenType.DRAW)) {
            generateDraw();
        } else {
            throw error(peek(), "Cannot generate code for unexpected token.");
        }
    }

    private void generateAssignment() {
        Token target = advance();
        consume(TokenType.ASSIGN);
        if (target.getType() == TokenType.NUM_ID) {
            emit(javaVariableName(target.getLexeme()) + " = " + expression() + ";");
        } else {
            emit(javaVariableName(target.getLexeme()) + " = " + textExpression() + ";");
        }
    }

    private void generateLoop() {
        if (match(TokenType.JOURNEY)) {
            consume(TokenType.FROM);
            String from = expression();
            consume(TokenType.TO);
            String to = expression();
            int id = loopCounter++;
            String start = "journeyStart" + id;
            String end = "journeyEnd" + id;
            String counter = "journeyCounter" + id;
            String body = captureStatementList(EnumSet.of(TokenType.ENDJOURNEY), indentLevel + 3);
            consume(TokenType.ENDJOURNEY);

            emit("{");
            indentLevel++;
            emit("double " + start + " = " + from + ";");
            emit("double " + end + " = " + to + ";");
            emit("if (" + start + " <= " + end + ") {");
            indentLevel++;
            emit("for (double " + counter + " = " + start + "; " + counter + " <= " + end + "; " + counter + "++) {");
            out.append(body);
            emit("}");
            indentLevel--;
            emit("} else {");
            indentLevel++;
            emit("for (double " + counter + " = " + start + "; " + counter + " >= " + end + "; " + counter + "--) {");
            out.append(body);
            emit("}");
            indentLevel--;
            emit("}");
            indentLevel--;
            emit("}");
            return;
        }

        consume(TokenType.GRIND);
        if (isGrindTimesForm()) {
            String count = expression();
            consume(TokenType.TIMES);
            String counter = "grindCounter" + loopCounter++;
            emit("for (int " + counter + " = 0; " + counter + " < (int) Math.round(" + count + "); " + counter + "++) {");
            indentLevel++;
            generateStatementList(EnumSet.of(TokenType.ENDGRIND));
            indentLevel--;
            emit("}");
            consume(TokenType.ENDGRIND);
        } else {
            emit("do {");
            indentLevel++;
            generateStatementList(EnumSet.of(TokenType.UNTIL));
            indentLevel--;
            consume(TokenType.UNTIL);
            String condition = condition();
            emit("} while (!(" + condition + "));");
        }
    }

    private boolean isGrindTimesForm() {
        int saved = current;
        try {
            expression();
            return match(TokenType.TIMES);
        } catch (RuntimeException ex) {
            return false;
        } finally {
            current = saved;
        }
    }

    private void generateDecide() {
        consume(TokenType.DECIDE);
        emit("if (" + condition() + ") {");
        indentLevel++;
        generateStatementList(EnumSet.of(TokenType.OTHERWISE, TokenType.ENDDECIDE));
        indentLevel--;

        if (match(TokenType.OTHERWISE)) {
            emit("} else {");
            indentLevel++;
            generateStatementList(EnumSet.of(TokenType.ENDDECIDE));
            indentLevel--;
        }

        emit("}");
        consume(TokenType.ENDDECIDE);
    }

    private void generateChoose() {
        consume(TokenType.CHOOSE);
        emit("switch ((int) Math.round(" + expression() + ")) {");
        indentLevel++;

        while (match(TokenType.OPTION)) {
            Token label = consume(TokenType.NUMBER);
            consume(TokenType.COLON);
            emit("case " + label.getLexeme() + ":");
            indentLevel++;
            generateStatementList(EnumSet.of(TokenType.OPTION, TokenType.OTHERWISE, TokenType.ENDCHOOSE));
            emit("break;");
            indentLevel--;
        }

        if (match(TokenType.OTHERWISE)) {
            emit("default:");
            indentLevel++;
            generateStatementList(EnumSet.of(TokenType.ENDCHOOSE));
            emit("break;");
            indentLevel--;
        }

        indentLevel--;
        emit("}");
        consume(TokenType.ENDCHOOSE);
    }

    private void generateAsk() {
        consume(TokenType.ASK);
        Token prompt = consume(TokenType.TEXT_LITERAL);
        consume(TokenType.COMMA);
        Token id = consumeId();
        emit("System.out.print(" + javaString(prompt.getLexeme()) + ");");
        if (id.getType() == TokenType.NUM_ID) {
            emit(javaVariableName(id.getLexeme()) + " = readNumber();");
        } else {
            emit(javaVariableName(id.getLexeme()) + " = readText();");
        }
    }

    private void generateSay() {
        consume(TokenType.SAY);
        emit("System.out.println(" + textExpression() + ");");
    }

    private void generateTrade() {
        consume(TokenType.TRADE);
        Token first = consumeId();
        consume(TokenType.COMMA);
        Token second = consumeId();
        String temp = "tradeTemp" + tempCounter++;
        if (variables.get(first.getLexeme()) == SymbolType.NUMERIC) {
            emit("double " + temp + " = " + javaVariableName(first.getLexeme()) + ";");
        } else {
            emit("String " + temp + " = " + javaVariableName(first.getLexeme()) + ";");
        }
        emit(javaVariableName(first.getLexeme()) + " = " + javaVariableName(second.getLexeme()) + ";");
        emit(javaVariableName(second.getLexeme()) + " = " + temp + ";");
    }

    private void generatePerform() {
        consume(TokenType.PERFORM);
        Token trickName = consume(TokenType.NAME);
        emit(trickMethodName(trickName.getLexeme()) + "();");
    }

    private void generateDraw() {
        consume(TokenType.DRAW);
        Token shape = advance();
        String size = expression();
        if (shape.getType() == TokenType.STAR) {
            emit("drawStar(" + size + ");");
        } else if (shape.getType() == TokenType.BOX) {
            emit("drawBox(" + size + ");");
        } else {
            emit("drawTriangle(" + size + ");");
        }
    }

    private String condition() {
        return orCondition();
    }

    private String orCondition() {
        String result = andCondition();
        while (match(TokenType.OR)) {
            result = "(" + result + " || " + andCondition() + ")";
        }
        return result;
    }

    private String andCondition() {
        String result = notCondition();
        while (match(TokenType.AND)) {
            result = "(" + result + " && " + notCondition() + ")";
        }
        return result;
    }

    private String notCondition() {
        if (match(TokenType.NOT)) {
            return "!(" + notCondition() + ")";
        }
        if (match(TokenType.LPAREN)) {
            String result = condition();
            consume(TokenType.RPAREN);
            return "(" + result + ")";
        }
        return relation();
    }

    private String relation() {
        String left = expression();
        Token op = advance();
        String right = expression();
        return left + " " + javaRelop(op) + " " + right;
    }

    private String textExpression() {
        String result = textItem();
        while (match(TokenType.AMPERSAND)) {
            result = result + " + " + textItem();
        }
        return result;
    }

    private String textItem() {
        if (match(TokenType.TEXT_LITERAL)) {
            return javaString(previous().getLexeme());
        }
        if (match(TokenType.TEXT_ID)) {
            return javaVariableName(previous().getLexeme());
        }
        return expression();
    }

    private String expression() {
        String result = term();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token op = previous();
            result = "(" + result + " " + op.getLexeme() + " " + term() + ")";
        }
        return result;
    }

    private String term() {
        String result = factor();
        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            Token op = previous();
            result = "(" + result + " " + op.getLexeme() + " " + factor() + ")";
        }
        return result;
    }

    private String factor() {
        if (match(TokenType.NUM_ID)) {
            return javaVariableName(previous().getLexeme());
        }
        if (match(TokenType.NUMBER)) {
            return previous().getLexeme();
        }
        if (match(TokenType.PI)) {
            return "Math.PI";
        }
        if (match(TokenType.LPAREN)) {
            String result = expression();
            consume(TokenType.RPAREN);
            return "(" + result + ")";
        }
        if (startsBuiltinCall(peek().getType())) {
            return builtinCall();
        }
        throw error(peek(), "Expected numeric expression.");
    }

    private String builtinCall() {
        Token functionName = advance();
        consume(TokenType.LPAREN);
        String first = expression();
        String second = null;

        if (functionName.getType() == TokenType.LUCK
                || functionName.getType() == TokenType.BIGGER
                || functionName.getType() == TokenType.SMALLER) {
            consume(TokenType.COMMA);
            second = expression();
        }

        consume(TokenType.RPAREN);

        switch (functionName.getType()) {
            case LUCK:
                return "luck(" + first + ", " + second + ")";
            case ROOT:
                return "Math.sqrt(" + first + ")";
            case ROUND:
                return "Math.round(" + first + ")";
            case ABS:
                return "Math.abs(" + first + ")";
            case BIGGER:
                return "Math.max(" + first + ", " + second + ")";
            case SMALLER:
                return "Math.min(" + first + ", " + second + ")";
            default:
                throw error(functionName, "Unknown built-in function.");
        }
    }

    private String javaRelop(Token op) {
        if (op.getType() == TokenType.EQ) {
            return "==";
        }
        if (op.getType() == TokenType.NEQ) {
            return "!=";
        }
        return op.getLexeme();
    }

    private String javaVariableName(String questName) {
        if (questName.endsWith("$")) {
            return questName.substring(0, questName.length() - 1) + "_text";
        }
        return questName;
    }

    private String trickMethodName(String questName) {
        return "trick_" + questName;
    }

    private String javaString(String value) {
        return "\"" + value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t") + "\"";
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

    private void emit(String line) {
        out.append("    ".repeat(indentLevel)).append(line).append(System.lineSeparator());
    }

    private CodeGenerationException error(Token token, String message) {
        return new CodeGenerationException("Code Generation Error at " + token.getLine() + ":" + token.getColumn()
                + " near '" + token.getLexeme() + "' - " + message);
    }
}
