package edu.ntnu.idatt2001;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for Player
 */

public class PlayerTest {

  Player player = new Player("test", 10, 30, 50);
  List<String> inventory = new ArrayList<>();
  String test = "Item";

  /**
   * Test constructor
   */
  @Nested
  public class ConstructorTest {
    /**
     * Test that constructor constructs object
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException")
    void testThatConstructorThrowsNullPointerException() {
      int health = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player("name", health, 10,20));
    }
  }

  /**
   * Test that exception is thrown when invalid values are used
   */
  @Nested
  public class ExceptionHandling {

    /**
     * Test that addHealth can not set negative value
     */
    @Test
    @DisplayName("Test that addHealth can not set negative value")
    void testThatAddHealthCanNotSetNegativeValue() {
      assertThrows(IllegalArgumentException.class, () -> player.addHealth(-20));
    }

    /**
     * Test that addScore can not set negative value
     */
    @Test
    @DisplayName("Test that addScore can not set negative value")
    void testThatAddScoreCanNotSetNegativeValue() {
      assertThrows(IllegalArgumentException.class, () -> player.addScore(-200));
    }

    /**
     * Test that addGold can not set negative value
     */
    @Test
    @DisplayName("Test that addGold can not set negative value")
    void testThatAddGoldCanNotSetNegativeValue() {
      assertThrows(IllegalArgumentException.class, () -> player.addGold(-200));
    }

    /**
     * Test that addToInventory can not set null value
     */
    @Test
    @DisplayName("Test that addToInventory can not set null value")
    void testThatAddToInventoryCanNotSetNullValue() {
      assertThrows(NullPointerException.class, () -> player.addToInventory(null));
    }

    /**
     * Test that removeFromInventory can not set null value
     */
    @Test
    @DisplayName("Test that constructor throws IllegalArgumentException when health is less than zero")
    void testThatConstructorThrowsIllegalArgumentException() {
      int health = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player("test", health,10,10));
    }

    /**
     * Test that constructor throws IllegalArgumentException when name is null
     */
    @Test
    @DisplayName("Test that constructor throws IllegalArgumentException when name is null")
    void testThatConstructorThrowsIllegalArgumentExceptionWhenNameIsNull() {
      String name = null;
      assertThrows(NullPointerException.class, () -> new Player(name, 10,10,10));
    }

    /**
     * Test that constructor throws IllegalArgumentException when health is less than zero
     */
    @Test
    @DisplayName("Test that constructor throws IllegalArgumentException when score is less than zero")
    void testThatConstructorThrowsIllegalArgumentExceptionWhenScoreIsLessThanZero() {
      int score = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player("name", 10, score,10));
    }

    /**
     * Test that constructor throws IllegalArgumentException when gold is less than zero
     */
    @Test
    @DisplayName("Test that constructor throws IllegalArgumentException when gold is less than zero")
    void testThatConstructorThrowsIllegalArgumentExceptionWhenGoldIsLessThanZero() {
      int gold = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player("name", 10, 10,gold));
    }
  }

  /**
   * Test that add-methods work
   */
  @Nested
  public class addMethods {

    /**
     * Test that Items will be added to inventory
     */
    @Test
    @DisplayName("Test that Items will be added to inventory")
    void testThatItemsAddToInventory() {
      player.addToInventory("item");
      assertEquals(1, player.getInventory().size());
    }

    /**
     * Test that gold will be added
     */
    @Test
    @DisplayName("Test that addGold works")
    void testThatAddGoldWorks() {
      player.addGold(10);
      assertEquals(player.getGold(), 60);
    }

    /**
     * Test that addScore works
     */
    @Test
    @DisplayName("Test that addScore works")
    void testThatAddScoreWorks() {
      player.addScore(10);
      assertEquals(player.getScore(), 40);
    }

    /**
     * Test that addHealth works
     */
    @Test
    @DisplayName("Test that addHealth works")
    void testThatAddHealthWorks() {
      player.addHealth(10);
      assertEquals(player.getHealth(), 20);
    }
  }


  /**
   * Test that return values are correct
   */
  @Nested
  public class ReturnValues {
    /**
     * Test that getName returns correct
     */
    @Test
    @DisplayName("Test that getName returns correct")
    void testThatGetNameReturnsCorrect() {
      assertEquals(player.getName(), "test");
    }

    /**
     * Test that getHealth returns correct
     */
    @Test
    @DisplayName("Test that getHealth returns correct")
    void testThatGetHealthReturnsCorrect() {
      assertEquals(player.getHealth(), 10);
    }

    /**
     * Test that getScore returns correct
     */
    @Test
    @DisplayName("Test that getScore returns correct")
    void testThatGetScoreReturnsCorrect() {
      assertEquals(player.getScore(), 30);
    }

    /**
     * Test that getGold returns correct
     */
    @Test
    @DisplayName("Test that getGold returns correct")
    void testThatGetGoldReturnsCorrect() {
      assertEquals(player.getGold(), 50);
    }

    /**
     * Test that getInventory returns correct
     */
    @Test
    @DisplayName("Test that getInventory returns correct")
    void testThatGetInventoryReturnsCorrect() {
      assertEquals(player.getInventory(), inventory);
    }
  }
}

