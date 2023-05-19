package edu.ntnu.idatt2001.paths.utility.paths;

import edu.ntnu.idatt2001.paths.models.*;
import edu.ntnu.idatt2001.paths.models.actions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* The type Paths reader.
* The PathsReader class is used to read a file and create a story from the file.
*
* @author Helle R. and Sander S.
* @version 0.2 19.05.2023
*/
public class PathsReader {
  /**
   * Load game story.
   * The loadGameStory method is used to read the story from a paths file.
   *
   * @param filePath the file path of the story file
   * @return the story object that is loaded from the file
   * @throws IllegalArgumentException the illegal argument exception if the file path is empty
   * @throws FileNotFoundException    the file not found exception if the file is not found
   */
  public static Story loadStory(String filePath) throws IllegalArgumentException, FileNotFoundException {
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("The file path can't be empty");
    }

    File file = new File(filePath);
    Scanner scanner = new Scanner(file);

    String title = scanner.nextLine();
    Passage openingPassage = null;
    Passage currentPassage = null;
    Story story = null;

    Pattern passagePattern = Pattern.compile("^(::)(.*)");
    Pattern linkPattern = Pattern.compile("\\[(.*?)\\]\\((.*?)\\)");
    Pattern actionPattern = Pattern.compile("\\{(.*?)\\}\\((.*?)\\)");

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      Matcher passageMatcher = passagePattern.matcher(line);
      Matcher linkMatcher = linkPattern.matcher(line);
      Matcher actionMatcher = actionPattern.matcher(line);

      if (line.isBlank()) {
        continue;
      } else if (passageMatcher.find()) {
        String passageTitle = passageMatcher.group(2);
        String content = scanner.nextLine();
        Passage newPassage = new Passage(passageTitle, content);

        if (openingPassage == null) {
          openingPassage = newPassage;
          story = new Story(title, openingPassage);
        } else {
          story.addPassage(newPassage);
        }

        currentPassage = newPassage;
      } else if (linkMatcher.find()) {
        String linkDescription = linkMatcher.group(1);
        String linkReference = linkMatcher.group(2);
        Link link = new Link(linkDescription, linkReference);
        List<Action> actions = new ArrayList<>();
        while (actionMatcher.find()) {
          String actionType = actionMatcher.group(1);
          String actionValue = actionMatcher.group(2);
          switch (actionType) {
            case "GoldAction" -> actions.add(new GoldAction(Integer.parseInt(actionValue)));
            case "ScoreAction" -> actions.add(new ScoreAction(Integer.parseInt(actionValue)));
            case "HealthAction" -> actions.add(new HealthAction(Integer.parseInt(actionValue)));
            case "InventoryAction" -> actions.add(new InventoryAction(actionValue));
            default -> throw new IllegalArgumentException("Invalid action type: " + actionType);
          }
        }
        for (Action action : actions) {
          link.addAction(action);
        }
        currentPassage.addLink(link);
      } else {
        throw new IllegalArgumentException("The file is not in the correct format: " + filePath + " - " + line);
      }
    }
    scanner.close();
    return story;
  }
}