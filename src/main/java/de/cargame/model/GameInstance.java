package de.cargame.model;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.GameApplicationManager;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.player.Player;
import de.cargame.model.handler.PlayerHandler;
import de.cargame.model.service.GameInstanceService;
import de.cargame.model.service.GameObjectService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * The GameInstance class represents an individual game session for a single player.
 * It is responsible for managing the lifecycle, state, and logic of the game session.
 * The class includes a game loop that executes continuously while the game is running,
 * maintaining a consistent frame update and ensuring interactions between game objects.
 */
@Slf4j
public class GameInstance implements Runnable {

    private final GameInstanceService gameInstanceService;
    private final PlayerHandler playerHandler;
    private final GameApplicationManager gameApplicationManager;
    private final GameObjectService gameObjectService;

    private final int FPS;

    @Getter
    private boolean isFinished = false;


    public GameInstance(GameInstanceService gameInstanceService, GameStateAPI gameStateController, GameApplicationManager gameApplicationManager, Player player) {
        this.gameInstanceService = gameInstanceService;
        this.gameApplicationManager = gameApplicationManager;
        this.playerHandler = new PlayerHandler(player);
        this.gameObjectService = new GameObjectService(gameStateController, playerHandler);

        FPS = GameConfigService.getInstance().loadInteger(ConfigKey.FPS);
    }

    /**
     * Executes the core game loop for this game instance. This method manages the
     * game's lifecycle and logic while the game state is set to IN_GAME.
     * <p>
     * The loop performs the following tasks:
     * 1. Starts the game by initializing necessary services and game objects.
     * 2. Maintains a consistent time step (deltaTime) based on the system's nanoTime.
     * 3. Updates the game state, including moving and spawning objects, and handling collisions.
     * 4. Renders the current game state visually via the associated game application manager.
     * 5. Controls the game's frame rate using a sleep interval defined in `GameConfig.FPS`.
     * <p>
     * The loop continues indefinitely as long as the game state remains IN_GAME.
     * If interrupted, the error is logged, and the loop exits, marking the game
     * instance as finished.
     */
    @Override
    public void run() {
        String playerId = playerHandler.getPlayer().getId();
        log.info("Starting game instance for player {}", playerId);
        gameObjectService.startGame();
        long lastTime = System.nanoTime();
        while (playerHandler.isPlayerAlive()) {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
            lastTime = currentTime;

            gameObjectService.update(deltaTime);
            gameApplicationManager.renderGameInstance();
            try {
                Thread.sleep(FPS);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
        log.info("Player {} has lost all lives -> game for this player ends", playerId);
        isFinished = true;
        boolean allGamesFinished = gameInstanceService.checkGameState();
        if (allGamesFinished) {
            log.info("All games finished -> game ends");
            gameApplicationManager.renderGameInstance();
        }
    }

    /**
     * Retrieves the current score for the player within this game instance.
     * The score is fetched from the player handler, which manages the player's state.
     *
     * @return the current score of the player as an integer
     */
    public int getScore() {
        return (int) playerHandler.getScore();
    }

    /**
     * Retrieves the current game model data for this game instance.
     * The game model data includes the ID of the currently playing player
     * and a list of all game objects present in the game.
     *
     * @return a GameModelData object containing the player ID and the list of game objects
     */
    public GameModelData getGameModelData() {
        return new GameModelData(getPlayingPlayerId(), gameObjectService.getAllGameObjects());
    }

    /**
     * Retrieves the unique identifier of the currently playing player
     * in this game instance. This identifier is managed by the player handler
     * and is associated with the active player in the game.
     *
     * @return a String representing the unique identifier of the currently playing player
     */
    public String getPlayingPlayerId() {
        return playerHandler.getPlayer().getId();
    }

}
