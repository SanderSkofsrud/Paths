package edu.ntnu.idatt2001.paths.views;

import javafx.scene.layout.Pane;

/**
 * The type View.
 */
public abstract class View {
        /**
         * Gets pane.
         *
         * @return the pane
         */
        public abstract Pane getPane();

        /**
         * Sets .
         */
        public abstract void setup();

        /**
         * Reset pane.
         */
        abstract void resetPane();
}
