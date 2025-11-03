package compiler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String input = Files.readString(Path.of("../../sample-programs/program1.c"));

        // 1️⃣ Lexer
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        // 2️⃣ Parser
        Parser parser = new Parser(tokens);
        List<Node> ast = parser.parseProgram();

        // 3️⃣ Semantic analysis
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        analyzer.analyze(ast);

        // 4️⃣ Interpreter
        Interpreter interpreter = new Interpreter();
        interpreter.execute(ast);
    }
}
