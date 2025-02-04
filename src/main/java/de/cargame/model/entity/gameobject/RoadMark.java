package de.cargame.model.entity.gameobject;

public class RoadMark extends GameObject {


    public RoadMark(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType) {
        super(coordinate, dimension, gameObjectBoundType);
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
        this.isCollidable = false;
    }
}
