package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.model.entity.gameobject.Coordinate;

public class StraightMovementStrategy extends AICarMovementStrategy {

    public StraightMovementStrategy(Coordinate gameObjectSpawnCoordinate) {
        super(gameObjectSpawnCoordinate);
    }

    @Override
    public void calcTargetPos() {
        this.targetPos = new Coordinate(-1000, gameObjectSpawnCoordinate.getY());
    }
}
