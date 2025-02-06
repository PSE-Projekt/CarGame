package de.cargame.view.game.sprites;

public class LifeSprites extends GameSprites {
    @Override
    protected void setWeight() {
        this.weight = 1;
    }

    @Override
    protected void setPaths() {
        this.paths.add("/sprites/life.png");
    }
}
