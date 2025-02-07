package de.cargame.view.game.sprites;

/**
 * Manages images used to render life bonuses that players can collect during the game.
 */
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
