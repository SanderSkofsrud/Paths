package edu.ntnu.idatt2001.paths.utility.json;

import com.google.gson.*;
import edu.ntnu.idatt2001.paths.models.goals.*;
import java.lang.reflect.Type;

/**
 * The type Goal deserializer.
 * The GoalDeserializer class is used to deserialize the different types of goals.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class GoalDeserializer implements JsonDeserializer<Goal> {
  /*
   * The deserialize method is used to deserialize the different types of goals.
   * The method checks for a unique property that can differentiate between the four
   * goal implementations.
   *
   * @param json    the json
   * @param typeOfT the type of t
   * @param context the context
   * @return the goal
   */
  @Override
  public Goal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();

    // Check for a unique property that can differentiate between the four goal implementations
    JsonElement minimumGold = jsonObject.get("minimumGold");
    JsonElement minimumHealth = jsonObject.get("minimumHealth");
    JsonElement mandatoryItems = jsonObject.get("mandatoryItems");
    JsonElement minimumScore = jsonObject.get("minimumScore");

    if (minimumGold != null) {
      // Deserialize as GoldGoal
      return context.deserialize(json, GoldGoal.class);
    } else if (minimumHealth != null) {
      // Deserialize as HealthGoal
      return context.deserialize(json, HealthGoal.class);
    } else if (mandatoryItems != null) {
      // Deserialize as InventoryGoal
      return context.deserialize(json, InventoryGoal.class);
    } else if (minimumScore != null) {
      // Deserialize as ScoreGoal
      return context.deserialize(json, ScoreGoal.class);
    } else {
      throw new JsonParseException("Invalid goal JSON object");
    }
  }
}
