package de.cargame.view.selection;

import de.cargame.view.ApiHandler;
import de.cargame.view.common.BackToMenuButton;
import de.cargame.view.menu.MenuNavigator;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;

public class SelectionInstanceView {

    public SelectionInstanceView(ApiHandler apiHandler){
        Navigator assignedNavigator = new MenuNavigator(apiHandler);
        Selectable dummy = assignedNavigator.getCurrentSelectable();


        Selectable backToMenuButton = new BackToMenuButton();
        Selectable fastCarButton = new CarSelectionPanel();
        Selectable agileCarButton = new CarSelectionPanel();

        dummy.setNeighbour(Direction.LEFT, fastCarButton);
        dummy.setNeighbour(Direction.RIGHT, agileCarButton);

        fastCarButton.setNeighbour(Direction.RIGHT, agileCarButton);
        fastCarButton.setNeighbour(Direction.DOWN, backToMenuButton);

        agileCarButton.setNeighbour(Direction.LEFT, fastCarButton);
        agileCarButton.setNeighbour(Direction.DOWN, backToMenuButton);

        backToMenuButton.setNeighbour(Direction.UP, fastCarButton);

    }
}
