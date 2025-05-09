package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.groceriesDAO.GroceriesDAOFactory;
import com.fges.modules.OptionsUsed;
import com.fges.services.ClearService;
import com.fges.services.DTO.ClearDTO;

import java.io.IOException;
import java.util.List;

public class ClearCommand implements Command {
    private final List<String> args;
    private final OptionsUsed optionsUsed;

    public ClearCommand(List<String> args, OptionsUsed optionsUsed) {
        this.args = args;
        this.optionsUsed = optionsUsed;
    }

    @Override
    public void validateArgs() {
        if (args.size() != 1) {
            throw new IllegalArgumentException("clear does not take any arguments");
        }
        if (optionsUsed.getFilename() == null) {
            throw new IllegalArgumentException("No filename provided. Use -s <filename> to set the filename.");
        }
    }

    @Override
    public void execute() throws IOException {
        GroceriesDAOFactory groceriesDAOFactory = new GroceriesDAOFactory();
        GroceriesDAO groceriesDAO = groceriesDAOFactory.createGroceriesDAO(optionsUsed.getFormat(), optionsUsed.getFilename());
        ClearService clearService = new ClearService(groceriesDAO);
        ClearDTO clearDTO = new ClearDTO();
        clearService.clear(clearDTO);
    }
}