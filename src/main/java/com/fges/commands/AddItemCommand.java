package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.groceriesDAO.GroceriesDAOFactory;
import com.fges.services.AddItemService;
import com.fges.modules.OptionsUsed;
import com.fges.services.DTO.AddItemDTO;

import java.io.IOException;
import java.util.List;

public class AddItemCommand implements Command {
    private final List<String> args;
    private final OptionsUsed optionsUsed;

    public AddItemCommand(List<String> args, OptionsUsed optionsUsed) {
        this.args = args;
        this.optionsUsed = optionsUsed;
    }

    @Override
    public void validateArgs() {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: add <item> <quantity>");
        }
        try {
            Integer.parseInt(args.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The quantity must be an integer");
        }
        if (this.optionsUsed.getFilename() == null) {
            throw new IllegalArgumentException("No filename provided. Use -s <filename> to set the filename.");
        }
    }

    @Override
    public void execute() throws IOException {
        String itemName = args.get(1);
        GroceriesDAOFactory groceriesDAOFactory = new GroceriesDAOFactory();
        GroceriesDAO groceriesDAO = groceriesDAOFactory.createGroceriesDAO(optionsUsed.getFormat(), optionsUsed.getFilename());
        int quantity = Integer.parseInt(args.get(2));
        AddItemService addItemService = new AddItemService(groceriesDAO);
        AddItemDTO addItemDTO = new AddItemDTO(itemName, quantity, optionsUsed.getCategory());
        addItemService.add(addItemDTO);
    }
}