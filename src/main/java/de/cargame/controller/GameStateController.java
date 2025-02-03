package de.cargame.controller;

import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.model.handler.GameStateHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameStateController implements GameStateAPI {

    private final GameStateHandler gameStateHandler;

    public GameStateController() {
        this.gameStateHandler = new GameStateHandler();
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
        this.gameStateHandler.setGameState(gameState);
    }
}
