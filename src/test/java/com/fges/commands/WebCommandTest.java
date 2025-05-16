package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.groceriesDAO.GroceriesDAOFactory;
import com.fges.modules.OptionsUsed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebCommandTest {

    private OptionsUsed optionsUsed;

    @BeforeEach
    void setup() {
        optionsUsed = mock(OptionsUsed.class);
        when(optionsUsed.getFormat()).thenReturn("json");
        when(optionsUsed.getFilename()).thenReturn("webdata.json");
    }

    @Test
    void testValidateArgs_validArguments_shouldNotThrow() {
        WebCommand command = new WebCommand(List.of("web", "8080"), optionsUsed);
        assertDoesNotThrow(command::validateArgs);
    }

    @Test
    void testValidateArgs_invalidPort_shouldThrow() {
        WebCommand command = new WebCommand(List.of("web", "notANumber"), optionsUsed);
        Exception exception = assertThrows(IllegalArgumentException.class, command::validateArgs);
        assertEquals("The port number must be an integer.", exception.getMessage());
    }

    @Test
    void testValidateArgs_missingFilename_shouldThrow() {
        OptionsUsed optionsWithoutFile = mock(OptionsUsed.class);
        when(optionsWithoutFile.getFilename()).thenReturn(null);

        WebCommand command = new WebCommand(List.of("web", "8080"), optionsWithoutFile);
        Exception exception = assertThrows(IllegalArgumentException.class, command::validateArgs);
        assertTrue(exception.getMessage().contains("No filename provided"));
    }

    @Test
    void testExecute_callsWebService() throws IOException {
        GroceriesDAO mockDAO = mock(GroceriesDAO.class);

        try (MockedConstruction<GroceriesDAOFactory> mockedFactory = mockConstruction(
                GroceriesDAOFactory.class,
                (mock, context) -> when(mock.createGroceriesDAO("json", "webdata.json")).thenReturn(mockDAO)
        )) {
            WebCommand command = new WebCommand(List.of("web", "8080"), optionsUsed);
            command.execute();

            assertEquals(1, mockedFactory.constructed().size());
            verify(mockedFactory.constructed().get(0)).createGroceriesDAO("json", "webdata.json");
        }
    }
}
