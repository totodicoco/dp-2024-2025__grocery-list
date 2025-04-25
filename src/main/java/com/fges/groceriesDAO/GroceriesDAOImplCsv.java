package com.fges.groceriesDAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.modules.GroceryList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceriesDAOImplCsv implements GroceriesDAO{
    private final String fileName;
    private final ObjectMapper OBJECT_MAPPER;

    public GroceriesDAOImplCsv(String fileName, ObjectMapper OBJECT_MAPPER){
        this.fileName = fileName;
        this.OBJECT_MAPPER = OBJECT_MAPPER;
    }

    /**
     * Save the grocery list to a CSV file.
     *
     * @param groceryList The grocery list to save.
     * @throws IOException If an I/O error occurs.
     */
    public void saveGroceryList(GroceryList groceryList) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write("Category,Item,Quantity\n");
            for (var categoryEntry : groceryList.getGroceryList().entrySet()) {
                String category = categoryEntry.getKey();
                Map<String, Integer> items = categoryEntry.getValue();

                for (var itemEntry : items.entrySet()) {
                    writer.write(category + "," + itemEntry.getKey() + "," + itemEntry.getValue() + "\n");
                }
            }
        }
    }

    /**
     * Load the grocery list from a CSV file.
     *
     * @return The loaded grocery list.
     * @throws IOException If an I/O error occurs.
     */
    public GroceryList loadGroceryList() throws IOException {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            return GroceryList.fromMap(new HashMap<>());
        }

        Map<String, Map<String, Integer>> categorizedList = new HashMap<>();
        List<String> lines = Files.readAllLines(filePath);

        if (lines.isEmpty()) {
            return GroceryList.fromMap(categorizedList);
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
                            .put(item, categorizedList.getOrDefault(category, new HashMap<>())
                                    .getOrDefault(item, 0) + quantity);
                }
            }
        }
        return GroceryList.fromMap(categorizedList);
    }
}
