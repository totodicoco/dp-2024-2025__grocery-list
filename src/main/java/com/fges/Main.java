package com.fges;

import com.fges.groceriesDAO.*;
import com.fges.modules.*;
import com.fges.services.*;


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

        //print format and filename
        System.out.println("Format: " + cli.format);
        System.out.println("Filename: " + cli.fileName);

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

        // les commandes qui s'executent
        switch (command) {
            case "add" -> {
                if (positionalArgs.size() < 3) {
                    System.err.println("Usage: add <item> <quantity>");
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

                // Create an instance of AddService and call the add method
                AddService addService = new AddService(groceriesDAO);
                addService.add(itemName, quantity, category);

                return 0;
            }
            case "list" -> {
                ListService listService = new ListService(groceriesDAO);
                listService.list();
                return 0;
            }
            case "remove" -> {
                if (positionalArgs.size() < 2) {
                    System.err.println("Usage: remove <item>");
                    return 1;
                }
                String itemName = positionalArgs.get(1);
                String category = cli.category;
                RemoveService removeService = new RemoveService(groceriesDAO);
                removeService.remove(itemName, 1, category);
                return 0;
            }
            // clear pour effacer toute la liste de course
            case "clear" -> {
                ClearService clearService = new ClearService(groceriesDAO);
                clearService.clear();
                return 0;
            }
            default -> {
                System.err.println("Commande inconnue: " + command);
                return 1;
            }
        }
    }
}