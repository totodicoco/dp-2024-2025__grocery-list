package com.fges;

import com.fges.commands.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;

import java.io.*;

public class Main {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException{
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws IOException {
        try{
            CommandFactory commandFactory = new CommandFactory(args);
            Command command = commandFactory.getCommand(OBJECT_MAPPER);
            command.validateArgs();
            command.execute();
        }
        catch(ParseException e){
            System.err.println("Erreur de parsing des arguments: " + e.getMessage());
            return 1;
        }
        catch(IllegalArgumentException e){
            System.err.println("Erreur d'argument: " + e.getMessage());
            return 1;
        }
        catch(Exception e){
            System.err.println("Erreur d'exécution: " + e.getMessage());
            return 1;
        }
        return 0;
    }
}