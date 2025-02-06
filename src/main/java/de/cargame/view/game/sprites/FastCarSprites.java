package de.cargame.view.game.sprites;

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
