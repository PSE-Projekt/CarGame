package view;


import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public abstract class CustomScene extends Scene {
    protected final ApiHandler apiHandler;

    public CustomScene(ApiHandler apiHandler) {
        super(new VBox());
        this.apiHandler = apiHandler;
    }

    public abstract void render();
}