package edu.ntnu.idatt2001.paths.utility.json;

import com.google.gson.JsonParseException;
import edu.ntnu.idatt2001.paths.models.*;
import edu.ntnu.idatt2001.paths.models.actions.ActionEnum;
import edu.ntnu.idatt2001.paths.models.actions.ActionFactory;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.goals.GoalEnum;
import edu.ntnu.idatt2001.paths.models.goals.GoalFactory;
import edu.ntnu.idatt2001.paths.models.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFileHandlerTest {
  String name;
  int score;
  int health;
  int gold;
  int minimumScore;
  String title;
  String content;
  Player player;
  Story story;
  Passage passage;
  Link link;
  List<Goal> goals;
  Game game;
  @Nested
  class jsonReader {
    @Nested
    class constructor {
      /**
       * Test that constructor constructs object.
       */
      @Test
      @DisplayName("Test that constructor constructs object")
      void testThatConstructorConstructsObject() {
        JsonReader fileHandler = new JsonReader();
        assertEquals(JsonReader.class, fileHandler.getClass());
      }

      /**
       * Test that constructor throws null pointer exception.
       */
      @Test
      @DisplayName("Test that constructor throws NullPointerException when player is null")
      void testThatConstructorThrowsNullPointerException() {
        JsonReader fileHandler = null;
        assertThrows(NullPointerException.class, () -> {
          fileHandler.getClass();
        });
      }
    }

    @Nested
    class jsonReaderTest {
      /**
       * Test that load game loads a game and returns it.
       */
      @Test
      @DisplayName("Test that loadGame loads a game and returns it")
      void testThatLoadGameLoadsAGameAndReturnsIt() throws FileNotFoundException {
        JsonReader fileHandler = new JsonReader();
        Game game = fileHandler.loadGameJson("src/test/resources/json/test.json");
        Story story = game.getStory();
        Passage openingPassage = story.getOpeningPassage();
        Link link = openingPassage.getLinks().get(0);
        Passage anotherRoom = story.getPassage(link);
        Player player = game.getPlayer();
        List<Goal> goals = game.getGoals();
        assertEquals("Haunted House", story.getTitle());
        assertEquals("\n::"
                + "Beginnings"
                + "\n"
                + "You are in a small room"
                + "\n"
                + "["
                + "Try open the door"
                + "]("
                + "Another room"
                + ")\n", openingPassage.toString());
        assertEquals("\n::"
                + "Another room"
                + "\n"
                + "The door opens to another room"
                + "\n"
                + "["
                + "Open the book"
                + "]("
                + "The book of spells"
                + ")\n"
                + "["
                + "Go back"
                + "]("
                + "Beginnings"
                + ")\n", anotherRoom.toString());
        assertEquals("test", ((Player) player).getName());
        assertEquals(2, goals.size());
      }


      /**
       * Test that load game throws file not found exception.
       */
      @Test
      @DisplayName("Test that loadGame throws FileNotFoundException when file is not found")
      void testThatLoadGameThrowsFileNotFoundException() {
        JsonReader fileHandler = new JsonReader();
        assertThrows(FileNotFoundException.class, () -> {
          fileHandler.loadGameJson("src/test/resources/json/doesNotExist.json");
        });
      }

      @Test
      @DisplayName("Test with an invalid JSON file")
      void testWithInvalidJsonFile() {
        assertThrows(JsonParseException.class, () -> {
          JsonReader.loadGameJson("src/test/resources/json/invalid.json");
        });
      }
    }

    @Nested
    class JsonWriterTests {

      @BeforeEach
      void setup(){
        // Delete the output file if it exists
        File file = new File("src/test/resources/json/output.json");
        if (file.exists()) {
          file.delete();
        }
        name = "name";
        score = 50;
        health = 10;
        gold = 30;
        minimumScore = 60;
        title = "title";
        content = "content";
        player = new Player.PlayerBuilder(name).health(health).score(score).gold(gold).build();
        passage = new Passage(title, content);
        link = new Link("link", "title2");
        link.addAction(ActionFactory.createAction(ActionEnum.GOLD, "10"));
        story = new Story(title, passage);
        story.addPassage(new Passage("title2", "content2"));
        goals = new ArrayList<>();
        goals.add(GoalFactory.createGoal(GoalEnum.SCORE, minimumScore));
        game = new Game(player, story, goals, story.getOpeningPassage());
      }
      @Test
      @DisplayName("Test saving a Game object to a JSON file")
      void testSaveGameToJsonFile() throws IOException {
        String filePath = "src/test/resources/json/output.json";
        JsonWriter.saveGameJson(game, filePath);

        // Validate the file was created and is not empty
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        // Load the file and validate the Game object
        Game loadedGame = JsonReader.loadGameJson(filePath);
        assertEquals(story.getPassages().toString(), loadedGame.getStory().getPassages().toString());
        assertEquals(player.toString(), loadedGame.getPlayer().toString());
        assertEquals(goals.toString(), loadedGame.getGoals().toString());

        // Clean up the created file
        Files.delete(Path.of(filePath));
      }
      @Test
      @DisplayName("Test saving a Game object to a non-writable directory or file")
      void testSaveGameToNonWritableFile() {
        assertThrows(IllegalArgumentException.class, () -> {
          JsonWriter.saveGameJson(game, "/nonWritableDirectory/output.json");
        });
      }
    }
  }
}
