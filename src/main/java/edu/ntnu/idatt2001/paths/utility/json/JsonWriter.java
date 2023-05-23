package edu.ntnu.idatt2001.paths.utility.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Story;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Json writer.
 * The JsonWriter class is used to write the Game object to a JSON file.
 * The class uses the Gson library to serialize the Game object.
 * The class uses the following serializers:
 * - StorySerializer
 *
 * @author Helle R. and Sander S.
 * @version 1.1 22.05.2023
 */
public class JsonWriter {
  /**
   * Save game json.
   * The saveGameJSON method is used to write the Game object to a JSON file.
   * The method uses the Gson library to serialize the Game object.
   * The method uses the following serializers:
   * - StorySerializer
   *
   * @param game     the game object that is to be saved
   * @param fileName the file name of the JSON file
   */
  public static void saveGameJson(Game game, String fileName) {
    try (FileWriter fileWriter = new FileWriter(fileName, false)) {
      Gson gson = new GsonBuilder()
              .registerTypeAdapter(Story.class, new StorySerializer())
              .setPrettyPrinting()
              .create();
      String jsonString = gson.toJson(game);
      fileWriter.write(jsonString);
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to file: " + fileName);
    }
  }
}
