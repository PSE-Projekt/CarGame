module cargame {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.kwhat.jnativehook;
    requires slf4j.api;
    requires static lombok;
    requires jinput;

    opens de.cargame to javafx.graphics;

    exports de.cargame.view;
    exports de.cargame.model;
    exports de.cargame.controller;
    exports de.cargame.controller.api;
}