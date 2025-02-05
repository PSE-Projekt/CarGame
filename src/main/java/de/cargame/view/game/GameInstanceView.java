package de.cargame.view.game;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.gameobject.*;
import de.cargame.model.entity.gameobject.car.ai.KamikazeCar;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.entity.gameobject.car.player.FastCar;
import de.cargame.view.ApiHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

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
        apiHandler.getPlayerApi().registerPlayerObserver(stats, playerID); // TODO: discuss with backend team

        for (GameModelData modelData : apiHandler.getGameInstanceApi().getModel()) {
            if (modelData.getPlayerId().equals(playerID)) {
                this.modelData = modelData;
            }
        }

        if (this.modelData == null) {
            throw new IllegalArgumentException("Player ID not found in game model data");
        }

        this.setHeight((double) SCREEN_HEIGHT / 2);
        this.setWidth(SCREEN_WIDTH);
        this.setStyle("-fx-background-color: grey;");
        this.getChildren().add(stats);
    }

    public void render() {

        this.getChildren().clear();
        for (GameObject gameObject : modelData.getGameObjects()) {
            ImageView objectView;

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

            this.getChildren().add(objectView);
        }
    }
}
