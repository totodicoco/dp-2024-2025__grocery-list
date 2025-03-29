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
            System.err.println("File error: " + ex.getMessage());
            System.exit(1);
        }
    }

    public static int exec(String[] args) throws IOException {
        // def options de la ligne de commande
        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();
        cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("f", "format", true, "Storage format: json or csv (default: json)");

        CommandLine cmd;
        try {
            cmd = parser.parse(cliOptions, args);
        } catch (ParseException ex) {
            System.err.println("Error parsing arguments: " + ex.getMessage());
            return 1;
        }

        // prend les arguments
        String fileName = cmd.getOptionValue("s");
        String format = cmd.getOptionValue("f", "json");

        // extension csv ou json si nécessaire
        if ("csv".equalsIgnoreCase(format) && !fileName.endsWith(".csv")) {
            fileName += ".csv";
        } else if ("json".equalsIgnoreCase(format) && !fileName.endsWith(".json")) {
            fileName += ".json";
        }

        // position des arguments
        List<String> positionalArgs = cmd.getArgList();
        if (positionalArgs.isEmpty()) {
            System.err.println("Missing command (use add, list, remove, clear)");
            return 1;
        }

        // la commande à exécuter
        String command = positionalArgs.get(0);

        // liste des courses selon le format
        Map<String, Integer> groceryList = format.equals("csv") ? loadGroceryListFromCSV(fileName)
                : loadGroceryListFromJSON(fileName);

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
                    System.err.println("Quantity must be an integer");
                    return 1;
                }
                groceryList.put(itemName, groceryList.getOrDefault(itemName, 0) + quantity);
                saveGroceryList(fileName, groceryList, format);
                return 0;
            }
            case "list" -> {
                if (groceryList.isEmpty()) {
                    System.out.println("Your grocery list is empty.");
                } else {
                    groceryList.forEach((item, qty) -> System.out.println(item + "," + qty));
                }
                return 0;
            }
            case "remove" -> {
                if (positionalArgs.size() < 2) {
                    System.err.println("Usage: remove <item>");
                    return 1;
                }
                String itemName = positionalArgs.get(1);
                groceryList.remove(itemName);
                saveGroceryList(fileName, groceryList, format);
                return 0;
            }
            // clear pour effacer toute la liste de course
            case "clear" -> {
                groceryList.clear();
                saveGroceryList(fileName, groceryList, format);
                return 0;
            }
            default -> {
                System.err.println("Unknown command: " + command);
                return 1;
            }
        }
    }

    // charge la liste depuis un json

    private static Map<String, Integer> loadGroceryListFromJSON(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            String fileContent = Files.readString(filePath);
            return OBJECT_MAPPER.readValue(fileContent, new TypeReference<>() {
            });
        }
        return new HashMap<>();
    }

    // charge la liste depuis un csv
    private static Map<String, Integer> loadGroceryListFromCSV(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            return new HashMap<>();
        }
        List<String> lines = Files.readAllLines(filePath);
        return lines.stream().skip(1).map(line -> line.split(","))
                .collect(Collectors.toMap(parts -> parts[0], parts -> Integer.parseInt(parts[1])));
    }

    // sauvegarde soit en json, soit en csv
    private static void saveGroceryList(String fileName, Map<String, Integer> groceryList, String format)
            throws IOException {
        if (format.equals("csv")) {
            saveGroceryListToCSV(fileName, groceryList);
        } else {
            saveGroceryListToJSON(fileName, groceryList);
        }
    }

    // sauvegarde json
    private static void saveGroceryListToJSON(String fileName, Map<String, Integer> groceryList) throws IOException {
        OBJECT_MAPPER.writeValue(new File(fileName), groceryList);
    }

    // sauvegarde csv
    private static void saveGroceryListToCSV(String fileName, Map<String, Integer> groceryList) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write("Item,Quantity\n");
            for (var entry : groceryList.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        }
    }
}