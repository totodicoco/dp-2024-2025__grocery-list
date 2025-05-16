package com.fges.groceriesDAO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class GroceriesDAOFactoryTest {

    @Test
    void should_create_json_dao() {
        GroceriesDAOFactory factory = new GroceriesDAOFactory();
        GroceriesDAO dao = factory.createGroceriesDAO("json", "test.json");
        assertNotNull(dao);
        assertTrue(dao instanceof GroceriesDAOImplJson);
    }

    @Test
    void should_create_csv_dao() {
        GroceriesDAOFactory factory = new GroceriesDAOFactory();
        GroceriesDAO dao = factory.createGroceriesDAO("csv", "test.csv");
        assertNotNull(dao);
        assertTrue(dao instanceof GroceriesDAOImplCsv);
    }

    @Test
    void should_throw_exception_on_unknown_format() {
        GroceriesDAOFactory factory = new GroceriesDAOFactory();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                factory.createGroceriesDAO("xml", "file.xml"));
        assertEquals("Unknown format: xml", exception.getMessage());
    }
}
