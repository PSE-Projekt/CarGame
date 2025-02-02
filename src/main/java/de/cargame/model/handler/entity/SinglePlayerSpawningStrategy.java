package de.cargame.model.handler.entity;

import de.cargame.config.GameConfig;

public class SinglePlayerSpawningStrategy extends GameObjectSpawningStrategy {

    private final int BUILDING_SPAWN_WIDTH = GameConfig.BUILDING_HEIGHT + GameConfig.BUILDING_SPAWN_AREA_WIDTH;
    private final int ROAD_WIDTH = GameConfig.SCREEN_HEIGHT - 2 * (BUILDING_SPAWN_WIDTH);

    private final int ROADMARK_Y1 = ROAD_WIDTH / 3 - (GameConfig.ROAD_MARK_HEIGHT/2) + BUILDING_SPAWN_WIDTH;
    private final int ROADMARK_Y2 = ROAD_WIDTH /3 +ROADMARK_Y1;

    public SinglePlayerSpawningStrategy() {
        super();
        setBuildingSpawnArea();
    }

    @Override
    protected void setBuildingSpawnArea() {
        this.buildingSpawnAreas = generateBuildingSpawnAreas();
    }

    @Override
    protected void setObstacleSpawnArea() {
        this.obstacleSpawnAreas.add(generateRoadSpawnArea(GameConfig.OBSTACLE_HEIGHT));
    }

    @Override
    protected void setRewardSpawnArea() {
        this.rewardSpawnAreas.add(generateRoadSpawnArea(GameConfig.REWARD_HEIGHT));
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
        SpawnArea spawnArea = new SpawnArea(PLAYER_SPAWN_X, SCREEN_HALVE_Y, PLAYER_SPAWN_X, SCREEN_HALVE_Y);
        this.playerSpawnAreas.add(spawnArea);
    }

    @Override
    protected void setAiCarSpawnArea() {
        this.aiCarSpawnAreas.add(generateRoadSpawnArea(GameConfig.AI_CAR_HEIGHT));
    }


    private SpawnArea generateRoadSpawnArea(int customHeight) {
        return new SpawnArea(GameConfig.SCREEN_WIDTH, BUILDING_SPAWN_WIDTH, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT - BUILDING_SPAWN_WIDTH - customHeight);
    }

    private SpawnAreaList generateBuildingSpawnAreas() {
        this.buildingSpawnAreas = new SpawnAreaList();
        buildingSpawnAreas.add(new SpawnArea(GameConfig.SCREEN_WIDTH, 0, GameConfig.SCREEN_WIDTH, GameConfig.BUILDING_SPAWN_AREA_WIDTH));
        buildingSpawnAreas.add(new SpawnArea(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT - BUILDING_SPAWN_WIDTH, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT - GameConfig.BUILDING_HEIGHT));

        return buildingSpawnAreas;
    }

}
