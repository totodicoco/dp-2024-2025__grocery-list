package com.fges.groceriesDAO;

import com.fges.modules.GroceryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
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
        // Créer une liste de courses
        GroceryList groceryList = GroceryList.fromMap(Map.of(
                "Fruits", Map.of("Apple", 3, "Banana", 5),
                "Vegetables", Map.of("Carrot", 2)
        ));

        // Sauvegarder la liste
        groceriesDAO.saveGroceryList(groceryList);

        // Contenu attendu du fichier CSV
        String expectedContent = """
                Category,Item,Quantity
                Fruits,Apple,3
                Fruits,Banana,5
                Vegetables,Carrot,2
                """;

        // Lire le contenu réel du fichier créé
        String actualContent = Files.readString(Path.of(testFileName));

        // Vérifier que le contenu est bien celui attendu
        assertEquals(expectedContent.trim(), actualContent.trim());

        // Supprimer le fichier de test
        Files.deleteIfExists(Path.of(testFileName));
    }

    @Test
    void should_loadGroceryList_whenFileExists() throws IOException {
        // Contenu du fichier CSV pour simuler une existence
        String fileContent = """
                Category,Item,Quantity
                Fruits,Apple,3
                Fruits,Banana,5
                Vegetables,Carrot,2
                """;
        Files.writeString(Path.of(testFileName), fileContent);

        // Charger la liste de courses depuis le fichier
        GroceryList result = groceriesDAO.loadGroceryList();

        // Carte attendue à partir du contenu du fichier CSV
        Map<String, Map<String, Integer>> expectedMap = Map.of(
                "Fruits", Map.of("Apple", 3, "Banana", 5),
                "Vegetables", Map.of("Carrot", 2)
        );

        // Vérifier que la liste chargée correspond à celle attendue
        assertEquals(GroceryList.fromMap(expectedMap), result);

        // Supprimer le fichier après le test
        Files.deleteIfExists(Path.of(testFileName));
    }

    @Test
    void should_loadGroceryList_whenFileDoesNotExist() throws IOException {
        // Tester le cas où le fichier n'existe pas
        GroceryList result = groceriesDAO.loadGroceryList();

        // Vérifier que la liste est vide
        assertTrue(result.getGroceryList().isEmpty(), "La liste de courses doit être vide si le fichier n'existe pas.");
    }
}
