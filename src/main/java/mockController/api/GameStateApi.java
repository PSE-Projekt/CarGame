package mockController.api;

import mockController.entity.GameMode;
import mockController.entity.GameState;

public interface GameStateApi {
    GameMode getGameMode();

    void setGameMode(GameMode gameMode);

    GameState getGameState();

    void setGameState(GameState gameState);
}
