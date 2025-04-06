package com.fges;

import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.*;

public class Cli {
    public String category;
    public String fileName;
    public String format;
    public List<String> command;

    public Cli(){
        //Nothing to add here
    }

    public void run(String[] args) throws ParseException, Exception{
        CommandLineParser parser = new DefaultParser();
        Options cliOptions = new Options();
        cliOptions.addOption("c", "category", true, "Catégorie pour les articles");
        cliOptions.addRequiredOption("s", "source", true, "Fichier contenant la liste de courses");
        cliOptions.addOption("f", "format", true, "Format de stockage: json ou csv (défaut: json)");

        CommandLine cmd;
        try {
            cmd = parser.parse(cliOptions, args);
            this.category = cmd.getOptionValue("category", "default");
            this.fileName = cmd.getOptionValue("s");
            this.format = cmd.getOptionValue("f", "json");
        } catch (ParseException ex) {
            throw new ParseException("Fail to parse arguments: " + ex.getMessage());
        }

        // extension csv ou json si nécessaire
        if ("csv".equalsIgnoreCase(this.format) && !this.fileName.endsWith(".csv")) {
            this.fileName += ".csv";
        } else if ("json".equalsIgnoreCase(this.format) && !this.fileName.endsWith(".json")) {
            this.fileName += ".json";
        }

        // Get arguments excluding option arguments aka get command
        this.command = cmd.getArgList();
        if (this.command.isEmpty()) {
            throw new Exception("Missing Command");
        }
    }
}
