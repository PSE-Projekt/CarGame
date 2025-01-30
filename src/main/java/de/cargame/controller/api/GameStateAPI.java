package de.cargame.controller.api;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;

/**
 * The GameStateAPI interface defines methods to manage and modify the current
 * game mode and game state within the application. Implementing classes
 * should provide functionality to retrieve and update these states dynamically
 * as the game progresses.
 */
public interface GameStateAPI {

    GameMode getGameMode();

    void setGameMode(GameMode gameMode);

    GameState getGameState();

    void setGameState(GameState gameState);

}
