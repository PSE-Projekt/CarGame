package de.cargame.view;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameState;
import de.cargame.view.navigation.InputReceiver;
import lombok.Getter;
import lombok.Setter;
/**
 * Class handling the APIs handed out by the Controller component. Will be created in
 * ApplicationView and passed on to the different CustomScenes.
 */
public class ApiHandler {
    @Getter
    private final GameInstanceAPI gameInstanceApi;
    @Getter
    private final GameStateAPI gameStateApi;
    @Getter
    private final PlayerAPI playerApi;

    @Getter
    private final InputReceiver inputReceiverKeyboard;

    private final ApplicationView applicationView;

    @Getter
    private final InputReceiver inputReceiverGamePad;

    @Getter
    @Setter
    private String playerOneId;

    /**
     * Creates a new ApiHandler using the APIs handed out by the Controller component.
     */
    public ApiHandler(GameInstanceAPI gameInstanceApi, GameStateAPI gameStateApi, PlayerAPI playerApi, ApplicationView applicationView) {
        this.gameInstanceApi = gameInstanceApi;
        this.gameStateApi = gameStateApi;
        this.playerApi = playerApi;
        this.applicationView = applicationView;

        this.inputReceiverGamePad = new InputReceiver();
        this.inputReceiverKeyboard = new InputReceiver();


        playerApi.createPlayerKeyboard();
        playerApi.createPlayerGamepad();

        String keyboardPlayerId = playerApi.getKeyboardPlayerId();
        playerApi.registerInputObserver(inputReceiverKeyboard, keyboardPlayerId);

        String gamepadPlayerId = playerApi.getGamepadPlayerId();
        playerApi.registerInputObserver(inputReceiverGamePad, gamepadPlayerId);
    }

    /**
     * Calls the ApplicationView to switch scenes depending on given GameState.
     */
    public void switchScene(GameState newGameState) {
        this.applicationView.switchScene(newGameState);
    }
}
