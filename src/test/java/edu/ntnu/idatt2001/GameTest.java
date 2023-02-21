package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.goals.Goal;
import edu.ntnu.idatt2001.goals.ScoreGoal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

  @Nested
  public class ExceptionHandling {
    @Test
    @DisplayName("Test that constructor throws NullPointerException when player is null")
    void testThatConstructorThrowsNullPointerException() {
      Player player = null;
      Story story = new Story("Title", new Passage("title", "content"));
      List<Goal> goals = new ArrayList<>();
      assertThrows(NullPointerException.class, () -> new Game(player,story,goals));
    }
    @Test
    @DisplayName("Test that constructor throws NullPointerException when story is null")
    void testThatBeginReturnsOpeningPassage(){
      Player player = new Player("name", 50, 10, 30);
      Passage openingPassage = new Passage("title", "content");
      Story story = new Story("title", openingPassage);
      List<Goal> goals = new ArrayList<>();

      Game game = new Game(player, story, goals);
      goals.add(new ScoreGoal(20));

      assertEquals(openingPassage, game.begin());
    }
  }

 @Nested
 public class ReturnValues {

   @Test
   @DisplayName("Test that go returns passage")
   void testThatGoReturnsPassage() {
     Player player = new Player("name", 50, 10, 30);
     Passage openingPassage = new Passage("title", "content");
     Story story = new Story("title", openingPassage);
     List<Goal> goals = new ArrayList<>();
     Passage passage = new Passage("title2", "content2");
     goals.add(new ScoreGoal(20));
     Game game = new Game(player, story, goals);
     game.getStory().addPassage(passage);

     assertEquals(passage, game.go(new Link(passage.getTitle(), passage.getTitle())));
   }

   @Test
   @DisplayName("Test that getStory returns correct story")
   void testThatGetStoryReturnsCorrect() {
     Player player = new Player("name", 50, 10, 30);
     Passage openingPassage = new Passage("title", "content");
     Story story = new Story("title", openingPassage);
     List<Goal> goals = new ArrayList<>();
     goals.add(new ScoreGoal(20));
     Game game = new Game(player, story, goals);

     assertEquals(story, game.getStory());
   }

   @Test
   @DisplayName("Test that getGoals returns correct goals")
   void testThatGetGoalsReturnsCorrect() {
     Player player = new Player("name", 50, 10, 30);
     Passage openingPassage = new Passage("title", "content");
     Story story = new Story("title", openingPassage);
     List<Goal> goals = new ArrayList<>();
     goals.add(new ScoreGoal(20));
     Game game = new Game(player, story, goals);

     assertEquals(goals, game.getGoals());
   }

   @Test
   @DisplayName("Test that getPlayers returns correct player")
   void testThatGetPlayersReturnsCorrect() {
     Player player = new Player("name", 50, 10, 30);
     Passage openingPassage = new Passage("title", "content");
     Story story = new Story("title", openingPassage);
     List<Goal> goals = new ArrayList<>();
     goals.add(new ScoreGoal(20));
     Game game = new Game(player, story, goals);

     assertEquals(player, game.getPlayer());
   }
 }
}

