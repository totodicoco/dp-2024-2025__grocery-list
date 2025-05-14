package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.OptionsUsed;
import com.fges.services.AddService;
import com.fges.services.DTO.AddDTO;
import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class AddCommandTest {

    @Test
    public void testExecute() throws IOException {
        List<String> args = Arrays.asList("add", "pomme", "3");

        CommandLine mockCmd = Mockito.mock(CommandLine.class);
        Mockito.when(mockCmd.getOptionValue("s")).thenReturn("test.json");
        Mockito.when(mockCmd.getOptionValue("f")).thenReturn("json");
        Mockito.when(mockCmd.getOptionValue("c", "default")).thenReturn("fruits");

        OptionsUsed optionsUsed = new OptionsUsed(mockCmd);

        GroceriesDAO mockDAO = mock(GroceriesDAO.class);

        AddCommand command = new AddCommand(args, optionsUsed) {
            @Override
            public void execute() throws IOException {
                String itemName = args.get(1);
                int quantity = Integer.parseInt(args.get(2));
                AddService addService = new AddService(mockDAO); // injecte le DAO mocké ici
                AddDTO addDTO = new AddDTO(itemName, quantity, optionsUsed.getCategory());
                addService.add(addDTO);
            }
        };

        command.validateArgs();

        command.execute();
    }
}
