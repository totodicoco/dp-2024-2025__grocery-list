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

class AddServiceTest {

    private GroceriesDAO groceriesDAO;
    private AddService addService;

    @BeforeEach
    void setUp() {
        groceriesDAO = mock(GroceriesDAO.class);
        addService = new AddService(groceriesDAO);
    }

    @Test
    void should_addItemToGroceryList() throws IOException {
        GroceryList groceryList = GroceryList.fromMap(new HashMap<>());
        boolean result = addService.add("Apple", 3, "Fruits");

        assertTrue(result);
        assertEquals(3, groceryList.getGroceryList().get("Fruits").get("Apple").intValue());
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
    }
}