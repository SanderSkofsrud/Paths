package edu.ntnu.idatt2001.paths.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.util.Optional;

public class ShowAlert {

  public static void showError(String title, String headerText) {
    showAlert(Alert.AlertType.ERROR, title, headerText);

  }
  public static void showInformation(String title, String headerText) {
    showAlert(Alert.AlertType.INFORMATION, title, headerText);
  }

  private static Optional<ButtonType> showAlert(Alert.AlertType alertType, String title, String headerText) {
    Alert alert = new Alert(alertType);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.initStyle(StageStyle.UNDECORATED);
    alert.setTitle(title);
    alert.setHeaderText(headerText);

    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets().add("stylesheet.css");

    return alert.showAndWait();
  }
}
