package edu.ntnu.idatt2001.utility;

import edu.ntnu.idatt2001.Passage;
import edu.ntnu.idatt2001.Story;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type File handeler test.
 */
public class FileHandelerTest {

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
      FileHandeler fileHandeler = new FileHandeler();
      assertEquals(FileHandeler.class, fileHandeler.getClass());
    }

    /**
     * Test that constructor throws null pointer exception.
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when player is null")
    void testThatConstructorThrowsNullPointerException() {
      FileHandeler fileHandeler = null;
      assertThrows(NullPointerException.class, () -> {
        fileHandeler.getClass();
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
      FileHandeler fileHandeler = new FileHandeler();
      Story story = fileHandeler.loadGame("src/main/resources/story.paths");
      assertEquals(Story.class, story.getClass());
    }

    /**
     * Test that read from file throws null pointer exception.
     */
    @Test
    @DisplayName("Test that readFromFile throws NullPointerException when player is null")
    void testThatReadFromFileThrowsNullPointerException() {
      FileHandeler fileHandeler = new FileHandeler();
      String string = null;
      assertThrows(NullPointerException.class, () -> {
        fileHandeler.loadGame(string);
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
      FileHandeler fileHandeler = new FileHandeler();
      Story story = fileHandeler.loadGame("src/main/resources/story.paths");
      fileHandeler.saveGame(story, "src/main/resources/story1.paths");
      Story story1 = fileHandeler.loadGame("src/main/resources/story1.paths");
      assertEquals(Story.class, story1.getClass());
    }

    /**
     * Test that save game throws null pointer exception.
     */
    @Test
    @DisplayName("Test that saveGame throws NullPointerException when player is null")
    void testThatSaveGameThrowsNullPointerException() {
      FileHandeler fileHandeler = new FileHandeler();
      Story story = null;
      assertThrows(NullPointerException.class, () -> {
        fileHandeler.saveGame(story, "src/main/resources/story1.paths");
      });
    }

    /**
     * Test that save game throws null pointer exception 2.
     */
    @Test
    @DisplayName("Test that saveGame throws NullPointerException when player is null")
    void testThatSaveGameThrowsNullPointerException2() {
      FileHandeler fileHandeler = new FileHandeler();
      Story story = new Story("Title", new Passage("title", "content"));
      String string = null;
      assertThrows(NullPointerException.class, () -> {
        fileHandeler.saveGame(story, string);
      });
    }
  }
}
