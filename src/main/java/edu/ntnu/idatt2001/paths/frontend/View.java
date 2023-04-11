package edu.ntnu.idatt2001.paths.frontend;

import javafx.scene.layout.Pane;

/**
 * The type View.
 */
abstract class View {
        /**
         * Gets pane.
         *
         * @return the pane
         */
        abstract Pane getPane();

        /**
         * Sets .
         */
        abstract void setup();

        /**
         * Reset pane.
         */
        abstract void resetPane();
}
