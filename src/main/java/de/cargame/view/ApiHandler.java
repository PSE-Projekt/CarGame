package de.cargame.view;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.view.navigation.InputReceiver;
import lombok.Getter;
import lombok.Setter;

public class ApiHandler {
    @Getter
    private final GameInstanceAPI gameInstanceApi;
    @Getter
    private final GameStateAPI gameStateApi;
    @Getter
    private final PlayerAPI playerApi;

    @Getter
    private final InputReceiver inputReceiverKeyboard;

    @Getter
    private final InputReceiver inputReceiverGamePad;

    @Getter
    @Setter
    private String playerOneId;

    public ApiHandler(GameInstanceAPI gameInstanceApi, GameStateAPI gameStateApi, PlayerAPI playerApi) {
        this.gameInstanceApi = gameInstanceApi;
        this.gameStateApi = gameStateApi;
        this.playerApi = playerApi;

        this.inputReceiverGamePad = new InputReceiver(playerApi.getGamepadPlayerId());
        this.inputReceiverKeyboard = new InputReceiver(playerApi.getKeyboardPlayerId());

        // TODO: register InputReceivers as Observers in backend
    }
}
