package de.cargame.model.entity.gameobject.car.ai;

public interface MovementStrategy {

    void calcTargetPos();

    double getTargetPosX();

    double getTargetPosY();

}
