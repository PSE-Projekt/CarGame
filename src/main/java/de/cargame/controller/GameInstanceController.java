package de.cargame.controller;

import de.cargame.controller.api.GameInstanceApi;
import de.cargame.controller.api.GameStateApi;
import de.cargame.controller.api.PlayerApi;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.player.Player;
import de.cargame.model.service.GameInstanceService;

import java.util.List;

public class GameInstanceController implements GameInstanceApi {


    private final GameInstanceService gameInstanceService;
    private final PlayerApi playerAPI;


    public GameInstanceController(GameApplicationManager gameApplicationManager,
                                  GameStateApi gameStateController,
                                  PlayerApi playerAPI) {
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
