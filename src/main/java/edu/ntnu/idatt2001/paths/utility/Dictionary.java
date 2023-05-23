package edu.ntnu.idatt2001.paths.utility;

/**
 * The enum Dictionary.
 * The Dictionary enum is used to store all the strings used in the
 * application to be able to change the language of the application.
 *
 * @author Helle R. and Sander S.
 * @version 1.1 22.05.2020
 */

public enum Dictionary {
  NEW_GAME("New game"),
  LOAD_GAME("Load game"),
  SELECT_NAME("Select name"),
  ENTER_NAME("Enter name"),
  SELECT_DIFFICULTY("Select difficulty"),
  EASY("Easy"),
  STANDARD("Standard"),
  HARD("Hard"),
  CUSTOM("Custom"),
  ENTER_HEALTH("Enter health"),
  ENTER_GOLD("Enter gold"),
  SELECT_HEALTH("Select health (Normal difficulty = 100)"),
  SELECT_GOLD("Select gold (Normal difficulty = 100)"),
  HEALTH_EASY("Health = 150 (Normal difficulty = 100)"),
  GOLD_EASY("Gold = 150 (Normal difficulty = 100)"),
  INVENTORY_EASY("* Sword and shield added to inventory"),
  HEALTH_STANDARD("Health = 100"),
  GOLD_STANDARD("Gold = 100"),
  INVENTORY_STANDARD("* Sword added to inventory"),
  HEALTH_HARD("Health = 50 (Normal difficulty = 100)"),
  GOLD_HARD("Gold = 0 (Normal difficulty = 100)"),
  HEALTH("Health"),
  GOLD("Gold"),
  SCORE("Score"),
  INVENTORY("Inventory"),
  SELECT_CHARACTER("Select character model"),
  CREATE_CHARACTER("Create Character"),
  SELECT_GOALS("Select goals"),
  GOLD_GOAL("Gold goal"),
  HEALTH_GOAL("Health goal"),
  INVENTORY_GOAL("Inventory goal"),
  SCORE_GOAL("Score goal"),
  STANDARD_GOALS("Standard goals:"),
  GOAL_1("Gold: 200"),
  GOAL_2("Score: 100"),
  GOAL_3("Health: 50"),
  GOAL_4("Inventory: Sword"),
  IMPOSSIBLE_GOALS("Impossible goals:"),
  GOAL_5_1("Gold: 200, Score: 100, Health: 50, "),

  GOAL_5_2("Inventory: Sword"),
  GOAL_6_1("Gold: 250, Score: 200, Health: 100, "),
  GOAL_6_2("Inventory: Sword, Potion, Shield"),
  CUSTOM_GOALS("Custom goals:"),
  SELECT_GOAL_TYPE("Select goal type"),
  ENTER_ITEM_NAME("item1, item2, item3, ..."),
  ENTER_GOAL_VALUE("Enter goal value"),
  ADD_GOAL("Add goal"),
  GOAL_TYPE("Goal type"),
  GOAL_VALUE("Goal value"),
  FILE_NAME("File name"),
  FILE_LOCATION("File location"),
  BROKEN_LINKS("Broken links"),
  NO_BROKEN_LINKS("No broken links"),
  MISSING_DATA("Missing data"),
  SOME_DATA_IS_MISSING("Some data is missing in the file"),
  DO_YOU_WANT_TO_CONTINUE("Do you want to continue and add this to the file"),
  CONTINUE("Continue"),
  CANCEL("Cancel"),
  MISSING_PLAYER_GOALS("Missing data: Player Goals"),
  MISSING_PLAYER("Missing data: Player"),
  MISSING_GOALS("Missing data: Goals"),
  EXIT("Exit"),
  CHOOSE_AN_OPTION("Choose an option"),
  LOSE_PROGRESS("You will lose all progress if you exit without saving"),
  LOSE_PROGRESS_HOME("You will lose all progress if you return \nto home without saving."),
  SAVE_EXIT("Save & exit"),
  GAME_HELP("Game help"),
  GAME_HELP_TEXT("*The top left bar shows your current health, gold, score and inventory"),
  GAME_HELP_TEXT_1("*The goals progress bar shows your current progress "
      + "in the game according to your goals "),
  GAME_HELP_TEXT_2("*The information bar describes the current passage you are in "),
  GAME_HELP_TEXT_3("*To play the game you must choose one of the options that "
      + "appear in the bottom of the screen "),
  GAME_HELP_TEXT_4("*You can exit the game at any time by clicking the exit button "),
  GAME_HELP_TEXT_5("*You can return to home by clicking on the home button "),
  HOME("Home"),
  RETURN_TO_HOME("You will lose all progress if you return \nto home without saving."),
  SAVE_HOME("Save & home"),
  UPLOAD_FILE("Upload file"),
  GAME_OVER("Game over"),
  BROKEN_LINK("Broken link"),
  LINK_BROKEN("You have selected a passage with a broken link"),
  SELECT_TEMPLATE("Select template"),
  START("Start"),
  GOALS_NEED_SELECTION("Goals need to be selected"),
  GOALS_NOT_SELECTED("Goals not selected"),
  TEMPLATE_NEED_SELECTION("A template needs to be selected"),
  TEMPLATE_NOT_SELECTED("Template not selected"),
  GOALS_AND_TEMPLATE_NOT_SELECTED("Goals and a template not selected"),
  GOALS_AND_TEMPLATE_NEED_SELECTION("Goals and a template need to be selected"),
  GOAL_ALREADY_ADDED("Goal already added"),
  THIS_GOAL_IS_ALREADY_ADDED("This goal is already added"),
  GOALS_IN_GAME("Goals: "),
  CREDITS("Summary and acknowledgements"),
  END_OF_GAME("Congratulations, you have completed the game!"),
  LOST_GAME("You have lost the game"),
  CREDITS_TEXT_1("Congratulations!"),
  CREDITS_TEXT_1_1("You have completed the game."),
  CREDITS_TEXT_2("Goals:"),
  CREDITS_TEXT_2_1("Goals achieved: "),
  CREDITS_TEXT_2_2("Goals failed: "),
  CREDITS_TEXT_2_3("Goal completion percentage: "),
  CREDITS_TEXT_3("Created by:"),
  CREDIT_TEXT_3_1("Helle R. and Sander S."),
  CREDITS_TEXT_4("A special thanks to:"),
  CREDITS_TEXT_4_1("The student assistants"),
  CREDITS_TEXT_4_2("The teachers"),
  CREDITS_TEXT_5("Sources:"),
  CREDITS_TEXT_5_1("Sounds:"),
  CREDITS_TEXT_5_2("Confirm sound: https://freesound.org/people/original_sound/sounds/366102/"),
  CREDITS_TEXT_5_3("Ambient noise: https://freesound.org/people/Robinhood76/sounds/96548/"),
  CREDITS_TEXT_6("Images:"),
  CREDITS_TEXT_6_1("Icons: https://fonts.google.com/icons"),
  CREDITS_TEXT_6_2("All other images were generated by DALL-E 2: https://labs.openai.com/"),
  CREDITS_TEXT_7("Thank you for playing!"),
  TEMPLATE("Choose a template"),
  UPLOAD_TEMPLATE("Uploaded template"),
  UPLOAD_TEMPLATE_SUCCESS("Template was uploaded successfully"),
  RESTART("Restart"),
  RESTART_GAME("Are you sure you want to restart the game?"),
  UPLOAD_GAME_FILE("Upload game file"),
  ERROR_UPLOADING_GAME_FILE("Error uploading game file"),
  INVALID_INPUT("Invalid input"),
  GOAL_AND_HEALTH_VALUES_MUST_BE_NUMBERS_ABOVE_ZERO(
      "Goal and health values must be numbers above zero"),
  GOAL_HEALTH_SCORE_GOALS_MUST_BE_POSITIVE("Goal, health and score goals must be positive"),
  SAME_GOAL_TWICE("You cannot add the same goal twice"),
  INVALID_GOAL_VALUE("Invalid goal value"),
  INVALID_INVENTORY_GOAL("Invalid inventory goal"),
  ;

  /**
   * The Key.
   * The key is the string that is used to fetch the correct string
   * to be displayed in the application.
   */
  private final String key;

  /**
   * Constructs a new Dictionary.
   *
   * @param key the key associated with the dictionary entry
   */
  Dictionary(String key) {
    this.key = key;
  }

  /**
   * Gets the key associated with the dictionary entry.
   *
   * @return the key
   */
  public String getKey() {
    return key;
  }
}

