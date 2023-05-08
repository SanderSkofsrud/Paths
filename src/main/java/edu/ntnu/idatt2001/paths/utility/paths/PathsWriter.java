package edu.ntnu.idatt2001.paths.utility.paths;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Story;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Paths writer.
 * The PathsWriter class is used to write the Game object to a paths file.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class PathsWriter {
  /**
   * Save game.
   * The saveGame method is used to write the Game object to a paths file.
   * The method uses the toString method of the Game class to write the Game object to a paths file.
   *
   * @param story    the story object that is to be saved
   * @param fileName the file name of the paths file
   */
  public static void saveGame(Story story, String fileName) {
    try (FileWriter fileWriter = new FileWriter(fileName, false)) {
      fileWriter.write(story.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Save game.
   * The saveGame method is used to write the Game object to a paths file.
   * The method uses the toString method of the Game class to write the Game object to a paths file.
   *
   * @param game     the game object that is to be saved
   * @param fileName the file name of the paths file
   */
  public static void saveGame(Game game, String fileName) {
    try (FileWriter fileWriter = new FileWriter(fileName, false)) {
      fileWriter.write(game.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
