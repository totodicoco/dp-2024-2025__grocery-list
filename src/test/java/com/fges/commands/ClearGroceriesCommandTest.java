package com.fges.commands;

import com.fges.modules.OptionsUsed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClearGroceriesCommandTest {

    private OptionsUsed optionsUsed;

    @BeforeEach
    void setUp() {
        optionsUsed = mock(OptionsUsed.class);
        when(optionsUsed.getSource()).thenReturn("test.json");
        when(optionsUsed.getFormat()).thenReturn("json");
    }

    @Test
    void should_validate_arguments_on_correct_use() {
        ClearGroceriesCommand cmd = new ClearGroceriesCommand(List.of("clear"), optionsUsed);
        assertDoesNotThrow(cmd::validateArgs);
    }

    @Test
    void should_not_validate_arguments_if_no_source() {
        when(optionsUsed.getSource()).thenReturn(null);
        ClearGroceriesCommand cmd = new ClearGroceriesCommand(List.of("clear"), optionsUsed);
        assertThrows(IllegalArgumentException.class, cmd::validateArgs);
    }

//    @Test
//    void should_create_correct_DAO_on_execute() throws IOException {
//        GroceriesDAO mockDAO = mock(GroceriesDAO.class);
//
//        try (MockedConstruction<GroceriesDAOFactory> mockedFactory = Mockito.mockConstruction(
//                GroceriesDAOFactory.class,
//                (mock, context) -> when(mock.createGroceriesDAO("json", "test.json")).thenReturn(mockDAO)
//        )) {
//            ClearGroceriesCommand cmd = new ClearGroceriesCommand(List.of("clear"), optionsUsed);
//            cmd.execute();
//
//            // Verify DAO creation
//            assertEquals(1, mockedFactory.constructed().size());
//            verify(mockedFactory.constructed().get(0), times(1)).createGroceriesDAO("json", "test.json");
//        }
//    }
//
//    @Test
//    void should_call_clear_service_method_on_execute() throws IOException {
//        GroceriesDAO mockDAO = mock(GroceriesDAO.class);
//
//        try (MockedConstruction<GroceriesDAOFactory> mockedFactory = Mockito.mockConstruction(
//                GroceriesDAOFactory.class,
//                (mock, context) -> when(mock.createGroceriesDAO("json", "test.json")).thenReturn(mockDAO)
//        )) {
//            try (MockedConstruction<ClearGroceriesService> mockedService = Mockito.mockConstruction(
//                    ClearGroceriesService.class,
//                    (mock, context) -> doNothing().when(mock).clear(any(ClearGroceriesDTO.class))
//            )) {
//                ClearGroceriesCommand cmd = new ClearGroceriesCommand(List.of("clear"), optionsUsed);
//                cmd.execute();
//
//                // Verify ClearGroceriesService's clear method is called
//                assertEquals(1, mockedService.constructed().size());
//                verify(mockedService.constructed().get(0), times(1)).clear(any(ClearGroceriesDTO.class));
//            }
//        }
//    }
}