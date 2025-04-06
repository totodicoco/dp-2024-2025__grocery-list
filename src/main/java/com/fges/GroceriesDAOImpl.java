package com.fges;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class GroceriesDAOImpl implements GroceriesDAO{
    protected final String fileName;
    protected final ObjectMapper OBJECT_MAPPER;

    protected GroceriesDAOImpl(String fileName, ObjectMapper OBJECT_MAPPER) {
        this.fileName = fileName;
        this.OBJECT_MAPPER = OBJECT_MAPPER;
    }

    public void add(String itemName, int quantity, String category) throws IOException{
        Map<String, Map<String, Integer>> categorizedGroceryList = loadCategorizedGroceryList();
        categorizedGroceryList.computeIfAbsent(category, k -> new HashMap<>())
                .put(itemName, categorizedGroceryList.getOrDefault(category, new HashMap<>())
                        .getOrDefault(itemName, 0) + quantity);
        saveCategorizedGroceryList(categorizedGroceryList);
    }

    public void list() throws IOException{
        Map<String, Map<String, Integer>> categorizedGroceryList = loadCategorizedGroceryList();
        if (categorizedGroceryList.isEmpty()) {
            System.out.println("Votre liste de courses est vide.");
        } else {
            categorizedGroceryList.forEach((cat, items) -> {
                System.out.println("#" + cat + ":");
                items.forEach((item, qty) -> System.out.println(item + "," + qty));
            });
        }
        saveCategorizedGroceryList(categorizedGroceryList);
    }

    public void remove(String itemName, String category) throws IOException{
        Map<String, Map<String, Integer>> categorizedGroceryList = loadCategorizedGroceryList();
        Map<String, Integer> categoryItems = categorizedGroceryList.get(category);
        if (categoryItems != null) {
            categoryItems.remove(itemName);
            // Si la catégorie est vide, la supprimer
            if (categoryItems.isEmpty()) {
                categorizedGroceryList.remove(category);
            }
        }
        saveCategorizedGroceryList(categorizedGroceryList);
    }

    public void clear() throws IOException{
        Map<String, Map<String, Integer>> categorizedGroceryList = loadCategorizedGroceryList();
        categorizedGroceryList.clear();
        saveCategorizedGroceryList(categorizedGroceryList);
        saveCategorizedGroceryList(categorizedGroceryList);
    }

    protected abstract void saveCategorizedGroceryList(Map<String, Map<String, Integer>> categorizedGroceryList) throws IOException;
    protected abstract Map<String, Map<String, Integer>> loadCategorizedGroceryList() throws IOException;
}
