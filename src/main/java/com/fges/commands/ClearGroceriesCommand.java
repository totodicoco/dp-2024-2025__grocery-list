package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.groceriesDAO.GroceriesDAOFactory;
import com.fges.modules.OptionsUsed;
import com.fges.services.ClearGroceriesService;
import com.fges.services.DTO.ClearGroceriesDTO;

import java.io.IOException;
import java.util.List;

public class ClearGroceriesCommand implements Command {
    private final List<String> args;
    private final OptionsUsed optionsUsed;

    public ClearGroceriesCommand(List<String> args, OptionsUsed optionsUsed) {
        this.args = args;
        this.optionsUsed = optionsUsed;
    }

    @Override
    public void validateArgs() {
        if (args.size() != 1) {
            throw new IllegalArgumentException("clear does not take any arguments");
        }
        if (optionsUsed.getSource() == null) {
            throw new IllegalArgumentException("No filename provided. Use -s <filename> to set the filename.");
        }
    }

    @Override
    public void execute() throws IOException {
        GroceriesDAOFactory groceriesDAOFactory = new GroceriesDAOFactory();
        GroceriesDAO groceriesDAO = groceriesDAOFactory.createGroceriesDAO(optionsUsed.getFormat(), optionsUsed.getSource());
        ClearGroceriesService clearGroceriesService = new ClearGroceriesService(groceriesDAO);
        ClearGroceriesDTO clearGroceriesDTO = new ClearGroceriesDTO();
        clearGroceriesService.clear(clearGroceriesDTO);
    }
}