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
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * Contains the logic and elements for a game instance view.
 * 'render()' will display said instance.
 */
class GameInstanceView extends Pane {
    private final SpriteService spriteService;
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
     * Renders the game instance safely, avoiding ConcurrentModificationException.
     */
    void render() {
        // Step 1: Collect current objects from the model data
        List<GameObject> currentObjects = modelData.getGameObjects();

        // Step 2: Remove outdated or irrelevant GameObject views
        objectViewMap.keySet().removeIf(gameObject -> {
            // Remove if collected or no longer in current objects
            if (!currentObjects.contains(gameObject) || (gameObject instanceof Life && ((Life) gameObject).isCollected())) {
                GameObjectView outdatedView = objectViewMap.get(gameObject);
                if (this.getChildren().contains(outdatedView)) {
                    this.getChildren().remove(outdatedView);
                }
                return true; // Mark for removal from the map
            }
            return false; // Keep in map
        });

        // Step 3: Add new GameObject views
        for (GameObject gameObject : currentObjects) {
            // Skip collected Life objects
            if ((gameObject instanceof Life) && ((Life) gameObject).isCollected()) {
                continue; // Don't create a view for collected Lives
            }

            if (!objectViewMap.containsKey(gameObject)) {
                GameObjectView newView = createObjectView(gameObject);
                objectViewMap.put(gameObject, newView);

                // Avoid duplicates: add only if not already present
                if (!this.getChildren().contains(newView)) {
                    this.getChildren().add(newView);
                }
            }
        }

        // Step 4: Update properties of views that already exist
        for (GameObject gameObject : currentObjects) {
            GameObjectView view = objectViewMap.get(gameObject);
            if (view != null) {
                updateObjectView(view, gameObject);
            }
        }

        // Keep stats element on top
        stats.toFront();
    }


    /**
     * Creates a GameObjectView for a given GameObject and inserts it at the correct layer.
     */
    private GameObjectView createObjectView(GameObject gameObject) {
        GameObjectView objectView = getSpriteForGameObject(gameObject);
        if (objectView != null) {
            objectViewMap.put(gameObject, objectView);

            // Find the correct insertion index using compareTo
            int index = 0;
            for (; index < this.getChildren().size(); index++) {
                if (this.getChildren().get(index) instanceof GameObjectView existingView) {
                    if (existingView.compareTo(objectView) > 0) { // Uses compareTo method
                        break;
                    }
                }
            }

            this.getChildren().add(index, objectView);
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

        if (gameObject instanceof AgileCar agileCar && agileCar.hasCrashCooldown()) {
            objectView.setOpacity(0.5);
        } else if (gameObject instanceof FastCar fastCar && fastCar.hasCrashCooldown()) {
            objectView.setOpacity(0.5);
        } else {
            objectView.setOpacity(1);
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
