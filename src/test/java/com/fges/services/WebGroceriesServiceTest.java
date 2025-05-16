//package com.fges.services;
//
//import com.fges.groceriesDAO.GroceriesDAO;
//import com.fges.modules.GroceryList;
//import com.fges.services.DTO.WebGroceriesDTO;
//import fr.anthonyquere.GroceryShopServer;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//class WebGroceriesServiceTest {
//
//    private GroceriesDAO groceriesDAO;
//    private WebGroceriesService webGroceriesService;
//
//    @BeforeEach
//    void setUp() {
//        groceriesDAO = mock(GroceriesDAO.class);
//        webGroceriesService = new WebGroceriesService(groceriesDAO);
//    }
//
//    @Test
//    void should_start_web_server() throws IOException {
//        // Arrange
//        WebGroceriesDTO webGroceriesDTO = new WebGroceriesDTO(8080);
//        GroceryList mockGroceryList = mock(GroceryList.class);
//        when(groceriesDAO.loadGroceryList()).thenReturn(mockGroceryList);
//
//        GroceryShopServer mockServer = mock(GroceryShopServer.class);
//
//        // Act
//        Boolean result = webGroceriesService.web(webGroceriesDTO);
//
//        // Assert result true
//        assertTrue(result, "The web server should start successfully");
//    }
//}