package view.scoreboard;

import view.CustomScene;
import view.navigation.Direction;
import view.navigation.Navigator;
import view.navigation.Selectable;
import view.common.BackToMenuButton;

public class ScoreBoardScene extends CustomScene {

    private Navigator assignedNavigator;

    public ScoreBoardScene(APIHandler apiHandler){
        super();
        this.assignedNavigator = new ScoreBoardNavigator(apiHandler);
        Selectable dummy = assignedNavigator.getCurrentSelectable();
        Selectable backToMenu = new BackToMenuButton();
        Selectable playAgain = new PlayAgainButton();

        dummy.setNeighbour(Direction.LEFT, playAgain);
        dummy.setNeighbour(Direction.RIGHT, backToMenu);

        playAgain.setNeighbour(Direction.RIGHT, backToMenu);
        backToMenu.setNeighbour(Direction.LEFT, playAgain);

    }

    @Override
    public void render() {
        //TODO
    }
}
