package edu.ntnu.idatt2001.paths.frontend;

import javafx.scene.layout.Pane;

abstract class View {
        abstract Pane getPane();
        abstract void setup();
        abstract void resetPane();
}
