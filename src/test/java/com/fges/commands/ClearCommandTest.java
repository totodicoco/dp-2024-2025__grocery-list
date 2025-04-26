package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.services.ClearService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

class ClearCommandTest {

    private GroceriesDAO groceriesDAO;
    private ClearCommand clearCommand;

    @BeforeEach
    void setUp() {
        groceriesDAO = mock(GroceriesDAO.class);
        clearCommand = new ClearCommand(List.of(), groceriesDAO);
    }

    @Test
    void should_executeSuccessfully() throws IOException {
        ClearService clearService = mock(ClearService.class);

        clearCommand.execute();

        verify(clearService, never()).clear();
    }
}