package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;

public abstract class AICarMovementStrategy implements MovementStrategy {

    protected final Coordinate gameObjectSpawnCoordinate;
    protected final int SCREEN_HEIGHT;
    protected Coordinate targetPos;
    protected GameMode gameMode;


    protected AICarMovementStrategy(Coordinate gameObjectSpawnCoordinate, GameMode gameMode) {
        SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);
        this.gameMode = gameMode;
        this.gameObjectSpawnCoordinate = gameObjectSpawnCoordinate;
        calcTargetPos();
    }

    @Override

    public double getTargetPosX() {
        return targetPos.getX();
    }

    @Override
    public double getTargetPosY() {
        return targetPos.getY();
    }
}
