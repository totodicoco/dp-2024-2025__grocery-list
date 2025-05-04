package com.fges.commands;

import com.fges.services.DTO.InfoDTO;
import com.fges.services.InfoService;

import java.time.LocalDate;
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
        LocalDate today = LocalDate.now();
        String osName = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        InfoDTO infoDTO = new InfoDTO(today, osName, javaVersion);
        infoService.info(infoDTO);
    }
}