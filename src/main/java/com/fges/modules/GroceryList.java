package com.fges.modules;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GroceryList {
    private final Map<String, Map<String, Integer>> groceryList;

    private GroceryList(Map<String, Map<String, Integer>> groceryList) {
        this.groceryList = groceryList;
    }

//    This a "factory method". We have not created a factory class because we don't need it for now, this is sufficient.
    public static GroceryList fromMap(Map<String, Map<String, Integer>> groceryList) {
        return new GroceryList(new HashMap<>(groceryList));
    }

    public Map<String, Map<String, Integer>> getGroceryList() {
        return groceryList;
    }

    public Map<String, Integer> getCategoryItems(String category) {
        return groceryList.get(category);
    }

    public Integer getItemQuantity(String category, String item) {
        return groceryList.get(category).get(item);
    }

    public static class Builder{
        private final Map<String, Map<String, Integer>> groceryList = new HashMap<>();

        public void add(String itemName, int quantity, String category) {
            groceryList.computeIfAbsent(category, k -> new HashMap<>())
                    .put(itemName, groceryList.getOrDefault(category, new HashMap<>())
                            .getOrDefault(itemName, 0) + quantity);
        }

        public GroceryList build() {
            return new GroceryList(new HashMap<>(groceryList));
        }
    }
}
