package com.fges.groceriesDAO;

import com.fges.modules.GroceryList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class GroceriesDAOImplCsvTest {

    private GroceriesDAOImplCsv groceriesDAO;
    private final String testFileName = "test-groceries.csv";

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Path.of(testFileName)); // Ensure no leftover file exists
        Files.createFile(Path.of(testFileName));    // Create the test file
        groceriesDAO = new GroceriesDAOImplCsv(testFileName);
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
            Category,Item,Quantity
            Vegetables,Carrot,2
            Fruits,Apple,3
            Fruits,Banana,5
            """;

        String actualContent = Files.readString(Path.of(testFileName));

        assertEquals(expectedContent.trim(), actualContent.trim());
    }

    @Test
    void should_load_grocery_list_to_file_when_file_exists() throws IOException {
        String fileContent = """
            Category,Item,Quantity
            Vegetables,Carrot,2
            Fruits,Apple,3
            Fruits,Banana,5
            """;
        Files.writeString(Path.of(testFileName), fileContent);

        GroceryList result = groceriesDAO.loadGroceryList();

        GroceryList.Builder builder = new GroceryList.Builder();
        builder.add("Apple", 3, "Fruits");
        builder.add("Banana", 5, "Fruits");
        builder.add("Carrot", 2, "Vegetables");
        GroceryList expectedGroceryList = builder.build();

        assertEquals(expectedGroceryList, result); // GroceryList equals method overridden so its all good
    }

    @Test
    void should_load_grocery_list_to_file_when_file_does_not_exist() throws IOException {
        Files.deleteIfExists(Path.of(testFileName)); // Ensure the file does not exist

        GroceryList result = groceriesDAO.loadGroceryList();

        assertTrue(result.getGroceryList().isEmpty(), "La liste de courses doit être vide si le fichier n'existe pas.");
    }
}
