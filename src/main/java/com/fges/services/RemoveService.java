package com.fges.services;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;

import java.io.IOException;
import java.util.Map;

public class RemoveService {
    private final GroceriesDAO groceriesDAO;

    public RemoveService(GroceriesDAO groceriesDAO) {
        this.groceriesDAO = groceriesDAO;
    }

    /**
     * Removes an item from the grocery list in the groceriesDAO.
     *
     * @param itemName the name of the item to remove
     * @param category the category of the item
     * @return true if the item was removed successfully, false otherwise
     * @throws IOException if there is an error saving the grocery list
     */
    public Boolean remove(String itemName, String category) throws IOException {
        Boolean success = false;

        GroceryList groceryList = groceriesDAO.loadGroceryList();
        Map<String, Integer> categoryItems = groceryList.getCategoryItems(category);
        if (categoryItems == null){
            throw new IOException("Category not found: " + category);
        }
        categoryItems.remove(itemName);
        // If the category is empty after removing the item, remove the category from the grocery list
        if (categoryItems.isEmpty()) {
            groceryList.getGroceryList().remove(category);
        }
        groceriesDAO.saveGroceryList(groceryList);

        return success;
    }


}
