package de.cargame.model.service;

import de.cargame.controller.GameApplicationManager;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameModelData;
import de.cargame.controller.entity.GameState;
import de.cargame.model.GameInstance;
import de.cargame.model.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameInstanceService {

    private final List<GameInstance> gameInstances = new CopyOnWriteArrayList<>();

    private final GameApplicationManager gameApplicationManager;
    private final GameStateAPI gameStateController;


    public GameInstanceService(GameApplicationManager gameApplicationManager, GameStateAPI gameStateController) {
        this.gameApplicationManager = gameApplicationManager;
        this.gameStateController = gameStateController;
    }

    public void addGameInstance(GameInstance gameInstance) {
        if (!gameInstances.contains(gameInstance)) {
            gameInstances.add(gameInstance);
        }
    }

    public void startGame(Player player) {
        player.resetScore();
        GameInstance gameInstance = new GameInstance(this, gameStateController, gameApplicationManager, player);
        addGameInstance(gameInstance);
        new Thread(gameInstance).start();
    }


    public List<GameModelData> getModel() {
        List<GameModelData> gameModels = new ArrayList<>();
        for (GameInstance gameInstance : gameInstances) {
            GameModelData gameModelData = gameInstance.getGameModelData();
            gameModels.add(gameModelData);
        }
        return gameModels;
    }


    public boolean checkGameState() {
        for (GameInstance gameInstance : gameInstances) {
            if (!gameInstance.isFinished()) {
                return false;
            }
        }
        gameStateController.setGameState(GameState.SCORE_BOARD);
        SoundService.getInstance().stopCarRaceSoundLoop();
        return true;
    }

    public void resetGameInstances() {
        gameInstances.clear();
    }

}
