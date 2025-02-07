package de.cargame.model.service;

import de.cargame.controller.GameApplicationManager;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameModelData;
import de.cargame.controller.entity.GameState;
import de.cargame.model.GameInstance;
import de.cargame.model.entity.player.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The GameInstanceService class is responsible for managing game instances within the application.
 * It coordinates the lifecycle of multiple game instances, oversees game state transitions, and
 * interacts with other core components such as game applications and state controllers.
 */
@Slf4j
public class GameInstanceService {

    private final List<GameInstance> gameInstances = new CopyOnWriteArrayList<>();

    private final GameApplicationManager gameApplicationManager;
    private final GameStateAPI gameStateController;


    public GameInstanceService(GameApplicationManager gameApplicationManager, GameStateAPI gameStateController) {
        this.gameApplicationManager = gameApplicationManager;
        this.gameStateController = gameStateController;
    }

    /**
     * Adds the specified game instance to the list of active game instances.
     * The game instance is added only if it does not already exist in the list.
     *
     * @param gameInstance the game instance to be added to the list of active game instances.
     */
    public void addGameInstance(GameInstance gameInstance) {
        if (!gameInstances.contains(gameInstance)) {
            gameInstances.add(gameInstance);
        }
    }

    /**
     * Starts a new game instance for the provided player. This method resets the player's score
     * to its default state, initializes a new game instance, adds it to the list of active game
     * instances, and begins running the game instance on a separate thread.
     *
     * @param player The player for whom the game instance is being started. The player's score
     *               will be reset, and they will be assigned to the new game instance.
     */
    public void startGame(Player player) {
        player.resetScore();
        GameInstance gameInstance = new GameInstance(this, gameStateController, gameApplicationManager, player);
        addGameInstance(gameInstance);
        new Thread(gameInstance).start();
    }


    /**
     * Retrieves a list of game model data corresponding to the active game instances.
     * Each element in the list represents the current state of a game instance,
     * including the player ID and the game objects associated with that instance.
     *
     * @return a list of GameModelData objects representing the state of all active game instances
     */
    public List<GameModelData> getModel() {
        List<GameModelData> gameModels = new ArrayList<>();
        for (GameInstance gameInstance : gameInstances) {
            GameModelData gameModelData = gameInstance.getGameModelData();
            gameModels.add(gameModelData);
        }
        return gameModels;
    }


    /**
     * Checks the current state of all active game instances to determine whether
     * all games have finished or not. If all game instances are finished, the
     * game state is updated to SCORE_BOARD, a sound service loop is stopped,
     * and a log message is recorded.
     *
     * @return {@code true} if all game instances have finished, indicating the game
     *         can transition to the SCORE_BOARD state; {@code false} otherwise.
     */
    public boolean checkGameState() {
        for (GameInstance gameInstance : gameInstances) {
            if (!gameInstance.isFinished()) {
                return false;
            }
        }
        gameStateController.setGameState(GameState.SCORE_BOARD);
        log.debug("All games finished - game state set to SCORE_BOARD");
        SoundService.getInstance().stopCarRaceSoundLoop();
        return true;
    }

    /**
     * Resets all game instances managed by the GameInstanceService.
     * This method clears the current list of active game instances, effectively removing
     * all references to existing games and preparing the service for a new set of games
     * to be started or managed.
     */
    public void resetGameInstances() {
        gameInstances.clear();
    }

}
