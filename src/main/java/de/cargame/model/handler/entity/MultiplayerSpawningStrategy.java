package de.cargame.model.handler.entity;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;

import java.util.List;

public class MultiplayerSpawningStrategy extends GameObjectSpawningStrategy {

    private final int ROADMARK_Y1 = GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_WIDTH) + (SCREEN_HALVE_Y - 2 * (GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT))) / 3;
    private final int ROADMARK_Y2 = ROADMARK_Y1 * 2;


    @Override
    protected void setBuildingSpawnArea() {
        this.buildingSpawnAreas = generateBuildingSpawnAreas();
    }

    @Override
    protected void setObstacleSpawnArea() {
        this.obstacleSpawnAreas.addAll(generateRoadSpawnAreas(GameConfigService.getInstance().loadInteger(ConfigKey.OBSTACLE_HEIGHT)));
    }

    @Override
    protected void setRewardSpawnArea() {
        this.rewardSpawnAreas.addAll(generateRoadSpawnAreas(GameConfigService.getInstance().loadInteger(ConfigKey.REWARD_HEIGHT)));
    }

    @Override
    protected void setRoadMarkSpawnArea() {
        SpawnArea spawnArea1 = new SpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), ROADMARK_Y1, GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), ROADMARK_Y1);
        SpawnArea spawnArea2 = new SpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), ROADMARK_Y2, GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), ROADMARK_Y2);

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
        SpawnArea upperRoadSpawnArea = new SpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT) + GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), SCREEN_HALVE_Y - GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH) - GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT) - customHeight);
        SpawnArea lowerRoadSpawnArea = new SpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), SCREEN_HALVE_Y + GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT) - GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH) - GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT) - customHeight);

        return List.of(upperRoadSpawnArea, lowerRoadSpawnArea);
    }

    private SpawnAreaList generateBuildingSpawnAreas() {
        this.buildingSpawnAreas = new SpawnAreaList();
        buildingSpawnAreas.add(new SpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), 0, GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH)));
        buildingSpawnAreas.add(new SpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), SCREEN_HALVE_Y - GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT) - GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), SCREEN_HALVE_Y - GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT)));

        buildingSpawnAreas.add(new SpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), SCREEN_HALVE_Y, GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), SCREEN_HALVE_Y + GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH)));
        buildingSpawnAreas.add(new SpawnArea(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT) - GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT) - GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT) - GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT)));
        return buildingSpawnAreas;
    }
}
