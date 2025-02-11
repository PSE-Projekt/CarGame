package de.cargame.model.handler;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.model.service.GameObjectService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * The GameObjectSpawnScheduler is responsible for managing the timed creation and spawning
 * of various game objects during gameplay. It schedules the spawning of AI cars, obstacles,
 * rewards, buildings, and road markings at randomized intervals.
 * <p>
 * This class uses a scheduled executor service to manage the spawning tasks. It also supports
 * a fast-forwarding mode, where the spawn intervals are adjusted based on a speed factor.
 * The spawn intervals and behavior are configured using values retrieved from the game configuration.
 */
@Slf4j
public class GameObjectSpawnScheduler {


    private final GameObjectService gameObjectService;
    private final PlayerHandler playerHandler;

    private final int GAME_SPEED;
    private final int GAME_SPEED_FAST_FORWARD;
    private final int AI_CAR_SPAWN_TIME_MIN;
    private final int AI_CAR_SPAWN_TIME_MAX;
    private final int OBSTACLE_SPAWN_TIME_MIN;
    private final int OBSTACLE_SPAWN_TIME_MAX;
    private final int REWARD_SPAWN_TIME_MIN;
    private final int REWARD_SPAWN_TIME_MAX;
    private final int BUILDING_SPAWN_TIME_MIN;
    private final int BUILDING_SPAWN_TIME_MAX;
    private final int ROAD_MARK_SPAWN_TIME_MIN;
    private final int ROAD_MARK_SPAWN_TIME_MAX;
    private final double fastForwardSpeedFactor;
    protected ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public GameObjectSpawnScheduler(PlayerHandler playerHandler, GameObjectService gameObjectService, GameStateAPI gameStateController) {
        this.playerHandler = playerHandler;
        this.gameObjectService = gameObjectService;
        GameMode gameMode = gameStateController.getGameMode();

        GameConfigService configService = GameConfigService.getInstance();


        GAME_SPEED = configService.loadInteger(ConfigKey.GAME_SPEED);
        GAME_SPEED_FAST_FORWARD = configService.loadInteger(ConfigKey.GAME_SPEED_FAST_FORWARD);
        REWARD_SPAWN_TIME_MIN = configService.loadInteger(ConfigKey.REWARD_SPAWN_TIME_MIN);
        REWARD_SPAWN_TIME_MAX = configService.loadInteger(ConfigKey.REWARD_SPAWN_TIME_MAX);
        BUILDING_SPAWN_TIME_MIN = configService.loadInteger(ConfigKey.BUILDING_SPAWN_TIME_MIN);
        BUILDING_SPAWN_TIME_MAX = configService.loadInteger(ConfigKey.BUILDING_SPAWN_TIME_MAX);
        ROAD_MARK_SPAWN_TIME_MIN = configService.loadInteger(ConfigKey.ROAD_MARK_SPAWN_TIME_MIN);
        ROAD_MARK_SPAWN_TIME_MAX = configService.loadInteger(ConfigKey.ROAD_MARK_SPAWN_TIME_MAX);
        fastForwardSpeedFactor = (double) GAME_SPEED / GAME_SPEED_FAST_FORWARD;


        if (gameMode == GameMode.SINGLEPLAYER) {
            AI_CAR_SPAWN_TIME_MIN = configService.loadInteger(ConfigKey.AI_CAR_SPAWN_TIME_MIN_SINGLEPLAYER);
            AI_CAR_SPAWN_TIME_MAX = configService.loadInteger(ConfigKey.AI_CAR_SPAWN_TIME_MAX_SINGLEPLAYER);
            OBSTACLE_SPAWN_TIME_MIN = configService.loadInteger(ConfigKey.OBSTACLE_SPAWN_TIME_MIN_SINGLEPLAYER);
            OBSTACLE_SPAWN_TIME_MAX = configService.loadInteger(ConfigKey.OBSTACLE_SPAWN_TIME_MAX_SINGLEPLAYER);
            return;
        } else if (gameMode == GameMode.MULTIPLAYER) {
            AI_CAR_SPAWN_TIME_MIN = configService.loadInteger(ConfigKey.AI_CAR_SPAWN_TIME_MIN_MULTIPLAYER);
            AI_CAR_SPAWN_TIME_MAX = configService.loadInteger(ConfigKey.AI_CAR_SPAWN_TIME_MAX_MULTIPLAYER);
            OBSTACLE_SPAWN_TIME_MIN = configService.loadInteger(ConfigKey.OBSTACLE_SPAWN_TIME_MIN_MULTIPLAYER);
            OBSTACLE_SPAWN_TIME_MAX = configService.loadInteger(ConfigKey.OBSTACLE_SPAWN_TIME_MAX_MULTIPLAYER);
            return;
        }
        throw new IllegalArgumentException("Game mode " + gameMode + " is not supported by the GameObjectSpawnScheduler");
    }


    /**
     * Starts the scheduled spawning of various game objects.
     * <p>
     * This method initiates the scheduling of different game elements, including AI cars,
     * obstacles, rewards, buildings, and road markings. Each type of game object is
     * scheduled to spawn at specific randomized intervals configured in the game settings.
     * The scheduling ensures continuous creation of these objects during gameplay.
     */
    public void startSpawning() {
        log.debug("Starting scheduled spawning of game objects");
        scheduleAICar();
        scheduleObstacle();
        scheduleReward();
        scheduleBuilding();
        scheduleRoadMark();
        log.debug("Scheduled spawning of game objects started successfully");
    }


    /**
     * Schedules the spawning of AI Cars at randomized intervals within predefined time ranges.
     */
    private void scheduleAICar() {
        scheduleSpawn(() -> gameObjectService::spawnAICar, AI_CAR_SPAWN_TIME_MIN, AI_CAR_SPAWN_TIME_MAX);
    }

    /**
     * Schedules the spawning of obstacles at randomized intervals within predefined time ranges.
     */
    private void scheduleObstacle() {
        scheduleSpawn(() -> gameObjectService::spawnObstacle, OBSTACLE_SPAWN_TIME_MIN, OBSTACLE_SPAWN_TIME_MAX);
    }

    /**
     * Schedules the spawning of rewards at randomized intervals within predefined time ranges.
     */
    private void scheduleReward() {
        scheduleSpawn(() -> gameObjectService::spawnReward, REWARD_SPAWN_TIME_MIN, REWARD_SPAWN_TIME_MAX);
    }

    /**
     * Schedules the spawning of buildings at randomized intervals within predefined time ranges.
     */
    private void scheduleBuilding() {
        scheduleSpawn(() -> gameObjectService::spawnBuilding, BUILDING_SPAWN_TIME_MIN, BUILDING_SPAWN_TIME_MAX);
    }


    /**
     * Schedules the spawning of road marks at randomized intervals within predefined time ranges.
     */
    private void scheduleRoadMark() {
        scheduleSpawn(() -> gameObjectService::spawnRoadMarks, ROAD_MARK_SPAWN_TIME_MIN, ROAD_MARK_SPAWN_TIME_MAX);
    }


    /**
     * Stops the currently scheduled spawning of game objects.
     * <p>
     * This method terminates all ongoing scheduled spawning tasks and shuts down the
     * current scheduler instance immediately. A new scheduler instance is created
     * to allow restarting spawning functionality if needed.
     */
    public void stopSpawning() {
        scheduler.shutdownNow();
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }


    /**
     * Generates a random delay value within the specified range, adjusted by fast-forwarding
     * speed if applicable.
     *
     * @param minDelay         The minimum delay in milliseconds. Will be capped to at least 1.
     * @param maxDelay         The maximum delay in milliseconds. Will be capped to at least 1.
     * @param isFastForwarding A flag indicating whether fast-forwarding mode is enabled.
     *                         If true, the delay range is adjusted by the fast-forwarding
     *                         speed factor.
     * @return A randomly generated delay value in milliseconds within the computed range.
     */
    private int getRandomDelay(int minDelay, int maxDelay, boolean isFastForwarding) {
        if (isFastForwarding) {
            minDelay = (int) Math.max(1, minDelay * fastForwardSpeedFactor);
            maxDelay = (int) Math.max(1, maxDelay * fastForwardSpeedFactor) + 1;
        } else {
            minDelay = Math.max(1, minDelay);
            maxDelay = Math.max(1, maxDelay) + 1;
        }
        return ThreadLocalRandom.current().nextInt(minDelay, maxDelay);
    }

    /**
     * Schedules a recurring task to spawn game objects with a randomized delay between executions.
     * The delay is generated within the specified range and adjusts for fast-forwarding if enabled.
     * Once the delay elapses, the spawn action is executed, and the method re-schedules itself.
     *
     * @param spawnAction A {@link Supplier} providing the {@link Runnable} task for spawning game objects.
     * @param minDelay    The minimum delay in milliseconds before the next execution. Will be adjusted based on fast-forwarding if applicable.
     * @param maxDelay    The maximum delay in milliseconds before the next execution. Will be adjusted based on fast-forwarding if applicable.
     */
    private void scheduleSpawn(Supplier<Runnable> spawnAction, int minDelay, int maxDelay) {
        boolean isFastForwarding = playerHandler.isFastForwarding();
        int initialDelay = getRandomDelay(minDelay, maxDelay, isFastForwarding);
        scheduler.schedule(() -> {
            spawnAction.get().run();
            scheduleSpawn(spawnAction, minDelay, maxDelay);
        }, initialDelay, TimeUnit.MILLISECONDS);
    }


}
