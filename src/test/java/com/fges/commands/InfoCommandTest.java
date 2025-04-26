package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.services.InfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

class InfoCommandTest {

    private InfoCommand infoCommand;

    @BeforeEach
    void setUp() {
        //empty list of strings for infocommand
        infoCommand = new InfoCommand(List.of());
    }

    @Test
    void should_executeSuccessfully() throws IOException {
        InfoService infoService = mock(InfoService.class);

        infoCommand.execute();

        verify(infoService, never()).info();
    }
}