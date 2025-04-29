package com.fges.groceriesDAO;

public class GroceriesDAOFactory{

    public GroceriesDAO createGroceriesDAO(String format, String filename) throws IllegalArgumentException {
        GroceriesDAO groceriesDAO;
        switch (format) {
            case "json" -> groceriesDAO = new GroceriesDAOImplJson(filename);
            case "csv" -> groceriesDAO = new GroceriesDAOImplCsv(filename);
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        }
        return groceriesDAO;
    }
}