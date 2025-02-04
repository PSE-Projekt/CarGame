package de.cargame.view;


import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;


public abstract class CustomScene extends Scene {
    protected final ApiHandler apiHandler;

    protected int SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
    protected int SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);

    public CustomScene(ApiHandler apiHandler) {
        super(new VBox(),
                GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH),
                GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT)
        );
        this.SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
        this.SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);

        this.apiHandler = apiHandler;
    }

    public abstract void setup();
}