package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;

import java.util.List;
import java.util.Map;

public class CommandFactory {
    private final GroceriesDAO groceriesDAO;
    private final String category;

    public CommandFactory(GroceriesDAO groceriesDAO, String category) {
        this.groceriesDAO = groceriesDAO;
        this.category = category;
    }

    public Command getCommand(List<String> args) {
        String commandName = args.get(0);
        return switch (commandName) {
            case "add" -> new AddCommand(args, groceriesDAO, category);
            case "list" -> new ListCommand(args, groceriesDAO);
            case "remove" -> new RemoveCommand(args, groceriesDAO, category);
            case "clear" -> new ClearCommand(args, groceriesDAO);
            case "info" -> new InfoCommand(args);
            default -> throw new IllegalArgumentException("Unknown command: " + commandName);
        };
    }
}