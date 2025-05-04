package com.fges.services;

import com.fges.services.DTO.InfoDTO;

import java.time.format.DateTimeFormatter;

public class InfoService {

    /**
     * Show system information as requested from TP3
     */

    public void info(InfoDTO infoDTO) {
//        // Get today's date
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String formattedDate = today.format(formatter);
//
//        // Get system properties
//        String osName = System.getProperty("os.name");
//        String javaVersion = System.getProperty("java.version");
        
        // Display system information
//        System.out.println("=== System information ===");
//        System.out.println("Today's date: " + formattedDate);
//        System.out.println("Operating System: " + osName);
//        System.out.println("Java version: " + javaVersion);

        // Display system information
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = infoDTO.date().format(formatter);
        System.out.println("=== System information ===");
        System.out.println("Today's date: " + formattedDate);
        System.out.println("Operating System: " + infoDTO.osName());
        System.out.println("Java version: " + infoDTO.javaVersion());
    }
}