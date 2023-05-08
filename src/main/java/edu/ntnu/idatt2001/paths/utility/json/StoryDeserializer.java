package edu.ntnu.idatt2001.paths.utility.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.actions.Action;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Story deserializer.
 * The StoryDeserializer class is used to deserialize the different types of stories.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class StoryDeserializer implements JsonDeserializer<Story> {
  /*
   * The deserialize method is used to deserialize the different types of stories.
   *
   * @param json    the json
   * @param typeOfT the type of t
   * @param context the context
   * @return the story
   */
    @Override
    public Story deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      JsonObject jsonObject = json.getAsJsonObject();

      String title = jsonObject.get("title").getAsString();
      Passage openingPassage = context.deserialize(jsonObject.get("openingPassage"), Passage.class);

      Story story = new Story(title, openingPassage);

      JsonObject passagesJson = jsonObject.get("passages").getAsJsonObject();
      for (Map.Entry<String, JsonElement> entry : passagesJson.entrySet()) {
        JsonObject passageJson = entry.getValue().getAsJsonObject();
        String passageTitle = passageJson.get("title").getAsString();
        String content = passageJson.get("content").getAsString();

        Passage passage = new Passage(passageTitle, content);

        JsonArray linksJson = passageJson.get("links").getAsJsonArray();
        for (JsonElement linkElement : linksJson) {
          JsonObject linkJson = linkElement.getAsJsonObject();
          String linkText = linkJson.get("text").getAsString();
          String reference = linkJson.get("reference").getAsString();
          Type listType = new TypeToken<List<Action>>() {}.getType();
          List<Action> actions = context.deserialize(linkJson.get("actions"), listType);

          Link link = new Link(linkText, reference);
          for (Action action : actions) {
            link.addAction(action);
          }

          passage.addLink(link);
        }

        story.addPassage(passage);
      }

      return story;
    }
  }




