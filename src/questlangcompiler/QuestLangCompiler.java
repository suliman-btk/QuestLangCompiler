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

            // All output files for this program are collected in one folder:
            //   output/<program-name>/{tokens.txt, parse_tree.txt, GeneratedProgram.java}
            Path outputDir = resolveOutputDir(sourcePath);
            Files.createDirectories(outputDir);

            StringBuilder tokenText = new StringBuilder();
            tokenText.append("QuestLang Scanner Output").append(System.lineSeparator());
            tokenText.append("------------------------").append(System.lineSeparator());
            for (Token token : tokens) {
                tokenText.append(token).append(System.lineSeparator());
            }
            Path tokensFile = outputDir.resolve("tokens.txt");
            Files.writeString(tokensFile, tokenText.toString(), StandardCharsets.UTF_8);

            System.out.println("Output folder:              " + outputDir.toAbsolutePath());
            System.out.println("Scanner tokens written to:  " + tokensFile.toAbsolutePath());

            QuestLangParser parser = new QuestLangParser(tokens);
            QuestLangTraceReporter.printSyntaxTrace(tokens);
            parser.parse();

            System.out.println();
            System.out.println("Syntax Analysis");
            System.out.println("---------------");
            System.out.println("Syntax analysis successful.");

            try {
                QuestLangParseTree.printParseTree(tokens, outputDir.resolve("parse_tree.txt"));
            } catch (RuntimeException treeEx) {
                System.out.println("(Parse tree display skipped: " + treeEx.getMessage() + ")");
            }

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

            // Also keep a copy of the generated program inside the program's output folder.
            Path generatedCopy = outputDir.resolve("GeneratedProgram.java");
            Files.writeString(generatedCopy, javaCode, StandardCharsets.UTF_8);

            System.out.println();
            System.out.println("Code Generation");
            System.out.println("---------------");
            System.out.println("Generated Java file:        " + outputPath.toAbsolutePath());
            System.out.println("Generated copy written to:  " + generatedCopy.toAbsolutePath());
            QuestLangTraceReporter.printCompilerSummary(tokens, sourcePath, outputPath, javaCode);
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

    private static Path resolveOutputDir(Path sourcePath) {
        String base = "program";
        if (sourcePath != null) {
            String name = sourcePath.getFileName().toString();
            int dot = name.lastIndexOf('.');
            base = (dot > 0) ? name.substring(0, dot) : name;
        }

        Path root;
        Path parent = (sourcePath != null) ? sourcePath.toAbsolutePath().getParent() : null;
        if (parent != null && "samples".equalsIgnoreCase(parent.getFileName().toString())
                && parent.getParent() != null) {
            root = parent.getParent().resolve("output");
        } else if (parent != null) {
            root = parent.resolve("output");
        } else {
            root = Path.of("output").toAbsolutePath();
        }
        return root.resolve(base);
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
