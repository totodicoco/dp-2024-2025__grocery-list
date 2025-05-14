package com.fges.groceriesDAO;

import com.fges.modules.GroceryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GroceriesDAOImplCsvTest {

    private GroceriesDAOImplCsv groceriesDAO;
    private final String testFileName = "test-groceries.csv";

    @BeforeEach
    void setUp() {
        groceriesDAO = new GroceriesDAOImplCsv(testFileName);
    }

    @Test
    void should_saveGroceryList() throws IOException {
        GroceryList groceryList = GroceryList.fromMap(Map.of(
                "Fruits", Map.of("Apple", 3, "Banana", 5),
                "Vegetables", Map.of("Carrot", 2)
        ));

        groceriesDAO.saveGroceryList(groceryList);

        String expectedContent = """
                Category,Item,Quantity
                Fruits,Apple,3
                Fruits,Banana,5
                Vegetables,Carrot,2
                """;

        String actualContent = Files.readString(Path.of(testFileName));

        assertEquals(expectedContent.trim(), actualContent.trim());

        Files.deleteIfExists(Path.of(testFileName));
    }

    @Test
    void should_loadGroceryList_whenFileExists() throws IOException {
        String fileContent = """
                Category,Item,Quantity
                Fruits,Apple,3
                Fruits,Banana,5
                Vegetables,Carrot,2
                """;
        Files.writeString(Path.of(testFileName), fileContent);

        GroceryList result = groceriesDAO.loadGroceryList();

        Map<String, Map<String, Integer>> expectedMap = Map.of(
                "Fruits", Map.of("Apple", 3, "Banana", 5),
                "Vegetables", Map.of("Carrot", 2)
        );

        assertEquals(GroceryList.fromMap(expectedMap), result);

        Files.deleteIfExists(Path.of(testFileName));
    }

    @Test
    void should_loadGroceryList_whenFileDoesNotExist() throws IOException {
        GroceryList result = groceriesDAO.loadGroceryList();

        assertTrue(result.getGroceryList().isEmpty(), "La liste de courses doit être vide si le fichier n'existe pas.");
    }
}
