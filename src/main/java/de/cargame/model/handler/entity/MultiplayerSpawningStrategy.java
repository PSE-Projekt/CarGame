package de.cargame.model.handler.entity;

import de.cargame.config.GameConfig;

import java.util.List;

public class MultiplayerSpawningStrategy extends GameObjectSpawningStrategy {

    private final int ROADMARK_Y1 = GameConfig.BUILDING_WIDTH + (SCREEN_HALVE_Y - 2 * (GameConfig.BUILDING_HEIGHT)) / 3;
    private final int ROADMARK_Y2 = ROADMARK_Y1 * 2;


    @Override
    protected void setBuildingSpawnArea() {
        this.buildingSpawnAreas = generateBuildingSpawnAreas();
    }

    @Override
    protected void setObstacleSpawnArea() {
        this.obstacleSpawnAreas.addAll(generateRoadSpawnAreas(GameConfig.OBSTACLE_HEIGHT));
    }

    @Override
    protected void setRewardSpawnArea() {
        this.rewardSpawnAreas.addAll(generateRoadSpawnAreas(GameConfig.REWARD_HEIGHT));
    }

    @Override
    protected void setRoadMarkSpawnArea() {
        SpawnArea spawnArea1 = new SpawnArea(GameConfig.SCREEN_WIDTH, ROADMARK_Y1, GameConfig.SCREEN_WIDTH, ROADMARK_Y1);
        SpawnArea spawnArea2 = new SpawnArea(GameConfig.SCREEN_WIDTH, ROADMARK_Y2, GameConfig.SCREEN_WIDTH, ROADMARK_Y2);

        this.roadSpawnAreas.add(spawnArea1);
        this.roadSpawnAreas.add(spawnArea2);
    }

    @Override
    protected void setPlayerSpawnArea() {
        SpawnArea upperPlayerSpawnArea = new SpawnArea(PLAYER_SPAWN_X, SCREEN_QUARTER_Y, PLAYER_SPAWN_X, SCREEN_QUARTER_Y);
        SpawnArea lowerPlayerSpawnArea = new SpawnArea(PLAYER_SPAWN_X, SCREEN_QUARTER_Y * 3, PLAYER_SPAWN_X, SCREEN_QUARTER_Y * 3);
        this.playerSpawnAreas.add(upperPlayerSpawnArea);
        this.playerSpawnAreas.add(lowerPlayerSpawnArea);
    }

    @Override
    protected void setAiCarSpawnArea() {

    }


    private List<SpawnArea> generateRoadSpawnAreas(int customHeight) {
        SpawnArea upperRoadSpawnArea = new SpawnArea(GameConfig.SCREEN_WIDTH, GameConfig.BUILDING_HEIGHT + GameConfig.BUILDING_SPAWN_WIDTH, GameConfig.SCREEN_WIDTH, SCREEN_HALVE_Y - GameConfig.BUILDING_SPAWN_WIDTH - GameConfig.BUILDING_HEIGHT - customHeight);
        SpawnArea lowerRoadSpawnArea = new SpawnArea(GameConfig.SCREEN_WIDTH, SCREEN_HALVE_Y + GameConfig.BUILDING_SPAWN_WIDTH, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT - GameConfig.BUILDING_SPAWN_WIDTH - GameConfig.BUILDING_HEIGHT - customHeight);

        return List.of(upperRoadSpawnArea, lowerRoadSpawnArea);
    }

    private SpawnAreaList generateBuildingSpawnAreas() {
        this.buildingSpawnAreas = new SpawnAreaList();
        buildingSpawnAreas.add(new SpawnArea(GameConfig.SCREEN_WIDTH, 0, GameConfig.SCREEN_WIDTH, GameConfig.BUILDING_SPAWN_WIDTH));
        buildingSpawnAreas.add(new SpawnArea(GameConfig.SCREEN_WIDTH, SCREEN_HALVE_Y - GameConfig.BUILDING_HEIGHT - GameConfig.BUILDING_SPAWN_WIDTH, GameConfig.SCREEN_WIDTH, SCREEN_HALVE_Y - GameConfig.BUILDING_HEIGHT));

        buildingSpawnAreas.add(new SpawnArea(GameConfig.SCREEN_WIDTH, SCREEN_HALVE_Y, GameConfig.SCREEN_WIDTH, SCREEN_HALVE_Y + GameConfig.BUILDING_SPAWN_WIDTH));
        buildingSpawnAreas.add(new SpawnArea(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT - GameConfig.BUILDING_HEIGHT - GameConfig.BUILDING_SPAWN_WIDTH, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT - GameConfig.BUILDING_HEIGHT));
        return buildingSpawnAreas;
    }
}
