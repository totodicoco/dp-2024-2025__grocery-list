package com.fges.commands;

public interface Command {
    void validateArgs() throws IllegalArgumentException;
    void execute() throws Exception;
}