package edu.ntnu.idatt2001.paths.models;

import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.goals.HealthGoal;
import edu.ntnu.idatt2001.paths.models.goals.ScoreGoal;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for Game
 */

public class GameTest {
  String name;
  int score;
  int health;
  int gold;
  int minimumScore;
  String title;
  String content;

  @BeforeEach
  void setUp() {
    name = "name";
    score = 50;
    health = 10;
    gold = 30;
    minimumScore = 60;
    title = "title";
    content = "content";
  }

  /**
   * Test class for constructor
   */
  @Nested
  class constructor {
    /**
     * Test that constructor constructs object with valid values and indirectly test that getters work
     */
    @Test
    @DisplayName("Test that constructor constructs object")
    void testThatConstructorConstructsObject() {
      Player player = new Player.Builder(name).health(health).score(score).gold(gold).build();
      Story story = new Story(title, new Passage(title, content));
      List<Goal> goals = new ArrayList<>();
      goals.add(new ScoreGoal(minimumScore));
      Game game = new Game(player, story, goals);
      assertEquals(player, game.getPlayer());
      assertEquals(story, game.getStory());
      assertEquals(goals, game.getGoals());
    }

    /**
     * Test that constructor throws NullPointerException when player is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when player is null")
    void testThatConstructorThrowsNullPointerException() {
      Player player = null;
      Story story = new Story(title, new Passage(title, content));
      List<Goal> goals = new ArrayList<>();
      assertThrows(NullPointerException.class, () -> new Game(player,story,goals));
    }

    /**
     * Test that constructor throws NullPointerException when goals is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when goals is null")
    void testThatConstructorThrowsNullPointerExceptionWhenGoalsIsNull() {
      Player player = new Player.Builder(name).health(health).score(score).gold(gold).build();
      Story story = new Story(title, new Passage(title, content));
      List<Goal> goals = null;
      assertThrows(NullPointerException.class, () -> new Game(player,story,goals));
    }
    /**
     * Test that constructor throws NullPointerException when story is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when story is null")
    void testThatConstructorThrowsNullPointerExceptionWhenStoryIsNull() {
      Player player = new Player.Builder(name).health(health).score(score).gold(gold).build();
      Story story = null;
      List<Goal> goals = new ArrayList<>();
      assertThrows(NullPointerException.class, () -> new Game(player,story,goals));
    }

    /**
     * Test that constructor throws IllegalArgumentException when goals is empty
     */
    @Test
    @DisplayName("Test that constructor throws IllegalArgumentException when goals is empty")
    void testThatConstructorThrowsIllegalArgumentExceptionWhenGoalsIsEmpty() {
      Player player = new Player.Builder(name).health(health).score(score).gold(gold).build();
      Story story = new Story(title, new Passage(title, content));
      List<Goal> goals = new ArrayList<>();
      assertThrows(IllegalArgumentException.class, () -> new Game(player,story,goals));
    }

    /**
     * Test that constructor throws NullPointerException when goals contains null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when goals contains null")
    void testThatConstructorThrowsNullPointerExceptionWhenGoalsContainsNull() {
      Player player = new Player.Builder(name).health(health).score(score).gold(gold).build();
      Story story = new Story(title, new Passage(title, content));
      List<Goal> goals = new ArrayList<>();
      goals.add(null);
      assertThrows(NullPointerException.class, () -> new Game(player,story,goals));
    }
  }
  /**
   * Test class for begin method
   */
  @Nested
  public class begin {

    /**
     * Test that begin returns openingPassage
     */
    @Test
    @DisplayName("Test that begin return openingPassage")
    void testThatBeginReturnsOpeningPassage(){
      Player player = new Player.Builder(name).health(health).score(score).gold(gold).build();
      Passage openingPassage = new Passage(title, content);
      Story story = new Story(title, openingPassage);
      List<Goal> goals = new ArrayList<>();
      goals.add(new ScoreGoal(minimumScore));

      Game game = new Game(player, story, goals);
      goals.add(new HealthGoal(20));

      assertEquals(openingPassage, game.begin());
    }
  }

  /**
   * Test class for go method
   */
 @Nested
 class go {

    /**
     * Test that go returns passage
     */
    @Test
    @DisplayName("Test that go returns passage")
    void testThatGoReturnsPassage() {
      Player player = new Player.Builder(name).health(health).score(score).gold(gold).build();
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
     * Test that go returns endingPassage when a goal is reached
     */
    @Test
    @DisplayName("Test that go returns endingPassage when goal is reached")
    void testThatGoReturnsEndingPassage(){
      Player player = new Player.Builder(name).health(health).score(minimumScore).gold(gold).build();
      Passage openingPassage = new Passage(title, content);
      Story story = new Story(title, openingPassage);
      List<Goal> goals = new ArrayList<>();
      Passage passage = new Passage("title2", "content2");
      goals.add(new ScoreGoal(minimumScore));
      Game game = new Game(player, story, goals);
      game.getStory().addPassage(passage);
      game.go(new Link(passage.getTitle(), passage.getTitle()));
      assertEquals(story.getEndingPassage(), game.go(new Link(passage.getTitle(), passage.getTitle())));
    }

    /**
     * Test that go throws NullPointerException when link is null
     */
    @Test
    @DisplayName("Test that go throws NullPointerException when link is null")
    void testThatGoThrowsNullPointerException() {
      Player player = new Player.Builder(name).health(health).score(score).gold(gold).build();
      Passage openingPassage = new Passage(title, content);
      Story story = new Story(title, openingPassage);
      List<Goal> goals = new ArrayList<>();
      goals.add(new ScoreGoal(minimumScore));
      Game game = new Game(player, story, goals);

      assertThrows(NullPointerException.class, () -> game.go(null));
    }

  }
}

