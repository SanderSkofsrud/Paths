package edu.ntnu.idatt2001;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
  Player player = new Player("Test", 10, 30, 50);
  List<String> inventory = new ArrayList<>();
  String test = "Item";

  @Test
  void getName() {
    assertEquals(player.getName(), "Test");
  }

  @Test
  void addHealth() {
    player.addHealth(10);
    assertEquals(player.getHealth(),20);

  }

  @Test
  void getHealth() {
    assertEquals(player.getHealth(), 10);
  }

  @Test
  void addScore() {
    player.addScore(10);
    assertEquals(player.getScore(),40);
  }

  @Test
  void getScore() {
    assertEquals(player.getScore(), 30);
  }

  @Test
  void addGold() {
    player.addGold(10);
    assertEquals(player.getGold(),60);
  }

  @Test
  void getGold() {
    assertEquals(player.getGold(), 50);
  }

  @Test
  void addToInventory() {
    player.addToInventory(test);
    assertNotNull(player.getInventory());
  }

  @Test
  void getInventory() {
    inventory.add(test);
    assertFalse(inventory.isEmpty());
  }
}

