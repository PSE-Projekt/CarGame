package de.cargame.view.game;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.gameobject.*;
import de.cargame.model.entity.gameobject.car.ai.KamikazeCar;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.entity.gameobject.car.player.FastCar;
import de.cargame.view.ApiHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Contains the logic and elements for a game instance view.
 * 'render()' will display said instance.
 */
class GameInstanceView extends Pane {
    private final SpriteService spriteService;
    private final ApiHandler apiHandler;
    private final int SCREEN_HEIGHT;
    private final int SCREEN_WIDTH;
    private final PlayerStats stats;
    private GameModelData modelData;

    /**
     * Creates a new GameInstanceView for the player using the apiHandler as well his playerID
     */
    GameInstanceView(ApiHandler apiHandler, String playerID) {
        this.spriteService = new SpriteService();
        this.apiHandler = apiHandler;
        this.SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
        this.SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);

        // add player stats to view and register in backend as observer
        this.stats = new PlayerStats();
        stats.setLayoutX(0);
        stats.setLayoutY(10);
        stats.setAlignment(Pos.CENTER);
        stats.setPrefSize(SCREEN_WIDTH, 50);
        apiHandler.getPlayerApi().registerPlayerObserver(stats, playerID);

        List<GameModelData> gameModels = apiHandler.getGameInstanceApi().getModel();

        for (GameModelData data : gameModels) {
            if (data.getPlayerId().equals(playerID)) {
                this.modelData = data;
            }
        }

        int playerCount = apiHandler.getGameInstanceApi().getModel().size();

        if (this.modelData == null) {
            throw new IllegalArgumentException("Player ID not found in game model data");
        }

        this.setPrefSize(SCREEN_WIDTH, (double) SCREEN_HEIGHT / playerCount);
        this.setStyle("-fx-background-color: grey;");
        this.getChildren().add(stats);
    }

    /**
     * Renders the contents of this player's game instance.
     */
    void render() {
        this.getChildren().clear();

        Queue<GameObjectView> gameObjectViews = new PriorityQueue<>(GameObjectView::compareTo);
        List<GameObject> gameObjects = modelData.getGameObjects();

        for (GameObject gameObject : gameObjects) {
            GameObjectView objectView;

            if (gameObject instanceof Building) {
                objectView = spriteService.getRandomBuildingSprite(gameObject.getId());
            } else if (gameObject instanceof Obstacle) {
                objectView = spriteService.getRandomObstacleSprite(gameObject.getId());
            } else if (gameObject instanceof Life life) {
                if (life.isCollected()) {
                    continue;
                }
                objectView = spriteService.getRandomLifeSprite(gameObject.getId());
            } else if (gameObject instanceof KamikazeCar) {
                objectView = spriteService.getRandomKamikazeSprite(gameObject.getId());
            } else if (gameObject instanceof AgileCar agileCar) {
                objectView = spriteService.getRandomAgileCarSprite(gameObject.getId());
                objectView.setOpacity(agileCar.hasCrashCooldown() ? 0.5 : 1);
            } else if (gameObject instanceof FastCar fastCar) {
                objectView = spriteService.getRandomFastCarSprite(gameObject.getId());
                objectView.setOpacity(fastCar.hasCrashCooldown() ? 0.5 : 1);
            } else if (gameObject instanceof RoadMark) {
                objectView = spriteService.getRandomRoadMarkSprite(gameObject.getId());
            } else {
                throw new IllegalArgumentException("Unknown game object type");
            }

            objectView.setX(gameObject.getX());
            objectView.setY(gameObject.getY());
            objectView.setFitWidth(gameObject.getWidth());
            objectView.setFitHeight(gameObject.getHeight());

            gameObjectViews.add(objectView);
        }

        List<Node> toRender = new ArrayList<>(configureGreenArea());

        while (!gameObjectViews.isEmpty()) {
            toRender.add(gameObjectViews.poll());
        }

        this.getChildren().addAll(toRender);

        stats.toFront();
    }

    private List<Pane> configureGreenArea() {
        int BUILDING_HEIGHT;
        int BUILDING_SPAWN_AREA;

        GameStateAPI gameStateApi = apiHandler.getGameStateApi();
        GameConfigService configService = GameConfigService.getInstance();

        if (gameStateApi.getGameMode().equals(GameMode.MULTIPLAYER)) {
            BUILDING_HEIGHT = configService.loadInteger(ConfigKey.BUILDING_HEIGHT_MULTIPLAYER);
            BUILDING_SPAWN_AREA = configService.loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH_MULTIPLAYER);
        } else if (gameStateApi.getGameMode().equals(GameMode.SINGLEPLAYER)) {
            BUILDING_HEIGHT = configService.loadInteger(ConfigKey.BUILDING_HEIGHT_SINGLEPLAYER);
            BUILDING_SPAWN_AREA = configService.loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH_SINGLEPLAYER);
        } else {
            throw new IllegalStateException("Game mode invalid");
        }

        int greenAreaHeight = BUILDING_SPAWN_AREA + BUILDING_HEIGHT + 10;

        Pane greenAreaUp = new Pane();
        greenAreaUp.setPrefSize(SCREEN_WIDTH, greenAreaHeight);
        greenAreaUp.setStyle("-fx-background-color: green;");

        Pane greenAreaDown = new Pane();
        greenAreaDown.setPrefSize(SCREEN_WIDTH, greenAreaHeight);
        greenAreaDown.setStyle("-fx-background-color: green;");

        greenAreaUp.setLayoutX(0);
        greenAreaUp.setLayoutY(0);

        greenAreaDown.setLayoutX(0);
        greenAreaDown.setLayoutY(this.getPrefHeight() - greenAreaHeight);

        Pane sideMarkUp = new Pane();
        sideMarkUp.setPrefSize(SCREEN_WIDTH, 10);
        sideMarkUp.setStyle("-fx-background-color: white;");
        sideMarkUp.setLayoutX(0);
        sideMarkUp.setLayoutY(greenAreaHeight + 10);

        Pane sideMarkDown = new Pane();
        sideMarkDown.setPrefSize(SCREEN_WIDTH, 10);
        sideMarkDown.setStyle("-fx-background-color: white;");
        sideMarkDown.setLayoutX(0);
        sideMarkDown.setLayoutY(this.getPrefHeight() - greenAreaHeight - 20);

        return List.of(greenAreaUp, greenAreaDown, sideMarkUp, sideMarkDown, stats);
    }
}
