package edu.ntnu.idatt2001.paths.controllers;

import static edu.ntnu.idatt2001.paths.models.goals.GoalEnum.*;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.goals.GoalEnum;
import edu.ntnu.idatt2001.paths.models.goals.GoalFactory;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.ShowAlert;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


/**
 * The type Load game controller.
 * This class is responsible for loading games.
 * It is also responsible for creating the GUI elements
 * for loading a game.
 * It is used to show the user a list of saved games, and to load the selected game.
 *
 * @author Helle R. and Sander S.
 * @version 1.1 21.05.2023
 */
public class ChooseGoalsController {
  /**
   * The type of the file.
   */
  private String fileType;
  /**
   * The Goals of the game.
   */
  private final ObservableList<Goal> goals;
  /**
   * The Game controller.
   */
  private final GameController gameController = GameController.getInstance();
  /**
   * The File handler controller.
   */
  private final FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  /**
   * The Player controller.
   */
  private final PlayerController playerController = PlayerController.getInstance();
  /**
   * The Language controller.
   */
  private final LanguageController languageController = LanguageController.getInstance();

  /**
   * Instantiates a new Choose goals controller.
   * Sets the goals observable list.
   */
  public ChooseGoalsController() {
    this.goals = FXCollections.observableArrayList();
  }

  /**
   * Fetch templates set.
   * Fetches the templates from the file system.
   *
   * @param fileType the file type of the templates
   * @return the set of templates
   */
  public Set<String> fetchTemplates(String fileType) {
    this.fileType = fileType;
    Set<String> fileNames = new HashSet<>();

    // Load files from file system
    try {
      Path resourcesPath = Paths.get("src/main/resources/templates/");
      if (Files.exists(resourcesPath)) {
        try (Stream<Path> stream = Files.list(resourcesPath)) {
          stream.forEach(file -> {
            if (Files.isRegularFile(file) && file.toString().endsWith(fileType)) {
              fileNames.add(file.getFileName().toString());
            }
          });
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to load files from file system");
    }
    return fileNames;
  }

  /**
   * Handle add goal goal.
   * Handles the adding of a custom goal.
   *
   * @param comboBox  the combo box containing the goal types
   * @param textField the text field containing the goal value
   * @return the goal that was added
   */
  public Goal handleAddGoal(ComboBox comboBox, TextField textField) {
    if (comboBox.getValue() != null && !textField.getText().isEmpty()) {
      int value = 0;
      String stringValue = null;
      try {
        value = Integer.parseInt(textField.getText());
      } catch (NumberFormatException ex) {
        stringValue = textField.getText();
      }
      String selectedTranslatedValue = comboBox.getValue().toString();
      String selectedValue = languageController.translateToEnglish(selectedTranslatedValue
          .toUpperCase().replace(" GOAL", ""));
      GoalEnum type = GoalEnum.valueOf(selectedValue);
      Goal goal = null;
      if (type.equals(GoalEnum.INVENTORY)) {
        goal = GoalFactory.createInventoryGoal(stringValue);
      } else {
        goal = GoalFactory.createGoal(type, value);
      }

      boolean isGoalAlreadyAdded = false;
      for (Goal existingGoal : goals) {
        if (existingGoal.getClass().equals(goal.getClass()) && existingGoal
            .toString().equals(goal.toString())) {
          isGoalAlreadyAdded = true;
          break;
        }
      }

      if (!isGoalAlreadyAdded) {
        goals.add(goal);
        return goal;
      }
    }
    return null;
  }

  /**
   * Handle predefined goals.
   * Handles the adding of predefined goals.
   *
   * @param checkBox1 the first predefined goal
   * @param checkBox2 the second predefined goal
   * @param checkBox3 the third predefined goal
   * @param checkBox4 the fourth predefined goal
   * @param checkBox5 the fifth predefined goal
   * @param checkBox6 the sixth predefined goal
   */
  public void handlePredefinedGoals(CheckBox checkBox1, CheckBox checkBox2,
                                    CheckBox checkBox3, CheckBox checkBox4,
                                    CheckBox checkBox5, CheckBox checkBox6) {
    if (checkBox1.isSelected()) {
      goals.add(GoalFactory.createGoal(GOLD, 200));
    }
    if (checkBox2.isSelected()) {
      goals.add(GoalFactory.createGoal(SCORE, 100));
    }
    if (checkBox3.isSelected()) {
      goals.add(GoalFactory.createGoal(HEALTH, 50));
    }
    if (checkBox4.isSelected()) {
      goals.add(GoalFactory.createInventoryGoal("Sword"));
    }
    if (checkBox5.isSelected()) {
      goals.add(GoalFactory.createGoal(GOLD, 200));
      goals.add(GoalFactory.createGoal(SCORE, 100));
      goals.add(GoalFactory.createGoal(HEALTH, 50));
      goals.add(GoalFactory.createInventoryGoal("Sword"));
    }
    if (checkBox6.isSelected()) {
      goals.add(GoalFactory.createGoal(HEALTH, 100));
      goals.add(GoalFactory.createGoal(SCORE, 200));
      goals.add(GoalFactory.createGoal(GOLD, 250));
      goals.add(GoalFactory.createInventoryGoal("Sword", "Potion", "Shield"));
    }
  }

  /**
   * Validates the game via the templates and goals.
   *
   * @param templates the combo box containing the templates
   */
  public void validateGame(ComboBox<String> templates) {
    try {
      if (goals.isEmpty() && templates.getValue() == null) {
        ShowAlert.showInformation(languageController.getTranslation(Dictionary
            .GOALS_AND_TEMPLATE_NOT_SELECTED.getKey()), languageController
            .getTranslation(Dictionary.GOALS_AND_TEMPLATE_NEED_SELECTION.getKey()));
      } else if (templates.getValue() == null) {
        ShowAlert.showInformation(languageController.getTranslation(Dictionary
            .TEMPLATE_NOT_SELECTED.getKey()), languageController
            .getTranslation(Dictionary.TEMPLATE_NEED_SELECTION.getKey()));
      } else if (goals.isEmpty()) {
        ShowAlert.showInformation(languageController.getTranslation(Dictionary
            .GOALS_NOT_SELECTED.getKey()), languageController
            .getTranslation(Dictionary.GOALS_NEED_SELECTION.getKey()));
      } else {
        createGame(templates);
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Creates the game.
   * Creates the game with the selected template and goals.
   *
   * @param templates the combo box containing the templates
   */
  public void createGame(ComboBox<String> templates) {
    List<Goal> distinctGoals = goals.stream()
            .collect(Collectors.toMap(Object::toString,
                    goal -> goal,
                    (existingGoal, newGoal) -> existingGoal
            ))
            .values()
            .stream()
            .toList();

    goals.clear();
    goals.addAll(distinctGoals);
    Game game;
    try {
      String selectedTemplate = templates.getValue();
      Story story = fileHandlerController.loadTemplate(selectedTemplate + "." + fileType);
      game = new Game(playerController.getPlayer(), story, goals, story.getOpeningPassage());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e.getMessage());
    }

    try {
      fileHandlerController.saveGameJson(playerController.getPlayer().getName(), null, game);
      fileHandlerController.saveGameJson(playerController.getPlayer().getName(),
          "src/main/resources/initialGame/", game);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e.getMessage());
    }

    gameController.setGame(game);
    playerController.setPlayer(null);
  }

  /**
   * Uploads a game file from the user's computer.
   */
  public void uploadGameFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(languageController.getTranslation(Dictionary.UPLOAD_GAME_FILE.getKey()));
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PATHS Files", "*.paths")
    );

    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      String extension = selectedFile.getName().substring(selectedFile
          .getName().lastIndexOf(".") + 1);

      if (extension.equals("paths")) {
        try {
          addSavedGame(selectedFile.getName(), selectedFile);
        } catch (IOException e) {
          throw new RuntimeException(languageController.getTranslation(Dictionary
              .ERROR_UPLOADING_GAME_FILE.getKey()));
        }
      }
    }
  }

  /**
   * Add saved game.
   * Adds the saved game to the resources directory.
   *
   * @param fileName     the file name of the saved game
   * @param selectedFile the selected file to be added
   * @throws IOException the io exception thrown if the file cannot be added
   */
  public void addSavedGame(String fileName, File selectedFile) throws IOException {
    // Get the path to the appropriate resources directory based on the fileType
    Path resourcesPath = Paths.get("src/main/resources/templates");

    // Create the resources directory if it doesn't exist
    if (!Files.exists(resourcesPath)) {
      Files.createDirectories(resourcesPath);
    }

    // Copy the selected file to the appropriate resources directory
    Path sourcePath = selectedFile.toPath();
    Path destPath = resourcesPath.resolve(fileName);
    Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
  }

  /**
   * Gets the templates.
   * Gets the templates from the resources directory.
   *
   * @param templates the templates combo box
   * @return the set of templates
   */
  public Set<String> addTemplates(Set<String> templates) {
    Set<String> templateNames = new HashSet<>();
    for (String template : templates) {
      if (template.endsWith(fileType)) {
        templateNames.add(template.replace(".paths", ""));
      }
    }
    return templateNames;
  }
}
