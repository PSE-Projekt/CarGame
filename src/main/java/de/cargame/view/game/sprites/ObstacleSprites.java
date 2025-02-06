package de.cargame.view.game.sprites;

public class ObstacleSprites extends GameSprites {

    @Override
    protected void setWeight() {
        this.weight = 1;
    }

    @Override
    protected void setPaths() {
        this.paths.add("/sprites/obstacle#1.png");
        this.paths.add("/sprites/obstacle#2.png");
    }
}
