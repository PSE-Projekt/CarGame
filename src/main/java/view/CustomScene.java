package view;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public abstract class CustomScene extends Scene {
    private final APIHandler apiHandler;

    public CustomScene(APIHandler apiHandler) {
        super(new VBox());
        this.apiHandler = apiHandler;
    }

    public abstract void render();
}