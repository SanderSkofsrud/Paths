module paths {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires com.google.gson;
    requires java.desktop;
    requires google.cloud.translate;
    requires google.cloud.core;

    exports edu.ntnu.idatt2001.paths;
    exports edu.ntnu.idatt2001.paths.models;
    exports edu.ntnu.idatt2001.paths.views;
    exports edu.ntnu.idatt2001.paths.controllers;
    exports edu.ntnu.idatt2001.paths.utility;
    exports edu.ntnu.idatt2001.paths.utility.json;
    exports edu.ntnu.idatt2001.paths.utility.paths;
    exports edu.ntnu.idatt2001.paths.models.goals;
    exports edu.ntnu.idatt2001.paths.models.actions;
    exports edu.ntnu.idatt2001.paths.models.player;

    opens edu.ntnu.idatt2001.paths.models to com.google.gson;
    opens edu.ntnu.idatt2001.paths.models.goals to com.google.gson;
    opens edu.ntnu.idatt2001.paths.models.actions to com.google.gson;
    opens edu.ntnu.idatt2001.paths.models.player to com.google.gson;
}