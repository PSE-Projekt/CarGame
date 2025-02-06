package de.cargame.view.game;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.gameobject.*;
import de.cargame.model.entity.gameobject.car.ai.KamikazeCar;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.entity.gameobject.car.player.FastCar;
import de.cargame.view.ApiHandler;
import de.cargame.view.game.sprites.GameSprites;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class GameInstanceView extends Pane {
    private final SpriteService spriteService;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private GameModelData modelData;

    public GameInstanceView(ApiHandler apiHandler, String playerID) {

        SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
        SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);

        this.spriteService = new SpriteService();

        // add player stats to view and register in backend as observer
        PlayerStats stats = new PlayerStats();
        stats.setLayoutX(0);
        stats.setLayoutY(0);
        apiHandler.getPlayerApi().registerPlayerObserver(stats, playerID);

        for (GameModelData modelData : apiHandler.getGameInstanceApi().getModel()) { //TODO fix namensüberdeckung (modelData)
            if (modelData.getPlayerId().equals(playerID)) {
                this.modelData = modelData;
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

    public void render() {
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

        while (!gameObjectViews.isEmpty()) {
            this.getChildren().add(gameObjectViews.poll());
        }
    }
}
