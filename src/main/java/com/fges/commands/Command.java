package com.fges.commands;

public interface Command {
    /**
     * Validates arguments for the command (e.g. arguments for add such as "Pomme 1").
     * Validates necessary command line arguments for that command (e.g. -s for add, list, remove, and clear).
     * @throws IllegalArgumentException if the arguments are invalid.
     */
    void validateArgs() throws IllegalArgumentException;

    /**
     * Executes the command, usually (always) by calling a corresponding service.
     * @throws Exception if an error occurs during execution.
     */
    void execute() throws Exception;
}