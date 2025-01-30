package de.cargame.mockController.entity;

import java.util.List;

public class GameModelData {

    private final String playerId;
    private final List<GameObject> gameObjects;


    public GameModelData(String playerId, final List<GameObject> gameObjects) {
        this.playerId = playerId;
        this.gameObjects = gameObjects;
    }
}
