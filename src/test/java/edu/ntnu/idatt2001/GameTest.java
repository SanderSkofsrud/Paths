package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.goals.Goal;
import edu.ntnu.idatt2001.goals.ScoreGoal;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for Game
 */

public class GameTest {
  String name = "";
  int score;
  int health;
  int gold;
  int minimumScore;
  String title = "";
  String content = "";

  @BeforeEach
  void setUp() {
    name = "name";
    score = 50;
    health = 10;
    gold = 30;
    minimumScore = 20;
    title = "title";
    content = "content";
  }

  /**
   * Test class for constructor
   */
  @Nested
  public class constructor {
    /**
     * Test that constructor constructs object
     */
    @Test
    @DisplayName("Test that constructor constructs object")
    void testThatConstructorConstructsObject() {
      Player player = new Player(name, health, score, gold);
      Story story = new Story(title, new Passage(title, content));
      List<Goal> goals = new ArrayList<>();
      Game game = new Game(player, story, goals);
      assertEquals(Game.class, game.getClass());
    }
  }
  /**
   * Test class for exception handling
   */
  @Nested
  public class ExceptionHandling {
    /**
     * Test that constructor throws NullPointerException when player is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when player is null")
    void testThatConstructorThrowsNullPointerException() {
      Player player = null;
      Story story = new Story("Title", new Passage("title", "content"));
      List<Goal> goals = new ArrayList<>();
      assertThrows(NullPointerException.class, () -> new Game(player,story,goals));
    }
    /**
     * Test that constructor throws NullPointerException when story is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when story is null")
    void testThatBeginReturnsOpeningPassage(){
      Player player = new Player(name, health, score, gold);
      Passage openingPassage = new Passage(title, content);
      Story story = new Story(title, openingPassage);
      List<Goal> goals = new ArrayList<>();

      Game game = new Game(player, story, goals);
      goals.add(new ScoreGoal(minimumScore));

      assertEquals(openingPassage, game.begin());
    }
    /**
     * Test that constructor throws NullPointerException when goals is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when goals is null")
    void testThatConstructorThrowsNullPointerExceptionWhenGoalsIsNull() {
      Player player = new Player(name, health, score, gold);
      Story story = new Story(title, new Passage(title, content));
      List<Goal> goals = null;
      assertThrows(NullPointerException.class, () -> new Game(player,story,goals));
    }

    /**
     * Test that go throws NullPointerException when link is null
     */
    @Test
    @DisplayName("Test that go throws NullPointerException when link is null")
    void testThatGoThrowsNullPointerException() {
      Player player = new Player(name, health, score, gold);
      Passage openingPassage = new Passage(title, content);
      Story story = new Story(title, openingPassage);
      List<Goal> goals = new ArrayList<>();
      goals.add(new ScoreGoal(minimumScore));
      Game game = new Game(player, story, goals);

      assertThrows(NullPointerException.class, () -> game.go(null));
    }
  }

  /**
   * Test class for return values
   */
 @Nested
 public class ReturnValues {

   /**
    * Test that go returns passage
    */
   @Test
   @DisplayName("Test that go returns passage")
   void testThatGoReturnsPassage() {
     Player player = new Player(name, health, score, gold);
     Passage openingPassage = new Passage(title, content);
     Story story = new Story(title, openingPassage);
     List<Goal> goals = new ArrayList<>();
     Passage passage = new Passage("title2", "content2");
     goals.add(new ScoreGoal(minimumScore));
     Game game = new Game(player, story, goals);
     game.getStory().addPassage(passage);

     assertEquals(passage, game.go(new Link(passage.getTitle(), passage.getTitle())));
   }

    /**
      * Test that begin returns opening passage
      */
   @Test
   @DisplayName("Test that begin returns opening passage")
    void testThatBeginReturnsOpeningPassage() {
      Player player = new Player(name, health, score, gold);
      Passage openingPassage = new Passage(title, content);
      Story story = new Story(title, openingPassage);
      List<Goal> goals = new ArrayList<>();
      goals.add(new ScoreGoal(minimumScore));
      Game game = new Game(player, story, goals);

      assertEquals(openingPassage, game.begin());
    }

    /**
     * Test that getStory returns correct player
     */
   @Test
   @DisplayName("Test that getStory returns correct story")
   void testThatGetStoryReturnsCorrect() {
     Player player = new Player(name, health, score, gold);
     Passage openingPassage = new Passage(title, content);
     Story story = new Story(title, openingPassage);
     List<Goal> goals = new ArrayList<>();
     goals.add(new ScoreGoal(minimumScore));
     Game game = new Game(player, story, goals);

     assertEquals(story, game.getStory());
   }
   /**
    * Test that getGoals returns correct player
    */
   @Test
   @DisplayName("Test that getGoals returns correct goals")
   void testThatGetGoalsReturnsCorrect() {
     Player player = new Player(name, health, score, gold);
     Passage openingPassage = new Passage(title, content);
     Story story = new Story(title, openingPassage);
     List<Goal> goals = new ArrayList<>();
     goals.add(new ScoreGoal(minimumScore));
     Game game = new Game(player, story, goals);

     assertEquals(goals, game.getGoals());
   }

    /**
      * Test that getPlayers returns correct player
      */
   @Test
   @DisplayName("Test that getPlayers returns correct player")
   void testThatGetPlayersReturnsCorrect() {
     Player player = new Player(name, health, score, gold);
     Passage openingPassage = new Passage(title, content);
     Story story = new Story(title, openingPassage);
     List<Goal> goals = new ArrayList<>();
     goals.add(new ScoreGoal(minimumScore));
     Game game = new Game(player, story, goals);

     assertEquals(player, game.getPlayer());
   }
 }
}

