package com.fges;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    private final ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();
    private final PrintStream sortieOriginale = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(sortieCapturee));
    }

    @AfterEach
    void tearDown() {
        System.setOut(sortieOriginale);
    }

    @Test
    void testExecReturnZeroWithValidCommand() throws Exception {
        String[] args = {"add", "--item", "Banane", "--quantity", "2", "--category", "Fruits", "--file", "test_grocery.json", "--format", "json"};
        int result = Main.exec(args);
        assertEquals(0, result);
    }

    @Test
    void testExecReturnsOneOnParseException() throws Exception {
        // Simule une commande invalide (option manquante)
        String[] args = {"add", "--item", "Banane"};
        int result = Main.exec(args);
        assertEquals(1, result);
    }

    @Test
    void testExecReturnsOneOnIllegalArgumentException() throws Exception {
        // Simule un mauvais format de commande (valeur invalide)
        String[] args = {"add", "--item", "Banane", "--quantity", "deux", "--category", "Fruits", "--file", "test_grocery.json", "--format", "json"};
        int result = Main.exec(args);
        assertEquals(1, result);
    }

    @Test
    void testExecReturnsOneOnGenericException() throws Exception {
        // Simule une commande inconnue
        String[] args = {"unknownCommand", "--file", "test_grocery.json", "--format", "json"};
        int result = Main.exec(args);
        assertEquals(1, result);
    }
}
