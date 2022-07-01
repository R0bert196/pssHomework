package org.example.configs;

import lombok.extern.slf4j.Slf4j;
import org.example.xmlHandlers.XmlParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class Config {

  public static Map<String, String> getConfigProperties() {
    Map<String, String> appProperties = new HashMap<>();

    try (InputStream input =
        Files.newInputStream(Paths.get("src/main/java/resources/config.properties"))) {
      Properties prop = new Properties();
      prop.load(input);

      appProperties.put("inputPath", prop.getProperty("INPUT_FILE_LOCATION"));
      appProperties.put("outputPath", prop.getProperty("OUTPUT_FILE_LOCATION"));
      return appProperties;
    } catch (IOException e) {
      log.error("There was an error while reading the config.properties", e);
    }
    return appProperties;
  }
}
