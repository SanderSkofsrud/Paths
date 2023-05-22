package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.goals.GoalEnum;
import edu.ntnu.idatt2001.paths.models.goals.GoalFactory;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.ShowAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static edu.ntnu.idatt2001.paths.models.goals.GoalEnum.*;

/**
 * The type Load game controller.
 * This class is responsible for loading games.
 * It is a singleton class, and can be accessed from anywhere in the program.
 * It is used to show the user a list of saved games, and to load the selected game.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class ChooseGoalsController {
  /**
   * The file type.
   */
  private String fileType;
  private final ObservableList<Goal> goals;
  private final GameController gameController = GameController.getInstance();
  private final FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  private final PlayerController playerController = PlayerController.getInstance();
  private final LanguageController languageController = LanguageController.getInstance();

  /**
   * Instantiates a new Load game controller.
   */
  public ChooseGoalsController() {
    this.goals = FXCollections.observableArrayList();
  }

  public Set<String> fetchTemplates(String fileType) {
    this.fileType = fileType;
    ObservableList<File> filesList = FXCollections.observableArrayList();
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
      e.printStackTrace();
    }
    return fileNames;
  }

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
      String selectedValue = languageController.translateToEnglish(selectedTranslatedValue.toUpperCase().replace(" GOAL", ""));
      GoalEnum type = GoalEnum.valueOf(selectedValue);
      Goal goal = null;
      if (type.equals(GoalEnum.INVENTORY)) {
        goal = GoalFactory.createInventoryGoal(stringValue);
      } else {
        goal = GoalFactory.createGoal(type, value);
      }

      boolean isGoalAlreadyAdded = false;
      for (Goal existingGoal : goals) {
        if (existingGoal.getClass().equals(goal.getClass()) && existingGoal.toString().equals(goal.toString())) {
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

  public void handlePredefinedGoals(CheckBox checkBox1, CheckBox checkBox2, CheckBox checkBox3, CheckBox checkBox4, CheckBox checkBox5, CheckBox checkBox6) {
    if (checkBox1.isSelected()) {
      goals.add(GoalFactory.createGoal(GOLD, 200));
    }
    if (checkBox2.isSelected()) {
      goals.add(GoalFactory.createGoal(SCORE, 100));
    }
    if (checkBox3.isSelected()) {
      goals.add(GoalFactory.createGoal(HEALTH,50));
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

  public void validateGame(ComboBox<String> templates) {
    if (goals.isEmpty() && templates.getValue() == null) {
      ShowAlert.showInformation(languageController.getTranslation(Dictionary.GOALS_AND_TEMPLATE_NOT_SELECTED.getKey()), languageController.getTranslation(Dictionary.GOALS_AND_TEMPLATE_NEED_SELECTION.getKey()));
    } else if (templates.getValue() == null) {
      ShowAlert.showInformation(languageController.getTranslation(Dictionary.TEMPLATE_NOT_SELECTED.getKey()), languageController.getTranslation(Dictionary.TEMPLATE_NEED_SELECTION.getKey()));
    } else if (goals.isEmpty()) {
      ShowAlert.showInformation(languageController.getTranslation(Dictionary.GOALS_NOT_SELECTED.getKey()), languageController.getTranslation(Dictionary.GOALS_NEED_SELECTION.getKey()));
    } else {
      createGame(templates);
    }
  }
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
      throw new RuntimeException(e);
    }

    fileHandlerController.saveGameJson(playerController.getPlayer().getName(), null, game);
    fileHandlerController.saveGameJson(playerController.getPlayer().getName(), "src/main/resources/initialGame/", game);

    gameController.setGame(game);
  }

  public void uploadGameFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Upload Game File");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PATHS Files", "*.paths")
    );

    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      System.out.println("Selected file: " + selectedFile.getName());
      String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);

      if (extension.equals("paths")) {
        try {
          addSavedGame(selectedFile.getName(), selectedFile);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

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

  public Set<String> addTemplates(Set<String> templates) {
    Set<String> templateNames = new HashSet<>();
    for (String template : templates) {
      if (!template.endsWith(fileType)) {
        templateNames.add(template.replace(".paths", ""));
      }
    }
    return templateNames;
  }
}
