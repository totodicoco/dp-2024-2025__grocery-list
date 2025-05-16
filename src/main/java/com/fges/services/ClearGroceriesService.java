package com.fges.services;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import com.fges.services.DTO.ClearGroceriesDTO;

import java.io.IOException;
public class ClearGroceriesService {
    private final GroceriesDAO groceriesDAO;

    public ClearGroceriesService(GroceriesDAO groceriesDAO) {
        this.groceriesDAO = groceriesDAO;
    }

    /**
     * Clears the grocery list in the groceriesDAO.
     *
     * @return true if the grocery list was cleared successfully, false otherwise
     * @throws IOException if there is an error saving the grocery list
     */
    public Boolean clear(ClearGroceriesDTO clearGroceriesDTO) throws IOException {
        Boolean success = false;

        GroceryList groceryList = groceriesDAO.loadGroceryList();
        groceryList.getGroceryList().clear();
        groceriesDAO.saveGroceryList(groceryList);

        return success;
    }
}
