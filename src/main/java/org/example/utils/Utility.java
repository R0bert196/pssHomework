package org.example.utils;

public class Utility {

  /**
   * @param fileName - the name of the added file
   * @return - the fileId extracted from the filename
   */
  public static int getFileId(String fileName) {
    try {
      return Integer.parseInt(fileName.substring(6, 8));
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Incorrect file name, file isn't of type orders##.xml");
    }
  }
}
