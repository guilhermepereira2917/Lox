package main.java;

import java.util.Objects;

public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (line != token.line) return false;
        if (type != token.type) return false;
        if (!Objects.equals(lexeme, token.lexeme)) return false;
        return Objects.equals(literal, token.literal);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", type, lexeme, literal, line);
    }
}
