package org.example.configs;

import lombok.extern.slf4j.Slf4j;
import org.example.xmlHandlers.XmlParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class Config {

  /**
   * @return a map containing the output and input path, read from the config.properties
   */
  public static Map<String, String> getConfigProperties() {

    Map<String, String> appProperties = new HashMap<>();
    try (InputStream input =
        Files.newInputStream(Paths.get("src/main/java/resources/config.properties"))) {
      Properties prop = new Properties();
      prop.load(input);
      //todo sa scot mai sus
//      String inputFileLocation = prop.getProperty("INPUT_FILE_LOCATION");
      String inputFileLocation = getLocation(prop, "INPUT_FILE_LOCATION");
      String outputFileLocation = getLocation(prop, "OUTPUT_FILE_LOCATION");
      appProperties.put("inputPath", inputFileLocation);
      appProperties.put("outputPath", outputFileLocation);
//      return appProperties;
    } catch (IOException e) {
//      throw new
      log.error("There was an error while reading the config.properties", e);
    }
    return appProperties;
  }


  private static String getLocation(Properties prop, String location) {
    String path = prop.getProperty(location);

    //todo try catch
    if (location == null) {
      throw new IllegalArgumentException("Couldn't read all the configuration file values");
    }
    return path;
  }
}
