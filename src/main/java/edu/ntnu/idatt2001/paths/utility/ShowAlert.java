package edu.ntnu.idatt2001.paths.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.util.Optional;

/**
 * The show alert class
 * Provides methods for displaying alerts in the GUI and is used to avoid code duplication.
 * Supports the display of error and information alerts.
 *
 * @author Helle R. & Sander S.
 * @version 0.1 08.05.2023
 */

public class ShowAlert {

  /**
   * Displays an error alert dialog with the specified title and header text of the Error type.
   *
   * @param title The title of the alert dialog.
   * @param headerText The header text of the alert dialog.
   */
  public static void showError(String title, String headerText) {
    showAlert(Alert.AlertType.ERROR, title, headerText);

  }

  /**
   * Displays an information alert dialog with the specified title and header text of the Information type.
   *
   * @param title The title of the alert dialog.
   * @param headerText The header text of the alert dialog.
   */
  public static void showInformation(String title, String headerText) {
    showAlert(Alert.AlertType.INFORMATION, title, headerText);
  }

  /**
   * Displays an alert dialog with the specified alert type, title and header text,
   * and returns an Optional representing the user's response to the alert dialog.
   *
   * @param alertType The type of alert dialog.
   * @param title The title of the alert dialog.
   * @param headerText The header text of the alert dialog.
   * @return an Optional representing the user's response to the alert dialog.
   */
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
