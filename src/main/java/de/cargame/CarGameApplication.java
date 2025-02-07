package de.cargame;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService;
import de.cargame.controller.GameApplicationManager;
import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.view.ApplicationView;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

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

    private ApplicationView getApplicationView(Stage primaryStage) {
        GameStateAPI gameStateAPI = this.gameApplicationManager.getGameStateAPI();
        GameInstanceAPI gameInstanceAPI = this.gameApplicationManager.getGameInstanceAPI();
        PlayerAPI playerAPI = this.gameApplicationManager.getPlayerAPI();

        // the applicationView will handle further rendering from now on once the stage is handed over and the applicationView
        // is registered in the gameApplicationManager
        return new ApplicationView(gameInstanceAPI, gameStateAPI, playerAPI, primaryStage);
    }

    @Override
    public void init() {
        // Set java.library.path to include the extracted native libraries
        System.setProperty("java.library.path", "target/natives");

        // Force JVM to reload the library path
        try {
            System.setProperty("jna.library.path", "target/natives");
        } catch (Exception e) {
            System.err.println("Could not set jna.library.path");
        }
        try {GlobalScreen.registerNativeHook();
            GlobalScreen.setEventDispatcher(new SwingDispatchService());

        } catch (NativeHookException e) {
           throw new RuntimeException(e);
        }
        System.setProperty("sun.java2d.opengl", "true");
        this.gameApplicationManager = new GameApplicationManager();
        log.info("Initialized game application");
    }
}
