package de.cargame.controller;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.player.Player;
import de.cargame.model.service.GameInstanceService;

import java.util.List;

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
        Player player = playerAPI.getKeyboardPlayer();
        gameInstanceService.startGame(player);
    }

    @Override
    public void startGamePlayerGamePad() {
        Player player = playerAPI.getGamepadPlayer();
        gameInstanceService.startGame(player);
    }

    @Override
    public void resetGameInstances() {
        gameInstanceService.resetGameInstances();
    }

    @Override
    public List<GameModelData> getModel() {
        return gameInstanceService.getModel();
    }

}
