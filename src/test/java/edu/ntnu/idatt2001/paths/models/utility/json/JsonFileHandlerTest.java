package edu.ntnu.idatt2001.paths.models.utility.json;

import edu.ntnu.idatt2001.paths.models.*;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.json.JsonReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonFileHandlerTest {
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
    class loadGame {
      /**
       * Test that load game loads a game and returns it.
       */
      @Test
      @DisplayName("Test that loadGame loads a game and returns it")
      void testThatLoadGameLoadsAGameAndReturnsIt() throws FileNotFoundException {
        JsonReader fileHandler = new JsonReader();
        Game game = fileHandler.loadGameJSON("src/test/resources/json/test.json");
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
          fileHandler.loadGameJSON("src/test/resources/json/doesNotExist.json");
        });
      }
    }
  }
}
