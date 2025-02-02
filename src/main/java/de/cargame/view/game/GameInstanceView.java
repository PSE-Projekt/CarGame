package de.cargame.view.game;

import de.cargame.config.GameConfig;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.gameobject.*;
import de.cargame.model.entity.gameobject.car.ai.KamikazeCar;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.entity.gameobject.car.player.FastCar;
import de.cargame.view.ApiHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameInstanceView extends Pane {
    private final SpriteService spriteService;
    private GameModelData modelData;

    public GameInstanceView(ApiHandler apiHandler, String playerID) {
        this.spriteService = new SpriteService();

        // add player stats to view and register in backend as observer
        PlayerStats stats = new PlayerStats();
        apiHandler.getPlayerApi().registerPlayerObserver(stats); // TODO: discuss with backend team

        for (GameModelData modelData : apiHandler.getGameInstanceApi().getModel()) {
            if (modelData.getPlayerId().equals(playerID)) {
                this.modelData = modelData;
            }
        }

        if (this.modelData == null) {
            throw new IllegalArgumentException("Player ID not found in game model data");
        }

        this.setHeight((double) GameConfig.SCREEN_HEIGHT / 2);
        this.setWidth(GameConfig.SCREEN_WIDTH);

        this.getChildren().add(stats);
    }

    public void render() {
        modelData.getGameObjects().forEach(gameObject -> {
            ImageView objectView;

            if (gameObject instanceof Building) {
                objectView = spriteService.getRandomBuildingSprite(gameObject.getId());
            } else if (gameObject instanceof Obstacle) {
                objectView = spriteService.getRandomObstacleSprite(gameObject.getId());
            } else if (gameObject instanceof Life) {
                objectView = spriteService.getRandomLifeSprite(gameObject.getId());
            } else if (gameObject instanceof KamikazeCar) {
                objectView = spriteService.getRandomKamikazeSprite(gameObject.getId());
            } else if (gameObject instanceof AgileCar) {
                objectView = spriteService.getRandomAgileCarSprite(gameObject.getId());
            } else if (gameObject instanceof FastCar) {
                objectView = spriteService.getRandomFastCarSprite(gameObject.getId());
            } else if(gameObject instanceof RoadMark) {
                objectView = spriteService.getRandomRoadMarkSprite(gameObject.getId());
            } else {
                throw new IllegalArgumentException("Unknown game object type");
            }

            objectView.setX(gameObject.getX());
            objectView.setY(gameObject.getY());
            objectView.setFitWidth(gameObject.getWidth());
            objectView.setFitHeight(gameObject.getHeight());

            this.getChildren().add(objectView);
        });
    }
}
