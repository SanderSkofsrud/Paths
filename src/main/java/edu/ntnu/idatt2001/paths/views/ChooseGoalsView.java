package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.FileHandlerController;
import edu.ntnu.idatt2001.paths.controllers.GameController;
import edu.ntnu.idatt2001.paths.controllers.PlayerController;
import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.goals.*;
import edu.ntnu.idatt2001.paths.models.goals.GoalEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static edu.ntnu.idatt2001.paths.models.goals.GoalEnum.*;
import static edu.ntnu.idatt2001.paths.models.goals.GoalFactory.createGoal;
import static edu.ntnu.idatt2001.paths.models.goals.GoalFactory.createInventoryGoal;

/**
 * The type Choose goals view.
 */
public class ChooseGoalsView extends View {
  /**
   * The Border pane.
   */
  protected BorderPane borderPane;
  /**
   * The Stack pane.
   */
  protected StackPane stackPane;
  private final ScreenController screenController;
  private final ObservableList<Goal> goals;
  private final GameController gameController = GameController.getInstance();
  FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  PlayerController playerController = PlayerController.getInstance();

  /**
   * Instantiates a new Choose goals view.
   *
   * @param screenController the screen controller
   */
  public ChooseGoalsView(ScreenController screenController) {
    borderPane = new BorderPane();
    stackPane = new StackPane();
    borderPane.setCenter(stackPane);
    this.screenController = screenController;
    this.goals = FXCollections.observableArrayList();
  }

  public Pane getPane() {
    return this.borderPane;
  }

  public void setup() {
    ImageView imageView = new ImageView(new Image("goals.png"));
    HBox hBox = new HBox();
    hBox.setSpacing(25);
    hBox.setAlignment(Pos.CENTER);
    VBox vBox = new VBox();
    vBox.setSpacing(10);
    vBox.setAlignment(Pos.CENTER);

    VBox predefinedGoals = new VBox();
    predefinedGoals.setSpacing(10);
    predefinedGoals.setAlignment(Pos.CENTER);

    Label standardGoals = new Label("Standard Goals:");
    CheckBox checkBox1 = new CheckBox("Goal 1");
    CheckBox checkBox2 = new CheckBox("Goal 2");
    CheckBox checkBox3 = new CheckBox("Goal 3");
    CheckBox checkBox4 = new CheckBox("Goal 4");
    Label impossibleGoals = new Label("Impossible goals:");
    CheckBox checkBox5 = new CheckBox("Goal 5");
    CheckBox checkBox6 = new CheckBox("Goal 6");

    predefinedGoals.getChildren().addAll(standardGoals, checkBox1, checkBox2, checkBox3, checkBox4, impossibleGoals, checkBox5, checkBox6);

    VBox customGoals = new VBox();
    customGoals.setSpacing(10);
    customGoals.setAlignment(Pos.CENTER);

    Label customGoalsLabel = new Label("Custom goals:");
    ComboBox comboBox = new ComboBox();
    comboBox.getItems().addAll("Gold goal", "Health goal", "Inventory goal", "Score goal");
    comboBox.setPromptText("Select goal type");
    TextField textField = new TextField();
    textField.setDisable(true);
    comboBox.setOnAction(e -> {
      if (comboBox.getValue() != null) {
        if (comboBox.getValue().equals("Inventory goal")) {
          textField.setPromptText("Enter item name");
        } else {
          textField.setPromptText("Enter goal value");
        }
        textField.setDisable(false);
      } else {
        textField.setDisable(true);
      }
    });
    Button button = new Button("Add goal");
    button.setId("subMenuButton");
    button.setOnAction(e -> {
      if (comboBox.getValue() != null && !textField.getText().isEmpty()) {
        try {
          int value = Integer.parseInt(textField.getText());
          GoalEnum type = GoalEnum.valueOf(comboBox.getValue().toString().toUpperCase().replace(" ", "_"));
          Goal goal = GoalFactory.createGoal(type, value);
          goals.add(goal);
        } catch (IllegalArgumentException ex) {
          if (comboBox.getValue().toString().equals("Inventory goal")) {
            List<String> itemList = Arrays.asList(textField.getText().split(","));
            Goal goal = GoalFactory.createInventoryGoal(itemList.toArray(new String[0]));
            goals.add(goal);
          } else {
            System.out.println("Error: " + ex.getMessage());
          }
        }
      }
    });


    TableView<Goal> tableView = new TableView<>();
    TableColumn<Goal, String> tableColumn1 = new TableColumn<>("Goal type");
    tableColumn1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClass().getSimpleName()));
    TableColumn<Goal, String> tableColumn2 = new TableColumn<>("Goal value");
    tableColumn2.setCellValueFactory(cellData -> {
      Goal goal = cellData.getValue();
      String goalValueString = "";
      if (goal instanceof GoldGoal goldGoal) {
        goalValueString = Integer.toString(goldGoal.getMinimumGold());
      } else if (goal instanceof HealthGoal healthGoal) {
        goalValueString = Integer.toString(healthGoal.getMinimumHealth());
      } else if (goal instanceof ScoreGoal scoreGoal) {
        goalValueString = Integer.toString(scoreGoal.getMinimumScore());
      } else if (goal instanceof InventoryGoal inventoryGoal) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : inventoryGoal.getMandatoryItems()) {
          stringBuilder.append(item).append(", ");
        }
        goalValueString = stringBuilder.toString();
      }
      return new SimpleStringProperty(goalValueString);
    });

    tableView.getColumns().addAll(tableColumn1, tableColumn2);

    goals.addListener((ListChangeListener.Change<? extends Goal> change) -> {
      while (change.next()) {
        if (change.wasAdded()) {
          tableView.getItems().addAll(change.getAddedSubList());
        }
      }
    });

    customGoals.getChildren().addAll(customGoalsLabel, comboBox, textField, button, tableView);

    Button startButton = new Button("Start");
    startButton.setId("subMenuButton");
    startButton.setOnAction(e -> {
      for (int i = 1; i < 7; i++) {
        if (i == 1 && checkBox1.isSelected()) {
          goals.add(createGoal(GOLD, 100));
        } else if (i == 2 && checkBox2.isSelected()) {
          goals.add(createGoal(HEALTH, 1000));
        } else if (i == 3 && checkBox3.isSelected()) {
          goals.add(createInventoryGoal("Sword", "Shield", "Potion"));
        } else if (i == 4 && checkBox4.isSelected()) {
          goals.add(createGoal(SCORE, 100));
        } else if (i == 5 && checkBox5.isSelected()) {
          goals.add(createGoal(GOLD, 1000));
        } else if (i == 6 && checkBox6.isSelected()) {
          goals.add(createGoal(HEALTH, 1000));
        }
      }

      if (!goals.isEmpty()) {
        Game game;
        if (fileHandlerController.getCurrentGameData() != null && fileHandlerController.getCurrentGameData().getStory() != null) {
          game = new Game(playerController.getPlayer(), fileHandlerController.getCurrentGameData().getStory(), goals);
        } else {
          try {
            game = new Game(playerController.getPlayer(), fileHandlerController.loadGame("template1.paths").getStory(), goals);
          } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
          }
        }

        fileHandlerController.saveGame(playerController.getPlayer().getName(), game.getStory());
        fileHandlerController.saveGameJson(playerController.getPlayer().getName(), game);

        gameController.setGame(game);

        screenController.activate("MainGame");
      } else {
        // Show an error message stating that at least one goal must be selected
      }
    });


    hBox.getChildren().addAll(predefinedGoals, customGoals);
    vBox.getChildren().addAll(imageView, hBox, startButton);

    Image background = new Image("background.png");
    BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true));
    borderPane.setBackground(new Background(backgroundImage));

    stackPane.getChildren().add(vBox);
    stackPane.getStylesheets().add("stylesheet.css");

    ImageView backImage = new ImageView(new Image("back.png"));
    Button backButton = new Button();
    backButton.setId("seeThroughButton");
    backButton.setGraphic(backImage);
    backButton.setPadding(new Insets(10, 10, 10, 10));
    backButton.setOnAction(e -> {
      screenController.activate("NewGame");
      resetPane();
    });

    borderPane.setTop(backButton);
    borderPane.getStylesheets().add("stylesheet.css");
  }

  public void resetPane() {
    stackPane.getChildren().clear();
  }
}
