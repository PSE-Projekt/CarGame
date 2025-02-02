package de.cargame.view;

import de.cargame.controller.api.GameInstanceApi;
import de.cargame.controller.api.GameStateApi;
import de.cargame.controller.api.PlayerApi;
import de.cargame.model.entity.player.Player;
import de.cargame.view.navigation.InputReceiver;
import de.cargame.view.navigation.Navigator;
import lombok.Getter;
import lombok.Setter;

public class ApiHandler {
    @Getter
    private final GameInstanceApi gameInstanceApi;
    @Getter
    private final GameStateApi gameStateApi;
    @Getter
    private final PlayerApi playerApi;

    @Getter
    @Setter
    private Player playerOne;

    @Getter
    @Setter
    private Player playerTwo;

    @Getter
    private final InputReceiver inputReceiverKeyboard;

    @Getter
    private final InputReceiver inputReceiverGamePad;

    public ApiHandler(GameInstanceApi gameInstanceApi, GameStateApi gameStateApi, PlayerApi playerApi) {
        this.gameInstanceApi = gameInstanceApi;
        this.gameStateApi = gameStateApi;
        this.playerApi = playerApi;

        this.playerOne = null;
        this.playerTwo = null;

        this.inputReceiverGamePad = new InputReceiver(playerApi.getGamepadPlayerId());
        this.inputReceiverKeyboard = new InputReceiver(playerApi.getKeyboardPlayerId());

        // TODO: register InputReceivers as Observers in backend
    }
}
