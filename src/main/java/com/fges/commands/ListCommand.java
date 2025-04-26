package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.services.ListService;

import java.io.IOException;
import java.util.List;

public class ListCommand implements Command {
    private final List<String> args;
    private final GroceriesDAO groceriesDAO;

    public ListCommand(List<String> args, GroceriesDAO groceriesDAO) {
        this.args = args;
        this.groceriesDAO = groceriesDAO;
    }

    public void validateArgs() {
        if (args.size() != 1) {
            throw new IllegalArgumentException("list does not take any arguments");
        }
    }

    public void execute() throws IOException {
        ListService listService = new ListService(groceriesDAO);
        listService.list();
    }
}