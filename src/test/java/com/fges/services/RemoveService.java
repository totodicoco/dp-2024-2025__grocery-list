package com.fges.services;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveServiceTest {

    private GroceriesDAO groceriesDAO;
    private RemoveService removeService;

    @BeforeEach
    void setUp() {
        groceriesDAO = mock(GroceriesDAO.class);
        removeService = new RemoveService(groceriesDAO);
    }

    @Test
    void should_removeItemFromGroceryList_whenItemExists() throws IOException {
        Map<String, Map<String, Integer>> groceryMap = new HashMap<>();
        GroceryList groceryList = GroceryList.fromMap(groceryMap);

        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        boolean result = removeService.remove("Apple", "Fruits");

        assertTrue(result);
        assertFalse(groceryList.getGroceryList().get("Fruits").containsKey("Apple"));
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
    }

    @Test
    void should_notRemoveItem_whenItemDoesNotExist() throws IOException {
        Map<String, Map<String, Integer>> groceryMap = new HashMap<>();
        groceryMap.put("Fruits", new HashMap<>(Map.of("Banana", 5)));
        GroceryList groceryList = GroceryList.fromMap(groceryMap);

        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        boolean result = removeService.remove("Apple", "Fruits");

        assertFalse(result);
        assertTrue(groceryList.getGroceryList().get("Fruits").containsKey("Banana"));
        verify(groceriesDAO, never()).saveGroceryList(any());
    }

    @Test
    void should_notRemoveItem_whenCategoryDoesNotExist() throws IOException {
        Map<String, Map<String, Integer>> groceryMap = new HashMap<>();
        groceryMap.put("Vegetables", new HashMap<>(Map.of("Carrot", 2)));
        GroceryList groceryList = GroceryList.fromMap(groceryMap);

        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        boolean result = removeService.remove("Apple", "Fruits");

        assertFalse(result);
        assertFalse(groceryList.getGroceryList().containsKey("Fruits"));
        verify(groceriesDAO, never()).saveGroceryList(any());
    }
}