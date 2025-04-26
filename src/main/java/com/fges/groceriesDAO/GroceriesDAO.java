package com.fges.groceriesDAO;

import com.fges.modules.GroceryList;

import java.io.IOException;

public interface GroceriesDAO{
    /**
     * Save the grocery list to a file.
     *
     * @param groceryList The grocery list to save.
     * @throws IOException If an I/O error occurs.
     */
    void saveGroceryList(GroceryList groceryList) throws IOException;

    /**
     * Load the grocery list from a file.
     *
     * @return The loaded grocery list.
     * @throws IOException If an I/O error occurs.
     */
    GroceryList loadGroceryList() throws IOException;

    /**
     * Get the filename of the grocery list. A GroceriesDAO should always have an associated file.
     *
     * @return The filename of the grocery list.
     */
    String getFilename();
}
