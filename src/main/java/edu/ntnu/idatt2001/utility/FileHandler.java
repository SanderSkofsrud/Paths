package edu.ntnu.idatt2001.utility;

import edu.ntnu.idatt2001.Link;
import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Story;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type File handler.
 */
public class FileHandler {

  /**
   * Save game.
   *
   * @param story    the story
   * @param fileName the file name
   */
  public static void saveGame(Story story, String fileName) {
    try (FileWriter fileWriter = new FileWriter(fileName, true)) {
      fileWriter.write(story.toString());
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
  public static Story loadGame(String fileName) throws IllegalArgumentException, FileNotFoundException {
    if (fileName.isBlank()){
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
      }
      else {
        throw new IllegalArgumentException("The file is not in the correct format");
      }
    }

    scanner.close();

    return story;
  }

}