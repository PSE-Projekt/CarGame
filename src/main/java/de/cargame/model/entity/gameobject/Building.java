package de.cargame.model.entity.gameobject;

import de.cargame.controller.entity.GameMode;

/**
 * Represents a Building object in the game world.
 * <p>
 * The Building class extends the GameObject class and implements specific logic
 * to define static, collidable, and despawnable properties for a building entity
 * in the game. It also provides movement behavior tailored for the Building object.
 */
public class Building extends GameObject {

    public Building(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, GameMode gameMode) {
        super(coordinate, dimension, gameObjectBoundType, gameMode);
    }

    @Override
    public void move(double deltaTime, boolean isFastForwarding) {
        if (isFastForwarding) {
            moveBy(-GAME_SPEED_FAST_FORWARD * deltaTime, 0);
        } else {
            moveBy(-GAME_SPEED * deltaTime, 0);
        }
    }

    @Override
    protected void setIsStatic() {
        this.isStatic = true;
    }

    @Override
    protected void setDespawnable() {
        this.isDespawnable = true;
    }

    @Override
    protected void setCollidable() {
        this.isCollidable = true;
    }
}
