package edu.ntnu.idatt2001.paths.models;

import edu.ntnu.idatt2001.paths.models.player.Player;
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
    player = new Player.PlayerBuilder("test").health(10).score(30).gold(50).build();
    inventory = new ArrayList<>();
    test = "Item";
  }

  /**
   * Test class for constructor
   */
  @Nested
  class ConstructorTest {
    /**
     * Test that constructor constructs object with valid values and indirectly test that getters work
     */
    @Test
    @DisplayName("Test that builder constructs object with given values")
    void testThatBuilderConstructsObjectWithGivenValues() {
      assertEquals("test", player.getName());
      assertEquals(10, player.getHealth());
      assertEquals(10, player.getHealth());
      assertEquals(30, player.getScore());
      assertEquals(50, player.getGold());
      assertEquals(inventory, player.getInventory());
    }

    /**
     * Test that builder constructs object with default values and indirectly test that getters work
     */
    @Test
    @DisplayName("Test that builder constructs object with default values")
    void testThatBuilderConstructsObjectWithDefaultValues() {
      Player player = new Player.PlayerBuilder("test").build();
      assertEquals("test", player.getName());
      assertEquals(100, player.getHealth());
      assertEquals(0, player.getScore());
      assertEquals(0, player.getGold());
      assertEquals(inventory, player.getInventory());
    }

    /**
     * Test that constructor throws IllegalArgumentException when health is less than zero
     */
    @Test
    @DisplayName("Test that constructor throws IllegalArgumentException when health is less than zero")
    void testThatConstructorThrowsNullPointerException() {
      int health = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player.PlayerBuilder("name").health(health));
    }
    /**
     * Test that constructor throws NullPointerException when name is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when name is null")
    void testThatConstructorThrowsIllegalArgumentException() {
      String name = null;
      assertThrows(NullPointerException.class, () -> new Player.PlayerBuilder(name));
    }

    /**
     * Test that constructor throws IllegalArgumentException when name is blank
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when name is blank")
    void testThatConstructorThrowsNUllPointerExceptionWhenNameIsNull() {
      String name = "";
      assertThrows(IllegalArgumentException.class, () -> new Player.PlayerBuilder(name));
    }

    /**
     * Test that constructor throws IllegalArgumentException when score is less than zero
     */
    @Test
    @DisplayName("Test that constructor throws IllegalArgumentException when score is less than zero")
    void testThatConstructorThrowsIllegalArgumentExceptionWhenScoreIsLessThanZero() {
      int score = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player.PlayerBuilder("name").score(score));
    }

    /**
     * Test that constructor throws IllegalArgumentException when gold is less than zero
     */
    @Test
    @DisplayName("Test that constructor throws IllegalArgumentException when gold is less than zero")
    void testThatConstructorThrowsIllegalArgumentExceptionWhenGoldIsLessThanZero() {
      int gold = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player.PlayerBuilder("name").gold(gold));
    }
  }

  /**
   * Test that exception is thrown when invalid values are used
   */
  @Nested
  class ExceptionHandling {

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
     * Test that negative value in health will set the player´s health to zero and check if isAlive works
     */
    @Test
    @DisplayName("Test negative value in health will set health to zero and check if isAlive works")
    void testThatNegativeValueInHealthWillSetHealthToZeroAndCheckIfIsAliveWorks() {
      player.addHealth(-20);
      assertEquals(0, player.getHealth());
      assertFalse(player.isAlive());
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
     * Test that addToInventory can not set blank value
     */
    @Test
    @DisplayName("Test that addToInventory can not set blank value")
    void testThatAddToInventoryCanNotSetBlankValue() {
      assertThrows(IllegalArgumentException.class, () -> player.addToInventory(""));
    }
  }

  /**
   * Test that add-methods work
   */
  @Nested
  class addMethods {

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

  /**
   * Test class for file handling
   */
  @Nested
  class fileHandling {

    /**
     * Test that toString works and has correct format
     * The correct format is:
     * ;;name;health;score;gold;[item1, item2, item3, ...]
     */
    @Test
    @DisplayName("Test that toString works")
    void testThatToStringWorks() {
      player.addToInventory("Stick");
      player.addToInventory("Sword");
      assertEquals("""
                      Name: test
                      Health: 10
                      Score: 30
                      Gold: 50
                      Inventory: stick, sword"""
          ,player.toString());
    }
  }
}

