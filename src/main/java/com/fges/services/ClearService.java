package com.fges.services;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;

import java.io.IOException;
public class ClearService {
    private final GroceriesDAO groceriesDAO;

    public ClearService(GroceriesDAO groceriesDAO) {
        this.groceriesDAO = groceriesDAO;
    }

    public Boolean clear() throws IOException {
        Boolean success = false;

        GroceryList groceryList = groceriesDAO.loadGroceryList();
        groceryList.getGroceryList().clear();
        groceriesDAO.saveGroceryList(groceryList);

        return success;
    }
}
