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

    public Boolean remove(String itemName, int quantity, String category) throws IOException {
        Boolean success = false;

        GroceryList groceryList = groceriesDAO.loadGroceryList();
        Map<String, Integer> categoryItems = groceryList.getCategoryItems(category);
        if (categoryItems != null) {
            categoryItems.remove(itemName);
            // Si la catégorie est vide, la supprimer
            if (categoryItems.isEmpty()) {
                groceryList.getGroceryList().remove(category);
            }
        }
        groceriesDAO.saveGroceryList(groceryList);

        return success;
    }


}
