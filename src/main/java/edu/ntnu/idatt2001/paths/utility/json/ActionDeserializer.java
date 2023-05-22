package edu.ntnu.idatt2001.paths.utility.json;

import com.google.gson.*;
import edu.ntnu.idatt2001.paths.models.actions.*;
import java.lang.reflect.Type;

/**
 * The type Action deserializer.
 * The ActionDeserializer class is used to deserialize the different types of actions.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class ActionDeserializer implements JsonDeserializer<Action> {

  /*
   * The deserialize method is used to deserialize the different types of actions.
   *
   * @param json    the json
   * @param typeOfT the type of t
   * @param context the context
   * @return the action
   */
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
