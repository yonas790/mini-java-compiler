package compiler;

public enum TokenType {
    // Keywords
    INT,
    PRINTF,
    
    // Symbols
    SEMICOLON,
    COMMA,
    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    ASSIGN,
    
    // Operators
    PLUS,
    MINUS,
    MUL,
    DIV,
    MOD,
    
    // Literals and identifiers
    NUMBER,
    STRING,
    IDENTIFIER,
    
    // End of file
    EOF
}