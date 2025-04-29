package com.fges.services;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import com.fges.services.DTO.AddDTO;

import java.io.IOException;
import java.util.HashMap;

public class AddService {
    private final GroceriesDAO groceriesDAO;

    public AddService(GroceriesDAO groceriesDAO) {
        this.groceriesDAO = groceriesDAO;
    }


    /**
     * Adds an item to the grocery list in the groceriesDAO.
     *
     * @param addDTO the DTO containing the item name, quantity, and category
     * @return true if the item was added successfully, false otherwise
     * @throws IOException if there is an error saving the grocery list
     */
    public Boolean add(AddDTO addDTO) throws IOException {
        Boolean success = false;

        String itemName = addDTO.itemName();
        int quantity = addDTO.quantity();
        String category = addDTO.category();

        GroceryList groceryList = groceriesDAO.loadGroceryList();
        groceryList.getGroceryList().computeIfAbsent(category, k -> new HashMap<>())
                .put(itemName, groceryList.getGroceryList().getOrDefault(category, new HashMap<>())
                        .getOrDefault(itemName, 0) + quantity);
        groceriesDAO.saveGroceryList(groceryList);

        return success;
    }

}
