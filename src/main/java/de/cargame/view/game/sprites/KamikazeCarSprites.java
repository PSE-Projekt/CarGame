package de.cargame.view.game.sprites;

public class KamikazeCarSprites extends GameSprites {
    @Override
    protected void setWeight() {
        this.weight = 3;
    }

    @Override
    protected void setPaths() {
        this.paths.add("/sprites/kamikaze_car#1.png");
        this.paths.add("/sprites/kamikaze_car#2.png");
    }
}
