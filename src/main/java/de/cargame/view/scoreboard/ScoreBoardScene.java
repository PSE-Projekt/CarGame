package de.cargame.view.scoreboard;

import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;

public class ScoreBoardScene extends CustomScene {

    public ScoreBoardScene(ApiHandler apiHandler){
        super(apiHandler);
        Navigator navigator = new ScoreBoardNavigator(apiHandler);
    }

    @Override
    public void render() {
        //TODO
    }
}
