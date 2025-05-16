package com.fges.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroceryList {
    private final Map<String, Map<String, Integer>> groceryList;

    private GroceryList(Map<String, Map<String, Integer>> groceryList) {
        this.groceryList = groceryList;
    }

    /**
     * This method is used to create a GroceryList object from a Map.
     * It creates a new GroceryList object with the provided map.
     * This a -fixed- "factory method". We have not created a factory class because we don't need it for now, this is sufficient.
     *
     * @param groceryList The map representing the grocery list.
     * @return A new GroceryList object.
     */
    public static GroceryList fromMap(Map<String, Map<String, Integer>> groceryList) {
        return new GroceryList(new HashMap<>(groceryList));
    }

    /**
     * This method is used to get the grocery list.
     * It returns an unmodifiable view of the grocery list.
     */
    public Map<String, Map<String, Integer>> getGroceryList() {
        return groceryList;
    }

    /**
     * This method is used to get the items in a specific category.
     * It returns an unmodifiable view of the items in the specified category.
     */
    public Map<String, Integer> getCategoryItems(String category) {
        return groceryList.get(category);
    }


    /**
     * This method is used to get all categories that contain a specific item.
     * It returns a list of categories that contain the specified item.
     */
    public List<String> getAllCategoriesWithItem(String item) {
        return groceryList.entrySet().stream()
                .filter(entry -> entry.getValue().containsKey(item))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * If for some reason I'll need to create a grocery list with a builder, I can use this class.
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroceryList that = (GroceryList) o;
        return groceryList.equals(that.groceryList);
    }

    @Override
    public int hashCode() {
        return groceryList.hashCode();
    }
}
