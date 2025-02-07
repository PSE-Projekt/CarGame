package de.cargame.controller;

import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.model.GameInstance;
import de.cargame.model.handler.GameStateHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * The GameStateController class implements the GameStateAPI interface.
 * It provides methods to manage and modify the current game state and game mode,
 * ensuring the game's progression and operational context are accurately maintained.
 * <p>
 * Collaboration:
 * - Delegates game state and mode operations to the GameStateHandler.
 * - Implements the methods defined in the GameStateAPI interface.
 */
@Getter
@Setter
@Slf4j
public class GameStateController implements GameStateAPI {

    private final GameStateHandler gameStateHandler;

    public GameStateController(GameStateHandler gameStateHandler) {
        this.gameStateHandler = gameStateHandler;
    }


    /**
     * Retrieves the current game mode from the GameStateHandler.
     * The game mode defines whether the game is in single-player, multiplayer, or not set mode.
     *
     * @return the current {@link GameMode}, representing the game's mode of operation.
     */
    @Override
    public GameMode getGameMode() {
        return gameStateHandler.getGameMode();
    }

    /**
     * Updates the game mode within the application by setting the specified {@code gameMode}.
     * The game mode determines whether the game operates in single-player, multiplayer,
     * or an undefined mode. This method delegates the game mode update to the internal
     * {@link GameStateHandler}.
     *
     * @param gameMode the {@link GameMode} to set, representing the desired mode of operation
     */
    @Override
    public void setGameMode(GameMode gameMode) {
        log.info("Setting game mode to {}", gameMode);
        gameStateHandler.setGameMode(gameMode);
    }

    /**
     * Retrieves the current game state from the GameStateHandler.
     * The game state represents the current phase of the game,
     * such as main menu, car selection, in-game, or score board.
     *
     * @return the current GameState, indicating the current phase of the game.
     */
    @Override
    public GameState getGameState() {
        return gameStateHandler.getGameState();
    }

    /**
     * Updates the current game state to the specified {@code gameState}.
     * The game state defines the current phase of the game, such as main menu,
     * car selection, in-game, or score board. This method delegates the state
     * change to the {@link GameStateHandler}.
     *
     * @param gameState the {@link GameState} to set, representing the new
     *                  phase of the game.
     */
    @Override
    public void setGameState(GameState gameState) {
        log.debug("Setting game state to {}", gameState);
        this.gameStateHandler.setGameState(gameState);
    }
}
