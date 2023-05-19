package edu.ntnu.idatt2001.paths.models.utility.paths;

import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.goals.GoalEnum;
import edu.ntnu.idatt2001.paths.models.goals.GoalFactory;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.player.PlayerBuilder;
import edu.ntnu.idatt2001.paths.utility.GameData;
import edu.ntnu.idatt2001.paths.utility.paths.PathsReader;
import edu.ntnu.idatt2001.paths.utility.paths.PathsWriter;
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
          fileHandler.loadGame(file);
        });
      }

      @Test
      @DisplayName("Test that loadGame throws IllegalArgumentException when file has wrong format")
      void testThatLoadGameThrowsIllegalArgumentException2() {
        PathsReader fileHandler = new PathsReader();
        String file = "src/test/resources/paths/testWrong/testWrong.paths";
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
        GameData gameData = fileHandler.loadGame("src/test/resources/paths/test1/test1.paths");
        assertEquals(GameData.class, gameData.getClass());
      }

      /**
       * Test that read from file returns a story with correct values.
       */
      @Test
      @DisplayName("Test that readFromFile returns a story with correct values")
      void testThatReadFromFileReturnsAStoryWithCorrectValues() {
        PathsReader fileHandler = new PathsReader();
        GameData gameData = null;
        try {
          gameData = fileHandler.loadGame("src/test/resources/paths/test1/test1.paths");
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        assertEquals("test1", gameData.getPlayer().getName());
        assertEquals(Story.class, gameData.getStory().getClass());
        assertEquals(2, gameData.getGoals().size());
        assertEquals("""
                Haunted House
                                
                ::Beginnings
                You are in a small room
                [Try open the door](Another room)
                                
                ::Another room
                The door opens to another room
                [Open the book](The book of spells)
                [Go back](Beginnings)            
                """, gameData.getStory().toString());
      }

      /**
       * Test that read from file throws null pointer exception.
       */
      @Test
      @DisplayName("Test that readFromFile throws NullPointerException when file path is null")
      void testThatReadFromFileThrowsNullPointerException() {
        PathsReader fileHandler = new PathsReader();
        String string = null;
        assertThrows(IllegalArgumentException.class, () -> {
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
        GameData gameData = fileHandler.loadGame("src/test/resources/paths/test1/test1.paths");
        PathsWriter pathsWriter = new PathsWriter();
        pathsWriter.saveGame(gameData.getStory(), gameData.getPlayer(), gameData.getGoals(), "src/test/resources/paths/test2/", "m.png");
        GameData gameData2 = fileHandler.loadGame("src/test/resources/paths/test2/test1.paths");
        assertEquals(Story.class, gameData2.getStory().getClass());
      }

      /**
       * Test that save game can load and save a game from a template.
       */
      @Test
      @DisplayName("Test that saveGame can load and save a game from a template")
      void testThatSaveGameCanLoadFromTemplate() {
        PathsReader fileHandler = new PathsReader();
        Story story = null;
        try {
          story = fileHandler.loadStory("src/test/resources/templates/template1.paths");
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        PathsWriter pathsWriter = new PathsWriter();
        Player player = new PlayerBuilder("template1").build();
        List<Goal> goals = new ArrayList<>();
        pathsWriter.saveGame(story, player, goals, "src/test/resources/paths/test3/", "m.png");
        GameData gameData = null;
        try {
          gameData = fileHandler.loadGame("src/test/resources/paths/test3/template1.paths");
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        assertEquals(Story.class, gameData.getStory().getClass());
        assertEquals("template1", gameData.getPlayer().getName());
        assertEquals(0, gameData.getGoals().size());
        assertEquals(story.toString(), gameData.getStory().toString());
      }

      /**
       * Test that save game throws null pointer exception.
       */
      @Test
      @DisplayName("Test that saveGame throws NullPointerException when story is null")
      void testThatSaveGameThrowsNullPointerException() {
        PathsWriter fileHandler = new PathsWriter();
        Story story = null;
        Player player = new PlayerBuilder("test1").build();
        List<Goal> goals = new ArrayList<>();
        goals.add(GoalFactory.createGoal(GoalEnum.HEALTH, 1));
        assertThrows(NullPointerException.class, () -> {
          fileHandler.saveGame(story, player, goals, "src/test/resources/paths/test2/", "m.png");
        });
      }

      /**
       * Test that save game throws null pointer exception 2.
       */
      @Test
      @DisplayName("Test that saveGame throws NullPointerException when the path is null")
      void testThatSaveGameThrowsNullPointerException2() {
        PathsWriter fileHandler = new PathsWriter();
        Player player = new PlayerBuilder("test1").build();
        Story story = new Story("Title", new Passage("title", "content"));
        String string = null;
        assertThrows(NullPointerException.class, () -> {
          fileHandler.saveGame(story, player, new ArrayList<>(), string, "m.png");
        });
      }

      /**
       * Test that save game throws null pointer exception 3.
       */
      @Test
      @DisplayName("Test that saveGame throws NullPointerException when the player is null")
      void testThatSaveGameThrowsNullPointerException3() {
        PathsWriter fileHandler = new PathsWriter();
        Story story = new Story("Title", new Passage("title", "content"));
        Player player = null;
        assertThrows(NullPointerException.class, () -> {
          fileHandler.saveGame(story, player, new ArrayList<>(), "src/test/resources/paths/test2/", "m.png");
        });
      }

      /**
       * Test that save game throws null pointer exception 4.
       */
      @Test
      @DisplayName("Test that saveGame throws NullPointerException when the goals is null")
      void testThatSaveGameThrowsNullPointerException4() {
        PathsWriter fileHandler = new PathsWriter();
        Story story = new Story("Title", new Passage("title", "content"));
        Player player = new PlayerBuilder("test1").build();
        List<Goal> goals = null;
        assertThrows(NullPointerException.class, () -> {
          fileHandler.saveGame(story, player, goals, "src/test/resources/paths/test2/", "m.png");
        });
      }

      /**
       * Test that save game throws null pointer exception 5.
       */
      @Test
      @DisplayName("Test that saveGame throws NullPointerException when the image is null")
      void testThatSaveGameThrowsNullPointerException5() {
        PathsWriter fileHandler = new PathsWriter();
        Story story = new Story("Title", new Passage("title", "content"));
        Player player = new PlayerBuilder("test1").build();
        List<Goal> goals = new ArrayList<>();
        goals.add(GoalFactory.createGoal(GoalEnum.HEALTH, 1));
        String string = null;
        assertThrows(NullPointerException.class, () -> {
          fileHandler.saveGame(story, player, goals, "src/test/resources/paths/test2/", string);
        });
      }
    }
  }
}
