package de.cargame.model.service;

import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.model.entity.gameobject.*;
import de.cargame.model.entity.gameobject.car.ai.AICar;
import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;
import de.cargame.model.entity.player.Player;
import de.cargame.model.handler.CollisionHandler;
import de.cargame.model.handler.GameObjectSpawnScheduler;
import de.cargame.model.handler.PlayerHandler;
import de.cargame.model.handler.entity.MultiplayerSpawningStrategy;
import de.cargame.model.handler.entity.SinglePlayerSpawningStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Service class responsible for managing game objects and their behaviors during
 * the game's lifecycle. It includes functionality for spawning, updating, and
 * removing game objects, as well as detecting and responding to collisions.
 * This class works in conjunction with other game components such as the
 * {@code CollisionHandler}, {@code GameObjectCreationService},
 * {@code GameObjectSpawnScheduler}, and {@code PlayerHandler}.
 * <p>
 * Responsibilities of this class include:
 * - Managing active game objects in a thread-safe manner.
 * - Spawning specific game objects such as player cars, AI cars, buildings,
 * road marks, obstacles, and rewards.
 * - Updating the positions and states of game objects over time.
 * - Handling object despawning when objects move outside a certain boundary.
 * - Delegating collision handling to the {@code CollisionHandler}.
 * - Coordinating player interaction and game state transitions.
 */
@Slf4j
public class GameObjectService {
    private final CollisionHandler collisionHandler;
    private final GameObjectCreationService gameObjectCreationService;
    private final GameStateAPI gameStateController;
    private final PlayerHandler playerHandler;
    private final GameObjectSpawnScheduler gameObjectSpawnScheduler;
    private final List<GameObject> gameObjects = new CopyOnWriteArrayList<>();


    public GameObjectService(GameStateAPI gameStateController, PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
        this.gameStateController = gameStateController;
        this.collisionHandler = new CollisionHandler(playerHandler);
        this.gameObjectCreationService = new GameObjectCreationService();
        this.gameObjectSpawnScheduler = new GameObjectSpawnScheduler(this.playerHandler, this);
    }


    public void startGame() {
        Player player = playerHandler.getPlayer();
        gameStateController.setGameState(GameState.IN_GAME);
        GameMode gameMode = gameStateController.getGameMode();
        if (gameMode.equals(GameMode.SINGLEPLAYER)) {
            SinglePlayerSpawningStrategy singlePlayerSpawningStrategy = new SinglePlayerSpawningStrategy();
            gameObjectCreationService.setGameObjectSpawningStrategy(singlePlayerSpawningStrategy);
        } else if (gameMode.equals(GameMode.MULTIPLAYER)) {
            MultiplayerSpawningStrategy multiplayerSpawningStrategy = new MultiplayerSpawningStrategy();
            gameObjectCreationService.setGameObjectSpawningStrategy(multiplayerSpawningStrategy);
        }
        spawnPlayerCar(player.getId(), player.getCarSelection());

        playerHandler.resetScore();
        gameObjectSpawnScheduler.startSpawning();
    }

    public void stopGame() {
        gameObjectSpawnScheduler.stopSpawning();
        gameObjects.removeAll(gameObjects);
    }


    public void update(double deltaTime) {
        moveElements(deltaTime);
        despawnPassedObjects();
        checkCollision();
    }

    private void moveElements(double deltaTime) {
        for (GameObject gameObject : gameObjects) {
            boolean fastForwarding = playerHandler.isFastForwarding();
            gameObject.move(deltaTime, fastForwarding);
        }
    }

    private void despawnPassedObjects() {
        List<GameObject> gameObjectsToRemove = new ArrayList<>();
        List<GameObject> despawnableObjects = gameObjects.stream()
                .filter(GameObject::isDespawnable)
                .toList();

        for (GameObject gameObject : despawnableObjects) {
            if ((gameObject.getX() + gameObject.getWidth() < 0)) {
                gameObjectsToRemove.add(gameObject);
            }
        }
        gameObjects.removeAll(gameObjectsToRemove);
    }


    private void spawnPlayerCar(String playerId, CarType carType) {
        PlayerCar playerCar = gameObjectCreationService.createPlayerCar(carType);
        playerCar.setPlayerId(playerId);
        playerCar.setPlayerHandler(playerHandler);
        playerHandler.setPlayerCar(playerCar);
        gameObjects.add(playerCar);
    }

    public void spawnBuilding() {
        List<Building> building = gameObjectCreationService.createBuildings();
        gameObjects.addAll(building);
    }

    public void spawnRoadMarks() {
        List<RoadMark> roadMark = gameObjectCreationService.createRoadMark();
        gameObjects.addAll(roadMark);
    }

    public void spawnObstacle() {
        List<Obstacle> obstacle = gameObjectCreationService.createObstacle();
        gameObjects.addAll(obstacle);
    }

    public void spawnReward() {
        Reward reward = gameObjectCreationService.createReward();
        gameObjects.add(reward);
    }

    public void spawnAICar() {
        AICar aiCar = gameObjectCreationService.createAICar();
        gameObjects.add(aiCar);
    }

    public List<GameObject> getAllGameObjects() {
        return gameObjects;
    }


    private void checkCollision() {
        collisionHandler.checkCollision(gameObjects);
        boolean isPlayerAlive = playerHandler.isPlayerAlive();

        if (!isPlayerAlive) {
            stopGame();
        }
    }

}
