package com.fges;

import java.io.IOException;
import java.util.Map;

public interface GroceriesDAO{
    void add(String item, int quantity, String category) throws IOException;
    void list() throws IOException;
    void remove(String item, String category) throws IOException;
    void clear() throws IOException;
}
