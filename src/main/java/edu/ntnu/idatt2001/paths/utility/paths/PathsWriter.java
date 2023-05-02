package edu.ntnu.idatt2001.paths.utility.paths;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Story;

import java.io.FileWriter;
import java.io.IOException;

public class PathsWriter {
  /**
   * Save game.
   *
   * @param story    the story
   * @param fileName the file name
   */
  public static void saveGame(Story story, String fileName) {
    try (FileWriter fileWriter = new FileWriter(fileName, false)) {
      fileWriter.write(story.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void saveGame(Game game, String fileName) {
    try (FileWriter fileWriter = new FileWriter(fileName, false)) {
      fileWriter.write(game.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
