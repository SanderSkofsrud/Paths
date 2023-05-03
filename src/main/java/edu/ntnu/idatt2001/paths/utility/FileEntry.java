package edu.ntnu.idatt2001.paths.utility;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import edu.ntnu.idatt2001.paths.models.Link;

public class FileEntry {
  private final SimpleStringProperty fileName;
  private final SimpleStringProperty fileLocation;

  public FileEntry(String fileName, String fileLocation) {
    this.fileName = new SimpleStringProperty(fileName);
    this.fileLocation = new SimpleStringProperty(fileLocation);
  }

  public String getFileName() {
    return fileName.get();
  }

  public void setFileName(String fileName) {
    this.fileName.set(fileName);
  }

  public String getFileLocation() {
    return fileLocation.get();
  }

  public void setFileLocation(String fileLocation) {
    this.fileLocation.set(fileLocation);
  }
}
