package com.fges.groceriesDAO;

import com.fges.modules.GroceryList;

import java.io.IOException;

public interface GroceriesDAO{
    void saveGroceryList(GroceryList groceryList) throws IOException;
    GroceryList loadGroceryList() throws IOException;
}
