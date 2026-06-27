package questlangcompiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class QuestLangCompiler {
    public static void main(String[] args) {
        String source;
        Path sourcePath = null;

        try {
            if (args.length > 0) {
                sourcePath = Path.of(args[0]);
                source = Files.readString(sourcePath, StandardCharsets.UTF_8);
            } else {
                source = defaultSample();
                System.out.println("No input file provided. Scanning built-in sample.\n");
            }

            QuestLangScanner scanner = new QuestLangScanner(source);
            List<Token> tokens = scanner.scanTokens();

            System.out.println("QuestLang Scanner Output");
            System.out.println("------------------------");
            for (Token token : tokens) {
                System.out.println(token);
            }

            QuestLangParser parser = new QuestLangParser(tokens);
            QuestLangTraceReporter.printSyntaxTrace(tokens);
            parser.parse();

            System.out.println();
            System.out.println("Syntax Analysis");
            System.out.println("---------------");
            System.out.println("Syntax analysis successful.");

            QuestLangSemanticAnalyzer semanticAnalyzer = new QuestLangSemanticAnalyzer(tokens);
            QuestLangTraceReporter.printSemanticTrace(tokens);
            semanticAnalyzer.analyze();

            System.out.println();
            System.out.println("Semantic Analysis");
            System.out.println("-----------------");
            System.out.println("Semantic analysis successful.");

            QuestLangJavaGenerator generator = new QuestLangJavaGenerator(tokens);
            QuestLangTraceReporter.printCodeGenerationTrace(tokens);
            String javaCode = generator.generate();
            Path outputPath = resolveGeneratedPath(sourcePath);
            Files.createDirectories(outputPath.getParent());
            Files.writeString(outputPath, javaCode, StandardCharsets.UTF_8);

            System.out.println();
            System.out.println("Code Generation");
            System.out.println("---------------");
            System.out.println("Generated Java file: " + outputPath.toAbsolutePath());
        } catch (ScannerException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        } catch (ParserException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        } catch (SemanticException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        } catch (CodeGenerationException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Could not read source file: " + ex.getMessage());
            System.exit(1);
        }
    }

    private static String defaultSample() {
        return String.join(System.lineSeparator(),
                "HERO Aisha",
                "BADGE 220011",
                "STAT aa1, bb1, sm1, dv1",
                "TALE ms$",
                "ASK \"Enter the first number:\", aa1",
                "ASK \"Enter the second number:\", bb1",
                "sm1 = aa1 + bb1",
                "DECIDE bb1 <> 0",
                "    dv1 = aa1 / bb1",
                "    SAY \"The quotient is \" & dv1",
                "OTHERWISE",
                "    ms$ = \"Cannot divide by zero\"",
                "    SAY ms$",
                "ENDDECIDE",
                "SAY \"The sum is \" & sm1",
                "THEEND");
    }

    private static Path resolveGeneratedPath(Path sourcePath) {
        if (sourcePath != null) {
            Path absoluteSource = sourcePath.toAbsolutePath();
            Path parent = absoluteSource.getParent();
            if (parent != null && "samples".equalsIgnoreCase(parent.getFileName().toString()) && parent.getParent() != null) {
                return parent.getParent().resolve("generated").resolve("GeneratedProgram.java");
            }
        }
        return Path.of("generated", "GeneratedProgram.java").toAbsolutePath();
    }
}
