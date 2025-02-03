package de.cargame.view.navigation;

import de.cargame.view.ApiHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class SceneButton extends Selectable implements Clickable{
    private final Image defaultDisplay;
    private final Image displayOnSelection;
    private final ImageView display;

public SceneButton(String pathDefaultImg, String pathSelectedImg) {
    this.defaultDisplay =  loadImage(pathDefaultImg);
    this.displayOnSelection =  loadImage(pathSelectedImg);
    this.display =  new ImageView(defaultDisplay);

    this.getChildren().add(display);
}
//TODO: schau ob loadImage nicht besser gemacht werden kann.
    private Image loadImage(String path) {
        try {
            return new Image(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            throw new IllegalArgumentException("Bild konnte nicht geladen werden: " + path);
        }
    }

    @Override
    public abstract void onClick(ApiHandler apiHandler, String playerID);

    @Override
    public void select() {
        display.setImage(displayOnSelection);
    }

    @Override
    public void deselect(){
        display.setImage(defaultDisplay);    }
}
