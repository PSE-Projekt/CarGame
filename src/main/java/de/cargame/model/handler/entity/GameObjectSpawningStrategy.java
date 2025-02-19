package de.cargame.model.handler.entity;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract class providing a base implementation for strategies responsible
 * for spawning different game objects within a game environment.
 * Subclasses are expected to define the specific spawn areas for each type of
 * game object by implementing the abstract methods provided.
 */
@Getter
@Slf4j
public abstract class GameObjectSpawningStrategy {

    protected final int SCREEN_HEIGHT;
    protected final int SCREEN_WIDTH;
    protected final int PLAYER_SPAWN_X;
    protected final int SCREEN_HALVE_Y;
    protected final int SCREEN_QUARTER_Y;


    protected SpawnAreaList buildingSpawnAreas = new SpawnAreaList();
    protected SpawnAreaList obstacleSpawnAreas = new SpawnAreaList();
    protected SpawnAreaList rewardSpawnAreas = new SpawnAreaList();
    protected SpawnAreaList roadSpawnAreas = new SpawnAreaList();
    protected SpawnAreaList playerSpawnAreas = new SpawnAreaList();
    protected SpawnAreaList aiCarSpawnAreas = new SpawnAreaList();


    protected GameObjectSpawningStrategy() {
        this.SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);
        this.SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
        this.PLAYER_SPAWN_X = SCREEN_WIDTH / 5;
        this.SCREEN_HALVE_Y = SCREEN_HEIGHT / 2;
        SCREEN_QUARTER_Y = SCREEN_HALVE_Y / 2;

    }

    protected void init() {
        log.debug("Start initializing SpawnAreas");
        setBuildingSpawnArea();
        setObstacleSpawnArea();
        setRewardSpawnArea();
        setRoadMarkSpawnArea();
        setPlayerSpawnArea();
        setAiCarSpawnArea();
        log.debug("Finished initializing SpawnAreas");
    }

    protected abstract void setBuildingSpawnArea();

    protected abstract void setObstacleSpawnArea();

    protected abstract void setRewardSpawnArea();

    protected abstract void setRoadMarkSpawnArea();

    protected abstract void setPlayerSpawnArea();

    protected abstract void setAiCarSpawnArea();


}
