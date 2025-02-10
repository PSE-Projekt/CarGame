module cargame {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires slf4j.api;
    requires static lombok;
    requires jinput;
    requires java.desktop;

    opens de.cargame to javafx.graphics;

    exports de.cargame.view;
    exports de.cargame.model;
    exports de.cargame.controller;
    exports de.cargame.controller.api;
}