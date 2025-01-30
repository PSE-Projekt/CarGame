package de.cargame.model.handler;


import de.cargame.config.GameConfig;
import de.cargame.model.service.GameObjectService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class GameObjectSpawnScheduler {
    private final double fastForwardSpeedFactor = (double) GameConfig.GAME_SPEED / GameConfig.GAME_SPEED_FAST_FORWARD;
    private final GameObjectService gameObjectService;
    private final PlayerHandler playerHandler;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public GameObjectSpawnScheduler(PlayerHandler playerHandler, GameObjectService gameObjectService) {
        this.playerHandler = playerHandler;
        this.gameObjectService = gameObjectService;
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
        scheduleAICar();
        scheduleObstacle();
        scheduleReward();
        scheduleBuilding();
        scheduleRoadMark();

    }


    /**
     * Schedules the spawning of AI Cars at randomized intervals within predefined time ranges.
     */
    private void scheduleAICar() {
        scheduleSpawn(() -> gameObjectService::spawnAICar, GameConfig.AI_CAR_SPAWN_TIME_MIN, GameConfig.AI_CAR_SPAWN_TIME_MAX);
    }

    /**
     * Schedules the spawning of obstacles at randomized intervals within predefined time ranges.
     */
    private void scheduleObstacle() {
        scheduleSpawn(() -> gameObjectService::spawnObstacle, GameConfig.OBSTACLE_SPAWN_TIME_MIN, GameConfig.OBSTACLE_SPAWN_TIME_MAX);
    }

    /**
     * Schedules the spawning of rewards at randomized intervals within predefined time ranges.
     */
    private void scheduleReward() {
        scheduleSpawn(() -> gameObjectService::spawnReward, GameConfig.REWARD_SPAWN_TIME_MIN, GameConfig.REWARD_SPAWN_TIME_MAX);
    }

    /**
     * Schedules the spawning of buildings at randomized intervals within predefined time ranges.
     */
    private void scheduleBuilding() {
        scheduleSpawn(() -> gameObjectService::spawnBuilding, GameConfig.BUILDING_SPAWN_TIME_MIN, GameConfig.BUILDING_SPAWN_TIME_MAX);
    }


    /**
     * Schedules the spawning of road marks at randomized intervals within predefined time ranges.
     */
    private void scheduleRoadMark() {
        scheduleSpawn(() -> gameObjectService::spawnRoadMarks, GameConfig.ROAD_MARK_SPAWN_TIME_MIN, GameConfig.ROAD_MARK_SPAWN_TIME_MAX);
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
