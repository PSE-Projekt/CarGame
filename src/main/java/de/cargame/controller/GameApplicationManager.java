package de.cargame.controller;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.model.handler.GameStateHandler;
import de.cargame.model.service.GameInstanceService;
import de.cargame.model.service.PlayerService;
import de.cargame.view.ApplicationView;
import javafx.application.Platform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides the core management for the game application by coordinating different APIs
 * related to game state, players, and game instances. It also manages the rendering and
 * interaction with the application's main view.
 * <p>
 * This class serves as the central controller for the game, ensuring seamless communication
 * between the game state, player management, and game instance functionalities. It is
 * responsible for initializing the APIs and providing access points for game rendering
 * and updating the user interface.
 */
@Getter
@Slf4j
public class GameApplicationManager {


    private final GameStateAPI gameStateAPI = new GameStateController(new GameStateHandler());
    private final PlayerAPI playerAPI = new PlayerController(new PlayerService());
    private final GameInstanceAPI gameInstanceAPI = new GameInstanceController(new GameInstanceService(this, gameStateAPI), playerAPI);

    private ApplicationView applicationView;


    /**
     * Handles rendering of the current game instance by delegating the rendering
     * task to the ApplicationView's renderGame method. This method runs the
     * rendering process on the JavaFX Application Thread to ensure proper
     * interaction with the JavaFX UI.
     * <p>
     * This method retrieves the current game state and ensures that the rendering
     * logic for different game phases, such as in-game or score board, is executed.
     * It is a synchronization point between the application's main view and the
     * game's rendering logic.
     * <p>
     * Note: Requires the ApplicationView to be registered previously using
     * the registerApplicationView method.
     */
    public void renderGameInstance() {
        Platform.runLater(() -> applicationView.renderGame());
    }

    /**
     * Registers the provided ApplicationView instance for managing and rendering the
     * application's user interface. The ApplicationView serves as the primary interface
     * for rendering scenes and interacting with backend logic.
     *
     * @param applicationView the ApplicationView instance to register, responsible for
     *                        handling the display and interaction with the application
     *                        stages and scenes.
     */
    public void registerApplicationView(ApplicationView applicationView) {
        this.applicationView = applicationView;
    }
}
