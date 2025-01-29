package view.selection;

import view.common.BackToMenuButton;
import view.menu.MenuNavigator;
import view.navigation.Direction;
import view.navigation.Navigator;
import view.navigation.Selectable;

public class SelectionInstanceView {

    private Navigator assignedNavigator;
    public SelectionInstanceView(APIHandler apiHandler){
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
