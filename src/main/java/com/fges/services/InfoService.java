package com.fges.services;

import com.fges.services.DTO.InfoDTO;

import java.time.format.DateTimeFormatter;

public class InfoService {

    /**
     * Show system information as requested from TP3
     */

    public Boolean info(InfoDTO infoDTO) {
        Boolean success = false;

        // Display system information
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = infoDTO.date().format(formatter);
        System.out.println("=== System information ===");
        System.out.println("Today's date: " + formattedDate);
        System.out.println("Operating System: " + infoDTO.osName());
        System.out.println("Java version: " + infoDTO.javaVersion());

        success = true;
        return success;
    }
}