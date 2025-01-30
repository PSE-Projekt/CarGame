package de.cargame.view.menu;

import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;

public class MenuScene extends CustomScene {
    private Navigator assignedNavigator;

    public MenuScene(ApiHandler apiHandler){
        super(apiHandler);
        this.assignedNavigator = new MenuNavigator(apiHandler);
        Selectable dummy = assignedNavigator.getCurrentSelectable();
        Selectable singlePlayer = new MultiPlayerButton();
        Selectable multiPlayer = new SinglePlayerButton();
        
        dummy.setNeighbour(Direction.LEFT, singlePlayer);
        dummy.setNeighbour(Direction.RIGHT, multiPlayer);

        singlePlayer.setNeighbour(Direction.RIGHT, multiPlayer);
        multiPlayer.setNeighbour(Direction.LEFT, singlePlayer);

    }

    @Override
    public void render() {
        //TODO
    }
}
