package de.cargame.view.game.sprites;

/**
 * Manages image assets for AgileCars.
 * Ensures that the correct visuals are loaded in when this CarType has been selected.
 */
public class AgileCarSprites extends GameSprites {

    @Override
    protected void setWeight() {
        this.weight = 3;
    }

    @Override
    protected void setPaths() {
        this.paths.add("/sprites/agile_car.png");
    }
}
