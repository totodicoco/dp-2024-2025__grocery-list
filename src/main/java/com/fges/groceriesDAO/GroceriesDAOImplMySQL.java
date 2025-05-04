package com.fges.groceriesDAO;

import com.fges.modules.GroceryList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GroceriesDAOImplMySQL implements GroceriesDAO{
    private final Connection connection;

    public GroceriesDAOImplMySQL(Connection connection){
        this.connection = connection;
    }

    /**
     * Save the grocery list to the MySQL Database.
     *
     * @param groceryList The grocery list to save.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void saveGroceryList(GroceryList groceryList) throws IOException {
        // Clear the existing data in the table
        String deleteSQL = "DELETE FROM item";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
            deleteStmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new IOException("Error clearing grocery list in database", e);
        }

        // Insert new data into the table
        String insertItemSQL = "INSERT INTO item (item_name, quantity, category_name) VALUES (?, ?, ?) ";
        try (PreparedStatement itemStmt = connection.prepareStatement(insertItemSQL)) {
            for (Map.Entry<String, Map<String, Integer>> categoryEntry : groceryList.getGroceryList().entrySet()) {
                String categoryName = categoryEntry.getKey();
                for (Map.Entry<String, Integer> itemEntry : categoryEntry.getValue().entrySet()){
                    String itemName = itemEntry.getKey();
                    int quantity = itemEntry.getValue();
                    itemStmt.setString(1, itemName);
                    itemStmt.setInt(2, quantity);
                    itemStmt.setString(3, categoryName);
                    itemStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new IOException("Error saving grocery list to database", e);
        }
    }

    /**
     * Load the grocery list from a CSV file.
     *
     * @return The loaded grocery list.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public GroceryList loadGroceryList() throws IOException {
        GroceryList.Builder groceryListBuilder = new GroceryList.Builder();
        String sql = "SELECT item_name, quantity, category_name FROM item";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("item_name");
                int quantity = resultSet.getInt("quantity");
                String category = resultSet.getString("category_name");
                groceryListBuilder.add(name, quantity, category);
            }
        } catch (SQLException e) {
            throw new IOException("Error loading grocery list from database", e);
        }
        return groceryListBuilder.build();
    }
}
