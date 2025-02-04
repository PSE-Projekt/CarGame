package de.cargame.view.game;

import de.cargame.view.game.sprites.*;
import javafx.scene.image.ImageView;

public class SpriteService {
    private final FastCarSprites fastCarSprites;
    private final BuildingSprites buildingSprites;
    private final LifeSprites lifeSprites;
    private final AgileCarSprites agileCarSprites;
    private final RoadMarkSprites roadMarkSprites;
    private final KamikazeCarSprites kamikazeCarSprites;
    private final ObstacleSprites obstacleSprites;

    public SpriteService() {
        this.fastCarSprites = new FastCarSprites();
        this.buildingSprites = new BuildingSprites();
        this.lifeSprites = new LifeSprites();
        this.agileCarSprites = new AgileCarSprites();
        this.roadMarkSprites = new RoadMarkSprites();
        this.kamikazeCarSprites = new KamikazeCarSprites();
        this.obstacleSprites = new ObstacleSprites();
    }

    public ImageView getRandomLifeSprite(String gameObjectId) {
        return this.lifeSprites.getRandomSprite(gameObjectId);
    }

    public ImageView getRandomObstacleSprite(String gameObjectId) {
        return this.obstacleSprites.getRandomSprite(gameObjectId);
    }

    public ImageView getRandomKamikazeSprite(String gameObjectId) {
        return this.kamikazeCarSprites.getRandomSprite(gameObjectId);
    }

    public ImageView getRandomRoadMarkSprite(String gameObjectId) {
        return this.roadMarkSprites.getRandomSprite(gameObjectId);
    }

    public ImageView getRandomAgileCarSprite(String gameObjectId) {
        return this.agileCarSprites.getRandomSprite(gameObjectId);
    }

    public ImageView getRandomFastCarSprite(String gameObjectId) {
        return this.fastCarSprites.getRandomSprite(gameObjectId);
    }

    public ImageView getRandomBuildingSprite(String gameObjectId) {
        return this.buildingSprites.getRandomSprite(gameObjectId);
    }
}
