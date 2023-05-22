package edu.ntnu.idatt2001.paths.utility.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.actions.Action;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * The type Json reader.
 * The JsonReader class is used to read the JSON file and deserialize it into a Game object.
 * The class uses the Gson library to deserialize the JSON file.
 * The class uses the following deserializers:
 * - StoryDeserializer
 * - GoalDeserializer
 * - ActionDeserializer
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class JsonReader {
  /**
   * Load game json game.
   * The loadGameJSON method is used to read the JSON file and deserialize it into a Game object.
   *
   * @param fileName the file name of the JSON file
   * @return the game object that is loaded from the JSON file
   * @throws FileNotFoundException the file not found exception
   */
  public static Game loadGameJson(String fileName) throws FileNotFoundException {
    try (Reader reader = new FileReader(fileName)) {
      Gson gson = new GsonBuilder()
              .registerTypeAdapter(Story.class, new StoryDeserializer())
              .registerTypeAdapter(Goal.class, new GoalDeserializer())
              .registerTypeAdapter(Action.class, new ActionDeserializer())
              .create();

      return gson.fromJson(reader, Game.class);
    } catch (IOException e) {
      throw new FileNotFoundException("File not found or could not be read: " + fileName);
    }
  }
}
