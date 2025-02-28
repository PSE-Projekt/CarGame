package de.cargame.view.game.sprites;

/**
 * Manages image assets for KamikazeCars. Ensures that their unique looks are
 * displayed in the game.
 */
public class KamikazeCarSprites extends GameSprites {
    @Override
    protected void setWeight() {
        this.weight = 3;
    }

    @Override
    protected void setPaths() {
        this.paths.add("/sprites/kamikaze_car#1.png");
        this.paths.add("/sprites/kamikaze_car#2.png");
        this.paths.add("/sprites/kamikaze_car#3.png");
        this.paths.add("/sprites/kamikaze_car#4.png");
    }
}
