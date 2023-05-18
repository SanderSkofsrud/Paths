package edu.ntnu.idatt2001.paths.utility.paths;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.GameData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
   *
   * @param story    the story object that is to be saved
   * @param player   the player object
   * @param goals    the goals object
   * @param directory the directory in which to save the files
   */
  public static void saveGame(Story story, Player player, List<Goal> goals, String directory, String image) {
    createDirectoryIfNotExists(directory);

    String storyFileName = directory + File.separator + player.getName() + ".paths";
    String playerFileName = directory + File.separator + "player";
    String goalsFileName = directory + File.separator + "goals";

    try (FileWriter storyFileWriter = new FileWriter(storyFileName)) {
      storyFileWriter.write(story.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

    try (FileWriter playerFileWriter = new FileWriter(playerFileName)) {
      playerFileWriter.write(player.toString());
      playerFileWriter.write(System.lineSeparator());
      playerFileWriter.write(image);
    } catch (IOException e) {
      e.printStackTrace();
    }

    try (FileWriter goalsFileWriter = new FileWriter(goalsFileName)) {
        for (Goal goal : goals) {
            goalsFileWriter.write(goal.toString());
            goalsFileWriter.write(System.lineSeparator());
        }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void saveProgress(Player player, Passage previousPassage, Passage currentPassage) {
    String directory = "src/main/resources/paths/" + player.getName() + "/";
    createDirectoryIfNotExists(directory);

    String progressFileName = directory + File.separator + "progress";

    try (FileWriter progressFileWriter = new FileWriter(progressFileName)) {
      progressFileWriter.write("PreviousPassage: " + previousPassage + System.lineSeparator());
      progressFileWriter.write("CurrentPassage: " + currentPassage + System.lineSeparator());
      progressFileWriter.write(player.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Create a directory if it doesn't exist.
   *
   * @param directory the directory to create
   */
  private static void createDirectoryIfNotExists(String directory) {
    File directoryFile = new File(directory);
    if (!directoryFile.exists()) {
      directoryFile.mkdirs();
    }
  }
}