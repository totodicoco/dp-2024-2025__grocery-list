package com.fges.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.groceriesDAO.GroceriesDAOImplCsv;
import com.fges.groceriesDAO.GroceriesDAOImplJson;
import org.apache.commons.cli.*;

import java.util.List;

public class CommandFactory {
    public String category;
    public String fileName;
    public String format;
    public List<String> command;

    public CommandFactory(String[] args) throws ParseException {
        CommandLine cmd = parseArguments(args);
        this.category = cmd.getOptionValue("c", "default");
        this.fileName = cmd.getOptionValue("s");
        this.format = determineFormat(cmd.getOptionValue("f"), this.fileName);
        this.command = cmd.getArgList();
    }

    private CommandLine parseArguments(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        Options cliOptions = new Options();
        cliOptions.addOption("c", "category", true, "Category for the items");
        cliOptions.addOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("f", "format", true, "File format: json or csv (default: json)");
        return parser.parse(cliOptions, args);
    }

    private String determineFormat(String format, String fileName) {
        if (format == null) {
            if (fileName != null) {
                if (fileName.endsWith(".csv")) return "csv";
                if (fileName.endsWith(".json")) return "json";
            }
            return "json"; // Default to json
        }
        return format;
    }

    /**
     * Returns the command object based on the command line arguments. It also creates the appropriate DAO I guess.
     *
     * @param OBJECT_MAPPER The ObjectMapper instance for JSON processing.
     * @return The Command object corresponding to the command line arguments.
     */
    public Command getCommand(ObjectMapper OBJECT_MAPPER) {
        GroceriesDAO groceriesDAO;
        switch (this.format) {
            case "json" -> groceriesDAO = new GroceriesDAOImplJson(this.fileName, OBJECT_MAPPER);
            case "csv" -> groceriesDAO = new GroceriesDAOImplCsv(this.fileName);
            default -> throw new IllegalArgumentException("Unknown format: " + this.format);
        }
        String commandName = this.command.get(0);
        return switch (commandName) {
            case "add" -> new AddCommand(this.command, groceriesDAO, category);
            case "list" -> new ListCommand(this.command, groceriesDAO);
            case "remove" -> new RemoveCommand(this.command, groceriesDAO, category);
            case "clear" -> new ClearCommand(this.command, groceriesDAO);
            case "info" -> new InfoCommand(this.command);
            default -> throw new IllegalArgumentException("Unknown command: " + commandName);
        };
    }
}