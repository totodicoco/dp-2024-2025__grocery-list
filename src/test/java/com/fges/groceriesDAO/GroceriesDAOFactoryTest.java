package com.fges.groceriesDAO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class GroceriesDAOFactoryTest {

    @Test
    void testCreateGroceriesDAO_JSON() {
        GroceriesDAOFactory factory = new GroceriesDAOFactory();
        GroceriesDAO dao = factory.createGroceriesDAO("json", "test.json");
        assertNotNull(dao);
        assertTrue(dao instanceof GroceriesDAOImplJson);
    }

    @Test
    void testCreateGroceriesDAO_CSV() {
        GroceriesDAOFactory factory = new GroceriesDAOFactory();
        GroceriesDAO dao = factory.createGroceriesDAO("csv", "test.csv");
        assertNotNull(dao);
        assertTrue(dao instanceof GroceriesDAOImplCsv);
    }

    @Test
    void testCreateGroceriesDAO_MySQL() {
        GroceriesDAOFactory factory = new GroceriesDAOFactory();

        GroceriesDAO dao = factory.createGroceriesDAO("mysql", "testdb");
        assertNotNull(dao);
        assertTrue(dao instanceof GroceriesDAOImplMySQL);
    }

    @Test
    void testCreateGroceriesDAO_InvalidFormat() {
        GroceriesDAOFactory factory = new GroceriesDAOFactory();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                factory.createGroceriesDAO("xml", "file.xml"));
        assertEquals("Unknown format: xml", exception.getMessage());
    }
}
