package com.fges.services;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;

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
     * @param itemName the name of the item to add
     * @param quantity the quantity of the item to add
     * @param category the category of the item
     * @return true if the item was added successfully, false otherwise
     * @throws IOException if there is an error saving the grocery list
     */
    public Boolean add(String itemName, int quantity, String category) throws IOException {
        Boolean success = false;

        GroceryList groceryList = groceriesDAO.loadGroceryList();
        groceryList.getGroceryList().computeIfAbsent(category, k -> new HashMap<>())
                .put(itemName, groceryList.getGroceryList().getOrDefault(category, new HashMap<>())
                        .getOrDefault(itemName, 0) + quantity);
        groceriesDAO.saveGroceryList(groceryList);

        return success;
    }

}
