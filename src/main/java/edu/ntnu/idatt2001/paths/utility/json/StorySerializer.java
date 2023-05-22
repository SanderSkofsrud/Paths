package edu.ntnu.idatt2001.paths.utility.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Story;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * The type Story serializer.
 * The StorySerializer class is used to serialize the different types of stories.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class StorySerializer implements JsonSerializer<Story> {
  /*
   * The serialize method is used to serialize the different types of stories.
   *
   * @param story      the story
   * @param typeOfSrc  the type of src
   * @param context    the context
   * @return the json element
   */
  @Override
  public JsonElement serialize(Story story, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("title", story.getTitle());
    jsonObject.add("openingPassage", context.serialize(story.getOpeningPassage()));
    jsonObject.add("endingPassage", context.serialize(story.getEndingPassage()));

    JsonObject passagesJson = new JsonObject();
    for (Map.Entry<Link, Passage> entry : story.getPassages().entrySet()) {
      passagesJson.add(entry.getKey().getText(), context.serialize(entry.getValue()));
    }
    jsonObject.add("passages", passagesJson);

    return jsonObject;
  }
}
