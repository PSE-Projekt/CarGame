package de.cargame;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import de.cargame.config.GameConfig;
import de.cargame.controller.GameApplicationManager;
import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.view.ApplicationView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    public static void main(String[] args) {
        // launch javafx application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameStateAPI gameStateAPI = this.gameApplicationManager.getGameStateAPI();
        GameInstanceAPI gameInstanceAPI = this.gameApplicationManager.getGameInstanceAPI();
        PlayerAPI playerAPI = this.gameApplicationManager.getPlayerAPI();

        // the applicationView will handle further rendering from now on once the stage is handed over and the applicationView
        // is registered in the gameApplicationManager
        ApplicationView applicationView = new ApplicationView(gameInstanceAPI, gameStateAPI, playerAPI, primaryStage);
        this.gameApplicationManager.registerApplicationView(applicationView);


        /*
            for testing purposes

            primaryStage.setTitle("Car Game");
            VBox root = new VBox();
            root.getChildren().add(new Text("init was called correctly: " + (this.gameApplicationManager != null)));
            Scene testScene = new Scene(root, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

            primaryStage.setScene(testScene);

            primaryStage.show();
         */


    }

    @Override
    public void init() {
        // Set java.library.path to include the extracted native libraries
        System.setProperty("java.library.path", "target/natives");

        // Force JVM to reload the library path
        try {
            System.setProperty("jna.library.path", "target/natives");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
        System.setProperty("sun.java2d.opengl", "true");
        this.gameApplicationManager = new GameApplicationManager();
    }
}
