package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.List;

public class FinalPassageController {
  private LanguageController languageController = LanguageController.getInstance();
  private GameController gameController = GameController.getInstance();

  public VBox goalsAchieved() {
    VBox goalsVBox = new VBox();
    List<Goal> goals = gameController.getGame().getGoals();
    Player player = gameController.getGame().getPlayer();
    for (Goal goal : goals) {
      Text text = new Text(goal.toString());
      text.setId("creditsGreen");
      if (goal.isFulfilled(player)) {
        goalsVBox.getChildren().add(text);
      }
    }
    return goalsVBox;
  }

  public VBox goalsFailed() {
    VBox goalsVBox = new VBox();
    List<Goal> goals = gameController.getGame().getGoals();
    Player player = gameController.getGame().getPlayer();
    for (Goal goal : goals) {
      Text text = new Text(goal.toString());
      text.setId("creditsRed");
      if (!goal.isFulfilled(player)) {
        goalsVBox.getChildren().add(text);
      }
    }
    return goalsVBox;
  }

  public TextFlow percentageOfGoalsCompleted() {
    List<Goal> goals = gameController.getGame().getGoals();
    Player player = gameController.getGame().getPlayer();
    double goalsCompleted = 0.0;
    for (Goal goal : goals) {
      if (goal.isFulfilled(player)) {
        goalsCompleted++;
      }
    }
    double percentage = (goalsCompleted / goals.size()) * 100;

    String textString = languageController.getTranslation(Dictionary.CREDITS_TEXT_2_3.getKey())
            + String.format("%.2f", percentage);
    String numberString = String.format("%.2f", percentage);

    TextFlow textFlow = new TextFlow();

    Text nonColoredText = new Text(textString.substring(0, textString.indexOf(numberString)));
    nonColoredText.setId("creditsText");

    Text coloredText = new Text(numberString);
    Text percentageText = new Text("%");
    if (percentage == 100) {
      coloredText.setId("creditsGreen");
      percentageText.setId("creditsGreen");
    } else {
      coloredText.setId("creditsRed");
      percentageText.setId("creditsRed");
    }

    textFlow.getChildren().addAll(nonColoredText, coloredText, percentageText);

    return textFlow;
  }
}

