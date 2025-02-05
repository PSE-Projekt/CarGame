package de.cargame.view.navigation;

import de.cargame.controller.input.UserInputBundle;
import de.cargame.controller.input.UserInputType;
import de.cargame.model.service.SoundService;
import de.cargame.view.ApiHandler;
import lombok.Getter;
import lombok.Setter;

public abstract class Navigator {
    protected final ApiHandler apiHandler;
    @Getter
    private final Selectable initialSelectable;
    @Setter
    @Getter
    protected Selectable currentSelection;
    protected SoundService soundService = new SoundService();

    public Navigator(ApiHandler apiHandler) {
        this.currentSelection = new DummySelectable();
        this.initialSelectable = this.currentSelection;
        this.apiHandler = apiHandler;
    }

    public void receiveInput(UserInputBundle userInputBundle, String playerID) {
        if (userInputBundle.getLatestInput().isEmpty()) {
            return;
        }

        UserInputType latestInputType = userInputBundle.getLatestInput().get().getUserInputType();

        if (userInputBundle.isFastForward() && this.currentSelection instanceof Clickable) {
            soundService.playSelectSound();
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

            soundService.playChangeSelectionSound();
            this.currentSelection.deselect();
            this.currentSelection = newSelection;
            this.currentSelection.select();
        }
    }

    public void reset() {
        this.currentSelection.setLockedInSelection(false); //TODO: maybe?
        this.currentSelection.deselect();
        this.currentSelection = this.initialSelectable;
        this.currentSelection.select();
    }
}
