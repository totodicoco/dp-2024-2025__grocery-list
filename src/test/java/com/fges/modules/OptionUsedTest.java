package com.fges.modules;
import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class OptionsUsedTest {

    private CommandLine cmd;
    private OptionsUsed options;

    @BeforeEach
    void setUp() {
        cmd = mock(CommandLine.class);
        when(cmd.getOptionValue("format")).thenReturn("csv");
        options = new OptionsUsed(cmd);
    }

    @Test
    void should_createOptionsUsedFromCommandLine() {
        assertNotNull(options);
        assertEquals("csv", options.getFormat());
    }

    @Test
    void should_determineFormatFromFilePath() {
        assertEquals("json", options.determineFormat(null, "file.json"));
        assertEquals("csv", options.determineFormat("csv", null));
    }

    @Test
    void should_returnNullWhenNoExtension() {
        assertNull(options.determineFormat(null, "file"));
    }
}
