package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.groceriesDAO.GroceriesDAOFactory;
import com.fges.modules.OptionsUsed;
import com.fges.services.RemoveService;

import java.io.IOException;
import java.util.List;

public class RemoveCommand implements Command {
    private final List<String> args;
    private final OptionsUsed optionsUsed;

    public RemoveCommand(List<String> args, OptionsUsed optionsUsed) {
        this.args = args;
        this.optionsUsed = optionsUsed;
    }

    @Override
    public void validateArgs() {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: remove <item>");
        }
        if (optionsUsed.getFilename() == null) {
            throw new IllegalArgumentException("No filename provided. Use -s <filename> to set the filename.");
        }
    }

    @Override
    public void execute() throws IOException {
        String itemName = args.get(1);
        GroceriesDAOFactory groceriesDAOFactory = new GroceriesDAOFactory();
        GroceriesDAO groceriesDAO = groceriesDAOFactory.createGroceriesDAO(optionsUsed.getFormat(), optionsUsed.getFilename());
        RemoveService removeService = new RemoveService(groceriesDAO);
        removeService.remove(itemName, optionsUsed.getCategory());
    }
}