package com.fges.services;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import com.fges.services.DTO.AddGroceriesItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddGroceriesItemServiceTest {

    private GroceriesDAO groceriesDAO;
    private AddGroceriesItemService addGroceriesItemService;

    @BeforeEach
    void setUp() {
        groceriesDAO = mock(GroceriesDAO.class);
        addGroceriesItemService = new AddGroceriesItemService(groceriesDAO);
    }

    @Test
    void should_add_item_to_existing_category() throws IOException {
        // Arrange
        GroceryList.Builder builder = new GroceryList.Builder();
        builder.add("Apple", 2, "Fruits");
        GroceryList groceryList = builder.build();

        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        AddGroceriesItemDTO addItemDTO = new AddGroceriesItemDTO("Banana", 3, "Fruits");

        // Act
        Boolean result = addGroceriesItemService.add(addItemDTO);

        // printing the grocery list for debugging
        System.out.println("Grocery List after adding item: " + groceryList.getGroceryList());

        // Assert
        assertTrue(result);
        assertEquals(3, groceryList.getGroceryList().get("Fruits").get("Banana").intValue());
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
    }

    @Test
    void should_add_item_to_new_category() throws IOException {
        // Arrange
        GroceryList.Builder builder = new GroceryList.Builder();
        GroceryList groceryList = builder.build();

        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        AddGroceriesItemDTO addItemDTO = new AddGroceriesItemDTO("Carrot", 5, "Vegetables");

        // Act
        Boolean result = addGroceriesItemService.add(addItemDTO);

        // Assert
        assertTrue(result);
        assertEquals(5, groceryList.getGroceryList().get("Vegetables").get("Carrot").intValue());
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
    }

    @Test
    void should_increment_quantity_for_existing_item() throws IOException {
        // Arrange
        GroceryList.Builder builder = new GroceryList.Builder();
        builder.add("Apple", 2, "Fruits");
        GroceryList groceryList = builder.build();

        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        AddGroceriesItemDTO addItemDTO = new AddGroceriesItemDTO("Apple", 3, "Fruits");

        // Act
        Boolean result = addGroceriesItemService.add(addItemDTO);

        // Assert
        assertTrue(result);
        assertEquals(5, groceryList.getGroceryList().get("Fruits").get("Apple").intValue());
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
    }
}