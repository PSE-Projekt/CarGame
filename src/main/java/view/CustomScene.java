package view;


import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class CustomScene extends Scene {
    public CustomScene(Parent root) {
        super(root);
    }

    public abstract void render();
}