package com.fges;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

class MainTest {
    private static final String TEST_FILE = "test_grocery.json";

    @BeforeEach
    void setup() throws IOException {
        // Nettoyer le fichier avant chaque test
        Files.write(new File(TEST_FILE).toPath(), "[]".getBytes());
    }

    @Test
    void should_add_item_to_list() throws IOException {
        String[] args = {"-s", TEST_FILE, "add", "Apple", "2"};
        int result = Main.exec(args);
        List<String> content = Files.readAllLines(new File(TEST_FILE).toPath());

        assertThat(result).isEqualTo(0);
        assertThat(content).contains("[{\"name\":\"Apple\",\"quantity\":2}]");
    }

    @Test
    void should_remove_existing_item() throws IOException {
        String[] addArgs = {"-s", TEST_FILE, "add", "Banana", "3"};
        Main.exec(addArgs);

        String[] removeArgs = {"-s", TEST_FILE, "remove", "Banana"};
        int result = Main.exec(removeArgs);
        List<String> content = Files.readAllLines(new File(TEST_FILE).toPath());

        assertThat(result).isEqualTo(0);
        assertThat(content).contains("[]"); // La liste doit être vide après suppression
    }

    @Test
    void should_not_remove_non_existing_item() throws IOException {
        String[] removeArgs = {"-s", TEST_FILE, "remove", "Orange"};
        int result = Main.exec(removeArgs);
        List<String> content = Files.readAllLines(new File(TEST_FILE).toPath());

        assertThat(result).isEqualTo(0);
        assertThat(content).contains("[]"); // Rien ne doit être supprimé car l'élément n'existait pas
    }
}
