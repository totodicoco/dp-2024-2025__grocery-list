package com.fges.groceriesDAO;

import java.sql.Connection;

public class GroceriesDAOFactory{

    public GroceriesDAO createGroceriesDAO(String format, String source) throws IllegalArgumentException {
        GroceriesDAO groceriesDAO;
        switch (format) {
            case "json" -> groceriesDAO = new GroceriesDAOImplJson(source);
            case "csv" -> groceriesDAO = new GroceriesDAOImplCsv(source);
            case "mysql" -> {
                Connection connection = DatabaseConnectionManager.getConnection(source);
                groceriesDAO = new GroceriesDAOImplMySQL(connection);
            }
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        }
        return groceriesDAO;
    }

}