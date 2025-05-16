package com.fges;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    private static final String TEST_FILE = "test_grocery.json";

    @BeforeEach
    void setUp() throws IOException {
        // Create the test storage file
        File file = new File(TEST_FILE);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{}"); // Initialize with an empty JSON object
            }
        }
    }

    @AfterEach
    void tearDown() {
        // Delete the test storage file
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testExecReturnZeroWithValidCommand() throws Exception {
        // Arrange
        String[] args = {"add", "Banane", "2", "-c", "Fruits", "-s", TEST_FILE, "-f", "json"};

        // Act
        int result = Main.exec(args);

        // Assert
        assertEquals(0, result, "Expected exec to return 0 for a valid command");
    }

    @Test
    void testExecWithInvalidArguments() throws IOException {
        // Arrange
        String[] args = {"add", "Banane"}; // Invalid because missing quantity

        // Act
        int result = Main.exec(args);

        // Assert
        assertEquals(1, result, "Expected exec to return 1 for invalid arguments");
    }

    @Test
    void testExecWithUnknownCommand() throws IOException {
        // Arrange
        String[] args = {"unknownCommand", "-s", "test_grocery.json", "-f", "json"};

        // Act
        int result = Main.exec(args);

        // Assert
        assertEquals(1, result, "Expected exec to return 1 for an unknown command");
    }
}