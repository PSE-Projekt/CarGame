package de.cargame.view.game;

import de.cargame.view.game.sprites.*;
/**
 * Utility class for handling all different GameSprites.
 * This includes the retrieval of random sprites based on the GameObject a sprite will represent.
 */
class SpriteService {
    private final FastCarSprites fastCarSprites;
    private final BuildingSprites buildingSprites;
    private final LifeSprites lifeSprites;
    private final AgileCarSprites agileCarSprites;
    private final RoadMarkSprites roadMarkSprites;
    private final KamikazeCarSprites kamikazeCarSprites;
    private final ObstacleSprites obstacleSprites;

    SpriteService() {
        this.fastCarSprites = new FastCarSprites();
        this.buildingSprites = new BuildingSprites();
        this.lifeSprites = new LifeSprites();
        this.agileCarSprites = new AgileCarSprites();
        this.roadMarkSprites = new RoadMarkSprites();
        this.kamikazeCarSprites = new KamikazeCarSprites();
        this.obstacleSprites = new ObstacleSprites();
    }

    GameObjectView getRandomLifeSprite(String gameObjectId) {
        return this.lifeSprites.getRandomSprite(gameObjectId);
    }

    GameObjectView getRandomObstacleSprite(String gameObjectId) {
        return this.obstacleSprites.getRandomSprite(gameObjectId);
    }

    GameObjectView getRandomKamikazeSprite(String gameObjectId) {
        return this.kamikazeCarSprites.getRandomSprite(gameObjectId);
    }

    GameObjectView getRandomRoadMarkSprite(String gameObjectId) {
        return this.roadMarkSprites.getRandomSprite(gameObjectId);
    }

    GameObjectView getRandomAgileCarSprite(String gameObjectId) {
        return this.agileCarSprites.getRandomSprite(gameObjectId);
    }

    GameObjectView getRandomFastCarSprite(String gameObjectId) {
        return this.fastCarSprites.getRandomSprite(gameObjectId);
    }

    GameObjectView getRandomBuildingSprite(String gameObjectId) {
        return this.buildingSprites.getRandomSprite(gameObjectId);
    }
}
