package com.fges;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    // les fichiers
    private static final String FICHIER_JSON = "test_grocery.json";
    private static final String FICHIER_CSV = "test_grocery.csv";
    private final ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();

    @BeforeEach
    void setup() throws IOException {
        // sortie du system
        System.setOut(new PrintStream(sortieCapturee));
        // clean les fichiers json et csv a chaque test
        Map<String, Integer> mapVide = Map.of(); // Créer une carte vide
        Main.OBJECT_MAPPER.writeValue(new File(FICHIER_JSON), mapVide); // Écrire la carte vide dans le fichier JSON
        Files.writeString(Paths.get(FICHIER_CSV), "Item,Quantity\n"); // Réinitialiser le fichier CSV
    }

    // test json

    // test pour ajouter un element au json
    @Test
    void ajouterArticleJson() throws IOException {
        String[] args = { "-s", FICHIER_JSON, "add", "Pomme", "2" };
        int resultat = Main.exec(args); // Appeler la méthode d'exécution
        assertEquals(0, resultat); // Vérifier que l'exécution a réussi
        // test ajout element
        Map<String, Integer> articles = Main.OBJECT_MAPPER.readValue(
                new File(FICHIER_JSON),
                Main.OBJECT_MAPPER.getTypeFactory().constructMapType(
                        Map.class, String.class, Integer.class));
        // test un seul article
        assertEquals(1, articles.size());
        // test quantité
        assertEquals(2, articles.get("Pomme"));
    }

    // test maj article existant dans le json
    @Test
    void mettreAJourArticleJson() throws IOException {
        // creation et maj de banane
        Main.exec(new String[] { "-s", FICHIER_JSON, "add", "Banane", "3" });
        String[] argsMaj = { "-s", FICHIER_JSON, "add", "Banane", "2" };
        int resultat = Main.exec(argsMaj);
        assertEquals(0, resultat); // Vérifier l'exécution
        Map<String, Integer> articles = Main.OBJECT_MAPPER.readValue(
                new File(FICHIER_JSON),
                Main.OBJECT_MAPPER.getTypeFactory().constructMapType(
                        Map.class, String.class, Integer.class));
        assertEquals(1, articles.size()); // Vérifier qu'il n'y a qu'un seul article
        assertEquals(5, articles.get("Banane")); // Vérifier la quantité mise à jour
    }

    // test suppression dans le json
    @Test
    void supprimerArticleJson() throws IOException {
        // test sur orange
        Main.exec(new String[] { "-s", FICHIER_JSON, "add", "Orange", "4" });
        String[] argsSupprimer = { "-s", FICHIER_JSON, "remove", "Orange" };
        int resultat = Main.exec(argsSupprimer);
        assertEquals(0, resultat);
        // test fichier vide
        Map<String, Integer> articles = Main.OBJECT_MAPPER.readValue(
                new File(FICHIER_JSON),
                Main.OBJECT_MAPPER.getTypeFactory().constructMapType(
                        Map.class, String.class, Integer.class));
        assertTrue(articles.isEmpty());
    }

    // test vider la liste du json
    @Test
    void viderListeJson() throws IOException {
        // mettre 2 elements
        Main.exec(new String[] { "-s", FICHIER_JSON, "add", "Lait", "1" });
        Main.exec(new String[] { "-s", FICHIER_JSON, "add", "Pain", "2" });
        // vider la liste
        String[] argsVider = { "-s", FICHIER_JSON, "clear" };
        int resultat = Main.exec(argsVider);
        assertEquals(0, resultat);
        // test liste vide dans le json
        Map<String, Integer> articles = Main.OBJECT_MAPPER.readValue(
                new File(FICHIER_JSON),
                Main.OBJECT_MAPPER.getTypeFactory().constructMapType(
                        Map.class, String.class, Integer.class));
        assertTrue(articles.isEmpty());
    }

    // test affichage liste dans le json
    @Test
    void afficherListeJson() throws IOException {
        // mettre 2 elements
        Main.exec(new String[] { "-s", FICHIER_JSON, "add", "Pommes", "5" });
        Main.exec(new String[] { "-s", FICHIER_JSON, "add", "Oranges", "3" });
        sortieCapturee.reset();
        // montrer la liste
        String[] argsListe = { "-s", FICHIER_JSON, "list" };
        int resultat = Main.exec(argsListe);
        assertEquals(0, resultat);
        // test 2 element en sortie
        String sortie = sortieCapturee.toString();
        assertTrue(sortie.contains("Pommes,5"));
        assertTrue(sortie.contains("Oranges,3"));
    }

    // test csv

    // test mettre un element dans le csv
    @Test
    void ajouterArticleCsv() throws IOException {
        String[] args = { "-s", FICHIER_CSV, "-f", "csv", "add", "Fromage", "2" };
        int resultat = Main.exec(args);
        assertEquals(0, resultat);
        // test element ajouté a la liste
        String contenuFichier = Files.readString(Paths.get(FICHIER_CSV));
        assertTrue(contenuFichier.contains("Item,Quantity"));
        assertTrue(contenuFichier.contains("Fromage,2"));
    }

    // test maj element dans le csv
    @Test
    void mettreAJourArticleCsv() throws IOException {
        // test ajouter un element et maj dessus
        Main.exec(new String[] { "-s", FICHIER_CSV, "-f", "csv", "add", "Yaourt", "3" });
        String[] argsMaj = { "-s", FICHIER_CSV, "-f", "csv", "add", "Yaourt", "2" };
        int resultat = Main.exec(argsMaj);
        assertEquals(0, resultat);
        // maj nombre de yaourt
        String contenuFichier = Files.readString(Paths.get(FICHIER_CSV));
        assertTrue(contenuFichier.contains("Yaourt,5"));
    }
}
