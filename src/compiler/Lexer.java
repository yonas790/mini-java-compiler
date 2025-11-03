package compiler;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos = 0;
    private final int length;

    public Lexer(String input) {
        this.input = input;
        this.length = input.length();
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < length) {
            char current = peek();

            if (Character.isWhitespace(current)) {
                advance();
            } else if (Character.isDigit(current)) {
                tokens.add(number());
            } else if (Character.isLetter(current)) {
                tokens.add(identifierOrKeyword());
            } else {
                switch (current) {
                    case ';': tokens.add(new Token(TokenType.SEMICOLON, ";")); advance(); break;
                    case ',': tokens.add(new Token(TokenType.COMMA, ",")); advance(); break;
                    case '(': tokens.add(new Token(TokenType.LPAREN, "(")); advance(); break;
                    case ')': tokens.add(new Token(TokenType.RPAREN, ")")); advance(); break;
                    case '{': tokens.add(new Token(TokenType.LBRACE, "{")); advance(); break;
                    case '}': tokens.add(new Token(TokenType.RBRACE, "}")); advance(); break;
                    case '=': tokens.add(new Token(TokenType.ASSIGN, "=")); advance(); break;
                    case '+': tokens.add(new Token(TokenType.PLUS, "+")); advance(); break;
                    case '-': tokens.add(new Token(TokenType.MINUS, "-")); advance(); break;
                    case '*': tokens.add(new Token(TokenType.MUL, "*")); advance(); break;
                    case '/': tokens.add(new Token(TokenType.DIV, "/")); advance(); break;
                    case '%': tokens.add(new Token(TokenType.MOD, "%")); advance(); break;
                    default:
                        throw new RuntimeException("Unexpected character: " + current);
                }
            }
        }

        tokens.add(new Token(TokenType.EOF, null));
        return tokens;
    }

    private Token number() {
        StringBuilder sb = new StringBuilder();
        while (pos < length && Character.isDigit(peek())) {
            sb.append(advance());
        }
        return new Token(TokenType.NUMBER, sb.toString());
    }

    private Token identifierOrKeyword() {
        StringBuilder sb = new StringBuilder();
        while (pos < length && Character.isLetterOrDigit(peek())) {
            sb.append(advance());
        }
        String val = sb.toString();
        return switch (val) {
            case "int" -> new Token(TokenType.INT, val);
            case "printf" -> new Token(TokenType.PRINTF, val);
            default -> new Token(TokenType.IDENTIFIER, val);
        };
    }

    private char peek() { return input.charAt(pos); }
    private char advance() { return input.charAt(pos++); }
}
