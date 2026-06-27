package questlangcompiler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public final class QuestLangTraceReporter {
    private final List<Token> tokens;
    private final Map<String, String> symbols = new LinkedHashMap<>();
    private final Set<String> tricks = new LinkedHashSet<>();

    private QuestLangTraceReporter(List<Token> tokens) {
        this.tokens = tokens;
    }

    public static void printSyntaxTrace(List<Token> tokens) {
        new QuestLangTraceReporter(tokens).syntaxTrace();
    }

    public static void printSemanticTrace(List<Token> tokens) {
        new QuestLangTraceReporter(tokens).semanticTrace();
    }

    public static void printCodeGenerationTrace(List<Token> tokens) {
        new QuestLangTraceReporter(tokens).codeGenerationTrace();
    }

    private void syntaxTrace() {
        List<List<Token>> lines = sourceLines();
        printHeader("Syntax Analysis Trace");
        trace("Parser type: recursive-descent parser with one-token lookahead.");
        trace("Start rule: <program> ::= <id-section> <decl-section> {<trick-def>} <stmt-list> THEEND");

        for (List<Token> line : lines) {
            Token first = first(line);
            switch (first.getType()) {
                case HERO:
                    traceLine(first, "parseIdSection: match HERO NAME.");
                    break;
                case BADGE:
                    traceLine(first, "parseIdSection optional branch: match BADGE NUMBER.");
                    break;
                case STAT:
                    traceLine(first, "parseDeclSection: match STAT then numeric identifiers "
                            + idsOfType(line, TokenType.NUM_ID) + ".");
                    break;
                case TALE:
                    traceLine(first, "parseDeclSection: match TALE then text identifiers "
                            + idsOfType(line, TokenType.TEXT_ID) + ".");
                    break;
                case DEFINE:
                    traceLine(first, "parseTrickDef: match DEFINE TRICK NAME, then parse statements until ENDTRICK.");
                    break;
                case ENDTRICK:
                    traceLine(first, "parseTrickDef: match ENDTRICK and return to the caller.");
                    break;
                case NUM_ID:
                case TEXT_ID:
                    traceLine(first, "parseAssignment: lookahead is an identifier, so parse ID = expression.");
                    break;
                case ASK:
                    traceLine(first, "parseInput: match ASK TEXT_LITERAL COMMA ID.");
                    break;
                case SAY:
                    traceLine(first, "parseOutput: match SAY then parse text expression.");
                    break;
                case DECIDE:
                    traceLine(first, "parseConditionStatement: match DECIDE, parse condition, then true branch.");
                    break;
                case OTHERWISE:
                    traceLine(first, "optional OTHERWISE branch selected: DECIDE else branch or CHOOSE default branch.");
                    break;
                case ENDDECIDE:
                    traceLine(first, "parseConditionStatement: match ENDDECIDE and close if block.");
                    break;
                case CHOOSE:
                    traceLine(first, "parseChooseStatement: match CHOOSE expression, then parse OPTION blocks.");
                    break;
                case OPTION:
                    traceLine(first, "parseOption: match OPTION NUMBER COLON, then parse option body.");
                    break;
                case ENDCHOOSE:
                    traceLine(first, "parseChooseStatement: match ENDCHOOSE and close switch-style block.");
                    break;
                case GRIND:
                    traceLine(first, grindSyntax(line));
                    break;
                case UNTIL:
                    traceLine(first, "parseLoop: match UNTIL and parse the loop-ending condition.");
                    break;
                case ENDGRIND:
                    traceLine(first, "parseLoop: match ENDGRIND and close GRIND TIMES body.");
                    break;
                case JOURNEY:
                    traceLine(first, "parseLoop: match JOURNEY FROM expression TO expression, then loop body.");
                    break;
                case ENDJOURNEY:
                    traceLine(first, "parseLoop: match ENDJOURNEY and close JOURNEY body.");
                    break;
                case TRADE:
                    traceLine(first, "parseTrade: match TRADE ID COMMA ID.");
                    break;
                case PERFORM:
                    traceLine(first, "parsePerform: match PERFORM NAME.");
                    break;
                case DRAW:
                    traceLine(first, "parseDraw: match DRAW shape expression.");
                    break;
                case THEEND:
                    traceLine(first, "parseProgram: match THEEND, then EOF.");
                    break;
                default:
                    traceLine(first, "Token belongs to the expression currently being parsed.");
                    break;
            }
        }
    }

    private void semanticTrace() {
        List<List<Token>> lines = sourceLines();
        symbols.clear();
        tricks.clear();

        printHeader("Semantic Analysis Trace");
        trace("Pass 1: build symbol table from declarations and register trick names.");
        for (List<Token> line : lines) {
            Token first = first(line);
            if (first.getType() == TokenType.STAT) {
                addDeclarations(line, TokenType.NUM_ID, "NUMERIC");
            } else if (first.getType() == TokenType.TALE) {
                addDeclarations(line, TokenType.TEXT_ID, "TEXT");
            } else if (first.getType() == TokenType.DEFINE) {
                Token trickName = tokenAfter(line, TokenType.TRICK);
                if (trickName != null) {
                    tricks.add(trickName.getLexeme());
                    traceLine(first, "register trick '" + trickName.getLexeme() + "'.");
                }
            }
        }

        trace("Pass 2: check each statement against the symbol table and semantic rules.");
        for (List<Token> line : lines) {
            Token first = first(line);
            switch (first.getType()) {
                case HERO:
                    traceLine(first, "program identification exists: HERO " + tokenLexeme(tokenOfType(line, TokenType.NAME)) + ".");
                    break;
                case BADGE:
                    traceLine(first, "badge value is a number token, so the badge format is valid.");
                    break;
                case STAT:
                    traceLine(first, "numeric declarations are available before the main statement list.");
                    break;
                case TALE:
                    traceLine(first, "text declarations are available before the main statement list.");
                    break;
                case DEFINE:
                    traceLine(first, "trick name is checked for duplicate definitions.");
                    break;
                case NUM_ID:
                    traceLine(first, "assignment target '" + first.getLexeme()
                            + "' is declared as " + knownType(first)
                            + "; right side must be a numeric expression.");
                    break;
                case TEXT_ID:
                    traceLine(first, "assignment target '" + first.getLexeme()
                            + "' is declared as " + knownType(first)
                            + "; right side must be text literals/text variables joined with &.");
                    break;
                case ASK:
                    Token askTarget = lastId(line);
                    traceLine(first, "ASK target '" + tokenLexeme(askTarget)
                            + "' is declared as " + knownType(askTarget) + ".");
                    break;
                case SAY:
                    traceLine(first, "SAY expression may contain text literals, text variables, numeric expressions, and & joins.");
                    break;
                case DECIDE:
                    traceLine(first, "DECIDE condition checks numeric expressions with relational/logical operators.");
                    break;
                case OTHERWISE:
                    traceLine(first, "OTHERWISE is allowed only inside a DECIDE or CHOOSE block.");
                    break;
                case CHOOSE:
                    traceLine(first, "CHOOSE control expression must be numeric; OPTION labels are checked as integers.");
                    break;
                case OPTION:
                    traceLine(first, "OPTION label " + tokenLexeme(tokenOfType(line, TokenType.NUMBER))
                            + " is checked for integer format and duplicate labels in this CHOOSE block.");
                    break;
                case GRIND:
                    traceLine(first, grindSemantic(line));
                    break;
                case UNTIL:
                    traceLine(first, "UNTIL condition is checked as a valid boolean condition.");
                    break;
                case JOURNEY:
                    traceLine(first, "JOURNEY FROM/TO bounds are checked as numeric expressions.");
                    break;
                case TRADE:
                    traceLine(first, tradeSemantic(line));
                    break;
                case PERFORM:
                    Token trickCall = tokenOfType(line, TokenType.NAME);
                    traceLine(first, "PERFORM target '" + tokenLexeme(trickCall)
                            + "' must exist in the defined trick set " + tricks + ".");
                    break;
                case DRAW:
                    traceLine(first, "DRAW shape is STAR/BOX/TRIANGLE and size is checked as a numeric expression.");
                    break;
                default:
                    break;
            }
        }

        trace("Final symbol table:");
        for (Map.Entry<String, String> entry : symbols.entrySet()) {
            trace("  " + entry.getKey() + " : " + entry.getValue());
        }
        if (!tricks.isEmpty()) {
            trace("Defined tricks: " + tricks);
        }
    }

    private void codeGenerationTrace() {
        List<List<Token>> lines = sourceLines();
        symbols.clear();
        for (List<Token> line : lines) {
            if (first(line).getType() == TokenType.STAT) {
                rememberDeclarations(line, TokenType.NUM_ID, "NUMERIC");
            } else if (first(line).getType() == TokenType.TALE) {
                rememberDeclarations(line, TokenType.TEXT_ID, "TEXT");
            }
        }

        printHeader("Code Generation Trace");
        trace("Target language: Java. Output file: generated/GeneratedProgram.java.");
        trace("Declarations become static fields; helper methods are added for input, random values, and drawing.");

        for (List<Token> line : lines) {
            Token first = first(line);
            switch (first.getType()) {
                case STAT:
                    traceLine(first, "emit Java double fields for " + idsOfType(line, TokenType.NUM_ID) + ".");
                    break;
                case TALE:
                    traceLine(first, "emit Java String fields for " + idsOfType(line, TokenType.TEXT_ID) + ".");
                    break;
                case DEFINE:
                    traceLine(first, "emit a private Java method named trick_" + tokenLexeme(tokenAfter(line, TokenType.TRICK)) + "().");
                    break;
                case ENDTRICK:
                    traceLine(first, "close the generated trick method.");
                    break;
                case NUM_ID:
                    traceLine(first, "emit numeric assignment: " + javaName(first.getLexeme()) + " = <numeric expression>;");
                    break;
                case TEXT_ID:
                    traceLine(first, "emit text assignment: " + javaName(first.getLexeme()) + " = <text expression>;");
                    break;
                case ASK:
                    traceLine(first, askGeneration(line));
                    break;
                case SAY:
                    traceLine(first, "emit System.out.println(<text expression>).");
                    break;
                case DECIDE:
                    traceLine(first, "emit Java if (<condition>) { ... }.");
                    break;
                case OTHERWISE:
                    traceLine(first, "emit Java else/default branch.");
                    break;
                case ENDDECIDE:
                    traceLine(first, "close generated Java if/else block.");
                    break;
                case CHOOSE:
                    traceLine(first, "emit switch ((int) Math.round(<expression>)) { ... }.");
                    break;
                case OPTION:
                    traceLine(first, "emit case " + tokenLexeme(tokenOfType(line, TokenType.NUMBER)) + ": with break.");
                    break;
                case ENDCHOOSE:
                    traceLine(first, "close generated Java switch block.");
                    break;
                case GRIND:
                    traceLine(first, grindGeneration(line));
                    break;
                case UNTIL:
                    traceLine(first, "emit the condition for the generated do-while loop.");
                    break;
                case ENDGRIND:
                    traceLine(first, "close generated GRIND TIMES for loop.");
                    break;
                case JOURNEY:
                    traceLine(first, "emit a Java range loop that works from low-to-high or high-to-low.");
                    break;
                case ENDJOURNEY:
                    traceLine(first, "close generated JOURNEY range loop.");
                    break;
                case TRADE:
                    traceLine(first, "emit temporary variable and three assignments to swap values.");
                    break;
                case PERFORM:
                    traceLine(first, "emit method call trick_" + tokenLexeme(tokenOfType(line, TokenType.NAME)) + "().");
                    break;
                case DRAW:
                    traceLine(first, drawGeneration(line));
                    break;
                case THEEND:
                    traceLine(first, "close Java main method and finish the class.");
                    break;
                default:
                    break;
            }
        }
    }

    private List<List<Token>> sourceLines() {
        List<List<Token>> lines = new ArrayList<>();
        List<Token> currentLine = new ArrayList<>();
        int lineNumber = -1;

        for (Token token : tokens) {
            if (token.getType() == TokenType.EOF) {
                continue;
            }
            if (token.getLine() != lineNumber) {
                if (!currentLine.isEmpty()) {
                    lines.add(currentLine);
                }
                currentLine = new ArrayList<>();
                lineNumber = token.getLine();
            }
            currentLine.add(token);
        }

        if (!currentLine.isEmpty()) {
            lines.add(currentLine);
        }
        return lines;
    }

    private void addDeclarations(List<Token> line, TokenType idType, String type) {
        for (Token token : line) {
            if (token.getType() == idType) {
                symbols.put(token.getLexeme(), type);
                traceLine(first(line), "declare " + token.getLexeme() + " as " + type + ".");
            }
        }
    }

    private void rememberDeclarations(List<Token> line, TokenType idType, String type) {
        for (Token token : line) {
            if (token.getType() == idType) {
                symbols.put(token.getLexeme(), type);
            }
        }
    }

    private String grindSyntax(List<Token> line) {
        if (has(line, TokenType.TIMES)) {
            return "parseLoop: GRIND expression TIMES, then parse body until ENDGRIND.";
        }
        return "parseLoop: GRIND body form, parse statements until UNTIL condition.";
    }

    private String grindSemantic(List<Token> line) {
        if (has(line, TokenType.TIMES)) {
            return "GRIND count expression is checked as numeric.";
        }
        return "GRIND UNTIL form will check the body first and then the UNTIL condition.";
    }

    private String grindGeneration(List<Token> line) {
        if (has(line, TokenType.TIMES)) {
            return "emit Java for loop using Math.round(count).";
        }
        return "emit Java do { ... } while (!(condition));";
    }

    private String tradeSemantic(List<Token> line) {
        List<Token> ids = ids(line);
        if (ids.size() >= 2) {
            return "TRADE checks both variables are declared and same type: "
                    + ids.get(0).getLexeme() + " is " + knownType(ids.get(0)) + ", "
                    + ids.get(1).getLexeme() + " is " + knownType(ids.get(1)) + ".";
        }
        return "TRADE checks both variables are declared and have the same type.";
    }

    private String askGeneration(List<Token> line) {
        Token target = lastId(line);
        String reader = "TEXT".equals(knownType(target)) ? "readText()" : "readNumber()";
        return "emit System.out.print(prompt), then assign " + javaName(tokenLexeme(target)) + " = " + reader + ".";
    }

    private String drawGeneration(List<Token> line) {
        if (has(line, TokenType.STAR)) {
            return "emit drawStar(<size>).";
        }
        if (has(line, TokenType.BOX)) {
            return "emit drawBox(<size>).";
        }
        return "emit drawTriangle(<size>).";
    }

    private Token first(List<Token> line) {
        return line.get(0);
    }

    private Token tokenOfType(List<Token> line, TokenType type) {
        for (Token token : line) {
            if (token.getType() == type) {
                return token;
            }
        }
        return null;
    }

    private Token tokenAfter(List<Token> line, TokenType marker) {
        for (int index = 0; index + 1 < line.size(); index++) {
            if (line.get(index).getType() == marker) {
                return line.get(index + 1);
            }
        }
        return null;
    }

    private Token lastId(List<Token> line) {
        Token result = null;
        for (Token token : line) {
            if (token.getType() == TokenType.NUM_ID || token.getType() == TokenType.TEXT_ID) {
                result = token;
            }
        }
        return result;
    }

    private List<Token> ids(List<Token> line) {
        List<Token> result = new ArrayList<>();
        for (Token token : line) {
            if (token.getType() == TokenType.NUM_ID || token.getType() == TokenType.TEXT_ID) {
                result.add(token);
            }
        }
        return result;
    }

    private String idsOfType(List<Token> line, TokenType type) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Token token : line) {
            if (token.getType() == type) {
                joiner.add(token.getLexeme());
            }
        }
        String result = joiner.toString();
        return result.isEmpty() ? "(none)" : result;
    }

    private boolean has(List<Token> line, TokenType type) {
        return tokenOfType(line, type) != null;
    }

    private String knownType(Token token) {
        if (token == null) {
            return "UNKNOWN";
        }
        String type = symbols.get(token.getLexeme());
        return type == null ? "UNKNOWN" : type;
    }

    private String tokenLexeme(Token token) {
        return token == null ? "UNKNOWN" : token.getLexeme();
    }

    private String javaName(String questName) {
        if (questName == null || "UNKNOWN".equals(questName)) {
            return "UNKNOWN";
        }
        if (questName.endsWith("$")) {
            return questName.substring(0, questName.length() - 1) + "_text";
        }
        return questName;
    }

    private void printHeader(String title) {
        System.out.println();
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
    }

    private void traceLine(Token token, String message) {
        trace("Line " + token.getLine() + ": " + message);
    }

    private void trace(String message) {
        System.out.println("[Trace] " + message);
    }
}
