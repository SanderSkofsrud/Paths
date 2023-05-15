package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Story;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MainGameController {

  private static MainGameController instance;

  private Story story;

  private Map<Link, Passage> passageMap;

  private MainGameController() {}

  public static MainGameController getInstance() {
    if (instance == null) {
      instance = new MainGameController();
    }
    return instance;
  }

  public Alert brokenLinksCollection() {
    story = GameController.getInstance().getGame().getStory();
    List<Link> brokenLinks = story.getBrokenLinks();

    HashMap<Link, Passage> brokenPassageMap = new HashMap<>();
    passageMap = story.getPassages();

    for (Map.Entry<Link, Passage> entry : passageMap.entrySet()) {
      Link link = entry.getKey();
      Passage passage = entry.getValue();

      if (brokenLinks.contains(link)) {
        brokenPassageMap.put(link, passage);
      }
    }
    Alert alert = null;
    if (!brokenPassageMap.isEmpty()) {
      alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Broken link");
      alert.setHeaderText("You've selected a passage with a broken link");
      alert.setContentText("Do you want to remove the passage?");

      ButtonType removeButton = new ButtonType("Remove");
      ButtonType cancelButton = new ButtonType("Cancel");

      alert.getButtonTypes().setAll(removeButton, cancelButton);

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == removeButton) {
        Link linkToRemove = brokenPassageMap.keySet().iterator().next();
        story.removePassage(linkToRemove);
        passageMap.remove(linkToRemove);
      }
    }
    return alert;
  }

}
