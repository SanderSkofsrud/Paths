package edu.ntnu.idatt2001.paths.utility.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import edu.ntnu.idatt2001.paths.models.goals.*;

import java.lang.reflect.Type;

public class GoalDeserializer implements JsonDeserializer<Goal> {
  @Override
  public Goal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
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
