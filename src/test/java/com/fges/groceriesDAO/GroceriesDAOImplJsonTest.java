package com.fges.groceriesDAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.modules.GroceryList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class GroceriesDAOImplJsonTest {

    private GroceriesDAOImplJson groceriesDAO;
    private final String testFileName = "test-groceries.json";

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Path.of(testFileName)); // Ensure no leftover file exists
        Files.createFile(Path.of(testFileName));    // Create the test file
        groceriesDAO = new GroceriesDAOImplJson(testFileName);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(testFileName)); // Clean up the test file
    }

    @Test
    void should_save_grocery_list_to_file() throws IOException {
        GroceryList.Builder builder = new GroceryList.Builder();
        builder.add("Apple", 3, "Fruits");
        builder.add("Banana", 5, "Fruits");
        builder.add("Carrot", 2, "Vegetables");
        GroceryList groceryList = builder.build();

        groceriesDAO.saveGroceryList(groceryList);

        String expectedContent = """
        {
          "Vegetables": {
            "Carrot": 2
          },
          "Fruits": {
            "Apple": 3,
            "Banana": 5
          }
        }
        """;

        String actualContent = Files.readString(Path.of(testFileName));

        ObjectMapper objectMapper = new ObjectMapper();
        var expectedJson = objectMapper.readTree(expectedContent);
        var actualJson = objectMapper.readTree(actualContent);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    void should_load_grocery_list_from_file_when_file_exists() throws IOException {
        String fileContent = """
        {
          "Fruits": {
            "Apple": 3,
            "Banana": 5
          },
          "Vegetables": {
            "Carrot": 2
          }
        }
        """;
        Files.writeString(Path.of(testFileName), fileContent);

        GroceryList result = groceriesDAO.loadGroceryList();

        GroceryList.Builder builder = new GroceryList.Builder();
        builder.add("Apple", 3, "Fruits");
        builder.add("Banana", 5, "Fruits");
        builder.add("Carrot", 2, "Vegetables");
        GroceryList expectedGroceryList = builder.build();

        assertEquals(expectedGroceryList, result);
    }

    @Test
    void should_load_empty_grocery_list_when_file_does_not_exist() throws IOException {
        Files.deleteIfExists(Path.of(testFileName)); // Ensure the file does not exist

        GroceryList result = groceriesDAO.loadGroceryList();

        assertTrue(result.getGroceryList().isEmpty(), "The grocery list should be empty if the file does not exist.");
    }
}