package com.fges;

import com.fges.groceriesDAO.*;
import com.fges.services.*;
import com.fges.commands.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.*;

public class Main {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        try {
            System.exit(exec(args));
        } catch (IOException ex) {
            System.err.println("Erreur de fichier: " + ex.getMessage());
            System.exit(1);
        }
    }

    public static int exec(String[] args) throws IOException {
        // Defines the options for the command line
        Cli cli = new Cli();
        try {
            cli.run(args);
        }
        catch (ParseException ex){
            System.err.println(ex.getMessage());
            return 1;
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
            return 1;
        }

        // DAO to use depending on format
        GroceriesDAO groceriesDAO;
        switch (cli.format) {
            case "json" -> groceriesDAO = new GroceriesDAOImplJson(cli.fileName, OBJECT_MAPPER);
            case "csv" -> groceriesDAO = new GroceriesDAOImplCsv(cli.fileName, OBJECT_MAPPER);
            default -> {
                System.err.println("Format non supporté: " + cli.format);
                return 1;
            }
        }

        // Command to use depending on the command line
        CommandFactory commandFactory = new CommandFactory(groceriesDAO, cli.category);
        List<String> positionalArgs = cli.command;
        Command command = commandFactory.getCommand(positionalArgs);

        // Validate and execute the command
        command.validateArgs();
        try {
            command.execute();
        } catch (Exception e) {
            System.err.println("Error while executing the command: " + e.getMessage());
            return 1;
        }
        return 0;
    }
}