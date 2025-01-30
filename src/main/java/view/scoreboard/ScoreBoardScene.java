package view.scoreboard;

import view.ApiHandler;
import view.CustomScene;
import view.navigation.Direction;
import view.navigation.Navigator;
import view.navigation.Selectable;
import view.common.BackToMenuButton;

public class ScoreBoardScene extends CustomScene {

    private final Navigator navigator;

    public ScoreBoardScene(ApiHandler apiHandler){
        super(apiHandler);
        this.navigator = new ScoreBoardNavigator(apiHandler);
    }

    @Override
    public void render() {
        //TODO
    }
}
