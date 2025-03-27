package com.fges;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class GroceryItem {
    private String name;
    private int quantity;

    public GroceryItem() {}

    public GroceryItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + ": " + quantity;
    }
}

public class Main {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws IOException {
        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();
        cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");

        CommandLine cmd;
        try {
            cmd = parser.parse(cliOptions, args);
        } catch (ParseException ex) {
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        }

        String fileName = cmd.getOptionValue("s");
        List<String> positionalArgs = cmd.getArgList();
        if (positionalArgs.isEmpty()) {
            System.err.println("Missing Command");
            return 1;
        }

        String command = positionalArgs.get(0);
        Path filePath = Paths.get(fileName);
        List<GroceryItem> groceryList;

        if (Files.exists(filePath)) {
            String fileContent = Files.readString(filePath);
            groceryList = OBJECT_MAPPER.readValue(fileContent, new TypeReference<List<GroceryItem>>() {});
        } else {
            groceryList = new ArrayList<>();
        }

        switch (command) {
            case "add" -> {
                if (positionalArgs.size() < 3) {
                    System.err.println("Missing arguments");
                    return 1;
                }
                String itemName = positionalArgs.get(1);
                int quantity;
                try {
                    quantity = Integer.parseInt(positionalArgs.get(2));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid quantity, please enter a valid number.");
                    return 1;
                }
                groceryList.add(new GroceryItem(itemName, quantity));
                OBJECT_MAPPER.writeValue(new File(fileName), groceryList);
                return 0;
            }
            case "list" -> {
                groceryList.forEach(item -> System.out.println(item));
                return 0;
            }
            case "remove" -> {
                if (positionalArgs.size() < 2) {
                    System.err.println("Missing arguments");
                    return 1;
                }
                String itemName = positionalArgs.get(1);
                groceryList = groceryList.stream()
                        .filter(item -> !item.getName().equalsIgnoreCase(itemName))
                        .collect(Collectors.toList());
                OBJECT_MAPPER.writeValue(new File(fileName), groceryList);
                return 0;
            }
            default -> {
                System.err.println("Unknown command: " + command);
                return 1;
            }
        }
    }
}

