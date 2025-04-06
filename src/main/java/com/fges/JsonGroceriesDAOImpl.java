package com.fges;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JsonGroceriesDAOImpl extends GroceriesDAOImpl{

    public JsonGroceriesDAOImpl(String fileName, ObjectMapper OBJECT_MAPPER) throws IOException {
        super(fileName, OBJECT_MAPPER);
    }
    @Override
    protected void saveCategorizedGroceryList(Map<String, Map<String, Integer>> categorizedGroceryList) throws IOException {
        OBJECT_MAPPER.writeValue(new File(fileName), categorizedGroceryList);
    }

    @Override
    protected Map<String, Map<String, Integer>> loadCategorizedGroceryList() throws IOException{
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
}
