package de.cargame.model.service;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
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

    private  int FAST_CAR_WIDTH;
    private  int AGILE_CAR_WIDTH;
    private  int BUILDING_WIDTH;
    private  int ROAD_MARK_WIDTH;
    private  int OBSTACLE_WIDTH;
    private  int REWARD_WIDTH;
    private  int AI_CAR_WIDTH;
    private  int FAST_CAR_HEIGHT;
    private  int AGILE_CAR_HEIGHT;
    private  int BUILDING_HEIGHT;
    private  int ROAD_MARK_HEIGHT;
    private  int OBSTACLE_HEIGHT;
    private  int REWARD_HEIGHT;
    private  int AI_CAR_HEIGHT;
    @Setter
    private GameObjectSpawningStrategy gameObjectSpawningStrategy;
    @Setter
    private GameMode gameMode;

    private final GameConfigService configService = GameConfigService.getInstance();

    public GameObjectCreationService() {


    }


    public void init(){
        if(gameMode == GameMode.SINGLEPLAYER) {
            this.FAST_CAR_WIDTH = configService.loadInteger(ConfigKey.FAST_CAR_WIDTH_SINGLEPLAYER);
            this.AGILE_CAR_WIDTH = configService.loadInteger(ConfigKey.AGILE_CAR_WIDTH_SINGLEPLAYER);
            this.BUILDING_WIDTH = configService.loadInteger(ConfigKey.BUILDING_WIDTH_SINGLEPLAYER);
            this.ROAD_MARK_WIDTH = configService.loadInteger(ConfigKey.ROAD_MARK_WIDTH_SINGLEPLAYER);
            this.OBSTACLE_WIDTH = configService.loadInteger(ConfigKey.OBSTACLE_WIDTH_SINGLEPLAYER);
            this.REWARD_WIDTH = configService.loadInteger(ConfigKey.REWARD_WIDTH_SINGLEPLAYER);
            this.AI_CAR_WIDTH = configService.loadInteger(ConfigKey.AI_CAR_WIDTH_SINGLEPLAYER);
            this.FAST_CAR_HEIGHT = configService.loadInteger(ConfigKey.FAST_CAR_HEIGHT_SINGLEPLAYER);
            this.AGILE_CAR_HEIGHT = configService.loadInteger(ConfigKey.AGILE_CAR_HEIGHT_SINGLEPLAYER);
            this.BUILDING_HEIGHT = configService.loadInteger(ConfigKey.BUILDING_HEIGHT_SINGLEPLAYER);
            this.ROAD_MARK_HEIGHT = configService.loadInteger(ConfigKey.ROAD_MARK_HEIGHT_SINGLEPLAYER);
            this.OBSTACLE_HEIGHT = configService.loadInteger(ConfigKey.OBSTACLE_HEIGHT_SINGLEPLAYER);
            this.REWARD_HEIGHT = configService.loadInteger(ConfigKey.REWARD_HEIGHT_SINGLEPLAYER);
            this.AI_CAR_HEIGHT = configService.loadInteger(ConfigKey.AI_CAR_HEIGHT_SINGLEPLAYER);
        }else {
            this.FAST_CAR_WIDTH = configService.loadInteger(ConfigKey.FAST_CAR_WIDTH_MULTIPLAYER);
            this.AGILE_CAR_WIDTH = configService.loadInteger(ConfigKey.AGILE_CAR_WIDTH_MULTIPLAYER);
            this.BUILDING_WIDTH = configService.loadInteger(ConfigKey.BUILDING_WIDTH_MULTIPLAYER);
            this.ROAD_MARK_WIDTH = configService.loadInteger(ConfigKey.ROAD_MARK_WIDTH_MULTIPLAYER);
            this.OBSTACLE_WIDTH = configService.loadInteger(ConfigKey.OBSTACLE_WIDTH_MULTIPLAYER);
            this.REWARD_WIDTH = configService.loadInteger(ConfigKey.REWARD_WIDTH_MULTIPLAYER);
            this.AI_CAR_WIDTH = configService.loadInteger(ConfigKey.AI_CAR_WIDTH_MULTIPLAYER);
            this.FAST_CAR_HEIGHT = configService.loadInteger(ConfigKey.FAST_CAR_HEIGHT_MULTIPLAYER);
            this.AGILE_CAR_HEIGHT = configService.loadInteger(ConfigKey.AGILE_CAR_HEIGHT_MULTIPLAYER);
            this.BUILDING_HEIGHT = configService.loadInteger(ConfigKey.BUILDING_HEIGHT_MULTIPLAYER);
            this.ROAD_MARK_HEIGHT = configService.loadInteger(ConfigKey.ROAD_MARK_HEIGHT_MULTIPLAYER);
            this.OBSTACLE_HEIGHT = configService.loadInteger(ConfigKey.OBSTACLE_HEIGHT_MULTIPLAYER);
            this.REWARD_HEIGHT = configService.loadInteger(ConfigKey.REWARD_HEIGHT_MULTIPLAYER);
            this.AI_CAR_HEIGHT = configService.loadInteger(ConfigKey.AI_CAR_HEIGHT_MULTIPLAYER);
        }
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
                return new FastCar(spawnCoordinate, dimension, GameObjectBoundType.RECTANGLE, gameMode);
            case AGILE_CAR:
                dimension = new Dimension(AGILE_CAR_WIDTH, AGILE_CAR_HEIGHT);
                return new AgileCar(spawnCoordinate, dimension, GameObjectBoundType.RECTANGLE, gameMode);
        }
        log.error("No valid car-selection has been made");
        throw new InvalidCarSelectionException("No valid car-selection has been made");
    }

    public List<Building> createBuildings() {
        Dimension dimension = new Dimension(BUILDING_WIDTH, BUILDING_HEIGHT);
        SpawnAreaList spawnAreas = gameObjectSpawningStrategy.getBuildingSpawnAreas();
        List<Coordinate> spawnCoordinates = spawnAreas.getRandomCoordinateOfEach();
        return spawnCoordinates.stream()
                .map(c -> new Building(c, dimension, GameObjectBoundType.RECTANGLE, gameMode))
                .toList();
    }


    public List<RoadMark> createRoadMark() {
        SpawnAreaList spawnAreas;
        spawnAreas = gameObjectSpawningStrategy.getRoadSpawnAreas();
        List<Coordinate> spawnCoordinates = spawnAreas.getRandomCoordinateOfEach();
        Dimension dimension = new Dimension(ROAD_MARK_WIDTH, ROAD_MARK_HEIGHT);
        return spawnCoordinates.stream()
                .map(c -> new RoadMark(c, dimension, GameObjectBoundType.RECTANGLE, gameMode))
                .toList();
    }

    public List<Obstacle> createObstacle() {
        SpawnAreaList spawnAreas;
        spawnAreas = gameObjectSpawningStrategy.getObstacleSpawnAreas();
        List<Coordinate> spawnCoordinates = spawnAreas.getRandomCoordinateOfEach();
        Dimension dimension = new Dimension(OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
        return spawnCoordinates.stream()
                .map(c -> new Obstacle(c, dimension, GameObjectBoundType.RECTANGLE, gameMode))
                .toList();

    }

    public Reward createReward() {
        SpawnAreaList spawnAreas = gameObjectSpawningStrategy.getRewardSpawnAreas();
        Coordinate spawnCoordinate = spawnAreas.getRandomCoordinate();
        Dimension dimension = new Dimension(REWARD_WIDTH, REWARD_HEIGHT);
        return new Life(spawnCoordinate, dimension, GameObjectBoundType.RECTANGLE, gameMode);
    }


    public AICar createAICar() {
        Dimension dimension = new Dimension(AI_CAR_WIDTH, AI_CAR_HEIGHT);
        SpawnAreaList spawnAreas = gameObjectSpawningStrategy.getAiCarSpawnAreas();
        Coordinate spawnCoordinate = spawnAreas.getRandomCoordinate();

        AICarType aiCarType = getRandomAICarType();

        return switch (aiCarType) {
            case CROSS_MOVING ->
                    new KamikazeCar(spawnCoordinate, dimension, GameObjectBoundType.RECTANGLE, new CrossMovementStrategy(spawnCoordinate, gameMode), gameMode);
            case STRAIGHT_MOVING ->
                    new KamikazeCar(spawnCoordinate, dimension, GameObjectBoundType.RECTANGLE, new StraightMovementStrategy(spawnCoordinate, gameMode), gameMode);
        };
    }


    private AICarType getRandomAICarType() {
        return AICarType.values()[new Random().nextInt(AICarType.values().length)];
    }

}
