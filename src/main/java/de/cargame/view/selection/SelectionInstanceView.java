package de.cargame.view.selection;

import de.cargame.view.ApiHandler;
import de.cargame.view.common.BackToMenuButton;
import de.cargame.view.menu.MenuNavigator;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;

public class SelectionInstanceView {

    private Navigator assignedNavigator;
    public SelectionInstanceView(ApiHandler apiHandler){
        this.assignedNavigator = new MenuNavigator(apiHandler);
        Selectable dummy = assignedNavigator.getCurrentSelectable();


        Selectable backButton = new BackToMenuButton();
        Selectable  fastCarButton = new CarSelectionPanel();
        Selectable  agileCarButton = new CarSelectionPanel();

        dummy.setNeighbour(Direction.LEFT, fastCarButton);
        dummy.setNeighbour(Direction.RIGHT, agileCarButton);

        fastCarButton.setNeighbour(Direction.RIGHT, agileCarButton);
        fastCarButton.setNeighbour(Direction.DOWN, backButton);

        agileCarButton.setNeighbour(Direction.LEFT, fastCarButton);
        agileCarButton.setNeighbour(Direction.DOWN, backButton);

        backButton.setNeighbour(Direction.UP, fastCarButton);

    }
}
