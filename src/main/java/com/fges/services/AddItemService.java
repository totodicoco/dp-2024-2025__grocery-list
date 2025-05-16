package com.fges.services;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import com.fges.services.DTO.AddItemDTO;

import java.io.IOException;
import java.util.HashMap;

public class AddItemService {
    private final GroceriesDAO groceriesDAO;

    public AddItemService(GroceriesDAO groceriesDAO) {
        this.groceriesDAO = groceriesDAO;
    }


    /**
     * Adds an item to the grocery list in the groceriesDAO.
     *
     * @param addItemDTO the DTO containing the item name, quantity, and category
     * @return true if the item was added successfully, false otherwise
     * @throws IOException if there is an error saving the grocery list
     */
    public Boolean add(AddItemDTO addItemDTO) throws IOException {
        Boolean success = false;

        String itemName = addItemDTO.itemName();
        int quantity = addItemDTO.quantity();
        String category = addItemDTO.category();

        GroceryList groceryList = groceriesDAO.loadGroceryList();
        groceryList.getGroceryList().computeIfAbsent(category, k -> new HashMap<>())
                .put(itemName, groceryList.getGroceryList().getOrDefault(category, new HashMap<>())
                        .getOrDefault(itemName, 0) + quantity);
        groceriesDAO.saveGroceryList(groceryList);

        return success;
    }

}
