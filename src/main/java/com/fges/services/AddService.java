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
