package com.fges.commands;

import com.fges.groceriesDAO.GroceriesDAO;
import com.fges.modules.OptionsUsed;
import com.fges.services.ClearService;
import com.fges.services.DTO.ClearDTO;
import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ClearCommandTest {

    @Test
    public void testExecuteWithoutError() throws IOException {
        List<String> args = List.of("clear");

        CommandLine mockCmd = Mockito.mock(CommandLine.class);
        Mockito.when(mockCmd.getOptionValue("s")).thenReturn("data.json");
        Mockito.when(mockCmd.getOptionValue("f")).thenReturn("json");

        OptionsUsed optionsUsed = new OptionsUsed(mockCmd);

        GroceriesDAO mockDAO = mock(GroceriesDAO.class);

        ClearCommand command = new ClearCommand(args, optionsUsed) {
            @Override
            public void execute() throws IOException {
                ClearService clearService = new ClearService(mockDAO);
                ClearDTO clearDTO = new ClearDTO();
                clearService.clear(clearDTO);
            }
        };

        command.validateArgs();

        command.execute();
    }
}
