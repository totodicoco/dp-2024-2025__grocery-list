package com.fges.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InfoService {

    /**
     * Show system information as requested from TP3
     */
    public void info() {
        // Get today's date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);
        
        // Get system properties
        String osName = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        
        // Display system information
        System.out.println("=== System information ===");
        System.out.println("Today's date: " + formattedDate);
        System.out.println("Operating System: " + osName);
        System.out.println("Java version: " + javaVersion);
    }
}