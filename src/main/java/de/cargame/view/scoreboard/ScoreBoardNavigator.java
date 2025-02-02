package de.cargame.view.scoreboard;

import de.cargame.controller.input.UserInputBundle;
import de.cargame.controller.input.UserInputType;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;

public class ScoreBoardNavigator extends Navigator {
    public ScoreBoardNavigator(ApiHandler apiHandler) {
        super(apiHandler);
    }

    @Override
    public void receiveInput(UserInputBundle userInputBundle, String playerID) {
        if (userInputBundle.getLatestInput().isEmpty()) {
            return;
        }

        UserInputType latestInputType = userInputBundle.getLatestInput().get().getUserInputType();

        if (latestInputType.equals(UserInputType.CONFIRM) && this.currentSelection instanceof Clickable) {
            ((Clickable) this.currentSelection).onClick(this.apiHandler, playerID);
        }

        Selectable newSelection = null;

        if (latestInputType.equals(UserInputType.UP)) {
            newSelection = currentSelection.getNeighbour(Direction.UP);
        } else if (latestInputType.equals(UserInputType.LEFT)) {
            newSelection = currentSelection.getNeighbour(Direction.LEFT);
        } else if (latestInputType.equals(UserInputType.RIGHT)) {
            newSelection = currentSelection.getNeighbour(Direction.RIGHT);
        } else if (latestInputType.equals(UserInputType.DOWN)) {
            newSelection = currentSelection.getNeighbour(Direction.DOWN);
        }

        if (newSelection != null) {
            // TODO: play select sound
            this.currentSelection = newSelection;
        }
    }
}
