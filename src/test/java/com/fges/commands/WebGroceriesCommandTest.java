package com.fges.commands;

import com.fges.modules.OptionsUsed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebGroceriesCommandTest {

    private OptionsUsed optionsUsed;

    @BeforeEach
    void setUp() {
        optionsUsed = mock(OptionsUsed.class);
        when(optionsUsed.getSource()).thenReturn("webdata.json");
        when(optionsUsed.getFormat()).thenReturn("json");
    }

    @Test
    void should_validate_arguments_on_correct_use() {
        WebGroceriesCommand cmd = new WebGroceriesCommand(List.of("web", "8080"), optionsUsed);
        assertDoesNotThrow(cmd::validateArgs);
    }

    @Test
    void should_not_validate_if_invalid_port() {
        WebGroceriesCommand cmd = new WebGroceriesCommand(List.of("web", "notANumber"), optionsUsed);
        Exception exception = assertThrows(IllegalArgumentException.class, cmd::validateArgs);
        assertEquals("The port number must be an integer.", exception.getMessage());
    }

    @Test
    void should_not_validate_arguments_if_no_source() {
        when(optionsUsed.getSource()).thenReturn(null);
        WebGroceriesCommand cmd = new WebGroceriesCommand(List.of("web", "8080"), optionsUsed);
        Exception exception = assertThrows(IllegalArgumentException.class, cmd::validateArgs);
        assertTrue(exception.getMessage().contains("No filename provided"));
    }

//    @Test
//    void should_create_correct_DAO_on_execute() throws IOException {
//        GroceriesDAO mockDAO = mock(GroceriesDAO.class);
//
//        try (MockedConstruction<GroceriesDAOFactory> mockedFactory = Mockito.mockConstruction(
//                GroceriesDAOFactory.class,
//                (mock, context) -> when(mock.createGroceriesDAO("json", "webdata.json")).thenReturn(mockDAO)
//        )) {
//            WebGroceriesCommand cmd = new WebGroceriesCommand(List.of("web", "8080"), optionsUsed);
//            cmd.execute();
//
//            // Verify DAO creation
//            assertEquals(1, mockedFactory.constructed().size());
//            verify(mockedFactory.constructed().get(0), times(1)).createGroceriesDAO("json", "webdata.json");
//        }
//    }
//
//    @Test
//    void should_call_web_service_method_on_execute() throws IOException {
//        GroceriesDAO mockDAO = mock(GroceriesDAO.class);
//
//        try (MockedConstruction<GroceriesDAOFactory> mockedFactory = Mockito.mockConstruction(
//                GroceriesDAOFactory.class,
//                (mock, context) -> when(mock.createGroceriesDAO("json", "webdata.json")).thenReturn(mockDAO)
//        )) {
//            try (MockedConstruction<WebGroceriesService> mockedService = Mockito.mockConstruction(
//                    WebGroceriesService.class,
//                    (mock, context) -> doNothing().when(mock).web(any(WebGroceriesDTO.class))
//            )) {
//                WebGroceriesCommand cmd = new WebGroceriesCommand(List.of("web", "8080"), optionsUsed);
//                cmd.execute();
//
//                // Verify WebService's web method is called
//                assertEquals(1, mockedService.constructed().size());
//                verify(mockedService.constructed().get(0), times(1)).web(any(WebGroceriesDTO.class));
//            }
//        }
//    }
}