package de.cargame;

import de.cargame.controller.GameApplicationManager;
import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.view.ApplicationView;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * The CarGameApplication class is the entry point for the application.
 * It extends the JavaFX Application class and initializes the game lifecycle.
 */
@Slf4j
public class CarGameApplication extends Application {
    private GameApplicationManager gameApplicationManager;

    /**
     * The main method serves as the entry point for the application.
     * It initializes system properties, sets up native library paths, registers hooks using `GlobalScreen`,
     * enables OpenGL rendering, and creates an instance of the `GameApplicationManager`
     * to manage the game lifecycle.
     *
     * @param args the command-line arguments passed to the application, not utilized within the implementation.
     */
    public void run(String[] args) {
        // launch javafx application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ApplicationView applicationView = getApplicationView(primaryStage);
        this.gameApplicationManager.registerApplicationView(applicationView);
    }

    protected ApplicationView getApplicationView(Stage primaryStage) {
        GameStateAPI gameStateAPI = this.gameApplicationManager.getGameStateAPI();
        GameInstanceAPI gameInstanceAPI = this.gameApplicationManager.getGameInstanceAPI();
        PlayerAPI playerAPI = this.gameApplicationManager.getPlayerAPI();

        // the applicationView will handle further rendering from now on once the stage is handed over and the applicationView
        // is registered in the gameApplicationManager
        return new ApplicationView(gameInstanceAPI, gameStateAPI, playerAPI, primaryStage);
    }

    @Override
    public void init() {
        this.gameApplicationManager = new GameApplicationManager();
    }
}
