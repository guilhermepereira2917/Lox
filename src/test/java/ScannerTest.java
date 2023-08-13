package test.java;

import main.java.Scanner;
import main.java.Token;
import main.java.TokenType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {
    private final static ByteArrayOutputStream systemOutContent = new ByteArrayOutputStream();
    private final static PrintStream originalOut = System.out;

    @BeforeAll
    public static void setupSystemOut() {
        System.setOut(new PrintStream(systemOutContent));
    }

    @AfterAll
    public static void restoreSystemOut() {
        System.setOut(originalOut);
    }

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

    @Test
    public void testSourceContainsOnlyBlockComment() {
        String source = "/* int x = 1 */";

        List<Token> expected = new ArrayList<>();
        expected.add(new Token(TokenType.EOF, "", null, 1));

        assertIterableEquals(new Scanner(source).scanTokens(), expected);
    }

    @Test
    public void testMultiLineBlockComment() {
        String source = "/* int x = 1 \n" +
                        "   int y = 2 */";

        List<Token> expected = new ArrayList<>();
        expected.add(new Token(TokenType.EOF, "", null, 2));

        assertIterableEquals(new Scanner(source).scanTokens(), expected);
    }

    @Test
    public void testIdentifierAfterBlockComment() {
        String source = "/* int x = 1 */\n" +
                        "int y = 2";

        List<Token> expected = new ArrayList<>();
        expected.add(new Token(TokenType.IDENTIFIER, "int", null, 2));
        expected.add(new Token(TokenType.IDENTIFIER, "y", null, 2));
        expected.add(new Token(TokenType.EQUAL, "=", null, 2));
        expected.add(new Token(TokenType.NUMBER, "2", 2.0, 2));
        expected.add(new Token(TokenType.EOF, "", null, 2));

        assertIterableEquals(new Scanner(source).scanTokens(), expected);
    }

    @Test
    public void testUnterminatedBlockComment() {
        String source = "/* int x";

        Scanner scanner = new Scanner(source);
        scanner.scanTokens();

        String errorMessage = systemOutContent.toString();
        assertTrue(errorMessage.contains("Unterminated block comment."));
    }
}