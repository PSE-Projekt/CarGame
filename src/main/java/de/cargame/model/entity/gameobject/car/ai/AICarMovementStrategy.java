package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.model.entity.gameobject.Coordinate;

public abstract class AICarMovementStrategy implements MovementStrategy {

    protected final Coordinate gameObjectSpawnCoordinate;
    protected Coordinate targetPos;

    protected final int SCREEN_HEIGHT;


    public AICarMovementStrategy(Coordinate gameObjectSpawnCoordinate) {
        SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);

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
