package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.groceriesDAO.GroceriesDAOFactory;
import com.fges.modules.OptionsUsed;
import com.fges.services.DTO.ListGroceriesDTO;
import com.fges.services.ListGroceriesService;

import java.io.IOException;
import java.util.List;

public class ListGroceriesCommand implements Command {
    private final List<String> args;
    private final OptionsUsed optionsUsed;

    public ListGroceriesCommand(List<String> args, OptionsUsed optionsUsed) {
        this.args = args;
        this.optionsUsed = optionsUsed;
    }

    @Override
    public void validateArgs() {
        if (args.size() != 1) {
            throw new IllegalArgumentException("list does not take any arguments");
        }
        if (optionsUsed.getFilename() == null) {
            throw new IllegalArgumentException("No filename provided. Use -s <filename> to set the filename.");
        }
    }

    @Override
    public void execute() throws IOException {
        GroceriesDAOFactory groceriesDAOFactory = new GroceriesDAOFactory();
        GroceriesDAO groceriesDAO = groceriesDAOFactory.createGroceriesDAO(optionsUsed.getFormat(), optionsUsed.getFilename());
        ListGroceriesService listGroceriesService = new ListGroceriesService(groceriesDAO);
        ListGroceriesDTO listGroceriesDTO = new ListGroceriesDTO();
        listGroceriesService.list(listGroceriesDTO);
    }
}