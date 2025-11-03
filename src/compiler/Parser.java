package compiler;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) { this.tokens = tokens; }

    public List<Node> parseProgram() {
        List<Node> statements = new ArrayList<>();
        while (!check(TokenType.EOF)) {
            statements.add(parseStatement());
        }
        return statements;
    }

    private Node parseStatement() {
        if (match(TokenType.INT)) {
            String varName = consume(TokenType.IDENTIFIER, "Expected variable name").value;
            consume(TokenType.ASSIGN, "Expected '='");
            Node expr = parseExpression();
            consume(TokenType.SEMICOLON, "Expected ';'");
            return new AssignmentNode(varName, expr);
        } else if (match(TokenType.PRINTF)) {
            consume(TokenType.LPAREN, "Expected '('");
            consume(TokenType.IDENTIFIER, "Expected format string or variable"); // simplified
            Node expr = parseExpression();
            consume(TokenType.RPAREN, "Expected ')'");
            consume(TokenType.SEMICOLON, "Expected ';'");
            return new PrintNode(expr);
        }
        throw new RuntimeException("Unknown statement at token: " + peek());
    }

    private Node parseExpression() {
        Node node = parseTerm();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token op = previous();
            node = new BinaryOpNode(node, op, parseTerm());
        }
        return node;
    }

    private Node parseTerm() {
        Node node = parseFactor();
        while (match(TokenType.MUL, TokenType.DIV, TokenType.MOD)) {
            Token op = previous();
            node = new BinaryOpNode(node, op, parseFactor());
        }
        return node;
    }

    private Node parseFactor() {
        Token token = advance();
        if (token.type == TokenType.NUMBER) return new NumberNode(Integer.parseInt(token.value));
        if (token.type == TokenType.IDENTIFIER) return new VariableNode(token.value);
        if (token.type == TokenType.LPAREN) {
            Node expr = parseExpression();
            consume(TokenType.RPAREN, "Expected ')'");
            return expr;
        }
        throw new RuntimeException("Unexpected token: " + token);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) { advance(); return true; }
        }
        return false;
    }

    private boolean check(TokenType type) { return peek().type == type; }
    private Token advance() { return tokens.get(pos++); }
    private Token peek() { return tokens.get(pos); }
    private Token previous() { return tokens.get(pos - 1); }
    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw new RuntimeException(message);
    }
}
