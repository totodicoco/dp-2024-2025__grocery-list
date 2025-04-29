package com.fges.services;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import com.fges.services.DTO.RemoveDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RemoveService {
    private final GroceriesDAO groceriesDAO;

    public RemoveService(GroceriesDAO groceriesDAO) {
        this.groceriesDAO = groceriesDAO;
    }

    /**
     * Removes an item from the grocery list in the groceriesDAO.
     *
     * @param removeDTO the DTO containing the item name to remove
     * @return true if the item was removed successfully, false otherwise
     * @throws IOException if there is an error saving the grocery list
     */
    public Boolean remove(RemoveDTO removeDTO) throws IOException {
        Boolean success = false;

        GroceryList groceryList = groceriesDAO.loadGroceryList();
        String itemName = removeDTO.itemName();
        List<String> categories = groceryList.getAllCategoriesWithItem(itemName);
        for(String category : categories){
            Map<String, Integer> categoryItems = groceryList.getCategoryItems(category);
            if (categoryItems == null){
                throw new IOException("Category not found: " + category);
            }
            categoryItems.remove(itemName);
            // If the category is empty after removing the item, remove the category from the grocery list
            if (categoryItems.isEmpty()) {
                groceryList.getGroceryList().remove(category);
            }
        }
        groceriesDAO.saveGroceryList(groceryList);

        return success;
    }
}
