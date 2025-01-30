package de.cargame.model.handler.entity;

import de.cargame.config.GameConfig;
import lombok.Getter;

@Getter
public abstract class GameObjectSpawningStrategy {


    protected final int PLAYER_SPAWN_X = GameConfig.SCREEN_WIDTH / 5;
    protected final int SCREEN_HALVE_Y = GameConfig.SCREEN_HEIGHT / 2;
    protected final int SCREEN_QUARTER_Y = SCREEN_HALVE_Y / 2;


    protected SpawnAreaList buildingSpawnAreas = new SpawnAreaList();
    protected SpawnAreaList obstacleSpawnAreas = new SpawnAreaList();
    protected SpawnAreaList rewardSpawnAreas = new SpawnAreaList();
    protected SpawnAreaList roadSpawnAreas = new SpawnAreaList();
    protected SpawnAreaList playerSpawnAreas = new SpawnAreaList();
    protected SpawnAreaList aiCarSpawnAreas = new SpawnAreaList();


    public GameObjectSpawningStrategy() {
        setBuildingSpawnArea();
        setObstacleSpawnArea();
        setRewardSpawnArea();
        setRoadMarkSpawnArea();
        setPlayerSpawnArea();
        setAiCarSpawnArea();
    }

    protected abstract void setBuildingSpawnArea();

    protected abstract void setObstacleSpawnArea();

    protected abstract void setRewardSpawnArea();

    protected abstract void setRoadMarkSpawnArea();

    protected abstract void setPlayerSpawnArea();

    protected abstract void setAiCarSpawnArea();


}
