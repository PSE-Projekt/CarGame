package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.model.entity.gameobject.Coordinate;

public abstract class AICarMovementStrategy implements MovementStrategy {

    protected final Coordinate gameObjectSpawnCoordinate;
    protected Coordinate targetPos;


    public AICarMovementStrategy(Coordinate gameObjectSpawnCoordinate) {
        this.gameObjectSpawnCoordinate = gameObjectSpawnCoordinate;
        calcTargetPos();
    }

    @Override
    public abstract void calcTargetPos();

    @Override

    public double getTargetPosX() {
        return targetPos.getX();
    }

    @Override
    public double getTargetPosY() {
        return targetPos.getY();
    }
}
