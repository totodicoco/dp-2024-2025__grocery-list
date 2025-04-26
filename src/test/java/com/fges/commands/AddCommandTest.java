package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.services.AddService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddCommandTest {

    private GroceriesDAO groceriesDAO;
    private AddCommand addCommand;

    @BeforeEach
    void setUp() {
        groceriesDAO = mock(GroceriesDAO.class);
    }

    @Test
    void should_validateArgs_whenValid() {
        when(groceriesDAO.getFilename()).thenReturn("test.csv");
        addCommand = new AddCommand(List.of("add", "Apple", "3"), groceriesDAO, "Fruits");

        assertDoesNotThrow(() -> addCommand.validateArgs());
    }

    @Test
    void should_throwException_whenInvalidArgs() {
        addCommand = new AddCommand(List.of("add", "Apple"), groceriesDAO, "Fruits");

        Exception exception = assertThrows(IllegalArgumentException.class, addCommand::validateArgs);
        assertEquals("Usage: add <item> <quantity>", exception.getMessage());
    }

    @Test
    void should_executeSuccessfully() throws IOException {
        when(groceriesDAO.getFilename()).thenReturn("test.csv");
        AddService addService = mock(AddService.class);
        addCommand = new AddCommand(List.of("add", "Apple", "3"), groceriesDAO, "Fruits");

        addCommand.execute();

        verify(addService, never()).add(anyString(), anyInt(), anyString());
    }
}