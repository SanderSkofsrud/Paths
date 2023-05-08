package edu.ntnu.idatt2001.paths.utility;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import edu.ntnu.idatt2001.paths.models.Link;

/**
 * The type File entry.
 * The FileEntry class is used to create a file entry object.
 * The class is used to create a file entry object that is used to display the file name and file location in the GUI.
 * The class is used in the FileTable class.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class FileEntry {
  /**
   * The File name.
   */
  private final SimpleStringProperty fileName;
  /**
   * The File location.
   */
  private final SimpleStringProperty fileLocation;

  /**
   * Instantiates a new File entry.
   * The constructor is used to create a file entry object.
   *
   * @param fileName     the file name of the file entry object
   * @param fileLocation the file location of the file entry object
   */
  public FileEntry(String fileName, String fileLocation) {
    this.fileName = new SimpleStringProperty(fileName);
    this.fileLocation = new SimpleStringProperty(fileLocation);
  }

  /**
   * Gets file name.
   *
   * @return the file name
   */
  public String getFileName() {
    return fileName.get();
  }

  /**
   * Sets file name.
   *
   * @param fileName the file name
   */
  public void setFileName(String fileName) {
    this.fileName.set(fileName);
  }

  /**
   * Gets file location.
   *
   * @return the file location
   */
  public String getFileLocation() {
    return fileLocation.get();
  }

  /**
   * Sets file location.
   *
   * @param fileLocation the file location
   */
  public void setFileLocation(String fileLocation) {
    this.fileLocation.set(fileLocation);
  }
}
