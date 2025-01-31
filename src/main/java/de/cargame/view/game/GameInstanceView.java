package de.cargame.view.game;

import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.gameobject.*;
import de.cargame.model.entity.gameobject.car.ai.KamikazeCar;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.entity.gameobject.car.player.FastCar;
import de.cargame.view.ApiHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameInstanceView extends Pane {
    private final ApiHandler apiHandler;
    private final SpriteService spriteService;
    private GameModelData modelData;

    public GameInstanceView(ApiHandler apiHandler, String playerID) {
        this.apiHandler = apiHandler;
        this.spriteService = new SpriteService();

        // add player stats to view and register in backend as observer
        PlayerStats stats = new PlayerStats();
        this.apiHandler.getPlayerApi().registerPlayerObserver(stats, playerID); // TODO: discuss with backend team

        for (GameModelData modelData : this.apiHandler.getGameInstanceApi().getModel()) {
            if (modelData.getPlayerId().equals(playerID)) {
                this.modelData = modelData;
            }
        }

        if (this.modelData == null) {
            throw new IllegalArgumentException("Player ID not found in game model data");
        }

        this.getChildren().add(stats);
    }

    public void render() {
        modelData.getGameObjects().forEach(gameObject -> {
            // render game objects
            // TODO: decide whether to use 'instance of', 'getClass() : String' 
            //  or to implement a GameObjectType used by all GameObject elements
            
            // first try to use instance of

            if (gameObject instanceof Building) {
                // render building
            } else if (gameObject instanceof Obstacle) {
                // render obstacle
            } else if (gameObject instanceof Life) {
                // render life
            } else if (gameObject instanceof KamikazeCar) {
                // render kamikaze car
            } else if (gameObject instanceof AgileCar) {
                // render agile player car
            } else if (gameObject instanceof FastCar) {
                // render fast player car
            } else if(gameObject instanceof RoadMark) {
                // render road mark
            } else {
                throw new IllegalArgumentException("Unknown game object type");
            }
        });
    }

    private void placeInView(ImageView imageView, Coordinate coordinate, Dimension dimension) {
        // place image in view
        imageView.setX(coordinate.getX());
        imageView.setY(coordinate.getY());
        imageView.setFitWidth(dimension.getWidth());
        imageView.setFitHeight(dimension.getHeight());

        this.getChildren().add(imageView);
    }
}
