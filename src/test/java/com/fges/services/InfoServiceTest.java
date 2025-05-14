package com.fges.services;

import com.fges.services.DTO.InfoDTO;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InfoServiceTest {

    @Test
    void testInfoOutput() {
        InfoDTO infoDTO = new InfoDTO(
                LocalDate.of(2024, 5, 14),
                "Linux",
                "Java 17"
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        InfoService service = new InfoService();
        service.info(infoDTO);

        System.setOut(originalOut);

        String result = output.toString();

        assertTrue(result.contains("=== System information ==="));
        assertTrue(result.contains("Today's date: 14/05/2024"));
        assertTrue(result.contains("Operating System: Linux"));
        assertTrue(result.contains("Java version: Java 17"));
    }
}
