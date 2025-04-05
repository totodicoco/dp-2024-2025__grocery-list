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
        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();
        cliOptions.addOption("c", "category", true, "Catégorie pour les articles");
        cliOptions.addRequiredOption("s", "source", true, "Fichier contenant la liste de courses");
        cliOptions.addOption("f", "format", true, "Format de stockage: json ou csv (défaut: json)");

        CommandLine cmd;
        try {
            cmd = parser.parse(cliOptions, args);
        } catch (ParseException ex) {
            System.err.println("Erreur d'analyse des arguments: " + ex.getMessage());
            return 1;
        }

        // prend les arguments
        String category = cmd.getOptionValue("category", "default");
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
            System.err.println("Commande manquante (utilisez add, list, remove, clear)");
            return 1;
        }

        // la commande à exécuter
        String command = positionalArgs.get(0);

        // liste des courses selon le format, maintenant avec des catégories
        Map<String, Map<String, Integer>> categorizedGroceryList = format.equals("csv")
                ? loadCategorizedGroceryListFromCSV(fileName)
                : loadCategorizedGroceryListFromJSON(fileName);

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
                // ajouter l'item a la bonne categorie
                categorizedGroceryList.computeIfAbsent(category, k -> new HashMap<>())
                        .put(itemName, categorizedGroceryList.getOrDefault(category, new HashMap<>())
                                .getOrDefault(itemName, 0) + quantity);
                saveCategorizedGroceryList(fileName, categorizedGroceryList, format);
                return 0;
            }
            case "list" -> {
                if (categorizedGroceryList.isEmpty()) {
                    System.out.println("Votre liste de courses est vide.");
                } else {
                    categorizedGroceryList.forEach((cat, items) -> {
                        System.out.println("#" + cat + ":");
                        items.forEach((item, qty) -> System.out.println(item + "," + qty));
                    });
                }
                return 0;
            }
            case "remove" -> {
                if (positionalArgs.size() < 2) {
                    System.err.println("Usage: remove <item>");
                    return 1;
                }
                String itemName = positionalArgs.get(1);
                // suppr l'item dans sa categorie specifiée
                Map<String, Integer> categoryItems = categorizedGroceryList.get(category);
                if (categoryItems != null) {
                    categoryItems.remove(itemName);
                    // Si la catégorie est vide, la supprimer
                    if (categoryItems.isEmpty()) {
                        categorizedGroceryList.remove(category);
                    }
                }
                saveCategorizedGroceryList(fileName, categorizedGroceryList, format);
                return 0;
            }
            // clear pour effacer toute la liste de course
            case "clear" -> {
                categorizedGroceryList.clear();
                saveCategorizedGroceryList(fileName, categorizedGroceryList, format);
                return 0;
            }
            default -> {
                System.err.println("Commande inconnue: " + command);
                return 1;
            }
        }
    }

    // charge la liste depuis un json avec catégories
    private static Map<String, Map<String, Integer>> loadCategorizedGroceryListFromJSON(String fileName)
            throws IOException {
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            String fileContent = Files.readString(filePath);
            try {
                // recharger la structure aavec categorie
                return OBJECT_MAPPER.readValue(fileContent, new TypeReference<Map<String, Map<String, Integer>>>() {
                });
            } catch (Exception e) {
                // si ça marche pas on convertie
                try {
                    Map<String, Integer> oldFormat = OBJECT_MAPPER.readValue(fileContent,
                            new TypeReference<Map<String, Integer>>() {
                            });
                    Map<String, Map<String, Integer>> newFormat = new HashMap<>();
                    newFormat.put("default", oldFormat);
                    return newFormat;
                } catch (Exception ex) {
                    // si les deux marche pas retourne une liste vide
                    return new HashMap<>();
                }
            }
        }
        return new HashMap<>();
    }

    // charge la liste depuis un csv avec categorie
    private static Map<String, Map<String, Integer>> loadCategorizedGroceryListFromCSV(String fileName)
            throws IOException {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            return new HashMap<>();
        }

        Map<String, Map<String, Integer>> categorizedList = new HashMap<>();
        List<String> lines = Files.readAllLines(filePath);

        if (lines.isEmpty()) {
            return categorizedList;
        }

        // bon format ?
        String header = lines.get(0);
        if (header.startsWith("Category,")) {
            // new format avec categorie
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length >= 3) {
                    String category = parts[0];
                    String item = parts[1];
                    int quantity = Integer.parseInt(parts[2]);

                    categorizedList.computeIfAbsent(category, k -> new HashMap<>())
                            .put(item, quantity);
                }
            }
        } else {
            // format sans categorie
            Map<String, Integer> defaultCategory = new HashMap<>();
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length >= 2) {
                    String item = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    defaultCategory.put(item, quantity);
                }
            }
            if (!defaultCategory.isEmpty()) {
                categorizedList.put("default", defaultCategory);
            }
        }

        return categorizedList;
    }

    // sauvegarde soit en json, soit en csv avec categorie
    private static void saveCategorizedGroceryList(String fileName,
            Map<String, Map<String, Integer>> categorizedGroceryList, String format)
            throws IOException {
        if (format.equals("csv")) {
            saveCategorizedGroceryListToCSV(fileName, categorizedGroceryList);
        } else {
            saveCategorizedGroceryListToJSON(fileName, categorizedGroceryList);
        }
    }

    // sauvegarde json avec categorie
    private static void saveCategorizedGroceryListToJSON(String fileName,
            Map<String, Map<String, Integer>> categorizedGroceryList) throws IOException {
        OBJECT_MAPPER.writeValue(new File(fileName), categorizedGroceryList);
    }

    // sauvegarde csv avec categorie
    private static void saveCategorizedGroceryListToCSV(String fileName,
            Map<String, Map<String, Integer>> categorizedGroceryList) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write("Category,Item,Quantity\n");
            for (var categoryEntry : categorizedGroceryList.entrySet()) {
                String category = categoryEntry.getKey();
                Map<String, Integer> items = categoryEntry.getValue();

                for (var itemEntry : items.entrySet()) {
                    writer.write(category + "," + itemEntry.getKey() + "," + itemEntry.getValue() + "\n");
                }
            }
        }
    }
}