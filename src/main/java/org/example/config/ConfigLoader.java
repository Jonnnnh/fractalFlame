package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    private static final String CONFIG_FILE_PATH = "config.json";

    public static FractalConfig loadConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(CONFIG_FILE_PATH), FractalConfig.class);
    }
}
