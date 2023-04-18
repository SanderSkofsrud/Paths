package edu.ntnu.idatt2001.paths.models.utility;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.goals.HealthGoal;
import edu.ntnu.idatt2001.paths.utility.FileHandler;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type File handler test.
 */
public class FileHandlerTest {

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {

  }

  /**
   * The type Constructor.
   */
  @Nested
  class constructor {
    /**
     * Test that constructor constructs object.
     */
    @Test
    @DisplayName("Test that constructor constructs object")
    void testThatConstructorConstructsObject() {
      FileHandler fileHandler = new FileHandler();
      assertEquals(FileHandler.class, fileHandler.getClass());
    }

    /**
     * Test that constructor throws null pointer exception.
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when player is null")
    void testThatConstructorThrowsNullPointerException() {
      FileHandler fileHandler = null;
      assertThrows(NullPointerException.class, () -> {
        fileHandler.getClass();
      });
    }
  }

  /**
   * The type Load game.
   */
  @Nested
  class loadGame {

    /**
     * Test that load game throws illegal argument exception when file is not empty.
     */
    @Test
    @DisplayName("Test that loadGame throws IllegalArgumentException when file is empty")
    void testThatLoadGameThrowsIllegalArgumentException() {
      FileHandler fileHandler = new FileHandler();
      String file = "";
      assertThrows(IllegalArgumentException.class, () -> {
        fileHandler.loadGame(file);
      });
    }
    @Test
    @DisplayName("Test that loadGame throws IllegalArgumentException when file has wrong format")
    void testThatLoadGameThrowsIllegalArgumentException2() {
      FileHandler fileHandler = new FileHandler();
      String file = "src/main/resources/paths/storyWrong.paths";
      assertThrows(IllegalArgumentException.class, () -> {
        fileHandler.loadGame(file);
      });
    }
    /**
     * Test that read from file returns a story.
     *
     * @throws FileNotFoundException the file not found exception
     */
    @Test
    @DisplayName("Test that readFromFile returns a story")
    void testThatReadFromFileReturnsAStory() throws FileNotFoundException {
      FileHandler fileHandler = new FileHandler();
      Game game = fileHandler.loadGame("src/main/resources/paths/story.paths");
      assertEquals(Game.class, game.getClass());
    }

    /**
     * Test that read from file throws null pointer exception.
     */
    @Test
    @DisplayName("Test that readFromFile throws NullPointerException when player is null")
    void testThatReadFromFileThrowsNullPointerException() {
      FileHandler fileHandler = new FileHandler();
      String string = null;
      assertThrows(NullPointerException.class, () -> {
        fileHandler.loadGame(string);
      });
    }
  }

  /**
   * The type Save game.
   */
  @Nested
  class saveGame {
    /**
     * Test that save game returns a story.
     *
     * @throws FileNotFoundException the file not found exception
     */
    @Test
    @DisplayName("Test that saveGame returns a story")
    void testThatSaveGameReturnsAStory() throws FileNotFoundException {
      FileHandler fileHandler = new FileHandler();
      Game game = fileHandler.loadGame("src/main/resources/paths/story.paths");
      fileHandler.saveGame(game, "src/main/resources/paths/story1.paths");
      Game game1 = fileHandler.loadGame("src/main/resources/paths/story1.paths");
      assertEquals(Game.class, game1.getClass());
    }

    /**
     * Test that save game throws null pointer exception.
     */
    @Test
    @DisplayName("Test that saveGame throws NullPointerException when player is null")
    void testThatSaveGameThrowsNullPointerException() {
      FileHandler fileHandler = new FileHandler();
      Game game = null;
      assertThrows(NullPointerException.class, () -> {
        fileHandler.saveGame(game, "src/main/resources/paths/story1.paths");
      });
    }

    /**
     * Test that save game throws null pointer exception 2.
     */
    @Test
    @DisplayName("Test that saveGame throws NullPointerException when player is null")
    void testThatSaveGameThrowsNullPointerException2() {
      FileHandler fileHandler = new FileHandler();
      Player player = new Player.Builder("name").build();
      Story story = new Story("Title", new Passage("title", "content"));
      List<Goal> goals = new ArrayList<>();
      goals.add(new HealthGoal(10));
      Game game = new Game(player, story, goals);
      String string = null;
      assertThrows(NullPointerException.class, () -> {
        fileHandler.saveGame(game, string);
      });
    }
  }
}
