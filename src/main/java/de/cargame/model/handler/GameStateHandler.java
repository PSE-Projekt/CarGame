package de.cargame.model.handler;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import lombok.Getter;
import lombok.Setter;

/**
 * The GameStateHandler class manages the game state and game mode for a game application.
 * It provides a mechanism to track and modify the current state of the game and the mode
 * in which the game is being played (e.g., single-player or multiplayer). This class is
 * primarily used to coordinate the game's flow and context.
 */
@Getter
@Setter
public class GameStateHandler {

    private GameMode gameMode;

    private GameState gameState;

}
