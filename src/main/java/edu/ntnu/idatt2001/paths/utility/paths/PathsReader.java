package edu.ntnu.idatt2001.paths.utility.paths;

import edu.ntnu.idatt2001.paths.controllers.PlayerController;
import edu.ntnu.idatt2001.paths.models.*;
import edu.ntnu.idatt2001.paths.models.actions.*;
import edu.ntnu.idatt2001.paths.models.goals.*;
import edu.ntnu.idatt2001.paths.models.player.Item;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.player.PlayerBuilder;
import edu.ntnu.idatt2001.paths.utility.GameData;
import edu.ntnu.idatt2001.paths.utility.ProgressData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.ntnu.idatt2001.paths.models.goals.GoalEnum.*;

/**
* The type Paths reader.
* The PathsReader class is used to read a file and create a story from the file.
*
* @author Helle R. and Sander S.
* @version 0.1 08.05.2023
*/
public class PathsReader {
  /**
   * The image for the character model the player is using.
   */
  static String image;
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

      if (passageMatcher.find()) {
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
      } else if (line.isBlank()) {
        continue;
      } else {
        throw new IllegalArgumentException("The file is not in the correct format: " + filePath + " - " + line);
      }
    }
    scanner.close();
    return story;
  }

  /**
   * Load game.
   * The loadGame method is used to read a GameData object from a paths file.
   *
   * @param filePath the file path of the paths file
   * @return the GameData object that is loaded from the file
   * @throws IllegalArgumentException the illegal argument exception
   * @throws FileNotFoundException    the file not found exception
   */
  public static GameData loadGame(String filePath) throws IllegalArgumentException, FileNotFoundException {
    if (filePath == null || filePath.isBlank()) {
      throw new IllegalArgumentException("The file path can't be empty");
    }

    File file = new File(filePath);
    Scanner scanner = new Scanner(file);
    String name = file.getName();

    String playerDataFilePath = filePath.replace(name, "player");
    String goalDataFilePath = filePath.replace(name, "goals");
    Player player = loadPlayer(playerDataFilePath);
    List<Goal> goals = loadGoals(goalDataFilePath);
    PlayerController.getInstance().setActiveCharacter(image);

    Story story = loadStory(filePath);

    scanner.close();

    return new GameData(story, player, goals);
  }

  /**
   * Load player.
   * The loadPlayer method is used to read the player data from a player file.
   *
   * @param filePath the file path of the player file
   * @return the player object that is loaded from the file
   * @throws IllegalArgumentException the illegal argument exception
   * @throws FileNotFoundException    the file not found exception
   */
  private static Player loadPlayer(String filePath) throws IllegalArgumentException, FileNotFoundException {
    if (filePath == null || filePath.isBlank()) {
      throw new IllegalArgumentException("The file path can't be empty");
    }

    File file = new File(filePath);
    Scanner scanner = new Scanner(file);

    PlayerBuilder playerBuilder = null;

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] parts = line.split(":", 2);

      if (parts.length != 2) {
        if (!line.isBlank() && line.endsWith(".png")) {
          image = line;
        } else {
          throw new IllegalArgumentException("The player file is not in the correct format");
        }
        continue; // continue with the next line
      }

      String attribute = parts[0].trim();
      String value = parts[1].trim();

      switch (attribute) {
        case "Name" -> playerBuilder = new PlayerBuilder(value);
        case "Score" -> playerBuilder.score(Integer.parseInt(value));
        case "Health" -> playerBuilder.health(Integer.parseInt(value));
        case "Gold" -> playerBuilder.gold(Integer.parseInt(value));
        case "Inventory" -> {
          String[] inventoryItems = value.split(",");
          List<String> inventory = new ArrayList<>();
          for (String item : inventoryItems) {
            inventory.add(item.trim());
          }
          playerBuilder.inventory(inventory);
        }
        default -> throw new IllegalArgumentException("Invalid attribute in the player file: " + attribute);
      }
    }

    scanner.close();
    return playerBuilder.build();
  }


  /**
   * Load goals.
   * The loadGoals method is used to read the goals data from a player file.
   *
   * @param filePath the file path of the player file
   * @return the goals object that is loaded from the file
   * @throws IllegalArgumentException the illegal argument exception
   * @throws FileNotFoundException    the file not found exception
   */
  private static List<Goal> loadGoals(String filePath) throws IllegalArgumentException, FileNotFoundException {
    if (filePath == null || filePath.isBlank()) {
      throw new IllegalArgumentException("The file path can't be empty");
    }

    File file = new File(filePath);
    Scanner scanner = new Scanner(file);

    List<Goal> goals = new ArrayList<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();

      if (line.isBlank()) {
        continue;
      }

      String[] parts = line.split(":", 2);

      if (parts.length != 2) {
        throw new IllegalArgumentException("The goals file is not in the correct format");
      }

      String goalType = parts[0].trim();
      String goalValue = parts[1].trim();

      switch (goalType) {
        case "GoldGoal" -> goals.add(GoalFactory.createGoal(GoalEnum.GOLD, Integer.parseInt(goalValue)));
        case "ScoreGoal" -> goals.add(GoalFactory.createGoal(GoalEnum.SCORE, Integer.parseInt(goalValue)));
        case "HealthGoal" -> goals.add(GoalFactory.createGoal(GoalEnum.HEALTH, Integer.parseInt(goalValue)));
        case "InventoryGoal" -> {
          String[] inventoryItems = goalValue.split(",");
        goals.add(GoalFactory.createInventoryGoal(inventoryItems));
        }
        default -> throw new IllegalArgumentException("Invalid goal type in the goals file: " + goalType);
      }
    }

    scanner.close();
    return goals;
  }

  public static ProgressData loadProgress(String playerName) throws FileNotFoundException {
    String directory = "src/main/resources/paths/" + playerName + "/";
    String progressFilePath = directory + "progress";
    File file = new File(progressFilePath);
    Scanner scanner = new Scanner(file);

    Pattern passagePattern = Pattern.compile("^(::)(.*)");
    Pattern linkPattern = Pattern.compile("\\[(.*?)\\]\\((.*?)\\)");
    Pattern actionPattern = Pattern.compile("\\{(.*?)\\}\\((.*?)\\)");

    Player player = null;
    Passage previousPassage = null;
    Passage currentPassage = null;
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
        if (previousPassage == null) {
          previousPassage = new Passage(passageTitle, content);
        } else {
          currentPassage = new Passage(passageTitle, content);
        }
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
        if (currentPassage == null) {
          previousPassage.addLink(link);
        } else {
          currentPassage.addLink(link);
        }
      } else if (line.startsWith("Name:")) {
        String playerNameFromFile = line.split(": ", 2)[1];
        int health = Integer.parseInt(scanner.nextLine().split(": ", 2)[1]);
        int score = Integer.parseInt(scanner.nextLine().split(": ", 2)[1]);
        int gold = Integer.parseInt(scanner.nextLine().split(": ", 2)[1]);
        List<String> inventory = Arrays.asList(scanner.nextLine().split(": ", 2)[1].split(",\\s*"));
        player = new PlayerBuilder(playerNameFromFile)
                .health(health)
                .score(score)
                .gold(gold)
                .inventory(inventory)
                .build();
      }


    }
    return new ProgressData(previousPassage, currentPassage, player);
  }
}