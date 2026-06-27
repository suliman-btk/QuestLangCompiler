QuestLangCompiler - Scanner, Parser, Semantic Analyzer, and Code Generator

Project folder:
C:\Users\WinDows\Desktop\My University\prog lang\project\code\QuestLangCompiler

Overview
--------
QuestLangCompiler is a Java compiler project for QuestLang, a quest-themed beginner
programming language. The compiler reads a .ql source file, checks it through the
compiler phases, and generates an equivalent Java program.

Compiler phases shown in the terminal
-------------------------------------
Every run prints detailed evidence for these phases:

1. QuestLang Scanner Output
   - Shows each token found by the lexical analyzer.
   - Shows token type, lexeme, line number, and column number.

2. Syntax Analysis Trace
   - Shows how the recursive-descent parser chooses grammar rules.
   - Examples: parseIdSection, parseDeclSection, parseAssignment,
     parseConditionStatement, parseChooseStatement, parseLoop, parseDraw.

3. Syntax Analysis
   - Prints whether syntax analysis was successful.
   - If syntax is wrong, the compiler stops with a syntax error.

4. Semantic Analysis Trace
   - Shows symbol table construction.
   - Shows declarations, type checks, ASK target checks, TRADE checks,
     PERFORM trick checks, and CHOOSE option checks.

5. Semantic Analysis
   - Prints whether semantic analysis was successful.
   - If semantics are wrong, the compiler stops with a semantic error.

6. Code Generation Trace
   - Shows how QuestLang statements are translated into Java.
   - Examples:
     ASK -> System.out.print + readNumber/readText
     SAY -> System.out.println
     DECIDE -> Java if/else
     CHOOSE -> Java switch
     GRIND -> Java for loop or do-while loop
     JOURNEY -> Java range loop
     DRAW -> drawStar/drawBox/drawTriangle helper method

7. Code Generation
   - Prints the generated Java file path.
   - Output file:
     generated\GeneratedProgram.java

How to open and run in NetBeans
-------------------------------
1. Open NetBeans.
2. Click File > Open Project.
3. Select this folder:
   C:\Users\WinDows\Desktop\My University\prog lang\project\code\QuestLangCompiler
4. Right-click the QuestLangCompiler project.
5. Click Clean and Build.
6. Click Run.

If no input file is provided, the compiler runs the built-in sample program.

How to run a specific .ql file in NetBeans
-----------------------------------------
1. Right-click the QuestLangCompiler project.
2. Click Properties.
3. Go to Run.
4. In Arguments, write the sample path you want to compile, for example:
   samples\valid_student_grade_checker.ql
5. Click OK.
6. Click Run.

Other examples you can use as Run Arguments:
samples\valid_simple_calculator.ql
samples\valid_student_grade_checker.ql
samples\valid_shopping_bill_calculator.ql
samples\valid_keyword_showcase.ql
samples\valid_creative.ql

How to run from Windows terminal
--------------------------------
Open Command Prompt or PowerShell, then run:

cd "C:\Users\WinDows\Desktop\My University\prog lang\project\code\QuestLangCompiler"

javac -d build\classes src\questlangcompiler\*.java

java -cp build\classes questlangcompiler.QuestLangCompiler samples\valid_student_grade_checker.ql

To run another QuestLang program, replace the sample file name:

java -cp build\classes questlangcompiler.QuestLangCompiler samples\valid_simple_calculator.ql

How to run the generated Java program
-------------------------------------
After the compiler succeeds, it creates:

generated\GeneratedProgram.java

Compile the generated Java file:

javac -d generated generated\GeneratedProgram.java

Run the generated program:

java -cp generated GeneratedProgram

Important:
Every time you compile a different .ql file, GeneratedProgram.java is replaced.
So you must run this command again before executing the generated program:

javac -d generated generated\GeneratedProgram.java

Valid sample programs
---------------------
samples\valid_calculator.ql
samples\valid_creative.ql
samples\valid_keyword_showcase.ql
samples\valid_loops_trade.ql
samples\valid_multiplication_table.ql
samples\valid_shopping_bill_calculator.ql
samples\valid_simple_calculator.ql
samples\valid_student_grade_checker.ql

Invalid sample programs
-----------------------
These files are used to demonstrate compiler error handling:

samples\invalid_bad_identifier.ql
samples\invalid_duplicate_declaration.ql
samples\invalid_duplicate_option.ql
samples\invalid_missing_enddecide.ql
samples\invalid_recursive_trick.ql
samples\invalid_text_assignment.ql
samples\invalid_trade_type.ql
samples\invalid_undeclared_variable.ql
samples\invalid_undefined_trick.ql

What to show during the project demo
------------------------------------
1. Run a valid program such as:
   samples\valid_student_grade_checker.ql

2. Explain the terminal output:
   - Scanner Output proves lexical analysis.
   - Syntax Analysis Trace proves parser rule selection.
   - Semantic Analysis Trace proves symbol table and semantic checks.
   - Code Generation Trace proves QuestLang-to-Java translation.

3. Open generated\GeneratedProgram.java and show the translated Java code.

4. Compile and run the generated Java program:
   javac -d generated generated\GeneratedProgram.java
   java -cp generated GeneratedProgram

5. Run an invalid program such as:
   samples\invalid_duplicate_option.ql

6. Show that the compiler stops with a clear error message.

Troubleshooting
---------------
If the terminal says "No input file provided":
- The compiler is running the built-in sample.
- Add a sample .ql path in NetBeans Project Properties > Run > Arguments.

If the generated program output looks old:
- Recompile GeneratedProgram.java:
  javac -d generated generated\GeneratedProgram.java

If Java says "Could not find or load main class":
- Make sure you are running the command from the QuestLangCompiler project folder.
- Make sure the classpath is correct:
  java -cp build\classes questlangcompiler.QuestLangCompiler samples\valid_student_grade_checker.ql
  java -cp generated GeneratedProgram

QuestLang program structure
---------------------------
A normal QuestLang file follows this structure:

HERO ProgrammerName
BADGE 1211305566
STAT numeric1, numeric2
TALE text1$, text2$

ASK "Enter number:", numeric1
SAY "Result is " & numeric1

THEEND

Main supported QuestLang features
---------------------------------
- HERO and BADGE for program identification.
- STAT for numeric variables.
- TALE for text variables.
- ASK for input.
- SAY for output.
- DECIDE, OTHERWISE, ENDDECIDE for conditions.
- CHOOSE, OPTION, ENDCHOOSE for selection.
- GRIND and JOURNEY for loops.
- DEFINE TRICK, PERFORM, ENDTRICK for reusable actions.
- TRADE for swapping variables.
- DRAW STAR, DRAW BOX, DRAW TRIANGLE for simple drawings.
- LUCK, ROOT, ROUND, ABS, BIGGER, SMALLER, and PI for numeric expressions.
