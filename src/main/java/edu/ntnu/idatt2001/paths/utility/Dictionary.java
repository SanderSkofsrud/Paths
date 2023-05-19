package edu.ntnu.idatt2001.paths.utility;

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
  GAME_HELP_TEXT("""
          *The top left bar shows your current health, gold, score and inventory

          *The goals progress bar shows your current progress in the game according to your goals

          *The information bar describes the current passage you are in

          *To play the game you must choose one of the options that appear in the bottom of the screen
          
          *You can exit the game at any time by clicking the exit button
          
          *You can return to home by clicking on the home button
          """),
  HOME("Home"),
  RETURN_TO_HOME("You will lose all progress if you return \nto home without saving."),
  SAVE_HOME("Save & home"),

  UPLOAD_FILE("Upload file"),

  GAME_OVER("Game over"),

  BROKEN_LINK("Broken link"),

  DIE("You died"),

  EMPTY_GOLD("You ran out of gold"),

  EMPTY_SCORE("Your score got too low"),

  LINK_BROKEN("You have selected a passage with a broken link"),

  ;
  private final String key;

  Dictionary(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}

