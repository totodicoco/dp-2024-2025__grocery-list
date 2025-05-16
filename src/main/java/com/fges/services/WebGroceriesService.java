package com.fges.services;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.groceryWeb.SimpleGroceryShop;
import com.fges.services.DTO.WebGroceriesDTO;
import fr.anthonyquere.GroceryShopServer;
import fr.anthonyquere.MyGroceryShop;

import java.io.IOException;

public class WebGroceriesService {
    private final GroceriesDAO groceriesDAO;

    public WebGroceriesService(GroceriesDAO groceriesDAO) {
        this.groceriesDAO = groceriesDAO;
    }


    /**
     * Adds an item to the grocery list in the groceriesDAO.
     *
     * @return true if the item was added successfully, false otherwise
     * @throws IOException if there is an error saving the grocery list
     */
    public Boolean web(WebGroceriesDTO webGroceriesDTO) throws IOException {
        Boolean success = false;

        MyGroceryShop groceryShop = new SimpleGroceryShop(groceriesDAO);
        GroceryShopServer server = new GroceryShopServer(groceryShop);
        int port = webGroceriesDTO.portNumber();
        server.start(port);

        System.out.println("Grocery shop server started at http://localhost:8080");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return success;
    }

}
