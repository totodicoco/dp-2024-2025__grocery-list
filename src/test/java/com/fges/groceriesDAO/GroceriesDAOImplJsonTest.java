package com.fges.groceriesDAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.modules.GroceryList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroceriesDAOImplJsonTest {

    private GroceriesDAOImplJson groceriesDAO;
    private ObjectMapper objectMapper;
    private final String testFileName = "test-groceries.json";

    void setUp() {
        objectMapper = mock(ObjectMapper.class);
        groceriesDAO = new GroceriesDAOImplJson(testFileName);
    }

    @Test
    void testSaveGroceryList() throws IOException {
        GroceryList groceryList = mock(GroceryList.class);
        Map<String, Map<String, Integer>> groceryMap = new HashMap<>();
        when(groceryList.getGroceryList()).thenReturn(groceryMap);

        groceriesDAO.saveGroceryList(groceryList);

        verify(objectMapper, times(1)).writeValue(new File(testFileName), groceryMap);
    }

    @Test
    void testLoadGroceryList_FileExists() throws IOException {
        Path filePath = Path.of(testFileName);
        String fileContent = "{\"category\":{\"item\":1}}";
        Map<String, Map<String, Integer>> groceryMap = Map.of("category", Map.of("item", 1));
        GroceryList expectedGroceryList = GroceryList.fromMap(groceryMap);

        Files.writeString(filePath, fileContent);
        when(objectMapper.readValue(fileContent, new TypeReference<Map<String, Map<String, Integer>>>() {}))
                .thenReturn(groceryMap);

        GroceryList result = groceriesDAO.loadGroceryList();

        assertEquals(expectedGroceryList, result);
        Files.deleteIfExists(filePath);
    }

    @Test
    void testLoadGroceryList_FileDoesNotExist() throws IOException {
        GroceryList result = groceriesDAO.loadGroceryList();
        assertTrue(result.getGroceryList().isEmpty());
    }
}