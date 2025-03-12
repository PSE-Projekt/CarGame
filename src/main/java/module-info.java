module cargame {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;

    // Logging & Utilities
    requires slf4j.api;
    requires static lombok;
    requires jinput;

    // Allow TestFX & JUnit 5 to access JavaFX components
    opens de.cargame to javafx.graphics, javafx.fxml, org.testfx, org.junit.jupiter;
    opens de.cargame.view to javafx.graphics, javafx.fxml, org.testfx, org.junit.jupiter;
    opens de.cargame.model.handler to org.testfx, org.junit.jupiter;
    opens de.cargame.controller.input to org.testfx, org.junit.jupiter;

    // Export application packages
    exports de.cargame.view;
    exports de.cargame.model;
    exports de.cargame.controller;
    exports de.cargame.controller.api;
}
