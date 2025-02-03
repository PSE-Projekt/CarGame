package de.cargame.view.selection;

import de.cargame.controller.entity.GameMode;
import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import de.cargame.view.navigation.Navigator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static de.cargame.config.GameConfig.SCREEN_HEIGHT;
import static de.cargame.config.GameConfig.SCREEN_WIDTH;

public class SelectionScene extends CustomScene {
    private Navigator assignedNavigator;

    public SelectionScene(ApiHandler apiHandler) {
        super(apiHandler);

        VBox root = (VBox) this.getRoot();
        if(apiHandler.getGameStateApi().getGameMode().equals(GameMode.SINGLEPLAYER)){

        } else {

        }

    }

    private Pane createBorder() {
        Pane border = new Pane();
        border.setStyle("-fx-background-color: black;");
        border.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT / 4);
        return border;
    }

    @Override
    public void render() {

    }

    @Override
    public void setup() {

    }

}
