module paths {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires transitive javafx.base;
    requires transitive com.google.gson;
    requires transitive java.desktop;
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