package de.cargame.view;

import de.cargame.controller.api.GameInstanceApi;
import de.cargame.controller.api.GameStateApi;
import de.cargame.controller.api.PlayerApi;

public class ApiHandler {
    private final GameInstanceApi gameInstanceApi;
    private final GameStateApi gameStateApi;
    private final PlayerApi playerApi;

    public ApiHandler(GameInstanceApi gameInstanceApi, GameStateApi gameStateApi, PlayerApi playerApi) {
        this.gameInstanceApi = gameInstanceApi;
        this.gameStateApi = gameStateApi;
        this.playerApi = playerApi;
    }

    public GameInstanceApi getGameInstanceApi() {
        return gameInstanceApi;
    }

    public GameStateApi getGameStateApi() {
        return gameStateApi;
    }

    public PlayerApi getPlayerApi() {
        return playerApi;
    }
}
