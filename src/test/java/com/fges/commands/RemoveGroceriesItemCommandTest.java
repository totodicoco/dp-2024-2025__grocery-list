package com.fges.commands;

import com.fges.modules.OptionsUsed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveGroceriesItemCommandTest {

    private OptionsUsed optionsUsed;

    @BeforeEach
    void setUp() {
        optionsUsed = mock(OptionsUsed.class);
        when(optionsUsed.getSource()).thenReturn("list.json");
        when(optionsUsed.getFormat()).thenReturn("json");
    }

    @Test
    void should_validate_arguments_on_correct_use() {
        RemoveGroceriesItemCommand cmd = new RemoveGroceriesItemCommand(List.of("remove", "Banane"), optionsUsed);
        assertDoesNotThrow(cmd::validateArgs);
    }

    @Test
    void should_not_validate_arguments_if_missing_arguments() {
        RemoveGroceriesItemCommand cmd = new RemoveGroceriesItemCommand(List.of("remove"), optionsUsed);
        assertThrows(IllegalArgumentException.class, cmd::validateArgs);
    }

    @Test
    void should_not_validate_arguments_if_no_source() {
        when(optionsUsed.getSource()).thenReturn(null);
        RemoveGroceriesItemCommand cmd = new RemoveGroceriesItemCommand(List.of("remove", "Pomme"), optionsUsed);
        assertThrows(IllegalArgumentException.class, cmd::validateArgs);
    }

//    @Test
//    void should_create_correct_DAO_on_execute() throws IOException {
//        GroceriesDAO mockDAO = mock(GroceriesDAO.class);
//
//        try (MockedConstruction<GroceriesDAOFactory> mockedFactory = Mockito.mockConstruction(
//                GroceriesDAOFactory.class,
//                (mock, context) -> when(mock.createGroceriesDAO("json", "list.json")).thenReturn(mockDAO)
//        )) {
//            RemoveGroceriesItemCommand cmd = new RemoveGroceriesItemCommand(List.of("remove", "Banane"), optionsUsed);
//            cmd.execute();
//
//            // Verify DAO creation
//            assertEquals(1, mockedFactory.constructed().size());
//            verify(mockedFactory.constructed().get(0), times(1)).createGroceriesDAO("json", "list.json");
//        }
//    }
//
//    @Test
//    void should_call_remove_service_method_on_execute() throws IOException {
//        GroceriesDAO mockDAO = mock(GroceriesDAO.class);
//
//        try (MockedConstruction<GroceriesDAOFactory> mockedFactory = Mockito.mockConstruction(
//                GroceriesDAOFactory.class,
//                (mock, context) -> when(mock.createGroceriesDAO("json", "list.json")).thenReturn(mockDAO)
//        )) {
//            try (MockedConstruction<RemoveGroceriesItemService> mockedService = Mockito.mockConstruction(
//                    RemoveGroceriesItemService.class,
//                    (mock, context) -> doNothing().when(mock).remove(any(RemoveGroceriesItemDTO.class))
//            )) {
//                RemoveGroceriesItemCommand cmd = new RemoveGroceriesItemCommand(List.of("remove", "Banane"), optionsUsed);
//                cmd.execute();
//
//                // Verify RemoveService's remove method is called
//                assertEquals(1, mockedService.constructed().size());
//                verify(mockedService.constructed().get(0), times(1)).remove(any(RemoveGroceriesItemDTO.class));
//            }
//        }
//    }
}