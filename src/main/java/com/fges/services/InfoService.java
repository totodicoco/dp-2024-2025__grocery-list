package services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InfoService {

    /**
     * Affiche les informations système comme spécifié dans les exigences du TP3
     * La méthode ignore tous les arguments qui pourraient être fournis
     */
    public void displayInfo() {
        // Récupération de la date 
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);
        
        // Récupération des informations 
        String osName = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        
        // Affichage des informations 
        System.out.println("=== Informations système ===");
        System.out.println("Date: " + formattedDate);
        System.out.println("Système d'exploitation: " + osName);
        System.out.println("Version Java: " + javaVersion);
    }
}