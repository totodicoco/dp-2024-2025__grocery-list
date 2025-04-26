package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.services.RemoveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveCommandTest {

    private GroceriesDAO groceriesDAO;
    private RemoveCommand removeCommand;

    @BeforeEach
    void setUp() {
        groceriesDAO = mock(GroceriesDAO.class);
    }

    @Test
    void should_validateArgs_whenValid() {
        when(groceriesDAO.getFilename()).thenReturn("test.csv");
        removeCommand = new RemoveCommand(List.of("remove", "Apple"), groceriesDAO, "Fruits");

        assertDoesNotThrow(() -> removeCommand.validateArgs());
    }

    @Test
    void should_throwException_whenInvalidArgs() {
        removeCommand = new RemoveCommand(List.of("remove"), groceriesDAO, "Fruits");

        Exception exception = assertThrows(IllegalArgumentException.class, removeCommand::validateArgs);
        assertEquals("Usage: remove <item>", exception.getMessage());
    }

    @Test
    void should_executeSuccessfully() throws IOException {
        when(groceriesDAO.getFilename()).thenReturn("test.csv");
        RemoveService removeService = mock(RemoveService.class);
        removeCommand = new RemoveCommand(List.of("remove", "Apple"), groceriesDAO, "Fruits");

        removeCommand.execute();

        verify(removeService, never()).remove(anyString(), anyString());
    }
}