module cargame {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.kwhat.jnativehook;
    requires org.slf4j;
    requires static lombok;
    requires jinput;

    opens de.cargame.view to javafx.fxml;
    opens de.cargame.view.fxtests to javafx.graphics;

    exports de.cargame.view;
    exports de.cargame.model;
    exports de.cargame.controller;
}