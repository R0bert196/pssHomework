package org.example.configs;

import org.example.xmlHandlers.XmlParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {

    public static Map<String, String> getConfigProperties() {
        Map<String, String> appProperties = new HashMap<>();

        try(InputStream input = Files.newInputStream(Paths.get("src/main/java/resources/config.properties"))) {
            Properties prop = new Properties();
            prop.load(input);

            appProperties.put("inputPath", prop.getProperty("input_file_location"));
            appProperties.put("outputPath", prop.getProperty("output_file_location"));
            return appProperties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appProperties;
    }
}
