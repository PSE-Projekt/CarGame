package de.cargame.view.game.sprites;

/**
 * Image assets for buildings, used as background elements
 */
public class BuildingSprites extends GameSprites {
    @Override
    protected void setWeight() {
        this.weight = 1;
    }

    @Override
    protected void setPaths() {
        this.paths.add("/sprites/building#1.png");
        this.paths.add("/sprites/building#2.png");
    }
}
