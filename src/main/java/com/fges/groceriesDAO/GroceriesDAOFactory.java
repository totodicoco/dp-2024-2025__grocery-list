package com.fges.groceriesDAO;

import java.sql.Connection;

public class GroceriesDAOFactory{

    public GroceriesDAO createGroceriesDAO(String format, String filename) throws IllegalArgumentException {
        GroceriesDAO groceriesDAO;
        switch (format) {
            case "json" -> groceriesDAO = new GroceriesDAOImplJson(filename);
            case "csv" -> groceriesDAO = new GroceriesDAOImplCsv(filename);
            case "mysql" -> {
                Connection connection = DatabaseConnectionManager.getConnection(filename);
                groceriesDAO = new GroceriesDAOImplMySQL(connection);
            }
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        }
        return groceriesDAO;
    }

}