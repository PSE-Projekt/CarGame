package de.cargame.model.handler.entity;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import lombok.extern.slf4j.Slf4j;

/**
 * The SinglePlayerSpawningStrategy class provides a specific implementation
 * for spawning game objects within a single-player game environment. It extends
 * the GameObjectSpawningStrategy and customizes the spawn areas for buildings,
 * obstacles, rewards, road marks, players, and AI cars tailored to a single-player mode.
 * <p>
 * The class utilizes configuration values loaded from the GameConfigService, ensuring
 * the spawn areas are dynamically defined based on the game's configuration.
 * <p>
 * This class overrides the abstract methods of GameObjectSpawningStrategy to implement
 * the logic specific to single-player mode, such as calculating spawn areas and logging
 * details for debugging purposes.
 */
@Slf4j
public class SinglePlayerSpawningStrategy extends GameObjectSpawningStrategy {


    private final int BUILDING_HEIGHT;
    private final int BUILDING_SPAWN_AREA_WIDTH;

    private final int ROAD_MARK_HEIGHT;

    private final int BUILDING_SPAWN_WIDTH;
    private final int ROAD_WIDTH;

    private final int ROADMARK_Y1;
    private final int ROADMARK_Y2;

    public SinglePlayerSpawningStrategy() {

        BUILDING_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT_SINGLEPLAYER);
        BUILDING_SPAWN_AREA_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH_SINGLEPLAYER);
        ROAD_MARK_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.ROAD_MARK_HEIGHT_SINGLEPLAYER);

        BUILDING_SPAWN_WIDTH = BUILDING_HEIGHT + BUILDING_SPAWN_AREA_WIDTH;
        ROAD_WIDTH = SCREEN_HEIGHT - 2 * (BUILDING_SPAWN_WIDTH);

        ROADMARK_Y1 = ROAD_WIDTH / 3 - (ROAD_MARK_HEIGHT / 2) + BUILDING_SPAWN_WIDTH;
        ROADMARK_Y2 = ROAD_WIDTH / 3 + ROADMARK_Y1;

        super.init();
    }

    @Override
    protected void setBuildingSpawnArea() {
        this.buildingSpawnAreas = generateBuildingSpawnAreas();
    }

    @Override
    protected void setObstacleSpawnArea() {
        int OBSTACLE_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.OBSTACLE_HEIGHT_SINGLEPLAYER);
        this.obstacleSpawnAreas.add(generateRoadSpawnArea(OBSTACLE_HEIGHT));
        log.debug("ObstacleSpawnAreas: {}", obstacleSpawnAreas);
    }

    @Override
    protected void setRewardSpawnArea() {
        this.rewardSpawnAreas.add(generateRoadSpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.REWARD_HEIGHT_SINGLEPLAYER)));
        log.debug("RewardSpawnAreas: {}", rewardSpawnAreas);
    }

    @Override
    protected void setRoadMarkSpawnArea() {
        SpawnArea spawnArea1 = new SpawnArea(SCREEN_WIDTH, ROADMARK_Y1, SCREEN_WIDTH, ROADMARK_Y1);
        SpawnArea spawnArea2 = new SpawnArea(SCREEN_WIDTH, ROADMARK_Y2, SCREEN_WIDTH, ROADMARK_Y2);
        this.roadSpawnAreas.add(spawnArea1);
        this.roadSpawnAreas.add(spawnArea2);
        log.debug("RoadSpawnAreas: {}", roadSpawnAreas);

    }

    @Override
    protected void setPlayerSpawnArea() {
        SpawnArea spawnArea = new SpawnArea(PLAYER_SPAWN_X, SCREEN_HALVE_Y, PLAYER_SPAWN_X, SCREEN_HALVE_Y);
        this.playerSpawnAreas.add(spawnArea);
        log.debug("PlayerSpawnAreas: {}", playerSpawnAreas);
    }

    @Override
    protected void setAiCarSpawnArea() {
        this.aiCarSpawnAreas.add(generateRoadSpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.AI_CAR_HEIGHT_SINGLEPLAYER)));
        log.debug("AiCarSpawnAreas: {}", aiCarSpawnAreas);
    }


    private SpawnArea generateRoadSpawnArea(int customHeight) {
        return new SpawnArea(SCREEN_WIDTH, BUILDING_SPAWN_WIDTH, SCREEN_WIDTH, SCREEN_HEIGHT - BUILDING_SPAWN_WIDTH - customHeight);
    }

    private SpawnAreaList generateBuildingSpawnAreas() {
        this.buildingSpawnAreas = new SpawnAreaList();
        buildingSpawnAreas.add(new SpawnArea(SCREEN_WIDTH, 0, SCREEN_WIDTH, BUILDING_SPAWN_AREA_WIDTH));
        buildingSpawnAreas.add(new SpawnArea(SCREEN_WIDTH, SCREEN_HEIGHT - BUILDING_SPAWN_WIDTH, SCREEN_WIDTH, SCREEN_HEIGHT - BUILDING_HEIGHT));

        return buildingSpawnAreas;
    }

}
