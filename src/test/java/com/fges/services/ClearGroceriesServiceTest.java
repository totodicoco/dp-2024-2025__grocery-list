package com.fges.services;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import com.fges.services.DTO.ClearGroceriesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ClearGroceriesServiceTest {

    private GroceriesDAO groceriesDAO;
    private ClearGroceriesService clearGroceriesService;

    @BeforeEach
    void setUp() {
        groceriesDAO = mock(GroceriesDAO.class);
        clearGroceriesService = new ClearGroceriesService(groceriesDAO);
    }

    @Test
    void should_clear_grocery_list() throws IOException {
        // Arrange
        GroceryList groceryList = new GroceryList.Builder().build();
        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        // Act
        clearGroceriesService.clear(new ClearGroceriesDTO());

        // Assert
        assertTrue(groceryList.getGroceryList().isEmpty(), "Grocery list should be empty after clearing");
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
    }
}