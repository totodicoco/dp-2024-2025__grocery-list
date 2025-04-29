//package com.fges;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class MainTest {
//    // les fichiers
//    private static final String FICHIER_JSON = "test_grocery.json";
//    private static final String FICHIER_CSV = "test_grocery.csv";
//    private final ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();
//
//    @BeforeEach
//    void setup() throws IOException {
//        // sortie du system
//        System.setOut(new PrintStream(sortieCapturee));
//        // clean les fichiers json et csv a chaque test
//        Map<String, Integer> mapVide = Map.of(); // Créer une carte vide
//        Main.OBJECT_MAPPER.writeValue(new File(FICHIER_JSON), mapVide); // Écrire la carte vide dans le fichier JSON
//        Files.writeString(Paths.get(FICHIER_CSV), "Item,Quantity\n"); // Réinitialiser le fichier CSV
//    }
//}
