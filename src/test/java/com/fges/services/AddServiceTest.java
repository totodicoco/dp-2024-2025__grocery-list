package com.fges.services;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.GroceryList;
import com.fges.services.DTO.AddDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddServiceTest {

    private GroceriesDAO groceriesDAO;
    private AddService addService;

    @BeforeEach
    void setUp() {
        groceriesDAO = mock(GroceriesDAO.class);
        addService = new AddService(groceriesDAO);
    }

    @Test
    void should_addItemToGroceryList() throws IOException {
        // Initialisation des données de test
        Map<String, Map<String, Integer>> initialData = new HashMap<>();
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("Apple", 2);  // On suppose que "Apple" a déjà une quantité de 2
        initialData.put("Fruits", fruits);

        // Création d'une GroceryList à partir des données
        GroceryList groceryList = GroceryList.fromMap(initialData);

        // Simulation du chargement de la liste de courses
        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        // Création d'un AddDTO avec les paramètres nécessaires
        AddDTO addDTO = new AddDTO("Apple", 3, "Fruits");

        // Appel de la méthode avec l'AddDTO
        boolean result = addService.add(addDTO);

        // Vérifications
        assertTrue(result);
        // Vérifie que la quantité d'Apple est bien mise à jour (2 + 3 = 5)
        assertEquals(5, groceryList.getGroceryList().get("Fruits").get("Apple").intValue());
        // Vérifie que la méthode saveGroceryList a été appelée une fois
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
    }

    @Test
    void should_addNewItemToNewCategory() throws IOException {
        Map<String, Map<String, Integer>> initialData = new HashMap<>();
        GroceryList groceryList = GroceryList.fromMap(initialData);

        when(groceriesDAO.loadGroceryList()).thenReturn(groceryList);

        // Création d'un AddDTO avec un nouvel item dans une nouvelle catégorie
        AddDTO addDTO = new AddDTO("Banana", 3, "Fruits");

        boolean result = addService.add(addDTO);

        assertTrue(result);
        // Vérifie que la quantité de Banana est bien 3 et qu'elle a été ajoutée à la catégorie "Fruits"
        assertEquals(3, groceryList.getGroceryList().get("Fruits").get("Banana").intValue());
        verify(groceriesDAO, times(1)).saveGroceryList(groceryList);
    }
}
