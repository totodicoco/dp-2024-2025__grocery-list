package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.groceriesDAO.GroceriesDAOFactory;
import com.fges.modules.OptionsUsed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListCommandTest {

    private OptionsUsed optionsUsed;

    @BeforeEach
    void setUp() {
        optionsUsed = mock(OptionsUsed.class);
        when(optionsUsed.getFilename()).thenReturn("list.json");
        when(optionsUsed.getFormat()).thenReturn("json");
    }

    @Test
    void testValidateArgs_valid() {
        ListCommand cmd = new ListCommand(List.of("list"), optionsUsed);
        assertDoesNotThrow(cmd::validateArgs);
    }

    @Test
    void testValidateArgs_invalidArgCount() {
        ListCommand cmd = new ListCommand(List.of("list", "extraArg"), optionsUsed);
        assertThrows(IllegalArgumentException.class, cmd::validateArgs);
    }

    @Test
    void testValidateArgs_missingFilename() {
        when(optionsUsed.getFilename()).thenReturn(null);
        ListCommand cmd = new ListCommand(List.of("list"), optionsUsed);
        assertThrows(IllegalArgumentException.class, cmd::validateArgs);
    }

    @Test
    void testExecute_success() throws IOException {
        GroceriesDAO mockDAO = mock(GroceriesDAO.class);

        try (MockedConstruction<GroceriesDAOFactory> mockedFactory = Mockito.mockConstruction(
                GroceriesDAOFactory.class,
                (mock, context) -> when(mock.createGroceriesDAO("json", "list.json")).thenReturn(mockDAO)
        )) {
            ListCommand cmd = new ListCommand(List.of("list"), optionsUsed);
            cmd.execute();

            assertEquals(1, mockedFactory.constructed().size());
        }
    }
}
