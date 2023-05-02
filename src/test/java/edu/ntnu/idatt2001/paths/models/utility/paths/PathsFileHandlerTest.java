package edu.ntnu.idatt2001.paths.models.utility.paths;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.utility.paths.PathsReader;
import edu.ntnu.idatt2001.paths.utility.paths.PathsWriter;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type File handler test.
 */
public class PathsFileHandlerTest {

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
  class pathsReader {

    @Nested
    class constructor {
      /**
       * Test that constructor constructs object.
       */
      @Test
      @DisplayName("Test that constructor constructs object")
      void testThatConstructorConstructsObject() {
        PathsReader fileHandler = new PathsReader();
        assertEquals(PathsReader.class, fileHandler.getClass());
      }

      /**
       * Test that constructor throws null pointer exception.
       */
      @Test
      @DisplayName("Test that constructor throws NullPointerException when player is null")
      void testThatConstructorThrowsNullPointerException() {
        PathsReader fileHandler = null;
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
        PathsReader fileHandler = new PathsReader();
        String file = "";
        assertThrows(IllegalArgumentException.class, () -> {
          fileHandler.loadGame(file);
        });
      }

      @Test
      @DisplayName("Test that loadGame throws IllegalArgumentException when file has wrong format")
      void testThatLoadGameThrowsIllegalArgumentException2() {
        PathsReader fileHandler = new PathsReader();
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
        PathsReader fileHandler = new PathsReader();
        //Game game = fileHandler.loadGame("src/main/resources/paths/story.paths");
        //assertEquals(Game.class, game.getClass());
        // TODO GAMEDATA
      }

      /**
       * Test that read from file throws null pointer exception.
       */
      @Test
      @DisplayName("Test that readFromFile throws NullPointerException when player is null")
      void testThatReadFromFileThrowsNullPointerException() {
        PathsReader fileHandler = new PathsReader();
        String string = null;
        assertThrows(NullPointerException.class, () -> {
          fileHandler.loadGame(string);
        });
      }
    }
  }

  @Nested
  class pathsWriter {
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
        PathsReader fileHandler = new PathsReader();
        Story story = fileHandler.loadStory("src/main/resources/paths/storyNoGame.paths");
        PathsWriter.saveGame(story, "src/main/resources/paths/story1.paths");
        Story story1 = fileHandler.loadStory("src/main/resources/paths/story1.paths");
        assertEquals(Story.class, story1.getClass());
      }

      /**
       * Test that save game throws null pointer exception.
       */
      @Test
      @DisplayName("Test that saveGame throws NullPointerException when player is null")
      void testThatSaveGameThrowsNullPointerException() {
        PathsWriter fileHandler = new PathsWriter();
        Story story = null;
        assertThrows(NullPointerException.class, () -> {
          fileHandler.saveGame(story, "src/main/resources/paths/story1.paths");
        });
      }

      /**
       * Test that save game throws null pointer exception 2.
       */
      @Test
      @DisplayName("Test that saveGame throws NullPointerException when player is null")
      void testThatSaveGameThrowsNullPointerException2() {
        PathsWriter fileHandler = new PathsWriter();
        Player player = new Player.Builder("name").build();
        Story story = new Story("Title", new Passage("title", "content"));
        String string = null;
        assertThrows(NullPointerException.class, () -> {
          fileHandler.saveGame(story, string);
        });
      }
    }
  }
}
