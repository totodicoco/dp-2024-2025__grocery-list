package com.fges.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    static class DummyCommand implements Command {
        boolean validated = false;
        boolean executed = false;

        @Override
        public void validateArgs() {
            validated = true;
        }

        @Override
        public void execute() {
            executed = true;
        }
    }

    @Test
    void testValidateArgsIsCalled() {
        DummyCommand command = new DummyCommand();
        command.validateArgs();
        assertTrue(command.validated, "validateArgs() should set validated to true");
    }

    @Test
    void testExecuteIsCalled() throws Exception {
        DummyCommand command = new DummyCommand();
        command.execute();
        assertTrue(command.executed, "execute() should set executed to true");
    }
}
