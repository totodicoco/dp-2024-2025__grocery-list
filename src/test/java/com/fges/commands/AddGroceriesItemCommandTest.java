package com.fges.commands;

import com.fges.modules.OptionsUsed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddGroceriesItemCommandTest {

    private OptionsUsed optionsUsed;

    @BeforeEach
    void setUp() {
        optionsUsed = mock(OptionsUsed.class);
        when(optionsUsed.getSource()).thenReturn("test.json");
        when(optionsUsed.getFormat()).thenReturn("json");
        when(optionsUsed.getCategory()).thenReturn("fruits");
    }

    @Test
    void should_validate_arguments_on_correct_use() {
        AddGroceriesItemCommand cmd = new AddGroceriesItemCommand(List.of("add", "pomme", "3"), optionsUsed);
        assertDoesNotThrow(cmd::validateArgs);
    }

    @Test
    void should_not_validate_arguments_if_missing_arguments() {
        AddGroceriesItemCommand cmd = new AddGroceriesItemCommand(List.of("add", "pomme"), optionsUsed);
        assertThrows(IllegalArgumentException.class, cmd::validateArgs);
    }

    @Test
    void should_not_validate_arguments_if_no_source() {
        when(optionsUsed.getSource()).thenReturn(null);
        AddGroceriesItemCommand cmd = new AddGroceriesItemCommand(List.of("add", "pomme", "3"), optionsUsed);
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
//            AddGroceriesItemCommand cmd = new AddGroceriesItemCommand(List.of("add", "pomme", "3"), optionsUsed);
//            cmd.execute();
//
//            // Verify DAO creation
//            assertEquals(1, mockedFactory.constructed().size());
//            verify(mockedFactory.constructed().get(0), times(1)).createGroceriesDAO("json", "test.json");
//        }
//    }
//
//    @Test
//    void should_call_add_service_method_on_execute() throws IOException {
//        GroceriesDAO mockDAO = mock(GroceriesDAO.class);
//
//        try (MockedConstruction<GroceriesDAOFactory> mockedFactory = Mockito.mockConstruction(
//                GroceriesDAOFactory.class,
//                (mock, context) -> when(mock.createGroceriesDAO("json", "test.json")).thenReturn(mockDAO)
//        )) {
//            try (MockedConstruction<AddGroceriesItemService> mockedService = Mockito.mockConstruction(
//                    AddGroceriesItemService.class,
//                    (mock, context) -> doNothing().when(mock).add(any(AddGroceriesItemDTO.class))
//            )) {
//                AddGroceriesItemCommand cmd = new AddGroceriesItemCommand(List.of("add", "pomme", "3"), optionsUsed);
//                cmd.execute();
//
//                // Verify AddGroceriesItemService's add method is called
//                assertEquals(1, mockedService.constructed().size());
//                verify(mockedService.constructed().get(0), times(1)).add(any(AddGroceriesItemDTO.class));
//            }
//        }
//    }
}