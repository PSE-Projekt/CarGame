package de.cargame.view.game.sprites;
/**
 * Manages image assets for FastCars.
 * Ensures that the correct visuals are loaded in when this CarType has been selected.
 */
public class FastCarSprites extends GameSprites {
    @Override
    protected void setWeight() {
        this.weight = 3;
    }

    @Override
    protected void setPaths() {
        this.paths.add("/sprites/fast_car.png");
    }
}
