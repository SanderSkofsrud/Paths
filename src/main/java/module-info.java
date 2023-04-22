module paths {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires com.google.gson;

    exports edu.ntnu.idatt2001.paths;
    exports edu.ntnu.idatt2001.paths.models;
    exports edu.ntnu.idatt2001.paths.views;
    exports edu.ntnu.idatt2001.paths.controllers;
    exports edu.ntnu.idatt2001.paths.utility;

    opens edu.ntnu.idatt2001.paths.models to com.google.gson;
    opens edu.ntnu.idatt2001.paths.models.goals to com.google.gson;
}