package com.fges.groceryWeb;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import com.fges.services.AddService;
import com.fges.services.DTO.AddDTO;
import com.fges.services.DTO.InfoDTO;
import com.fges.services.DTO.ListDTO;
import com.fges.services.DTO.RemoveDTO;
import com.fges.services.InfoService;
import com.fges.services.ListService;
import com.fges.services.RemoveService;
import fr.anthonyquere.MyGroceryShop;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimpleGroceryShop implements MyGroceryShop{
    private final List<WebGroceryItem> groceries;
    private final GroceriesDAO groceriesDAO;
    public SimpleGroceryShop(GroceriesDAO groceriesDAO) throws IOException {
        this.groceriesDAO = groceriesDAO;
        this.groceries = groceryListToGroceries(groceriesDAO.loadGroceryList());
    }
    private List<WebGroceryItem> groceryListToGroceries(GroceryList groceryList){
        List<WebGroceryItem> groceries = new ArrayList<>();
        groceryList.getGroceryList().forEach((category, items) -> {
            items.forEach((name, quantity) -> {
                groceries.add(new WebGroceryItem(name, quantity, category));
            });
        });
        return groceries;
    }
    private void reload() throws IOException {
        groceries.clear();
        groceries.addAll(groceryListToGroceries(groceriesDAO.loadGroceryList()));
    }

    // Might as well also list in terminal? This method only seems to run when the groceries are updated or when loading the website so it should be fine
    @Override
    public List<WebGroceryItem> getGroceries() {
        ListService listService = new ListService(groceriesDAO);
        ListDTO listDTO = new ListDTO();
        try {
            listService.list(listDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return groceries;
    }

    @Override
    public void addGroceryItem(String name, int quantity, String category) {
        AddService addService = new AddService(groceriesDAO);
        AddDTO addDTO = new AddDTO(name, quantity, category);
        try {
            addService.add(addDTO);
            reload();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeGroceryItem(String name) {
        RemoveService removeService = new RemoveService(groceriesDAO);
        RemoveDTO removeDTO = new RemoveDTO(name);
        try {
            removeService.remove(removeDTO);
            reload();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Runtime getRuntime() {
        InfoService infoService = new InfoService();
        LocalDate today = LocalDate.now();
        String osName = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        InfoDTO infoDTO = new InfoDTO(today, osName, javaVersion);
            infoService.info(infoDTO);
        return new Runtime(
                LocalDate.now(),
                System.getProperty("java.version"),
                System.getProperty("os.name")
        );
    }
}