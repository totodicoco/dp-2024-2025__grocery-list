package com.fges.services;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;

import java.io.IOException;
import java.util.Map;

public class ListService {

    private final GroceriesDAO groceriesDAO;;

    public ListService(GroceriesDAO groceriesDAO) {
        this.groceriesDAO = groceriesDAO;
    }

    public Boolean list() throws IOException{
        boolean success = false;

        GroceryList groceryList = groceriesDAO.loadGroceryList();
        Map<String, Map<String, Integer>> groceryListContents = groceryList.getGroceryList();
        if (groceryListContents.isEmpty()) {
            System.out.println("Votre liste de courses est vide.");
        } else {
            StringBuilder listOutput = new StringBuilder();
            groceryListContents.forEach((cat, items) -> {
                listOutput.append("#").append(cat).append(":\n");
                items.forEach((item, qty) -> listOutput.append(item).append(": ").append(qty).append("\n"));
            });
            System.out.println(listOutput);
        }

        return success;
    }

}
