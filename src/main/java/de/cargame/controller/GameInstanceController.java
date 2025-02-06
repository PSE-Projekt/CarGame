package de.cargame.controller;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.player.Player;
import de.cargame.model.service.GameInstanceService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GameInstanceController implements GameInstanceAPI {


    private final GameInstanceService gameInstanceService;
    private final PlayerAPI playerAPI;


    public GameInstanceController(GameApplicationManager gameApplicationManager,
                                  GameStateAPI gameStateController,
                                  PlayerAPI playerAPI) {
        this.gameInstanceService = new GameInstanceService(gameApplicationManager, gameStateController);
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

    @Override
    public List<GameModelData> getModel() {
        return gameInstanceService.getModel();
    }

}
