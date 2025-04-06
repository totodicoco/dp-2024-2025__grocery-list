package com.fges;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
        // def options de la ligne de commande
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

        // la commande à exécuter
        List<String> positionalArgs = cli.command;
        String command = positionalArgs.get(0);

        // Create the DAO corresponding to the file format
        GroceriesDAOImpl groceriesDao;
        switch (cli.format){
            case "json" -> {
                groceriesDao = new JsonGroceriesDAOImpl(cli.fileName, OBJECT_MAPPER);
            }
            case "csv" -> {
                groceriesDao = new CsvGroceriesDAOImpl(cli.fileName, OBJECT_MAPPER);
            }
            default -> {
                groceriesDao = new JsonGroceriesDAOImpl(cli.fileName, OBJECT_MAPPER);
            }
        }

        // les commandes qui s'executent
        switch (command) {
            case "add" -> {
                if (positionalArgs.size() < 3) {
                    System.err.println("Usage: add <item> <quantity> [<category>]");
                    return 1;
                }
                String itemName = positionalArgs.get(1);
                int quantity;
                try {
                    quantity = Integer.parseInt(positionalArgs.get(2));
                } catch (NumberFormatException e) {
                    System.err.println("La quantité doit être un entier");
                    return 1;
                }
                String category = cli.category;
                groceriesDao.add(itemName, quantity, category);
                return 0;
            }
            case "list" -> {
                groceriesDao.list();
                return 0;
            }
            case "remove" -> {
                if (positionalArgs.size() < 2) {
                    System.err.println("Usage: remove <item> [<category>]");
                    return 1;
                }
                String itemName = positionalArgs.get(1);
                String category = cli.category;
                groceriesDao.remove(itemName, category);
                return 0;
            }
            // clear pour effacer toute la liste de course
            case "clear" -> {
                groceriesDao.clear();
                return 0;
            }
            default -> {
                System.err.println("Commande inconnue: " + command);
                return 1;
            }
        }
    }
}