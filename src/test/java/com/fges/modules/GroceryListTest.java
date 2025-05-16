package com.fges.modules;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceryListTest {

    private GroceryList groceryList;

    @BeforeEach
    public void setUp() {
        Map<String, Map<String, Integer>> testData = new HashMap<>();
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("Apple", 3);
        testData.put("Fruits", fruits);

        groceryList = GroceryList.fromMap(testData);
    }

    @Test
    public void should_create_from_map() {
        assertNotNull(groceryList);
        assertEquals(1, groceryList.getGroceryList().size());
    }

    @Test
    public void should_get_grocery_list() {
        Map<String, Map<String, Integer>> list = groceryList.getGroceryList();
        assertTrue(list.containsKey("Fruits"));
        assertEquals(1, list.get("Fruits").size());
        assertEquals(3, list.get("Fruits").get("Apple"));
    }

    @Test
    public void should_get_all_items_from_given_category() {
        Map<String, Integer> items = groceryList.getCategoryItems("Fruits");
        assertNotNull(items);
        assertEquals(1, items.size());
        assertTrue(items.containsKey("Apple"));
        assertEquals(3, items.get("Apple"));
    }

    @Test
    public void should_get_all_categories_containing_given_item() {
        List<String> categories = groceryList.getAllCategoriesWithItem("Apple");
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertTrue(categories.contains("Fruits"));
    }

    @Test
    public void testGetAllCategoriesWithNonExistentItem() {
        List<String> categories = groceryList.getAllCategoriesWithItem("Banana");
        assertNotNull(categories);
        assertTrue(categories.isEmpty());
    }
}
