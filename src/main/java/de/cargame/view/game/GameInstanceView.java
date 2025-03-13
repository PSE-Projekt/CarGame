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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.*;

/**
 * Contains the logic and elements for a game instance view.
 * 'render()' will display said instance.
 */
class GameInstanceView extends Pane {
    private final SpriteService spriteService;
    private final Set<Pane> gameUiSet;
    private final Map<GameObject, GameObjectView> objectViewMap;
    private final ApiHandler apiHandler;
    private final int SCREEN_HEIGHT;
    private final int SCREEN_WIDTH;
    private final PlayerStats stats;
    private GameModelData modelData;

    /**
     * Creates a new GameInstanceView for the player using the apiHandler as well as his playerID
     */
    public GameInstanceView(ApiHandler apiHandler, String playerID) {
        this.objectViewMap = new HashMap<>();
        this.gameUiSet = new HashSet<>();
        this.spriteService = new SpriteService();
        this.apiHandler = apiHandler;
        this.SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
        this.SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);

        // Initialize player stats
        this.stats = new PlayerStats();
        stats.setLayoutX(0);
        stats.setLayoutY(10);
        stats.setAlignment(Pos.CENTER);
        stats.setPrefSize(SCREEN_WIDTH, 50);
        apiHandler.getPlayerApi().registerPlayerObserver(stats, playerID);

        this.modelData = apiHandler.getGameInstanceApi().getModel()
                .stream()
                .filter(data -> data.getPlayerId().equals(playerID))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player ID not found in game model data"));

        int playerCount = apiHandler.getGameInstanceApi().getModel().size();
        this.setPrefSize(SCREEN_WIDTH, (double) SCREEN_HEIGHT / playerCount);
        this.setStyle("-fx-background-color: grey;");

        this.getChildren().add(stats);
        configureGreenArea();
    }

    /**
     * Renders the game instance.
     */
    void render() {
        List<GameObject> gameObjects = modelData.getGameObjects();

        // Remove objects that no longer exist in the game
        objectViewMap.entrySet().removeIf(entry -> !gameObjects.contains(entry.getKey()));

        for (GameObject gameObject : gameObjects) {
            objectViewMap.computeIfAbsent(gameObject, this::createObjectView);
            GameObjectView objectView = objectViewMap.get(gameObject);

            if (objectView != null) {
                updateObjectView(objectView, gameObject);
            }
        }
        stats.toFront();
    }

    /**
     * Creates a GameObjectView for a given GameObject.
     */
    private GameObjectView createObjectView(GameObject gameObject) {
        GameObjectView objectView = getSpriteForGameObject(gameObject);
        if (objectView != null) {
            this.getChildren().add(objectView);
        }
        return objectView;
    }

    /**
     * Retrieves the appropriate sprite for the given GameObject.
     */
    private GameObjectView getSpriteForGameObject(GameObject gameObject) {
        if (gameObject instanceof Building) {
            return spriteService.getRandomBuildingSprite(gameObject.getId());
        } else if (gameObject instanceof Obstacle) {
            return spriteService.getRandomObstacleSprite(gameObject.getId());
        } else if (gameObject instanceof Life life) {
            return life.isCollected() ? null : spriteService.getRandomLifeSprite(gameObject.getId());
        } else if (gameObject instanceof KamikazeCar) {
            return spriteService.getRandomKamikazeSprite(gameObject.getId());
        } else if (gameObject instanceof AgileCar agileCar) {
            GameObjectView sprite = spriteService.getRandomAgileCarSprite(gameObject.getId());
            sprite.setOpacity(agileCar.hasCrashCooldown() ? 0.5 : 1);
            return sprite;
        } else if (gameObject instanceof FastCar fastCar) {
            GameObjectView sprite = spriteService.getRandomFastCarSprite(gameObject.getId());
            sprite.setOpacity(fastCar.hasCrashCooldown() ? 0.5 : 1);
            return sprite;
        } else if (gameObject instanceof RoadMark) {
            return spriteService.getRandomRoadMarkSprite(gameObject.getId());
        } else {
            throw new IllegalArgumentException("Unknown game object type");
        }
    }

    /**
     * Updates the position and size of a GameObjectView.
     */
    private void updateObjectView(GameObjectView objectView, GameObject gameObject) {
        if (objectView.getX() != gameObject.getX()) {
            objectView.setX(gameObject.getX());
        }
        if (objectView.getY() != gameObject.getY()) {
            objectView.setY(gameObject.getY());
        }
        if (objectView.getFitWidth() != gameObject.getWidth()) {
            objectView.setFitWidth(gameObject.getWidth());
        }
        if (objectView.getFitHeight() != gameObject.getHeight()) {
            objectView.setFitHeight(gameObject.getHeight());
        }
    }

    /**
     * Configures the green area with buildings.
     */
    void configureGreenArea() {
        GameStateAPI gameStateApi = apiHandler.getGameStateApi();
        GameConfigService configService = GameConfigService.getInstance();

        int BUILDING_HEIGHT;
        int BUILDING_SPAWN_AREA;

        if (gameStateApi.getGameMode().equals(GameMode.MULTIPLAYER)) {
            BUILDING_HEIGHT = configService.loadInteger(ConfigKey.BUILDING_HEIGHT_MULTIPLAYER);
            BUILDING_SPAWN_AREA = configService.loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH_MULTIPLAYER);
        } else {
            BUILDING_HEIGHT = configService.loadInteger(ConfigKey.BUILDING_HEIGHT_SINGLEPLAYER);
            BUILDING_SPAWN_AREA = configService.loadInteger(ConfigKey.BUILDING_SPAWN_AREA_WIDTH_SINGLEPLAYER);
        }

        int greenAreaHeight = BUILDING_SPAWN_AREA + BUILDING_HEIGHT + 10;

        Pane greenAreaUp = createGreenPane(greenAreaHeight, 0);
        Pane greenAreaDown = createGreenPane(greenAreaHeight, this.getPrefHeight() - greenAreaHeight);
        Pane sideMarkUp = createSideMark(greenAreaHeight + 10);
        Pane sideMarkDown = createSideMark(this.getPrefHeight() - greenAreaHeight - 20);

        this.getChildren().addAll(greenAreaUp, greenAreaDown, sideMarkUp, sideMarkDown);
    }

    private Pane createGreenPane(int height, double y) {
        Pane pane = new Pane();
        pane.setPrefSize(SCREEN_WIDTH, height);
        pane.setStyle("-fx-background-color: green;");
        pane.setLayoutY(y);
        return pane;
    }

    private Pane createSideMark(double y) {
        Pane pane = new Pane();
        pane.setPrefSize(SCREEN_WIDTH, 10);
        pane.setStyle("-fx-background-color: white;");
        pane.setLayoutY(y);
        return pane;
    }
}
