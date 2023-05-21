package edu.ntnu.idatt2001.paths.models.utility.paths;

import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.goals.GoalEnum;
import edu.ntnu.idatt2001.paths.models.goals.GoalFactory;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.utility.paths.PathsReader;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
          fileHandler.loadStory(file);
        });
      }

      @Test
      @DisplayName("Test that loadGame throws IllegalArgumentException when file has wrong format")
      void testThatLoadGameThrowsIllegalArgumentException2() {
        PathsReader fileHandler = new PathsReader();
        String file = "src/test/resources/paths/testWrong/testWrong.paths";
        assertThrows(IllegalArgumentException.class, () -> {
          fileHandler.loadStory(file);
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
        Story story = fileHandler.loadStory("src/test/resources/paths/test1/test1.paths");
        assertEquals(Story.class, story.getClass());
      }

      /**
       * Test that read from file returns a story with correct values.
       */
      @Test
      @DisplayName("Test that readFromFile returns a story with correct values")
      void testThatReadFromFileReturnsAStoryWithCorrectValues() {
        PathsReader fileHandler = new PathsReader();
        Story story = null;
        try {
          story = fileHandler.loadStory("src/test/resources/paths/test1/test1.paths");
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        assertEquals("""
                Haunted House

                ::Beginnings
                You are in a small room
                [Try open the door](Another room)

                ::Another room
                The door opens to another room
                [Open the book](The book of spells)
                [Go back](Beginnings)
                """, story.toString());
      }

      /**
       * Test that read from file throws null pointer exception.
       */
      @Test
      @DisplayName("Test that readFromFile throws NullPointerException when file path is null")
      void testThatReadFromFileThrowsNullPointerException() {
        PathsReader fileHandler = new PathsReader();
        String string = null;
        assertThrows(NullPointerException.class, () -> {
          fileHandler.loadStory(string);
        });
      }
    }
  }
}
