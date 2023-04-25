package edu.ntnu.idatt2001.paths.utility.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Story;

import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {
  public static void saveGameJSON(Game game, String fileName) {
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
}
