package com.fges.services;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import com.fges.services.DTO.RemoveGroceriesItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class RemoveGroceriesItemServiceTest {

    private GroceriesDAO groceriesDAO;
    private RemoveGroceriesItemService removeGroceriesItemService;

    @BeforeEach
    void setUp() {
        groceriesDAO = mock(GroceriesDAO.class);
        removeGroceriesItemService = new RemoveGroceriesItemService(groceriesDAO);
    }

    @Test
    void should_remove_item_from_grocery_list() throws IOException {
        // Arrange
        GroceryList.Builder builder = new GroceryList.Builder();
        builder.add("Apple", 2, "Fruits");
        builder.add("Banana", 2, "Fruits");
        GroceryList groceryList = builder.build();

        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        RemoveGroceriesItemDTO removeItemDTO = new RemoveGroceriesItemDTO("Apple");

        // Act
        removeGroceriesItemService.remove(removeItemDTO);

        // Assert
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
        assertFalse(groceryList.getGroceryList().get("Fruits").containsKey("Apple"));
    }

    @Test
    void should_remove_item_from_all_categories_in_grocery_list() throws IOException {
        // Arrange
        GroceryList.Builder builder = new GroceryList.Builder();
        builder.add("Apple", 2, "Fruits");
        builder.add("Apple", 3, "Snacks");
        builder.add("Banana", 2, "Fruits");
        builder.add("Cheetos", 2, "Snacks");
        GroceryList groceryList = builder.build();

        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        RemoveGroceriesItemDTO removeItemDTO = new RemoveGroceriesItemDTO("Apple");

        // Act
        removeGroceriesItemService.remove(removeItemDTO);

        // Assert
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
        assertFalse(groceryList.getGroceryList().get("Fruits").containsKey("Apple"), "Apple should be removed from Fruits");
        assertFalse(groceryList.getGroceryList().get("Snacks").containsKey("Apple"), "Apple should be removed from Snacks");
    }

    @Test
    void should_remove_category_when_last_item_is_removed() throws IOException {
        // Arrange
        GroceryList.Builder builder = new GroceryList.Builder();
        builder.add("Apple", 1, "Fruits");
        GroceryList groceryList = builder.build();

        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        RemoveGroceriesItemDTO removeItemDTO = new RemoveGroceriesItemDTO("Apple");

        // Act
        removeGroceriesItemService.remove(removeItemDTO);

        // Assert
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
        assertFalse(groceryList.getGroceryList().containsKey("Fruits"), "Fruits category should be removed when last item is removed");
    }
}