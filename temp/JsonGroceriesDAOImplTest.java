package com.fges;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.temp.JsonGroceriesDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonGroceriesDAOImplTest {
    private static final String JSON_FILE = "test_grocery.json";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeEach
    void setup() throws IOException {
        // Delete the existing JSON file if it exists
        Files.deleteIfExists(Paths.get(JSON_FILE));
        // Create a new empty JSON file
        OBJECT_MAPPER.writeValue(new File(JSON_FILE), new HashMap<String, Map<String, Integer>>());
    }

    /* Tests for adding items to the grocery list
     * Test to add an entry to an empty list
     * Test to add item to an existing category where the item already exists
     * Test to add item to an existing category where the item does not exist
     * Test to add item to a new category
     * Test to add item to a new category with an existing item
     * Test to add item to a new category with a new item
     */
    @Test
    void should_add_entry_when_list_empty() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.add("Banana", 3, "Fruits");
        Map<String, Map<String, Integer>> articles = groceriesDao.loadGroceryList();
//        print articles
        System.out.println("PIPIPIPIPI" + articles);
        // Assert that the items were added correctly
        assertEquals(1, articles.size());
        assertEquals(3, articles.get("Fruits").get("Banana"));
    }

    @Test
    void should_add_item_to_existing_category_with_item() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.add("Banana", 3, "Fruits");
        groceriesDao.add("Banana", 2, "Fruits");
        Map<String, Map<String, Integer>> articles = groceriesDao.loadGroceryList();
        // Assert that the items were added correctly
        assertEquals(1, articles.size());
        assertEquals(5, articles.get("Fruits").get("Banana"));
    }

    @Test
    void should_add_item_to_existing_category_without_item() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.add("Banana", 3, "Fruits");
        groceriesDao.add("Apple", 2, "Fruits");
        Map<String, Map<String, Integer>> articles = groceriesDao.loadGroceryList();
        // Assert that the items were added correctly
        assertEquals(1, articles.size());
        assertEquals(3, articles.get("Fruits").get("Banana"));
        assertEquals(2, articles.get("Fruits").get("Apple"));
    }

    @Test
    void should_add_item_to_new_category() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.add("Banana", 3, "Fruits");
        groceriesDao.add("Carrot", 2, "Vegetables");
        Map<String, Map<String, Integer>> articles = groceriesDao.loadGroceryList();
        // Assert that the items were added correctly
        assertEquals(2, articles.size());
        assertEquals(3, articles.get("Fruits").get("Banana"));
        assertEquals(2, articles.get("Vegetables").get("Carrot"));
    }

    @Test
    void should_add_item_to_new_category_with_existing_item() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.add("Banana", 3, "Fruits");
        groceriesDao.add("Banana", 2, "Vegetables");
        Map<String, Map<String, Integer>> articles = groceriesDao.loadGroceryList();
        // Assert that the items were added correctly
        assertEquals(2, articles.size());
        assertEquals(3, articles.get("Fruits").get("Banana"));
        assertEquals(2, articles.get("Vegetables").get("Banana"));
    }

    @Test
    void should_add_item_to_new_category_with_new_item() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.add("Banana", 3, "Fruits");
        groceriesDao.add("Carrot", 2, "Vegetables");
        Map<String, Map<String, Integer>> articles = groceriesDao.loadGroceryList();
        // Assert that the items were added correctly
        assertEquals(2, articles.size());
        assertEquals(3, articles.get("Fruits").get("Banana"));
        assertEquals(2, articles.get("Vegetables").get("Carrot"));
    }

    /* Tests for listing items in the grocery list
        * Test to list items in an empty list as: "Votre liste de courses est vide."
        * Test to list items in a non-empty list with the format:
           # dairy:
           Milk: 10
           Tea: 999
           # default:
           Coffee: 2
     */
    @Test
    void should_list_items_in_empty_list() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        groceriesDao.list();
        String expectedOutput = "Votre liste de courses est vide.";
        assertTrue(outContent.toString().contains(expectedOutput.trim()));
    }

    @Test
    void should_list_items_in_correct_format() throws IOException{
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.add("Milk", 10, "Dairy");
        groceriesDao.add("Tea", 999, "Dairy");
        groceriesDao.add("Coffee", 2, "default");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        groceriesDao.list();
        String expectedOutput = "#Dairy:\n" +
                "Milk: 10\n" +
                "Tea: 999\n" +
                "#default:\n" +
                "Coffee: 2\n";
        assertTrue(outContent.toString().contains(expectedOutput.trim()));
    }

    /* Tests for removing items from the grocery list
        * Test to remove an item of a list
        * Test to remove the singular item of a list
     */

    @Test
    void should_remove_item() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.add("Banana", 3, "Fruits");
        groceriesDao.add("Apple", 2, "Fruits");
        groceriesDao.remove("Banana", "Fruits");
        Map<String, Map<String, Integer>> articles = groceriesDao.loadGroceryList();
        // Assert that the items were removed correctly
        assertEquals(1, articles.size());
        // Assert that the item was removed
        assertEquals(0, articles.get("Fruits").getOrDefault("Banana", 0));
    }

    @Test
    void should_remove_singular_item() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.add("Banana", 3, "Fruits");
        groceriesDao.remove("Banana", "Fruits");
        Map<String, Map<String, Integer>> articles = groceriesDao.loadGroceryList();
        // Assert that the items were removed correctly
        assertEquals(0, articles.size());
    }

    /* Tests for clearing the grocery list
        * Test to clear an empty list
        * Test to clear a non-empty list
     */

    @Test
    void should_clear_empty_list() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.clear();
        Map<String, Map<String, Integer>> articles = groceriesDao.loadGroceryList();
        // Assert that the items were cleared correctly
        assertEquals(0, articles.size());
    }

    @Test
    void should_clear_non_empty_list() throws IOException {
        var groceriesDao = new JsonGroceriesDAOImpl(JSON_FILE, OBJECT_MAPPER);
        groceriesDao.add("Banana", 3, "Fruits");
        groceriesDao.add("Apple", 2, "Fruits");
        groceriesDao.add("Carrot", 2, "Vegetables");
        groceriesDao.clear();
        Map<String, Map<String, Integer>> articles = groceriesDao.loadGroceryList();
        // Assert that the items were cleared correctly
        assertEquals(0, articles.size());
    }
}
