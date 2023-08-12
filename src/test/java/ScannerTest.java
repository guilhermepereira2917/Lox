package test.java;

import main.java.Scanner;
import main.java.Token;
import main.java.TokenType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {
    @Test
    public void testIntVariableDeclaration() {
        String source = "int x = 1";

        List<Token> expected = new ArrayList<>();
        expected.add(new Token(TokenType.IDENTIFIER, "int", null, 1));
        expected.add(new Token(TokenType.IDENTIFIER, "x", null, 1));
        expected.add(new Token(TokenType.EQUAL, "=", null, 1));
        expected.add(new Token(TokenType.NUMBER, "1", 1.0, 1));
        expected.add(new Token(TokenType.EOF, "", null, 1));

        assertIterableEquals(new Scanner(source).scanTokens(), expected);
    }
}