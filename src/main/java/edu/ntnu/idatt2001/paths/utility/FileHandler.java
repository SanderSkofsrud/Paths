package edu.ntnu.idatt2001.paths.utility;

import edu.ntnu.idatt2001.paths.models.*;
import edu.ntnu.idatt2001.paths.models.goals.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type File handler.
 */
public class FileHandler {

  static List<String> inventoryGoals = new ArrayList<>();

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

  public static void gameSave(Game game, String fileName) {
    try (FileWriter fileWriter = new FileWriter(fileName, false)) {
      fileWriter.write(game.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Load game story.
   *
   * @param fileName the file name
   * @return the story
   * @throws FileNotFoundException the file not found exception
   */
  public static Game loadGame(String fileName) throws IllegalArgumentException, FileNotFoundException {
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

    List<Goal> goals = new ArrayList<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      Matcher passageMatcher = passagePattern.matcher(line);
      Matcher linkMatcher = linkPattern.matcher(line);
      Matcher playerMatcher = playerPattern.matcher(line);
      Matcher goalMatcher = goalPattern.matcher(line);

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
      } else if (playerMatcher.find()) {
        String playerName = playerMatcher.group(1);
        int playerHealth = Integer.parseInt(playerMatcher.group(2));
        int playerScore = Integer.parseInt(playerMatcher.group(3));
        int playerGold = Integer.parseInt(playerMatcher.group(4));
        List<String> playerInventory = Arrays.asList(playerMatcher.group(5).split(","));
        player = new Player.Builder(playerName)
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
      } else if (line.isBlank()) {
        continue;
      } else {
        throw new IllegalArgumentException("The file is not in the correct format");
      }
    }
    scanner.close();
    if (!inventoryGoals.isEmpty()) {
      goals.add(new InventoryGoal(inventoryGoals));
    }
    return new Game(player, story, goals);
  }

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


  private static Goal createGoal(String goalType, String goalDescription) {
    switch (goalType) {
      case "HealthGoal" -> {
        return new HealthGoal(Integer.parseInt(goalDescription));
      }
      case "ScoreGoal" -> {
        return new ScoreGoal(Integer.parseInt(goalDescription));
      }
      case "GoldGoal" -> {
        return new GoldGoal(Integer.parseInt(goalDescription));
      }
      case "InventoryGoal" -> inventoryGoals.add(goalDescription);
      default -> throw new IllegalArgumentException("The goal type is not valid");
    }
    return null;
  }
  public static void gameSaveJSON(Game game, String fileName) {
    try (FileWriter fileWriter = new FileWriter(fileName, false)) {
      Gson gson = new GsonBuilder()
              .registerTypeAdapter(Story.class, new StorySerializer())
              .setPrettyPrinting()
              .create();
      String jsonString = gson.toJson(game);
      fileWriter.write(jsonString);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Game loadGameJSON(String fileName) throws FileNotFoundException {
    try (Reader reader = new FileReader(fileName)) {
      Gson gson = new GsonBuilder()
              .registerTypeAdapter(Story.class, new StoryDeserializer())
              .registerTypeAdapter(Goal.class, new GoalDeserializer())
              .create();

      return gson.fromJson(reader, Game.class);
    } catch (IOException e) {
      e.printStackTrace();
      throw new FileNotFoundException("File not found or could not be read: " + fileName);
    }
  }
}
