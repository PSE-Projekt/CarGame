package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World");
        primaryStage.show();
    }

    public void render() {
        // meant to be called from the controller
    }
}
