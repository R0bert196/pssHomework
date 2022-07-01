package org.example.utils;

public class Utility {

  public static int validateFilename(String fileName) {
    try {
      return Integer.parseInt(fileName.substring(6, 8));
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Incorrect file name, file isn't of type orders##.xml");
    }
  }
}
