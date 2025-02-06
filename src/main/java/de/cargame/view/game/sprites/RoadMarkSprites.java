package de.cargame.view.game.sprites;

public class RoadMarkSprites extends GameSprites {

    @Override
    protected void setWeight() {
        this.weight = 0;
    }

    @Override
    protected void setPaths() {
        this.paths.add("/sprites/road_mark.png");
    }
}
