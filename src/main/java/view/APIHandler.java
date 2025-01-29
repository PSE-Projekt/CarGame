package view;

public class APIHandler {
    private final GameInstanceAPI gameInstanceAPI;
    private final GameStateAPI gameStateAPI;
    private final PlayerAPI playerAPI;

    public APIHandler(GameInstanceAPI gameInstanceAPI, GameStateAPI gameStateAPI, PlayerAPI playerAPI) {
        this.gameInstanceAPI = gameInstanceAPI;
        this.gameStateAPI = gameStateAPI;
        this.playerAPI = playerAPI;
    }

    public GameInstanceAPI getGameInstanceAPI() {
        return gameInstanceAPI;
    }

    public GameStateAPI getGameStateAPI() {
        return gameStateAPI;
    }

    public PlayerAPI getPlayerAPI() {
        return playerAPI;
    }
}
