package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.*;
import edu.ntnu.idatt2001.paths.models.goals.GoalEnum;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.ShowAlert;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.ntnu.idatt2001.paths.models.goals.GoalEnum.*;
import static edu.ntnu.idatt2001.paths.models.goals.GoalFactory.createGoal;
import static edu.ntnu.idatt2001.paths.models.goals.GoalFactory.createInventoryGoal;

/**
 * The type Choose goals view.
 * The class is used to create the view where the player can choose the goals of the game.
 * The class extends the View class.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class ChooseGoalsView extends View {
  /**
   * The Border pane.
   * The borderPane is the main pane of the view.
   */
  protected BorderPane borderPane;
  /**
   * The Stack pane.
   * The stackPane is used to stack the different panes of the view.
   */
  protected StackPane stackPane;
  /**
   * The Screen controller.
   * The screenController is used to switch between the different views of the GUI.
   */
  private final ScreenController screenController;

  /**
   * The ChooseGame controller.
   * The ChooseGameController is used to fetch all templates of the game.
   */
  private final ChooseGoalsController chooseGoalsController = ChooseGoalsController.getInstance();
  /**
   * The Goals.
   * The goals is a list of the goals of the game.
   */
  private final ObservableList<Goal> goals;
  /**
   * The Game controller.
   * The gameController is used to create a new game.
   */
  private final GameController gameController = GameController.getInstance();
  /**
   * The File handler controller.
   * The fileHandlerController is used to save the game.
   */
  private final FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  /**
   * The Player controller.
   * The playerController is used to create the player of the game.
   */
  private final PlayerController playerController = PlayerController.getInstance();
  private final LanguageController languageController = LanguageController.getInstance();
  private final String GOLDGOAL = "Gold goal";
  private String GOLDGOALTRANSLATED;
  private final String INVENTORYGOAL = "Inventory goal";
  private String INVENTORYGOALTRANSLATED;
  private final String HEALTHGOAL = "Health goal";
  private String HEALTHGOALTRANSLATED;
  private final String SCOREGOAL = "Score goal";
  private String SCOREGOALTRANSLATED;

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

  /**
   * Gets pane.
   *
   * @return the pane
   */
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * Setup.
   * The setup method is used to create the view.
   */
  public void setup() {
    GOLDGOALTRANSLATED = languageController.getTranslation(Dictionary.GOLD_GOAL.getKey());
    INVENTORYGOALTRANSLATED = languageController.getTranslation(Dictionary.INVENTORY_GOAL.getKey());
    HEALTHGOALTRANSLATED = languageController.getTranslation(Dictionary.HEALTH_GOAL.getKey());
    SCOREGOALTRANSLATED = languageController.getTranslation(Dictionary.SCORE_GOAL.getKey());

    ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/goals.png")));
    HBox hBox = new HBox();
    hBox.setSpacing(25);
    hBox.setAlignment(Pos.CENTER);
    VBox vBox = new VBox();
    vBox.setSpacing(10);
    vBox.setAlignment(Pos.CENTER);

    VBox predefinedGoals = new VBox();
    predefinedGoals.setSpacing(10);
    predefinedGoals.setAlignment(Pos.TOP_LEFT);
    predefinedGoals.setPadding(new Insets(0, 0, 0, 120));

    Label standardGoals = new Label(languageController.getTranslation(Dictionary.STANDARD_GOALS.getKey()));
    CheckBox checkBox1 = new CheckBox(languageController.getTranslation(Dictionary.GOAL_1.getKey()));
    CheckBox checkBox2 = new CheckBox(languageController.getTranslation(Dictionary.GOAL_2.getKey()));
    CheckBox checkBox3 = new CheckBox(languageController.getTranslation(Dictionary.GOAL_3.getKey()));
    CheckBox checkBox4 = new CheckBox(languageController.getTranslation(Dictionary.GOAL_4.getKey()));
    Label impossibleGoals = new Label(languageController.getTranslation(Dictionary.IMPOSSIBLE_GOALS.getKey()));
    CheckBox checkBox5 = new CheckBox(languageController.getTranslation(Dictionary.GOAL_5_1.getKey()) + "\n" + Dictionary.GOAL_5_2.getKey());
    CheckBox checkBox6 = new CheckBox(languageController.getTranslation(Dictionary.GOAL_6_1.getKey()) + "\n" + Dictionary.GOAL_6_2.getKey());

    ComboBox <String> templates = new ComboBox();
    Set<String> templateNames = chooseGoalsController.fetchTemplates("paths");
    for (String templateName : templateNames) {
      templates.getItems().add(templateName);
    }
    templates.setPromptText(languageController.getTranslation(Dictionary.SELECT_TEMPLATE.getKey()));


    predefinedGoals.getChildren().addAll(standardGoals, checkBox1, checkBox2, checkBox3, checkBox4, impossibleGoals, checkBox5, checkBox6, templates);

    VBox customGoals = new VBox();
    customGoals.setSpacing(10);
    customGoals.setAlignment(Pos.CENTER);
    customGoals.setPadding(new Insets(0, 0, 0, 20));

    Label customGoalsLabel = new Label(languageController.getTranslation(Dictionary.CUSTOM_GOALS.getKey()));
    ComboBox comboBox = new ComboBox();
    comboBox.getItems().addAll(GOLDGOALTRANSLATED, HEALTHGOALTRANSLATED, INVENTORYGOALTRANSLATED, SCOREGOALTRANSLATED);
    comboBox.setPromptText(languageController.getTranslation(Dictionary.SELECT_GOAL_TYPE.getKey()));
    TextField textField = new TextField();
    textField.setDisable(true);
    comboBox.setOnAction(e -> {
      if (comboBox.getValue() != null) {
        if (comboBox.getValue().equals(INVENTORYGOALTRANSLATED)) {
          textField.setPromptText(languageController.getTranslation(Dictionary.ENTER_ITEM_NAME.getKey()));
        } else {
          textField.setPromptText(languageController.getTranslation(Dictionary.ENTER_GOAL_VALUE.getKey()));
        }
        textField.setDisable(false);
      } else {
        textField.setDisable(true);
      }
    });

    Button button = new Button(languageController.getTranslation(Dictionary.ADD_GOAL.getKey()));
    button.setId("subMenuButton");
    button.setOnAction(e -> {
      if (comboBox.getValue() != null && !textField.getText().isEmpty()) {
        int value = Integer.parseInt(textField.getText());
        String selectedTranslatedValue = comboBox.getValue().toString();
        if (selectedTranslatedValue.equals(GOLDGOALTRANSLATED)) {
          selectedTranslatedValue = GOLDGOAL;
        } else if (selectedTranslatedValue.equals(HEALTHGOALTRANSLATED)) {
          selectedTranslatedValue = HEALTHGOAL;
        } else if (selectedTranslatedValue.equals(INVENTORYGOALTRANSLATED)) {
          selectedTranslatedValue = INVENTORYGOAL;
        } else if (selectedTranslatedValue.equals(SCOREGOALTRANSLATED)) {
          selectedTranslatedValue = SCOREGOAL;
        }
        String selectedValue = languageController.translateToEnglish(selectedTranslatedValue.toUpperCase().replace(" GOAL", ""));
        GoalEnum type = GoalEnum.valueOf(selectedValue);
        Goal goal = GoalFactory.createGoal(type, value);

        boolean isGoalAlreadyAdded = false;
        for (Goal existingGoal : goals) {
          if (existingGoal.getClass().equals(goal.getClass()) && existingGoal.toString().equals(goal.toString())) {
            isGoalAlreadyAdded = true;
            break;
          }
        }

        if (isGoalAlreadyAdded) {
          ShowAlert.showInformation(languageController.getTranslation(Dictionary.GOAL_ALREADY_ADDED.getKey()), languageController.getTranslation(Dictionary.THIS_GOAL_IS_ALREADY_ADDED.getKey()));
        } else {
          goals.add(goal);
        }
      }
    });

    TableView<Goal> tableView = new TableView<>();
    TableColumn<Goal, String> tableColumn1 = new TableColumn<>(languageController.getTranslation(Dictionary.GOAL_TYPE.getKey()));
    tableColumn1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClass().getSimpleName()));
    TableColumn<Goal, String> tableColumn2 = new TableColumn<>(languageController.getTranslation(Dictionary.GOAL_VALUE.getKey()));
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

    Button startButton = new Button(languageController.getTranslation(Dictionary.START.getKey()));
    startButton.setId("subMenuButton");
    startButton.setOnAction(e -> {

      for (int i = 1; i < 7; i++) {
        if (i == 1 && checkBox1.isSelected()) {
          goals.add(createGoal(GOLD, 200));
        } else if (i == 2 && checkBox2.isSelected()) {
          goals.add(createGoal(SCORE, 100));
        } else if (i == 3 && checkBox3.isSelected()) {
          goals.add(createGoal(HEALTH,50));
        } else if (i == 4 && checkBox4.isSelected()) {
          goals.add(createInventoryGoal("Sword"));
        } else if (i == 5 && checkBox5.isSelected()) {
          goals.add(createGoal(GOLD, 200));
          goals.add(createGoal(SCORE, 100));
          goals.add(createGoal(HEALTH, 50));
          goals.add(createInventoryGoal("Sword"));
        } else if (i == 6 && checkBox6.isSelected()) {
          goals.add(createGoal(HEALTH, 100));
          goals.add(createGoal(SCORE, 200));
          goals.add(createGoal(GOLD, 250));
          goals.add(createInventoryGoal("Sword", "Potion", "Shield"));
        }
      }

      if (goals.isEmpty() && templates.getValue() == null) {
        ShowAlert.showInformation(languageController.getTranslation(Dictionary.GOALS_AND_TEMPLATE_NOT_SELECTED.getKey()), languageController.getTranslation(Dictionary.GOALS_AND_TEMPLATE_NEED_SELECTION.getKey()));
      } else if (templates.getValue() == null) {
        ShowAlert.showInformation(languageController.getTranslation(Dictionary.TEMPLATE_NOT_SELECTED.getKey()), languageController.getTranslation(Dictionary.TEMPLATE_NEED_SELECTION.getKey()));
      } else if (goals.isEmpty()) {
        ShowAlert.showInformation(languageController.getTranslation(Dictionary.GOALS_NOT_SELECTED.getKey()), languageController.getTranslation(Dictionary.GOALS_NEED_SELECTION.getKey()));
      } else {
        List<Goal> distinctGoals = goals.stream()
            .collect(Collectors.toMap(
                goal -> goal.toString(),
                goal -> goal,
                (existingGoal, newGoal) -> existingGoal
            ))
            .values()
            .stream()
            .collect(Collectors.toList());

        goals.clear();
        goals.addAll(distinctGoals);
        Game game;
          try {
            String selectedTemplate = templates.getValue();
            Story story = fileHandlerController.loadTemplate(selectedTemplate);
            game = new Game(playerController.getPlayer(), story, goals, story.getOpeningPassage());
          } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
          }

        fileHandlerController.saveGameJson(playerController.getPlayer().getName(), null, game);
        fileHandlerController.saveGameJson(playerController.getPlayer().getName(), "src/main/resources/initialGame/", game);

        gameController.setGame(game);

        screenController.activate("MainGame");
        resetPane();
      }
    });

    hBox.getChildren().addAll(customGoals, predefinedGoals);
    vBox.getChildren().addAll(imageView, hBox, startButton);

    Image background = new Image(getClass().getResourceAsStream("/images/background.png"));
    BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true));
    borderPane.setBackground(new Background(backgroundImage));

    stackPane.getChildren().add(vBox);
    stackPane.getStylesheets().add("stylesheet.css");

    ImageView backImage = new ImageView(new Image(getClass().getResourceAsStream("/images/back.png")));
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

  /**
   * Resets the pane to its original state.
   */
  public void resetPane() {
    stackPane.getChildren().clear();
  }
}
