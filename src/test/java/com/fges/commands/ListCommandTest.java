package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.services.ListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

class ListCommandTest {

    private GroceriesDAO groceriesDAO;
    private ListCommand listCommand;

    @BeforeEach
    void setUp() {
        groceriesDAO = mock(GroceriesDAO.class);
        listCommand = new ListCommand(List.of(), groceriesDAO);
    }

    @Test
    void should_executeSuccessfully() throws IOException {
        ListService listService = mock(ListService.class);

        listCommand.execute();

        verify(listService, never()).list();
    }
}