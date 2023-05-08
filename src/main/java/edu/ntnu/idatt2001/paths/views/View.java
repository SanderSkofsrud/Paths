package edu.ntnu.idatt2001.paths.views;

import javafx.scene.layout.Pane;

/**
 * The type View.
 * The class is used to create the GUI of the game.
 * The class is abstract and is used to create the different views of the GUI.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public abstract class View {
        /**
         * Gets pane.
         *
         * @return the pane
         */
        public abstract Pane getPane();

        /**
         * Sets up the pane.
         */
        public abstract void setup();

        /**
         * Reset pane.
         */
        abstract void resetPane();
}
