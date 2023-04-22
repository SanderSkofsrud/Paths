package edu.ntnu.idatt2001.paths.utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Story;

import java.lang.reflect.Type;
import java.util.Map;

class StorySerializer implements JsonSerializer<Story> {
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
