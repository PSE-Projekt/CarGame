package de.cargame.controller;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.player.Player;
import de.cargame.model.service.GameInstanceService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * The GameInstanceController class is responsible for managing game-related operations,
 * serving as the implementation of the GameInstanceAPI interface. It coordinates the
 * starting of game sessions for different player types, resetting game instances, and
 * retrieving game model data. The controller interacts with the GameInstanceService to
 * handle game logic and with the PlayerAPI to manage player-related operations.
 */
@Slf4j
public class GameInstanceController implements GameInstanceAPI {


    private final GameInstanceService gameInstanceService;
    private final PlayerAPI playerAPI;


    public GameInstanceController(GameInstanceService gameInstanceService, PlayerAPI playerAPI) {
        this.gameInstanceService = gameInstanceService;
        this.playerAPI = playerAPI;
    }


    @Override
    public void startGamePlayerKeyboard() {
        log.info("Starting game with keyboard player");
        Player player = playerAPI.getKeyboardPlayer();
        player.resetLives();
        gameInstanceService.startGame(player);
    }

    @Override
    public void startGamePlayerGamePad() {
        log.info("Starting game with gamepad player");
        Player player = playerAPI.getGamepadPlayer();
        player.resetLives();
        gameInstanceService.startGame(player);
    }

    @Override
    public void resetGameInstances() {
        log.debug("Resetting game instances");
        gameInstanceService.resetGameInstances();
    }

    /**
     * Retrieves the current state of the game models for all active game instances.
     * Each game model encapsulates data about a specific player's gameplay session,
     * including associated game objects and player details.
     *
     * @return a list of {@link GameModelData} representing the game state
     * for all active game instances.
     */
    @Override
    public List<GameModelData> getModel() {
        return gameInstanceService.getModel();
    }

}
