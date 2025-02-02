package de.cargame.view;

import de.cargame.controller.api.GameInstanceApi;
import de.cargame.controller.api.GameStateApi;
import de.cargame.controller.api.PlayerApi;
import de.cargame.model.entity.player.Player;
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

    public ApiHandler(GameInstanceApi gameInstanceApi, GameStateApi gameStateApi, PlayerApi playerApi) {
        this.gameInstanceApi = gameInstanceApi;
        this.gameStateApi = gameStateApi;
        this.playerApi = playerApi;

        this.playerOne = null;
        this.playerTwo = null;
    }
}
