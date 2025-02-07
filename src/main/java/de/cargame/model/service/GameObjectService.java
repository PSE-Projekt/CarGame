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


    /**
     * Initializes and starts a new game session. This method performs the following actions:
     * <p>
     * 1. Retrieves the current player from the player handler.
     * 2. Switches the game state to "IN_GAME".
     * 3. Determines the game mode (SINGLEPLAYER or MULTIPLAYER):
     *    - Configures the spawning strategy and game mode for single-player mode.
     *    - Configures the spawning strategy and game mode for multiplayer mode.
     * 4. Initializes the game object creation service.
     * 5. Spawns the player's selected car based on their ID and car selection.
     * 6. Resets the player's score.
     * 7. Starts the game object spawn scheduler.
     */
    public void startGame() {
        Player player = playerHandler.getPlayer();
        gameStateController.setGameState(GameState.IN_GAME);
        GameMode gameMode = gameStateController.getGameMode();
        if (gameMode.equals(GameMode.SINGLEPLAYER)) {
            SinglePlayerSpawningStrategy singlePlayerSpawningStrategy = new SinglePlayerSpawningStrategy();
            gameObjectCreationService.setGameObjectSpawningStrategy(singlePlayerSpawningStrategy);
            gameObjectCreationService.setGameMode(GameMode.SINGLEPLAYER);
        } else if (gameMode.equals(GameMode.MULTIPLAYER)) {
            MultiplayerSpawningStrategy multiplayerSpawningStrategy = new MultiplayerSpawningStrategy();
            gameObjectCreationService.setGameObjectSpawningStrategy(multiplayerSpawningStrategy);
            gameObjectCreationService.setGameMode(GameMode.MULTIPLAYER);
        }
        gameObjectCreationService.init();
        spawnPlayerCar(player.getId(), player.getCarSelection());

        playerHandler.resetScore();
        gameObjectSpawnScheduler.startSpawning();
    }

    /**
     * Stops the current game by halting all game object spawning events and
     * clearing all active game objects. Logs a debug message indicating
     * the game has been stopped.
     */
    public void stopGame() {
        gameObjectSpawnScheduler.stopSpawning();
        gameObjects.clear();
        log.debug("Game stopped");
    }


    /**
     * Updates the game state by performing movement, object clean-up,
     * and collision detection for the current frame.
     *
     * @param deltaTime the time elapsed since the last update, used to calculate
     *                  changes in the positions and states of game objects.
     */
    public void update(double deltaTime) {
        moveElements(deltaTime);
        despawnPassedObjects();
        checkCollision();
    }

    /**
     * Moves all game objects based on the elapsed time and game state.
     *
     * @param deltaTime the amount of time (in seconds) that has passed since the last update,
     *                  which determines how far objects should move.
     */
    private void moveElements(double deltaTime) {
        for (GameObject gameObject : gameObjects) {
            boolean fastForwarding = playerHandler.isFastForwarding();
            gameObject.move(deltaTime, fastForwarding);
        }
    }



    /**
     * Spawns a new set of buildings in the game.
     * <p>
     * This method utilizes the `gameObjectCreationService` to create a list of building
     * game objects and adds them to the collection of active game objects.
     * It is typically used when a new set of buildings needs to be dynamically
     * generated during gameplay.
     */
    public void spawnBuilding() {
        List<Building> building = gameObjectCreationService.createBuildings();
        gameObjects.addAll(building);
    }

    /**
     * Spawns road marks in the game world by utilizing the game object creation service.
     * This method generates a list of road marks and adds them to the collection of game objects.
     * It is typically used to visually represent road boundaries or markings within the game.
     */
    public void spawnRoadMarks() {
        List<RoadMark> roadMark = gameObjectCreationService.createRoadMark();
        gameObjects.addAll(roadMark);
    }

    /**
     * Spawns one or more obstacles in the game environment.
     * <p>
     * This method uses the gameObjectCreationService to create a list of
     * obstacle objects and adds them to the game's collection of active
     * game objects. These obstacles can serve as challenges or interactable
     * objects within the game.
     *
     */
    public void spawnObstacle() {
        List<Obstacle> obstacle = gameObjectCreationService.createObstacle();
        gameObjects.addAll(obstacle);
    }

    /**
     * Spawns a new reward in the game area by creating a `Reward` instance
     * and adding it to the collection of active game objects.
     * <p>
     * This method utilizes a game object creation service to instantiate
     * the reward, ensuring it adheres to the game object's creation standards.
     */
    public void spawnReward() {
        Reward reward = gameObjectCreationService.createReward();
        gameObjects.add(reward);
    }

    /**
     * Spawns a new AI-controlled car in the game world.
     * <p>
     * This method uses the `gameObjectCreationService` to create an instance
     * of an AI car and then adds it to the `gameObjects` collection. The spawned
     * AI car allows for adding non-player-controlled vehicles to the gameplay.
     */
    public void spawnAICar() {
        AICar aiCar = gameObjectCreationService.createAICar();
        gameObjects.add(aiCar);
    }

    /**
     * Retrieves a list of all game objects currently in the game.
     *
     * @return a List containing all GameObject instances managed by the game.
     */
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

}
