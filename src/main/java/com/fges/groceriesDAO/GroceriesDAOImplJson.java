package com.fges.groceriesDAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.modules.GroceryList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class GroceriesDAOImplJson implements GroceriesDAO{
    private final String fileName;
    private final ObjectMapper OBJECT_MAPPER;

    public GroceriesDAOImplJson(String fileName, ObjectMapper OBJECT_MAPPER) {
        this.fileName = fileName;
        this.OBJECT_MAPPER = OBJECT_MAPPER;
    }

    @Override
    public void saveGroceryList(GroceryList groceryList) throws IOException{
        OBJECT_MAPPER.writeValue(new File(fileName), groceryList.getGroceryList());
    }

    @Override
    public GroceryList loadGroceryList() throws IOException{
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            String fileContent = Files.readString(filePath);
            try {
                // Load the structure with categories
                Map<String, Map<String, Integer>> groceries = OBJECT_MAPPER.readValue(fileContent, new TypeReference<>() {
                });
                return GroceryList.fromMap(groceries);
            }
            catch (IOException e) {
                throw new IOException("Failed to load grocery list from JSON file", e);
            }
        }
        return GroceryList.fromMap(new HashMap<>());
    }

    @Override
    public String getFilename(){
        return fileName;
    }
}
