package com.fges.groceriesDAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    public static Connection getConnection(String url) {
        String databaseName = url.substring(url.lastIndexOf("/") + 1);
        String serverUrl = url.substring(0, url.lastIndexOf("/"));

        System.out.print("Enter MySQL username: ");
        String username = System.console().readLine();
        System.out.print("Enter MySQL password: ");
        String password = new String(System.console().readPassword());

        try (Connection serverConnection = connectToServer(serverUrl, username, password)) {
            if (databaseExists(url, username, password)) {
                return DriverManager.getConnection(url, username, password);
            } else {
                System.out.println("Database '" + databaseName + "' does not exist. Creating it...");
                createDatabase(serverConnection, databaseName);
                String sqlScriptPath = "src/main/java/com/fges/groceriesDAO/groceryListMysqlStructure.sql";
                return initializeDatabase(serverUrl, databaseName, username, password, sqlScriptPath);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    private static Connection connectToServer(String serverUrl, String username, String password) throws SQLException {
        return DriverManager.getConnection(serverUrl, username, password);
    }

    private static boolean databaseExists(String url, String username, String password) {
        try (Connection dbConnection = DriverManager.getConnection(url, username, password)) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static void createDatabase(Connection serverConnection, String databaseName) throws SQLException {
        String createDatabaseQuery = "CREATE DATABASE " + databaseName;
        try (PreparedStatement stmt = serverConnection.prepareStatement(createDatabaseQuery)) {
            stmt.executeUpdate();
            System.out.println("Database '" + databaseName + "' created successfully.");
        }
        catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
            throw new RuntimeException("Failed to create database", e);
        }
    }

    private static Connection initializeDatabase(String serverUrl, String databaseName, String username, String password, String sqlScriptPath) throws SQLException {
        String databaseUrl = serverUrl + "/" + databaseName;
        try (Connection databaseConnection = DriverManager.getConnection(databaseUrl, username, password)) {
            executeSqlScript(databaseConnection, sqlScriptPath);
            return DriverManager.getConnection(databaseUrl, username, password);
        } catch (SQLException | RuntimeException e) {
            dropDatabase(serverUrl, databaseName, username, password);
            throw new RuntimeException("Failed to initialize the database", e);
        }
    }

    private static void executeSqlScript(Connection databaseConnection, String sqlFilePath) {
        try {
            String sqlScript = Files.readString(Paths.get(sqlFilePath));
            System.out.println(sqlScript);
            String[] queries = sqlScript.split(";");
            for (String query : queries) {
                query = query.trim();
                if (!query.isEmpty() && !query.startsWith("--") && !query.startsWith("/*") && !query.toUpperCase().startsWith("SET")) {
                    try (PreparedStatement stmt = databaseConnection.prepareStatement(query)) {
                        stmt.executeUpdate();
                    }
                }
            }
            System.out.println("Database tables created successfully.");
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Error executing SQL script", e);
        }
    }

    private static void dropDatabase(String serverUrl, String databaseName, String username, String password) throws SQLException {
        String dropDatabaseQuery = "DROP DATABASE " + databaseName;
        try (Connection serverConnection = DriverManager.getConnection(serverUrl, username, password);
             PreparedStatement dropStmt = serverConnection.prepareStatement(dropDatabaseQuery)) {
            dropStmt.executeUpdate();
            System.out.println("Database '" + databaseName + "' dropped due to an error.");
        } catch (SQLException e) {
            System.err.println("Error dropping database: " + e.getMessage());
        }
    }
}