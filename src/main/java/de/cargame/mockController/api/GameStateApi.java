package de.cargame.mockController.api;

import de.cargame.mockController.entity.GameMode;
import de.cargame.mockController.entity.GameState;

public interface GameStateApi {
    GameMode getGameMode();

    void setGameMode(GameMode gameMode);

    GameState getGameState();

    void setGameState(GameState gameState);
}
