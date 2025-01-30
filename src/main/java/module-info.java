module cargame {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.kwhat.jnativehook;
    requires static lombok;

    opens de.cargame.view to javafx.fxml;

    exports de.cargame.view;
    exports de.cargame.model;
    exports de.cargame.controller;
}