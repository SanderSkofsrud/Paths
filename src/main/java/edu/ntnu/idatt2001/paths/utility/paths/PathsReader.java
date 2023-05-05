package edu.ntnu.idatt2001.paths.utility.paths;

import edu.ntnu.idatt2001.paths.models.*;
import edu.ntnu.idatt2001.paths.models.actions.*;
import edu.ntnu.idatt2001.paths.models.goals.*;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.player.PlayerBuilder;
import edu.ntnu.idatt2001.paths.utility.GameData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.ntnu.idatt2001.paths.models.goals.GoalEnum.*;

public class PathsReader {
  static List<String> inventoryGoals = new ArrayList<>();

  /**
   * Load game story.
   *
   * @param fileName the file name
   * @return the story
   * @throws FileNotFoundException the file not found exception
   */
  public static Story loadStory(String fileName) throws IllegalArgumentException, FileNotFoundException {
    if (fileName.isBlank()) {
      throw new IllegalArgumentException("The file name can´t be empty");
    }

    File file = new File(fileName);
    Scanner scanner = new Scanner(file);

    String title = scanner.nextLine();
    Passage openingPassage = null;
    Passage currentPassage = null;
    Story story = null;

    Pattern passagePattern = Pattern.compile("^(::)(.*)");
    Pattern linkPattern = Pattern.compile("\\[(.*?)\\]\\((.*?)\\)");

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      Matcher passageMatcher = passagePattern.matcher(line);
      Matcher linkMatcher = linkPattern.matcher(line);

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
        currentPassage.addLink(link);
      } else if (line.isBlank()) {
        continue;
      } else {
        throw new IllegalArgumentException("The file is not in the correct format");
      }
    }
    scanner.close();
    return story;
  }

  public static GameData loadGame(String fileName) throws IllegalArgumentException, FileNotFoundException {
    if (fileName.isBlank()) {
      throw new IllegalArgumentException("The file name can´t be empty");
    }

    File file = new File(fileName);
    Scanner scanner = new Scanner(file);

    String title = scanner.nextLine();
    Passage openingPassage = null;
    Passage currentPassage = null;
    Story story = null;
    Player player = null;

    Pattern passagePattern = Pattern.compile("^(::)(.*)");
    Pattern linkPattern = Pattern.compile("\\[(.*?)\\]\\((.*?)\\)");
    Pattern playerPattern = Pattern.compile("^;;(.+);(\\d+);(\\d+);(\\d+);\\[(.*)\\]");
    Pattern goalPattern = Pattern.compile("^\\((.+Goal)\\)\\((.+)\\)");
    Pattern actionPattern = Pattern.compile("\\{(.*?)\\}\\((.*?)\\)");

    List<Goal> goals = new ArrayList<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      if (line.isBlank()) {
        continue;
      }

      Matcher passageMatcher = passagePattern.matcher(line);
      Matcher linkMatcher = linkPattern.matcher(line);
      Matcher playerMatcher = playerPattern.matcher(line);
      Matcher goalMatcher = goalPattern.matcher(line);
      Matcher actionMatcher = actionPattern.matcher(line);

      if (passageMatcher.find()) {
        String passageTitle = passageMatcher.group(2);
        String content;
        content = scanner.nextLine();
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
      } else if (playerMatcher.find()) {
        String playerName = playerMatcher.group(1);
        int playerHealth = Integer.parseInt(playerMatcher.group(2));
        int playerScore = Integer.parseInt(playerMatcher.group(3));
        int playerGold = Integer.parseInt(playerMatcher.group(4));
        List<String> playerInventory = Arrays.asList(playerMatcher.group(5).split(","));
        player = new PlayerBuilder(playerName)
                .health(playerHealth)
                .score(playerScore)
                .gold(playerGold)
                .inventory(playerInventory)
                .build();
      } else if (goalMatcher.find()) {
        String goalType = goalMatcher.group(1);
        String goalDescription = goalMatcher.group(2);
        Goal goal = createGoal(goalType, goalDescription);
        goals.add(goal);
    } else {
      throw new IllegalArgumentException("The file is not in the correct format");
    }
  }
scanner.close();
if (!inventoryGoals.isEmpty()) {
    goals.add(GoalFactory.createInventoryGoal(String.valueOf(inventoryGoals)));
  }

if (story == null) {
    throw new IllegalArgumentException(fileName + "The file is not in the correct format");
  }
return new GameData(story, player, goals);
}


  private static Goal createGoal(String goalType, String goalDescription) {
    switch (goalType) {
      case "HealthGoal" -> GoalFactory.createGoal(HEALTH, Integer.parseInt(goalDescription));
      case "ScoreGoal" -> GoalFactory.createGoal(SCORE, Integer.parseInt(goalDescription));
      case "GoldGoal" -> GoalFactory.createGoal(GOLD, Integer.parseInt(goalDescription));
      case "InventoryGoal" -> inventoryGoals.add(goalDescription);
      default -> throw new IllegalArgumentException("The goal type is not valid");
    }
    return null;
  }
}
