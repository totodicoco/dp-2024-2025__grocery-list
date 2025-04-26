package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.services.InfoService;

import java.io.IOException;
import java.util.List;

public class InfoCommand implements Command {
    private final List<String> args;

    public InfoCommand(List<String> args) {
        this.args = args;
    }
    public void validateArgs() {
        if (args.size() != 1) {
            throw new IllegalArgumentException("list does not take any arguments");
        }
    }

    public void execute() throws IOException {
        InfoService infoService = new InfoService();
        infoService.info();
    }
}