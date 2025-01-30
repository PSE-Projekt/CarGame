package de.cargame.model.service;

import de.cargame.controller.GameApplicationManager;
import de.cargame.controller.api.GameStateApi;
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
    private final GameStateApi gameStateController;


    public GameInstanceService(GameApplicationManager gameApplicationManager, GameStateApi gameStateController) {
        this.gameApplicationManager = gameApplicationManager;
        this.gameStateController = gameStateController;
    }

    public void addGameInstance(GameInstance gameInstance) {
        if (!gameInstances.contains(gameInstance)) {
            gameInstances.add(gameInstance);
        }
    }

    public void startGame(Player player) {
        GameInstance gameInstance = new GameInstance(gameStateController, gameApplicationManager, player);
        addGameInstance(gameInstance);
        gameInstance.run();
        boolean allGamesFinished = getFinishedGameInstances().size() == gameInstances.size();
        if (allGamesFinished) {
            gameStateController.setGameState(GameState.SCORE_BOARD);
        }
    }


    public List<GameModelData> getModel() {
        List<GameModelData> gameModels = new ArrayList<>();
        for (GameInstance gameInstance : gameInstances) {
            GameModelData gameModelData = gameInstance.getGameModelData();
            gameModels.add(gameModelData);
        }
        return gameModels;
    }

    public void resetGameInstances() {
        gameInstances.clear();
    }

    private List<GameInstance> getFinishedGameInstances() {
        return gameInstances.stream()
                .filter(GameInstance::isFinished)
                .toList();
    }
}
