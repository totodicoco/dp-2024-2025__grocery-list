package com.fges;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvGroceriesDAOImpl extends GroceriesDAOImpl{

    public CsvGroceriesDAOImpl(String fileName, ObjectMapper OBJECT_MAPPER) throws IOException {
        super(fileName, OBJECT_MAPPER);
    }
    @Override
    protected void saveCategorizedGroceryList(Map<String, Map<String, Integer>> categorizedGroceryList) throws IOException {
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

    @Override
    protected Map<String, Map<String, Integer>> loadCategorizedGroceryList() throws IOException{
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
}
