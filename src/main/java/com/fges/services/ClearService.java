package com.fges.services;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;

import java.io.IOException;
public class ClearService {
    private final GroceriesDAO groceriesDAO;

    public ClearService(GroceriesDAO groceriesDAO) {
        this.groceriesDAO = groceriesDAO;
    }

    /**
     * Clears the grocery list in the groceriesDAO.
     *
     * @return true if the grocery list was cleared successfully, false otherwise
     * @throws IOException if there is an error saving the grocery list
     */
    public Boolean clear() throws IOException {
        Boolean success = false;

        GroceryList groceryList = groceriesDAO.loadGroceryList();
        groceryList.getGroceryList().clear();
        groceriesDAO.saveGroceryList(groceryList);

        return success;
    }
}
