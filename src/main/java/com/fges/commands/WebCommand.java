package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.groceriesDAO.GroceriesDAOFactory;
import com.fges.modules.OptionsUsed;
import com.fges.services.DTO.WebDTO;
import com.fges.services.WebService;

import java.io.IOException;
import java.util.List;

public class WebCommand implements Command {
    private final List<String> args;
    private final OptionsUsed optionsUsed;

    public WebCommand(List<String> args, OptionsUsed optionsUsed) {
        this.args = args;
        this.optionsUsed = optionsUsed;
    }

    @Override
    public void validateArgs() {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: web <port>");
        }
        try {
            Integer.parseInt(args.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The port number must be an integer.");
        }
        if (this.optionsUsed.getFilename() == null) {
            throw new IllegalArgumentException("No filename provided. Use -s <filename> to set the filename.");
        }
    }

    @Override
    public void execute() throws IOException {
        int portNumber = Integer.parseInt(args.get(1));
        GroceriesDAOFactory groceriesDAOFactory = new GroceriesDAOFactory();
        GroceriesDAO groceriesDAO = groceriesDAOFactory.createGroceriesDAO(optionsUsed.getFormat(), optionsUsed.getFilename());
        WebService webService = new WebService(groceriesDAO);
        WebDTO webDTO = new WebDTO(portNumber);
        webService.web(webDTO);
    }
}