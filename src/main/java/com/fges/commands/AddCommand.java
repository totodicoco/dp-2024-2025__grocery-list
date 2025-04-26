package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.services.AddService;

import java.io.IOException;
import java.util.List;

public class AddCommand implements Command {
    private final List<String> args;
    private final GroceriesDAO groceriesDAO;
    private final String category;

    public AddCommand(List<String> args, GroceriesDAO groceriesDAO, String category) {
        this.args = args;
        this.groceriesDAO = groceriesDAO;
        this.category = category;
    }

    public void validateArgs() {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: add <item> <quantity>");
        }
        try {
            Integer.parseInt(args.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The quantity must be an integer");
        }
    }

    public void execute() throws IOException {
        String itemName = args.get(1);
        int quantity = Integer.parseInt(args.get(2));
        AddService addService = new AddService(groceriesDAO);
        addService.add(itemName, quantity, category);
    }
}