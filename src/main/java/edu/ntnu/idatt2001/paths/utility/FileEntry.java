package edu.ntnu.idatt2001.paths.utility;

import javafx.beans.property.SimpleStringProperty;

public class FileEntry {
  private final SimpleStringProperty fileName;

  public FileEntry(String fileName) {
    this.fileName = new SimpleStringProperty(fileName);
  }

  public String getFileName() {
    return fileName.get();
  }

  public void setFileName(String fileName) {
    this.fileName.set(fileName);
  }
}
