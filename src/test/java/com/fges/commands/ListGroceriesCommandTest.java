package com.fges.commands;

import com.fges.modules.OptionsUsed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListGroceriesCommandTest {

    private OptionsUsed optionsUsed;

    @BeforeEach
    void setUp() {
        optionsUsed = mock(OptionsUsed.class);
        when(optionsUsed.getSource()).thenReturn("list.json");
        when(optionsUsed.getFormat()).thenReturn("json");
    }

    @Test
    void should_validate_arguments_on_correct_use() {
        ListGroceriesCommand cmd = new ListGroceriesCommand(List.of("list"), optionsUsed);
        assertDoesNotThrow(cmd::validateArgs);
    }

    @Test
    void should_not_validate_arguments_if_more_than_one_arg() {
        ListGroceriesCommand cmd = new ListGroceriesCommand(List.of("list", "extraArg"), optionsUsed);
        assertThrows(IllegalArgumentException.class, cmd::validateArgs);
    }

    @Test
    void should_not_validate_arguments_if_no_source() {
        when(optionsUsed.getSource()).thenReturn(null);
        ListGroceriesCommand cmd = new ListGroceriesCommand(List.of("list"), optionsUsed);
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
//            ListGroceriesCommand cmd = new ListGroceriesCommand(List.of("list"), optionsUsed);
//            cmd.execute();
//
//            // Verify DAO creation
//            assertEquals(1, mockedFactory.constructed().size());
//            verify(mockedFactory.constructed().get(0), times(1)).createGroceriesDAO("json", "list.json");
//        }
//    }
//
//    @Test
//    void should_call_list_service_method_on_execute() throws IOException {
//        GroceriesDAO mockDAO = mock(GroceriesDAO.class);
//
//        try (MockedConstruction<GroceriesDAOFactory> mockedFactory = Mockito.mockConstruction(
//                GroceriesDAOFactory.class,
//                (mock, context) -> when(mock.createGroceriesDAO("json", "list.json")).thenReturn(mockDAO)
//        )) {
//            try (MockedConstruction<ListGroceriesService> mockedService = Mockito.mockConstruction(
//                    ListGroceriesService.class,
//                    (mock, context) -> doNothing().when(mock).list(any(ListGroceriesDTO.class))
//            )) {
//                ListGroceriesCommand cmd = new ListGroceriesCommand(List.of("list"), optionsUsed);
//                cmd.execute();
//
//                // Verify ListService's list method is called
//                assertEquals(1, mockedService.constructed().size());
//                verify(mockedService.constructed().get(0), times(1)).list(any(ListGroceriesDTO.class));
//            }
//        }
//    }
}
