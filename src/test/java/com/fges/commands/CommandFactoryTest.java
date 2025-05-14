package com.fges.commands;

import com.fges.modules.OptionsUsed;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    @Test
    void testGetAddCommand() throws ParseException {
        String[] args = {"add", "Pomme", "2", "-s", "test.json", "-f", "json"};
        CommandFactory factory = new CommandFactory(args);
        Command command = factory.getCommand();
        assertTrue(command instanceof AddCommand);
    }

    @Test
    void testGetClearCommand() throws ParseException {
        String[] args = {"clear", "-s", "data.json", "-f", "json"};
        CommandFactory factory = new CommandFactory(args);
        Command command = factory.getCommand();
        assertTrue(command instanceof ClearCommand);
    }

    @Test
    void testGetInfoCommand() throws ParseException {
        String[] args = {"info"};
        CommandFactory factory = new CommandFactory(args);
        Command command = factory.getCommand();
        assertTrue(command instanceof InfoCommand);
    }

    @Test
    void testUnknownCommandThrowsException() {
        String[] args = {"unknown", "-s", "file.json"};
        assertThrows(IllegalArgumentException.class, () -> {
            CommandFactory factory = new CommandFactory(args);
            factory.getCommand();
        });
    }

    @Test
    void testOptionsUsedIsParsedCorrectly() throws ParseException {
        String[] args = {"list", "-s", "data.csv", "-f", "csv", "-c", "fruits"};
        CommandFactory factory = new CommandFactory(args);
        OptionsUsed options = factory.optionsUsed;

        assertEquals("data.csv", options.getFilename());
        assertEquals("csv", options.getFormat());
        assertEquals("fruits", options.getCategory());
    }
}
