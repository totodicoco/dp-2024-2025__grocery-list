package com.fges.commands;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class InfoCommandTest {

    @Test
    void testValidateArgs() {
        InfoCommand cmd = new InfoCommand(Collections.singletonList("info"));
        assertDoesNotThrow(cmd::validateArgs);
    }

    @Test
    void testExecute() {
        InfoCommand cmd = new InfoCommand(Collections.singletonList("info"));
        assertDoesNotThrow(() -> cmd.execute());
    }
}
