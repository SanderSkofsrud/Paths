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

  Player player;
  List<String> inventory;
  String test;

  @BeforeEach
  void setUp() {
    player = new Player("test", 10, 30, 50);
    inventory = new ArrayList<>();
    test = "Item";
  }

  /**
   * Test class for constructor
   */
  @Nested
  public class ConstructorTest {
    /**
     * Test that constructor constructs object with valid values and indirectly test that getters work
     */
    @Test
    void testThatConstructorConstructsObject() {
      assertEquals("test", player.getName());
      assertEquals(10, player.getHealth());
      assertEquals(10, player.getHealth());
      assertEquals(30, player.getScore());
      assertEquals(50, player.getGold());
      assertEquals(inventory, player.getInventory());
    }

    /**
     * Test that constructor throws IllegalArgumentException when health is less than zero
     */
    @Test
    @DisplayName("Test that constructor throws IllegalArgumentException when health is less than zero")
    void testThatConstructorThrowsNullPointerException() {
      int health = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player("name", health, 10,20));
    }
    /**
     * Test that constructor throws NullPointerException when name is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when name is null")
    void testThatConstructorThrowsIllegalArgumentException() {
      String name = null;
      assertThrows(NullPointerException.class, () -> new Player(name,10,10,10));
    }

    /**
     * Test that constructor throws IllegalArgumentException when name is blank
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when name is blank")
    void testThatConstructorThrowsNUllPointerExceptionWhenNameIsNull() {
      String name = "";
      assertThrows(NullPointerException.class, () -> new Player(name, 10,10,10));
    }

    /**
     * Test that constructor throws IllegalArgumentException when score is less than zero
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
   * Test that exception is thrown when invalid values are used
   */
  @Nested
  public class ExceptionHandling {

    /**
     * Test that exception is not thrown when valid values are used
     */
    @Test
    @DisplayName("Test that exception is not thrown when valid values are used")
    void testThatAddMethodsWorksWhenValidValuesAreUsed() {
      assertDoesNotThrow(() -> player.addHealth(10));
      assertDoesNotThrow(() -> player.addScore(10));
      assertDoesNotThrow(() -> player.addGold(10));
    }

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
     * Test that add methods work and indirectly test getters
     */
    @Test
    @DisplayName("Test that add methods work")
    void testThatAddMethodsWork() {
      player.addGold(10);
      player.addHealth(10);
      player.addScore(10);
      assertEquals("test", player.getName());
      assertEquals(20, player.getHealth());
      assertEquals(40, player.getScore());
      assertEquals(60, player.getGold());
    }
  }
}

