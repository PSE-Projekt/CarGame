package de.cargame.view.navigation;

import de.cargame.view.ApiHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

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
    private Image loadImage(String path){
        Image image = null;
        try {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("image in path: " + path +" couldnt be loaded");
        }
        return image;
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
