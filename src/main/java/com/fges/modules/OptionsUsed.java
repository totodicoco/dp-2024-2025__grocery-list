package com.fges.modules;

import org.apache.commons.cli.CommandLine;

public class OptionsUsed {
    public String fileName;
    public String format;
    public String category;

    public OptionsUsed(CommandLine cmd) {
        this.fileName = cmd.getOptionValue("s");
        this.format = determineFormat(cmd.getOptionValue("f"), this.fileName);
        this.category = cmd.getOptionValue("c", "default");
    }

    private String determineFormat(String format, String fileName) {
        if (format == null) {
            if (fileName != null) {
                if (fileName.endsWith(".csv")) return "csv";
                if (fileName.endsWith(".json")) return "json";
            }
            return "json"; // Default to json
        }
        return format;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFormat() {
        return format;
    }

    public String getCategory() {
        return category;
    }
}
