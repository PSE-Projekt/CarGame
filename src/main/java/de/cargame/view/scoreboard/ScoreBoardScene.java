package de.cargame.view.scoreboard;

import de.cargame.controller.api.GameStateApi;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import de.cargame.view.common.BackToMenuButton;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ScoreBoardScene extends CustomScene {
    private final Selectable backToMenuButton;
    private final Selectable playAgainButton;
    private final Navigator navigator;

    public ScoreBoardScene(ApiHandler apiHandler) {
        super(apiHandler);
        this.navigator = new ScoreBoardNavigator(apiHandler);

        this.playAgainButton = new PlayAgainButton();
        this.backToMenuButton = new BackToMenuButton();

        this.playAgainButton.setNeighbour(Direction.LEFT, this.backToMenuButton);
        this.backToMenuButton.setNeighbour(Direction.RIGHT, this.playAgainButton);

        this.navigator.getInitialSelectable().setNeighbour(Direction.LEFT, this.backToMenuButton);
        this.navigator.getInitialSelectable().setNeighbour(Direction.RIGHT, this.playAgainButton);
    }

    public void setup() throws IllegalStateException { // TODO: erweiterbarer gestalten
        GameStateApi gameStateApi = this.apiHandler.getGameStateApi();

        if (!gameStateApi.getGameState().equals(GameState.SCORE_BOARD)) {
            throw new IllegalStateException(
                    "Game not over yet. Rendering the score board is an invalid action at this moment"
            );
        }

        Parent scoreView = getScoreView(gameStateApi);

        ((VBox) this.getRoot()).getChildren().addAll(scoreView, this.playAgainButton, this.backToMenuButton);

        this.navigator.reset();
        this.apiHandler.getInputReceiverGamePad().assignNavigator(this.navigator);
        this.apiHandler.getInputReceiverKeyboard().assignNavigator(this.navigator);
    }

    private Parent getScoreView(GameStateApi gameStateApi) {
        Parent scoreView;

        if (gameStateApi.getGameMode().equals(GameMode.SINGLEPLAYER)) {
            scoreView = new ScoreViewSinglePlayer(this.apiHandler);
        } else if (gameStateApi.getGameMode().equals(GameMode.MULTIPLAYER)) {
            scoreView = new ScoreViewMultiplayer(this.apiHandler);
        } else {
            throw new IllegalStateException(
                    "Game mode not set yet. This implicates a wrong control flow of changing game related information."
            );
        }
        return scoreView;
    }

    @Override
    public void render() {
        // this method does nothing because no rendering in relation to frequency is needed for this scene due
        // to it only having static content
    }
}
