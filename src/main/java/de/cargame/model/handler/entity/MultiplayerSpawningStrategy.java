package de.cargame.model.handler.entity;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import lombok.extern.slf4j.Slf4j;

/**
 * The MultiplayerSpawningStrategy class extends the GameObjectSpawningStrategy
 * and provides a specific implementation for determining spawn areas for
 * different game objects in a multiplayer game mode. It utilizes values
 * from the game configuration to compute spawn areas for buildings,
 * obstacles, rewards, road marks, players, and AI-controlled cars.
 */
@Slf4j
public class MultiplayerSpawningStrategy extends GameObjectSpawningStrategy {

    private final int BUILDING_HEIGHT;
    private final int BUILDING_SPAWN_AREA_WIDTH;

    private final int ROAD_MARK_HEIGHT;

    private final int BUILDING_SPAWN_WIDTH;
    private final int ROAD_WIDTH;

    private final int ROADMARK_Y1;
    private final int ROADMARK_Y2;

    public MultiplayerSpawningStrategy() {

        BUILDING_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT_MULTIPLAYER);
        BUILDING_SPAWN_AREA_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH_MULTIPLAYER);
        ROAD_MARK_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.ROAD_MARK_HEIGHT_MULTIPLAYER);

        BUILDING_SPAWN_WIDTH = BUILDING_HEIGHT + BUILDING_SPAWN_AREA_WIDTH;
        ROAD_WIDTH = SCREEN_HALVE_Y - 2 * (BUILDING_SPAWN_WIDTH);

        ROADMARK_Y1 = ROAD_WIDTH / 3 - (ROAD_MARK_HEIGHT / 2) + BUILDING_SPAWN_WIDTH;
        ROADMARK_Y2 = ROAD_WIDTH / 3 + ROADMARK_Y1;

        super.init();
    }

    @Override
    protected void setBuildingSpawnArea() {
        this.buildingSpawnAreas = generateBuildingSpawnAreas();
        log.debug("BuildingSpawnAreas: {}", buildingSpawnAreas);
    }

    @Override
    protected void setObstacleSpawnArea() {
        int OBSTACLE_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.OBSTACLE_HEIGHT_MULTIPLAYER);
        this.obstacleSpawnAreas.add(generateRoadSpawnArea(OBSTACLE_HEIGHT));
        log.debug("ObstacleSpawnAreas: {}", obstacleSpawnAreas);

    }

    @Override
    protected void setRewardSpawnArea() {
        this.rewardSpawnAreas.add(generateRoadSpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.REWARD_HEIGHT_MULTIPLAYER)));
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
        SpawnArea spawnArea = new SpawnArea(PLAYER_SPAWN_X, SCREEN_QUARTER_Y, PLAYER_SPAWN_X, SCREEN_QUARTER_Y);
        this.playerSpawnAreas.add(spawnArea);
        log.debug("PlayerSpawnAreas: {}", playerSpawnAreas);
    }

    @Override
    protected void setAiCarSpawnArea() {
        this.aiCarSpawnAreas.add(generateRoadSpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.AI_CAR_HEIGHT_MULTIPLAYER)));
        log.debug("AiCarSpawnAreas: {}", aiCarSpawnAreas);
    }


    private SpawnArea generateRoadSpawnArea(int customHeight) {
        return new SpawnArea(SCREEN_WIDTH, BUILDING_SPAWN_WIDTH, SCREEN_WIDTH, SCREEN_HALVE_Y - BUILDING_SPAWN_WIDTH - customHeight);
    }

    private SpawnAreaList generateBuildingSpawnAreas() {
        this.buildingSpawnAreas = new SpawnAreaList();
        buildingSpawnAreas.add(new SpawnArea(SCREEN_WIDTH, 0, SCREEN_WIDTH, BUILDING_SPAWN_AREA_WIDTH));
        buildingSpawnAreas.add(new SpawnArea(SCREEN_WIDTH, SCREEN_HALVE_Y - BUILDING_SPAWN_WIDTH, SCREEN_WIDTH, SCREEN_HALVE_Y - BUILDING_HEIGHT));

        return buildingSpawnAreas;
    }
}
