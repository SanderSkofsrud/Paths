package edu.ntnu.idatt2001.utility;

import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Story;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

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
  public class constructor {
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
  public class loadGame {
    /**
     * Test that read from file returns a story.
     *
     * @throws FileNotFoundException the file not found exception
     */
    @Test
    @DisplayName("Test that readFromFile returns a story")
    void testThatReadFromFileReturnsAStory() throws FileNotFoundException {
      FileHandler fileHandler = new FileHandler();
      Story story = fileHandler.loadGame("src/main/resources/paths/story.paths");
      assertEquals(Story.class, story.getClass());
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
  public class saveGame {
    /**
     * Test that save game returns a story.
     *
     * @throws FileNotFoundException the file not found exception
     */
    @Test
    @DisplayName("Test that saveGame returns a story")
    void testThatSaveGameReturnsAStory() throws FileNotFoundException {
      FileHandler fileHandler = new FileHandler();
      Story story = fileHandler.loadGame("src/main/resources/paths/story.paths");
      fileHandler.saveGame(story, "src/main/resources/paths/story1.paths");
      Story story1 = fileHandler.loadGame("src/main/resources/paths/story1.paths");
      assertEquals(Story.class, story1.getClass());
    }

    /**
     * Test that save game throws null pointer exception.
     */
    @Test
    @DisplayName("Test that saveGame throws NullPointerException when player is null")
    void testThatSaveGameThrowsNullPointerException() {
      FileHandler fileHandler = new FileHandler();
      Story story = null;
      assertThrows(NullPointerException.class, () -> {
        fileHandler.saveGame(story, "src/main/resources/story1.paths");
      });
    }

    /**
     * Test that save game throws null pointer exception 2.
     */
    @Test
    @DisplayName("Test that saveGame throws NullPointerException when player is null")
    void testThatSaveGameThrowsNullPointerException2() {
      FileHandler fileHandler = new FileHandler();
      Story story = new Story("Title", new Passage("title", "content"));
      String string = null;
      assertThrows(NullPointerException.class, () -> {
        fileHandler.saveGame(story, string);
      });
    }
  }
}
