//package com.fges.services;
//
//import com.fges.groceriesDAO.GroceriesDAO;
//import com.fges.modules.GroceryList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.Map;
//
//import static org.mockito.Mockito.*;
//
//class ClearServiceTest {
//
//    private GroceriesDAO groceriesDAO;
//    private ClearService clearService;
//
//    @BeforeEach
//    void setUp() {
//        groceriesDAO = mock(GroceriesDAO.class);
//        clearService = new ClearService(groceriesDAO);
//    }
//
//    @Test
//    void should_clearGroceryList() throws IOException {
//        GroceryList emptyList = GroceryList.fromMap(Map.of());
//        doNothing().when(groceriesDAO).saveGroceryList(emptyList);
//
//        clearService.clear();
//
//        verify(groceriesDAO, times(1)).saveGroceryList(emptyList);
//    }
//}