package edu.ntnu.idatt2001.paths.utility.json;

import com.google.gson.*;
import edu.ntnu.idatt2001.paths.models.actions.*;

import java.lang.reflect.Type;

public class ActionDeserializer implements JsonDeserializer<Action> {
  @Override
  public Action deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
          throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();

    if (jsonObject.has("gold")) {
      return context.deserialize(json, GoldAction.class);
    } else if (jsonObject.has("health")) {
      return context.deserialize(json, HealthAction.class);
    } else if (jsonObject.has("points")) {
      return context.deserialize(json, ScoreAction.class);
    } else if (jsonObject.has("item")) {
      return context.deserialize(json, InventoryAction.class);
    } else {
      throw new JsonParseException("Unsupported action type");
    }
  }
}
