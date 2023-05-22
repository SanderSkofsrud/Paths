package edu.ntnu.idatt2001.paths.utility.paths;

import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.player.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Paths writer.
 * The PathsWriter class is used to write the Game object to a paths file.
 *
 * @author Helle R. and Sander S.
 * @version 1.0 08.05.2023
 */
public class PathsWriter {

  /**
   * Save game.
   * The saveGame method is used to write the Game object to a paths file.
   *
   * @param story    the story object that is to be saved
   * @param player   the player object
   * @param directory the directory in which to save the files
   */
  public static void saveStory(Story story, Player player, String directory) {
    String storyFileName = directory + File.separator + player.getName() + ".paths";

    try (FileWriter storyFileWriter = new FileWriter(storyFileName)) {
      storyFileWriter.write(story.toString());
    } catch (IOException e) {
      throw new RuntimeException("Could not write to file");
    }
  }
}