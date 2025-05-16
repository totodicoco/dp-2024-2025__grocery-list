package com.fges.commands;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class InfoCommandTest {

    @Test
    void should_validate_arguments_on_correct_use() {
        InfoCommand cmd = new InfoCommand(Collections.singletonList("info"));
        assertDoesNotThrow(cmd::validateArgs);
    }

//    @Test
//    void should_throw_exception_if_arguments_are_invalid() {
//        InfoCommand cmd = new InfoCommand(Collections.singletonList("invalid"));
//        Exception exception = assertThrows(IllegalArgumentException.class, cmd::validateArgs);
//        assertEquals("info does not take any arguments", exception.getMessage());
//    }
//
//    @Test
//    void should_execute_without_throwing_exception() {
//        InfoCommand cmd = new InfoCommand(Collections.singletonList("info"));
//        assertDoesNotThrow(cmd::execute);
//    }
}