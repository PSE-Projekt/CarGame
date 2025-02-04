package de.cargame.model.service;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.exception.InvalidCarSelectionException;
import de.cargame.model.entity.gameobject.*;
import de.cargame.model.entity.gameobject.car.ai.*;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.car.player.FastCar;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;
import de.cargame.model.handler.entity.GameObjectSpawningStrategy;
import de.cargame.model.handler.entity.SpawnArea;
import de.cargame.model.handler.entity.SpawnAreaList;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;


@Slf4j
public class GameObjectCreationService {

    private final int FAST_CAR_WIDTH;
    private final int AGILE_CAR_WIDTH;
    private final int BUILDING_WIDTH;
    private final int ROAD_MARK_WIDTH;
    private final int OBSTACLE_WIDTH;
    private final int REWARD_WIDTH;
    private final int AI_CAR_WIDTH;
    private final int FAST_CAR_HEIGHT;
    private final int AGILE_CAR_HEIGHT;
    private final int BUILDING_HEIGHT;
    private final int ROAD_MARK_HEIGHT;
    private final int OBSTACLE_HEIGHT;
    private final int REWARD_HEIGHT;
    private final int AI_CAR_HEIGHT;
    @Setter
    private GameObjectSpawningStrategy gameObjectSpawningStrategy;


    public GameObjectCreationService() {
        this.FAST_CAR_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.FAST_CAR_WIDTH);
        this.AGILE_CAR_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.AGILE_CAR_WIDTH);
        this.BUILDING_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_WIDTH);
        this.ROAD_MARK_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.ROAD_MARK_WIDTH);
        this.OBSTACLE_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.OBSTACLE_WIDTH);
        this.REWARD_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.REWARD_WIDTH);
        this.AI_CAR_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.AI_CAR_WIDTH);
        this.FAST_CAR_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.FAST_CAR_HEIGHT);
        this.AGILE_CAR_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.AGILE_CAR_HEIGHT);
        this.BUILDING_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.BUILDING_HEIGHT);
        this.ROAD_MARK_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.ROAD_MARK_HEIGHT);
        this.OBSTACLE_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.OBSTACLE_HEIGHT);
        this.REWARD_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.REWARD_HEIGHT);
        this.AI_CAR_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.AI_CAR_HEIGHT);

    }


    public PlayerCar createPlayerCar(CarType carType) {
        Dimension dimension;
        SpawnAreaList playerSpawnAreas = gameObjectSpawningStrategy.getPlayerSpawnAreas();
        SpawnArea spawnArea = playerSpawnAreas.getRandomSpawnArea();
        playerSpawnAreas.remove(spawnArea);
        Coordinate spawnCoordinate = spawnArea.getRandomCoordinateInArea();

        switch (carType) {
            case FAST_CAR:
                dimension = new Dimension(FAST_CAR_WIDTH, FAST_CAR_HEIGHT);
                return new FastCar(spawnCoordinate, dimension, GameObjectBoundType.RECTANGLE);
            case AGILE_CAR:
                dimension = new Dimension(AGILE_CAR_WIDTH, AGILE_CAR_HEIGHT);
                return new AgileCar(spawnCoordinate, dimension, GameObjectBoundType.RECTANGLE);
        }
        log.error("No valid car-selection has been made");
        throw new InvalidCarSelectionException("No valid car-selection has been made");
    }

    public List<Building> createBuildings() {
        Dimension dimension = new Dimension(BUILDING_WIDTH, BUILDING_HEIGHT);
        SpawnAreaList spawnAreas = gameObjectSpawningStrategy.getBuildingSpawnAreas();
        List<Coordinate> spawnCoordinates = spawnAreas.getRandomCoordinateOfEach();
        return spawnCoordinates.stream()
                .map(c -> new Building(c, dimension, GameObjectBoundType.RECTANGLE))
                .toList();
    }


    public List<RoadMark> createRoadMark() {
        SpawnAreaList spawnAreas;
        spawnAreas = gameObjectSpawningStrategy.getRoadSpawnAreas();
        List<Coordinate> spawnCoordinates = spawnAreas.getRandomCoordinateOfEach();
        Dimension dimension = new Dimension(ROAD_MARK_WIDTH, ROAD_MARK_HEIGHT);
        return spawnCoordinates.stream()
                .map(c -> new RoadMark(c, dimension, GameObjectBoundType.RECTANGLE))
                .toList();
    }

    public List<Obstacle> createObstacle() {
        SpawnAreaList spawnAreas;
        spawnAreas = gameObjectSpawningStrategy.getObstacleSpawnAreas();
        List<Coordinate> spawnCoordinates = spawnAreas.getRandomCoordinateOfEach();
        Dimension dimension = new Dimension(OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
        return spawnCoordinates.stream()
                .map(c -> new Obstacle(c, dimension, GameObjectBoundType.RECTANGLE))
                .toList();

    }

    public Reward createReward() {
        SpawnAreaList spawnAreas = gameObjectSpawningStrategy.getRewardSpawnAreas();
        Coordinate spawnCoordinate = spawnAreas.getRandomCoordinate();
        Dimension dimension = new Dimension(REWARD_WIDTH, REWARD_HEIGHT);
        return new Life(spawnCoordinate, dimension, GameObjectBoundType.RECTANGLE);
    }


    public AICar createAICar() {
        Dimension dimension = new Dimension(AI_CAR_WIDTH, AI_CAR_HEIGHT);
        SpawnAreaList spawnAreas = gameObjectSpawningStrategy.getAiCarSpawnAreas();
        Coordinate spawnCoordinate = spawnAreas.getRandomCoordinate();

        AICarType aiCarType = getRandomAICarType();

        return switch (aiCarType) {
            case CROSS_MOVING ->
                    new KamikazeCar(spawnCoordinate, dimension, GameObjectBoundType.RECTANGLE, new CrossMovementStrategy(spawnCoordinate));
            case STRAIGHT_MOVING ->
                    new KamikazeCar(spawnCoordinate, dimension, GameObjectBoundType.RECTANGLE, new StraightMovementStrategy(spawnCoordinate));
        };
    }


    private AICarType getRandomAICarType() {
        return AICarType.values()[new Random().nextInt(AICarType.values().length)];
    }

}
