package com.fges.groceryWeb;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import com.fges.services.AddGroceriesItemService;
import com.fges.services.DTO.AddGroceriesItemDTO;
import com.fges.services.DTO.InfoDTO;
import com.fges.services.DTO.ListGroceriesDTO;
import com.fges.services.DTO.RemoveGroceriesItemDTO;
import com.fges.services.InfoService;
import com.fges.services.ListGroceriesService;
import com.fges.services.RemoveGroceriesItemService;
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
        System.out.println(this.groceries);
        ListGroceriesService listGroceriesService = new ListGroceriesService(groceriesDAO);
        ListGroceriesDTO listGroceriesDTO = new ListGroceriesDTO();
        try {
            listGroceriesService.list(listGroceriesDTO);
            reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return groceries;
    }

    @Override
    public void addGroceryItem(String name, int quantity, String category) {
        AddGroceriesItemService addGroceriesItemService = new AddGroceriesItemService(groceriesDAO);
        AddGroceriesItemDTO addItemDTO = new AddGroceriesItemDTO(name, quantity, category);
        try {
            addGroceriesItemService.add(addItemDTO);
            reload();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeGroceryItem(String name) {
        RemoveGroceriesItemService removeGroceriesItemService = new RemoveGroceriesItemService(groceriesDAO);
        RemoveGroceriesItemDTO removeGroceriesItemDTO = new RemoveGroceriesItemDTO(name);
        try {
            removeGroceriesItemService.remove(removeGroceriesItemDTO);
            reload();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Runtime getRuntime() {
        LocalDate today = LocalDate.now();
        String osName = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        InfoService infoService = new InfoService();
        InfoDTO infoDTO = new InfoDTO(today, osName, javaVersion);
        infoService.info(infoDTO);
        return new Runtime(
                today,
                javaVersion,
                osName
        );
    }
}