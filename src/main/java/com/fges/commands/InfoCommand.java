package com.fges.commands;

import com.fges.services.InfoService;

import java.util.List;

public class InfoCommand implements Command {
    private final List<String> args;

    public InfoCommand(List<String> args) {
        this.args = args;
    }

    @Override
    public void validateArgs() {
        if (args.size() != 1) {
            throw new IllegalArgumentException("list does not take any arguments");
        }
    }

    @Override
    public void execute(){
        InfoService infoService = new InfoService();
        infoService.info();
    }
}