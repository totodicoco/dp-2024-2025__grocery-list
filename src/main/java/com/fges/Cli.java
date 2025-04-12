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
        // Nothing to add here
        // Est ce que cette classe viole le le principe de responsabilité unique ? OUI car ça store les arguments et ça les formattent aussi?! Mais je suis FATIGUÉ et c'est DIFFICILE ce cours mais J'ADORE.
        // Après j'ai fais un truc banger pour le DAO groceries donc je vais me poser pour l'instant.
    }

    public void run(String[] args) throws ParseException, Exception{
        CommandLineParser parser = new DefaultParser();
        Options cliOptions = new Options();
        cliOptions.addOption("c", "category", true, "Catégorie pour les articles");
        cliOptions.addRequiredOption("s", "source", true, "Fichier contenant la liste de courses");
        cliOptions.addOption("f", "format", true, "Format de stockage: json ou csv (défaut: json)");

        CommandLine cmd;
        // Parse the command line options
        try {
            cmd = parser.parse(cliOptions, args);
            this.category = cmd.getOptionValue("c", "default");
            this.fileName = cmd.getOptionValue("s");
            this.format = cmd.getOptionValue("f");

            // Determine format based on file extension if not provided
            if (this.format == null) {
                if (this.fileName.endsWith(".csv")) {
                    this.format = "csv";
                } else if (this.fileName.endsWith(".json")) {
                    this.format = "json";
                } else {
                    this.format = "json"; // Default to json
                }
            }
        } catch (ParseException ex) {
            throw new ParseException("Fail to parse arguments: " + ex.getMessage());
        }

        // Add file extension if not present
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
