package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.models.goals.*;
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

import java.util.Set;

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
  private final ChooseGoalsController chooseGoalsController = new ChooseGoalsController();
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
  private final LanguageController languageController = LanguageController.getInstance();

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

    Label templatesLabel = new Label(languageController.getTranslation(Dictionary.TEMPLATE.getKey()));

    ComboBox <String> templates = new ComboBox();
    Set<String> templateNames = chooseGoalsController.fetchTemplates("paths");
    templates.getItems().addAll(chooseGoalsController.addTemplates(templateNames));
    templates.setPromptText(languageController.getTranslation(Dictionary.SELECT_TEMPLATE.getKey()));

    Button upload = new Button(languageController.getTranslation(Dictionary.UPLOAD_FILE.getKey()));
    upload.setId("subMenuButton");
    upload.setOnAction(e -> {
      chooseGoalsController.uploadGameFile();
      templates.getItems().clear();
      templates.getItems().addAll(chooseGoalsController.addTemplates(chooseGoalsController.fetchTemplates("paths")));
      ShowAlert.showInformation(languageController.getTranslation(Dictionary.UPLOAD_TEMPLATE.getKey()), languageController.getTranslation(Dictionary.UPLOAD_TEMPLATE_SUCCESS.getKey()));
    });

    predefinedGoals.getChildren().addAll(standardGoals, checkBox1, checkBox2, checkBox3, checkBox4, impossibleGoals, checkBox5, checkBox6, templatesLabel, templates, upload);

    VBox customGoals = new VBox();
    customGoals.setSpacing(10);
    customGoals.setAlignment(Pos.CENTER);
    customGoals.setPadding(new Insets(0, 0, 0, 20));

    Label customGoalsLabel = new Label(languageController.getTranslation(Dictionary.CUSTOM_GOALS.getKey()));
    ComboBox comboBox = new ComboBox();
    comboBox.getItems().addAll(languageController.getTranslation(Dictionary.GOLD_GOAL.getKey()),
            languageController.getTranslation(Dictionary.INVENTORY_GOAL.getKey()),
            languageController.getTranslation(Dictionary.SCORE_GOAL.getKey()),
            languageController.getTranslation(Dictionary.HEALTH_GOAL.getKey()));
    comboBox.setPromptText(languageController.getTranslation(Dictionary.SELECT_GOAL_TYPE.getKey()));
    TextField textField = new TextField();
    textField.setDisable(true);
    comboBox.setOnAction(e -> {
      if (comboBox.getValue() != null) {
        if (comboBox.getValue().equals(languageController.getTranslation(Dictionary.INVENTORY_GOAL.getKey()))) {
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
    button.setOnAction(e -> goals.add(chooseGoalsController.handleAddGoal(comboBox, textField)));

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

      chooseGoalsController.handlePredefinedGoals(checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6);

      try {
        chooseGoalsController.validateGame(templates);
      } catch (Exception ex) {
        ShowAlert.showError(ex.getMessage(), ex.getMessage());
        return;
      }

      screenController.activate("MainGame");
      resetPane();

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
