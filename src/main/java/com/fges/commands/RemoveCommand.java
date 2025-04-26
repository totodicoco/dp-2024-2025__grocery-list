package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.services.RemoveService;

import java.io.IOException;
import java.util.List;

public class RemoveCommand implements Command {
    private final List<String> args;
    private final GroceriesDAO groceriesDAO;
    private final String category;

    public RemoveCommand(List<String> args, GroceriesDAO groceriesDAO, String category) {
        this.args = args;
        this.groceriesDAO = groceriesDAO;
        this.category = category;
    }

    public void validateArgs() {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: remove <item>");
        }
    }

    public void execute() throws IOException {
        String itemName = args.get(1);
        RemoveService removeService = new RemoveService(groceriesDAO);
        removeService.remove(itemName, category);
    }
}