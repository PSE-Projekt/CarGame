package de.cargame.view.game.sprites;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameSprites {
    private List<String> paths;

    private Image loadSprite() {
        // TODO: implement
        return null;
    }

    private void setPaths(List<String> paths) {
        this.paths.addAll(paths);
    }

    public ImageView getRandomSprite(String gameObjectId) {
        // TODO: implement
        return null;
    }
}
