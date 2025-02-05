package de.cargame.view.scoreboard;

import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import de.cargame.view.common.BackToMenuButton;
import de.cargame.view.config.TextConfig;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ScoreBoardScene extends CustomScene {
    private final HBox buttonContainer;
    private final Navigator navigator;
    private final VBox sceneContent;

    public ScoreBoardScene(ApiHandler apiHandler) {
        super(apiHandler);
        sceneContent = new VBox();
        sceneContent.setStyle("-fx-background-color: #131d34;");
        sceneContent.setPrefSize(SCREEN_WIDTH, (double) SCREEN_HEIGHT /2);
        sceneContent.setAlignment(Pos.CENTER);

        this.navigator = new ScoreBoardNavigator(apiHandler);

        // instantiate navigation graph and static UI elements
        PlayAgainButton playAgainButton = new PlayAgainButton();
        BackToMenuButton backToMenuButton = new BackToMenuButton();

        playAgainButton.setNeighbour(Direction.LEFT, backToMenuButton);
        backToMenuButton.setNeighbour(Direction.RIGHT, playAgainButton);

        this.navigator.getInitialSelectable().setNeighbour(Direction.LEFT, backToMenuButton);
        this.navigator.getInitialSelectable().setNeighbour(Direction.RIGHT, playAgainButton);

        this.buttonContainer = new HBox(50);
        this.buttonContainer.getChildren().addAll(backToMenuButton, playAgainButton);
        this.buttonContainer.setAlignment(Pos.CENTER);
        this.buttonContainer.setPrefSize(sceneContent.getPrefWidth(), sceneContent.getPrefHeight() / 3);

        ((VBox) this.getRoot()).getChildren().add(sceneContent);

        // configure root to have a vertical layout and black background and configured size
        this.configureRoot();

    }

    public void setup() throws IllegalStateException {
        sceneContent.getChildren().clear();

        Text scoreTitle = TextConfig.makeH1("Score Board");

        VBox titleContainer = new VBox();
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setPrefSize(sceneContent.getPrefWidth(), sceneContent.getPrefHeight() / 3);
        titleContainer.getChildren().add(scoreTitle);

        GameStateAPI gameStateApi = this.apiHandler.getGameStateApi();

        if (!gameStateApi.getGameState().equals(GameState.SCORE_BOARD)) {
            throw new IllegalStateException(
                    "Game not over yet. Rendering the score board is an invalid action at this moment"
            );
        }

        ScoreView scoreView = new ScoreView(this.apiHandler);
        scoreView.setPrefSize(sceneContent.getPrefWidth(), sceneContent.getPrefHeight() / 3);

        sceneContent.getChildren().addAll(titleContainer, scoreView, this.buttonContainer);

        this.navigator.reset();
        this.apiHandler.getInputReceiverGamePad().assignNavigator(this.navigator);
        this.apiHandler.getInputReceiverKeyboard().assignNavigator(this.navigator);
    }
}
